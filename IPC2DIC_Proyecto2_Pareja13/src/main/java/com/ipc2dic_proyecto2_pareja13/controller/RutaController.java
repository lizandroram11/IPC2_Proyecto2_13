package com.ipc2dic_proyecto2_pareja13.controller;

import com.ipc2dic_proyecto2_pareja13.model.Ruta;
import com.ipc2dic_proyecto2_pareja13.service.RutaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {

    private final RutaService rutaService;

    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    // GET /api/rutas
    @GetMapping
    public ResponseEntity<Map<String, Ruta>> listarRutas() {
        return ResponseEntity.ok(rutaService.listarRutas());
    }

    // GET /api/rutas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Ruta> obtenerRuta(@PathVariable String id) {
        return ResponseEntity.ok(rutaService.obtenerRuta(id));
    }

    // POST /api/rutas
    @PostMapping
    public ResponseEntity<String> crearRuta(@RequestBody Ruta ruta) {
        rutaService.crearRuta(ruta);
        return ResponseEntity.ok("Ruta creada con ID: " + ruta.getId());
    }

    // PUT /api/rutas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarRuta(@PathVariable String id, @RequestBody Ruta ruta) {
        rutaService.actualizarRuta(id, ruta);
        return ResponseEntity.ok("Ruta actualizada con ID: " + id);
    }

    // DELETE /api/rutas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRuta(@PathVariable String id) {
        rutaService.eliminarRuta(id);
        return ResponseEntity.ok("Ruta eliminada con ID: " + id);
    }
}