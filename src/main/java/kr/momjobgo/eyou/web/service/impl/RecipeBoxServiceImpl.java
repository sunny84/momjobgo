package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.config.security.UserManager;
import kr.momjobgo.eyou.web.jpa.entity.RecipeBoxEntity;
import kr.momjobgo.eyou.web.jpa.repository.RecipeBoxRepository;
import kr.momjobgo.eyou.web.service.RecipeBoxService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeBoxServiceImpl implements RecipeBoxService {
    private final RecipeBoxRepository recipeBoxRepository;

    public RecipeBoxServiceImpl(RecipeBoxRepository recipeBoxRepository) { this.recipeBoxRepository = recipeBoxRepository; }

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
    public List<RecipeBoxEntity> findByUserId(Long userId){
        return recipeBoxRepository.findByUserId(userId);
    }
}
