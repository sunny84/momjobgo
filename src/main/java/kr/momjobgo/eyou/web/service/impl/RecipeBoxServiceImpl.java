package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.config.security.UserManager;
import kr.momjobgo.eyou.web.jpa.entity.FileEntity;
import kr.momjobgo.eyou.web.jpa.entity.RecipeBoxEntity;
import kr.momjobgo.eyou.web.jpa.entity.RecipeEntity;
import kr.momjobgo.eyou.web.jpa.repository.*;
import kr.momjobgo.eyou.web.service.RecipeBoxService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeBoxServiceImpl implements RecipeBoxService {
    private final RecipeBoxRepository recipeBoxRepository;
    private final RecipeRepository recipeRepository;
    private final FileRepository fileRepository;

    public RecipeBoxServiceImpl(RecipeBoxRepository recipeBoxRepository,
                                RecipeRepository recipeRepository,
                                FileRepository fileRepository) {
        this.recipeBoxRepository = recipeBoxRepository;
        this.recipeRepository = recipeRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public List<RecipeBoxEntity> joinRecipe() { return recipeBoxRepository.findAll(); }

    @Override
    public List<RecipeBoxEntity> getAll() { return recipeBoxRepository.findAll(); }

    @Override
    public RecipeBoxEntity getById(Long id){

        Optional<RecipeBoxEntity> recipeBoxEntity = recipeBoxRepository.findById(id);

        if(recipeBoxEntity.isPresent()){
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
        if(optional.isPresent()) {
            System.out.println("===> 1");
            recipeBoxEntity = recipeBoxRepository.getById(id);
            recipeBoxEntity.setName(name);
        }
        else{
            System.out.println("===> 2");
            recipeBoxEntity = new RecipeBoxEntity();
            recipeBoxEntity.setName(name);
        }
        recipeBoxRepository.save(recipeBoxEntity);
        return recipeBoxEntity;
    }

    @Override
    public String deleteRecipeBoxById(Long id) {
        if(recipeBoxRepository.findById(id).isPresent()) {
            recipeBoxRepository.deleteById(id);
            return "삭제성공";
        }
        else {
            return "아이디가 존재하지 않음";
        }
    }

    @Override
    public String deleteRecipeBoxByName(String name) {
        if(!recipeBoxRepository.findByName(name).isEmpty()) {
            List<RecipeBoxEntity> recipeBoxEntityList = recipeBoxRepository.findByName(name);
            for(int i=0; i<recipeBoxEntityList.size(); i++) {
                RecipeBoxEntity recipeBox = recipeBoxEntityList.get(i);
                if(recipeBox.getIsDefault() == true){
                    return "기본 박스는 삭제 할 수 없습니다.";
                }
            }
            recipeBoxRepository.deleteByName(name);
            return "삭제성공";
        }
        else {
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

    @Override
    public List<RecipeBoxEntity> findByUserId(){
        return recipeBoxRepository.findByUserId(UserManager.getUser().getId());
    }

    @Override
    public List<Map<String, Object>> getReceipeBoxList(){
        List recipeBoxList = new ArrayList<>();
        List<RecipeBoxEntity> recipeBoxes = recipeBoxRepository.findByUserId(UserManager.getUser().getId());

        recipeBoxes.forEach(recipeBox -> {

            Map<String, Object> recipeBoxMap = new HashMap<>();
            recipeBoxMap.put("id", recipeBox.getId());
            recipeBoxMap.put("name", recipeBox.getName());
            recipeBoxMap.put("isDefault", recipeBox.getIsDefault());    // TODO: isDefault 기준 정렬

            List<RecipeEntity> recipes = recipeBox.getRecipeEntities();
            List recipeList = new ArrayList<>();
            recipes.forEach(recipe -> {
                Map<String, Object> recipeMap = new HashMap<>();
                recipeMap.put("id", recipe.getId());
                recipeMap.put("title", recipe.getContentsEntity().getTitle());
                List<FileEntity> files = fileRepository.findByContentsId(recipe.getContentsEntity().getId());
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
