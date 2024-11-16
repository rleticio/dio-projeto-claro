package com.estudo.pizzaplanet.service;

import com.estudo.pizzaplanet.dto.*;
import com.estudo.pizzaplanet.entities.Product;
import com.estudo.pizzaplanet.entities.ProductVariation;
import com.estudo.pizzaplanet.enums.Category;
import com.estudo.pizzaplanet.mapper.ProductMapper;
import com.estudo.pizzaplanet.repositories.ProductRepository;
import com.estudo.pizzaplanet.repositories.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductVariationRepository productVariationRepository;
    @Autowired
    private ProductMapper productMapper;

    public RecoveryProductDto createProduct(CreateProductDto createProductDto) {

        List<ProductVariation> productVariations = createProductDto.productVariations().stream()
                .map(variation -> productMapper.mapCreateProductVariationDtoToProductVariation(variation))
                .toList();

        /*
            Se o produto estiver com available = false, as variações também devem estar como false.
            !product.getAvailable() se for falso, ! transforma em verdadeiro e continua para a próxima verificação
            .anymatch é uma operação terminal que retorna true or false se algum elemento comparar com o argumento Predicate.
            Predicate é uma functional interface (boolea-valued function of one argument). É aplicado o predicate para cada elemeto da steam.
            Se predicate retornar true para qualquer elemento, imediatamente retorna true.
            Se predicate retornar false para todos os elementos, retorna false.
            Se stream está vazio, retorna falso.
         */
        Product product = Product.builder()
                .name(createProductDto.name())
                .description(createProductDto.description())
                .category(Category.valueOf(createProductDto.category().toUpperCase()))
                .productVariations(productVariations)
                .available(createProductDto.available())
                .build();

        if (!product.getAvailable() && product.getProductVariations().stream().anyMatch(ProductVariation::getAvailable)) {                                              //The ProductVariation::getAvailable is a method reference. It's equivalent to writing (productVariation -> productVariation.getAvailable()).
            throw new RuntimeException("A variação não pode estar disponível se o produto estiver indisponível");
        }

        /*
            Duvida se precisa informar o produto nas productVariations "até então esta nulo" ou se ao salvar o produto, automaticamente é criado a relação
            When you set up a bi-directional relationship between Product and ProductVariation entities, you usually annotate them with @OneToMany and @ManyToOne annotations (or @OneToOne and @ManyToOne if it's a one-to-one relationship). These annotations tell Hibernate how to map the relationship between the two entities.
            When you save the Product entity, Hibernate will also save the ProductVariation entities and automatically set the foreign key (product_id) in the ProductVariation table to refer to the primary key (id) of the Product table. This is known as cascading save operation
            OBS: AO SALVAR O PRODUTO, AS VARIAÇÔES FICARAM COM NULL NO PRODUCT_ID, SENDO NECESSÀRIO SETAR O PRODUTO PARA CADA VARIAÇÃO.
         */
        productVariations.forEach(productVariation -> productVariation.setProduct(product));


        /*
            the save() method in Spring Data's CrudRepository does not return an Optional. Instead, it returns the saved entity itself. This is because the save() method always has a valid entity to return after saving it to the database.
            If you want to handle cases where the entity might not exist, you would typically use the findById() method, which returns an Optional.
         */
        Product productSaved = productRepository.save(product);

        return productMapper.mapProductToRecoveryProductDto(productSaved);
    }

    public RecoveryProductDto createProductVariation(Long productId, CreateProductVariationDto createProductVariationDto) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        ProductVariation productVariation = productMapper.mapCreateProductVariationDtoToProductVariation(createProductVariationDto);

        productVariation.setProduct(product);
        ProductVariation productVariationSaved = productVariationRepository.save(productVariation);

        //
        //product.getProductVariations().add(productVariationSaved);
        //productRepository.save(product);

        return productMapper.mapProductToRecoveryProductDto(productVariationSaved.getProduct());
    }

    public List<RecoveryProductDto> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> productMapper.mapProductToRecoveryProductDto(product)).toList();
    }

    public RecoveryProductDto getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return productMapper.mapProductToRecoveryProductDto(product);
    }

    //Atualiza um produto sem atualizar as variações dele
    public RecoveryProductDto updateProductPart(Long productId, UpdateProductDto updateProductDto){

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (updateProductDto.name() != null) {
            product.setName(updateProductDto.name());
        }

        if (updateProductDto.description() != null){
            product.setDescription(updateProductDto.description());
        }

        if (updateProductDto.available() != null){
            product.setAvailable(updateProductDto.available());

            //se produto available = false, seta false para todas as variações
            if (!product.getAvailable()) {
                product.getProductVariations().forEach(productVariation -> productVariation.setAvailable(false));
            }
        }

        //save retorna produto, diferente do find retorna optional
        return productMapper.mapProductToRecoveryProductDto(productRepository.save(product));
    }

    public RecoveryProductDto updateProductVariation(Long productId, Long productVariationId, UpdateProductVariationDto updateProductVariationDto){

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        //The equals method is not overridden in the ProductVariation class, but it is automatically provided by the Long class.
        //The Long class overrides the equals method from the Object class to provide a proper comparison of the numerical values encapsulated within Long objects.
        //findFirst returns an optional
        ProductVariation productVariation = product.getProductVariations().stream()
                .filter(productVariationInProduct -> productVariationInProduct.getId().equals(productVariationId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Variação não encontrada"));

        if (updateProductVariationDto.sizeName() != null) {
            productVariation.setSizeName(updateProductVariationDto.sizeName());
        }

        if (updateProductVariationDto.description() != null) {
            productVariation.setDescription(updateProductVariationDto.description());
        }

        if (updateProductVariationDto.available() != null) {

            //Se o produto da variação estiver indisponível, a variação deve estar indisponível também
            if (updateProductVariationDto.available() && !productVariation.getProduct().getAvailable()){
                throw new RuntimeException("A variação de tamanho deve estar disponível");
            }
            productVariation.setAvailable(updateProductVariationDto.available());
        }

        if (updateProductVariationDto.price() != null){
            productVariation.setPrice(updateProductVariationDto.price());
        }

        //The cascade = CascadeType.ALL configuration means that any operation (such as persist, merge, refresh, remove) applied to the Product entity will also be cascaded to the ProductVariation entities associated with it. In other words, when you save the Product, the ProductVariation entities will be saved as well
        Product productSaved = productRepository.save(product);

        return productMapper.mapProductToRecoveryProductDto(productSaved);
    }

    public void deleteProductId(Long productId){

        if (!productRepository.existsById(productId)){
            throw new RuntimeException("Producto não encontrado");
        }
        productRepository.deleteById(productId);
    }

    public void deleteProductVariationById(Long productId, Long productVariationId){

        ProductVariation productVariation = productVariationRepository
                .findByProductIdAndProductVariationId(productId, productVariationId)
                .orElseThrow(() -> new RuntimeException("Variação não encontrada para o produto em questão"));

        //apaga pela variação localizada, não pelo parametro passado.
        productVariationRepository.deleteById(productVariation.getId());
    }





}
