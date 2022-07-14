package kr.momjobgo.eyou.web.jpa.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "TIP")
public class TipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable=false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(name = "text")
    private String text;

    @Column(name = "order")
    private Byte order;
}
