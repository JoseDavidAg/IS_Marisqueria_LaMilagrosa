package com.IS.marisqueria3.model;

import com.IS.marisqueria3.model.Ingrediente;
import com.IS.marisqueria3.model.Producto;
import com.IS.marisqueria3.model.ProductoIngredientePK;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-05-29T22:03:05", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(ProductoIngrediente.class)
public class ProductoIngrediente_ { 

    public static volatile SingularAttribute<ProductoIngrediente, ProductoIngredientePK> productoIngredientePK;
    public static volatile SingularAttribute<ProductoIngrediente, Integer> cantidad;
    public static volatile SingularAttribute<ProductoIngrediente, Producto> producto;
    public static volatile SingularAttribute<ProductoIngrediente, Ingrediente> ingrediente;

}