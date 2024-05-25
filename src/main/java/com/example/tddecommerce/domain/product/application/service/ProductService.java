package com.example.tddecommerce.domain.product.application.service;

import com.example.tddecommerce.domain.product.application.business.*;
import com.example.tddecommerce.domain.product.domain.model.DiscountPolicy;
import com.example.tddecommerce.domain.product.domain.model.Product;
import com.example.tddecommerce.domain.product.domain.model.ProductStock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductValidator productValidator;
    private final ProductCreator productCreator;
    private final ProductStockCreator productStockCreator;
    private final ProductReader productReader;
    private final ProductUpdater productUpdater;
    private final ProductDeleter productDeleter;

    /**
     * 상품 등록 및 상품 재고 설정 기능
     */
    public Product addProduct(String name, BigDecimal price, String description, DiscountPolicy discountPolicy, int stock) {
        // 입력 데이터 유효성 검사
        productValidator.createRequestValidate(name, price, stock);

        // 상품 정보를 바탕으로 상품 객체 생성
        Product product = new Product(name, price, description, discountPolicy);

        // 상품을 데이터베이스에 저장
        Product result = productCreator.save(product);

        // 요청된 초기 재고 수량으로 재고 객체 생성
        ProductStock productStock = new ProductStock(result, stock);

        // 재고를 데이터베이스에 저장
        productStockCreator.save(productStock);

        // 추가된 상품 정보 반환
        return result;
    }

    /**
     * 상품 조회기능
     * @param productId
     * @return
     */
    @Transactional(readOnly = true)
    public Product getProduct(Long productId){
        // 상품 하나를 조회합니다.
        try {
            // 상품유무조회
            Optional<Product> result = productReader.selectOne(productId);
            // 상품유무결과
            return result.filter(product -> !product.isDelFlag()).orElseThrow(() -> new ProductException("Product not found or deleted: " + productId));
        } catch (Exception e) {
            log.error("상품 조회 중 오류 발생: {}", e.getMessage());
            throw new ProductException("상품 조회 중 오류 발생", e);
        }
    }

    /**
     * 상품 수정 기능
     * @param productId
     * @param name
     * @param price
     * @param description
     * @param discountPolicy
     * @return
     */
    public Product updateProduct(Long productId, String name, BigDecimal price, String description, DiscountPolicy discountPolicy) {
        // 입력 데이터 유효성 검사
        productValidator.updateRequestValidate(name, price);

        // 상품 유무 조회
        Product product = getProduct(productId);

        // 상품 정보 업데이트
        return productUpdater.updateProduct(product, name, price, description, discountPolicy);
    }


    /**
     * 상품 삭제 기능
     * @param productId
     */
    public void deleteProduct(Long productId) {
        // 상품 유무 조회
        Product product = getProduct(productId);

        // 상품 삭제
        productDeleter.deleteProduct(product);
    }
}
