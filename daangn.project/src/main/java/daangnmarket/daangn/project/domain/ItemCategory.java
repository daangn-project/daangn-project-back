package daangnmarket.daangn.project.domain;

import lombok.Getter;
import java.util.Arrays;

@Getter
public enum ItemCategory {
    SPORTS("운동기구"),
    CLOTHES("의류");

    private final String value;

    ItemCategory(String value) {
        this.value = value;
    }

    public static ItemCategory fromCode(String dbData) {
        return Arrays.stream(ItemCategory.values())
                .filter(v -> v.getValue().equals(dbData))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("아이템 카테고리에 %s가 존재하지 않습니다.", dbData)));
    }
}
