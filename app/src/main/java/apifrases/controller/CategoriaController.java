package apifrases.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import apifrases.dto.CategoriaCreateDTO;
import apifrases.dto.CategoriaDTO;
import apifrases.dto.FraseDTO;
import apifrases.service.CategoriaService;
import apifrases.service.FraseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import apifrases.config.APIConfig;

/**
 * Controlador REST para la gestión de Categorías.
 */
@RestController
@RequestMapping(APIConfig.API_PATH + "/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final FraseService fraseService;

    /**
     * Lista todas las categorías.
     */
    @GetMapping
    public ResponseEntity<Page<CategoriaDTO>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(categoriaService.listarTodas(pageable));
    }

    /**
     * Busca categoría por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    /**
     * Lista frases de una categoría.
     */
    @GetMapping("/{id}/frases")
    public ResponseEntity<Page<FraseDTO>> getFrasesDeCategoria(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(fraseService.buscarPorCategoria(id, pageable));
    }

    /**
     * Crea nueva categoría.
     */
    @PostMapping
    public ResponseEntity<CategoriaDTO> crear(@Valid @RequestBody CategoriaCreateDTO dto) {
        return new ResponseEntity<>(categoriaService.guardar(dto), HttpStatus.CREATED);
    }

    /**
     * Actualiza categoría existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaCreateDTO dto) {
        return ResponseEntity.ok(categoriaService.actualizar(id, dto));
    }

    /**
     * Elimina categoría por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
