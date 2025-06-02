package com.IS.marisqueria3.model;

import com.IS.marisqueria3.model.ItemOrdenCompra;
import com.IS.marisqueria3.model.ProductoIngrediente;
import com.IS.marisqueria3.model.Proveedor;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-06-01T22:37:25", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Ingrediente.class)
public class Ingrediente_ { 

    public static volatile SingularAttribute<Ingrediente, String> descripcion;
    public static volatile SingularAttribute<Ingrediente, Integer> stockMinimo;
    public static volatile ListAttribute<Ingrediente, ProductoIngrediente> productoIngredienteList;
    public static volatile SingularAttribute<Ingrediente, Date> fechaCaducidad;
    public static volatile ListAttribute<Ingrediente, ItemOrdenCompra> itemOrdenCompraList;
    public static volatile SingularAttribute<Ingrediente, Integer> ingredienteId;
    public static volatile SingularAttribute<Ingrediente, Float> precioUnitario;
    public static volatile SingularAttribute<Ingrediente, Proveedor> proveedorId;
    public static volatile SingularAttribute<Ingrediente, String> unidadMedida;
    public static volatile SingularAttribute<Ingrediente, String> nombre;
    public static volatile SingularAttribute<Ingrediente, Integer> stockDisponible;

}