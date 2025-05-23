package com.IS.marisqueria3.model;

import com.IS.marisqueria3.model.Cliente;
import com.IS.marisqueria3.model.ItemPedido;
import com.IS.marisqueria3.model.Ticket;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-05-23T00:33:47")
@StaticMetamodel(Pedido.class)
public class Pedido_ { 

    public static volatile SingularAttribute<Pedido, String> estado;
    public static volatile SingularAttribute<Pedido, Ticket> ticket;
    public static volatile SingularAttribute<Pedido, Cliente> clienteId;
    public static volatile SingularAttribute<Pedido, Date> fechaGeneracion;
    public static volatile SingularAttribute<Pedido, Date> fechaEntrega;
    public static volatile SingularAttribute<Pedido, Integer> numeroPedido;
    public static volatile SingularAttribute<Pedido, Boolean> esUrgente;
    public static volatile SingularAttribute<Pedido, String> tipoPedido;
    public static volatile ListAttribute<Pedido, ItemPedido> itemPedidoList;

}