package kr.momjobgo.eyou.web.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "RECIPE")
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "contents_id")
    private String contentsId;

    @Column(name = "period")
    private String period;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "time_taken_id")
    private String timeTakenId;

    @Column(name = "open")
    private String open;

    @Column(name = "clip_link")
    private String clipLink;

    @Column(name = "youtube_link")
    private String youtubeLink;

    public void setRecipeBox(RecipeBoxEntity recipeBox) {
    }
}