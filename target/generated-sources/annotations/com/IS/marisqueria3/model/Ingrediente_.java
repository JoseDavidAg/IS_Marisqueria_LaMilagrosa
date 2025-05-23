package com.IS.marisqueria3.model;

import com.IS.marisqueria3.model.ItemOrdenCompra;
import com.IS.marisqueria3.model.Producto;
import com.IS.marisqueria3.model.Proveedor;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.12.v20230209-rNA", date="2025-05-23T00:33:47")
@StaticMetamodel(Ingrediente.class)
public class Ingrediente_ { 

    public static volatile SingularAttribute<Ingrediente, String> descripcion;
    public static volatile SingularAttribute<Ingrediente, Integer> stockMinimo;
    public static volatile SingularAttribute<Ingrediente, Date> fechaCaducidad;
    public static volatile ListAttribute<Ingrediente, ItemOrdenCompra> itemOrdenCompraList;
    public static volatile SingularAttribute<Ingrediente, BigDecimal> precioUnitario;
    public static volatile SingularAttribute<Ingrediente, Proveedor> proveedorId;
    public static volatile SingularAttribute<Ingrediente, String> unidadMedida;
    public static volatile ListAttribute<Ingrediente, Producto> productoList;
    public static volatile SingularAttribute<Ingrediente, Integer> codigoProducto;
    public static volatile SingularAttribute<Ingrediente, String> nombre;
    public static volatile SingularAttribute<Ingrediente, Integer> stockDisponible;

}