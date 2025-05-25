/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.controller.exceptions.services;

/**
 *
 * @author ambro
 */
public class Test {
    public static void main(String args[]){
        PedidoService s = new PedidoService();
        String ds= s.traerIngredientes(1);
        System.out.println(ds);
    }
    
    
}
