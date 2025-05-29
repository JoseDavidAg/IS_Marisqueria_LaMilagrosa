package com.IS.marisqueria3.model;

import com.IS.marisqueria3.model.ItemPedidoPK;
import com.IS.marisqueria3.model.Pedido;
import com.IS.marisqueria3.model.Producto;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-05-29T01:54:34", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(ItemPedido.class)
public class ItemPedido_ { 

    public static volatile SingularAttribute<ItemPedido, String> descripcion;
    public static volatile SingularAttribute<ItemPedido, Pedido> pedido;
    public static volatile SingularAttribute<ItemPedido, ItemPedidoPK> itemPedidoPK;
    public static volatile SingularAttribute<ItemPedido, Integer> cantidad;
    public static volatile SingularAttribute<ItemPedido, Producto> producto;

}