/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.IS.marisqueria3.vista.IAdministradorC; 

import com.IS.marisqueria3.model.Ingrediente;
import com.IS.marisqueria3.model.MTablas.TMInventario;
import com.IS.marisqueria3.model.Usuario;
import com.IS.marisqueria3.services.ProductoService;
import com.IS.marisqueria3.services.UsuarioService;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class PanelUsuarios extends javax.swing.JPanel {
      
    List<Usuario> usuarios= new ArrayList<>();
    UsuarioService usuarioS;
    Map<String,Usuario> usuarioM = new HashMap<>();
    public PanelUsuarios() {
       
        usuarioS = new UsuarioService();
        initComponents();
        cargarUsuarios();
    }

    public void limpiarEntradas(){
        txtApellido.setText("");
        txtContraseña.setText("");
        txtNombre.setText("");
        cmbRol.setSelectedIndex(-1);
    }
    
    public void cargarUsuarios(){
        usuarios= usuarioS.traerTodosUsuarios();
        DefaultTableModel model= (DefaultTableModel) tbUsuarios.getModel();
        model.setRowCount(0);
        for(Usuario u: usuarios){
            model.addRow(new Object[]{ u.getNombreUsuario(), u.getApellidosUsuario(), u.getContraseña(), u.getRol() });
            usuarioM.put(u.getNombreUsuario(), u);
        }
    }
    
    private void cargarDatosTabla() {
        int filaSeleccionada = tbUsuarios.getSelectedRow();

        if (filaSeleccionada >= 0) {
            DefaultTableModel model = (DefaultTableModel) tbUsuarios.getModel();


            String nombre = (String) model.getValueAt(filaSeleccionada, 0);
            String apellido = (String) model.getValueAt(filaSeleccionada, 1);
            String contraseña = (String) model.getValueAt(filaSeleccionada, 2);
            String rol = (String) model.getValueAt(filaSeleccionada, 3);

            // Cargar datos en los campos
            txtNombre.setText(nombre);
            txtApellido.setText(apellido);
            txtContraseña.setText(contraseña);
            cmbRol.setSelectedItem(rol);
 
        }
    }
    
    private void crearUsuario() {

        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String contraseña = txtContraseña.getText().trim();
        String rol = (String) cmbRol.getSelectedItem();


        if (nombre.isEmpty() || apellido.isEmpty() || contraseña.isEmpty() || rol == null) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }


        Usuario u = new Usuario();
        u.setNombreUsuario(nombre);
        u.setApellidosUsuario(apellido);
        u.setContraseña(contraseña);
        u.setRol(rol);

        try {
            usuarioS.crearUsuario(u);
            JOptionPane.showMessageDialog(this, "Usuario creado correctamente.");
            limpiarEntradas();
            cargarUsuarios(); 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al crear usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void eliminarUsuario(){
        String nombre = txtNombre.getText().trim();
        


        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario u= usuarioM.get(nombre);
        
        try {
            usuarioS.eliminarUsuario(u.getIdUsuario());
            JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.");
            limpiarEntradas();
            cargarUsuarios(); 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void editarUsuario(){
        int filaSeleccionada = tbUsuarios.getSelectedRow();
    
        if (filaSeleccionada >= 0) {

            Usuario usuarioSeleccionado = usuarios.get(filaSeleccionada);

            usuarioSeleccionado.setNombreUsuario(txtNombre.getText());
            usuarioSeleccionado.setApellidosUsuario(txtApellido.getText());
            usuarioSeleccionado.setContraseña(txtContraseña.getText());
            usuarioSeleccionado.setRol((String) cmbRol.getSelectedItem());

            try {
                // Llamar al servicio para actualizar en la base de datos
                usuarioS.editarUsuario(usuarioSeleccionado);
            } catch (Exception ex) {
                Logger.getLogger(PanelUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            }

            cargarUsuarios();
            limpiarEntradas();

            JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente");
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario para actualizar", "Error", JOptionPane.WARNING_MESSAGE);
        }
       
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        btnAUsuario = new javax.swing.JButton();
        btnEUsuario = new javax.swing.JButton();
        btnAcUsuario = new javax.swing.JButton();
        btnLEntradas = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbUsuarios = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtContraseña = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        cmbRol = new javax.swing.JComboBox<>();

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Ingreso / Merma de Inventario");

        btnAUsuario.setText("Agregar Usuario");
        btnAUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAUsuarioMouseClicked(evt);
            }
        });
        btnAUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAUsuarioActionPerformed(evt);
            }
        });

        btnEUsuario.setText("Eliminar Usuario");
        btnEUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEUsuarioMouseClicked(evt);
            }
        });
        btnEUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEUsuarioActionPerformed(evt);
            }
        });

        btnAcUsuario.setText("Actualizar Usuario");
        btnAcUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAcUsuarioMouseClicked(evt);
            }
        });
        btnAcUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcUsuarioActionPerformed(evt);
            }
        });

        btnLEntradas.setText("Limpiar entradas");
        btnLEntradas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLEntradasMouseClicked(evt);
            }
        });
        btnLEntradas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLEntradasActionPerformed(evt);
            }
        });

        tbUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre ", "Apellido ", "Contraseña", "Rol"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tbUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbUsuariosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbUsuarios);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setForeground(new java.awt.Color(51, 204, 255));

        jLabel14.setText("Nombre de usuario");

        jLabel15.setText("Apellidos:");

        jLabel16.setText("Contraseña:");

        jLabel17.setText("Rol:");

        cmbRol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mesero", "Cocina", "Administrador" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(326, 326, 326)
                .addComponent(jLabel5)
                .addContainerGap(290, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(72, 72, 72)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(btnAUsuario)
                            .addGap(61, 61, 61)
                            .addComponent(btnEUsuario)
                            .addGap(78, 78, 78)
                            .addComponent(btnAcUsuario)
                            .addGap(50, 50, 50)
                            .addComponent(btnLEntradas)
                            .addGap(15, 15, 15))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(52, 52, 52)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(38, 38, 38)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 643, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(72, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel5)
                .addContainerGap(640, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(50, 50, 50)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(41, 41, 41)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAUsuario)
                        .addComponent(btnEUsuario)
                        .addComponent(btnAcUsuario)
                        .addComponent(btnLEntradas))
                    .addGap(83, 83, 83)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(50, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAUsuarioMouseClicked
        crearUsuario();
    }//GEN-LAST:event_btnAUsuarioMouseClicked

    private void btnAUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAUsuarioActionPerformed
       
        
        
    }//GEN-LAST:event_btnAUsuarioActionPerformed

    private void btnEUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEUsuarioMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEUsuarioMouseClicked

    private void btnEUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEUsuarioActionPerformed
        eliminarUsuario();
    }//GEN-LAST:event_btnEUsuarioActionPerformed

    private void btnAcUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAcUsuarioMouseClicked
        editarUsuario();
                                 
    }//GEN-LAST:event_btnAcUsuarioMouseClicked

    private void btnLEntradasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLEntradasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLEntradasMouseClicked

    private void btnLEntradasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLEntradasActionPerformed
        limpiarEntradas();
    }//GEN-LAST:event_btnLEntradasActionPerformed

    private void tbUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbUsuariosMouseClicked
       cargarDatosTabla();
    }//GEN-LAST:event_tbUsuariosMouseClicked

    private void btnAcUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAcUsuarioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAUsuario;
    private javax.swing.JButton btnAcUsuario;
    private javax.swing.JButton btnEUsuario;
    private javax.swing.JButton btnLEntradas;
    private javax.swing.JComboBox<String> cmbRol;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tbUsuarios;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtContraseña;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
