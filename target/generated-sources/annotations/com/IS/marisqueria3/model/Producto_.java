package com.IS.marisqueria3.model;

import com.IS.marisqueria3.model.CategoriaCarta;
import com.IS.marisqueria3.model.Ingrediente;
import com.IS.marisqueria3.model.ItemPedido;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-05-23T00:33:47")
@StaticMetamodel(Producto.class)
public class Producto_ { 

    public static volatile SingularAttribute<Producto, String> descripcion;
    public static volatile SingularAttribute<Producto, Integer> idPlatillo;
    public static volatile SingularAttribute<Producto, CategoriaCarta> categoriaNombre;
    public static volatile SingularAttribute<Producto, String> unidadMedida;
    public static volatile ListAttribute<Producto, Ingrediente> ingredienteList;
    public static volatile SingularAttribute<Producto, BigDecimal> precioVenta;
    public static volatile SingularAttribute<Producto, String> nombre;
    public static volatile ListAttribute<Producto, ItemPedido> itemPedidoList;

}