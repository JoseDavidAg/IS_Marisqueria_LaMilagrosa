/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.vista;

/**
 *
 * @author ambro
 */


import com.IS.marisqueria3.controller.exceptions.services.ClienteMesaService;
import com.IS.marisqueria3.controller.exceptions.services.PedidoService;
import com.IS.marisqueria3.model.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

public class PanelPedidoIM extends JPanel {
    private final PedidoService pedidoS;
    private final ClienteMesaService clienteS;
    private final DefaultListModel<ItemPedido> listModel;
    private JTextField txtCliente;
    private JList<ItemPedido> listaItems;
    private JLabel lblTotal;
    private PedidoListener listener;
    private int mesaActual;

    public interface PedidoListener {
        void onPedidoConfirmado(Pedido pedido, int mesa);
        void onActualizarEstadoMesa(int mesa, String estado, double total);
    }

    public PanelPedidoIM() {
        clienteS= new ClienteMesaService();
        this.pedidoS = new PedidoService();
        this.listModel = new DefaultListModel<>();
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new TitledBorder("Detalles del Pedido"));

        // Panel Cliente
        JPanel panelCliente = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCliente.add(new JLabel("Cliente:"));
        txtCliente = new JTextField(20);
        panelCliente.add(txtCliente);

        // Lista de Items con renderizado personalizado
        listaItems = new JList<>(listModel);
        listaItems.setCellRenderer(new ItemPedidoRenderer());

        // Panel Total
        JPanel panelTotal = new JPanel();
        lblTotal = new JLabel("Total: $0.00");
        panelTotal.add(lblTotal);

        // Botón Confirmar
        JButton btnConfirmar = new JButton("Confirmar Pedido");
        btnConfirmar.addActionListener(e -> confirmarPedido());

        // Ensamblaje
        add(panelCliente, BorderLayout.NORTH);
        add(new JScrollPane(listaItems), BorderLayout.CENTER);
        add(panelTotal, BorderLayout.SOUTH);
        add(btnConfirmar, BorderLayout.EAST);
    }

    public void agregarItem(Producto producto) {
        Optional<ItemPedido> itemExistente = Collections.list(listModel.elements()).stream()
            .filter(item -> item.getProducto().getIdPlatillo() == producto.getIdPlatillo())
            .findFirst();

       if (itemExistente.isPresent()) {
        ItemPedido item = itemExistente.get();
        item.setCantidad(item.getCantidad() + 1);
        int index = listModel.indexOf(item);
        listModel.set(index, item); // Notifica cambios automáticamente
    } else {
            ItemPedido nuevoItem = new ItemPedido();
            nuevoItem.setProducto(producto);
            nuevoItem.setCantidad(1);
            listModel.addElement(nuevoItem);
        }
          actualizarTotal();
    }


    private void actualizarTotal() {
        BigDecimal total = Collections.list(listModel.elements()).stream()
            .map(item -> item.getProducto().getPrecioVenta().multiply(BigDecimal.valueOf(item.getCantidad())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        lblTotal.setText(String.format("Total: $%.2f", total));
    }

    private void confirmarPedido() {
        if (txtCliente.getText().isEmpty() || listModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete cliente y agregue productos");
            return;
        }

        // Crear y persistir cliente
        Cliente cliente = new Cliente();
        cliente.setNombre(txtCliente.getText().trim());
        clienteS.crearCliente(cliente); // Genera el ID automáticamente

        // Crear pedido con cliente persistido
        Pedido pedido = new Pedido();
        pedido.setClienteId(cliente); // Usar el objeto completo, no solo el ID
        pedido.setFechaGeneracion(new Date());
        pedido.setEstado("Pendiente");
    
        // Asignar items al pedido
        List<ItemPedido> items = Collections.list(listModel.elements());
        items.forEach(item -> item.setPedido(pedido)); // Establecer relación bidireccional
        pedido.setItemPedidoList(items);
    
        if (listener != null) {
            listener.onPedidoConfirmado(pedido, mesaActual);
            listener.onActualizarEstadoMesa(mesaActual, "Ocupado - " + txtCliente.getText(), 
                Double.parseDouble(lblTotal.getText().replaceAll("[^\\d.]", "")));
        }

        resetearFormulario();
    }

    public void setMesaActual(int mesa) {
        this.mesaActual = mesa;
    }

    public void setPedidoListener(PedidoListener listener) {
        this.listener = listener;
    }

    private void resetearFormulario() {
        listModel.clear();
        txtCliente.setText("");
        actualizarTotal();
    }

    // Renderizador personalizado para los items
    private static class ItemPedidoRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                      boolean isSelected, boolean cellHasFocus) {
                ItemPedido item = (ItemPedido) value;
                String text = String.format("%s x%d - $%.2f", 
            item.getProducto().getNombre(), 
            item.getCantidad(),
            item.getProducto().getPrecioVenta().multiply(BigDecimal.valueOf(item.getCantidad())));
            return null;
        }
    }
}