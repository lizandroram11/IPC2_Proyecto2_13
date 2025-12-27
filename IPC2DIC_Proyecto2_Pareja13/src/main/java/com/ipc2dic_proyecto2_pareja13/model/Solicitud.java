package com.ipc2dic_proyecto2_pareja13.model;

public class Solicitud implements Comparable<Solicitud> {
    private String id;
    private TipoSolicitud tipo;
    private String paqueteId;
    private int prioridad;
    private EstadoSolicitud estado;

    public Solicitud(String id, TipoSolicitud tipo, String paqueteId, int prioridad) {
        this.id = id;
        this.tipo = tipo;
        this.paqueteId = paqueteId;
        this.prioridad = prioridad;
        this.estado = EstadoSolicitud.PENDIENTE;
    }

    // Métodos de negocio
    public void marcarAtendida() {
        estado = EstadoSolicitud.ATENDIDA;
    }

    @Override
    public int compareTo(Solicitud otra) {
        return Integer.compare(otra.prioridad, this.prioridad); // mayor prioridad primero
    }

    // Getters
    public String getId() {
        return id;
    }
    public TipoSolicitud getTipo() {
        return tipo;
    }
    public String getPaqueteId() {
        return paqueteId;
    }
    public int getPrioridad() {
        return prioridad;
    }
    public EstadoSolicitud getEstado() {
        return estado;
    }
}