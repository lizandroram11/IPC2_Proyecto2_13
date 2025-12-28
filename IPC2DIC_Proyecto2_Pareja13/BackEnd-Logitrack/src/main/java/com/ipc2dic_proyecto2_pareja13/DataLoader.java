package com.ipc2dic_proyecto2_pareja13;

import com.ipc2dic_proyecto2_pareja13.repository.*;
import com.ipc2dic_proyecto2_pareja13.util.XmlImporter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(CentroRepository centroRepo,
                               RutaRepository rutaRepo,
                               MensajeroRepository mensajeroRepo,
                               PaqueteRepository paqueteRepo,
                               SolicitudRepository solicitudRepo) {
        return args -> {
            XmlImporter.importarDatos("src/main/resources/data.xml",
                    centroRepo, rutaRepo, mensajeroRepo, paqueteRepo, solicitudRepo);
            System.out.println("Datos iniciales cargados desde data.xml");
        };
    }
}

