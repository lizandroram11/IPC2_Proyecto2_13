package com.ipc2dic_proyecto2_pareja13.model;

import java.util.ArrayList;
import java.util.List;

public class Centro {
    private String id;
    private String nombre;
    private String ciudad;
    private int capacidad;
    private List<Paquete> paquetes;
    private List<Mensajero> mensajeros;

    public Centro(String id, String nombre, String ciudad, int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.capacidad = capacidad;
        this.paquetes = new ArrayList<>();
        this.mensajeros = new ArrayList<>();
    }

    // Métodos de negocio
    public boolean tieneCapacidadDisponible() {
        return paquetes.size() < capacidad;
    }

    public void agregarPaquete(Paquete paquete) {
        if (tieneCapacidadDisponible()) {
            paquetes.add(paquete);
        } else {
            throw new IllegalStateException("Capacidad máxima alcanzada en el centro " + nombre);
        }
    }

    public void agregarMensajero(Mensajero mensajero) {
        mensajeros.add(mensajero);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }
    public int getCapacidad() {
        return capacidad;
    }
    public List<Paquete> getPaquetes() {
        return paquetes;
    }
    public List<Mensajero> getMensajeros() {
        return mensajeros;
    }
}