package com.ipc2dic_proyecto2_pareja13.repository;

import com.ipc2dic_proyecto2_pareja13.model.Paquete;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PaqueteRepository {
    private Map<String, Paquete> paquetes = new HashMap<>();

    public void guardar(Paquete paquete) {

        paquetes.put(paquete.getId(), paquete);
    }

    public Paquete buscarPorId(String id) {

        return paquetes.get(id);
    }

    public Map<String, Paquete> listarTodos() {

        return paquetes;
    }

    public void eliminar(String id) {

        paquetes.remove(id);
    }

    public void limpiar() {
        paquetes.clear();
    }
}
