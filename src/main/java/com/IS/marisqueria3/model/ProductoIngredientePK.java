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
public class ProductoIngredientePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "producto_id")
    private int productoId;
    @Basic(optional = false)
    @Column(name = "ingrediente_codigo")
    private int ingredienteCodigo;

    public ProductoIngredientePK() {
    }

    public ProductoIngredientePK(int productoId, int ingredienteCodigo) {
        this.productoId = productoId;
        this.ingredienteCodigo = ingredienteCodigo;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getIngredienteCodigo() {
        return ingredienteCodigo;
    }

    public void setIngredienteCodigo(int ingredienteCodigo) {
        this.ingredienteCodigo = ingredienteCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) productoId;
        hash += (int) ingredienteCodigo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoIngredientePK)) {
            return false;
        }
        ProductoIngredientePK other = (ProductoIngredientePK) object;
        if (this.productoId != other.productoId) {
            return false;
        }
        if (this.ingredienteCodigo != other.ingredienteCodigo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.IS.marisqueria3.model.ProductoIngredientePK[ productoId=" + productoId + ", ingredienteCodigo=" + ingredienteCodigo + " ]";
    }
    
}
