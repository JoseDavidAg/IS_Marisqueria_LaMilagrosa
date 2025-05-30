package com.IS.marisqueria3.model;

import com.IS.marisqueria3.model.ItemOrdenCompra;
import com.IS.marisqueria3.model.Proveedor;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-05-29T22:03:05", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(OrdenCompra.class)
public class OrdenCompra_ { 

    public static volatile SingularAttribute<OrdenCompra, Integer> numeroOrden;
    public static volatile ListAttribute<OrdenCompra, ItemOrdenCompra> itemOrdenCompraList;
    public static volatile SingularAttribute<OrdenCompra, Character> estado;
    public static volatile SingularAttribute<OrdenCompra, Proveedor> proveedorId;
    public static volatile SingularAttribute<OrdenCompra, Date> fechaCreacion;

}