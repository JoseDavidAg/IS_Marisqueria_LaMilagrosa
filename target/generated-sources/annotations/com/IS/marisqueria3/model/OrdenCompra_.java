package com.IS.marisqueria3.model;

import com.IS.marisqueria3.model.ItemOrdenCompra;
import com.IS.marisqueria3.model.Proveedor;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-05-23T00:33:47")
@StaticMetamodel(OrdenCompra.class)
public class OrdenCompra_ { 

    public static volatile SingularAttribute<OrdenCompra, Integer> numeroOrden;
    public static volatile ListAttribute<OrdenCompra, ItemOrdenCompra> itemOrdenCompraList;
    public static volatile SingularAttribute<OrdenCompra, Character> estado;
    public static volatile SingularAttribute<OrdenCompra, Proveedor> proveedorId;
    public static volatile SingularAttribute<OrdenCompra, Date> fechaCreacion;

}