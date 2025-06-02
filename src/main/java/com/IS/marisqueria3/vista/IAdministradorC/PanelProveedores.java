/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.IS.marisqueria3.vista.IAdministradorC; 

import com.IS.marisqueria3.model.Proveedor;
import com.IS.marisqueria3.services.OrdenCompraService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PanelProveedores extends javax.swing.JPanel {

    private List<Proveedor> usuarios = new ArrayList<>();
    private OrdenCompraService ordenS;
    private Map<String, Proveedor> usuarioM = new HashMap<>();



    public PanelProveedores() {
        ordenS = new OrdenCompraService();
        initComponents();
        cargarUsuarios();
    }

    public void limpiarEntradas() {
        txtDireccion.setText("");
        txtCorreo.setText("");
        txtNombre.setText("");
        txtNoTelefono.setText("");
    }

    public void cargarUsuarios() {
        usuarios = ordenS.listarProveedores();
        usuarioM.clear();
        DefaultTableModel model = (DefaultTableModel) tbUsuarios.getModel();
        model.setRowCount(0);
        for (Proveedor u : usuarios) {
            model.addRow(new Object[]{u.getNombre(), u.getDireccion(), u.getEmail(), u.getTelefono()});
            usuarioM.put(u.getNombre(), u);
        }
    }

    private void cargarDatosTabla() {
        int filaSeleccionada = tbUsuarios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            DefaultTableModel model = (DefaultTableModel) tbUsuarios.getModel();
            String nombre = (String) model.getValueAt(filaSeleccionada, 0);
            String direccion = (String) model.getValueAt(filaSeleccionada, 1);
            String correo = (String) model.getValueAt(filaSeleccionada, 2);
            String telefono = (String) model.getValueAt(filaSeleccionada, 3);

            txtNombre.setText(nombre);
            txtDireccion.setText(direccion);
            txtCorreo.setText(correo);
            txtNoTelefono.setText(telefono);
        }
    }

    private void crearUsuario() {
        String nombre = txtNombre.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String correo = txtCorreo.getText().trim();
        String telefono = txtNoTelefono.getText().trim();

        if (nombre.isEmpty() || direccion.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar llenos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Proveedor u = new Proveedor();
        u.setNombre(nombre);
        u.setDireccion(direccion);
        u.setEmail(correo);
        u.setTelefono(telefono);

        try {
            ordenS.crearProveedor(u);
            JOptionPane.showMessageDialog(this, "Proveedor creado correctamente.");
            limpiarEntradas();
            cargarUsuarios();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al crear proveedor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminarUsuario() {
        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un nombre.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Proveedor u = usuarioM.get(nombre);
        if (u == null) {
            JOptionPane.showMessageDialog(this, "Proveedor no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            ordenS.eliminarProveedor(u.getIdProveedor()); // Asegúrate que es getIdProveedor() y no getIdUsuario()
            JOptionPane.showMessageDialog(this, "Proveedor eliminado correctamente.");
            limpiarEntradas();
            cargarUsuarios();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar proveedor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void editarUsuario() {
        int filaSeleccionada = tbUsuarios.getSelectedRow();

        if (filaSeleccionada >= 0) {
            Proveedor usuarioSeleccionado = usuarios.get(filaSeleccionada);

            usuarioSeleccionado.setNombre(txtNombre.getText());
            usuarioSeleccionado.setDireccion(txtDireccion.getText());
            usuarioSeleccionado.setEmail(txtCorreo.getText());
            usuarioSeleccionado.setTelefono(txtNoTelefono.getText());

            try {
                ordenS.editarProveedor(usuarioSeleccionado);
                JOptionPane.showMessageDialog(this, "Proveedor actualizado correctamente.");
            } catch (Exception ex) {
                Logger.getLogger(PanelProveedores.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Error al actualizar proveedor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            cargarUsuarios();
            limpiarEntradas();
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un proveedor para actualizar", "Advertencia", JOptionPane.WARNING_MESSAGE);
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
        txtDireccion = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtNoTelefono = new javax.swing.JTextField();

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Registro de Proveedores");

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
                "Nombre ", "Direccion", "Correo", "Telefono"
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

        jLabel14.setText("Nombre:");

        jLabel15.setText("Direccion:");

        jLabel16.setText("Correo Electronico:");

        jLabel17.setText("No. telefono:");

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
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtCorreo, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtDireccion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                        .addComponent(txtNoTelefono, javax.swing.GroupLayout.Alignment.LEADING)))
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
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtNoTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(326, 326, 326)
                .addComponent(jLabel5)
                .addContainerGap(333, Short.MAX_VALUE))
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
                    .addContainerGap(47, Short.MAX_VALUE)))
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
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tbUsuarios;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtNoTelefono;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
