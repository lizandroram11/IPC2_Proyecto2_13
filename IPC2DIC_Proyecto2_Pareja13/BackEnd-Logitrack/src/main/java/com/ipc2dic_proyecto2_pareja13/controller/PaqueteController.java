package com.ipc2dic_proyecto2_pareja13.controller;
import com.ipc2dic_proyecto2_pareja13.model.Paquete;
import com.ipc2dic_proyecto2_pareja13.service.PaqueteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/paquetes")
public class PaqueteController {

    private final PaqueteService paqueteService;

    public PaqueteController(PaqueteService paqueteService) {
        this.paqueteService = paqueteService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Paquete>> listarPaquetes() {
        return ResponseEntity.ok(paqueteService.listarPaquetes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paquete> obtenerPaquete(@PathVariable String id) {
        return ResponseEntity.ok(paqueteService.obtenerPaquete(id));
    }

    @PostMapping
    public ResponseEntity<String> crearPaquete(@RequestBody Paquete paquete) {
        paqueteService.crearPaquete(paquete);
        return ResponseEntity.ok("Paquete creado con ID: " + paquete.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarPaquete(@PathVariable String id, @RequestBody Paquete paquete) {
        paqueteService.actualizarPaquete(id, paquete);
        return ResponseEntity.ok("Paquete actualizado con ID: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaquete(@PathVariable String id) {
        paqueteService.eliminarPaquete(id);
        return ResponseEntity.ok("Paquete eliminado con ID: " + id);
    }
}