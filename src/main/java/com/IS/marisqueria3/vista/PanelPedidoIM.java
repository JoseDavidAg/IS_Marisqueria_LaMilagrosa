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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.text.DecimalFormat;

public class PanelPedidoIM extends JPanel {
    private final PedidoService pedidoS;
    private final ClienteMesaService clienteS;
    private final DefaultListModel<ItemPedido> listModel;
    private JTextField txtCliente;
    private JList<ItemPedido> listaItems;
    private JLabel lblTotal;
    private PedidoListener listener;
    private int mesaActual;
    private JButton btnEliminarItem;
    private JButton btnEditarCantidad;

    public interface PedidoListener {
        void onPedidoConfirmado(Pedido pedido, int mesa);
        void onActualizarEstadoMesa(int mesa, String estado, float total);
    }

    public PanelPedidoIM() {
        clienteS = new ClienteMesaService();
        this.pedidoS = new PedidoService();
        this.listModel = new DefaultListModel<>();
        initUI();
        setPreferredSize(new Dimension(800, 400));
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new TitledBorder("Detalles del Pedido"));

        // Panel Cliente (Norte)
        JPanel panelCliente = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCliente.add(new JLabel("Cliente:"));
        txtCliente = new JTextField(25);
        panelCliente.add(txtCliente);

        // Panel Central con lista y botones
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        
        // Lista de Items
        listaItems = new JList<>(listModel);
        listaItems.setCellRenderer(new ItemPedidoRenderer());
        listaItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollItems = new JScrollPane(listaItems);
        scrollItems.setPreferredSize(new Dimension(650, 250));
        centerPanel.add(scrollItems, BorderLayout.CENTER);
        
        // Panel de botones para items
        JPanel itemButtonsPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        btnEliminarItem = new JButton("Eliminar Item");
        btnEditarCantidad = new JButton("Editar Cantidad");
        
        btnEliminarItem.addActionListener(e -> eliminarItemSeleccionado());
        btnEditarCantidad.addActionListener(e -> editarCantidadItem());
        
        itemButtonsPanel.add(btnEditarCantidad);
        itemButtonsPanel.add(btnEliminarItem);
        centerPanel.add(itemButtonsPanel, BorderLayout.EAST);

        // Panel Total (Sur)
        JPanel panelSur = new JPanel(new BorderLayout(10, 10));
        JPanel panelTotal = new JPanel();
        lblTotal = new JLabel("Total: $0.00");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
        panelTotal.add(lblTotal);
        panelSur.add(panelTotal, BorderLayout.CENTER);

