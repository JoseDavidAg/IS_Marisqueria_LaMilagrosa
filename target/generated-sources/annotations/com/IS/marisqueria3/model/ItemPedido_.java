package com.IS.marisqueria3.model;

import com.IS.marisqueria3.model.ItemPedidoPK;
import com.IS.marisqueria3.model.Pedido;
import com.IS.marisqueria3.model.Producto;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-05-23T00:33:47")
@StaticMetamodel(ItemPedido.class)
public class ItemPedido_ { 

    public static volatile SingularAttribute<ItemPedido, Pedido> pedido;
    public static volatile SingularAttribute<ItemPedido, ItemPedidoPK> itemPedidoPK;
    public static volatile SingularAttribute<ItemPedido, Integer> cantidad;
    public static volatile SingularAttribute<ItemPedido, Producto> producto;

}