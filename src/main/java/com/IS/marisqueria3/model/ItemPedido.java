/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
    @NamedQuery(name = "ItemPedido.findByCantidad", query = "SELECT i FROM ItemPedido i WHERE i.cantidad = :cantidad"),
    @NamedQuery(name = "ItemPedido.findByPedidoNumero", query = "SELECT i FROM ItemPedido i WHERE i.itemPedidoPK.pedidoNumero = :pedidoNumero"),
    @NamedQuery(name = "ItemPedido.findByIdProducto", query = "SELECT i FROM ItemPedido i WHERE i.itemPedidoPK.idProducto = :idProducto"),
    @NamedQuery(name = "ItemPedido.findByDescripcion", query = "SELECT i FROM ItemPedido i WHERE i.descripcion = :descripcion")})
public class ItemPedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ItemPedidoPK itemPedidoPK;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "pedido_numero", referencedColumnName = "numero_pedido", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pedido pedido;
    @JoinColumn(name = "id_producto", referencedColumnName = "id_platillo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;

    public ItemPedido() {
    }

    public ItemPedido(ItemPedidoPK itemPedidoPK) {
        this.itemPedidoPK = itemPedidoPK;
    }

    public ItemPedido(int pedidoNumero, int idProducto) {
        this.itemPedidoPK = new ItemPedidoPK(pedidoNumero, idProducto);
    }

    public ItemPedidoPK getItemPedidoPK() {
        return itemPedidoPK;
    }

    public void setItemPedidoPK(ItemPedidoPK itemPedidoPK) {
        this.itemPedidoPK = itemPedidoPK;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemPedidoPK != null ? itemPedidoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemPedido)) {
            return false;
        }
        ItemPedido other = (ItemPedido) object;
        if ((this.itemPedidoPK == null && other.itemPedidoPK != null) || (this.itemPedidoPK != null && !this.itemPedidoPK.equals(other.itemPedidoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.IS.marisqueria3.model.ItemPedido[ itemPedidoPK=" + itemPedidoPK + " ]";
    }
    
}
