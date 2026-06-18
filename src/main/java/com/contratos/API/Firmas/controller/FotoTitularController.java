package com.contratos.API.Firmas.controller;

import com.contratos.API.Firmas.model.ApiResponse;
import com.contratos.API.Firmas.model.FotoTitularRequest;
import com.contratos.API.Firmas.service.FotoTitularService;
import jakarta.validation.Valid;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/titulares")
@RestController
@RequestMapping("/api/titulares")
public class FotoTitularController {

    private final FotoTitularService fotoTitularService;

    public FotoTitularController(FotoTitularService fotoTitularService) {
        this.fotoTitularService = fotoTitularService;
    }

    @PostMapping("/foto")
    public ResponseEntity<ApiResponse> guardarFoto(@Valid @RequestBody FotoTitularRequest request) {
        String ruta = fotoTitularService.guardarFotoYRegistrar(request);
        return ResponseEntity.ok(ApiResponse.ok("Foto guardada y registrada correctamente", ruta));
    }

    /**
     * GET /api/titulares/ping
     * test para verificar que el servidor está activo y recibiendo solicitudes
     * correctamente
     */
    @GetMapping("/ping")
    public ResponseEntity<ApiResponse> ping() {
        return ResponseEntity.ok(ApiResponse.ok("Servidor activo", null));
    }

    /**
     * POST /api/titulares/test
     * test endpoint
     */
    @PostMapping("/test")
    public ResponseEntity<Map<String, Object>> testRequest(@RequestBody FotoTitularRequest request) {
        Map<String, Object> echo = new LinkedHashMap<>();
        echo.put("idTitular", request.getIdTitular());
        echo.put("nombreCompleto", request.getNombreCompleto());
        echo.put("extension", request.getExtension());
        // Don't echo the full base64 — just confirm it arrived and show its length
        echo.put("imagenBase64_longitud", request.getImagenBase64() != null
                ? request.getImagenBase64().length()
                : 0);
        echo.put("imagenBase64_primeros50chars", request.getImagenBase64() != null
                ? request.getImagenBase64().substring(0, Math.min(50, request.getImagenBase64().length()))
                : null);
        return ResponseEntity.ok(echo);
    }

}
