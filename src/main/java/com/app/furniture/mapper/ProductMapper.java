package com.app.furniture.mapper;

import com.app.furniture.dto.PageResponse;
import com.app.furniture.dto.ProductRequest;
import com.app.furniture.dto.ProductResponse;
import com.app.furniture.entity.Product;
import com.app.furniture.util.FileStorageUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper
public interface ProductMapper {

    @Mapping(target = "category.name", source = "category")
    Product toProduct(ProductRequest productRequest);

//    void toProduct(ProductRequest productRequest,@MappingTarget Product product);

    default ProductResponse toProductResponse(Product product) {
        if (product.getIsFavorite() == null) {
            product.setIsFavorite(false);
        }
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory().getName(),
                product.getDescription(),
                product.getPrice(),
//                product.getStockQuantity(),
                product.getIsAvailable(),
                product.getIsFavorite(),
                FileStorageUtil.readFileToByteArray(product.getImage())
        );
    }

    PageResponse<ProductResponse> toPageResponse(Page<Product> products);

}
