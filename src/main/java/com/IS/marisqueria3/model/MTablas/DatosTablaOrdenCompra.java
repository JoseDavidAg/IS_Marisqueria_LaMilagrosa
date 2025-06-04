/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.model.MTablas;

import com.IS.marisqueria3.util.OrdenCompraP; 

/**
 *
 * @author ambro
 */
public class DatosTablaOrdenCompra {
    
    /*select p.nombre,p.telefono,p.email,i.nombre as "Nombre producto", i.descripcion, i.stock_minimo, i.stock_disponible,(stock_disponible-stock_minimo) as cantidad_pedir from ingrediente i
join proveedor p on p.id_proveedor=i.proveedor_id
order by cantidad_pedir ;*/
    private OrdenCompraP orden;
    private String nombreProveedor;
    private String telefono;
    private String gmail;
    private String nombreProducto;
    private String descripcion;
    private int stockMinimo;
    private int stockDisponible;
    private int cantidadPedir;
    

    public DatosTablaOrdenCompra(OrdenCompraP t) {
        this.orden=t;
        nombreProveedor=t.getNombreProveedor();
        telefono=t.getTelefono();
        gmail=t.getGmail();
        nombreProducto=t.getNombreProducto();
        descripcion=t.getDescripcion();
        stockMinimo=t.getStockMinimo();
        stockDisponible=t.getStockDisponible();
        cantidadPedir=t.getCantidadPedir();
     
    }

    public OrdenCompraP getOrden() {
        return orden;
    }

    public void setOrden(OrdenCompraP orden) {
        this.orden = orden;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(int stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    public int getCantidadPedir() {
        return cantidadPedir;
    }

    public void setCantidadPedir(int cantidadPedir) {
        this.cantidadPedir = cantidadPedir;
    }
    
    
}