package daangnmarket.daangn.project.domain.community;

import lombok.Getter;

import javax.persistence.*;

public enum CommunityCategory {
    QUESTION("동네질문"),
    FOOD("동네맛집"),
    FIND("분실/실종센터");

    private final String value;

    CommunityCategory(String value) {
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
}
