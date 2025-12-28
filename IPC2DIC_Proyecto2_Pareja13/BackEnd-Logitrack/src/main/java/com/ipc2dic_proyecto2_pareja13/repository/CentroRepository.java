package com.ipc2dic_proyecto2_pareja13.repository;

import com.ipc2dic_proyecto2_pareja13.model.Centro;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

@Repository
public class CentroRepository {
    private Map<String, Centro> centros = new HashMap<>();

    public void guardar(Centro centro) {

        centros.put(centro.getId(), centro);
    }

    public Centro buscarPorId(String id) {

        return centros.get(id);
    }

    public Map<String, Centro> listarTodos() {

        return centros;
    }

    public void eliminar(String id) {

        centros.remove(id);
    }

    public void limpiar() {

        centros.clear();
    }
}