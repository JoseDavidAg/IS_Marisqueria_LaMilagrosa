package com.IS.marisqueria3.model;

import com.IS.marisqueria3.model.Pedido;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-05-29T22:03:05", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Ticket.class)
public class Ticket_ { 

    public static volatile SingularAttribute<Ticket, String> metodoPago;
    public static volatile SingularAttribute<Ticket, BigInteger> iva;
    public static volatile SingularAttribute<Ticket, BigInteger> subtotal;
    public static volatile SingularAttribute<Ticket, Date> fechaGeneracion;
    public static volatile SingularAttribute<Ticket, Pedido> pedidoNumero;
    public static volatile SingularAttribute<Ticket, BigInteger> totalPagar;
    public static volatile SingularAttribute<Ticket, Integer> numeroTicket;

}