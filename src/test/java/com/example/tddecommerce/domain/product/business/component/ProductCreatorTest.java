package com.example.tddecommerce.domain.product.business.component;

import com.example.tddecommerce.domain.product.application.CreateProductRequest;
import com.example.tddecommerce.domain.product.business.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.business.repository.IProductRepository;
import com.example.tddecommerce.domain.product.business.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductCreatorTest {

    private IProductRepository iProductRepository;

    private ProductCreator productCreator;

    @BeforeEach
    void setUp() {
        iProductRepository = mock(IProductRepository.class);
        productCreator = new ProductCreator(iProductRepository);
    }

    @Test
    void testSaveProduct() {
        // given
        CreateProductRequest createProductRequest = new CreateProductRequest("Test Product", new BigDecimal("199.99"), "Test Description", DiscountPolicy.NONE);
        Product product = new Product("Test Product", new BigDecimal("199.99"), "Test Description", DiscountPolicy.NONE);
        when(iProductRepository.save(any(Product.class))).thenReturn(product);

        // when
        Product createdProduct = productCreator.execute(createProductRequest.getName(),createProductRequest.getPrice(),createProductRequest.getDescription(),createProductRequest.getDiscountPolicy());

        // then
        assertNotNull(createdProduct);
        assertEquals(product, createdProduct);
        verify(iProductRepository, times(1)).save(any(Product.class));
    }
    private static class ProductFixture {

        public static CreateProductRequest createProductRequest() {
            return null;
        }
    }


}
