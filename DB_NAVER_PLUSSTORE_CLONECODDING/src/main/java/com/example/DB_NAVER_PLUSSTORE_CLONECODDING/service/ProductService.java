package com.example.DB_NAVER_PLUSSTORE_CLONECODDING.service;

import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.domain.*;
import com.example.DB_NAVER_PLUSSTORE_CLONECODDING.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void createProduct(Map<String, Object> body) {
        Long sellerId = Long.valueOf(body.get("sellerId").toString());
        String name = (String) body.get("name");
        BigDecimal price = new BigDecimal(body.get("price").toString());
        Integer stock = Integer.valueOf(body.get("stock").toString());
        String imageUrl = body.get("imageUrl") != null ? (String) body.get("imageUrl") : null;

        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("판매자 없음"));

        Category category;
        if (body.get("categoryId") != null && !body.get("categoryId").toString().isEmpty()) {
            Long catId = Long.valueOf(body.get("categoryId").toString());
            category = categoryRepository.findById(catId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리"));
        } else if (body.get("newCategoryName") != null) {
            String newCatName = (String) body.get("newCategoryName");
            category = Category.builder().seller(seller).categoryName(newCatName).build();
            categoryRepository.save(category);
        } else {
            throw new IllegalArgumentException("카테고리 필수");
        }

        Product product = Product.builder()
                .seller(seller)
                .category(category)
                .name(name)
                .price(price)
                .stock(stock)
                .imageUrl(imageUrl)
                .build();

        List<Map<String, Object>> optionsData = (List<Map<String, Object>>) body.get("options");
        if (optionsData != null) {
            List<ProductOption> options = optionsData.stream().map(opt -> ProductOption.builder()
                    .product(product)
                    .optionName((String) opt.get("optionName"))
                    .extraPrice(new BigDecimal(opt.get("extraPrice").toString()))
                    .stock(Integer.valueOf(opt.get("stock").toString()))
                    .build()
            ).toList();
            product.setOptions(options);
        }

        productRepository.save(product);
    }
}