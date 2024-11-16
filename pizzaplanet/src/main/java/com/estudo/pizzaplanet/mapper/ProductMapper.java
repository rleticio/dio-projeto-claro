package com.estudo.pizzaplanet.mapper;

//MapStruct generates bean mappings at compile-time, eliminating the need for manual mapping code. This significantly reduces the amount of boilerplate code typically involved in mapping between different object models, such as entities and DTOs
//Need to add depedency to build file,for maven in pom.xml:
//<properties>
//    <org.mapstruct.version>1.5.5.Final</org.mapstruct.version>
//    <org.projectlombok.version>0.2.0</org.projectlombok.version>
//</properties>
//
//<dependencies>
//    <dependency>
//        <groupId>org.mapstruct</groupId>
//        <artifactId>mapstruct</artifactId>
//        <version>${org.mapstruct.version}</version>
//    </dependency>
//    <dependency>
//        <groupId>org.projectlombok</groupId>
//        <artifactId>lombok-mapstruct-binding</artifactId>
//        <version>${org.projectlombok.version}</version>
//    </dependency>
//</dependencies>

//need to add MapStruct processor enabled
//<build>
//        <plugins>
//        <plugin>
//        <groupId>org.apache.maven.plugins</groupId>
//        <artifactId>maven-compiler-plugin</artifactId>
//        <version>3.8.1</version> <!-- Adjust the version if needed -->
//        <configuration>
//        <source>${java.version}</source>
//        <target>${java.version}</target>
//        <annotationProcessorPaths>
//        <path>
//        <groupId>org.mapstruct</groupId>
//        <artifactId>mapstruct-processor</artifactId>
//        <version>${org.mapstruct.version}</version>
//        </path>
//        <path>
//        <groupId>org.projectlombok</groupId>
//        <artifactId>lombok</artifactId>
//        <version>1.18.26</version>
//        </path>
//        <path>
//        <groupId>org.projectlombok</groupId>
//        <artifactId>lombok-mapstruct-binding</artifactId>
//        <version>0.2.0</version>
//       </path>
//        </annotationProcessorPaths>
//       </configuration>
//        </plugin>
//        <plugin>
//        <groupId>org.springframework.boot</groupId>
//        <artifactId>spring-boot-maven-plugin</artifactId>
//        <configuration>
//        <excludes>
//        <exclude>
//        <groupId>org.projectlombok</groupId>
//        <artifactId>lombok</artifactId>
//        </exclude>
//        </excludes>
//        </configuration>
//        </plugin>
//        </plugins>
//        </build>

import com.estudo.pizzaplanet.dto.CreateProductVariationDto;
import com.estudo.pizzaplanet.dto.RecoveryProductDto;
import com.estudo.pizzaplanet.dto.RecoveryProductVariationDto;
import com.estudo.pizzaplanet.entities.Product;
import com.estudo.pizzaplanet.entities.ProductVariation;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

//The @Mapper(componentModel = "spring") annotation tells MapStruct to generate a Spring bean for the mapper interface. This means that you can inject the mapper into other Spring components using dependency injection with @Autowired
@Mapper(componentModel = "spring")
public interface ProductMapper {

    //The @Mapping annotation in MapStruct is used to configure the mapping of one bean attribute or enum constant. It is applied to methods in mapper interfaces to specify how to map properties from the source object to the target object.
    //- target: Specifies the name of the property in the target object that should be mapped. (left)
    //- source: Specifies the name of the property in the source object to map. This is optional and defaults to the property name specified in target.
    //- qualifiedByName: Specifies a method to use for mapping the property. This method must be defined in the same mapper interface and must return the desired type.
    //- expression: Specifies a Java expression to use for mapping the property. This is useful for performing simple transformations or calculations on the source property.
    //- constant: Specifies a constant value to assign to the target property.
    @Mapping(target = "productVariations", qualifiedByName = "mapProductVariationToRecoveryProductVariationDto")
    RecoveryProductDto mapProductToRecoveryProductDto(Product product);

    //@Named - This annotation is used to give a name to a mapping method, who can be referenced in others places like attribute "qualifiedByName" in annotation "Mapping".
    //IterableMapping(qualifiedByName ...) used in mapping methods who deal with iterable types(List, set,..) define a method to be used for each element inside iterable.
    @Named("mapProductVariationToRecoveryProductVariationDto")
    @IterableMapping(qualifiedByName = "mapProductVariationToRecoveryProductVariationDto")
    List<RecoveryProductVariationDto> mapProductVariationToRecoveryProductVariationDto(List<ProductVariation> productVariations);

    @Named("mapProductVariationToRecoveryProductVariationDto")
    RecoveryProductVariationDto mapProductVariationToRecoveryProductVariationDto(ProductVariation productVariation);

    //@Named("mapCreateProductVariationDtoToProductVariation")
    ProductVariation mapCreateProductVariationDtoToProductVariation(CreateProductVariationDto createProductVariationDto);


    //@Mapping(target = "productVariations", qualifiedByName = "mapCreateProductVariationDtoToProductVariation")
    //Product mapCreateProductDtoToProduct(CreateProductDto createProductDto);

    //@Named("mapCreateProductVariationDtoToProductVariation")
    //@IterableMapping(qualifiedByName = "mapCreateProductVariationDtoToProductVariation")
    //List<ProductVariation> mapCreateProductVariationDtoToProductVariation(List<CreateProductVariation> createProductVariation);
}
