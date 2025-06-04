/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.IS.marisqueria3.vista.IAdministradorC;

import com.IS.marisqueria3.model.MTablas.DatosTablaOrdenCompra;
import com.IS.marisqueria3.model.MTablas.TMPedidoIngredientes;
import com.IS.marisqueria3.services.ReporteService;
import com.IS.marisqueria3.util.OrdenCompraP;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author ambro
 */
public class PanelPedidoIngredientes extends javax.swing.JPanel {
    private ReporteService reporteS;
    private List<DatosTablaOrdenCompra> datosI;
    private TMPedidoIngredientes tablaIngrediente;
    
    /**
     * Creates new form PanelPedidoIngredientes
     */
    public PanelPedidoIngredientes() {
        reporteS= new ReporteService();
        initComponents();
        cargarTablaIngredientes();
    }
    
    // En la clase PanelPedidoIngredientes
public void cargarTablaIngredientes() {
    datosI = new ArrayList<>();
    List<OrdenCompraP> ingredientes = reporteS.generarReportePedido();
    
    for (OrdenCompraP i : ingredientes) {
        
        datosI.add(new DatosTablaOrdenCompra(i));
    }
    
    tablaIngrediente = new TMPedidoIngredientes(datosI);
    tablaOrdenCompra.setModel(tablaIngrediente);
    tablaOrdenCompra.setRowHeight(30);
    
    // Configurar spinner correctamente
    TableColumn spinnerColumn = tablaOrdenCompra.getColumnModel().getColumn(7);
    
    // Eliminamos el modelo compartido y usamos los constructores sin parámetros
    spinnerColumn.setCellEditor(new SpinnerEditor());
    spinnerColumn.setCellRenderer(new SpinnerRenderer());
    
    // Asegurar que los cambios se propaguen al modelo
    tablaOrdenCompra.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
    ajustarAnchosColumnas();
    
    
    revalidate();
    repaint();
}

// Clases internas corregidas
class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {
    private final JSpinner spinner;

    public SpinnerEditor() {  // Constructor sin parámetros
        spinner = new JSpinner();
        // Configurar modelo con valor inicial 0, mínimo 0, sin máximo y paso de 1
        spinner.setModel(new SpinnerNumberModel(0, 0, null, 1));
        
        // Guardar cambios inmediatamente al cambiar valor
        spinner.addChangeListener(e -> stopCellEditing());
        
        // Personalizar el editor para que sea más fácil de usar
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "#");
        spinner.setEditor(editor);
    }

    @Override
    public Object getCellEditorValue() {
        // Devolver el valor numérico, no el componente
        return spinner.getValue();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, 
            boolean isSelected, int row, int column) {
        // Establecer el valor inicial del spinner con el valor actual de la celda
        spinner.setValue(value != null ? value : 0);
        return spinner;
    }
}

class SpinnerRenderer extends JSpinner implements TableCellRenderer {
    public SpinnerRenderer() {  // Constructor sin parámetros
        super(new SpinnerNumberModel(0, 0, null, 1));
        setEnabled(false);  // Solo para visualización
        setBorder(BorderFactory.createEmptyBorder());
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        // Establecer el valor a mostrar
        setValue(value != null ? value : 0);
        
        // Estilos de selección
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }
        
        return this;
    }
}
    
   
     private void ajustarAnchosColumnas() {
        TableColumnModel columnModel = tablaOrdenCompra.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150); // Nombre
        columnModel.getColumn(1).setPreferredWidth(100); // Stock mínimo
        columnModel.getColumn(2).setPreferredWidth(100); // Stock actual
        columnModel.getColumn(3).setPreferredWidth(80);  // Unidad M
        columnModel.getColumn(4).setPreferredWidth(120); // Precio x Unidad
        columnModel.getColumn(5).setPreferredWidth(150); // Ingresar/Merma
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaOrdenCompra = new javax.swing.JTable();

        jButton3.setText("Descargar PDF");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Crear Pedido de Compra");

        tablaOrdenCompra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaOrdenCompra);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(340, 340, 340)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 53, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 736, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(337, 337, 337)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel5)
                .addGap(45, 45, 45)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        String nombre= "Orden de reabastecimiento Marisqueria \" La Pesca Milagrosa\" ";

        //obtener una lista de todos los producto nombre, proveedor, no.proveedor, direccion, stock actual, stockMinimo, cantidadSolicitar(cargar por defecto
            //con la cantidad para acompletar el minimo, un spiner para ir modificando al gusto. Todo esto con un MTabla, con DatosIngredienteOrdenPedido que se cargara
            //a la vista previa para editar y depues se cargara para imprimir en formato PDF.

    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaOrdenCompra;
    // End of variables declaration//GEN-END:variables
}
