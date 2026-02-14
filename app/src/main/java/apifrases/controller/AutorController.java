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

import apifrases.dto.AutorCreateDTO;
import apifrases.dto.AutorDTO;
import apifrases.dto.FraseDTO;
import apifrases.service.AutorService;
import apifrases.service.FraseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import apifrases.config.APIConfig;

/**
 * Controlador REST para la gestión de Autores.
 */
@RestController
@RequestMapping(APIConfig.API_PATH + "/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService autorService;
    private final FraseService fraseService;

    /**
     * Lista todos los autores de forma paginada.
     */
    @GetMapping
    public ResponseEntity<Page<AutorDTO>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(autorService.listarTodos(pageable));
    }

    /**
     * Busca un autor por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(autorService.buscarPorId(id));
    }

    /*
     * Endpoint extra: listar frases de un autor
     */
    @GetMapping("/{id}/frases")
    public ResponseEntity<Page<FraseDTO>> getFrasesDeAutor(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(fraseService.buscarPorAutor(id, pageable));
    }

    /**
     * Crea un nuevo autor.
     */
    @PostMapping
    public ResponseEntity<AutorDTO> crear(@Valid @RequestBody AutorCreateDTO dto) {
        return new ResponseEntity<>(autorService.guardar(dto), HttpStatus.CREATED);
    }

    /**
     * Actualiza un autor existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AutorDTO> actualizar(@PathVariable Long id, @Valid @RequestBody AutorCreateDTO dto) {
        return ResponseEntity.ok(autorService.actualizar(id, dto));
    }

    /**
     * Elimina un autor por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        autorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
