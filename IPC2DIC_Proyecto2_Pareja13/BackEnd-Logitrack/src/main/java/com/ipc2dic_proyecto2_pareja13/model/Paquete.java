package com.ipc2dic_proyecto2_pareja13.model;

public class Paquete {
    private String id;
    private String cliente;
    private double peso;
    private String destino;
    private EstadoPaquete estado;
    private String centroActual;

    public Paquete(String id, String cliente, double peso, String destino, String centroActual) {
        if (peso <= 0) throw new IllegalArgumentException("El peso debe ser mayor a cero");
        this.id = id;
        this.cliente = cliente;
        this.peso = peso;
        this.destino = destino;
        this.centroActual = centroActual;
        this.estado = EstadoPaquete.PENDIENTE;
    }

    // Métodos de negocio
    public void marcarEnTransito() {
        if (estado == EstadoPaquete.PENDIENTE) {
            estado = EstadoPaquete.EN_TRANSITO;
        }
    }

    public void marcarEntregado() {
        if (estado == EstadoPaquete.EN_TRANSITO) {
            estado = EstadoPaquete.ENTREGADO;
        }
    }

    // Getters
    public String getId() {

        return id;
    }
    public String getCliente() {

        return cliente;
    }
    public double getPeso() {

        return peso;
    }
    public String getDestino() {

        return destino;
    }
    public EstadoPaquete getEstado() {

        return estado;
    }
    public String getCentroActual() {

        return centroActual;
    }
}
