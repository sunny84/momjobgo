package kr.momjobgo.eyou.web.controller;

import kr.momjobgo.eyou.web.jpa.entity.RecipeBoxEntity;
import kr.momjobgo.eyou.web.service.RecipeBoxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipebox")
public class RecipeBoxController {
    private final RecipeBoxService recipeBoxService;

    public RecipeBoxController(RecipeBoxService recipeBoxService) { this.recipeBoxService = recipeBoxService; }

    @GetMapping("/join/recipe")
    public ResponseEntity<?> joinRecipe() { return ResponseEntity.ok(recipeBoxService.joinRecipe()); }

    @GetMapping("/box/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body( recipeBoxService.getAll() );
    }

    @GetMapping("/box/{id}")   // id에 해당되는 name 받아오기
    public ResponseEntity<?> getById(@PathVariable Long id) { return ResponseEntity.ok().body( recipeBoxService.getById(id) ); }

    @PostMapping("/box/name")
    public ResponseEntity<?> insertRecipeBoxName(@RequestParam String name){
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeBoxService.insertRecipeBoxName(name));
    }

    @PostMapping("/box/entity")
    public ResponseEntity<?> insertRecipeBox(@RequestBody RecipeBoxEntity recipeBoxEntity){
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeBoxService.insertRecipeBox(recipeBoxEntity));
    }

    @PatchMapping("/box/{id}")
    public ResponseEntity<?> updateRecipeBoxById(@PathVariable Long id, @RequestBody RecipeBoxEntity recipeBoxEntity){
        return ResponseEntity.ok().body(recipeBoxService.updateRecipeBoxById(id, recipeBoxEntity));
    }

    @DeleteMapping("/box/{id}")    // id에 해당되는 데이터 삭제. 없는 id일 경유 500 에러 발생
    public ResponseEntity<?> deleteRecipeBoxById(@PathVariable Long id) {
        return ResponseEntity.ok().body(recipeBoxService.deleteRecipeBoxById(id));
    }

    @DeleteMapping("/box/all")    // id에 해당되는 데이터 삭제. 없는 id일 경유 500 에러 발생
    public ResponseEntity<?> deleteRecipeBoxAll() {
        return ResponseEntity.ok().body(recipeBoxService.deleteRecipeBoxAll());
    }

    @GetMapping("/box/find-name")
    public ResponseEntity<?> getRecipeBoxByName(@RequestParam String name){
        return ResponseEntity.ok().body(recipeBoxService.findByName(name));
    }

    @GetMapping("/box/find-name-contains")
    public ResponseEntity<?> getRecipeBoxByNameContains(@RequestParam String name){
        return ResponseEntity.ok().body(recipeBoxService.findByNameContains(name));
    }
}
