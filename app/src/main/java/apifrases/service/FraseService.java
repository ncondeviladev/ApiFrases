package apifrases.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import apifrases.dto.FraseCreateDTO;
import apifrases.dto.FraseDTO;
import apifrases.model.Autor;
import apifrases.model.Categoria;
import apifrases.model.Frase;
import apifrases.repository.AutorRepository;
import apifrases.repository.CategoriaRepository;
import apifrases.repository.FraseRepository;

import lombok.RequiredArgsConstructor;

/**
 * Servicio de logica de negocio de frases
 */
@Service
@RequiredArgsConstructor
public class FraseService {

    private final FraseRepository fraseRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;

    /**
     * Obtiene una lista paginada de todas las frases.
     * 
     * @param pageable Configuración de paginación
     * @return FraseDTO
     */
    @Transactional(readOnly = true)
    public Page<FraseDTO> listarTodas(Pageable pageable) {
        return fraseRepository.findAll(pageable).map(this::mapToDTO);
    }

    /**
     * Busca una frase por su ID.
     * 
     * @param id Identificador de la frase
     * @return FraseDTO
     */
    @Transactional //Eliminamos el readOnly
    public FraseDTO buscarPorId(Long id) {
        Frase frase = fraseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Frase no encontrada con id: " + id));

        //Añadimos el contador
        frase.setVisitas(frase.getVisitas() + 1);
        fraseRepository.save(frase);

        return mapToDTO(frase);
    }

    /**
     * Obtiene la frase programada para un dia especifico o la de hoy.
     * 
     * @param fecha Fecha a buscar opcional
     * @return FraseDTO
     */
    @Transactional(readOnly = true)
    public FraseDTO obtenerFraseDelDia(LocalDate fecha) {
        // Intenta buscar ficha concreta, si no busca para hoy
        LocalDate targetDate = (fecha != null) ? fecha : LocalDate.now();

        Frase frase = fraseRepository.findByFechaProgramada(targetDate)
                .orElseThrow(() -> new RuntimeException("No hay frase programada para la fecha " + targetDate));

        return mapToDTO(frase);
    }

    /**
     * Busca frases de un autor concreto.
     * 
     * @param autorId  ID del autor
     * @param pageable Paginación
     * @return Pagina de FraseDTO
     */
    @Transactional(readOnly = true)
    public Page<FraseDTO> buscarPorAutor(Long autorId, Pageable pageable) {
        // Verificamos que el autor exista primero
        if (!autorRepository.existsById(autorId)) {
            throw new RuntimeException("Autor no existe");
        }
        return fraseRepository.findByAutorId(autorId, pageable).map(this::mapToDTO);
    }

    /**
     * Busca frases de una categoria concreta.
     * 
     * @param catId    ID de la categoria
     * @param pageable Paginación
     * @return Pagina de FraseDTO
     */
    @Transactional(readOnly = true)
    public Page<FraseDTO> buscarPorCategoria(Long catId, Pageable pageable) {
        if (!categoriaRepository.existsById(catId)) {
            throw new RuntimeException("Categoría no existe");
        }
        return fraseRepository.findByCategoriaId(catId, pageable).map(this::mapToDTO);
    }

    /**
     * Busca frases que contengan un texto.
     * 
     * @param texto    Texto a buscar
     * @param pageable Paginación
     * @return Pagina de FraseDTO
     */
    @Transactional(readOnly = true)
    public Page<FraseDTO> buscarPorTexto(String texto, Pageable pageable) {
        return fraseRepository.findByTextoContainingIgnoreCase(texto, pageable).map(this::mapToDTO);
    }

    /**
     * Crea una nueva frase.
     * 
     * @param dto Datos de la frase
     * @return FraseDTO
     */
    @Transactional
    public FraseDTO guardar(FraseCreateDTO dto) {
        // Validar unicidad de frase del día
        if (dto.getFechaProgramada() != null) {
            Optional<Frase> existente = fraseRepository.findByFechaProgramada(dto.getFechaProgramada());
            if (existente.isPresent()) {
                throw new RuntimeException("Ya existe una frase programada para el " + dto.getFechaProgramada());
            }
        }

        Autor autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Frase frase = new Frase();
        frase.setTexto(dto.getTexto());
        frase.setFechaProgramada(dto.getFechaProgramada());
        frase.setAutor(autor);
        frase.setCategoria(categoria);

        return mapToDTO(fraseRepository.save(frase));
    }

    /**
     * Actualiza una frase existente parcialmente
     * 
     * @param id  Identificador de la frase a actualizar
     * @param dto Datos de la frase a actualizar
     * @return FraseDTO
     */
    @Transactional
    public FraseDTO actualizar(Long id, FraseCreateDTO dto) {
        Frase frase = fraseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Frase no encontrada con id: " + id));

        if (dto.getTexto() != null && !dto.getTexto().isBlank()) {
            frase.setTexto(dto.getTexto());
        }

        if (dto.getFechaProgramada() != null) {
            if (!dto.getFechaProgramada().equals(frase.getFechaProgramada())) {
                if (fraseRepository.findByFechaProgramada(dto.getFechaProgramada()).isPresent()) {
                    throw new RuntimeException("Ya existe una frase programada para el " + dto.getFechaProgramada());
                }
                frase.setFechaProgramada(dto.getFechaProgramada());
            }
        }

        if (dto.getAutorId() != null) {
            Autor autor = autorRepository.findById(dto.getAutorId())
                    .orElseThrow(() -> new RuntimeException("Autor no encontrado"));
            frase.setAutor(autor);
        }

        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            frase.setCategoria(categoria);
        }

        return mapToDTO(fraseRepository.save(frase));
    }

    /**
     * Elimina una frase por su ID.
     * 
     * @param id Identificador de la frase a eliminar
     */
    @Transactional
    public void eliminar(Long id) {
        if (!fraseRepository.existsById(id)) {
            throw new RuntimeException("Frase no encontrada");
        }
        fraseRepository.deleteById(id);
    }

    /**
     * Mapea una entidad Frase a un DTO de Frase.
     * 
     * @param frase Entidad Frase
     * @return FraseDTO
     */
    private FraseDTO mapToDTO(Frase frase) {
        return new FraseDTO(
                frase.getId(),
                frase.getTexto(),
                frase.getFechaProgramada(),
                frase.getAutor().getNombre(),
                frase.getCategoria().getNombre(),
                frase.getVisitas());
    }
}
