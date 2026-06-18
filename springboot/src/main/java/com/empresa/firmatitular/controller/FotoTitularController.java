package com.empresa.firmatitular.controller;

import com.empresa.firmatitular.model.ApiResponse;
import com.empresa.firmatitular.model.FotoTitularRequest;
import com.empresa.firmatitular.service.FotoTitularService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/titulares")
public class FotoTitularController {

    private final FotoTitularService fotoTitularService;

    public FotoTitularController(FotoTitularService fotoTitularService) {
        this.fotoTitularService = fotoTitularService;
    }

    /**
     * Recibe la foto del titular en Base64, la guarda en la carpeta
     * compartida de Windows y registra la ruta en PMT_App_Ventas_Titulares.RutaFirma
     *
     * POST /api/titulares/foto
     * Body: { "idTitular": 1024, "imagenBase64": "...", "extension": "jpg" }
     */
    @PostMapping("/foto")
    public ResponseEntity<ApiResponse> guardarFoto(@Valid @RequestBody FotoTitularRequest request) {
        String ruta = fotoTitularService.guardarFotoYRegistrar(request);
        return ResponseEntity.ok(ApiResponse.ok("Foto guardada y registrada correctamente", ruta));
    }
}
