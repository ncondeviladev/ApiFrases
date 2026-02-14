package apifrases.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import apifrases.dto.AutorCreateDTO;
import apifrases.dto.AutorDTO;
import apifrases.model.Autor;
import apifrases.repository.AutorRepository;

import lombok.RequiredArgsConstructor;

/**
 * Servicio de logica de negocio de autores realiza conversiones DTO/Entidad
 */
@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;

    /**
     * Obtiene una lista paginada de todos los autores.
     * 
     * @param pageable Configuración de paginación page y size
     * @return AutorDTO
     */
    @Transactional(readOnly = true)
    public Page<AutorDTO> listarTodos(Pageable pageable) {
        return autorRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    @Transactional(readOnly = true)
    public AutorDTO buscarPorId(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor no encontrado con id: " + id));
        return mapToDTO(autor);
    }

    /**
     * Crea un nuevo autor en la base de datos.
     * 
     * @param dto Datos recibidos del nuevo autor.
     * @return El autor creado convertido a DTO.
     * @throws RuntimeException Si ya existe un autor con el mismo nombre.
     */
    @Transactional
    public AutorDTO guardar(AutorCreateDTO dto) {

        if (autorRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe un autor con ese nombre");
        }

        Autor autor = new Autor();
        autor.setNombre(dto.getNombre());
        autor.setAnioNacimiento(dto.getAnioNacimiento());
        autor.setAnioFallecimiento(dto.getAnioFallecimiento());
        autor.setProfesion(dto.getProfesion());

        return mapToDTO(autorRepository.save(autor));
    }

    /*
     * Actualiza un autor existente
     * Comprueba cada atributo del dto y lo actualiza si es distinto al origina sin borrarlos los null
     */
    @Transactional
    public AutorDTO actualizar(Long id, AutorCreateDTO dto) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));

        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            if (!autor.getNombre().equals(dto.getNombre()) &&
                    autorRepository.findByNombre(dto.getNombre()).isPresent()) {
                throw new RuntimeException("Ya existe otro autor con ese nombre");
            }
            autor.setNombre(dto.getNombre());
        }

        if (dto.getAnioNacimiento() != null) {
            autor.setAnioNacimiento(dto.getAnioNacimiento());
        }

        if (dto.getAnioFallecimiento() != null) {
            autor.setAnioFallecimiento(dto.getAnioFallecimiento());
        }

        if (dto.getProfesion() != null && !dto.getProfesion().isBlank()) {
            autor.setProfesion(dto.getProfesion());
        }

        return mapToDTO(autorRepository.save(autor));
    }

    /*
     * Elimina un autor existente
     */
    @Transactional
    public void eliminar(Long id) {
        if (!autorRepository.existsById(id)) {
            throw new RuntimeException("Autor no encontrado");
        }
        autorRepository.deleteById(id);
    }

    /*
     * Mapper manual Entity -> DTO
     */
    private AutorDTO mapToDTO(Autor autor) {
        return new AutorDTO(
                autor.getId(),
                autor.getNombre(),
                autor.getAnioNacimiento(),
                autor.getAnioFallecimiento(),
                autor.getProfesion(),
                autor.getFrases() != null ? autor.getFrases().size() : 0);
    }
}