        // Botón Confirmar (Sur-Este)
        JButton btnConfirmar = new JButton("Confirmar Pedido");
        btnConfirmar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnConfirmar.setBackground(new Color(50, 150, 50));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.addActionListener(e -> {
            try {
                confirmarPedido();
            } catch (Exception ex) {
                Logger.getLogger(PanelPedidoIM.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        panelSur.add(btnConfirmar, BorderLayout.EAST);

        // Organización final
        add(panelCliente, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);
    }

    public void agregarItem(Producto producto) {
        if (producto == null) return;

        // Diálogo para descripción
        String descripcion = JOptionPane.showInputDialog(
            SwingUtilities.getRoot(this), 
            "Ingrese descripción para " + producto.getNombre(),
            "Detalle del Producto",
            JOptionPane.PLAIN_MESSAGE
        );

        if (descripcion == null) return; // Si el usuario cancela

        // Diálogo para cantidad
        String cantidadStr = JOptionPane.showInputDialog(
            SwingUtilities.getRoot(this), 
            "Ingrese cantidad para " + producto.getNombre(),
            "Cantidad",
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (cantidadStr == null) return; // Si el usuario cancela
        
        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que 0", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear ítem con descripción y cantidad
        ItemPedido nuevoItem = new ItemPedido();
        nuevoItem.setIdProducto(producto);
        nuevoItem.setCantidad(cantidad);
        nuevoItem.setDescripcion(descripcion);
        nuevoItem.setPrecioUnitario(producto.getPrecioVenta()); // Guardar precio histórico

        // Actualizar modelo
        listModel.addElement(nuevoItem);
        actualizarTotal();
    }

    private void eliminarItemSeleccionado() {
        int selectedIndex = listaItems.getSelectedIndex();
        if (selectedIndex != -1) {
            listModel.remove(selectedIndex);
            actualizarTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un item para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void editarCantidadItem() {
        int selectedIndex = listaItems.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un item para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ItemPedido item = listModel.getElementAt(selectedIndex);
        String cantidadStr = JOptionPane.showInputDialog(
            this, 
            "Ingrese nueva cantidad para " + item.getIdProducto().getNombre(),
            "Editar Cantidad",
            JOptionPane.PLAIN_MESSAGE,
            null,
            null,
            item.getCantidad()
        ).toString();

        if (cantidadStr == null) return; // Si el usuario cancela

        try {
            int nuevaCantidad = Integer.parseInt(cantidadStr);
            if (nuevaCantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que 0", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            item.setCantidad(nuevaCantidad);
            listModel.setElementAt(item, selectedIndex);
            actualizarTotal();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTotal() {
        float total = Collections.list(listModel.elements()).stream()
            .map(item -> item.getIdProducto().getPrecioVenta() * item.getCantidad()) // Multiplicación directa de floats
            .reduce(0.0f, Float::sum); // Suma todos los floats

        lblTotal.setText(String.format("Total: $%.2f", total));
    }

    public void confirmarPedido() {
        // Validar campos
        if (txtCliente.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del cliente", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (listModel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Agregue al menos un item al pedido", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        EntityManager em = pedidoS.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // 1. Persistir cliente
            Cliente cliente = new Cliente();
            cliente.setNombre(txtCliente.getText().trim());
            em.persist(cliente);
            em.flush();

            // 2. Persistir pedido
            Pedido pedido = new Pedido();
            pedido.setClienteId(cliente);
            pedido.setFechaGeneracion(new Date());
            pedido.setEstado("pendiente");
            pedido.setTipoPedido("local");
            pedido.setEsUrgente(false);
            
            // Calcular total
            Float totalPedido = null;
            for (int i = 0; i < listModel.size(); i++) {
                ItemPedido item = listModel.getElementAt(i);
                totalPedido = item.getPrecioUnitario()*item.getCantidad();
            }
            
            em.persist(pedido);
            em.flush();

            // 3. Persistir ítems
            for (int i = 0; i < listModel.size(); i++) {
                ItemPedido item = listModel.getElementAt(i);
                item.setPedidoNumero(pedido); // Establecer relación con el pedido
                em.persist(item);
            }

            tx.commit();

            // 4. Actualizar UI y estado
            actualizarEstadoMesa(totalPedido, cliente.getNombre());
            resetearFormulario();
            JOptionPane.showMessageDialog(this, "Pedido confirmado exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al confirmar el pedido: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
     private float calcularTotal() {
        float total = Collections.list(listModel.elements()).stream()
            .map(item -> item.getIdProducto().getPrecioVenta() * item.getCantidad()) // Multiplicación directa de floats
            .reduce(0.0f, Float::sum); // Suma todos los floats

        return total;
    }

    private void actualizarEstadoMesa(float total, String nombreCliente) {
        if (listener != null) {
            String estado = "Ocupado - " + nombreCliente;
            listener.onActualizarEstadoMesa(mesaActual, estado, total);
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
        if (pedido.getItemPedidoList()!= null) {
            for (ItemPedido item : pedido.getItemPedidoList()) {
                listModel.addElement(item);
            }
        }

        actualizarTotal();
        repaint();
    }

    public void resetearFormulario() {
        listModel.clear();
        txtCliente.setText("");
        actualizarTotal();
        listaItems.repaint();
        revalidate();
    }

    // Renderizador personalizado para los items
    private static class ItemPedidoRenderer extends DefaultListCellRenderer {
        private final DecimalFormat df = new DecimalFormat("#,##0.00");
        
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                     boolean isSelected, boolean cellHasFocus) {
            ItemPedido item = (ItemPedido) value;
            float subtotal = item.getIdProducto().getPrecioVenta()*item.getCantidad();
            
            String texto = String.format(
                "<html><div style='width:400px;'><b>%s</b> x%d<br><i>%s</i><br>Precio: $%s | Subtotal: $%s</div></html>", 
                item.getIdProducto().getNombre(),
                item.getCantidad(),
                item.getDescripcion(),
                df.format(item.getPrecioUnitario()),
                df.format(subtotal)
            );
            
            JLabel label = (JLabel) super.getListCellRendererComponent(
                list, texto, index, isSelected, cellHasFocus
            );
            
            label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            return label;
        }
    }
}