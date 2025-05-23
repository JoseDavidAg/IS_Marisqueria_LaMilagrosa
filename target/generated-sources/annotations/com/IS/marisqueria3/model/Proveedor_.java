package com.IS.marisqueria3.model;

import com.IS.marisqueria3.model.Ingrediente;
import com.IS.marisqueria3.model.OrdenCompra;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-05-23T00:33:47")
@StaticMetamodel(Proveedor.class)
public class Proveedor_ { 

    public static volatile ListAttribute<Proveedor, OrdenCompra> ordenCompraList;
    public static volatile ListAttribute<Proveedor, Ingrediente> ingredienteList;
    public static volatile SingularAttribute<Proveedor, Integer> idProveedor;
    public static volatile SingularAttribute<Proveedor, String> direccion;
    public static volatile SingularAttribute<Proveedor, String> telefono;
    public static volatile SingularAttribute<Proveedor, String> nombre;
    public static volatile SingularAttribute<Proveedor, String> email;

}