/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.model.MTablas;

import com.IS.marisqueria3.model.Ingrediente;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author ambro
 */
public class DatosTablaInventario {
    
    private Ingrediente t;
    private int stockMinimo;
    private int stockDisponible;
    private String unidadMedida;
    private float precio;
    private JSpinner spinner;
    

    public DatosTablaInventario(Ingrediente t) {
        this.t = t;
        stockMinimo= t.getStockMinimo();
        stockDisponible=t.getStockDisponible();
        unidadMedida=t.getUnidadMedida();
        precio= t.getPrecioUnitario();
        spinner = new JSpinner(new SpinnerNumberModel(0, -1000, 1000, 1));
     
    }
    
    public Ingrediente getT() {
        return t;
    }

    public void setT(Ingrediente t) {
        this.t = t;
    }

    public String getNombre(){
        return t.getNombre();
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
    
    
    public int getStockMinimo() {
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

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
    

    public JSpinner getSpinner() {
        return spinner;
    }
    
}