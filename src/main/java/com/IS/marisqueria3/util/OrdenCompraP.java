/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.util;


/**
 *
 * @author ambro
 * select p.nombre,p.telefono,p.email,i.nombre as "Nombre producto", i.descripcion, i.stock_minimo, i.stock_disponible,(stock_disponible-stock_minimo) as cantidad_pedir from ingrediente i
join proveedor p on p.id_proveedor=i.proveedor_id
order by cantidad_pedir ;
 */
public class OrdenCompraP {
    private String nombreProveedor;
    private String telefono;
    private String gmail;
    private String nombreProducto;
    private String descripcion;
    private int stockMinimo;
    private int stockDisponible;
    private int cantidadPedir;
    private String unidadMedida;

    public OrdenCompraP(String nombreProveedor, String telefono, String gmail, String nombreProducto, String descripcion, Integer stockMinimo, Integer stockDisponible, String unidadMedida) {
        this.nombreProveedor = nombreProveedor;
        this.telefono = telefono;
        this.gmail = gmail;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.stockMinimo = stockMinimo;
        this.stockDisponible = stockDisponible;
        this.unidadMedida= unidadMedida;
        if(stockDisponible-stockMinimo<=0){
            cantidadPedir=(stockDisponible-stockMinimo)*-1;
        }else{
            cantidadPedir=0;
        }
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getGmail() {
        return gmail;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }

    public int getCantidadPedir() {
        return cantidadPedir;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public void setStockDisponible(int stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    
    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public void setCantidadPedir(int cantidadPedir) {
        this.cantidadPedir = cantidadPedir;
    }
    
    
    
    
    

    
}

