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
@Table(name = "producto_ingrediente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductoIngrediente.findAll", query = "SELECT p FROM ProductoIngrediente p"),
    @NamedQuery(name = "ProductoIngrediente.findByProductoId", query = "SELECT p FROM ProductoIngrediente p WHERE p.productoIngredientePK.productoId = :productoId"),
    @NamedQuery(name = "ProductoIngrediente.findByIngredienteCodigo", query = "SELECT p FROM ProductoIngrediente p WHERE p.productoIngredientePK.ingredienteCodigo = :ingredienteCodigo"),
    @NamedQuery(name = "ProductoIngrediente.findByCantidad", query = "SELECT p FROM ProductoIngrediente p WHERE p.cantidad = :cantidad")})
public class ProductoIngrediente implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProductoIngredientePK productoIngredientePK;
    @Column(name = "cantidad")
    private Integer cantidad;
    @JoinColumn(name = "ingrediente_codigo", referencedColumnName = "ingrediente_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ingrediente ingrediente;
    @JoinColumn(name = "producto_id", referencedColumnName = "id_platillo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;

    public ProductoIngrediente() {
    }

    public ProductoIngrediente(ProductoIngredientePK productoIngredientePK) {
        this.productoIngredientePK = productoIngredientePK;
    }

    public ProductoIngrediente(int productoId, int ingredienteCodigo) {
        this.productoIngredientePK = new ProductoIngredientePK(productoId, ingredienteCodigo);
    }

    public ProductoIngredientePK getProductoIngredientePK() {
        return productoIngredientePK;
    }

    public void setProductoIngredientePK(ProductoIngredientePK productoIngredientePK) {
        this.productoIngredientePK = productoIngredientePK;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
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
        hash += (productoIngredientePK != null ? productoIngredientePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoIngrediente)) {
            return false;
        }
        ProductoIngrediente other = (ProductoIngrediente) object;
        if ((this.productoIngredientePK == null && other.productoIngredientePK != null) || (this.productoIngredientePK != null && !this.productoIngredientePK.equals(other.productoIngredientePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.IS.marisqueria3.model.ProductoIngrediente[ productoIngredientePK=" + productoIngredientePK + " ]";
    }
    
}
