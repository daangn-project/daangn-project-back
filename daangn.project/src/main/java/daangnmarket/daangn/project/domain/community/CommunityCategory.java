package daangnmarket.daangn.project.domain.community;

import com.fasterxml.jackson.annotation.JsonValue;
import daangnmarket.daangn.project.domain.product.ProductCategory;
import lombok.Getter;

import javax.persistence.*;
import java.util.Arrays;

@Getter
public enum CommunityCategory {
    QUESTION("동네질문"),
    FOOD("동네맛집"),
    FIND("분실/실종센터"),
    INVEST("투자/주식"),
    LOVE("썸/연애"),
    CAREER("진로/직장"),
    HEALTH("헬스/건강"),
    ANNOUNCE("동네알림"),
    HOBBY("취미");

    @JsonValue
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
