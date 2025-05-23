/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ambro
 */
@Entity
@Table(name = "categoria_carta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoriaCarta.findAll", query = "SELECT c FROM CategoriaCarta c"),
    @NamedQuery(name = "CategoriaCarta.findByNombre", query = "SELECT c FROM CategoriaCarta c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "CategoriaCarta.findByProductos", query = "SELECT c FROM CategoriaCarta c WHERE c.productos = :productos")})
public class CategoriaCarta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "productos")
    private Integer productos;
    @OneToMany(mappedBy = "categoriaNombre")
    private List<Producto> productoList;

    public CategoriaCarta() {
    }

    public CategoriaCarta(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getProductos() {
        return productos;
    }

    public void setProductos(Integer productos) {
        this.productos = productos;
    }

    @XmlTransient
    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombre != null ? nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaCarta)) {
            return false;
        }
        CategoriaCarta other = (CategoriaCarta) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.IS.marisqueria3.model.CategoriaCarta[ nombre=" + nombre + " ]";
    }
    
}
