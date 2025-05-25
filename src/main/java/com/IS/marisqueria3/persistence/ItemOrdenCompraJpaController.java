/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.persistence;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.IS.marisqueria3.model.Ingrediente;
import com.IS.marisqueria3.model.ItemOrdenCompra;
import com.IS.marisqueria3.model.OrdenCompra;
import com.IS.marisqueria3.persistence.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ambro
 */
public class ItemOrdenCompraJpaController implements Serializable {

    public ItemOrdenCompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public ItemOrdenCompraJpaController(){
        emf= Persistence.createEntityManagerFactory("Marisqueria3TPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ItemOrdenCompra itemOrdenCompra) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingrediente ingredienteCodigo = itemOrdenCompra.getIngredienteCodigo();
            if (ingredienteCodigo != null) {
                ingredienteCodigo = em.getReference(ingredienteCodigo.getClass(), ingredienteCodigo.getIngredienteId());
                itemOrdenCompra.setIngredienteCodigo(ingredienteCodigo);
            }
            OrdenCompra ordenCompraNumero = itemOrdenCompra.getOrdenCompraNumero();
            if (ordenCompraNumero != null) {
                ordenCompraNumero = em.getReference(ordenCompraNumero.getClass(), ordenCompraNumero.getNumeroOrden());
                itemOrdenCompra.setOrdenCompraNumero(ordenCompraNumero);
            }
            em.persist(itemOrdenCompra);
            if (ingredienteCodigo != null) {
                ingredienteCodigo.getItemOrdenCompraList().add(itemOrdenCompra);
                ingredienteCodigo = em.merge(ingredienteCodigo);
            }
            if (ordenCompraNumero != null) {
                ordenCompraNumero.getItemOrdenCompraList().add(itemOrdenCompra);
                ordenCompraNumero = em.merge(ordenCompraNumero);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ItemOrdenCompra itemOrdenCompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItemOrdenCompra persistentItemOrdenCompra = em.find(ItemOrdenCompra.class, itemOrdenCompra.getIdItemOrden());
            Ingrediente ingredienteCodigoOld = persistentItemOrdenCompra.getIngredienteCodigo();
            Ingrediente ingredienteCodigoNew = itemOrdenCompra.getIngredienteCodigo();
            OrdenCompra ordenCompraNumeroOld = persistentItemOrdenCompra.getOrdenCompraNumero();
            OrdenCompra ordenCompraNumeroNew = itemOrdenCompra.getOrdenCompraNumero();
            if (ingredienteCodigoNew != null) {
                ingredienteCodigoNew = em.getReference(ingredienteCodigoNew.getClass(), ingredienteCodigoNew.getIngredienteId());
                itemOrdenCompra.setIngredienteCodigo(ingredienteCodigoNew);
            }
            if (ordenCompraNumeroNew != null) {
                ordenCompraNumeroNew = em.getReference(ordenCompraNumeroNew.getClass(), ordenCompraNumeroNew.getNumeroOrden());
                itemOrdenCompra.setOrdenCompraNumero(ordenCompraNumeroNew);
            }
            itemOrdenCompra = em.merge(itemOrdenCompra);
            if (ingredienteCodigoOld != null && !ingredienteCodigoOld.equals(ingredienteCodigoNew)) {
                ingredienteCodigoOld.getItemOrdenCompraList().remove(itemOrdenCompra);
                ingredienteCodigoOld = em.merge(ingredienteCodigoOld);
            }
            if (ingredienteCodigoNew != null && !ingredienteCodigoNew.equals(ingredienteCodigoOld)) {
                ingredienteCodigoNew.getItemOrdenCompraList().add(itemOrdenCompra);
                ingredienteCodigoNew = em.merge(ingredienteCodigoNew);
            }
            if (ordenCompraNumeroOld != null && !ordenCompraNumeroOld.equals(ordenCompraNumeroNew)) {
                ordenCompraNumeroOld.getItemOrdenCompraList().remove(itemOrdenCompra);
                ordenCompraNumeroOld = em.merge(ordenCompraNumeroOld);
            }
            if (ordenCompraNumeroNew != null && !ordenCompraNumeroNew.equals(ordenCompraNumeroOld)) {
                ordenCompraNumeroNew.getItemOrdenCompraList().add(itemOrdenCompra);
                ordenCompraNumeroNew = em.merge(ordenCompraNumeroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = itemOrdenCompra.getIdItemOrden();
                if (findItemOrdenCompra(id) == null) {
                    throw new NonexistentEntityException("The itemOrdenCompra with id " + id + " no longer exists.");
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
            ItemOrdenCompra itemOrdenCompra;
            try {
                itemOrdenCompra = em.getReference(ItemOrdenCompra.class, id);
                itemOrdenCompra.getIdItemOrden();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemOrdenCompra with id " + id + " no longer exists.", enfe);
            }
            Ingrediente ingredienteCodigo = itemOrdenCompra.getIngredienteCodigo();
            if (ingredienteCodigo != null) {
                ingredienteCodigo.getItemOrdenCompraList().remove(itemOrdenCompra);
                ingredienteCodigo = em.merge(ingredienteCodigo);
            }
            OrdenCompra ordenCompraNumero = itemOrdenCompra.getOrdenCompraNumero();
            if (ordenCompraNumero != null) {
                ordenCompraNumero.getItemOrdenCompraList().remove(itemOrdenCompra);
                ordenCompraNumero = em.merge(ordenCompraNumero);
            }
            em.remove(itemOrdenCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ItemOrdenCompra> findItemOrdenCompraEntities() {
        return findItemOrdenCompraEntities(true, -1, -1);
    }

    public List<ItemOrdenCompra> findItemOrdenCompraEntities(int maxResults, int firstResult) {
        return findItemOrdenCompraEntities(false, maxResults, firstResult);
    }

    private List<ItemOrdenCompra> findItemOrdenCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ItemOrdenCompra.class));
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

    public ItemOrdenCompra findItemOrdenCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ItemOrdenCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemOrdenCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ItemOrdenCompra> rt = cq.from(ItemOrdenCompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
