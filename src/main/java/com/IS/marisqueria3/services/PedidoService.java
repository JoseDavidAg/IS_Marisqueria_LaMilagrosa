/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.services;


import com.IS.marisqueria3.model.Ingrediente;
import com.IS.marisqueria3.model.ItemPedido;
import com.IS.marisqueria3.model.ItemPedidoPK;
import com.IS.marisqueria3.model.Pedido;
import com.IS.marisqueria3.persistence.ItemPedidoJpaController;
import com.IS.marisqueria3.persistence.PedidoJpaController;
import com.IS.marisqueria3.persistence.ProductoIngredienteJpaController;
import com.IS.marisqueria3.persistence.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
/**
 *
 * @author ambro
 */
public class PedidoService {
    private final PedidoJpaController pedidoJpa;
    private final ItemPedidoJpaController itemPedidoJpa;
    ProductoIngredienteJpaController proIngredienteJpa;
    

    public PedidoService() {
        pedidoJpa = new PedidoJpaController();
        itemPedidoJpa = new ItemPedidoJpaController();
        proIngredienteJpa = new ProductoIngredienteJpaController();
    }
    
    public List<Pedido> traerTodosPedidos() {
        return pedidoJpa.findPedidoEntities();
    }

    // En PedidoService.java (método crearPedido)
    // En PedidoService.crearPedido()
    public void crearPedido(Pedido pedido) {
        EntityManager em = pedidoJpa.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(pedido);
            em.flush(); // Forzar generación de ID
            em.getTransaction().commit();
        } catch (Exception e) {
            // Manejar rollback
        }
}
    
    public EntityManager getEntityManager() {
        return pedidoJpa.getEntityManager();
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

    public String traerIngredientes(int platilloId) {
    List<Ingrediente> ingredientes = pedidoJpa.findPedidoIngredientes(platilloId);
    
    if (ingredientes == null || ingredientes.isEmpty()) {
        return "No hay ingredientes registrados para este producto.";
    }

    StringBuilder sb = new StringBuilder();
    for (Ingrediente i : ingredientes) {
        sb.append(i.getNombre())
          .append(": ")
          .append(i.getDescripcion() != null ? i.getDescripcion() : "Sin descripción")
          .append("\n");
    }
    return sb.toString();
    }
    /*
    public boolean productoDisponible(Integer idProducto) {
        
        // Obtener la lista de ProductoIngrediente directamente del platillo
        List<ProductoIngrediente> ingredientes = proIngredienteJpa.findByProductoId(idProducto);
        for (ProductoIngrediente pi : ingredientes) {
            Ingrediente i = pi.getIngrediente();
            Integer cantidadNecesaria = pi.getCantidad();

            if (cantidadNecesaria == null || i == null || i.getStockDisponible() == null) {
                return false; // Información incompleta
            }

            if ((i.getStockDisponible() - cantidadNecesaria) < 0) {
                return false;
            }
        }

        return true;
    }*/
    
  
    
    public boolean productoDisponible(Integer idProducto) {
    // Usar una consulta optimizada con JOIN para evitar múltiples SELECTs
    List<Object[]> resultados = proIngredienteJpa.findStockByProductoId(idProducto);
    for (Object[] resultado : resultados) {
        Integer stockDisponible = (Integer) resultado[0];
        Integer cantidadNecesaria = (Integer) resultado[1];
        if (stockDisponible == null || cantidadNecesaria == null || stockDisponible < cantidadNecesaria) {
            return false;
        }
    }
    return true;
}


    
    

    
}
