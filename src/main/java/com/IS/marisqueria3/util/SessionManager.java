/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.util;

import com.IS.marisqueria3.model.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ambro
 */
public class SessionManager {
    private List<Usuario>usuarios=new ArrayList<>();
    
    public void setUsuarioActual(Usuario usuario) {
        usuarios.add(usuario);
    }
    
}
