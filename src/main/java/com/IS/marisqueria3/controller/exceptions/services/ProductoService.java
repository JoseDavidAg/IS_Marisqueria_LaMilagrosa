/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.controller.exceptions.services;

import com.IS.marisqueria3.persistence.CategoriaCartaJpaController;
import com.IS.marisqueria3.persistence.IngredienteJpaController;
import com.IS.marisqueria3.persistence.ProductoJpaController;

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
}
