package kr.momjobgo.eyou;

import kr.momjobgo.eyou.web.controller.RecipeBoxController;
import kr.momjobgo.eyou.web.jpa.entity.RecipeBoxEntity;
import kr.momjobgo.eyou.web.jpa.entity.RecipeEntity;
import kr.momjobgo.eyou.web.jpa.entity.UserEntity;
import kr.momjobgo.eyou.web.jpa.repository.RecipeBoxRepository;
import kr.momjobgo.eyou.web.jpa.repository.RecipeRepository;
import kr.momjobgo.eyou.web.service.RecipeBoxService;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @BeforeEach
    void init() {
    }

//    @Disabled
//    @Test
//    void contextLoads() {
//        UserEntity user = new UserEntity();
//        RecipeBoxEntity recipeBox = new RecipeBoxEntity();
//        RecipeEntity recipe = new RecipeEntity();
//
//        // CREATE
//        // UPDATE
////        id 에 해당하는 name 변경은 어떻게 하지?
////        recipeBox.setId(1L);
//        recipeBox.setName("호박레시피");
//        recipeBox.setIsDefault(false);
//        recipeBoxRepository.save(recipeBox);
//
//        recipe.setPeriod("중기");
//        recipe.setOpen("Y");
//        recipe.setQuantity("1회분");
//        recipeBox.add(recipe);
//        recipeRepository.save(recipe);
//
//        // GET
//        recipeRepository.findAll();
//        System.out.println(recipeRepository.count());
//        recipeBoxRepository.findAll();
//        System.out.println(recipeBoxRepository.count());
//
//        // DELETE
////        recipeRepository.deleteById(1L);
////        recipeBoxRepository.deleteById(1L);
////        recipeRepository.deleteAll();
////        recipeBoxRepository.deleteAll();
//    }

    @Test
    void contextLoads() {
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
