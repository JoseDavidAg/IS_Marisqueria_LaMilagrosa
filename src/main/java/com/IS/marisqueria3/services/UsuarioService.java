/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.services;

import com.IS.marisqueria3.model.Usuario;
import com.IS.marisqueria3.persistence.UsuarioJpaController;
import java.util.List;
/**
 *
 * @author ambro
 */
public class UsuarioService {

    private final UsuarioJpaController usuarioJpa;

    public UsuarioService() {
        
        usuarioJpa = new UsuarioJpaController();
    }
    
    
    public List<Usuario> traerTodosUsuarios() {
        return usuarioJpa.findUsuarioEntities();
    }

    public void crearUsuario(Usuario usuario) {
        usuarioJpa.create(usuario);
    }

    public void eliminarUsuario(int idUsuario) {
        try {
            usuarioJpa.destroy(idUsuario);
        } catch (Exception e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
    }
    
    public void editarUsuario(Usuario usuario) throws Exception{
        usuarioJpa.edit(usuario);
    }
    
    public Usuario autenticar(String user, String contraseña) {
        
        Usuario u = usuarioJpa.findUsuarioNombre(user);
        if (u != null && u.getContraseña().equals(contraseña)) {
            return u;
        }
        return null;
    }
    
}

