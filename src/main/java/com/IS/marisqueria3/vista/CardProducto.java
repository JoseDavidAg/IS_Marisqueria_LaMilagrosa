/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.vista;

import com.IS.marisqueria3.services.*;
import com.IS.marisqueria3.model.Producto;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author ambro
 */
public class CardProducto extends JPanel {
    private Producto producto;
    private JButton btnAgregar;
    PedidoService pedidoS = new PedidoService();
    JLabel lblNombre;
    JLabel lblPrecio;
    public CardProducto(Producto producto) {
        this.producto = producto;
        setLayout(null);
        setPreferredSize(new Dimension(240, 160));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
         
         
         
        lblNombre = new JLabel(producto.getNombre(), SwingConstants.CENTER);
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblNombre.setBounds(10, 10, 200, 25);
        add(lblNombre);

        lblPrecio = new JLabel(String.format("$%.2f", producto.getPrecioVenta()), SwingConstants.CENTER);
        lblPrecio.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblPrecio.setForeground(new Color(60, 120, 60));
        lblPrecio.setBounds(10, 40, 200, 20);
        add(lblPrecio);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(60, 110, 100, 30);
        btnAgregar.setBackground(new Color(0, 153, 102));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);
        add(btnAgregar);

        btnAgregar.addActionListener(e -> {
            JOptionPane.showMessageDialog(SwingUtilities.getRoot(this), // Usar el root pane
                producto.getNombre() + " agregado a la comanda");
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!btnAgregar.getBounds().contains(e.getPoint())) {
                    mostrarDetallesProducto(pedidoS);
                }
            }
        });
        btnAgregar.addActionListener(e -> {
            IMesero parent = (IMesero) SwingUtilities.getWindowAncestor(this);
            parent.getPanelPedido().agregarItem(producto);

            // Llama al método público de actualización
            parent.actualizarPanelPedido(); 
        });
        actualizarDisponibilidad();
    }

    public void setProducto(Producto nuevoProducto) {
        this.producto = nuevoProducto;
        lblNombre.setText(nuevoProducto.getNombre());
        lblPrecio.setText(String.format("$%.2f", nuevoProducto.getPrecioVenta()));
        actualizarDisponibilidad();
    }
    
    public Producto getProducto(){
        return producto;
    }
    
    public boolean actualizarDisponibilidad() {
        
        boolean disponible = pedidoS.productoDisponible(producto.getIdPlatillo());
        if (btnAgregar.isEnabled() != disponible) {
            btnAgregar.setEnabled(disponible);
            btnAgregar.setText(disponible ? "Agregar" : "No disponible");
            repaint(); // solo si cambia
        }
        return disponible;
    }


    private void mostrarDetallesProducto(PedidoService pedidoS) {
        JTextArea detalle = new JTextArea();
        String ingre = pedidoS.traerIngredientes(producto.getIdPlatillo());
        detalle.setText("🧾 Descripción:\n" + producto.getDescripcion()
                + "\n\n🍤 Ingredientes:\n" + String.join("\n", ingre));
        detalle.setEditable(false);
        detalle.setLineWrap(true);
        detalle.setWrapStyleWord(true);
        detalle.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(detalle);
        scroll.setPreferredSize(new Dimension(350, 250));

        JOptionPane.showMessageDialog(this, scroll, producto.getNombre(), JOptionPane.INFORMATION_MESSAGE);
    }
    
}

