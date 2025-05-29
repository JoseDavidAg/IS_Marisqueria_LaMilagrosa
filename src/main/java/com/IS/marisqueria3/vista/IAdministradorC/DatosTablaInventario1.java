/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.vista.IAdministradorC;

import com.IS.marisqueria3.model.Ingrediente;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author ambro
 */
public class DatosTablaInventario1 {
    
    private Ingrediente t;
    private int stockMinimo;
    private int stockDisponible;
    private String unidadMedida;
    private JSpinner sp;
    

    public DatosTablaInventario1(Ingrediente t) {
        this.t = t;
        stockMinimo= t.getStockMinimo();
        stockDisponible=t.getStockDisponible();
        unidadMedida=t.getUnidadMedida();
        this.sp = new JSpinner(new SpinnerNumberModel(0, -100, 1000, 1));
     
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
        return sp;
    }

    public void setSpinner(JSpinner sp) {
        this.sp = sp;
    }
    
    public int getCantidad() {
        Object val = sp.getValue();
        if (val instanceof Integer) {
            return (Integer) val;
        }
        return 0;
    }

 
}


