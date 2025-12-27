package com.ipc2dic_proyecto2_pareja13.controller;

import com.ipc2dic_proyecto2_pareja13.repository.*;
import com.ipc2dic_proyecto2_pareja13.util.XmlExporter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reporte")
public class ReporteController {

    private final CentroRepository centroRepo;
    private final MensajeroRepository mensajeroRepo;
    private final PaqueteRepository paqueteRepo;
    private final SolicitudRepository solicitudRepo;

    public ReporteController(CentroRepository centroRepo,
                             MensajeroRepository mensajeroRepo,
                             PaqueteRepository paqueteRepo,
                             SolicitudRepository solicitudRepo) {
        this.centroRepo = centroRepo;
        this.mensajeroRepo = mensajeroRepo;
        this.paqueteRepo = paqueteRepo;
        this.solicitudRepo = solicitudRepo;
    }

    @GetMapping
    public ResponseEntity<String> generarReporte() {
        try {
            String ruta = "resultado.xml"; // nombre del archivo de salida
            XmlExporter.exportarReporte(ruta, centroRepo, mensajeroRepo, paqueteRepo, solicitudRepo);
            return ResponseEntity.ok("Reporte generado en: " + ruta);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al generar reporte: " + e.getMessage());
        }
    }
}