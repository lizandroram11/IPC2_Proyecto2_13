package com.ipc2dic_proyecto2_pareja13.service;

import com.ipc2dic_proyecto2_pareja13.model.Centro;
import com.ipc2dic_proyecto2_pareja13.model.EstadoPaquete;
import com.ipc2dic_proyecto2_pareja13.model.Paquete;
import com.ipc2dic_proyecto2_pareja13.repository.CentroRepository;
import com.ipc2dic_proyecto2_pareja13.repository.PaqueteRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaqueteService {

    private final PaqueteRepository paqueteRepository;
    private final CentroRepository centroRepository;

    public PaqueteService(PaqueteRepository paqueteRepository, CentroRepository centroRepository) {
        this.paqueteRepository = paqueteRepository;
        this.centroRepository = centroRepository;
    }

    public Map<String, Paquete> listarPaquetes() {
        return paqueteRepository.listarTodos();
    }

    public Paquete obtenerPaquete(String id) {
        Paquete p = paqueteRepository.buscarPorId(id);
        if (p == null) throw new IllegalArgumentException("Paquete no encontrado: " + id);
        return p;
    }

    public void crearPaquete(Paquete paquete) {
        paqueteRepository.guardar(paquete);
        Centro centro = centroRepository.buscarPorId(paquete.getCentroActual());
        if (centro != null) {
            centro.agregarPaquete(paquete);
        }
    }

    public void actualizarPaquete(String id, Paquete paqueteActualizado) {
        Paquete existente = paqueteRepository.buscarPorId(id);
        if (existente == null) throw new IllegalArgumentException("Paquete no encontrado: " + id);
        paqueteRepository.guardar(paqueteActualizado);

        Centro centro = centroRepository.buscarPorId(paqueteActualizado.getCentroActual());
        if (centro != null && !centro.getPaquetes().contains(paqueteActualizado)) {
            centro.agregarPaquete(paqueteActualizado);
        }
    }

    public void eliminarPaquete(String id) {
        Paquete p = paqueteRepository.buscarPorId(id);
        if (p == null) throw new IllegalArgumentException("Paquete no encontrado: " + id);
        if (p.getEstado() == EstadoPaquete.EN_TRANSITO || p.getEstado() == EstadoPaquete.ENTREGADO) {
            throw new IllegalStateException("No se puede eliminar un paquete en tránsito o entregado");
        }
        paqueteRepository.eliminar(id);
    }

    public void actualizarEstado(String id, EstadoPaquete nuevo) {
        Paquete p = obtenerPaquete(id);
        if (nuevo == EstadoPaquete.EN_TRANSITO && p.getEstado() == EstadoPaquete.PENDIENTE) {
            p.marcarEnTransito();
        } else if (nuevo == EstadoPaquete.ENTREGADO && p.getEstado() == EstadoPaquete.EN_TRANSITO) {
            p.marcarEntregado();
        } else {
            throw new IllegalStateException("Transición inválida para paquete " + id);
        }
    }
}