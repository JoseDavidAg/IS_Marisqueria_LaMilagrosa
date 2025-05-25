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
import com.IS.marisqueria3.model.Cliente;
import com.IS.marisqueria3.model.Ingrediente;
import com.IS.marisqueria3.model.Ticket;
import java.util.ArrayList;
import java.util.List;
import com.IS.marisqueria3.model.ItemPedido;
import com.IS.marisqueria3.model.Pedido;
import com.IS.marisqueria3.persistence.exceptions.IllegalOrphanException;
import com.IS.marisqueria3.persistence.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author ambro
 */
public class PedidoJpaController implements Serializable {

    public PedidoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public PedidoJpaController(){
        emf= Persistence.createEntityManagerFactory("Marisqueria3TPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pedido pedido) {
        if (pedido.getTicketList() == null) {
            pedido.setTicketList(new ArrayList<Ticket>());
        }
        if (pedido.getItemPedidoList() == null) {
            pedido.setItemPedidoList(new ArrayList<ItemPedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente clienteId = pedido.getClienteId();
            if (clienteId != null) {
                clienteId = em.getReference(clienteId.getClass(), clienteId.getIdCliente());
                pedido.setClienteId(clienteId);
            }
            List<Ticket> attachedTicketList = new ArrayList<Ticket>();
            for (Ticket ticketListTicketToAttach : pedido.getTicketList()) {
                ticketListTicketToAttach = em.getReference(ticketListTicketToAttach.getClass(), ticketListTicketToAttach.getNumeroTicket());
                attachedTicketList.add(ticketListTicketToAttach);
            }
            pedido.setTicketList(attachedTicketList);
            List<ItemPedido> attachedItemPedidoList = new ArrayList<ItemPedido>();
            for (ItemPedido itemPedidoListItemPedidoToAttach : pedido.getItemPedidoList()) {
                itemPedidoListItemPedidoToAttach = em.getReference(itemPedidoListItemPedidoToAttach.getClass(), itemPedidoListItemPedidoToAttach.getItemPedidoPK());
                attachedItemPedidoList.add(itemPedidoListItemPedidoToAttach);
            }
            pedido.setItemPedidoList(attachedItemPedidoList);
            em.persist(pedido);
            if (clienteId != null) {
                clienteId.getPedidoList().add(pedido);
                clienteId = em.merge(clienteId);
            }
            for (Ticket ticketListTicket : pedido.getTicketList()) {
                Pedido oldPedidoNumeroOfTicketListTicket = ticketListTicket.getPedidoNumero();
                ticketListTicket.setPedidoNumero(pedido);
                ticketListTicket = em.merge(ticketListTicket);
                if (oldPedidoNumeroOfTicketListTicket != null) {
                    oldPedidoNumeroOfTicketListTicket.getTicketList().remove(ticketListTicket);
                    oldPedidoNumeroOfTicketListTicket = em.merge(oldPedidoNumeroOfTicketListTicket);
                }
            }
            for (ItemPedido itemPedidoListItemPedido : pedido.getItemPedidoList()) {
                Pedido oldPedidoOfItemPedidoListItemPedido = itemPedidoListItemPedido.getPedido();
                itemPedidoListItemPedido.setPedido(pedido);
                itemPedidoListItemPedido = em.merge(itemPedidoListItemPedido);
                if (oldPedidoOfItemPedidoListItemPedido != null) {
                    oldPedidoOfItemPedidoListItemPedido.getItemPedidoList().remove(itemPedidoListItemPedido);
                    oldPedidoOfItemPedidoListItemPedido = em.merge(oldPedidoOfItemPedidoListItemPedido);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pedido pedido) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido persistentPedido = em.find(Pedido.class, pedido.getNumeroPedido());
            Cliente clienteIdOld = persistentPedido.getClienteId();
            Cliente clienteIdNew = pedido.getClienteId();
            List<Ticket> ticketListOld = persistentPedido.getTicketList();
            List<Ticket> ticketListNew = pedido.getTicketList();
            List<ItemPedido> itemPedidoListOld = persistentPedido.getItemPedidoList();
            List<ItemPedido> itemPedidoListNew = pedido.getItemPedidoList();
            List<String> illegalOrphanMessages = null;
            for (ItemPedido itemPedidoListOldItemPedido : itemPedidoListOld) {
                if (!itemPedidoListNew.contains(itemPedidoListOldItemPedido)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ItemPedido " + itemPedidoListOldItemPedido + " since its pedido field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clienteIdNew != null) {
                clienteIdNew = em.getReference(clienteIdNew.getClass(), clienteIdNew.getIdCliente());
                pedido.setClienteId(clienteIdNew);
            }
            List<Ticket> attachedTicketListNew = new ArrayList<Ticket>();
            for (Ticket ticketListNewTicketToAttach : ticketListNew) {
                ticketListNewTicketToAttach = em.getReference(ticketListNewTicketToAttach.getClass(), ticketListNewTicketToAttach.getNumeroTicket());
                attachedTicketListNew.add(ticketListNewTicketToAttach);
            }
            ticketListNew = attachedTicketListNew;
            pedido.setTicketList(ticketListNew);
            List<ItemPedido> attachedItemPedidoListNew = new ArrayList<ItemPedido>();
            for (ItemPedido itemPedidoListNewItemPedidoToAttach : itemPedidoListNew) {
                itemPedidoListNewItemPedidoToAttach = em.getReference(itemPedidoListNewItemPedidoToAttach.getClass(), itemPedidoListNewItemPedidoToAttach.getItemPedidoPK());
                attachedItemPedidoListNew.add(itemPedidoListNewItemPedidoToAttach);
            }
            itemPedidoListNew = attachedItemPedidoListNew;
            pedido.setItemPedidoList(itemPedidoListNew);
            pedido = em.merge(pedido);
            if (clienteIdOld != null && !clienteIdOld.equals(clienteIdNew)) {
                clienteIdOld.getPedidoList().remove(pedido);
                clienteIdOld = em.merge(clienteIdOld);
            }
            if (clienteIdNew != null && !clienteIdNew.equals(clienteIdOld)) {
                clienteIdNew.getPedidoList().add(pedido);
                clienteIdNew = em.merge(clienteIdNew);
            }
            for (Ticket ticketListOldTicket : ticketListOld) {
                if (!ticketListNew.contains(ticketListOldTicket)) {
                    ticketListOldTicket.setPedidoNumero(null);
                    ticketListOldTicket = em.merge(ticketListOldTicket);
                }
            }
            for (Ticket ticketListNewTicket : ticketListNew) {
                if (!ticketListOld.contains(ticketListNewTicket)) {
                    Pedido oldPedidoNumeroOfTicketListNewTicket = ticketListNewTicket.getPedidoNumero();
                    ticketListNewTicket.setPedidoNumero(pedido);
                    ticketListNewTicket = em.merge(ticketListNewTicket);
                    if (oldPedidoNumeroOfTicketListNewTicket != null && !oldPedidoNumeroOfTicketListNewTicket.equals(pedido)) {
                        oldPedidoNumeroOfTicketListNewTicket.getTicketList().remove(ticketListNewTicket);
                        oldPedidoNumeroOfTicketListNewTicket = em.merge(oldPedidoNumeroOfTicketListNewTicket);
                    }
                }
            }
            for (ItemPedido itemPedidoListNewItemPedido : itemPedidoListNew) {
                if (!itemPedidoListOld.contains(itemPedidoListNewItemPedido)) {
                    Pedido oldPedidoOfItemPedidoListNewItemPedido = itemPedidoListNewItemPedido.getPedido();
                    itemPedidoListNewItemPedido.setPedido(pedido);
                    itemPedidoListNewItemPedido = em.merge(itemPedidoListNewItemPedido);
                    if (oldPedidoOfItemPedidoListNewItemPedido != null && !oldPedidoOfItemPedidoListNewItemPedido.equals(pedido)) {
                        oldPedidoOfItemPedidoListNewItemPedido.getItemPedidoList().remove(itemPedidoListNewItemPedido);
                        oldPedidoOfItemPedidoListNewItemPedido = em.merge(oldPedidoOfItemPedidoListNewItemPedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pedido.getNumeroPedido();
                if (findPedido(id) == null) {
                    throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.");
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
            Pedido pedido;
            try {
                pedido = em.getReference(Pedido.class, id);
                pedido.getNumeroPedido();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pedido with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ItemPedido> itemPedidoListOrphanCheck = pedido.getItemPedidoList();
            for (ItemPedido itemPedidoListOrphanCheckItemPedido : itemPedidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pedido (" + pedido + ") cannot be destroyed since the ItemPedido " + itemPedidoListOrphanCheckItemPedido + " in its itemPedidoList field has a non-nullable pedido field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cliente clienteId = pedido.getClienteId();
            if (clienteId != null) {
                clienteId.getPedidoList().remove(pedido);
                clienteId = em.merge(clienteId);
            }
            List<Ticket> ticketList = pedido.getTicketList();
            for (Ticket ticketListTicket : ticketList) {
                ticketListTicket.setPedidoNumero(null);
                ticketListTicket = em.merge(ticketListTicket);
            }
            em.remove(pedido);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pedido> findPedidoEntities() {
        return findPedidoEntities(true, -1, -1);
    }

    public List<Pedido> findPedidoEntities(int maxResults, int firstResult) {
        return findPedidoEntities(false, maxResults, firstResult);
    }

    private List<Pedido> findPedidoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pedido.class));
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

    public Pedido findPedido(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pedido.class, id);
        } finally {
            em.close();
        }
    }

    public List<Pedido> findPedidoEntities(String completado) {
    EntityManager em = getEntityManager();
    try {
        TypedQuery<Pedido> query = em.createQuery(
            "SELECT u FROM Pedido u WHERE u.estado != :completado", Pedido.class);
        query.setParameter("completado", completado);

        List<Pedido> resultados = query.getResultList();
        return resultados;
    } finally {
        em.close();
    }
} 

    public List<Ingrediente> findPedidoIngredientes(int platilloId) {
    EntityManager em = getEntityManager();
    try {
        return em.createQuery(
            "SELECT i FROM Ingrediente i JOIN i.productoIngredienteList pi WHERE pi.productoIngredientePK.productoId = :platilloId",
            Ingrediente.class)
            .setParameter("platilloId", platilloId)
            .getResultList();
    } finally {
        em.close();
    }
}

//en pedido

    public int getPedidoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pedido> rt = cq.from(Pedido.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
