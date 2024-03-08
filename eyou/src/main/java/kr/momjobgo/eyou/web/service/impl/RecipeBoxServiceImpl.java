package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.config.security.UserManager;
import kr.momjobgo.eyou.web.common.GetDateTime;
import kr.momjobgo.eyou.web.jpa.entity.*;
import kr.momjobgo.eyou.web.jpa.repository.*;
import kr.momjobgo.eyou.web.service.RecipeBoxService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeBoxServiceImpl implements RecipeBoxService {
    private final RecipeBoxRepository recipeBoxRepository;
    private final RecipeRepository recipeRepository;
    private final FileRepository fileRepository;
    private final GetDateTime getDateTime;
    ScoreRepository scoreRepository;
    TimeTakenRepository timeTakenRepository;

    public RecipeBoxServiceImpl(RecipeBoxRepository recipeBoxRepository,
                                RecipeRepository recipeRepository,
                                FileRepository fileRepository,
                                GetDateTime getDateTime,
                                ScoreRepository scoreRepository,
                                TimeTakenRepository timeTakenRepository) {
        this.recipeBoxRepository = recipeBoxRepository;
        this.recipeRepository = recipeRepository;
        this.fileRepository = fileRepository;
        this.getDateTime = getDateTime;
        this.scoreRepository = scoreRepository;
        this.timeTakenRepository = timeTakenRepository;
    }

    @Override
    public List<RecipeBoxEntity> joinRecipe() {
        return recipeBoxRepository.findAll();
    }

    @Override
    public List<RecipeBoxEntity> getAll() {
        return recipeBoxRepository.findAll();
    }

    @Override
    public RecipeBoxEntity getById(Long id) {

        Optional<RecipeBoxEntity> recipeBoxEntity = recipeBoxRepository.findById(id);

        if (recipeBoxEntity.isPresent()) {
            return recipeBoxEntity.get();
        } else {
            return null;
        }
    }

    @Override
    public RecipeBoxEntity insertRecipeBoxName(String name) {

        RecipeBoxEntity recipeBoxEntity = new RecipeBoxEntity();
        recipeBoxEntity.setName(name);
        recipeBoxEntity.setUserId(UserManager.getUser().getId());
        recipeBoxEntity.setIsDefault(false);
        return recipeBoxRepository.save(recipeBoxEntity);
    }

    @Override
    public RecipeBoxEntity insertRecipeBox() {
        RecipeBoxEntity recipeBoxEntity = new RecipeBoxEntity();
        recipeBoxEntity.setName("default");
        recipeBoxEntity.setUserId(UserManager.getUser().getId());
        recipeBoxEntity.setIsDefault(true);
        return recipeBoxRepository.save(recipeBoxEntity);
    }

    @Override
    public RecipeBoxEntity updateRecipeBox(Long id, String name) {
        Optional<RecipeBoxEntity> optional = recipeBoxRepository.findById(id);
        System.out.println(optional);

        RecipeBoxEntity recipeBoxEntity;
        if (optional.isPresent()) {
            System.out.println("===> 1");
            recipeBoxEntity = recipeBoxRepository.getById(id);
            recipeBoxEntity.setName(name);
        } else {
            System.out.println("===> 2");
            recipeBoxEntity = new RecipeBoxEntity();
            recipeBoxEntity.setName(name);
        }
        recipeBoxRepository.save(recipeBoxEntity);
        return recipeBoxEntity;
    }

    @Override
    public String deleteRecipeBoxById(Long id) {
        if (recipeBoxRepository.findById(id).isPresent()) {
            recipeBoxRepository.deleteById(id);
            return "삭제성공";
        } else {
            return "아이디가 존재하지 않음";
        }
    }

    @Override
    public String deleteRecipeBoxByName(String name) {
        if (!recipeBoxRepository.findByName(name).isEmpty()) {
            List<RecipeBoxEntity> recipeBoxEntityList = recipeBoxRepository.findByName(name);
            for (int i = 0; i < recipeBoxEntityList.size(); i++) {
                RecipeBoxEntity recipeBox = recipeBoxEntityList.get(i);
                if (recipeBox.getIsDefault() == true) {
                    return "기본 박스는 삭제 할 수 없습니다.";
                }
            }
            recipeBoxRepository.deleteByName(name);
            return "삭제성공";
        } else {
            return "박스가 존재하지 않음";
        }
    }

    @Override
    public String deleteRecipeBoxAll() {
        return null;
    }

    @Override
    public List<RecipeBoxEntity> findByName(String name) {
        return recipeBoxRepository.findByName(name);
    }

    @Override
    public List<RecipeBoxEntity> findByNameContains(String name) {
        return recipeBoxRepository.findByNameContains(name);
    }

    @Override
    public RecipeBoxEntity findByIsDefault() {
        Long userId = UserManager.getUser().getId();
        return recipeBoxRepository.findByIsDefaultAndUserId(true, userId);
    }

//    @Override
//    public List<RecipeBoxEntity> findByUserId() {
//        return recipeBoxRepository.findByUserId(UserManager.getUser().getId());
//    }

    @Override
    public List<Map<String, Object>> getReceipeBoxList(Pageable pageable) {
        List recipeBoxList = new ArrayList<>();
        List<RecipeBoxEntity> recipeBoxes = recipeBoxRepository.findByUserId(UserManager.getUser().getId(), pageable);
        System.out.println(recipeBoxes);
        Collections.sort(recipeBoxes, (Comparator<RecipeBoxEntity>) (a, b) -> {
                if (a.getIsDefault()) {
                    return -1;
                } else if (b.getIsDefault()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        );

        recipeBoxes.forEach(recipeBox -> {

            Map<String, Object> recipeBoxMap = new HashMap<>();
            recipeBoxMap.put("id", recipeBox.getId());
            recipeBoxMap.put("name", recipeBox.getName());
            recipeBoxMap.put("isDefault", recipeBox.getIsDefault());
            recipeBoxMap.put("new", recipeBox.getCreatedAt().after(this.getDateTime.yesterday())); // 어제 이후 시간이면 true

            List<RecipeEntity> recipes = recipeBox.getRecipeEntities();
            List recipeList = new ArrayList<>();
            recipes.forEach(recipe -> {
                Map<String, Object> recipeMap = new HashMap<>();
                recipeMap.put("id", recipe.getId());
                recipeMap.put("title", recipe.getContentsEntity().getTitle());
                recipeMap.put("subTitle", recipe.getContentsEntity().getSubTitle());
                recipeMap.put("new", recipe.getContentsEntity().getCreatedAt().after(this.getDateTime.yesterday())); // 어제 이후 시간이면 true
                Optional<ScoreEntity> scoreEntity = scoreRepository.findByRecipeId(recipe.getId());
                if (scoreEntity.isPresent()) {
                    recipeMap.put("score", scoreEntity.get().getScore());
                }
                Optional<TimeTakenEntity> timeTakenEntity = timeTakenRepository.findById(recipe.getTimeTakenId());
                if (timeTakenEntity.isPresent()) {
                    recipeMap.put("timeTaken", timeTakenEntity.get().getTime());
                }
                recipeMap.put("period", recipe.getPeriod());
                recipeMap.put("recipeId", recipe.getId());
                Long contentsId = recipe.getContentsId();
                recipeMap.put("contentsId", contentsId);
                recipeMap.put("open", recipe.getOpen());
                List<FileEntity> files = fileRepository.findByContentsId(contentsId);
                files.forEach(file -> {
                    if (file.getFileRealName().startsWith("M")) { // M 으로 시작하는 파일 가져오기
                        recipeMap.put("mainImgId", file.getId());
                    }
                });
                recipeList.add(recipeMap);
                recipeBoxMap.put("recipe", recipeList);
            });
            recipeBoxList.add(recipeBoxMap);
        });
        return recipeBoxList;
    }
}
