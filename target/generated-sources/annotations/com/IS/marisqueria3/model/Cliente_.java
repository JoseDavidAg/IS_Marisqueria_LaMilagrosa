package com.IS.marisqueria3.model;

import com.IS.marisqueria3.model.Mesa;
import com.IS.marisqueria3.model.Pedido;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-05-23T00:33:47")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile SingularAttribute<Cliente, Integer> idCliente;
    public static volatile ListAttribute<Cliente, Mesa> mesaList;
    public static volatile ListAttribute<Cliente, Pedido> pedidoList;
    public static volatile SingularAttribute<Cliente, String> telefono;
    public static volatile SingularAttribute<Cliente, String> nombre;
    public static volatile SingularAttribute<Cliente, String> email;

}