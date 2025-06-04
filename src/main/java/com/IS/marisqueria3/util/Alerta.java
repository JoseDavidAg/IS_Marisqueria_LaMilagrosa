/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.util;

import java.time.LocalDate;

/**
 *
 * @author ambro
 */
public class Alerta {
    private String nombreIngrediente;
    private int stockMinimo;
    private int stockDisponible;
    private LocalDate fechaGeneracion;

    public Alerta(String nombreIngrediente, Integer stockMinimo, Integer stockDisponible) {
        this.nombreIngrediente = nombreIngrediente;
        this.stockMinimo = stockMinimo;
        this.stockDisponible = stockDisponible;
        fechaGeneracion= LocalDate.now();
    }

    public String getMensajeStock() {
        return "Ingrediente: " + nombreIngrediente + " — Stock actual: " + stockDisponible + " (mínimo requerido: " + stockMinimo + ")";
    }

    public String getNombreIngrediente() {
        return nombreIngrediente;
    }

    public void setNombreIngrediente(String nombreIngrediente) {
        this.nombreIngrediente = nombreIngrediente;
    }

    public double getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(int stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDate fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    
    
}

