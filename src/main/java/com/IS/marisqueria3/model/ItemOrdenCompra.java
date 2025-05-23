/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.model;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ambro
 */
@Entity
@Table(name = "item_orden_compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItemOrdenCompra.findAll", query = "SELECT i FROM ItemOrdenCompra i"),
    @NamedQuery(name = "ItemOrdenCompra.findByIdItemOrden", query = "SELECT i FROM ItemOrdenCompra i WHERE i.idItemOrden = :idItemOrden"),
    @NamedQuery(name = "ItemOrdenCompra.findByCantidad", query = "SELECT i FROM ItemOrdenCompra i WHERE i.cantidad = :cantidad")})
public class ItemOrdenCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_item_orden")
    private Integer idItemOrden;
    @Column(name = "cantidad")
    private Integer cantidad;
    @JoinColumn(name = "ingrediente_codigo", referencedColumnName = "codigo_producto")
    @ManyToOne
    private Ingrediente ingredienteCodigo;
    @JoinColumn(name = "orden_compra_numero", referencedColumnName = "numero_orden")
    @ManyToOne
    private OrdenCompra ordenCompraNumero;

    public ItemOrdenCompra() {
    }

    public ItemOrdenCompra(Integer idItemOrden) {
        this.idItemOrden = idItemOrden;
    }

    public Integer getIdItemOrden() {
        return idItemOrden;
    }

    public void setIdItemOrden(Integer idItemOrden) {
        this.idItemOrden = idItemOrden;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Ingrediente getIngredienteCodigo() {
        return ingredienteCodigo;
    }

    public void setIngredienteCodigo(Ingrediente ingredienteCodigo) {
        this.ingredienteCodigo = ingredienteCodigo;
    }

    public OrdenCompra getOrdenCompraNumero() {
        return ordenCompraNumero;
    }

    public void setOrdenCompraNumero(OrdenCompra ordenCompraNumero) {
        this.ordenCompraNumero = ordenCompraNumero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idItemOrden != null ? idItemOrden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemOrdenCompra)) {
            return false;
        }
        ItemOrdenCompra other = (ItemOrdenCompra) object;
        if ((this.idItemOrden == null && other.idItemOrden != null) || (this.idItemOrden != null && !this.idItemOrden.equals(other.idItemOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.IS.marisqueria3.model.ItemOrdenCompra[ idItemOrden=" + idItemOrden + " ]";
    }
    
}
