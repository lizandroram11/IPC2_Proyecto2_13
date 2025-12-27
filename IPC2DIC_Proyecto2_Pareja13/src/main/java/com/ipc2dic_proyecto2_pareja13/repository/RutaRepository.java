package com.ipc2dic_proyecto2_pareja13.repository;

import com.ipc2dic_proyecto2_pareja13.model.Ruta;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class RutaRepository {
    private Map<String, Ruta> rutas = new HashMap<>();

    public void guardar(Ruta ruta) {
        rutas.put(ruta.getId(), ruta);
    }

    public Ruta buscarPorId(String id) {
        return rutas.get(id);
    }

    public Map<String, Ruta> listarTodos() {
        return rutas;
    }

    public void eliminar(String id) {
        rutas.remove(id);
    }

    public void limpiar() {
        rutas.clear();
    }
}