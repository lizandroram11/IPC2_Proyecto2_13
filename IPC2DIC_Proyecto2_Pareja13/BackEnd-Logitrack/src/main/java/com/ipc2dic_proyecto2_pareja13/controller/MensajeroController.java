package com.ipc2dic_proyecto2_pareja13.controller;

import com.ipc2dic_proyecto2_pareja13.model.EstadoMensajero;
import com.ipc2dic_proyecto2_pareja13.model.Mensajero;
import com.ipc2dic_proyecto2_pareja13.service.MensajeroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/mensajeros")
public class MensajeroController {

    private final MensajeroService mensajeroService;

    public MensajeroController(MensajeroService mensajeroService) {

        this.mensajeroService = mensajeroService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Mensajero>> listarMensajeros() {
        return ResponseEntity.ok(mensajeroService.listarMensajeros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mensajero> obtenerMensajero(@PathVariable String id) {
        try {
            return ResponseEntity.ok(mensajeroService.obtenerMensajero(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> crearMensajero(@RequestBody Mensajero mensajero) {
        mensajeroService.crearMensajero(mensajero);
        return ResponseEntity.ok("Mensajero creado con ID: " + mensajero.getId());
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<String> cambiarEstado(@PathVariable String id, @RequestParam EstadoMensajero estado) {
        try {
            mensajeroService.cambiarEstado(id, estado);
            return ResponseEntity.ok("Estado del mensajero " + id + " cambiado a " + estado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("Mensajero no encontrado: " + id);
        }
    }

    @PutMapping("/{id}/centro")
    public ResponseEntity<String> reasignarCentro(@PathVariable String id, @RequestParam String nuevoCentro) {
        try {
            mensajeroService.reasignarCentro(id, nuevoCentro);
            return ResponseEntity.ok("Mensajero " + id + " reasignado al centro " + nuevoCentro);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("Mensajero no encontrado: " + id);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body("" + e.getMessage());
        }
    }
}