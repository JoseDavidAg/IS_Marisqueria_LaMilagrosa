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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
    @NamedQuery(name = "Ingrediente.findByCodigoProducto", query = "SELECT i FROM Ingrediente i WHERE i.codigoIngrediente = :codigoIngrediente"),
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
    @Column(name = "codigo_producto")
    private Integer codigoIngrediente;
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
    @JoinTable(name = "producto_ingrediente", joinColumns = {
        @JoinColumn(name = "ingrediente_codigo", referencedColumnName = "codigo_producto")}, inverseJoinColumns = {
        @JoinColumn(name = "producto_id", referencedColumnName = "id_platillo")})
    @ManyToMany
    private List<Producto> productoList;
    @OneToMany(mappedBy = "ingredienteCodigo")
    private List<ItemOrdenCompra> itemOrdenCompraList;
    @JoinColumn(name = "categoria_id", referencedColumnName = "id_categoria")
    @ManyToOne(optional = false)
    private CategoriaCarta categoriaId;
    @JoinColumn(name = "proveedor_id", referencedColumnName = "id_proveedor")
    @ManyToOne
    private Proveedor proveedorId;

    public Ingrediente() {
    }

    public Ingrediente(Integer codigoProducto) {
        this.codigoIngrediente = codigoProducto;
    }

    public Integer getCodigoIngrediente() {
        return codigoIngrediente;
    }

    public void setCodigoIngrediente(Integer codigoIngrediente) {
        this.codigoIngrediente = codigoIngrediente;
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

    @XmlTransient
    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }

    @XmlTransient
    public List<ItemOrdenCompra> getItemOrdenCompraList() {
        return itemOrdenCompraList;
    }

    public void setItemOrdenCompraList(List<ItemOrdenCompra> itemOrdenCompraList) {
        this.itemOrdenCompraList = itemOrdenCompraList;
    }

    public CategoriaCarta getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(CategoriaCarta categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Proveedor getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Proveedor proveedorId) {
        this.proveedorId = proveedorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoIngrediente != null ? codigoIngrediente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingrediente)) {
            return false;
        }
        Ingrediente other = (Ingrediente) object;
        if ((this.codigoIngrediente == null && other.codigoIngrediente != null) || (this.codigoIngrediente != null && !this.codigoIngrediente.equals(other.codigoIngrediente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.IS.marisqueria3.model.Ingrediente[ codigoProducto=" + codigoIngrediente + " ]";
    }
    
}
