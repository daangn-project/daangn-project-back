package daangnmarket.daangn.project.converter;

import daangnmarket.daangn.project.domain.product.ProductCategory;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@Slf4j
public class ProductCategoryConverter implements AttributeConverter<ProductCategory, String> {

    @Override
    public String convertToDatabaseColumn(ProductCategory productCategory) {
        if(productCategory == null) return null;
        return productCategory.getValue();
    }

    @Override
    public ProductCategory convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return ProductCategory.fromCode(dbData);
        } catch (IllegalArgumentException e) {
            log.error("failure to convert cause unexpected code [{}]", dbData, e);
            throw e;
        }
    }
}
