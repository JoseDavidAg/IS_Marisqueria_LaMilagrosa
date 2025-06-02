/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.model.MTablas;

/**
 *
 * @author ambro
 */

import com.IS.marisqueria3.services.OrdenCompraService;
import com.IS.marisqueria3.model.Ingrediente;
import com.IS.marisqueria3.model.Proveedor;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author ambro
 */
public class TMIngrediente extends AbstractTableModel{
    private List<Ingrediente> datosIngredientes;
    OrdenCompraService ordenS;
    String encabezado[]={"Nombre","Descripcion","$xUnidad","Stock mínimo","Stock disponible","U.Medida","Proveedor"};
    Class clasesC[]={ String.class,String.class,Float.class,Integer.class,Integer.class,String.class,String.class};
    
    public TMIngrediente(List<Ingrediente> mtc){
        datosIngredientes=mtc;
        ordenS= new OrdenCompraService();
    }
    @Override
    public boolean isCellEditable(int r, int c){
        if(c==0)return true;
        if(c==1)return true;
        if(c==2)return true;
        if(c==3)return true;
        if(c==4)return false;
        if(c==5)return false;
        if(c==6)return false;
        return false;
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
            case 0: return datosIngredientes.get(row).getNombre();
            case 1: return datosIngredientes.get(row).getDescripcion();
            case 2: return datosIngredientes.get(row).getPrecioUnitario();
            case 3: return datosIngredientes.get(row).getStockMinimo();
            case 4: return datosIngredientes.get(row).getStockDisponible();
            case 5: return datosIngredientes.get(row).getUnidadMedida();
            case 6: return (datosIngredientes.get(row).getIngredienteId()!=null)?datosIngredientes.get(row).getProveedorId().getNombre():"No hay";
            default: return null;
        }
    }
    
    @Override
    public void setValueAt(Object dato, int r, int c) {
        Ingrediente ing = datosIngredientes.get(r);
        try { 
            switch(c) {
                case 1: ing.setDescripcion((String) dato); break;
                case 2: ing.setPrecioUnitario(Float.parseFloat(dato.toString())); break;
                case 3: ing.setStockMinimo(Integer.valueOf(dato.toString())); break;
                case 4: ing.setStockDisponible(Integer.valueOf(dato.toString())); break;
                case 6: if(ing.getProveedorId()!=null){
                    ing.setProveedorId(ordenS.listarProveedoresNombre(dato.toString()));
                }else ing.setProveedorId(null);
                  
                    break;
            }
            fireTableCellUpdated(r, c); // Notificar actualización
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor numérico inválido");
        }
    }
    
    
}


