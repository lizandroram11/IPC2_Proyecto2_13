package com.ipc2dic_proyecto2_pareja13.controller;

import com.ipc2dic_proyecto2_pareja13.repository.*;
import com.ipc2dic_proyecto2_pareja13.util.XmlImporter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/api/importar")
public class ImportController {

    private final CentroRepository centroRepo;
    private final RutaRepository rutaRepo;
    private final MensajeroRepository mensajeroRepo;
    private final PaqueteRepository paqueteRepo;
    private final SolicitudRepository solicitudRepo;

    public ImportController(CentroRepository centroRepo,
                            RutaRepository rutaRepo,
                            MensajeroRepository mensajeroRepo,
                            PaqueteRepository paqueteRepo,
                            SolicitudRepository solicitudRepo) {
        this.centroRepo = centroRepo;
        this.rutaRepo = rutaRepo;
        this.mensajeroRepo = mensajeroRepo;
        this.paqueteRepo = paqueteRepo;
        this.solicitudRepo = solicitudRepo;
    }

    @PostMapping
    public ResponseEntity<String> importarArchivo(@RequestParam("file") MultipartFile file) {
        try {
            // Guardar temporalmente el archivo recibido
            File tempFile = File.createTempFile("import", ".xml");
            file.transferTo(tempFile);

            // Usar tu XmlImporter para cargar datos
            XmlImporter.importarDatos(tempFile.getAbsolutePath(),
                    centroRepo, rutaRepo, mensajeroRepo, paqueteRepo, solicitudRepo);

            return ResponseEntity.ok("Datos importados correctamente desde archivo subido");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al importar: " + e.getMessage());
        }
    }
}