/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.persistence;

import com.IS.marisqueria3.model.ItemPedido;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.IS.marisqueria3.model.Pedido;
import com.IS.marisqueria3.model.Producto;
import com.IS.marisqueria3.persistence.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ambro
 */
public class ItemPedidoJpaController implements Serializable {

    public ItemPedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ItemPedido itemPedido) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedidoNumero = itemPedido.getPedidoNumero();
            if (pedidoNumero != null) {
                pedidoNumero = em.getReference(pedidoNumero.getClass(), pedidoNumero.getNumeroPedido());
                itemPedido.setPedidoNumero(pedidoNumero);
            }
            Producto idProducto = itemPedido.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getIdPlatillo());
                itemPedido.setIdProducto(idProducto);
            }
            em.persist(itemPedido);
            if (pedidoNumero != null) {
                pedidoNumero.getItemPedidoList().add(itemPedido);
                pedidoNumero = em.merge(pedidoNumero);
            }
            if (idProducto != null) {
                idProducto.getItemPedidoList().add(itemPedido);
                idProducto = em.merge(idProducto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ItemPedido itemPedido) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItemPedido persistentItemPedido = em.find(ItemPedido.class, itemPedido.getIdItem());
            Pedido pedidoNumeroOld = persistentItemPedido.getPedidoNumero();
            Pedido pedidoNumeroNew = itemPedido.getPedidoNumero();
            Producto idProductoOld = persistentItemPedido.getIdProducto();
            Producto idProductoNew = itemPedido.getIdProducto();
            if (pedidoNumeroNew != null) {
                pedidoNumeroNew = em.getReference(pedidoNumeroNew.getClass(), pedidoNumeroNew.getNumeroPedido());
                itemPedido.setPedidoNumero(pedidoNumeroNew);
            }
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getIdPlatillo());
                itemPedido.setIdProducto(idProductoNew);
            }
            itemPedido = em.merge(itemPedido);
            if (pedidoNumeroOld != null && !pedidoNumeroOld.equals(pedidoNumeroNew)) {
                pedidoNumeroOld.getItemPedidoList().remove(itemPedido);
                pedidoNumeroOld = em.merge(pedidoNumeroOld);
            }
            if (pedidoNumeroNew != null && !pedidoNumeroNew.equals(pedidoNumeroOld)) {
                pedidoNumeroNew.getItemPedidoList().add(itemPedido);
                pedidoNumeroNew = em.merge(pedidoNumeroNew);
            }
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getItemPedidoList().remove(itemPedido);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getItemPedidoList().add(itemPedido);
                idProductoNew = em.merge(idProductoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = itemPedido.getIdItem();
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

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItemPedido itemPedido;
            try {
                itemPedido = em.getReference(ItemPedido.class, id);
                itemPedido.getIdItem();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemPedido with id " + id + " no longer exists.", enfe);
            }
            Pedido pedidoNumero = itemPedido.getPedidoNumero();
            if (pedidoNumero != null) {
                pedidoNumero.getItemPedidoList().remove(itemPedido);
                pedidoNumero = em.merge(pedidoNumero);
            }
            Producto idProducto = itemPedido.getIdProducto();
            if (idProducto != null) {
                idProducto.getItemPedidoList().remove(itemPedido);
                idProducto = em.merge(idProducto);
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

    public ItemPedido findItemPedido(Integer id) {
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
