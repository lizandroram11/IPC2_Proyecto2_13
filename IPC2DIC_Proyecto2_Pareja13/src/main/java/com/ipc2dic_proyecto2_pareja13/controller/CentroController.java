// CentroController
package com.ipc2dic_proyecto2_pareja13.controller;

import com.ipc2dic_proyecto2_pareja13.model.Centro;
import com.ipc2dic_proyecto2_pareja13.model.Mensajero;
import com.ipc2dic_proyecto2_pareja13.model.Paquete;
import com.ipc2dic_proyecto2_pareja13.service.CentroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/centros")
public class CentroController {

    private final CentroService centroService;

    public CentroController(CentroService centroService) {
        this.centroService = centroService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Centro>> listarCentros() {
        return ResponseEntity.ok(centroService.listarCentros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Centro> obtenerCentro(@PathVariable String id) {
        return ResponseEntity.ok(centroService.obtenerCentro(id));
    }

    @GetMapping("/{id}/paquetes")
    public ResponseEntity<List<Paquete>> listarPaquetesDeCentro(@PathVariable String id) {
        return ResponseEntity.ok(centroService.listarPaquetesDeCentro(id));
    }

    @GetMapping("/{id}/mensajeros")
    public ResponseEntity<List<Mensajero>> listarMensajerosDeCentro(@PathVariable String id) {
        return ResponseEntity.ok(centroService.listarMensajerosDeCentro(id));
    }
}