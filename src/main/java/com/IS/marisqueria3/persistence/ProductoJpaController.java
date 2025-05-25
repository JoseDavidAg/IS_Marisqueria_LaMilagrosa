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
import com.IS.marisqueria3.model.CategoriaCarta;
import com.IS.marisqueria3.model.ItemPedido;
import com.IS.marisqueria3.model.Producto;
import java.util.ArrayList;
import java.util.List;
import com.IS.marisqueria3.model.ProductoIngrediente;
import com.IS.marisqueria3.persistence.exceptions.IllegalOrphanException;
import com.IS.marisqueria3.persistence.exceptions.NonexistentEntityException;
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
        emf= Persistence.createEntityManagerFactory("Marisqueria3TPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getItemPedidoList() == null) {
            producto.setItemPedidoList(new ArrayList<ItemPedido>());
        }
        if (producto.getProductoIngredienteList() == null) {
            producto.setProductoIngredienteList(new ArrayList<ProductoIngrediente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriaCarta categoriaId = producto.getCategoriaId();
            if (categoriaId != null) {
                categoriaId = em.getReference(categoriaId.getClass(), categoriaId.getIdCategoria());
                producto.setCategoriaId(categoriaId);
            }
            List<ItemPedido> attachedItemPedidoList = new ArrayList<ItemPedido>();
            for (ItemPedido itemPedidoListItemPedidoToAttach : producto.getItemPedidoList()) {
                itemPedidoListItemPedidoToAttach = em.getReference(itemPedidoListItemPedidoToAttach.getClass(), itemPedidoListItemPedidoToAttach.getItemPedidoPK());
                attachedItemPedidoList.add(itemPedidoListItemPedidoToAttach);
            }
            producto.setItemPedidoList(attachedItemPedidoList);
            List<ProductoIngrediente> attachedProductoIngredienteList = new ArrayList<ProductoIngrediente>();
            for (ProductoIngrediente productoIngredienteListProductoIngredienteToAttach : producto.getProductoIngredienteList()) {
                productoIngredienteListProductoIngredienteToAttach = em.getReference(productoIngredienteListProductoIngredienteToAttach.getClass(), productoIngredienteListProductoIngredienteToAttach.getProductoIngredientePK());
                attachedProductoIngredienteList.add(productoIngredienteListProductoIngredienteToAttach);
            }
            producto.setProductoIngredienteList(attachedProductoIngredienteList);
            em.persist(producto);
            if (categoriaId != null) {
                categoriaId.getProductoList().add(producto);
                categoriaId = em.merge(categoriaId);
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
            for (ProductoIngrediente productoIngredienteListProductoIngrediente : producto.getProductoIngredienteList()) {
                Producto oldProductoOfProductoIngredienteListProductoIngrediente = productoIngredienteListProductoIngrediente.getProducto();
                productoIngredienteListProductoIngrediente.setProducto(producto);
                productoIngredienteListProductoIngrediente = em.merge(productoIngredienteListProductoIngrediente);
                if (oldProductoOfProductoIngredienteListProductoIngrediente != null) {
                    oldProductoOfProductoIngredienteListProductoIngrediente.getProductoIngredienteList().remove(productoIngredienteListProductoIngrediente);
                    oldProductoOfProductoIngredienteListProductoIngrediente = em.merge(oldProductoOfProductoIngredienteListProductoIngrediente);
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
            CategoriaCarta categoriaIdOld = persistentProducto.getCategoriaId();
            CategoriaCarta categoriaIdNew = producto.getCategoriaId();
            List<ItemPedido> itemPedidoListOld = persistentProducto.getItemPedidoList();
            List<ItemPedido> itemPedidoListNew = producto.getItemPedidoList();
            List<ProductoIngrediente> productoIngredienteListOld = persistentProducto.getProductoIngredienteList();
            List<ProductoIngrediente> productoIngredienteListNew = producto.getProductoIngredienteList();
            List<String> illegalOrphanMessages = null;
            for (ItemPedido itemPedidoListOldItemPedido : itemPedidoListOld) {
                if (!itemPedidoListNew.contains(itemPedidoListOldItemPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ItemPedido " + itemPedidoListOldItemPedido + " since its producto field is not nullable.");
                }
            }
            for (ProductoIngrediente productoIngredienteListOldProductoIngrediente : productoIngredienteListOld) {
                if (!productoIngredienteListNew.contains(productoIngredienteListOldProductoIngrediente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductoIngrediente " + productoIngredienteListOldProductoIngrediente + " since its producto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (categoriaIdNew != null) {
                categoriaIdNew = em.getReference(categoriaIdNew.getClass(), categoriaIdNew.getIdCategoria());
                producto.setCategoriaId(categoriaIdNew);
            }
            List<ItemPedido> attachedItemPedidoListNew = new ArrayList<ItemPedido>();
            for (ItemPedido itemPedidoListNewItemPedidoToAttach : itemPedidoListNew) {
                itemPedidoListNewItemPedidoToAttach = em.getReference(itemPedidoListNewItemPedidoToAttach.getClass(), itemPedidoListNewItemPedidoToAttach.getItemPedidoPK());
                attachedItemPedidoListNew.add(itemPedidoListNewItemPedidoToAttach);
            }
            itemPedidoListNew = attachedItemPedidoListNew;
            producto.setItemPedidoList(itemPedidoListNew);
            List<ProductoIngrediente> attachedProductoIngredienteListNew = new ArrayList<ProductoIngrediente>();
            for (ProductoIngrediente productoIngredienteListNewProductoIngredienteToAttach : productoIngredienteListNew) {
                productoIngredienteListNewProductoIngredienteToAttach = em.getReference(productoIngredienteListNewProductoIngredienteToAttach.getClass(), productoIngredienteListNewProductoIngredienteToAttach.getProductoIngredientePK());
                attachedProductoIngredienteListNew.add(productoIngredienteListNewProductoIngredienteToAttach);
            }
            productoIngredienteListNew = attachedProductoIngredienteListNew;
            producto.setProductoIngredienteList(productoIngredienteListNew);
            producto = em.merge(producto);
            if (categoriaIdOld != null && !categoriaIdOld.equals(categoriaIdNew)) {
                categoriaIdOld.getProductoList().remove(producto);
                categoriaIdOld = em.merge(categoriaIdOld);
            }
            if (categoriaIdNew != null && !categoriaIdNew.equals(categoriaIdOld)) {
                categoriaIdNew.getProductoList().add(producto);
                categoriaIdNew = em.merge(categoriaIdNew);
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
            for (ProductoIngrediente productoIngredienteListNewProductoIngrediente : productoIngredienteListNew) {
                if (!productoIngredienteListOld.contains(productoIngredienteListNewProductoIngrediente)) {
                    Producto oldProductoOfProductoIngredienteListNewProductoIngrediente = productoIngredienteListNewProductoIngrediente.getProducto();
                    productoIngredienteListNewProductoIngrediente.setProducto(producto);
                    productoIngredienteListNewProductoIngrediente = em.merge(productoIngredienteListNewProductoIngrediente);
                    if (oldProductoOfProductoIngredienteListNewProductoIngrediente != null && !oldProductoOfProductoIngredienteListNewProductoIngrediente.equals(producto)) {
                        oldProductoOfProductoIngredienteListNewProductoIngrediente.getProductoIngredienteList().remove(productoIngredienteListNewProductoIngrediente);
                        oldProductoOfProductoIngredienteListNewProductoIngrediente = em.merge(oldProductoOfProductoIngredienteListNewProductoIngrediente);
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
            List<ProductoIngrediente> productoIngredienteListOrphanCheck = producto.getProductoIngredienteList();
            for (ProductoIngrediente productoIngredienteListOrphanCheckProductoIngrediente : productoIngredienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the ProductoIngrediente " + productoIngredienteListOrphanCheckProductoIngrediente + " in its productoIngredienteList field has a non-nullable producto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            CategoriaCarta categoriaId = producto.getCategoriaId();
            if (categoriaId != null) {
                categoriaId.getProductoList().remove(producto);
                categoriaId = em.merge(categoriaId);
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
