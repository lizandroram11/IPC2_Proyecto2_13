package com.ipc2dic_proyecto2_pareja13.controller;

import com.ipc2dic_proyecto2_pareja13.model.EstadoPaquete;
import com.ipc2dic_proyecto2_pareja13.service.PaqueteService;
import com.ipc2dic_proyecto2_pareja13.service.SolicitudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    private final SolicitudService solicitudService;
    private final PaqueteService paqueteService;

    public EnvioController(SolicitudService solicitudService, PaqueteService paqueteService) {
        this.solicitudService = solicitudService;
        this.paqueteService = paqueteService;
    }

    // Asignación directa de mensajero a paquete
    @PutMapping("/asignar")
    public ResponseEntity<String> asignarMensajero(
            @RequestParam String paqueteId,
            @RequestParam String mensajeroId) {
        String resultado = solicitudService.asignarMensajeroDirecto(paqueteId, mensajeroId);
        return ResponseEntity.ok(resultado);
    }

    // Actualizar estado de un paquete
    @PutMapping("/{id}/estado")
    public ResponseEntity<String> actualizarEstadoPaquete(
            @PathVariable String id,
            @RequestParam EstadoPaquete nuevoEstado) {
        try {
            paqueteService.actualizarEstado(id, nuevoEstado);
            return ResponseEntity.ok("Estado del paquete " + id + " cambiado a " + nuevoEstado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("Paquete no encontrado: " + id);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(" " + e.getMessage());
        }
    }
}