package com.IS.marisqueria3.model;

import com.IS.marisqueria3.model.Cliente;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-05-23T00:33:47")
@StaticMetamodel(Mesa.class)
public class Mesa_ { 

    public static volatile SingularAttribute<Mesa, Integer> idMesa;
    public static volatile SingularAttribute<Mesa, Cliente> clienteId;
    public static volatile SingularAttribute<Mesa, Boolean> estaDisponible;

}