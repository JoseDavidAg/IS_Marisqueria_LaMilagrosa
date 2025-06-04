/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.IS.marisqueria3.services;

import com.IS.marisqueria3.model.MTablas.ReporteVentas;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ambro
 */ 
public class ReporteService implements Serializable {
    private EntityManagerFactory emf = null;
    
    public ReporteService(){
        emf= Persistence.createEntityManagerFactory("Marisqueria3TPU");
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    

 
    public List<ReporteVentas> generarReporteVentas(Date fechaInicio, Date fechaFin) {
    EntityManager em = getEntityManager();
    try {
        Timestamp inicioTimestamp = new Timestamp(fechaInicio.getTime());
        Timestamp finTimestamp = new Timestamp(fechaFin.getTime());

        
        
        // Consulta JPQL corregida
        String jpql = "SELECT new com.IS.marisqueria3.model.MTablas.ReporteVentas("
                    + "c.idCategoria, c.nombre, p.idPlatillo, p.nombre, "
                    + "p.descripcion, p.precioVenta, "
                    + "SUM(ip.cantidad), "
                    + "SUM(ip.cantidad * p.precioVenta)) "
                    + "FROM ItemPedido ip "
                    + "JOIN ip.idProducto p "
                    + "JOIN ip.pedidoNumero pd "
                    + "JOIN p.categoriaId c "
                    + "WHERE pd.fechaGeneracion BETWEEN :inicio AND :fin "
                    + "AND pd.estado = 'terminado' "
                    + "GROUP BY c.idCategoria, c.nombre, p.idPlatillo, p.nombre, p.descripcion, p.precioVenta";
        System.out.println("jpql: "+jpql);
        return em.createQuery(jpql, ReporteVentas.class)
                .setParameter("inicio", inicioTimestamp)
                .setParameter("fin", finTimestamp)
                .getResultList();
    } catch (Exception e) {
        throw new RuntimeException("Error generando reporte de ventas: " + e.getMessage(), e);
    } finally {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}
    
  
}