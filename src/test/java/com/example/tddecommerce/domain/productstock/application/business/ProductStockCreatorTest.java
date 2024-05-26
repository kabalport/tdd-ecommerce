package com.example.tddecommerce.domain.productstock.application.business;

import com.example.tddecommerce.domain.productstock.application.model.ProductStock;
import com.example.tddecommerce.domain.productstock.application.repository.IProductStockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class ProductStockCreatorTest {

    @Mock
    private IProductStockRepository productStockRepository;

    @InjectMocks
    private ProductStockCreator productStockCreator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveProductStock() {
        // Given
        ProductStock productStock = new ProductStock();

        // When
        productStockCreator.save(productStock);

        // Then
        verify(productStockRepository, times(1)).save(productStock);
    }
}
