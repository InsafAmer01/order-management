package com.example.AssTwo.service.impl;


import com.example.AssTwo.dto.ProductDto;
import com.example.AssTwo.entity.Product;
import com.example.AssTwo.exception.ResourceNotFoundException;
import com.example.AssTwo.repository.ProductRepository;
import com.example.AssTwo.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * It is used to mark the class as a service provider.
 * So overall @Service annotation is used with classes that provide some business functionalities.
 * Spring context will autodetect these classes when annotation-based configuration and classpath scanning is used

 */
@Service //To enable this class for component scanning
public class ProductServiceImpl implements ProductService {

    private com.example.AssTwo.repository.ProductRepository ProductRepository;

    public ProductServiceImpl(ProductRepository ProductRepository) {
        this.ProductRepository = ProductRepository;
    }

    @Override
    public ProductDto createProduct(ProductDto ProductDto) {
        // convert DTO to entity
        Product product = mapToEntity(ProductDto);
        Product newProduct = ProductRepository.save(product);

        // convert entity to DTO
        ProductDto ProductResponse = mapToDTO(newProduct);
        return ProductResponse;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> Products = ProductRepository.findAll();
        return Products.stream().map(Product -> mapToDTO(Product))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(long id) {
        Product product = ProductRepository.findById((int) id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        return mapToDTO(product);
    }

    @Override
    public ProductDto updateProduct(ProductDto ProductDto, long id) {
        // get Product by id from the database
        Product Product = ProductRepository.findById((int) id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        Product.setName(ProductDto.getName());
        Product.setSlug(ProductDto.getSlug());
        Product.setPrice(ProductDto.getPrice());
        Product.setReference(ProductDto.getReference());
        Product.setVat(ProductDto.getVat());

        Product updatedProduct = ProductRepository.save(Product);
        return mapToDTO(updatedProduct);
    }

    @Override
    public void deleteProductById(long id) {
        // get Product by id from the database
        Product Product = ProductRepository.findById((int) id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        ProductRepository.delete(Product);
    }

    // convert Entity into DTO
    private ProductDto mapToDTO(Product Product) {
        ProductDto ProductDto = new ProductDto();
        ProductDto.setId(Product.getId());
        ProductDto.setName(Product.getName());
        ProductDto.setPrice(Product.getPrice());
        ProductDto.setReference(Product.getReference());
        ProductDto.setSlug(Product.getSlug());
        ProductDto.setVat(Product.getVat());
        return ProductDto;
    }

    // convert DTO to entity
    private Product mapToEntity(ProductDto ProductDto) {
        Product Product = new Product();
        Product.setId(ProductDto.getId());
        Product.setName(ProductDto.getName());
        Product.setReference(ProductDto.getReference());
        Product.setVat(ProductDto.getVat());
        Product.setSlug(ProductDto.getSlug());
        Product.setPrice(ProductDto.getPrice());

        return Product;
    }
}
