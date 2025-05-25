/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.persistence;

import com.IS.marisqueria3.model.ItemPedido;
import com.IS.marisqueria3.model.ItemPedidoPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.IS.marisqueria3.model.Pedido;
import com.IS.marisqueria3.model.Producto;
import com.IS.marisqueria3.persistence.exceptions.NonexistentEntityException;
import com.IS.marisqueria3.persistence.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ambro
 */
public class ItemPedidoJpaController implements Serializable {

    public ItemPedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public ItemPedidoJpaController(){
        emf= Persistence.createEntityManagerFactory("Marisqueria3TPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ItemPedido itemPedido) throws PreexistingEntityException, Exception {
        if (itemPedido.getItemPedidoPK() == null) {
            itemPedido.setItemPedidoPK(new ItemPedidoPK());
        }
        itemPedido.getItemPedidoPK().setPedidoNumero(itemPedido.getPedido().getNumeroPedido());
        itemPedido.getItemPedidoPK().setIdProducto(itemPedido.getProducto().getIdPlatillo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedido = itemPedido.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getNumeroPedido());
                itemPedido.setPedido(pedido);
            }
            Producto producto = itemPedido.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getIdPlatillo());
                itemPedido.setProducto(producto);
            }
            em.persist(itemPedido);
            if (pedido != null) {
                pedido.getItemPedidoList().add(itemPedido);
                pedido = em.merge(pedido);
            }
            if (producto != null) {
                producto.getItemPedidoList().add(itemPedido);
                producto = em.merge(producto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findItemPedido(itemPedido.getItemPedidoPK()) != null) {
                throw new PreexistingEntityException("ItemPedido " + itemPedido + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ItemPedido itemPedido) throws NonexistentEntityException, Exception {
        itemPedido.getItemPedidoPK().setPedidoNumero(itemPedido.getPedido().getNumeroPedido());
        itemPedido.getItemPedidoPK().setIdProducto(itemPedido.getProducto().getIdPlatillo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItemPedido persistentItemPedido = em.find(ItemPedido.class, itemPedido.getItemPedidoPK());
            Pedido pedidoOld = persistentItemPedido.getPedido();
            Pedido pedidoNew = itemPedido.getPedido();
            Producto productoOld = persistentItemPedido.getProducto();
            Producto productoNew = itemPedido.getProducto();
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getNumeroPedido());
                itemPedido.setPedido(pedidoNew);
            }
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getIdPlatillo());
                itemPedido.setProducto(productoNew);
            }
            itemPedido = em.merge(itemPedido);
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getItemPedidoList().remove(itemPedido);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getItemPedidoList().add(itemPedido);
                pedidoNew = em.merge(pedidoNew);
            }
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getItemPedidoList().remove(itemPedido);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getItemPedidoList().add(itemPedido);
                productoNew = em.merge(productoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ItemPedidoPK id = itemPedido.getItemPedidoPK();
                if (findItemPedido(id) == null) {
                    throw new NonexistentEntityException("The itemPedido with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ItemPedidoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItemPedido itemPedido;
            try {
                itemPedido = em.getReference(ItemPedido.class, id);
                itemPedido.getItemPedidoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemPedido with id " + id + " no longer exists.", enfe);
            }
            Pedido pedido = itemPedido.getPedido();
            if (pedido != null) {
                pedido.getItemPedidoList().remove(itemPedido);
                pedido = em.merge(pedido);
            }
            Producto producto = itemPedido.getProducto();
            if (producto != null) {
                producto.getItemPedidoList().remove(itemPedido);
                producto = em.merge(producto);
            }
            em.remove(itemPedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ItemPedido> findItemPedidoEntities() {
        return findItemPedidoEntities(true, -1, -1);
    }

    public List<ItemPedido> findItemPedidoEntities(int maxResults, int firstResult) {
        return findItemPedidoEntities(false, maxResults, firstResult);
    }

    private List<ItemPedido> findItemPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ItemPedido.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ItemPedido findItemPedido(ItemPedidoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ItemPedido.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ItemPedido> rt = cq.from(ItemPedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
