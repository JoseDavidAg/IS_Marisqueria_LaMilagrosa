/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.controller.exceptions.services;

import com.IS.marisqueria3.model.CategoriaCarta;
import com.IS.marisqueria3.model.Ingrediente;
import com.IS.marisqueria3.model.Producto;
import com.IS.marisqueria3.persistence.CategoriaCartaJpaController;
import com.IS.marisqueria3.persistence.IngredienteJpaController;
import com.IS.marisqueria3.persistence.ProductoJpaController;
import java.util.List;
/**
 *
 * @author ambro
 */
public class ProductoService {
    private final CategoriaCartaJpaController categoriaJpa;
    private final IngredienteJpaController ingredientesJpa;
    private final ProductoJpaController productoJpa;
    

    public ProductoService() {
        categoriaJpa = new CategoriaCartaJpaController();
        ingredientesJpa = new IngredienteJpaController();
        productoJpa = new ProductoJpaController();
       
    }
    
    // === Métodos para Producto ===
    
    public List<Producto> listarProductos() {
        return productoJpa.findProductoEntities();
    }

    public void crearProducto(Producto producto) throws Exception {
        productoJpa.create(producto);
    }

    public void eliminarProducto(int idProducto) throws Exception {
        productoJpa.destroy(idProducto);
    }

    // === Métodos para Ingrediente ===
    
    public List<Ingrediente> listarIngredientes() {
        return ingredientesJpa.findIngredienteEntities();
    }

    public void crearIngrediente(Ingrediente ingrediente) throws Exception {
        ingredientesJpa.create(ingrediente);
    }

    public void eliminarIngrediente(int idIngrediente) throws Exception {
        ingredientesJpa.destroy(idIngrediente);
    }

    // === Métodos para CategoriaCarta ===
    
    public List<CategoriaCarta> listarCategorias() {
        return categoriaJpa.findCategoriaCartaEntities();
    }

    public void crearCategoria(CategoriaCarta categoria) throws Exception {
        categoriaJpa.create(categoria);
    }

    public void eliminarCategoria(String idCategoria) throws Exception {
        categoriaJpa.destroy(idCategoria);
    }
    
}
