/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.services;

import com.IS.marisqueria3.model.ItemOrdenCompra;
import com.IS.marisqueria3.model.OrdenCompra;
import com.IS.marisqueria3.model.Proveedor;
import com.IS.marisqueria3.persistence.ItemOrdenCompraJpaController;
import com.IS.marisqueria3.persistence.OrdenCompraJpaController;
import com.IS.marisqueria3.persistence.ProveedorJpaController;
import java.util.List; 
/**
 *
 * @author ambro
 */
public class OrdenCompraService {
    private final OrdenCompraJpaController ordenCompraJpa;
    private final ItemOrdenCompraJpaController itemOrdenCompraJpa;
    private final ProveedorJpaController proveedorJpa;

    public OrdenCompraService() {
        ordenCompraJpa = new OrdenCompraJpaController();
        itemOrdenCompraJpa = new ItemOrdenCompraJpaController();
        proveedorJpa = new ProveedorJpaController();
    }
    
    // --- Métodos para OrdenCompra ---
    public List<OrdenCompra> listarOrdenesCompra() {
        return ordenCompraJpa.findOrdenCompraEntities();
    }

    public void crearOrdenCompra(OrdenCompra orden) {
        ordenCompraJpa.create(orden);
    }

    public void eliminarOrdenCompra(int id) {
        try {
            ordenCompraJpa.destroy(id);
        } catch (Exception e) {
            e.printStackTrace(); // O lanza tu propia excepción personalizada
        }
    }

    // --- Métodos para ItemOrdenCompra ---
    public List<ItemOrdenCompra> listarItemsOrdenCompra() {
        return itemOrdenCompraJpa.findItemOrdenCompraEntities();
    }

    public void crearItemOrdenCompra(ItemOrdenCompra item) {
        itemOrdenCompraJpa.create(item);
    }

    public void eliminarItemOrdenCompra(int id) {
        try {
            itemOrdenCompraJpa.destroy(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- Métodos para Proveedor ---
    public List<Proveedor> listarProveedores() {
        return proveedorJpa.findProveedorEntities();
    }

    public void crearProveedor(Proveedor proveedor) {
        proveedorJpa.create(proveedor);
    }

    public void eliminarProveedor(int id) {
        try {
            proveedorJpa.destroy(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Proveedor listarProveedoresNombre(String toString) {
        return proveedorJpa.findProveedorNombre(toString);
    }
    
    public void editarProveedor(Proveedor proveedor) throws Exception{
        proveedorJpa.edit(proveedor);
    }
    
}