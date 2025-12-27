package com.ipc2dic_proyecto2_pareja13.controller;

import com.ipc2dic_proyecto2_pareja13.model.Solicitud;
import com.ipc2dic_proyecto2_pareja13.service.SolicitudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    // GET /api/solicitudes
    @GetMapping
    public ResponseEntity<List<Solicitud>> listarSolicitudes() {
        return ResponseEntity.ok(solicitudService.listarSolicitudes());
    }

    // POST /api/solicitudes
    @PostMapping
    public ResponseEntity<String> crearSolicitud(@RequestBody Solicitud solicitud) {
        solicitudService.crearSolicitud(solicitud);
        return ResponseEntity.ok("Solicitud creada con ID: " + solicitud.getId());
    }

    // POST /api/solicitudes/procesar
    @PostMapping("/procesar")
    public ResponseEntity<String> procesarSolicitud() {
        return ResponseEntity.ok(solicitudService.procesarSolicitud());
    }

    // POST /api/solicitudes/procesar/{n}
    @PostMapping("/procesar/{n}")
    public ResponseEntity<List<String>> procesarNSolicitudes(@PathVariable int n) {
        return ResponseEntity.ok(solicitudService.procesarNSolicitudes(n));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarSolicitud(@PathVariable String id) {
        solicitudService.eliminarSolicitud(id);
        return ResponseEntity.ok("Solicitud eliminada con ID: " + id);
    }
}