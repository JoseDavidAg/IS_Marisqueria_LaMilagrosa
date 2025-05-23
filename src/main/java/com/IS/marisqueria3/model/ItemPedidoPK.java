/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author ambro
 */
@Embeddable
public class ItemPedidoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_producto")
    private int idProducto;
    @Basic(optional = false)
    @Column(name = "pedido_numero")
    private int pedidoNumero;

    public ItemPedidoPK() {
    }

    public ItemPedidoPK(int idProducto, int pedidoNumero) {
        this.idProducto = idProducto;
        this.pedidoNumero = pedidoNumero;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getPedidoNumero() {
        return pedidoNumero;
    }

    public void setPedidoNumero(int pedidoNumero) {
        this.pedidoNumero = pedidoNumero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idProducto;
        hash += (int) pedidoNumero;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemPedidoPK)) {
            return false;
        }
        ItemPedidoPK other = (ItemPedidoPK) object;
        if (this.idProducto != other.idProducto) {
            return false;
        }
        if (this.pedidoNumero != other.pedidoNumero) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.IS.marisqueria3.model.ItemPedidoPK[ idProducto=" + idProducto + ", pedidoNumero=" + pedidoNumero + " ]";
    }
    
}
