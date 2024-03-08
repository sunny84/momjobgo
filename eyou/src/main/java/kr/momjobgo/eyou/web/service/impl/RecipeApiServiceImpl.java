package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.config.security.UserManager;
import kr.momjobgo.eyou.web.dto.RecipeRequest;
import kr.momjobgo.eyou.web.jpa.entity.ContentsEntity;
import kr.momjobgo.eyou.web.jpa.entity.RecipeEntity;
import kr.momjobgo.eyou.web.jpa.repository.*;
import kr.momjobgo.eyou.web.service.RecipeApiService;
import kr.momjobgo.eyou.web.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RecipeApiServiceImpl implements RecipeApiService {
    private final RecipeRepository recipeRepository;
    private final ContentsRepository contentsRepository;
    private final FileRepository fileRepository;
    private final IngredientRepository ingredientRepository;
    @Autowired
    private RecipeIngredientMapRepository recipeIngredientMapRepository;

    @Autowired
    private TipRepository tipRepository;

    @Autowired
    private CookingOrderRepository cookingOrderRepository;

    public RecipeApiServiceImpl(RecipeRepository recipeRepository, ContentsRepository contentsRepository,
        FileRepository fileRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.contentsRepository = contentsRepository;
        this.fileRepository = fileRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Transactional
    @CrossOrigin
    public Map<String, Object> write(HttpServletRequest req, @RequestBody RecipeRequest request) {

        request.getContentsEntity().setWriter(UserManager.getUser().getId());
        ContentsEntity entity = contentsRepository.save(request.getContentsEntity());

        System.out.println("entity: "+ entity);

        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setContentsEntity(new ContentsEntity());
        recipeEntity.getContentsEntity().setId(entity.getId());
        recipeEntity.setPeriod(request.getPeriod());
        recipeEntity.setQuantity(request.getQuantity());
        recipeEntity.setTimeTakenId(request.getTimeTakenId());
        recipeEntity.setOpen(request.getOpen());

        System.out.println("clipLink : "+request.getClipLink());

        recipeEntity.setClipLink(request.getClipLink());
        recipeEntity.setYoutubeLink(request.getYoutubeLink());

        RecipeEntity resultRecipe = recipeRepository.save(recipeEntity);

        request.getRecipeIngredientMapEntities().stream().forEach(item -> {
            item.setRecipeId(resultRecipe.getId());
            recipeIngredientMapRepository.save(item);
        });

        System.out.println(request.getTipEntities()==null);
        if(request.getTipEntities()!=null && !request.getTipEntities().isEmpty()) {
            request.getTipEntities().stream().forEach(item -> {
                item.setRecipeId(resultRecipe.getId());
                tipRepository.save(item);
            });
        }

        request.getCookingOrderEntities().stream().forEach(item -> {
            item.setRecipeId(resultRecipe.getId());
            cookingOrderRepository.save(item);
        });

        Map<String, Object> result = new HashMap<>();
        result.put("contentsId", entity.getId());
        result.put("recipeId", resultRecipe.getId());

        return result;
    }

    public String updateOpen(Long id) {
        String result = "Fail to update open";
        Optional<RecipeEntity> recipeEntity = recipeRepository.findById(id);

        if (recipeEntity.isPresent()) {
            if (recipeEntity.get().getOpen()) {
                recipeEntity.get().setOpen(false);
            } else {
                recipeEntity.get().setOpen(true);
            }
            recipeRepository.save(recipeEntity.get());
            result = "Success to update open";
        }
        return result;
    }
}
