/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.persistence;

import com.IS.marisqueria3.model.CategoriaCarta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.IS.marisqueria3.model.Ingrediente;
import com.IS.marisqueria3.persistence.exceptions.IllegalOrphanException;
import com.IS.marisqueria3.persistence.exceptions.NonexistentEntityException;
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
    
    public CategoriaCartaJpaController() {
        emf= Persistence.createEntityManagerFactory("Marisqueria3TPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CategoriaCarta categoriaCarta) {
        if (categoriaCarta.getIngredienteList() == null) {
            categoriaCarta.setIngredienteList(new ArrayList<Ingrediente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ingrediente> attachedIngredienteList = new ArrayList<Ingrediente>();
            for (Ingrediente ingredienteListIngredienteToAttach : categoriaCarta.getIngredienteList()) {
                ingredienteListIngredienteToAttach = em.getReference(ingredienteListIngredienteToAttach.getClass(), ingredienteListIngredienteToAttach.getCodigoIngrediente());
                attachedIngredienteList.add(ingredienteListIngredienteToAttach);
            }
            categoriaCarta.setIngredienteList(attachedIngredienteList);
            em.persist(categoriaCarta);
            for (Ingrediente ingredienteListIngrediente : categoriaCarta.getIngredienteList()) {
                CategoriaCarta oldCategoriaIdOfIngredienteListIngrediente = ingredienteListIngrediente.getCategoriaId();
                ingredienteListIngrediente.setCategoriaId(categoriaCarta);
                ingredienteListIngrediente = em.merge(ingredienteListIngrediente);
                if (oldCategoriaIdOfIngredienteListIngrediente != null) {
                    oldCategoriaIdOfIngredienteListIngrediente.getIngredienteList().remove(ingredienteListIngrediente);
                    oldCategoriaIdOfIngredienteListIngrediente = em.merge(oldCategoriaIdOfIngredienteListIngrediente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CategoriaCarta categoriaCarta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriaCarta persistentCategoriaCarta = em.find(CategoriaCarta.class, categoriaCarta.getIdCategoria());
            List<Ingrediente> ingredienteListOld = persistentCategoriaCarta.getIngredienteList();
            List<Ingrediente> ingredienteListNew = categoriaCarta.getIngredienteList();
            List<String> illegalOrphanMessages = null;
            for (Ingrediente ingredienteListOldIngrediente : ingredienteListOld) {
                if (!ingredienteListNew.contains(ingredienteListOldIngrediente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ingrediente " + ingredienteListOldIngrediente + " since its categoriaId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Ingrediente> attachedIngredienteListNew = new ArrayList<Ingrediente>();
            for (Ingrediente ingredienteListNewIngredienteToAttach : ingredienteListNew) {
                ingredienteListNewIngredienteToAttach = em.getReference(ingredienteListNewIngredienteToAttach.getClass(), ingredienteListNewIngredienteToAttach.getCodigoIngrediente());
                attachedIngredienteListNew.add(ingredienteListNewIngredienteToAttach);
            }
            ingredienteListNew = attachedIngredienteListNew;
            categoriaCarta.setIngredienteList(ingredienteListNew);
            categoriaCarta = em.merge(categoriaCarta);
            for (Ingrediente ingredienteListNewIngrediente : ingredienteListNew) {
                if (!ingredienteListOld.contains(ingredienteListNewIngrediente)) {
                    CategoriaCarta oldCategoriaIdOfIngredienteListNewIngrediente = ingredienteListNewIngrediente.getCategoriaId();
                    ingredienteListNewIngrediente.setCategoriaId(categoriaCarta);
                    ingredienteListNewIngrediente = em.merge(ingredienteListNewIngrediente);
                    if (oldCategoriaIdOfIngredienteListNewIngrediente != null && !oldCategoriaIdOfIngredienteListNewIngrediente.equals(categoriaCarta)) {
                        oldCategoriaIdOfIngredienteListNewIngrediente.getIngredienteList().remove(ingredienteListNewIngrediente);
                        oldCategoriaIdOfIngredienteListNewIngrediente = em.merge(oldCategoriaIdOfIngredienteListNewIngrediente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categoriaCarta.getIdCategoria();
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriaCarta categoriaCarta;
            try {
                categoriaCarta = em.getReference(CategoriaCarta.class, id);
                categoriaCarta.getIdCategoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaCarta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ingrediente> ingredienteListOrphanCheck = categoriaCarta.getIngredienteList();
            for (Ingrediente ingredienteListOrphanCheckIngrediente : ingredienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CategoriaCarta (" + categoriaCarta + ") cannot be destroyed since the Ingrediente " + ingredienteListOrphanCheckIngrediente + " in its ingredienteList field has a non-nullable categoriaId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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

    public CategoriaCarta findCategoriaCarta(Integer id) {
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
