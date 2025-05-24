/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ambro
 */
@Entity
@Table(name = "orden_compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrdenCompra.findAll", query = "SELECT o FROM OrdenCompra o"),
    @NamedQuery(name = "OrdenCompra.findByNumeroOrden", query = "SELECT o FROM OrdenCompra o WHERE o.numeroOrden = :numeroOrden"),
    @NamedQuery(name = "OrdenCompra.findByEstado", query = "SELECT o FROM OrdenCompra o WHERE o.estado = :estado"),
    @NamedQuery(name = "OrdenCompra.findByFechaCreacion", query = "SELECT o FROM OrdenCompra o WHERE o.fechaCreacion = :fechaCreacion")})
public class OrdenCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numero_orden")
    private Integer numeroOrden;
    @Column(name = "estado")
    private Character estado;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "proveedor_id", referencedColumnName = "id_proveedor")
    @ManyToOne
    private Proveedor proveedorId;
    @OneToMany(mappedBy = "ordenCompraNumero")
    private List<ItemOrdenCompra> itemOrdenCompraList;

    public OrdenCompra() {
    }

    public OrdenCompra(Integer numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public Integer getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(Integer numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Proveedor getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Proveedor proveedorId) {
        this.proveedorId = proveedorId;
    }

    @XmlTransient
    public List<ItemOrdenCompra> getItemOrdenCompraList() {
        return itemOrdenCompraList;
    }

    public void setItemOrdenCompraList(List<ItemOrdenCompra> itemOrdenCompraList) {
        this.itemOrdenCompraList = itemOrdenCompraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroOrden != null ? numeroOrden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenCompra)) {
            return false;
        }
        OrdenCompra other = (OrdenCompra) object;
        if ((this.numeroOrden == null && other.numeroOrden != null) || (this.numeroOrden != null && !this.numeroOrden.equals(other.numeroOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.IS.marisqueria3.model.OrdenCompra[ numeroOrden=" + numeroOrden + " ]";
    }
    
}
