package com.ipc2dic_proyecto2_pareja13.service;

import com.ipc2dic_proyecto2_pareja13.model.Ruta;
import com.ipc2dic_proyecto2_pareja13.repository.RutaRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RutaService {

    private final RutaRepository rutaRepository;

    public RutaService(RutaRepository rutaRepository) {

        this.rutaRepository = rutaRepository;
    }

    public Map<String, Ruta> listarRutas() {

        return rutaRepository.listarTodos();
    }

    public Ruta obtenerRuta(String id) {

        return rutaRepository.buscarPorId(id);
    }

    public void crearRuta(Ruta ruta) {

        rutaRepository.guardar(ruta);
    }

    public void actualizarRuta(String id, Ruta ruta) {

        rutaRepository.guardar(ruta); // sobrescribe si ya existe
    }

    public void eliminarRuta(String id) {

        rutaRepository.eliminar(id);
    }
}