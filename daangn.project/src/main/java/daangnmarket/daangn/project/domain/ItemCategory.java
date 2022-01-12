package daangnmarket.daangn.project.domain;


public enum ItemCategory {
    SPORTS("운동기구"),
    CLOTHES("의류");

    private final String name;

    ItemCategory(String name) {
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

}
