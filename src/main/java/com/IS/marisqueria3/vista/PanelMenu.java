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
import com.IS.marisqueria3.model.Producto;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PanelMenu extends JPanel {
    private JTabbedPane tabbedPane;
    private List<CardProducto> tarjetas;
    private CardProducto tarjeta;

    public PanelMenu(List<Producto> productos) {
        tarjetas = new ArrayList<>();
        
        setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();
        add(tabbedPane, BorderLayout.CENTER);
        agruparPorCategoria(productos);
   
    }

    private void agruparPorCategoria(List<Producto> productos) {
        Map<String, List<Producto>> porCategoria = productos.stream()
                .collect(Collectors.groupingBy(Producto::getCategoriaNombre));

        porCategoria.forEach((categoria, lista) -> {
            JPanel panelCategoria = new JPanel(new GridLayout(0, 2, 20, 20));
            panelCategoria.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            for (Producto p : lista) {
                tarjeta = new CardProducto(p);
                tarjetas.add(tarjeta);
                panelCategoria.add(tarjeta);
            }
            JScrollPane scrollPane = new JScrollPane(panelCategoria);
            tabbedPane.addTab(categoria, scrollPane);
        });
        
    }

   
    
    public boolean iniciarTimerActualizacion() {
   
        boolean necesitaRedibujar = false;
        for (CardProducto tarjeta1 : tarjetas) {
            if (tarjeta1.actualizarDisponibilidad()) {
                necesitaRedibujar = true;
            }
        }
        if (necesitaRedibujar) {
            revalidate();
            repaint();
            return true;
            }
        return false;
        
    }
    
    public void actualizarTarjetas(List<Producto> nuevosProductos) {
    // Actualizar cada tarjeta con la nueva lista de productos
    Map<Integer, Producto> productoMap = nuevosProductos.stream()
        .collect(Collectors.toMap(Producto::getIdPlatillo, p -> p));

    for (CardProducto tarjetaA : tarjetas) {
        Producto nuevoProducto = productoMap.get(tarjetaA.getProducto().getIdPlatillo());
        if (nuevoProducto != null) {
            tarjetaA.setProducto(nuevoProducto); // Añade un método setProducto en CardProducto
            tarjetaA.actualizarDisponibilidad();
        }
        }
    }


    
}
