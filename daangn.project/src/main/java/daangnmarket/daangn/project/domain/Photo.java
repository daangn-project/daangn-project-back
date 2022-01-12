package daangnmarket.daangn.project.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PHOTO_ID")
    private Long id;

    private String name;

    private String path;

    private Long size;

}
