/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.controller.exceptions.services;

import com.IS.marisqueria3.controller.exceptions.NonexistentEntityException;
import com.IS.marisqueria3.model.ItemPedido;
import com.IS.marisqueria3.model.ItemPedidoPK;
import com.IS.marisqueria3.model.Pedido;
import com.IS.marisqueria3.persistence.ItemPedidoJpaController;
import com.IS.marisqueria3.persistence.PedidoJpaController;
import java.util.List;
/**
 *
 * @author ambro
 */
public class PedidoService {
    private final PedidoJpaController pedidoJpa;
    private final ItemPedidoJpaController itemPedidoJpa;
    

    public PedidoService() {
        pedidoJpa = new PedidoJpaController();
        itemPedidoJpa = new ItemPedidoJpaController();
    }
    
    public List<Pedido> traerTodosPedidos() {
        return pedidoJpa.findPedidoEntities();
    }

    public void crearPedido(Pedido pedido) {
        pedidoJpa.create(pedido);
    }

    public void eliminarPedido(int idPedido) {
        try {
            pedidoJpa.destroy(idPedido);
        } catch (Exception e) {
            System.out.println("Error al eliminar pedido: " + e.getMessage());
        }
    }
    
    public List<ItemPedido> traerTodosItemPedidos() {
        return itemPedidoJpa.findItemPedidoEntities();
    }

    public void crearItemPedido(ItemPedido itemPedido) throws Exception {
        itemPedidoJpa.create(itemPedido);
    }

    public void eliminarItemPedido(ItemPedidoPK id) {
        try {
            itemPedidoJpa.destroy(id);
        } catch (Exception e) {
            System.out.println("Error al eliminar item de pedido: " + e.getMessage());
        }
    }

    public List<Pedido> traerTodosPedidos(String pendiente) {
        return pedidoJpa.findPedidoEntities(pendiente);
    }

    public void actualizarPedido(Pedido pedido) throws NonexistentEntityException, Exception {
        pedidoJpa.edit(pedido);
    }
    
    

    
}
