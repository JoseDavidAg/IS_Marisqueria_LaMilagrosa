package com.IS.marisqueria3.model;

import com.IS.marisqueria3.model.Pedido;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-05-23T00:33:47")
@StaticMetamodel(Ticket.class)
public class Ticket_ { 

    public static volatile SingularAttribute<Ticket, String> metodoPago;
    public static volatile SingularAttribute<Ticket, BigDecimal> iva;
    public static volatile SingularAttribute<Ticket, BigDecimal> subtotal;
    public static volatile SingularAttribute<Ticket, Date> fechaGeneracion;
    public static volatile SingularAttribute<Ticket, Pedido> pedidoNumero;
    public static volatile SingularAttribute<Ticket, BigDecimal> totalPagar;
    public static volatile SingularAttribute<Ticket, Integer> numeroTicket;

}