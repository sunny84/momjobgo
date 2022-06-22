package kr.momjobgo.eyou.web.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "MEMBER")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID", nullable = false)
    private Long id;

    @Column(name = "MEMBER_NAME")
    private String name;

//    @OneToMany(fetch = FetchType.EAGER)

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MEMBER_TEACHER",
            joinColumns = @JoinColumn(name="MEMBER_ID"),
            inverseJoinColumns = @JoinColumn(name="TEACHER_ID")
    )
    private List<Teacher> teachers = new ArrayList<Teacher>();

    public void addTeacher(Teacher teacher) {
        teacher.setMember(this);
        this.teachers.add(teacher);
    }
}
