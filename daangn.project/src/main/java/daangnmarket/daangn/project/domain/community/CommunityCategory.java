package daangnmarket.daangn.project.domain.community;

import daangnmarket.daangn.project.domain.product.ProductCategory;
import lombok.Getter;

import javax.persistence.*;
import java.util.Arrays;

@Getter
public enum CommunityCategory {
    QUESTION("동네질문"),
    FOOD("동네맛집"),
    FIND("분실/실종센터");

    private final String value;

    CommunityCategory(String value) {
        this.value = value;
    }
    public static CommunityCategory fromCode(String dbData) {
        return Arrays.stream(CommunityCategory.values())
                .filter(v -> v.getValue().equals(dbData))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("커뮤니티 카테고리에 %s가 존재하지 않습니다.", dbData)));
    }
}
