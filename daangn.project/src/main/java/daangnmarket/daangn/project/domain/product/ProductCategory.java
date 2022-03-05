package daangnmarket.daangn.project.domain.product;

import lombok.Getter;
import java.util.Arrays;

@Getter
public enum ProductCategory {
    SPORTS("운동기구"),
    CLOTHES("의류");

    private final String value;

    ProductCategory(String value) {
        this.value = value;
    }

    public static ProductCategory fromCode(String dbData) {
        return Arrays.stream(ProductCategory.values())
                .filter(v -> v.getValue().equals(dbData))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("아이템 카테고리에 %s가 존재하지 않습니다.", dbData)));
    }
}
