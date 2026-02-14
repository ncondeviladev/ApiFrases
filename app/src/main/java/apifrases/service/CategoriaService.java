package apifrases.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import apifrases.dto.CategoriaCreateDTO;
import apifrases.dto.CategoriaDTO;
import apifrases.model.Categoria;
import apifrases.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Servicio de logica de negocio de Categorias  realiza conversiones DTO/Entidad
 */
@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    /**
     * Obtiene una lista paginada de todas las categorias.
     * 
     * @param pageable Configuración de paginación page y size
     * @return CategoriaDTO
     */
    @Transactional(readOnly = true)
    public Page<CategoriaDTO> listarTodas(Pageable pageable) {
        return categoriaRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    /**
     * Busca una categoria por su ID.
     * 
     * @param id Identificador de la categoria
     * @return CategoriaDTO
     */
    @Transactional(readOnly = true)
    public CategoriaDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));
        return mapToDTO(categoria);
    }

    /**
     * Crea una nueva categoria.
     * 
     * @param dto Datos recibidos de la nueva categoria
     * @return CategoriaDTO
     */
    @Transactional
    public CategoriaDTO guardar(CategoriaCreateDTO dto) {
        if (categoriaRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        return mapToDTO(categoriaRepository.save(categoria));
    }

    /*
     * Actualiza una categoria existente parciamente
     */
    @Transactional
    public CategoriaDTO actualizar(Long id, CategoriaCreateDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            if (!categoria.getNombre().equals(dto.getNombre()) &&
                    categoriaRepository.findByNombre(dto.getNombre()).isPresent()) {
                throw new RuntimeException("Ya existe otra categoría con ese nombre");
            }
            categoria.setNombre(dto.getNombre());
        }

        return mapToDTO(categoriaRepository.save(categoria));
    }

    /*
     * Elimina una categoria existente
     */
    @Transactional
    public void eliminar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada");
        }
        categoriaRepository.deleteById(id);
    }

    /*
     * Mapper manual Entity -> DTO
     */
    private CategoriaDTO mapToDTO(Categoria categoria) {
        return new CategoriaDTO(categoria.getId(), categoria.getNombre());
    }
}
