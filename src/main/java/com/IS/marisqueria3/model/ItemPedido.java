/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ambro
 */
@Entity
@Table(name = "item_pedido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItemPedido.findAll", query = "SELECT i FROM ItemPedido i"),
    @NamedQuery(name = "ItemPedido.findByIdItem", query = "SELECT i FROM ItemPedido i WHERE i.idItem = :idItem"),
    @NamedQuery(name = "ItemPedido.findByCantidad", query = "SELECT i FROM ItemPedido i WHERE i.cantidad = :cantidad"),
    @NamedQuery(name = "ItemPedido.findByPrecioUnitario", query = "SELECT i FROM ItemPedido i WHERE i.precioUnitario = :precioUnitario"),
    @NamedQuery(name = "ItemPedido.findByDescripcion", query = "SELECT i FROM ItemPedido i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "ItemPedido.findByCreatedAt", query = "SELECT i FROM ItemPedido i WHERE i.createdAt = :createdAt")})
public class ItemPedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_item")
    private Integer idItem;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "precio_unitario")
    private float precioUnitario;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "pedido_numero", referencedColumnName = "numero_pedido")
    @ManyToOne(optional = false)
    private Pedido pedidoNumero;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_platillo")
    @ManyToOne(optional = false)
    private Producto idProducto;

    public ItemPedido() {
    }

    public ItemPedido(Integer idItem) {
        this.idItem = idItem;
    }

    public ItemPedido(Integer idItem, int cantidad, float precioUnitario) {
        this.idItem = idItem;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Pedido getPedidoNumero() {
        return pedidoNumero;
    }

    public void setPedidoNumero(Pedido pedidoNumero) {
        this.pedidoNumero = pedidoNumero;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idItem != null ? idItem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemPedido)) {
            return false;
        }
        ItemPedido other = (ItemPedido) object;
        if ((this.idItem == null && other.idItem != null) || (this.idItem != null && !this.idItem.equals(other.idItem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.IS.marisqueria3.model.ItemPedido[ idItem=" + idItem + " ]";
    }
    
}
