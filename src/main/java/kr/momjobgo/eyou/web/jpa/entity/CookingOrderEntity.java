package kr.momjobgo.eyou.web.jpa.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "COOKING_ORDER")
public class CookingOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Long recipeId;

    @Column(name = "contents_no")
    private Long contentsNo;

    @Column(name = "path")
    private String path;

    @Column(name = "file_save_name")
    private String fileSaveName;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "contents")
    private String contents;
}
