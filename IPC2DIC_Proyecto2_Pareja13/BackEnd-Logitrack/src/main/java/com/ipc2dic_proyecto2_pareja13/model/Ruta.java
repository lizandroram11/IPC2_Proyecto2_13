package com.ipc2dic_proyecto2_pareja13.model;

public class Ruta {
    private String id;
    private String origen;   // ID del centro origen
    private String destino;  // ID del centro destino
    private double distancia;

    // Constructor vacío (necesario para @RequestBody)
    public Ruta() {}

    public Ruta(String id, String origen, String destino, double distancia) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
    }

    // Getters
    public String getId() {
        return id; }
    public String getOrigen() {
        return origen; }
    public String getDestino() {
        return destino; }
    public double getDistancia() {
        return distancia; }

    // Setters
    public void setId(String id) {
        this.id = id; }
    public void setOrigen(String origen) {
        this.origen = origen; }
    public void setDestino(String destino) {
        this.destino = destino; }
    public void setDistancia(double distancia) {
        this.distancia = distancia; }
}