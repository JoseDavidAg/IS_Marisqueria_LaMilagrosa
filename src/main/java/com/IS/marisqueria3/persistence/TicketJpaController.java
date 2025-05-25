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
import com.IS.marisqueria3.model.Pedido;
import com.IS.marisqueria3.model.Ticket;
import com.IS.marisqueria3.persistence.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ambro
 */
public class TicketJpaController implements Serializable {

    public TicketJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public TicketJpaController(){
        emf= Persistence.createEntityManagerFactory("Marisqueria3TPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ticket ticket) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedidoNumero = ticket.getPedidoNumero();
            if (pedidoNumero != null) {
                pedidoNumero = em.getReference(pedidoNumero.getClass(), pedidoNumero.getNumeroPedido());
                ticket.setPedidoNumero(pedidoNumero);
            }
            em.persist(ticket);
            if (pedidoNumero != null) {
                pedidoNumero.getTicketList().add(ticket);
                pedidoNumero = em.merge(pedidoNumero);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ticket ticket) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ticket persistentTicket = em.find(Ticket.class, ticket.getNumeroTicket());
            Pedido pedidoNumeroOld = persistentTicket.getPedidoNumero();
            Pedido pedidoNumeroNew = ticket.getPedidoNumero();
            if (pedidoNumeroNew != null) {
                pedidoNumeroNew = em.getReference(pedidoNumeroNew.getClass(), pedidoNumeroNew.getNumeroPedido());
                ticket.setPedidoNumero(pedidoNumeroNew);
            }
            ticket = em.merge(ticket);
            if (pedidoNumeroOld != null && !pedidoNumeroOld.equals(pedidoNumeroNew)) {
                pedidoNumeroOld.getTicketList().remove(ticket);
                pedidoNumeroOld = em.merge(pedidoNumeroOld);
            }
            if (pedidoNumeroNew != null && !pedidoNumeroNew.equals(pedidoNumeroOld)) {
                pedidoNumeroNew.getTicketList().add(ticket);
                pedidoNumeroNew = em.merge(pedidoNumeroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ticket.getNumeroTicket();
                if (findTicket(id) == null) {
                    throw new NonexistentEntityException("The ticket with id " + id + " no longer exists.");
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
            Ticket ticket;
            try {
                ticket = em.getReference(Ticket.class, id);
                ticket.getNumeroTicket();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ticket with id " + id + " no longer exists.", enfe);
            }
            Pedido pedidoNumero = ticket.getPedidoNumero();
            if (pedidoNumero != null) {
                pedidoNumero.getTicketList().remove(ticket);
                pedidoNumero = em.merge(pedidoNumero);
            }
            em.remove(ticket);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ticket> findTicketEntities() {
        return findTicketEntities(true, -1, -1);
    }

    public List<Ticket> findTicketEntities(int maxResults, int firstResult) {
        return findTicketEntities(false, maxResults, firstResult);
    }

    private List<Ticket> findTicketEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ticket.class));
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

    public Ticket findTicket(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ticket.class, id);
        } finally {
            em.close();
        }
    }

    public int getTicketCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ticket> rt = cq.from(Ticket.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
