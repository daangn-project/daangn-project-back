package daangnmarket.daangn.project.domain;

import lombok.Getter;

import javax.persistence.*;

public enum CommunityCategory {
    QUESTION("동네질문"),
    FOOD("동네맛집");

    private final String name;

    CommunityCategory(String name) {
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
