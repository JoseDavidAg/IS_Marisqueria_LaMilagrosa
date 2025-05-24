/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.persistence;

import com.IS.marisqueria3.controller.exceptions.IllegalOrphanException;
import com.IS.marisqueria3.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.IS.marisqueria3.model.CategoriaCarta;
import com.IS.marisqueria3.model.Ingrediente;
import java.util.ArrayList;
import java.util.List;
import com.IS.marisqueria3.model.ItemPedido;
import com.IS.marisqueria3.model.Producto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ambro
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public ProductoJpaController(){
        emf=Persistence.createEntityManagerFactory("MarisqueriaUP");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getIngredienteList() == null) {
            producto.setIngredienteList(new ArrayList<Ingrediente>());
        }
        if (producto.getItemPedidoList() == null) {
            producto.setItemPedidoList(new ArrayList<ItemPedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriaCarta categoriaNombre = producto.getCategoriaNombre();
            if (categoriaNombre != null) {
                categoriaNombre = em.getReference(categoriaNombre.getClass(), categoriaNombre.getNombre());
                producto.setCategoriaNombre(categoriaNombre);
            }
            List<Ingrediente> attachedIngredienteList = new ArrayList<Ingrediente>();
            for (Ingrediente ingredienteListIngredienteToAttach : producto.getIngredienteList()) {
                ingredienteListIngredienteToAttach = em.getReference(ingredienteListIngredienteToAttach.getClass(), ingredienteListIngredienteToAttach.getCodigoProducto());
                attachedIngredienteList.add(ingredienteListIngredienteToAttach);
            }
            producto.setIngredienteList(attachedIngredienteList);
            List<ItemPedido> attachedItemPedidoList = new ArrayList<ItemPedido>();
            for (ItemPedido itemPedidoListItemPedidoToAttach : producto.getItemPedidoList()) {
                itemPedidoListItemPedidoToAttach = em.getReference(itemPedidoListItemPedidoToAttach.getClass(), itemPedidoListItemPedidoToAttach.getItemPedidoPK());
                attachedItemPedidoList.add(itemPedidoListItemPedidoToAttach);
            }
            producto.setItemPedidoList(attachedItemPedidoList);
            em.persist(producto);
            if (categoriaNombre != null) {
                categoriaNombre.getProductoList().add(producto);
                categoriaNombre = em.merge(categoriaNombre);
            }
            for (Ingrediente ingredienteListIngrediente : producto.getIngredienteList()) {
                ingredienteListIngrediente.getProductoList().add(producto);
                ingredienteListIngrediente = em.merge(ingredienteListIngrediente);
            }
            for (ItemPedido itemPedidoListItemPedido : producto.getItemPedidoList()) {
                Producto oldProductoOfItemPedidoListItemPedido = itemPedidoListItemPedido.getProducto();
                itemPedidoListItemPedido.setProducto(producto);
                itemPedidoListItemPedido = em.merge(itemPedidoListItemPedido);
                if (oldProductoOfItemPedidoListItemPedido != null) {
                    oldProductoOfItemPedidoListItemPedido.getItemPedidoList().remove(itemPedidoListItemPedido);
                    oldProductoOfItemPedidoListItemPedido = em.merge(oldProductoOfItemPedidoListItemPedido);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getIdPlatillo());
            CategoriaCarta categoriaNombreOld = persistentProducto.getCategoriaNombre();
            CategoriaCarta categoriaNombreNew = producto.getCategoriaNombre();
            List<Ingrediente> ingredienteListOld = persistentProducto.getIngredienteList();
            List<Ingrediente> ingredienteListNew = producto.getIngredienteList();
            List<ItemPedido> itemPedidoListOld = persistentProducto.getItemPedidoList();
            List<ItemPedido> itemPedidoListNew = producto.getItemPedidoList();
            List<String> illegalOrphanMessages = null;
            for (ItemPedido itemPedidoListOldItemPedido : itemPedidoListOld) {
                if (!itemPedidoListNew.contains(itemPedidoListOldItemPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ItemPedido " + itemPedidoListOldItemPedido + " since its producto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (categoriaNombreNew != null) {
                categoriaNombreNew = em.getReference(categoriaNombreNew.getClass(), categoriaNombreNew.getNombre());
                producto.setCategoriaNombre(categoriaNombreNew);
            }
            List<Ingrediente> attachedIngredienteListNew = new ArrayList<Ingrediente>();
            for (Ingrediente ingredienteListNewIngredienteToAttach : ingredienteListNew) {
                ingredienteListNewIngredienteToAttach = em.getReference(ingredienteListNewIngredienteToAttach.getClass(), ingredienteListNewIngredienteToAttach.getCodigoProducto());
                attachedIngredienteListNew.add(ingredienteListNewIngredienteToAttach);
            }
            ingredienteListNew = attachedIngredienteListNew;
            producto.setIngredienteList(ingredienteListNew);
            List<ItemPedido> attachedItemPedidoListNew = new ArrayList<ItemPedido>();
            for (ItemPedido itemPedidoListNewItemPedidoToAttach : itemPedidoListNew) {
                itemPedidoListNewItemPedidoToAttach = em.getReference(itemPedidoListNewItemPedidoToAttach.getClass(), itemPedidoListNewItemPedidoToAttach.getItemPedidoPK());
                attachedItemPedidoListNew.add(itemPedidoListNewItemPedidoToAttach);
            }
            itemPedidoListNew = attachedItemPedidoListNew;
            producto.setItemPedidoList(itemPedidoListNew);
            producto = em.merge(producto);
            if (categoriaNombreOld != null && !categoriaNombreOld.equals(categoriaNombreNew)) {
                categoriaNombreOld.getProductoList().remove(producto);
                categoriaNombreOld = em.merge(categoriaNombreOld);
            }
            if (categoriaNombreNew != null && !categoriaNombreNew.equals(categoriaNombreOld)) {
                categoriaNombreNew.getProductoList().add(producto);
                categoriaNombreNew = em.merge(categoriaNombreNew);
            }
            for (Ingrediente ingredienteListOldIngrediente : ingredienteListOld) {
                if (!ingredienteListNew.contains(ingredienteListOldIngrediente)) {
                    ingredienteListOldIngrediente.getProductoList().remove(producto);
                    ingredienteListOldIngrediente = em.merge(ingredienteListOldIngrediente);
                }
            }
            for (Ingrediente ingredienteListNewIngrediente : ingredienteListNew) {
                if (!ingredienteListOld.contains(ingredienteListNewIngrediente)) {
                    ingredienteListNewIngrediente.getProductoList().add(producto);
                    ingredienteListNewIngrediente = em.merge(ingredienteListNewIngrediente);
                }
            }
            for (ItemPedido itemPedidoListNewItemPedido : itemPedidoListNew) {
                if (!itemPedidoListOld.contains(itemPedidoListNewItemPedido)) {
                    Producto oldProductoOfItemPedidoListNewItemPedido = itemPedidoListNewItemPedido.getProducto();
                    itemPedidoListNewItemPedido.setProducto(producto);
                    itemPedidoListNewItemPedido = em.merge(itemPedidoListNewItemPedido);
                    if (oldProductoOfItemPedidoListNewItemPedido != null && !oldProductoOfItemPedidoListNewItemPedido.equals(producto)) {
                        oldProductoOfItemPedidoListNewItemPedido.getItemPedidoList().remove(itemPedidoListNewItemPedido);
                        oldProductoOfItemPedidoListNewItemPedido = em.merge(oldProductoOfItemPedidoListNewItemPedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getIdPlatillo();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getIdPlatillo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ItemPedido> itemPedidoListOrphanCheck = producto.getItemPedidoList();
            for (ItemPedido itemPedidoListOrphanCheckItemPedido : itemPedidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the ItemPedido " + itemPedidoListOrphanCheckItemPedido + " in its itemPedidoList field has a non-nullable producto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            CategoriaCarta categoriaNombre = producto.getCategoriaNombre();
            if (categoriaNombre != null) {
                categoriaNombre.getProductoList().remove(producto);
                categoriaNombre = em.merge(categoriaNombre);
            }
            List<Ingrediente> ingredienteList = producto.getIngredienteList();
            for (Ingrediente ingredienteListIngrediente : ingredienteList) {
                ingredienteListIngrediente.getProductoList().remove(producto);
                ingredienteListIngrediente = em.merge(ingredienteListIngrediente);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
