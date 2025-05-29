/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.vista;

/**
 *
 * @author ambro
 */


import com.IS.marisqueria3.services.ClienteMesaService;
import com.IS.marisqueria3.services.PedidoService;
import com.IS.marisqueria3.model.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.*; 
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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
        setPreferredSize(new Dimension(700, 300));
    }

    private void initUI() {
    setLayout(new BorderLayout(10, 10));
    setBorder(new TitledBorder("Detalles del Pedido"));

    // Panel Cliente (Norte)
    JPanel panelCliente = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panelCliente.add(new JLabel("Cliente:"));
    txtCliente = new JTextField(20);
    panelCliente.add(txtCliente);

    // Lista de Items (Centro)
    listaItems = new JList<>(listModel);
    listaItems.setCellRenderer(new ItemPedidoRenderer());
    JScrollPane scrollItems = new JScrollPane(listaItems);
    scrollItems.setPreferredSize(new Dimension(600, 200)); 

    // Panel Total (Sur)
    JPanel panelTotal = new JPanel();
    lblTotal = new JLabel("Total: $0.00");
    panelTotal.add(lblTotal);

    // Botón Confirmar (Este)
    JButton btnConfirmar = new JButton("Confirmar Pedido");
    btnConfirmar.addActionListener(e -> {
        try {
            confirmarPedido();
        } catch (Exception ex) {
            Logger.getLogger(PanelPedidoIM.class.getName()).log(Level.SEVERE, null, ex);
        }
    });

    // Organización final
    add(panelCliente, BorderLayout.NORTH);
    add(scrollItems, BorderLayout.CENTER); // Asegurar que la lista está en el centro
    add(panelTotal, BorderLayout.SOUTH);
    add(btnConfirmar, BorderLayout.EAST);
}

    // En PanelPedidoIM.java
        public void agregarItem(Producto producto) {
            if (producto == null) return;

            // Diálogo para descripción
            String descripcion = JOptionPane.showInputDialog(
                SwingUtilities.getRoot(this), 
                "Ingrese descripción para " + producto.getNombre(),
                "Detalle del Producto",
                JOptionPane.PLAIN_MESSAGE
            );
            System.out.println("[DEBUG] Agregando producto: " + producto.getNombre());

            if (descripcion == null) return; // Si el usuario cancela

            // Crear ítem con descripción
            ItemPedido nuevoItem = new ItemPedido();
            nuevoItem.setProducto(producto);
            nuevoItem.setCantidad(1);
            nuevoItem.setDescripcion(descripcion);
            
            // Actualizar modelo y forzar repintado
            listModel.addElement(nuevoItem);
            listaItems.setModel(listModel); // Refrescar modelo
            listaItems.repaint(); // Actualizar visualmente
            System.out.println("[DEBUG] Items en lista: " + listModel.size()); 
            actualizarTotal();
        }


   private void actualizarTotal() {
        float total = Collections.list(listModel.elements()).stream()
            .map(item -> item.getProducto().getPrecioVenta() * item.getCantidad()) // Multiplicación directa de floats
            .reduce(0.0f, Float::sum); // Suma todos los floats

        lblTotal.setText(String.format("Total: $%.2f", total));
    }

    public void confirmarPedido() {
        EntityManager em = pedidoS.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // 1. Persistir cliente
            Cliente cliente = new Cliente();
            cliente.setNombre(txtCliente.getText().trim());
            em.persist(cliente);

            // 2. Persistir pedido (genera ID automático)
            Pedido pedido = new Pedido();
            pedido.setClienteId(cliente);
            em.persist(pedido);
 System.out.println(cliente.getIdCliente());           
System.out.println(pedido.getNumeroPedido());
            // 3. Persistir ítems (usando cascade)
            List<ItemPedido> items = Collections.list(listModel.elements());
            for (ItemPedido item : items) {
                item.setPedido(pedido);
                em.persist(item);
                System.out.println(item.getPedido());
            }

            tx.commit(); // ✅ Se genera el ID aquí

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            // Manejar error
        } finally {
            em.close();
        }
    }

private float calcularTotal(List<ItemPedido> items) {
    if (items == null || items.isEmpty()) {
        return 0.0f; // Manejo de lista nula o vacía
    }

    return items.stream()
        .map(item -> { 
            float precio = item.getProducto().getPrecioVenta(); // Asume que getPrecioVenta() retorna float
            float cantidad = item.getCantidad(); // Asume que getCantidad() retorna float (o int)
            return precio * cantidad;
        })
        .reduce(0.0f, Float::sum); // Suma todos los elementos
}
    private void actualizarEstadoMesa(float total, String nombreCliente) {
        if (listener != null) {
            String estado = "Ocupado - " + nombreCliente;
            listener.onActualizarEstadoMesa(
                mesaActual, 
                estado,
                total // Ya es float, no necesita conversión
            );
        }
    }

    public void setMesaActual(int mesa) {
        this.mesaActual = mesa;
    }

    public void setPedidoListener(PedidoListener listener) {
        this.listener = listener;
    }

    public void cargarPedido(Pedido pedido) {
        if (pedido == null) return;

        // Limpiar el modelo actual
        listModel.clear();

        // Establecer datos del cliente
        if (pedido.getClienteId() != null) {
            txtCliente.setText(pedido.getClienteId().getNombre());
        }

        // Cargar items del pedido
        if (pedido.getItemPedidoList() != null) {
            for (ItemPedido item : pedido.getItemPedidoList()) {
                listModel.addElement(item);
            }
        }

        actualizarTotal();
        repaint();
    }

    // Método mejorado para resetear el formulario
    public void resetearFormulario() {
        listModel.clear();
        txtCliente.setText("");
        actualizarTotal();
        listaItems.repaint();
        revalidate(); // Añadir esto para forzar actualización del layout
    }
    // Renderizador personalizado para los items
   private static class ItemPedidoRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                 boolean isSelected, boolean cellHasFocus) {
        ItemPedido item = (ItemPedido) value;
        float subtotal = item.getProducto().getPrecioVenta() * item.getCantidad(); // ✅ float * int/float
        String texto = String.format(
            "<html><b>%s</b> x%d<br><i>%s</i> <br>($%.2f)</html>", 
            item.getProducto().getNombre(),
            item.getCantidad(),
            item.getDescripcion(),
            subtotal  // Mostrar el resultado ya calculado
        );
        return super.getListCellRendererComponent(list, texto, index, isSelected, cellHasFocus);
        }
    }
    
}