/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.IS.marisqueria3.vista.IAdministradorC; 

import com.IS.marisqueria3.model.Ingrediente;
import com.IS.marisqueria3.model.MTablas.TMInventario;
import com.IS.marisqueria3.services.ProductoService;
import com.IS.marisqueria3.services.OrdenCompraService;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class PanelInventario extends javax.swing.JPanel {
      
    TMInventario tablaIngrediente;
    ProductoService productoS;
    List<DatosTablaInventario1> datosI;
    
    public PanelInventario() {
        productoS = new ProductoService();
        initComponents();
        cargarIngredientesEditar();
    }
    
    public void cargarIngredientesEditar() {
        datosI = new ArrayList<>();
        List<Ingrediente> ingredientes = productoS.listarIngredientes();
        
        for (Ingrediente i : ingredientes) {
            datosI.add(new DatosTablaInventario1(i));
        }
        
        tablaIngrediente = new TMInventario(datosI);
        ingredientesTabla2.setModel(tablaIngrediente);
        
        // Configurar spinner
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, -1000, 1000, 1);
        TableColumn spinnerColumn = ingredientesTabla2.getColumnModel().getColumn(5);
        spinnerColumn.setCellEditor(new SpinnerEditor(spinnerModel));
        spinnerColumn.setCellRenderer(new SpinnerRenderer(spinnerModel));
        
        // Ajustar anchos de columnas
        ajustarAnchosColumnas();
        
        // Actualizar UI
        ingredientesTabla2.revalidate();
        ingredientesTabla2.repaint();
    }
    
    private void ajustarAnchosColumnas() {
        TableColumnModel columnModel = ingredientesTabla2.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150); // Nombre
        columnModel.getColumn(1).setPreferredWidth(100); // Stock mínimo
        columnModel.getColumn(2).setPreferredWidth(100); // Stock actual
        columnModel.getColumn(3).setPreferredWidth(80);  // Unidad M
        columnModel.getColumn(4).setPreferredWidth(120); // Precio x Unidad
        columnModel.getColumn(5).setPreferredWidth(150); // Ingresar/Merma
    }
    
    class SpinnerEditor extends AbstractCellEditor implements TableCellEditor {
        private final JSpinner spinner;

        public SpinnerEditor(SpinnerModel model) {
            spinner = new JSpinner(model);
            
            // Actualizar stock cuando se cambia el valor
            spinner.addChangeListener(e -> {
                int row = ingredientesTabla2.getEditingRow();
                if (row >= 0) {
                    // Obtener el nuevo valor del spinner
                    int cambio = (Integer) spinner.getValue();
                    
                    // Obtener los datos de la fila
                    DatosTablaInventario1 datoFila = datosI.get(row);
                    
                    // Calcular nuevo stock
                    int nuevoStock = datoFila.getStockDisponible() + cambio;
                    
                    // Actualizar los datos
                    datoFila.setStockDisponible(nuevoStock);
                    datoFila.getT().setStockDisponible(nuevoStock);
                    
                    // Actualizar la tabla
                    tablaIngrediente.fireTableCellUpdated(row, 2); // Columna de stock actual
                    
                    // Resetear el spinner a 0 después del cambio
                    spinner.setValue(0);
                    
                    // Actualizar en la base de datos
                    actualizarStockEnBaseDatos(datoFila.getT(), nuevoStock);
                }
            });
        }

        @Override
        public Object getCellEditorValue() {
            return spinner.getValue();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, 
                boolean isSelected, int row, int column) {
            spinner.setValue(value != null ? value : 0);
            return spinner;
        }
        
        private void actualizarStockEnBaseDatos(Ingrediente ingrediente, int nuevoStock) {
            try {
                // Actualizar el stock en la base de datos
                productoS.actualizarStockIngrediente(ingrediente.getIngredienteId(), nuevoStock);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(PanelInventario.this, 
                    "Error al actualizar stock: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class SpinnerRenderer extends JSpinner implements TableCellRenderer {
        public SpinnerRenderer(SpinnerModel model) {
            super(model);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setValue(value != null ? value : 0);
            
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
    
    // ... El resto de tu código (TMInventario, etc) ...



    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton4 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        ingredientesTabla2 = new javax.swing.JTable();

        jButton4.setText("Guardar");

        jLabel5.setText("Ingreso / Merma de Inventario");

        ingredientesTabla2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(ingredientesTabla2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 643, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(355, 355, 355)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(418, 418, 418)
                        .addComponent(jButton4)))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel5)
                .addGap(109, 109, 109)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(jButton4)
                .addContainerGap(184, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ingredientesTabla2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane8;
    // End of variables declaration//GEN-END:variables
}
