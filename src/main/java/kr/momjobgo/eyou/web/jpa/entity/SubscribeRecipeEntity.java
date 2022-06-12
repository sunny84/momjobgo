package kr.momjobgo.eyou.web.jpa.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "SUBSCRIBE_RECIPE")
@Data
public class SubscribeRecipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipe;

    @ManyToOne
    @JoinColumn(name = "subscribe_id")
    private SubscribeEntity subscribe;

}
