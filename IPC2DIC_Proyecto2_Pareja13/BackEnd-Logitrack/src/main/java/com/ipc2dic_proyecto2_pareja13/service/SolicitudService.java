package com.ipc2dic_proyecto2_pareja13.service;

import com.ipc2dic_proyecto2_pareja13.model.*;
import com.ipc2dic_proyecto2_pareja13.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final PaqueteRepository paqueteRepository;
    private final MensajeroRepository mensajeroRepository;
    private final RutaRepository rutaRepository;

    public SolicitudService(SolicitudRepository solicitudRepository,
                            PaqueteRepository paqueteRepository,
                            MensajeroRepository mensajeroRepository,
                            RutaRepository rutaRepository) {
        this.solicitudRepository = solicitudRepository;
        this.paqueteRepository = paqueteRepository;
        this.mensajeroRepository = mensajeroRepository;
        this.rutaRepository = rutaRepository;
    }

    // Listar solicitudes
    public List<Solicitud> listarSolicitudes() {

        return new ArrayList<>(solicitudRepository.listarTodas());
    }

    // Crear nueva solicitud
    public void crearSolicitud(Solicitud solicitud) {

        solicitudRepository.guardar(solicitud);
    }

    // Procesar la solicitud más prioritaria
    public String procesarSolicitud() {
        Solicitud solicitud = solicitudRepository.procesarMasPrioritaria();
        if (solicitud == null) return "No hay solicitudes pendientes";

        Paquete paquete = paqueteRepository.buscarPorId(solicitud.getPaqueteId());
        if (paquete == null) {
            return "Solicitud fallida: paquete no existe";
        }
        if (paquete.getEstado() != EstadoPaquete.PENDIENTE) {
            return "Solicitud fallida: paquete no está pendiente";
        }

        // Buscar mensajero disponible en el mismo centro
        Mensajero mensajeroDisponible = mensajeroRepository.listarTodos().values().stream()
                .filter(m -> m.estaDisponible() && m.getCentroActual().equals(paquete.getCentroActual()))
                .findFirst()
                .orElse(null);

        if (mensajeroDisponible == null) {
            return "Solicitud fallida: no hay mensajeros disponibles en el centro " + paquete.getCentroActual();
        }

        if (paquete.getPeso() > mensajeroDisponible.getCapacidad()) {
            return "Solicitud fallida: capacidad del mensajero excedida";
        }

        Ruta ruta = rutaRepository.listarTodos().values().stream()
                .filter(r -> r.getOrigen().equals(paquete.getCentroActual()) &&
                        r.getDestino().equals(paquete.getDestino()))
                .findFirst()
                .orElse(null);

        if (ruta == null) {
            return "Solicitud fallida: no existe ruta entre " + paquete.getCentroActual() + " y " + paquete.getDestino();
        }

        // Actualizar estados
        mensajeroDisponible.asignarEnvio();
        paquete.marcarEnTransito();
        solicitud.marcarAtendida();

        return "Solicitud procesada: paquete " + paquete.getId() +
                " asignado a mensajero " + mensajeroDisponible.getNombre() +
                " desde " + ruta.getOrigen() + " hacia " + ruta.getDestino();
    }


    // Procesar N solicitudes
    public List<String> procesarNSolicitudes(int n) {
        List<String> resultados = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            resultados.add(procesarSolicitud());
        }
        return resultados;
    }

    // Eliminar solicitud por ID
    public void eliminarSolicitud(String id) {
        Solicitud solicitud = solicitudRepository.buscarPorId(id);
        if (solicitud == null) {
            throw new IllegalArgumentException("Solicitud no encontrada: " + id);
        }
        solicitudRepository.eliminar(id);
    }

    // 🔹 Asignación directa de mensajero
    public String asignarMensajeroDirecto(String paqueteId, String mensajeroId) {
        Paquete paquete = paqueteRepository.buscarPorId(paqueteId);
        Mensajero mensajero = mensajeroRepository.buscarPorId(mensajeroId);

        if (paquete == null) return "Paquete no encontrado";
        if (mensajero == null) return "Mensajero no encontrado";
        if (!paquete.getCentroActual().equals(mensajero.getCentroActual())) {
            return "Paquete y mensajero no están en el mismo centro";
        }
        if (!mensajero.estaDisponible()) {
            return "Mensajero no disponible";
        }

        mensajero.asignarEnvio();
        paquete.marcarEnTransito();
        return "Mensajero " + mensajeroId + " asignado al paquete " + paqueteId;
    }
}