/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ambro
 */
@Entity
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByIdPlatillo", query = "SELECT p FROM Producto p WHERE p.idPlatillo = :idPlatillo"),
    @NamedQuery(name = "Producto.findByNombre", query = "SELECT p FROM Producto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Producto.findByDescripcion", query = "SELECT p FROM Producto p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Producto.findByUnidadMedida", query = "SELECT p FROM Producto p WHERE p.unidadMedida = :unidadMedida"),
    @NamedQuery(name = "Producto.findByPrecioVenta", query = "SELECT p FROM Producto p WHERE p.precioVenta = :precioVenta")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_platillo")
    private Integer idPlatillo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "unidad_medida")
    private String unidadMedida;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio_venta")
    private BigDecimal precioVenta;
    @JoinTable(name = "producto_ingrediente", joinColumns = {
        @JoinColumn(name = "producto_id", referencedColumnName = "id_platillo")}, inverseJoinColumns = {
        @JoinColumn(name = "ingrediente_codigo", referencedColumnName = "codigo_producto")})
    @ManyToMany
    private List<Ingrediente> ingredienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private List<ItemPedido> itemPedidoList;
    @JoinColumn(name = "categoria_nombre", referencedColumnName = "nombre")
    @ManyToOne
    private CategoriaCarta categoriaNombre;

    public Producto() {
    }

    public Producto(Integer idPlatillo) {
        this.idPlatillo = idPlatillo;
    }

    public Producto(Integer idPlatillo, String nombre) {
        this.idPlatillo = idPlatillo;
        this.nombre = nombre;
    }

    public Integer getIdPlatillo() {
        return idPlatillo;
    }

    public void setIdPlatillo(Integer idPlatillo) {
        this.idPlatillo = idPlatillo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    @XmlTransient
    public List<Ingrediente> getIngredienteList() {
        return ingredienteList;
    }

    public void setIngredienteList(List<Ingrediente> ingredienteList) {
        this.ingredienteList = ingredienteList;
    }

    @XmlTransient
    public List<ItemPedido> getItemPedidoList() {
        return itemPedidoList;
    }

    public void setItemPedidoList(List<ItemPedido> itemPedidoList) {
        this.itemPedidoList = itemPedidoList;
    }

    public CategoriaCarta getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(CategoriaCarta categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlatillo != null ? idPlatillo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idPlatillo == null && other.idPlatillo != null) || (this.idPlatillo != null && !this.idPlatillo.equals(other.idPlatillo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.IS.marisqueria3.model.Producto[ idPlatillo=" + idPlatillo + " ]";
    }
    
}
