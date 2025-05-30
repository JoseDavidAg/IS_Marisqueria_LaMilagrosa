package com.IS.marisqueria3.model;

import com.IS.marisqueria3.model.Cliente;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-05-29T22:03:05", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Mesa.class)
public class Mesa_ { 

    public static volatile SingularAttribute<Mesa, Integer> idMesa;
    public static volatile SingularAttribute<Mesa, Cliente> clienteId;
    public static volatile SingularAttribute<Mesa, Boolean> estaDisponible;

}