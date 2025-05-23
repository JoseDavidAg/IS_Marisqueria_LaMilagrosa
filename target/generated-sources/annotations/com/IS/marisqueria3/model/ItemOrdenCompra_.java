package com.IS.marisqueria3.model;

import com.IS.marisqueria3.model.Ingrediente;
import com.IS.marisqueria3.model.OrdenCompra;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-05-23T00:33:47")
@StaticMetamodel(ItemOrdenCompra.class)
public class ItemOrdenCompra_ { 

    public static volatile SingularAttribute<ItemOrdenCompra, Integer> idItemOrden;
    public static volatile SingularAttribute<ItemOrdenCompra, OrdenCompra> ordenCompraNumero;
    public static volatile SingularAttribute<ItemOrdenCompra, Ingrediente> ingredienteCodigo;
    public static volatile SingularAttribute<ItemOrdenCompra, Integer> cantidad;

}