/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "ingrediente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingrediente.findAll", query = "SELECT i FROM Ingrediente i"),
    @NamedQuery(name = "Ingrediente.findByIngredienteId", query = "SELECT i FROM Ingrediente i WHERE i.ingredienteId = :ingredienteId"),
    @NamedQuery(name = "Ingrediente.findByDescripcion", query = "SELECT i FROM Ingrediente i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "Ingrediente.findByFechaCaducidad", query = "SELECT i FROM Ingrediente i WHERE i.fechaCaducidad = :fechaCaducidad"),
    @NamedQuery(name = "Ingrediente.findByNombre", query = "SELECT i FROM Ingrediente i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "Ingrediente.findByPrecioUnitario", query = "SELECT i FROM Ingrediente i WHERE i.precioUnitario = :precioUnitario"),
    @NamedQuery(name = "Ingrediente.findByStockDisponible", query = "SELECT i FROM Ingrediente i WHERE i.stockDisponible = :stockDisponible"),
    @NamedQuery(name = "Ingrediente.findByStockMinimo", query = "SELECT i FROM Ingrediente i WHERE i.stockMinimo = :stockMinimo"),
    @NamedQuery(name = "Ingrediente.findByUnidadMedida", query = "SELECT i FROM Ingrediente i WHERE i.unidadMedida = :unidadMedida")})
public class Ingrediente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ingrediente_id")
    private Integer ingredienteId;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_caducidad")
    @Temporal(TemporalType.DATE)
    private Date fechaCaducidad;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "precio_unitario")
    private BigInteger precioUnitario;
    @Column(name = "stock_disponible")
    private Integer stockDisponible;
    @Column(name = "stock_minimo")
    private Integer stockMinimo;
    @Column(name = "unidad_medida")
    private String unidadMedida;
    @JoinColumn(name = "proveedor_id", referencedColumnName = "id_proveedor")
    @ManyToOne
    private Proveedor proveedorId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ingrediente")
    private List<ProductoIngrediente> productoIngredienteList;
    @OneToMany(mappedBy = "ingredienteCodigo")
    private List<ItemOrdenCompra> itemOrdenCompraList;

    public Ingrediente() {
    }

    public Ingrediente(Integer ingredienteId) {
        this.ingredienteId = ingredienteId;
    }

    public Integer getIngredienteId() {
        return ingredienteId;
    }

    public void setIngredienteId(Integer ingredienteId) {
        this.ingredienteId = ingredienteId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigInteger getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigInteger precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(Integer stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Proveedor getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Proveedor proveedorId) {
        this.proveedorId = proveedorId;
    }

    @XmlTransient
    public List<ProductoIngrediente> getProductoIngredienteList() {
        return productoIngredienteList;
    }

    public void setProductoIngredienteList(List<ProductoIngrediente> productoIngredienteList) {
        this.productoIngredienteList = productoIngredienteList;
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
        hash += (ingredienteId != null ? ingredienteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingrediente)) {
            return false;
        }
        Ingrediente other = (Ingrediente) object;
        if ((this.ingredienteId == null && other.ingredienteId != null) || (this.ingredienteId != null && !this.ingredienteId.equals(other.ingredienteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.IS.marisqueria3.model.Ingrediente[ ingredienteId=" + ingredienteId + " ]";
    }
    
}
