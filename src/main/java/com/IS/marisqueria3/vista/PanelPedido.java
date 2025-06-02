/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.vista;

/**
 *
 * @author ambro
 */

import com.IS.marisqueria3.services.*;
import com.IS.marisqueria3.model.ItemPedido;
import com.IS.marisqueria3.model.Pedido;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PanelPedido extends JPanel {
    public PanelPedido(Pedido pedido, ICocina cocina, PedidoService pedidoS) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            "Pedido #" + pedido.getNumeroPedido(),
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));

        setBackground(pedido.getEsUrgente() ? new Color(255, 230, 230) : Color.WHITE);

        JLabel lblInfo = new JLabel("👤 Cliente: " + pedido.getClienteId().getNombre()
            + " | 🚨 Urgente: " + (pedido.getEsUrgente() ? "Sí" : "No")
            + " | 🕒 Estado: " + pedido.getEstado());
        add(lblInfo);

        JCheckBox chkPreparando = new JCheckBox("🍳 Preparando");
        JCheckBox chkTerminado = new JCheckBox("✅ Terminado");

        if (pedido.getEstado().equals("preparando")) {
            chkPreparando.setSelected(true);
            chkPreparando.setEnabled(false);
        }

        chkPreparando.addActionListener(e -> {
            if (chkPreparando.isSelected()) {
                try {
                    pedido.setEstado("preparando");
                    pedidoS.actualizarPedido(pedido);
                } catch (Exception ex) {
                    Logger.getLogger(ICocina.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        chkTerminado.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Marcar el pedido como terminado?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                pedido.setEstado("terminado");
                try {
                    pedidoS.actualizarPedido(pedido);
                    cocina.refrescarListaPedidos();
                } catch (Exception ex) {
                    Logger.getLogger(ICocina.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                chkTerminado.setSelected(false);
            }
        });

        add(chkPreparando);
        add(chkTerminado);

        // Productos
        JPanel panelProductos = new JPanel();
        panelProductos.setLayout(new BoxLayout(panelProductos, BoxLayout.Y_AXIS));
        panelProductos.setBackground(new Color(245, 245, 245));
        panelProductos.setBorder(BorderFactory.createTitledBorder("📦 Productos"));

        for (ItemPedido item : pedido.getItemPedidoList()) {
            JLabel lblProducto = new JLabel("🍽 " + item.getIdProducto().getNombre() + " x" + item.getCantidad());
            panelProductos.add(lblProducto);
        }

        add(panelProductos);
    }
}

