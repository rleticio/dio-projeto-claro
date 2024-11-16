package com.estudo.pizzaplanet.mapper;

import com.estudo.pizzaplanet.dto.CreateProductVariationDto;
import com.estudo.pizzaplanet.dto.RecoveryProductDto;
import com.estudo.pizzaplanet.dto.RecoveryProductVariationDto;
import com.estudo.pizzaplanet.entities.Product;
import com.estudo.pizzaplanet.entities.ProductVariation;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-27T11:09:00-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.8 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public RecoveryProductDto mapProductToRecoveryProductDto(Product product) {
        if ( product == null ) {
            return null;
        }

        List<RecoveryProductVariationDto> productVariations = null;
        Long id = null;
        String name = null;
        String description = null;
        String category = null;
        Boolean available = null;

        productVariations = mapProductVariationToRecoveryProductVariationDto( product.getProductVariations() );
        id = product.getId();
        name = product.getName();
        description = product.getDescription();
        if ( product.getCategory() != null ) {
            category = product.getCategory().name();
        }
        available = product.getAvailable();

        RecoveryProductDto recoveryProductDto = new RecoveryProductDto( id, name, description, category, productVariations, available );

        return recoveryProductDto;
    }

    @Override
    public List<RecoveryProductVariationDto> mapProductVariationToRecoveryProductVariationDto(List<ProductVariation> productVariations) {
        if ( productVariations == null ) {
            return null;
        }

        List<RecoveryProductVariationDto> list = new ArrayList<RecoveryProductVariationDto>( productVariations.size() );
        for ( ProductVariation productVariation : productVariations ) {
            list.add( mapProductVariationToRecoveryProductVariationDto( productVariation ) );
        }

        return list;
    }

    @Override
    public RecoveryProductVariationDto mapProductVariationToRecoveryProductVariationDto(ProductVariation productVariation) {
        if ( productVariation == null ) {
            return null;
        }

        Long id = null;
        String sizeName = null;
        String description = null;
        BigDecimal price = null;
        Boolean available = null;

        id = productVariation.getId();
        sizeName = productVariation.getSizeName();
        description = productVariation.getDescription();
        price = productVariation.getPrice();
        available = productVariation.getAvailable();

        RecoveryProductVariationDto recoveryProductVariationDto = new RecoveryProductVariationDto( id, sizeName, description, price, available );

        return recoveryProductVariationDto;
    }

    @Override
    public ProductVariation mapCreateProductVariationDtoToProductVariation(CreateProductVariationDto createProductVariationDto) {
        if ( createProductVariationDto == null ) {
            return null;
        }

        ProductVariation.ProductVariationBuilder productVariation = ProductVariation.builder();

        productVariation.sizeName( createProductVariationDto.sizeName() );
        productVariation.description( createProductVariationDto.description() );
        productVariation.available( createProductVariationDto.available() );
        productVariation.price( createProductVariationDto.price() );

        return productVariation.build();
    }
}
