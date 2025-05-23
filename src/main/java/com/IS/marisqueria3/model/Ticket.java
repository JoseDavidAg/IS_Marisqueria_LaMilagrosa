/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ambro
 */
@Entity
@Table(name = "ticket")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ticket.findAll", query = "SELECT t FROM Ticket t"),
    @NamedQuery(name = "Ticket.findByNumeroTicket", query = "SELECT t FROM Ticket t WHERE t.numeroTicket = :numeroTicket"),
    @NamedQuery(name = "Ticket.findByFechaGeneracion", query = "SELECT t FROM Ticket t WHERE t.fechaGeneracion = :fechaGeneracion"),
    @NamedQuery(name = "Ticket.findByTotalPagar", query = "SELECT t FROM Ticket t WHERE t.totalPagar = :totalPagar"),
    @NamedQuery(name = "Ticket.findByMetodoPago", query = "SELECT t FROM Ticket t WHERE t.metodoPago = :metodoPago"),
    @NamedQuery(name = "Ticket.findBySubtotal", query = "SELECT t FROM Ticket t WHERE t.subtotal = :subtotal"),
    @NamedQuery(name = "Ticket.findByIva", query = "SELECT t FROM Ticket t WHERE t.iva = :iva")})
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numero_ticket")
    private Integer numeroTicket;
    @Column(name = "fecha_generacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaGeneracion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_pagar")
    private BigDecimal totalPagar;
    @Column(name = "metodo_pago")
    private String metodoPago;
    @Column(name = "subtotal")
    private BigDecimal subtotal;
    @Column(name = "iva")
    private BigDecimal iva;
    @JoinColumn(name = "pedido_numero", referencedColumnName = "numero_pedido")
    @OneToOne(optional = false)
    private Pedido pedidoNumero;

    public Ticket() {
    }

    public Ticket(Integer numeroTicket) {
        this.numeroTicket = numeroTicket;
    }

    public Integer getNumeroTicket() {
        return numeroTicket;
    }

    public void setNumeroTicket(Integer numeroTicket) {
        this.numeroTicket = numeroTicket;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public BigDecimal getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(BigDecimal totalPagar) {
        this.totalPagar = totalPagar;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public Pedido getPedidoNumero() {
        return pedidoNumero;
    }

    public void setPedidoNumero(Pedido pedidoNumero) {
        this.pedidoNumero = pedidoNumero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroTicket != null ? numeroTicket.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.numeroTicket == null && other.numeroTicket != null) || (this.numeroTicket != null && !this.numeroTicket.equals(other.numeroTicket))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.IS.marisqueria3.model.Ticket[ numeroTicket=" + numeroTicket + " ]";
    }
    
}
