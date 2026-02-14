package apifrases.controller;

import java.time.LocalDate;

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

import apifrases.dto.FraseCreateDTO;
import apifrases.dto.FraseDTO;
import apifrases.service.FraseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import apifrases.config.APIConfig;

/**
 * Controlador REST para la gestión de Frases.
 */
@RestController
@RequestMapping(APIConfig.API_PATH + "/frases")
@RequiredArgsConstructor
public class FraseController {

    private final FraseService fraseService;

    /**
     * Lista todas las frases.
     */
    @GetMapping
    public ResponseEntity<Page<FraseDTO>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(fraseService.listarTodas(pageable));
    }

    /**
     * Busca frase por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FraseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(fraseService.buscarPorId(id));
    }

    /**
     * Devuelve la frase del día programada para hoy sin fecha o añadiendo la fecha al endpoint.
     */
    @GetMapping("/dia")
    public ResponseEntity<FraseDTO> getFraseDelDia(
            @RequestParam(required = false) LocalDate fecha) {

        return ResponseEntity.ok(fraseService.obtenerFraseDelDia(fecha));
    }

    /**
     * Crea nueva frase.
     */
    @PostMapping
    public ResponseEntity<FraseDTO> crear(@Valid @RequestBody FraseCreateDTO dto) {
        return new ResponseEntity<>(fraseService.guardar(dto), HttpStatus.CREATED);
    }

    /**
     * Actualiza frase existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FraseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody FraseCreateDTO dto) {
        return ResponseEntity.ok(fraseService.actualizar(id, dto));
    }

    // Elimina frase por ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        fraseService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
