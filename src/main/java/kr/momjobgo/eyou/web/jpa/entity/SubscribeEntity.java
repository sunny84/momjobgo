package kr.momjobgo.eyou.web.jpa.entity;

import kr.momjobgo.eyou.web.jpa.entity.base.BaseTimeEntity;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "SUBSCRIBE")
@Data
public class SubscribeEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private UserEntity user;

    @OneToMany(mappedBy = "subscribe")
    private List<SubscribeRecipeEntity> subscribeRecipes = new ArrayList<>();
}
