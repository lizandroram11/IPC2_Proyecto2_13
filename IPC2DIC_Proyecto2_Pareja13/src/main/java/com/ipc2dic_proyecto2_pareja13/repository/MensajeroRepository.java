package com.ipc2dic_proyecto2_pareja13.repository;

import com.ipc2dic_proyecto2_pareja13.model.Mensajero;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MensajeroRepository {
    private Map<String, Mensajero> mensajeros = new HashMap<>();

    public void guardar(Mensajero mensajero) {

        mensajeros.put(mensajero.getId(), mensajero);
    }

    public Mensajero buscarPorId(String id) {

        return mensajeros.get(id);
    }

    public Map<String, Mensajero> listarTodos() {

        return mensajeros;
    }

    public void eliminar(String id) {

        mensajeros.remove(id);
    }

    public void limpiar() {

        mensajeros.clear();
    }
}
