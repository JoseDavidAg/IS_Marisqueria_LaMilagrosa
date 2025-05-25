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
import com.IS.marisqueria3.model.OrdenCompra;
import java.util.ArrayList;
import java.util.List;
import com.IS.marisqueria3.model.Ingrediente;
import com.IS.marisqueria3.model.Proveedor;
import com.IS.marisqueria3.persistence.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ambro
 */
public class ProveedorJpaController implements Serializable {

    public ProveedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public ProveedorJpaController(){
        emf= Persistence.createEntityManagerFactory("Marisqueria3TPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedor proveedor) {
        if (proveedor.getOrdenCompraList() == null) {
            proveedor.setOrdenCompraList(new ArrayList<OrdenCompra>());
        }
        if (proveedor.getIngredienteList() == null) {
            proveedor.setIngredienteList(new ArrayList<Ingrediente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<OrdenCompra> attachedOrdenCompraList = new ArrayList<OrdenCompra>();
            for (OrdenCompra ordenCompraListOrdenCompraToAttach : proveedor.getOrdenCompraList()) {
                ordenCompraListOrdenCompraToAttach = em.getReference(ordenCompraListOrdenCompraToAttach.getClass(), ordenCompraListOrdenCompraToAttach.getNumeroOrden());
                attachedOrdenCompraList.add(ordenCompraListOrdenCompraToAttach);
            }
            proveedor.setOrdenCompraList(attachedOrdenCompraList);
            List<Ingrediente> attachedIngredienteList = new ArrayList<Ingrediente>();
            for (Ingrediente ingredienteListIngredienteToAttach : proveedor.getIngredienteList()) {
                ingredienteListIngredienteToAttach = em.getReference(ingredienteListIngredienteToAttach.getClass(), ingredienteListIngredienteToAttach.getIngredienteId());
                attachedIngredienteList.add(ingredienteListIngredienteToAttach);
            }
            proveedor.setIngredienteList(attachedIngredienteList);
            em.persist(proveedor);
            for (OrdenCompra ordenCompraListOrdenCompra : proveedor.getOrdenCompraList()) {
                Proveedor oldProveedorIdOfOrdenCompraListOrdenCompra = ordenCompraListOrdenCompra.getProveedorId();
                ordenCompraListOrdenCompra.setProveedorId(proveedor);
                ordenCompraListOrdenCompra = em.merge(ordenCompraListOrdenCompra);
                if (oldProveedorIdOfOrdenCompraListOrdenCompra != null) {
                    oldProveedorIdOfOrdenCompraListOrdenCompra.getOrdenCompraList().remove(ordenCompraListOrdenCompra);
                    oldProveedorIdOfOrdenCompraListOrdenCompra = em.merge(oldProveedorIdOfOrdenCompraListOrdenCompra);
                }
            }
            for (Ingrediente ingredienteListIngrediente : proveedor.getIngredienteList()) {
                Proveedor oldProveedorIdOfIngredienteListIngrediente = ingredienteListIngrediente.getProveedorId();
                ingredienteListIngrediente.setProveedorId(proveedor);
                ingredienteListIngrediente = em.merge(ingredienteListIngrediente);
                if (oldProveedorIdOfIngredienteListIngrediente != null) {
                    oldProveedorIdOfIngredienteListIngrediente.getIngredienteList().remove(ingredienteListIngrediente);
                    oldProveedorIdOfIngredienteListIngrediente = em.merge(oldProveedorIdOfIngredienteListIngrediente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedor proveedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor persistentProveedor = em.find(Proveedor.class, proveedor.getIdProveedor());
            List<OrdenCompra> ordenCompraListOld = persistentProveedor.getOrdenCompraList();
            List<OrdenCompra> ordenCompraListNew = proveedor.getOrdenCompraList();
            List<Ingrediente> ingredienteListOld = persistentProveedor.getIngredienteList();
            List<Ingrediente> ingredienteListNew = proveedor.getIngredienteList();
            List<OrdenCompra> attachedOrdenCompraListNew = new ArrayList<OrdenCompra>();
            for (OrdenCompra ordenCompraListNewOrdenCompraToAttach : ordenCompraListNew) {
                ordenCompraListNewOrdenCompraToAttach = em.getReference(ordenCompraListNewOrdenCompraToAttach.getClass(), ordenCompraListNewOrdenCompraToAttach.getNumeroOrden());
                attachedOrdenCompraListNew.add(ordenCompraListNewOrdenCompraToAttach);
            }
            ordenCompraListNew = attachedOrdenCompraListNew;
            proveedor.setOrdenCompraList(ordenCompraListNew);
            List<Ingrediente> attachedIngredienteListNew = new ArrayList<Ingrediente>();
            for (Ingrediente ingredienteListNewIngredienteToAttach : ingredienteListNew) {
                ingredienteListNewIngredienteToAttach = em.getReference(ingredienteListNewIngredienteToAttach.getClass(), ingredienteListNewIngredienteToAttach.getIngredienteId());
                attachedIngredienteListNew.add(ingredienteListNewIngredienteToAttach);
            }
            ingredienteListNew = attachedIngredienteListNew;
            proveedor.setIngredienteList(ingredienteListNew);
            proveedor = em.merge(proveedor);
            for (OrdenCompra ordenCompraListOldOrdenCompra : ordenCompraListOld) {
                if (!ordenCompraListNew.contains(ordenCompraListOldOrdenCompra)) {
                    ordenCompraListOldOrdenCompra.setProveedorId(null);
                    ordenCompraListOldOrdenCompra = em.merge(ordenCompraListOldOrdenCompra);
                }
            }
            for (OrdenCompra ordenCompraListNewOrdenCompra : ordenCompraListNew) {
                if (!ordenCompraListOld.contains(ordenCompraListNewOrdenCompra)) {
                    Proveedor oldProveedorIdOfOrdenCompraListNewOrdenCompra = ordenCompraListNewOrdenCompra.getProveedorId();
                    ordenCompraListNewOrdenCompra.setProveedorId(proveedor);
                    ordenCompraListNewOrdenCompra = em.merge(ordenCompraListNewOrdenCompra);
                    if (oldProveedorIdOfOrdenCompraListNewOrdenCompra != null && !oldProveedorIdOfOrdenCompraListNewOrdenCompra.equals(proveedor)) {
                        oldProveedorIdOfOrdenCompraListNewOrdenCompra.getOrdenCompraList().remove(ordenCompraListNewOrdenCompra);
                        oldProveedorIdOfOrdenCompraListNewOrdenCompra = em.merge(oldProveedorIdOfOrdenCompraListNewOrdenCompra);
                    }
                }
            }
            for (Ingrediente ingredienteListOldIngrediente : ingredienteListOld) {
                if (!ingredienteListNew.contains(ingredienteListOldIngrediente)) {
                    ingredienteListOldIngrediente.setProveedorId(null);
                    ingredienteListOldIngrediente = em.merge(ingredienteListOldIngrediente);
                }
            }
            for (Ingrediente ingredienteListNewIngrediente : ingredienteListNew) {
                if (!ingredienteListOld.contains(ingredienteListNewIngrediente)) {
                    Proveedor oldProveedorIdOfIngredienteListNewIngrediente = ingredienteListNewIngrediente.getProveedorId();
                    ingredienteListNewIngrediente.setProveedorId(proveedor);
                    ingredienteListNewIngrediente = em.merge(ingredienteListNewIngrediente);
                    if (oldProveedorIdOfIngredienteListNewIngrediente != null && !oldProveedorIdOfIngredienteListNewIngrediente.equals(proveedor)) {
                        oldProveedorIdOfIngredienteListNewIngrediente.getIngredienteList().remove(ingredienteListNewIngrediente);
                        oldProveedorIdOfIngredienteListNewIngrediente = em.merge(oldProveedorIdOfIngredienteListNewIngrediente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proveedor.getIdProveedor();
                if (findProveedor(id) == null) {
                    throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.");
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
            Proveedor proveedor;
            try {
                proveedor = em.getReference(Proveedor.class, id);
                proveedor.getIdProveedor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.", enfe);
            }
            List<OrdenCompra> ordenCompraList = proveedor.getOrdenCompraList();
            for (OrdenCompra ordenCompraListOrdenCompra : ordenCompraList) {
                ordenCompraListOrdenCompra.setProveedorId(null);
                ordenCompraListOrdenCompra = em.merge(ordenCompraListOrdenCompra);
            }
            List<Ingrediente> ingredienteList = proveedor.getIngredienteList();
            for (Ingrediente ingredienteListIngrediente : ingredienteList) {
                ingredienteListIngrediente.setProveedorId(null);
                ingredienteListIngrediente = em.merge(ingredienteListIngrediente);
            }
            em.remove(proveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proveedor> findProveedorEntities() {
        return findProveedorEntities(true, -1, -1);
    }

    public List<Proveedor> findProveedorEntities(int maxResults, int firstResult) {
        return findProveedorEntities(false, maxResults, firstResult);
    }

    private List<Proveedor> findProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedor.class));
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

    public Proveedor findProveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedor> rt = cq.from(Proveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
