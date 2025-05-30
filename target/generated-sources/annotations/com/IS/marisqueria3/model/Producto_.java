package com.IS.marisqueria3.model;

import com.IS.marisqueria3.model.CategoriaCarta;
import com.IS.marisqueria3.model.ItemPedido;
import com.IS.marisqueria3.model.ProductoIngrediente;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-05-29T22:03:05", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Producto.class)
public class Producto_ { 

    public static volatile SingularAttribute<Producto, String> descripcion;
    public static volatile ListAttribute<Producto, ProductoIngrediente> productoIngredienteList;
    public static volatile SingularAttribute<Producto, Integer> idPlatillo;
    public static volatile SingularAttribute<Producto, String> unidadMedida;
    public static volatile SingularAttribute<Producto, Float> precioVenta;
    public static volatile SingularAttribute<Producto, String> nombre;
    public static volatile ListAttribute<Producto, ItemPedido> itemPedidoList;
    public static volatile SingularAttribute<Producto, CategoriaCarta> categoriaId;

}