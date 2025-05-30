package com.IS.marisqueria3.model;

import com.IS.marisqueria3.model.Ingrediente;
import com.IS.marisqueria3.model.OrdenCompra;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-05-29T22:03:05", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(ItemOrdenCompra.class)
public class ItemOrdenCompra_ { 

    public static volatile SingularAttribute<ItemOrdenCompra, Integer> idItemOrden;
    public static volatile SingularAttribute<ItemOrdenCompra, OrdenCompra> ordenCompraNumero;
    public static volatile SingularAttribute<ItemOrdenCompra, Ingrediente> ingredienteCodigo;
    public static volatile SingularAttribute<ItemOrdenCompra, Integer> cantidad;

}