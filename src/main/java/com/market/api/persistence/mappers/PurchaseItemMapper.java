package com.market.api.persistence.mappers;

import com.market.api.domain.PurchaseItem;
import com.market.api.persistence.entities.ComprasProducto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface PurchaseItemMapper {
//    cuando el source y el target se llaman exactamente iguales no es necesario el mapping
    @Mappings({
            @Mapping(source = "id.idProducto", target = "productId"),
            @Mapping(source = "cantidad", target = "quantity"),
            @Mapping(source = "estado", target = "active")
    })
    PurchaseItem toPurchaseItem(ComprasProducto comprasProducto);

//    Es importante que TODOS aquellos atributos que no esten mapeados arriba en el map inverso se ignoren
//    SI en el ignore se hace uso de otro mapper, asi sea para solo ignorarlo se debe usar (uses={ProductMapper.class})
    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "compra", ignore = true),
            @Mapping(target = "producto", ignore = true),
            @Mapping(target = "id.idCompra", ignore = true),
    })
    ComprasProducto toComprasProducto(PurchaseItem purchaseItem);
}
