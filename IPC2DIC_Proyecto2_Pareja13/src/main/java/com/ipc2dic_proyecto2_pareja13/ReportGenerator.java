package com.ipc2dic_proyecto2_pareja13;

import com.ipc2dic_proyecto2_pareja13.repository.*;
import com.ipc2dic_proyecto2_pareja13.util.XmlExporter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportGenerator {

    @Bean
    CommandLineRunner exportReport(CentroRepository centroRepo,
                                   MensajeroRepository mensajeroRepo,
                                   PaqueteRepository paqueteRepo,
                                   SolicitudRepository solicitudRepo) {
        return args -> {
            XmlExporter.exportarReporte(
                    "src/main/resources/reporteFinal.xml",
                    centroRepo,
                    mensajeroRepo,
                    paqueteRepo,
                    solicitudRepo
            );
            System.out.println("Reporte final exportado a src/main/resources/reporteFinal.xml");
        };
    }
}