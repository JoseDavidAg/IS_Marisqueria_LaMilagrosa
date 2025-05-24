/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.persistence;

import com.IS.marisqueria3.controller.exceptions.NonexistentEntityException;
import com.IS.marisqueria3.controller.exceptions.PreexistingEntityException;
import com.IS.marisqueria3.model.CategoriaCarta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.IS.marisqueria3.model.Producto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ambro
 */
public class CategoriaCartaJpaController implements Serializable {

    public CategoriaCartaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    
    public CategoriaCartaJpaController(){
        emf=Persistence.createEntityManagerFactory("MarisqueriaUP");
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CategoriaCarta categoriaCarta) throws PreexistingEntityException, Exception {
        if (categoriaCarta.getProductoList() == null) {
            categoriaCarta.setProductoList(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Producto> attachedProductoList = new ArrayList<Producto>();
            for (Producto productoListProductoToAttach : categoriaCarta.getProductoList()) {
                productoListProductoToAttach = em.getReference(productoListProductoToAttach.getClass(), productoListProductoToAttach.getIdPlatillo());
                attachedProductoList.add(productoListProductoToAttach);
            }
            categoriaCarta.setProductoList(attachedProductoList);
            em.persist(categoriaCarta);
            for (Producto productoListProducto : categoriaCarta.getProductoList()) {
                CategoriaCarta oldCategoriaNombreOfProductoListProducto = productoListProducto.getCategoriaNombre();
                productoListProducto.setCategoriaNombre(categoriaCarta);
                productoListProducto = em.merge(productoListProducto);
                if (oldCategoriaNombreOfProductoListProducto != null) {
                    oldCategoriaNombreOfProductoListProducto.getProductoList().remove(productoListProducto);
                    oldCategoriaNombreOfProductoListProducto = em.merge(oldCategoriaNombreOfProductoListProducto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCategoriaCarta(categoriaCarta.getNombre()) != null) {
                throw new PreexistingEntityException("CategoriaCarta " + categoriaCarta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CategoriaCarta categoriaCarta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriaCarta persistentCategoriaCarta = em.find(CategoriaCarta.class, categoriaCarta.getNombre());
            List<Producto> productoListOld = persistentCategoriaCarta.getProductoList();
            List<Producto> productoListNew = categoriaCarta.getProductoList();
            List<Producto> attachedProductoListNew = new ArrayList<Producto>();
            for (Producto productoListNewProductoToAttach : productoListNew) {
                productoListNewProductoToAttach = em.getReference(productoListNewProductoToAttach.getClass(), productoListNewProductoToAttach.getIdPlatillo());
                attachedProductoListNew.add(productoListNewProductoToAttach);
            }
            productoListNew = attachedProductoListNew;
            categoriaCarta.setProductoList(productoListNew);
            categoriaCarta = em.merge(categoriaCarta);
            for (Producto productoListOldProducto : productoListOld) {
                if (!productoListNew.contains(productoListOldProducto)) {
                    productoListOldProducto.setCategoriaNombre(null);
                    productoListOldProducto = em.merge(productoListOldProducto);
                }
            }
            for (Producto productoListNewProducto : productoListNew) {
                if (!productoListOld.contains(productoListNewProducto)) {
                    CategoriaCarta oldCategoriaNombreOfProductoListNewProducto = productoListNewProducto.getCategoriaNombre();
                    productoListNewProducto.setCategoriaNombre(categoriaCarta);
                    productoListNewProducto = em.merge(productoListNewProducto);
                    if (oldCategoriaNombreOfProductoListNewProducto != null && !oldCategoriaNombreOfProductoListNewProducto.equals(categoriaCarta)) {
                        oldCategoriaNombreOfProductoListNewProducto.getProductoList().remove(productoListNewProducto);
                        oldCategoriaNombreOfProductoListNewProducto = em.merge(oldCategoriaNombreOfProductoListNewProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = categoriaCarta.getNombre();
                if (findCategoriaCarta(id) == null) {
                    throw new NonexistentEntityException("The categoriaCarta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriaCarta categoriaCarta;
            try {
                categoriaCarta = em.getReference(CategoriaCarta.class, id);
                categoriaCarta.getNombre();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaCarta with id " + id + " no longer exists.", enfe);
            }
            List<Producto> productoList = categoriaCarta.getProductoList();
            for (Producto productoListProducto : productoList) {
                productoListProducto.setCategoriaNombre(null);
                productoListProducto = em.merge(productoListProducto);
            }
            em.remove(categoriaCarta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CategoriaCarta> findCategoriaCartaEntities() {
        return findCategoriaCartaEntities(true, -1, -1);
    }

    public List<CategoriaCarta> findCategoriaCartaEntities(int maxResults, int firstResult) {
        return findCategoriaCartaEntities(false, maxResults, firstResult);
    }

    private List<CategoriaCarta> findCategoriaCartaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CategoriaCarta.class));
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

    public CategoriaCarta findCategoriaCarta(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategoriaCarta.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaCartaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CategoriaCarta> rt = cq.from(CategoriaCarta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
