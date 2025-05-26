package com.IS.marisqueria3.model;

import com.IS.marisqueria3.model.Cliente;
import com.IS.marisqueria3.model.ItemPedido;
import com.IS.marisqueria3.model.Ticket;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-05-26T05:16:45", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Pedido.class)
public class Pedido_ { 

    public static volatile SingularAttribute<Pedido, String> estado;
    public static volatile ListAttribute<Pedido, Ticket> ticketList;
    public static volatile SingularAttribute<Pedido, Cliente> clienteId;
    public static volatile SingularAttribute<Pedido, Date> fechaEntrega;
    public static volatile SingularAttribute<Pedido, Date> fechaGeneracion;
    public static volatile SingularAttribute<Pedido, Integer> numeroPedido;
    public static volatile SingularAttribute<Pedido, Boolean> esUrgente;
    public static volatile SingularAttribute<Pedido, String> tipoPedido;
    public static volatile ListAttribute<Pedido, ItemPedido> itemPedidoList;

}