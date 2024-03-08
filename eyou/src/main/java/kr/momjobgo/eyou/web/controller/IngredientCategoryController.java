package kr.momjobgo.eyou.web.controller;

import io.swagger.models.Response;
import kr.momjobgo.eyou.web.jpa.entity.IngredientCategoryEntity;
import kr.momjobgo.eyou.web.service.IngredientCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredient-category")
public class IngredientCategoryController {
    private final IngredientCategoryService ingredientCategoryService;

    public IngredientCategoryController(IngredientCategoryService ingredientCategoryService) {
        this.ingredientCategoryService = ingredientCategoryService;
    }

    @GetMapping("/ingredient-category")
    public String ingredientCategory(@RequestParam String param) {
        return param;
    }

    @GetMapping("/ingredient-category/all")
    public ResponseEntity<?> getAll() { return ResponseEntity.ok().body( ingredientCategoryService.getAll()); }

    @GetMapping("/ingredient-category/{id}")
    public ResponseEntity<?> getIngredientCategory(@PathVariable Long id) {
        return ResponseEntity.ok().body( ingredientCategoryService.getIngredientCategory(id) );
    }

    @PostMapping("/ingredient-category")
    public ResponseEntity<?> postIngredientCategory(@RequestParam String title) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientCategoryService.insertIngredientCategory(title));
    }

    @PostMapping("/ingredient-category-entity")
    public ResponseEntity<?> postIngredientCategory(@RequestBody IngredientCategoryEntity entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientCategoryService.insertIngredientCategoryEntity(entity));
    }

    @PatchMapping("/ingredient-category/{id}")
    public ResponseEntity<?> updateIngredientCategory(@RequestBody IngredientCategoryEntity entity) {
        return ResponseEntity.ok().body(ingredientCategoryService.updateIngredientCategory(entity));
    }

    @DeleteMapping("/ingredient-category/{id}")
    public ResponseEntity<?> deleteIngredientCategory(@PathVariable Long id){
        return ResponseEntity.ok().body(ingredientCategoryService.deleteIngredientCategory(id));
    }

    @GetMapping("/ingredient-category/title")
    public ResponseEntity<?> findByTitle(@RequestParam String title) {
        return ResponseEntity.ok().body(ingredientCategoryService.findByTitle(title));
    }

    @GetMapping("/ingredient-category/titles")
    public ResponseEntity<?> findByTitles(@RequestParam String title){
        return ResponseEntity.ok().body(ingredientCategoryService.findByTitles(title));
    }
}

