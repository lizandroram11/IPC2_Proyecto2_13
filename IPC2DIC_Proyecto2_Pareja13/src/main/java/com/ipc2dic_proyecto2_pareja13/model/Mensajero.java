package com.ipc2dic_proyecto2_pareja13.model;

public class Mensajero {
    private String id;
    private String nombre;
    private int capacidad;
    private String centroActual;
    private EstadoMensajero estado;

    // Constructor vacío (necesario para @RequestBody)
    public Mensajero() {}

    public Mensajero(String id, String nombre, int capacidad, String centroActual) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.centroActual = centroActual;
        this.estado = EstadoMensajero.DISPONIBLE;
    }

    // Métodos de negocio
    public boolean estaDisponible() {
        return estado == EstadoMensajero.DISPONIBLE;
    }

    public void asignarEnvio() {
        if (estaDisponible()) {
            estado = EstadoMensajero.EN_TRANSITO;
        } else {
            throw new IllegalStateException("El mensajero ya está en tránsito");
        }
    }

    public void marcarDisponible() {
        estado = EstadoMensajero.DISPONIBLE;
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public int getCapacidad() { return capacidad; }
    public String getCentroActual() { return centroActual; }
    public EstadoMensajero getEstado() { return estado; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public void setCentroActual(String centroActual) { this.centroActual = centroActual; }
    public void setEstado(EstadoMensajero estado) { this.estado = estado; }
}