package kr.momjobgo.eyou.web.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TEACHER_ID", nullable = false)
    private Long id;

    @Column(name = "TEACHER_NAME")
    private String name;

    public void setMember(Member member) {
    }
}
