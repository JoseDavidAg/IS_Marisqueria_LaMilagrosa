/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.model.MTablas;

import com.IS.marisqueria3.services.OrdenCompraService;
import com.IS.marisqueria3.services.ProductoService;
import com.IS.marisqueria3.util.OrdenCompraP;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ambro
 */
/**
 *
 * @author ambro
 * select p.nombre,p.telefono,p.email,i.nombre as "Nombre producto", i.descripcion, i.stock_minimo, i.stock_disponible,(stock_disponible-stock_minimo) as cantidad_pedir from ingrediente i
join proveedor p on p.id_proveedor=i.proveedor_id
order by cantidad_pedir ;
 */
public class TMPedidoIngredientes extends AbstractTableModel{
    private List<DatosTablaOrdenCompra> datosIngredientes;
    private OrdenCompraService ordenS;
    private ProductoService productoS;
    private String encabezado[]={"Nombre proveedor","Telefono","Email","Nombre producto","Producto descripcion","Stock mínimo","Stock Disponible","Incrementar/Disminuir","Unidad medida"};
    private Class clasesC[]={String.class,String.class,String.class,String.class,String.class,Integer.class,Integer.class,Integer.class,String.class};
    
    public TMPedidoIngredientes(List<DatosTablaOrdenCompra> mtc){
        datosIngredientes=mtc;
        ordenS= new OrdenCompraService();
        productoS= new ProductoService();
    }
    
      @Override
    public boolean isCellEditable(int r, int c) {
        // Solo la columna 7 (Incrementar/Disminuir) es editable
        return c == 7;
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
        DatosTablaOrdenCompra dato = datosIngredientes.get(row);
        return switch (column) {
            case 0 -> dato.getOrden().getNombreProveedor();
            case 1 -> dato.getOrden().getTelefono();
            case 2 -> dato.getOrden().getGmail();
            case 3 -> dato.getOrden().getNombreProducto();
            case 4 -> dato.getOrden().getDescripcion();
            case 5 -> dato.getOrden().getStockMinimo();
            case 6 -> dato.getOrden().getStockDisponible();
            case 7 -> dato.getOrden().getCantidadPedir();
            case 8 -> dato.getOrden().getUnidadMedida();
            default -> null;
        };
    }
    
    @Override    
    public void setValueAt(Object value, int row, int column) {
        if (column != 7) return; // Solo procesamos cambios en la columna 7

        DatosTablaOrdenCompra datoFila = datosIngredientes.get(row);
        OrdenCompraP ing = datoFila.getOrden();

        try {
            // Guardar directamente el valor del spinner (cantidad a pedir)
            int cantidad = (Integer) value;
            ing.setCantidadPedir(cantidad);

            // Notificar que la celda ha cambiado
            fireTableCellUpdated(row, column);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage());
            e.printStackTrace();
        }
    }  
    
}

