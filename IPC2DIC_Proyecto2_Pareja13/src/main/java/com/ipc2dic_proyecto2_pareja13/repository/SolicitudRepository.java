package com.ipc2dic_proyecto2_pareja13.repository;

import com.ipc2dic_proyecto2_pareja13.model.Solicitud;
import org.springframework.stereotype.Repository;

import java.util.PriorityQueue;

@Repository
public class SolicitudRepository {
    private PriorityQueue<Solicitud> solicitudes = new PriorityQueue<>();

    public void guardar(Solicitud solicitud) {
        solicitudes.add(solicitud);
    }

    public Solicitud obtenerMasPrioritaria() {
        return solicitudes.peek();
    }

    public Solicitud procesarMasPrioritaria() {
        return solicitudes.poll();
    }

    public PriorityQueue<Solicitud> listarTodas() {
        return solicitudes;
    }

    // Buscar por ID recorriendo la cola
    public Solicitud buscarPorId(String id) {
        for (Solicitud s : solicitudes) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    // Eliminar por ID recorriendo la cola
    public void eliminar(String id) {
        Solicitud encontrada = buscarPorId(id);
        if (encontrada != null) {
            solicitudes.remove(encontrada);
        }
    }

    public void limpiar() {
        solicitudes.clear();
    }
}