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
import com.IS.marisqueria3.model.Proveedor;
import com.IS.marisqueria3.model.ItemOrdenCompra;
import com.IS.marisqueria3.model.OrdenCompra;
import com.IS.marisqueria3.persistence.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ambro
 */
public class OrdenCompraJpaController implements Serializable {

    public OrdenCompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrdenCompra ordenCompra) {
        if (ordenCompra.getItemOrdenCompraList() == null) {
            ordenCompra.setItemOrdenCompraList(new ArrayList<ItemOrdenCompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor proveedorId = ordenCompra.getProveedorId();
            if (proveedorId != null) {
                proveedorId = em.getReference(proveedorId.getClass(), proveedorId.getIdProveedor());
                ordenCompra.setProveedorId(proveedorId);
            }
            List<ItemOrdenCompra> attachedItemOrdenCompraList = new ArrayList<ItemOrdenCompra>();
            for (ItemOrdenCompra itemOrdenCompraListItemOrdenCompraToAttach : ordenCompra.getItemOrdenCompraList()) {
                itemOrdenCompraListItemOrdenCompraToAttach = em.getReference(itemOrdenCompraListItemOrdenCompraToAttach.getClass(), itemOrdenCompraListItemOrdenCompraToAttach.getIdItemOrden());
                attachedItemOrdenCompraList.add(itemOrdenCompraListItemOrdenCompraToAttach);
            }
            ordenCompra.setItemOrdenCompraList(attachedItemOrdenCompraList);
            em.persist(ordenCompra);
            if (proveedorId != null) {
                proveedorId.getOrdenCompraList().add(ordenCompra);
                proveedorId = em.merge(proveedorId);
            }
            for (ItemOrdenCompra itemOrdenCompraListItemOrdenCompra : ordenCompra.getItemOrdenCompraList()) {
                OrdenCompra oldOrdenCompraNumeroOfItemOrdenCompraListItemOrdenCompra = itemOrdenCompraListItemOrdenCompra.getOrdenCompraNumero();
                itemOrdenCompraListItemOrdenCompra.setOrdenCompraNumero(ordenCompra);
                itemOrdenCompraListItemOrdenCompra = em.merge(itemOrdenCompraListItemOrdenCompra);
                if (oldOrdenCompraNumeroOfItemOrdenCompraListItemOrdenCompra != null) {
                    oldOrdenCompraNumeroOfItemOrdenCompraListItemOrdenCompra.getItemOrdenCompraList().remove(itemOrdenCompraListItemOrdenCompra);
                    oldOrdenCompraNumeroOfItemOrdenCompraListItemOrdenCompra = em.merge(oldOrdenCompraNumeroOfItemOrdenCompraListItemOrdenCompra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrdenCompra ordenCompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrdenCompra persistentOrdenCompra = em.find(OrdenCompra.class, ordenCompra.getNumeroOrden());
            Proveedor proveedorIdOld = persistentOrdenCompra.getProveedorId();
            Proveedor proveedorIdNew = ordenCompra.getProveedorId();
            List<ItemOrdenCompra> itemOrdenCompraListOld = persistentOrdenCompra.getItemOrdenCompraList();
            List<ItemOrdenCompra> itemOrdenCompraListNew = ordenCompra.getItemOrdenCompraList();
            if (proveedorIdNew != null) {
                proveedorIdNew = em.getReference(proveedorIdNew.getClass(), proveedorIdNew.getIdProveedor());
                ordenCompra.setProveedorId(proveedorIdNew);
            }
            List<ItemOrdenCompra> attachedItemOrdenCompraListNew = new ArrayList<ItemOrdenCompra>();
            for (ItemOrdenCompra itemOrdenCompraListNewItemOrdenCompraToAttach : itemOrdenCompraListNew) {
                itemOrdenCompraListNewItemOrdenCompraToAttach = em.getReference(itemOrdenCompraListNewItemOrdenCompraToAttach.getClass(), itemOrdenCompraListNewItemOrdenCompraToAttach.getIdItemOrden());
                attachedItemOrdenCompraListNew.add(itemOrdenCompraListNewItemOrdenCompraToAttach);
            }
            itemOrdenCompraListNew = attachedItemOrdenCompraListNew;
            ordenCompra.setItemOrdenCompraList(itemOrdenCompraListNew);
            ordenCompra = em.merge(ordenCompra);
            if (proveedorIdOld != null && !proveedorIdOld.equals(proveedorIdNew)) {
                proveedorIdOld.getOrdenCompraList().remove(ordenCompra);
                proveedorIdOld = em.merge(proveedorIdOld);
            }
            if (proveedorIdNew != null && !proveedorIdNew.equals(proveedorIdOld)) {
                proveedorIdNew.getOrdenCompraList().add(ordenCompra);
                proveedorIdNew = em.merge(proveedorIdNew);
            }
            for (ItemOrdenCompra itemOrdenCompraListOldItemOrdenCompra : itemOrdenCompraListOld) {
                if (!itemOrdenCompraListNew.contains(itemOrdenCompraListOldItemOrdenCompra)) {
                    itemOrdenCompraListOldItemOrdenCompra.setOrdenCompraNumero(null);
                    itemOrdenCompraListOldItemOrdenCompra = em.merge(itemOrdenCompraListOldItemOrdenCompra);
                }
            }
            for (ItemOrdenCompra itemOrdenCompraListNewItemOrdenCompra : itemOrdenCompraListNew) {
                if (!itemOrdenCompraListOld.contains(itemOrdenCompraListNewItemOrdenCompra)) {
                    OrdenCompra oldOrdenCompraNumeroOfItemOrdenCompraListNewItemOrdenCompra = itemOrdenCompraListNewItemOrdenCompra.getOrdenCompraNumero();
                    itemOrdenCompraListNewItemOrdenCompra.setOrdenCompraNumero(ordenCompra);
                    itemOrdenCompraListNewItemOrdenCompra = em.merge(itemOrdenCompraListNewItemOrdenCompra);
                    if (oldOrdenCompraNumeroOfItemOrdenCompraListNewItemOrdenCompra != null && !oldOrdenCompraNumeroOfItemOrdenCompraListNewItemOrdenCompra.equals(ordenCompra)) {
                        oldOrdenCompraNumeroOfItemOrdenCompraListNewItemOrdenCompra.getItemOrdenCompraList().remove(itemOrdenCompraListNewItemOrdenCompra);
                        oldOrdenCompraNumeroOfItemOrdenCompraListNewItemOrdenCompra = em.merge(oldOrdenCompraNumeroOfItemOrdenCompraListNewItemOrdenCompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ordenCompra.getNumeroOrden();
                if (findOrdenCompra(id) == null) {
                    throw new NonexistentEntityException("The ordenCompra with id " + id + " no longer exists.");
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
            OrdenCompra ordenCompra;
            try {
                ordenCompra = em.getReference(OrdenCompra.class, id);
                ordenCompra.getNumeroOrden();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ordenCompra with id " + id + " no longer exists.", enfe);
            }
            Proveedor proveedorId = ordenCompra.getProveedorId();
            if (proveedorId != null) {
                proveedorId.getOrdenCompraList().remove(ordenCompra);
                proveedorId = em.merge(proveedorId);
            }
            List<ItemOrdenCompra> itemOrdenCompraList = ordenCompra.getItemOrdenCompraList();
            for (ItemOrdenCompra itemOrdenCompraListItemOrdenCompra : itemOrdenCompraList) {
                itemOrdenCompraListItemOrdenCompra.setOrdenCompraNumero(null);
                itemOrdenCompraListItemOrdenCompra = em.merge(itemOrdenCompraListItemOrdenCompra);
            }
            em.remove(ordenCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrdenCompra> findOrdenCompraEntities() {
        return findOrdenCompraEntities(true, -1, -1);
    }

    public List<OrdenCompra> findOrdenCompraEntities(int maxResults, int firstResult) {
        return findOrdenCompraEntities(false, maxResults, firstResult);
    }

    private List<OrdenCompra> findOrdenCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrdenCompra.class));
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

    public OrdenCompra findOrdenCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrdenCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdenCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrdenCompra> rt = cq.from(OrdenCompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
