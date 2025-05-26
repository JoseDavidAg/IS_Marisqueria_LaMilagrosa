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
import com.IS.marisqueria3.model.Producto;
import com.IS.marisqueria3.model.ProductoIngrediente;
import com.IS.marisqueria3.model.ProductoIngredientePK;
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
public class ProductoIngredienteJpaController implements Serializable {

    public ProductoIngredienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public ProductoIngredienteJpaController(){
        emf= Persistence.createEntityManagerFactory("Marisqueria3TPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProductoIngrediente productoIngrediente) throws PreexistingEntityException, Exception {
        if (productoIngrediente.getProductoIngredientePK() == null) {
            productoIngrediente.setProductoIngredientePK(new ProductoIngredientePK());
        }
        productoIngrediente.getProductoIngredientePK().setProductoId(productoIngrediente.getProducto().getIdPlatillo());
        productoIngrediente.getProductoIngredientePK().setIngredienteCodigo(productoIngrediente.getIngrediente().getIngredienteId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingrediente ingrediente = productoIngrediente.getIngrediente();
            if (ingrediente != null) {
                ingrediente = em.getReference(ingrediente.getClass(), ingrediente.getIngredienteId());
                productoIngrediente.setIngrediente(ingrediente);
            }
            Producto producto = productoIngrediente.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getIdPlatillo());
                productoIngrediente.setProducto(producto);
            }
            em.persist(productoIngrediente);
            if (ingrediente != null) {
                ingrediente.getProductoIngredienteList().add(productoIngrediente);
                ingrediente = em.merge(ingrediente);
            }
            if (producto != null) {
                producto.getProductoIngredienteList().add(productoIngrediente);
                producto = em.merge(producto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProductoIngrediente(productoIngrediente.getProductoIngredientePK()) != null) {
                throw new PreexistingEntityException("ProductoIngrediente " + productoIngrediente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProductoIngrediente productoIngrediente) throws NonexistentEntityException, Exception {
        productoIngrediente.getProductoIngredientePK().setProductoId(productoIngrediente.getProducto().getIdPlatillo());
        productoIngrediente.getProductoIngredientePK().setIngredienteCodigo(productoIngrediente.getIngrediente().getIngredienteId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductoIngrediente persistentProductoIngrediente = em.find(ProductoIngrediente.class, productoIngrediente.getProductoIngredientePK());
            Ingrediente ingredienteOld = persistentProductoIngrediente.getIngrediente();
            Ingrediente ingredienteNew = productoIngrediente.getIngrediente();
            Producto productoOld = persistentProductoIngrediente.getProducto();
            Producto productoNew = productoIngrediente.getProducto();
            if (ingredienteNew != null) {
                ingredienteNew = em.getReference(ingredienteNew.getClass(), ingredienteNew.getIngredienteId());
                productoIngrediente.setIngrediente(ingredienteNew);
            }
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getIdPlatillo());
                productoIngrediente.setProducto(productoNew);
            }
            productoIngrediente = em.merge(productoIngrediente);
            if (ingredienteOld != null && !ingredienteOld.equals(ingredienteNew)) {
                ingredienteOld.getProductoIngredienteList().remove(productoIngrediente);
                ingredienteOld = em.merge(ingredienteOld);
            }
            if (ingredienteNew != null && !ingredienteNew.equals(ingredienteOld)) {
                ingredienteNew.getProductoIngredienteList().add(productoIngrediente);
                ingredienteNew = em.merge(ingredienteNew);
            }
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getProductoIngredienteList().remove(productoIngrediente);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getProductoIngredienteList().add(productoIngrediente);
                productoNew = em.merge(productoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ProductoIngredientePK id = productoIngrediente.getProductoIngredientePK();
                if (findProductoIngrediente(id) == null) {
                    throw new NonexistentEntityException("The productoIngrediente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ProductoIngredientePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductoIngrediente productoIngrediente;
            try {
                productoIngrediente = em.getReference(ProductoIngrediente.class, id);
                productoIngrediente.getProductoIngredientePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productoIngrediente with id " + id + " no longer exists.", enfe);
            }
            Ingrediente ingrediente = productoIngrediente.getIngrediente();
            if (ingrediente != null) {
                ingrediente.getProductoIngredienteList().remove(productoIngrediente);
                ingrediente = em.merge(ingrediente);
            }
            Producto producto = productoIngrediente.getProducto();
            if (producto != null) {
                producto.getProductoIngredienteList().remove(productoIngrediente);
                producto = em.merge(producto);
            }
            em.remove(productoIngrediente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProductoIngrediente> findProductoIngredienteEntities() {
        return findProductoIngredienteEntities(true, -1, -1);
    }

    public List<ProductoIngrediente> findProductoIngredienteEntities(int maxResults, int firstResult) {
        return findProductoIngredienteEntities(false, maxResults, firstResult);
    }

    private List<ProductoIngrediente> findProductoIngredienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProductoIngrediente.class));
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

    public ProductoIngrediente findProductoIngrediente(ProductoIngredientePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProductoIngrediente.class, id);
        } finally {
            em.close();
        }
    }
    
    public List<ProductoIngrediente> findByProductoId(int productoId) {
    EntityManager em = getEntityManager();
    try {
        return em.createQuery("SELECT p FROM ProductoIngrediente p WHERE p.productoIngredientePK.productoId = :productoId", ProductoIngrediente.class)
                 .setParameter("productoId", productoId)
                 .getResultList();
    } finally {
        em.close();
    }
}
    public List<Object[]> findStockByProductoId(int productoId) {
    EntityManager em = getEntityManager();
    try {
        return em.createQuery(
            "SELECT i.stockDisponible, pi.cantidad " +
            "FROM ProductoIngrediente pi " +
            "JOIN pi.ingrediente i " +
            "WHERE pi.producto.idPlatillo = :productoId", Object[].class)
            .setParameter("productoId", productoId)
            .getResultList();
    } finally {
        em.close();
    }
}


    public int getProductoIngredienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProductoIngrediente> rt = cq.from(ProductoIngrediente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
