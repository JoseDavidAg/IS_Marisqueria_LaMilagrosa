/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.controller.exceptions.services;

import com.IS.marisqueria3.model.ItemPedido;
import com.IS.marisqueria3.model.ItemPedidoPK;
import com.IS.marisqueria3.model.Pedido;
import com.IS.marisqueria3.model.Usuario;
import com.IS.marisqueria3.persistence.ItemPedidoJpaController;
import com.IS.marisqueria3.persistence.PedidoJpaController;
import com.IS.marisqueria3.persistence.UsuarioJpaController;
import java.util.List;
/**
 *
 * @author ambro
 */
public class PedidoService {
    private final PedidoJpaController pedidoJpa;
    private final ItemPedidoJpaController itemPedidoJpa;
    private final UsuarioJpaController usuarioJpa;

    public PedidoService() {
        pedidoJpa = new PedidoJpaController();
        itemPedidoJpa = new ItemPedidoJpaController();
        usuarioJpa = new UsuarioJpaController();
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

    // ----------------------
    // Métodos para Usuario
    // ----------------------

    public List<Usuario> traerTodosUsuarios() {
        return usuarioJpa.findUsuarioEntities();
    }

    public void crearUsuario(Usuario usuario) {
        usuarioJpa.create(usuario);
    }

    public void eliminarUsuario(int idUsuario) {
        try {
            usuarioJpa.destroy(idUsuario);
        } catch (Exception e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
    }
    
}
