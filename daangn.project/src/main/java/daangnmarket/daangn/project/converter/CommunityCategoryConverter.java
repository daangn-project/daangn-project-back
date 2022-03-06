package daangnmarket.daangn.project.converter;

import daangnmarket.daangn.project.domain.community.CommunityCategory;
import daangnmarket.daangn.project.domain.product.ProductCategory;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
@Slf4j
public class CommunityCategoryConverter implements AttributeConverter<CommunityCategory, String> {
    @Override
    public String convertToDatabaseColumn(CommunityCategory communityCategory)

    {
        if(communityCategory == null) return null;
        return communityCategory.getValue();
    }

    @Override
    public CommunityCategory convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return CommunityCategory.fromCode(dbData);
        } catch (IllegalArgumentException e) {
            log.error("failure to convert cause unexpected code [{}]", dbData, e);
            throw e;
        }
    }
}
