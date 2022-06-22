package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.config.security.UserManager;
import kr.momjobgo.eyou.web.jpa.entity.RecipeBoxEntity;
import kr.momjobgo.eyou.web.jpa.entity.TestEntity;
import kr.momjobgo.eyou.web.jpa.repository.RecipeBoxRepository;
import kr.momjobgo.eyou.web.service.RecipeBoxService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

@Service
public class RecipeBoxServiceImpl implements RecipeBoxService {
    private final RecipeBoxRepository recipeBoxRepository;

    public RecipeBoxServiceImpl(RecipeBoxRepository recipeBoxRepository) { this.recipeBoxRepository = recipeBoxRepository; }

    @Override
    public List<RecipeBoxEntity> joinRecipe() { return recipeBoxRepository.findAll(); }

    @Override
    public List<RecipeBoxEntity> getAll()  { return recipeBoxRepository.findAll(); }

    @Override
    public RecipeBoxEntity getById(Long id) { return recipeBoxRepository.getById(id); }

    @Override
    public RecipeBoxEntity insertRecipeBoxName(String name) {
        RecipeBoxEntity recipeBoxEntity = new RecipeBoxEntity();
        if(recipeBoxRepository.count() > 0) {
            List<RecipeBoxEntity> recipeBoxEntitys = recipeBoxRepository.findAll();

            int i = 0;
            ListIterator<RecipeBoxEntity> it = recipeBoxEntitys.listIterator();
            while (it.hasNext()) {
                System.out.println(it.next());
//                if(it.get().getIsDefault() == null || it.get().getIsDefault().equals(false)){
//                } else {
//                    i++;
//                }
            }

            if(i == 0){
                recipeBoxEntity.setIsDefault(true);
            } else {
                recipeBoxEntity.setIsDefault(false);
            }
        }
        recipeBoxEntity.setName(name);
        return recipeBoxRepository.save(recipeBoxEntity);
    }

    @Override
    public RecipeBoxEntity insertRecipeBox(RecipeBoxEntity entity) {
        return recipeBoxRepository.save(entity);
    }

    @Override
    public RecipeBoxEntity updateRecipeBoxById(Long id, RecipeBoxEntity entity) {
        RecipeBoxEntity result = null;

        Optional<RecipeBoxEntity> recipeBoxEntity = recipeBoxRepository.findById(id);

        if(recipeBoxEntity.isPresent()){
            if(recipeBoxEntity.get().getIsDefault() == null || recipeBoxEntity.get().getIsDefault().equals(false)){
                result = recipeBoxRepository.save(entity);
            } else {
                System.out.println("기본 레시피 이름을 수정할 수 없음");
            }
        }
        return result;
    }

    @Override
    public RecipeBoxEntity updateRecipeBox(RecipeBoxEntity entity) {
        RecipeBoxEntity result = null;

        Optional<RecipeBoxEntity> recipeBoxEntity = recipeBoxRepository.findById(entity.getId());

        if(recipeBoxEntity.isPresent()){
            if(recipeBoxEntity.get().getIsDefault() == null || recipeBoxEntity.get().getIsDefault().equals(false)){
                result = recipeBoxRepository.save(entity);
            } else {
                System.out.println("기본 레시피 이름을 수정할 수 없음");
            }
        }
        return result;
    }

    @Override
    public String deleteRecipeBoxById(Long id) {
        if(recipeBoxRepository.findById(id).isPresent()) {
            recipeBoxRepository.deleteById(id);
            return "삭제성공";
        }
        else {
            return "존재하지 않음";
        }
    }

    @Override
    public String deleteRecipeBoxAll() {
        if(recipeBoxRepository.count() > 0 ) {
            recipeBoxRepository.deleteAll();
            return "모두 삭제 성공";
        }
        else {
            return "존재하지 않음";
        }
    }

    @Override
    public List<RecipeBoxEntity> findByName(String name) {
        return recipeBoxRepository.findByName(name);
    }

    @Override
    public List<RecipeBoxEntity> findByNameContains(String name) {
        return recipeBoxRepository.findByNameContains(name);
    }
}
