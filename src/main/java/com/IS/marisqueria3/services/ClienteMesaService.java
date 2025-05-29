/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.services;

import com.IS.marisqueria3.model.Cliente;
import com.IS.marisqueria3.model.Mesa;
import com.IS.marisqueria3.model.Ticket;
import com.IS.marisqueria3.persistence.ClienteJpaController;
import com.IS.marisqueria3.persistence.MesaJpaController;
import com.IS.marisqueria3.persistence.TicketJpaController;
import java.util.List;
/**
 *
 * @author ambro
 */
public class ClienteMesaService {
    private final ClienteJpaController clienteJpa;
    private final MesaJpaController mesaJpa;
    private final TicketJpaController ticketJpa;

    public ClienteMesaService() {
        clienteJpa = new ClienteJpaController();
        mesaJpa = new MesaJpaController();
        ticketJpa = new TicketJpaController();
    }
    
     // ===== CLIENTE =====
    public List<Cliente> traerClientes() {
        return clienteJpa.findClienteEntities();
    }

    public void crearCliente(Cliente cliente) {
        try {
            clienteJpa.create(cliente);
        } catch (Exception e) {
            e.printStackTrace(); // Manejo básico de errores, puedes usar un logger
        }
    }

    public void eliminarCliente(int idCliente) {
        try {
            clienteJpa.destroy(idCliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== MESA =====
    public List<Mesa> traerMesas() {
        return mesaJpa.findMesaEntities();
    }

    public void crearMesa(Mesa mesa) {
        try {
            mesaJpa.create(mesa);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarMesa(int idMesa) {
        try {
            mesaJpa.destroy(idMesa);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== TICKET =====
    public List<Ticket> traerTickets() {
        return ticketJpa.findTicketEntities();
    }

    public void crearTicket(Ticket ticket) {
        try {
            ticketJpa.create(ticket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarTicket(int idTicket) {
        try {
            ticketJpa.destroy(idTicket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // En ClienteMesaService.java (método actualizarEstadoMesa)
    public void actualizarEstadoMesa(int idMesa, String estado) throws Exception {
        Mesa mesa = mesaJpa.findMesa(idMesa);
        if (mesa == null) {
            throw new IllegalArgumentException("Mesa no existe");
        }
        
        boolean es=!estado.equals("Ocupado");
        mesa.setEstaDisponible(es);
        mesaJpa.edit(new Mesa(idMesa));
    }
    
}
