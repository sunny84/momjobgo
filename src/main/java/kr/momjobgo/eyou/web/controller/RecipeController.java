package kr.momjobgo.eyou.web.controller;

import kr.momjobgo.eyou.config.security.UserManager;
import kr.momjobgo.eyou.web.dto.RecipeRequest;
import kr.momjobgo.eyou.web.service.RecipeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Recipe")
public class RecipeController {
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
     }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body( recipeService.getAll() );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) { return ResponseEntity.ok().body( recipeService.getById(id) ); }

    @GetMapping("/contents={contentsId}")
    public ResponseEntity<?> getByContentsId(@PathVariable Long contentsId) { return ResponseEntity.ok().body( recipeService.getByContentsId(contentsId) ); }
    
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getDetailById(@PathVariable Long id) {
        if(!recipeService.getDetailById(id).isEmpty())
            return ResponseEntity.ok().body(recipeService.getDetailById(id));
        else
            return ResponseEntity.notFound().build();
    }
}
