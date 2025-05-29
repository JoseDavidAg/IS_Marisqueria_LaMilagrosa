/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.persistence;

import com.IS.marisqueria3.model.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.IS.marisqueria3.model.Mesa;
import java.util.ArrayList;
import java.util.List;
import com.IS.marisqueria3.model.Pedido;
import com.IS.marisqueria3.persistence.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ambro
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public ClienteJpaController(){
        emf= Persistence.createEntityManagerFactory("Marisqueria3TPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getMesaList() == null) {
            cliente.setMesaList(new ArrayList<Mesa>());
        }
        if (cliente.getPedidos() == null) {
            cliente.setPedidos(new ArrayList<Pedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Mesa> attachedMesaList = new ArrayList<Mesa>();
            for (Mesa mesaListMesaToAttach : cliente.getMesaList()) {
                mesaListMesaToAttach = em.getReference(mesaListMesaToAttach.getClass(), mesaListMesaToAttach.getIdMesa());
                attachedMesaList.add(mesaListMesaToAttach);
            }
            cliente.setMesaList(attachedMesaList);
            List<Pedido> attachedPedidoList = new ArrayList<Pedido>();
            for (Pedido pedidoListPedidoToAttach : cliente.getPedidos()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getNumeroPedido());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            cliente.setPedidos(attachedPedidoList);
            em.persist(cliente);
            for (Mesa mesaListMesa : cliente.getMesaList()) {
                Cliente oldClienteIdOfMesaListMesa = mesaListMesa.getClienteId();
                mesaListMesa.setClienteId(cliente);
                mesaListMesa = em.merge(mesaListMesa);
                if (oldClienteIdOfMesaListMesa != null) {
                    oldClienteIdOfMesaListMesa.getMesaList().remove(mesaListMesa);
                    oldClienteIdOfMesaListMesa = em.merge(oldClienteIdOfMesaListMesa);
                }
            }
            for (Pedido pedidoListPedido : cliente.getPedidos()) {
                Cliente oldClienteIdOfPedidoListPedido = pedidoListPedido.getClienteId();
                pedidoListPedido.setClienteId(cliente);
                pedidoListPedido = em.merge(pedidoListPedido);
                if (oldClienteIdOfPedidoListPedido != null) {
                    oldClienteIdOfPedidoListPedido.getPedidos().remove(pedidoListPedido);
                    oldClienteIdOfPedidoListPedido = em.merge(oldClienteIdOfPedidoListPedido);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdCliente());
            List<Mesa> mesaListOld = persistentCliente.getMesaList();
            List<Mesa> mesaListNew = cliente.getMesaList();
            List<Pedido> pedidoListOld = persistentCliente.getPedidos();
            List<Pedido> pedidoListNew = cliente.getPedidos();
            List<Mesa> attachedMesaListNew = new ArrayList<Mesa>();
            for (Mesa mesaListNewMesaToAttach : mesaListNew) {
                mesaListNewMesaToAttach = em.getReference(mesaListNewMesaToAttach.getClass(), mesaListNewMesaToAttach.getIdMesa());
                attachedMesaListNew.add(mesaListNewMesaToAttach);
            }
            mesaListNew = attachedMesaListNew;
            cliente.setMesaList(mesaListNew);
            List<Pedido> attachedPedidoListNew = new ArrayList<Pedido>();
            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getNumeroPedido());
                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
            }
            pedidoListNew = attachedPedidoListNew;
            cliente.setPedidos(pedidoListNew);
            cliente = em.merge(cliente);
            for (Mesa mesaListOldMesa : mesaListOld) {
                if (!mesaListNew.contains(mesaListOldMesa)) {
                    mesaListOldMesa.setClienteId(null);
                    mesaListOldMesa = em.merge(mesaListOldMesa);
                }
            }
            for (Mesa mesaListNewMesa : mesaListNew) {
                if (!mesaListOld.contains(mesaListNewMesa)) {
                    Cliente oldClienteIdOfMesaListNewMesa = mesaListNewMesa.getClienteId();
                    mesaListNewMesa.setClienteId(cliente);
                    mesaListNewMesa = em.merge(mesaListNewMesa);
                    if (oldClienteIdOfMesaListNewMesa != null && !oldClienteIdOfMesaListNewMesa.equals(cliente)) {
                        oldClienteIdOfMesaListNewMesa.getMesaList().remove(mesaListNewMesa);
                        oldClienteIdOfMesaListNewMesa = em.merge(oldClienteIdOfMesaListNewMesa);
                    }
                }
            }
            for (Pedido pedidoListOldPedido : pedidoListOld) {
                if (!pedidoListNew.contains(pedidoListOldPedido)) {
                    pedidoListOldPedido.setClienteId(null);
                    pedidoListOldPedido = em.merge(pedidoListOldPedido);
                }
            }
            for (Pedido pedidoListNewPedido : pedidoListNew) {
                if (!pedidoListOld.contains(pedidoListNewPedido)) {
                    Cliente oldClienteIdOfPedidoListNewPedido = pedidoListNewPedido.getClienteId();
                    pedidoListNewPedido.setClienteId(cliente);
                    pedidoListNewPedido = em.merge(pedidoListNewPedido);
                    if (oldClienteIdOfPedidoListNewPedido != null && !oldClienteIdOfPedidoListNewPedido.equals(cliente)) {
                        oldClienteIdOfPedidoListNewPedido.getPedidos().remove(pedidoListNewPedido);
                        oldClienteIdOfPedidoListNewPedido = em.merge(oldClienteIdOfPedidoListNewPedido);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getIdCliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<Mesa> mesaList = cliente.getMesaList();
            for (Mesa mesaListMesa : mesaList) {
                mesaListMesa.setClienteId(null);
                mesaListMesa = em.merge(mesaListMesa);
            }
            List<Pedido> pedidoList = cliente.getPedidos();
            for (Pedido pedidoListPedido : pedidoList) {
                pedidoListPedido.setClienteId(null);
                pedidoListPedido = em.merge(pedidoListPedido);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
