// CentroService
package com.ipc2dic_proyecto2_pareja13.service;

import com.ipc2dic_proyecto2_pareja13.model.Centro;
import com.ipc2dic_proyecto2_pareja13.model.Mensajero;
import com.ipc2dic_proyecto2_pareja13.model.Paquete;
import com.ipc2dic_proyecto2_pareja13.repository.CentroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CentroService {
    private final CentroRepository centroRepository;

    public CentroService(CentroRepository centroRepository) {

        this.centroRepository = centroRepository;
    }

    public Map<String, Centro> listarCentros() {

        return centroRepository.listarTodos();
    }

    public Centro obtenerCentro(String id) {
        Centro c = centroRepository.buscarPorId(id);
        if (c == null) throw new IllegalArgumentException("Centro no encontrado: " + id);
        return c;
    }

    public List<Paquete> listarPaquetesDeCentro(String id) {

        return obtenerCentro(id).getPaquetes();
    }

    public List<Mensajero> listarMensajerosDeCentro(String id) {

        return obtenerCentro(id).getMensajeros();
    }
}