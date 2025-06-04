/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.model.MTablas;

import com.IS.marisqueria3.services.OrdenCompraService;
import com.IS.marisqueria3.model.Ingrediente;
import com.IS.marisqueria3.services.ProductoService;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;

/**
 *
 * @author ambro
 */
public class TMInventario extends AbstractTableModel{
    private List<DatosTablaInventario> datosIngredientes;
    private OrdenCompraService ordenS;
    private ProductoService productoS;
    private String encabezado[]={"Nombre","Stock mínimo","Stock actual","Unidad M","Precio x Unidad","Ingresar/Merma"};
    private Class clasesC[]={String.class,Integer.class,Integer.class,String.class,Float.class,Integer.class};
    
    public TMInventario(List<DatosTablaInventario> mtc){
        datosIngredientes=mtc;
        ordenS= new OrdenCompraService();
        productoS= new ProductoService();
    }
    @Override
    public boolean isCellEditable(int r, int c){
        if(c==0)return false;
        if(c==1)return false;
        if(c==2)return false;
        if(c==3)return false;
        if (c==4) return false;
        return c==5;


    }
    
    @Override
    public String getColumnName(int c){
        return encabezado[c];
    }
    
    @Override
    public Class getColumnClass(int c){
        return clasesC[c];
    }
    
    @Override
    public int getRowCount() {
        return datosIngredientes.size();
    }

    @Override
    public int getColumnCount() {
        return encabezado.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        switch(column){
            case 0: return datosIngredientes.get(row).getT().getNombre();
            case 1: return datosIngredientes.get(row).getStockMinimo();
            case 2: return datosIngredientes.get(row).getStockDisponible();
            case 3: return datosIngredientes.get(row).getUnidadMedida();
            case 4: return datosIngredientes.get(row).getPrecio();
            case 5: return datosIngredientes.get(row).getSpinner();
            default: return null;
        }
        
    }
    
    @Override    
    public void setValueAt(Object dato, int r, int c) {
        DatosTablaInventario datoFila = datosIngredientes.get(r);
        Ingrediente ing = datoFila.getT();
        try {
            switch (c) {
                case 0:
                    ing.setNombre(dato.toString());
                    break;
                case 1:
                    ing.setStockMinimo(Integer.valueOf(dato.toString()));
                    datoFila.setStockMinimo(ing.getStockMinimo());
                    break;
                case 2:
                    ing.setStockDisponible(Integer.valueOf(dato.toString()));
                    datoFila.setStockDisponible(ing.getStockDisponible());
                    break;
                case 3:
                    ing.setUnidadMedida(dato.toString());
                    datoFila.setUnidadMedida(ing.getUnidadMedida());
                    break;
                case 4:
                    ing.setPrecioUnitario(Float.parseFloat(dato.toString()));
                    break;
                case 5:
                    try {

                // Manejar cambios en el spinner
                JSpinner spinner = (JSpinner) dato;
                int cambio = (Integer) spinner.getValue();
                
                // Calcular nuevo stock
                int nuevoStock = datoFila.getStockDisponible() + cambio;
                
                // Actualizar datos
                datoFila.setStockDisponible(nuevoStock);
                ing.setStockDisponible(nuevoStock);
                
                // Actualizar base de datos
                productoS.actualizarStockIngrediente(ing.getIngredienteId(), nuevoStock);
                
                // Resetear spinner
                spinner.setValue(0);
                
                // Notificar cambios
                fireTableCellUpdated(r, 2);  // Actualizar columna de stock
            
            // ... manejo de otras columnas ...
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
                    break;
                
                    // Si quieres establecer el valor del spinner desde la tabla:
            }
        fireTableCellUpdated(r, c); // Notificar que la celda se actualizó
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Valor numérico inválido");
    }
    }
    
    
    
    
}

