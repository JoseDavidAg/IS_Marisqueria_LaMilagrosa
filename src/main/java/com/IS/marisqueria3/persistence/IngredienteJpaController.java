/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.persistence;

import com.IS.marisqueria3.controller.exceptions.NonexistentEntityException;
import com.IS.marisqueria3.model.Ingrediente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.IS.marisqueria3.model.Proveedor;
import com.IS.marisqueria3.model.Producto;
import java.util.ArrayList;
import java.util.List;
import com.IS.marisqueria3.model.ItemOrdenCompra;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ambro
 */
public class IngredienteJpaController implements Serializable {

    public IngredienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public IngredienteJpaController(){
        Persistence.createEntityManagerFactory("MarisqueriaUP");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingrediente ingrediente) {
        if (ingrediente.getProductoList() == null) {
            ingrediente.setProductoList(new ArrayList<Producto>());
        }
        if (ingrediente.getItemOrdenCompraList() == null) {
            ingrediente.setItemOrdenCompraList(new ArrayList<ItemOrdenCompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor proveedorId = ingrediente.getProveedorId();
            if (proveedorId != null) {
                proveedorId = em.getReference(proveedorId.getClass(), proveedorId.getIdProveedor());
                ingrediente.setProveedorId(proveedorId);
            }
            List<Producto> attachedProductoList = new ArrayList<Producto>();
            for (Producto productoListProductoToAttach : ingrediente.getProductoList()) {
                productoListProductoToAttach = em.getReference(productoListProductoToAttach.getClass(), productoListProductoToAttach.getIdPlatillo());
                attachedProductoList.add(productoListProductoToAttach);
            }
            ingrediente.setProductoList(attachedProductoList);
            List<ItemOrdenCompra> attachedItemOrdenCompraList = new ArrayList<ItemOrdenCompra>();
            for (ItemOrdenCompra itemOrdenCompraListItemOrdenCompraToAttach : ingrediente.getItemOrdenCompraList()) {
                itemOrdenCompraListItemOrdenCompraToAttach = em.getReference(itemOrdenCompraListItemOrdenCompraToAttach.getClass(), itemOrdenCompraListItemOrdenCompraToAttach.getIdItemOrden());
                attachedItemOrdenCompraList.add(itemOrdenCompraListItemOrdenCompraToAttach);
            }
            ingrediente.setItemOrdenCompraList(attachedItemOrdenCompraList);
            em.persist(ingrediente);
            if (proveedorId != null) {
                proveedorId.getIngredienteList().add(ingrediente);
                proveedorId = em.merge(proveedorId);
            }
            for (Producto productoListProducto : ingrediente.getProductoList()) {
                productoListProducto.getIngredienteList().add(ingrediente);
                productoListProducto = em.merge(productoListProducto);
            }
            for (ItemOrdenCompra itemOrdenCompraListItemOrdenCompra : ingrediente.getItemOrdenCompraList()) {
                Ingrediente oldIngredienteCodigoOfItemOrdenCompraListItemOrdenCompra = itemOrdenCompraListItemOrdenCompra.getIngredienteCodigo();
                itemOrdenCompraListItemOrdenCompra.setIngredienteCodigo(ingrediente);
                itemOrdenCompraListItemOrdenCompra = em.merge(itemOrdenCompraListItemOrdenCompra);
                if (oldIngredienteCodigoOfItemOrdenCompraListItemOrdenCompra != null) {
                    oldIngredienteCodigoOfItemOrdenCompraListItemOrdenCompra.getItemOrdenCompraList().remove(itemOrdenCompraListItemOrdenCompra);
                    oldIngredienteCodigoOfItemOrdenCompraListItemOrdenCompra = em.merge(oldIngredienteCodigoOfItemOrdenCompraListItemOrdenCompra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingrediente ingrediente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingrediente persistentIngrediente = em.find(Ingrediente.class, ingrediente.getCodigoProducto());
            Proveedor proveedorIdOld = persistentIngrediente.getProveedorId();
            Proveedor proveedorIdNew = ingrediente.getProveedorId();
            List<Producto> productoListOld = persistentIngrediente.getProductoList();
            List<Producto> productoListNew = ingrediente.getProductoList();
            List<ItemOrdenCompra> itemOrdenCompraListOld = persistentIngrediente.getItemOrdenCompraList();
            List<ItemOrdenCompra> itemOrdenCompraListNew = ingrediente.getItemOrdenCompraList();
            if (proveedorIdNew != null) {
                proveedorIdNew = em.getReference(proveedorIdNew.getClass(), proveedorIdNew.getIdProveedor());
                ingrediente.setProveedorId(proveedorIdNew);
            }
            List<Producto> attachedProductoListNew = new ArrayList<Producto>();
            for (Producto productoListNewProductoToAttach : productoListNew) {
                productoListNewProductoToAttach = em.getReference(productoListNewProductoToAttach.getClass(), productoListNewProductoToAttach.getIdPlatillo());
                attachedProductoListNew.add(productoListNewProductoToAttach);
            }
            productoListNew = attachedProductoListNew;
            ingrediente.setProductoList(productoListNew);
            List<ItemOrdenCompra> attachedItemOrdenCompraListNew = new ArrayList<ItemOrdenCompra>();
            for (ItemOrdenCompra itemOrdenCompraListNewItemOrdenCompraToAttach : itemOrdenCompraListNew) {
                itemOrdenCompraListNewItemOrdenCompraToAttach = em.getReference(itemOrdenCompraListNewItemOrdenCompraToAttach.getClass(), itemOrdenCompraListNewItemOrdenCompraToAttach.getIdItemOrden());
                attachedItemOrdenCompraListNew.add(itemOrdenCompraListNewItemOrdenCompraToAttach);
            }
            itemOrdenCompraListNew = attachedItemOrdenCompraListNew;
            ingrediente.setItemOrdenCompraList(itemOrdenCompraListNew);
            ingrediente = em.merge(ingrediente);
            if (proveedorIdOld != null && !proveedorIdOld.equals(proveedorIdNew)) {
                proveedorIdOld.getIngredienteList().remove(ingrediente);
                proveedorIdOld = em.merge(proveedorIdOld);
            }
            if (proveedorIdNew != null && !proveedorIdNew.equals(proveedorIdOld)) {
                proveedorIdNew.getIngredienteList().add(ingrediente);
                proveedorIdNew = em.merge(proveedorIdNew);
            }
            for (Producto productoListOldProducto : productoListOld) {
                if (!productoListNew.contains(productoListOldProducto)) {
                    productoListOldProducto.getIngredienteList().remove(ingrediente);
                    productoListOldProducto = em.merge(productoListOldProducto);
                }
            }
            for (Producto productoListNewProducto : productoListNew) {
                if (!productoListOld.contains(productoListNewProducto)) {
                    productoListNewProducto.getIngredienteList().add(ingrediente);
                    productoListNewProducto = em.merge(productoListNewProducto);
                }
            }
            for (ItemOrdenCompra itemOrdenCompraListOldItemOrdenCompra : itemOrdenCompraListOld) {
                if (!itemOrdenCompraListNew.contains(itemOrdenCompraListOldItemOrdenCompra)) {
                    itemOrdenCompraListOldItemOrdenCompra.setIngredienteCodigo(null);
                    itemOrdenCompraListOldItemOrdenCompra = em.merge(itemOrdenCompraListOldItemOrdenCompra);
                }
            }
            for (ItemOrdenCompra itemOrdenCompraListNewItemOrdenCompra : itemOrdenCompraListNew) {
                if (!itemOrdenCompraListOld.contains(itemOrdenCompraListNewItemOrdenCompra)) {
                    Ingrediente oldIngredienteCodigoOfItemOrdenCompraListNewItemOrdenCompra = itemOrdenCompraListNewItemOrdenCompra.getIngredienteCodigo();
                    itemOrdenCompraListNewItemOrdenCompra.setIngredienteCodigo(ingrediente);
                    itemOrdenCompraListNewItemOrdenCompra = em.merge(itemOrdenCompraListNewItemOrdenCompra);
                    if (oldIngredienteCodigoOfItemOrdenCompraListNewItemOrdenCompra != null && !oldIngredienteCodigoOfItemOrdenCompraListNewItemOrdenCompra.equals(ingrediente)) {
                        oldIngredienteCodigoOfItemOrdenCompraListNewItemOrdenCompra.getItemOrdenCompraList().remove(itemOrdenCompraListNewItemOrdenCompra);
                        oldIngredienteCodigoOfItemOrdenCompraListNewItemOrdenCompra = em.merge(oldIngredienteCodigoOfItemOrdenCompraListNewItemOrdenCompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ingrediente.getCodigoProducto();
                if (findIngrediente(id) == null) {
                    throw new NonexistentEntityException("The ingrediente with id " + id + " no longer exists.");
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
            Ingrediente ingrediente;
            try {
                ingrediente = em.getReference(Ingrediente.class, id);
                ingrediente.getCodigoProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingrediente with id " + id + " no longer exists.", enfe);
            }
            Proveedor proveedorId = ingrediente.getProveedorId();
            if (proveedorId != null) {
                proveedorId.getIngredienteList().remove(ingrediente);
                proveedorId = em.merge(proveedorId);
            }
            List<Producto> productoList = ingrediente.getProductoList();
            for (Producto productoListProducto : productoList) {
                productoListProducto.getIngredienteList().remove(ingrediente);
                productoListProducto = em.merge(productoListProducto);
            }
            List<ItemOrdenCompra> itemOrdenCompraList = ingrediente.getItemOrdenCompraList();
            for (ItemOrdenCompra itemOrdenCompraListItemOrdenCompra : itemOrdenCompraList) {
                itemOrdenCompraListItemOrdenCompra.setIngredienteCodigo(null);
                itemOrdenCompraListItemOrdenCompra = em.merge(itemOrdenCompraListItemOrdenCompra);
            }
            em.remove(ingrediente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingrediente> findIngredienteEntities() {
        return findIngredienteEntities(true, -1, -1);
    }

    public List<Ingrediente> findIngredienteEntities(int maxResults, int firstResult) {
        return findIngredienteEntities(false, maxResults, firstResult);
    }

    private List<Ingrediente> findIngredienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingrediente.class));
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

    public Ingrediente findIngrediente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingrediente.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngredienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingrediente> rt = cq.from(Ingrediente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
