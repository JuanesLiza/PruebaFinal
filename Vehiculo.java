package com.ejemplo.lavado_autos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("vehiculo") // Mapea esta clase a la tabla "vehiculo" en la base de datos
public class Vehiculo {

    @Id // Indica que este campo es la clave primaria de la tabla
    private Long id;

    private String marca;
    private String placa;
    private String servicio;
    private double precio;

    // Getters y Setters (necesarios para acceder y modificar los atributos)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}