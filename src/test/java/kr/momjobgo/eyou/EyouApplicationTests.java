package kr.momjobgo.eyou;

import kr.momjobgo.eyou.web.controller.RecipeBoxController;
import kr.momjobgo.eyou.web.jpa.entity.*;
import kr.momjobgo.eyou.web.jpa.repository.MemberRepository;
import kr.momjobgo.eyou.web.jpa.repository.RecipeBoxRepository;
import kr.momjobgo.eyou.web.jpa.repository.RecipeRepository;
import kr.momjobgo.eyou.web.jpa.repository.TeacherRepository;
import kr.momjobgo.eyou.web.service.MemberService;
import kr.momjobgo.eyou.web.service.RecipeBoxService;
import kr.momjobgo.eyou.web.service.TeacherService;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("local")
class EyouApplicationTests {
    @Autowired
    RecipeBoxRepository recipeBoxRepository;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    RecipeBoxService recipeBoxService;
    @Autowired
    RecipeBoxController recipeBoxController;

    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    TeacherService teacherService;


    @BeforeEach
    void init() {
    }

    @Test
    void contextLoads() {
        Teacher teacher = new Teacher();
//        teacher.setId(1L);
        teacher.setName("김일타");
        teacherRepository.save(teacher);

        Teacher teacher2 = new Teacher();
//        teacher2.setId(2L);
        teacher2.setName("박이타");
        teacherRepository.save(teacher2);

        Member member = new Member();
//        member.setId(1L);
        member.setName("홍길동");
        member.addTeacher(teacher);
        member.addTeacher(teacher2);
        memberRepository.save(member);

//        List<Teacher> teachers = member.getTeachers();// LAZY
//        teachers.get(0).getName();
//
        System.out.println(memberService.findAll());
        System.out.println(teacherService.findAll());
//        memberService.findAllMembersNative();

    }

    @Disabled
    @Test
    void recipeBoxContextLoads() {
        UserEntity user = new UserEntity();
        RecipeBoxEntity recipeBox = new RecipeBoxEntity();
        RecipeEntity recipe = new RecipeEntity();

        // CREATE
        // UPDATE
//        id 에 해당하는 name 변경은 어떻게 하지?
//        recipeBox.setId(1L);
        recipeBox.setName("호박레시피");
        recipeBox.setIsDefault(false);
        recipeBoxRepository.save(recipeBox);

        recipe.setPeriod("중기");
        recipe.setOpen("Y");
        recipe.setQuantity("1회분");
        recipeBox.add(recipe);
        recipeRepository.save(recipe);

        // GET
        recipeRepository.findAll();
        System.out.println(recipeRepository.count());
        recipeBoxRepository.findAll();
        System.out.println(recipeBoxRepository.count());

        // DELETE
//        recipeRepository.deleteById(1L);
//        recipeBoxRepository.deleteById(1L);
//        recipeRepository.deleteAll();
//        recipeBoxRepository.deleteAll();
    }

    @Disabled
    @Test
    void RecipeBox2ContextLoads() {
        List<RecipeBoxEntity> recipeBoxs = new ArrayList<>();
//        recipeBoxService.joinRecipe();
        recipeBoxs = recipeBoxService.getAll();
        System.out.println(recipeBoxs);
//        Long id = 1L;
//        recipeBoxService.getById(id);
//        String name = "당근당근";
//        recipeBoxService.insertRecipeBoxName(name);
//
//        RecipeBoxEntity recipeBox = new RecipeBoxEntity();
//        recipeBox.setId(id);
//        recipeBox.setName(name);
//        recipeBoxService.insertRecipeBox(recipeBox);
//        recipeBoxService.updateRecipeBox(recipeBox);
//
//        System.out.println(recipeBoxService.deleteRecipeBoxById(id));
//        System.out.println(recipeBoxService.deleteRecipeBoxAll());
//
//        recipeBoxService.findByName(name);
//        recipeBoxService.findByNameContains(name);
    }

    @AfterEach
    void clear() {
    }
}
