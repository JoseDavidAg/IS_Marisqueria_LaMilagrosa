/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.model.MTablas;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author ambro
 */
public class ReporteVentas {
    private int categoriaId;
    private String categoriaNombre;
    private int platilloId;
    private String platilloNombre;
    private String descripcion;
    private float precioVenta;
    private long cantidadVendida;
    private double montoTotal;
    private LocalDate fechaVenta;
    private LocalTime horaVenta;

    // Constructor vacío (necesario para frameworks)
    public ReporteVentas() {
    }

    // Constructor para reportes básicos
    public ReporteVentas(int platilloId, String platilloNombre, int cantidadVendida, float montoTotal) {
        this.platilloId = platilloId;
        this.platilloNombre = platilloNombre;
        this.cantidadVendida = cantidadVendida;
        this.montoTotal = montoTotal;
    }

        // En la clase ReporteVentas
    public ReporteVentas(
        Integer idCategoria,      // java.lang.Integer
        String nombreCategoria,   // java.lang.String
        Integer idPlatillo,       // java.lang.Integer
        String nombrePlatillo,    // java.lang.String
        String descripcion,       // java.lang.String
        Float precioVenta,        // java.lang.Float (no float primitivo!)
        Long cantidadVendida,     // java.lang.Long
        Double totalVendido       // java.lang.Double
    ) {
        // Asignación de valores a campos
        this.categoriaId = idCategoria;
        this.categoriaNombre = nombreCategoria;
        this.platilloId = idPlatillo;
        this.platilloNombre = nombrePlatillo;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.cantidadVendida = cantidadVendida;
        this.montoTotal = totalVendido;
    }
            
    // Constructor para reportes detallados
    public ReporteVentas(int categoriaId, String categoriaNombre, int platilloId, 
                        String platilloNombre, String descripcion, float precioVenta, 
                        int cantidadVendida, float montoTotal, LocalDate fechaVenta) {
        this(categoriaId, categoriaNombre, platilloId, platilloNombre, descripcion, 
             precioVenta, cantidadVendida, montoTotal, fechaVenta, null);
    }

    // Constructor completo con todos los campos
    public ReporteVentas(int categoriaId, String categoriaNombre, int platilloId, String platilloNombre, String descripcion, float precioVenta, 
                        int cantidadVendida, float montoTotal, LocalDate fechaVenta, LocalTime horaVenta) {
        this.categoriaId = categoriaId;
        this.categoriaNombre = categoriaNombre;
        this.platilloId = platilloId;
        this.platilloNombre = platilloNombre;
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.cantidadVendida = cantidadVendida;
        this.montoTotal = montoTotal;
        this.fechaVenta = fechaVenta;
        this.horaVenta = horaVenta;
    }



    // Getters y Setters
    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public int getPlatilloId() {
        return platilloId;
    }

    public void setPlatilloId(int platilloId) {
        this.platilloId = platilloId;
    }

    public String getPlatilloNombre() {
        return platilloNombre;
    }

    public void setPlatilloNombre(String platilloNombre) {
        this.platilloNombre = platilloNombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public long getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(float montoTotal) {
        this.montoTotal = montoTotal;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }
 
    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public LocalTime getHoraVenta() {
        return horaVenta;
    }

    public void setHoraVenta(LocalTime horaVenta) {
        this.horaVenta = horaVenta;
    }

    // Método de ayuda para obtener fecha formateada
    public String getFechaFormateada() {
        if (fechaVenta != null) {
            return fechaVenta.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return "N/A";
    }

    // Método de ayuda para obtener hora formateada
    public String getHoraFormateada() {
        if (horaVenta != null) {
            return horaVenta.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
        }
        return "N/A";
    }

    // Método de ayuda para obtener precio formateado
    public String getPrecioFormateado() {
        return "$" + String.format("%.2f", precioVenta);
        
    }

    // Método de ayuda para obtener monto total formateado
    public String getMontoTotalFormateado() {
        return "$" + String.format("%.2f", montoTotal);
    }

    @Override
    public String toString() {
        return "ReporteVentas{" +
                "categoriaId=" + categoriaId +
                ", categoriaNombre='" + categoriaNombre + '\'' +
                ", platilloId=" + platilloId +
                ", platilloNombre='" + platilloNombre + '\'' +
                ", cantidadVendida=" + cantidadVendida +
                ", montoTotal=" + getMontoTotalFormateado() +
                ", fechaVenta=" + getFechaFormateada() +
                ", horaVenta=" + getHoraFormateada() +
                '}';
    }
}
