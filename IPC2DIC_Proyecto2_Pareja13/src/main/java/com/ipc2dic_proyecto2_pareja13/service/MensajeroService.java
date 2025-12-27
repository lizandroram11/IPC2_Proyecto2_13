package com.ipc2dic_proyecto2_pareja13.service;

import com.ipc2dic_proyecto2_pareja13.model.Centro;
import com.ipc2dic_proyecto2_pareja13.model.EstadoMensajero;
import com.ipc2dic_proyecto2_pareja13.model.Mensajero;
import com.ipc2dic_proyecto2_pareja13.repository.CentroRepository;
import com.ipc2dic_proyecto2_pareja13.repository.MensajeroRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MensajeroService {

    private final MensajeroRepository mensajeroRepository;
    private final CentroRepository centroRepository;

    public MensajeroService(MensajeroRepository mensajeroRepository, CentroRepository centroRepository) {
        this.mensajeroRepository = mensajeroRepository;
        this.centroRepository = centroRepository;
    }

    public Map<String, Mensajero> listarMensajeros() {
        return mensajeroRepository.listarTodos();
    }

    public Mensajero obtenerMensajero(String id) {
        Mensajero m = mensajeroRepository.buscarPorId(id);
        if (m == null) throw new IllegalArgumentException("Mensajero no encontrado: " + id);
        return m;
    }

    public void crearMensajero(Mensajero mensajero) {
        mensajeroRepository.guardar(mensajero);
        Centro centro = centroRepository.buscarPorId(mensajero.getCentroActual());
        if (centro != null) {
            centro.getMensajeros().add(mensajero);
        }
    }

    public void cambiarEstado(String id, EstadoMensajero estado) {
        Mensajero m = obtenerMensajero(id);
        if (estado == EstadoMensajero.EN_TRANSITO) {
            m.asignarEnvio();
        } else {
            m.marcarDisponible();
        }
        mensajeroRepository.guardar(m);
    }

    public void reasignarCentro(String id, String nuevoCentro) {
        Mensajero m = obtenerMensajero(id);
        if (m.getEstado() == EstadoMensajero.EN_TRANSITO) {
            throw new IllegalStateException("No se puede reasignar un mensajero en tránsito");
        }
        Centro centro = centroRepository.buscarPorId(nuevoCentro);
        if (centro == null) {
            throw new IllegalArgumentException("Centro no encontrado: " + nuevoCentro);
        }
        m.setCentroActual(nuevoCentro);
        centro.getMensajeros().add(m);
        mensajeroRepository.guardar(m);
    }
}