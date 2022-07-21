package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.config.security.UserManager;
import kr.momjobgo.eyou.web.dto.RecipeRequest;
import kr.momjobgo.eyou.web.jpa.entity.*;
import kr.momjobgo.eyou.web.jpa.repository.*;
import kr.momjobgo.eyou.web.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Integer.parseInt;

@Service
public class RecipeServiceImpl implements RecipeService {

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

    public RecipeServiceImpl(RecipeRepository recipeRepository, ContentsRepository contentsRepository,
                             FileRepository fileRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.contentsRepository = contentsRepository;
        this.fileRepository = fileRepository;
        this.ingredientRepository = ingredientRepository;
     }

    @Override
    public List<Map<String, Object>> findByFilter(Pageable pageable, Long userId, Long sort, Long period, Long timeTakenId, List<Long> Ids) {
        return recipeRepository.findByFilter(pageable, userId, sort, period, timeTakenId, Ids, Long.valueOf(Ids.size()));
    }

    @Override
    public List<RecipeEntity> getAll() { return recipeRepository.findAll(); }

    @Override
    public RecipeEntity getById(Long id){

        Optional<RecipeEntity> recipeEntity = recipeRepository.findById(id);

        if(recipeEntity.isPresent()){
            return recipeEntity.get();
        } else {
            return null;
        }
    }

    @Override
    public List<RecipeEntity> getByContentsId(Long contentsId){
        return recipeRepository.findByContentsId(contentsId);
    }

    @Transactional
    @CrossOrigin
    public Map<String, Object> write(HttpServletRequest req, @RequestBody RecipeRequest request) {

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

        request.getTipEntities().stream().forEach(item -> {
            item.setRecipeId(resultRecipe.getId());
            tipRepository.save(item);
        });

        request.getCookingOrderEntities().stream().forEach(item -> {
            item.setRecipeId(resultRecipe.getId());
            cookingOrderRepository.save(item);
        });

        Map<String, Object> result = new HashMap<>();
        result.put("contentsId", entity.getId());
        result.put("recipeId", resultRecipe.getId());

        return result;
    }

    @Override
    public String updateOpen(Long id) {
        Optional<RecipeEntity> recipeEntity = recipeRepository.findById(id);

        if (recipeEntity.isPresent()) {
            if (recipeEntity.get().getOpen()) {
                recipeEntity.get().setOpen(false);
            } else {
                recipeEntity.get().setOpen(true);
            }
            recipeRepository.save(recipeEntity.get());
            return "Success to update open";
        }
        return "Fail to update open";
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getDetailById(Long id) {
        RecipeEntity recipeEntity = recipeRepository.getById(id);
        Map<String, Object> result = new HashMap<>();

        /** Recipe 기본정보 **/
        Long contentsId = recipeEntity.getContentsEntity().getId();
        result.put("contentsId", contentsId);
        result.put("writer", recipeEntity.getContentsEntity().getWriter());

        // user table id대신 snsid를 보여줘야할 경우 사용할 코드
        //result.put("writer", UserManager.getUser().getSnsId());

        result.put("title", recipeEntity.getContentsEntity().getTitle());
        result.put("subTitle", recipeEntity.getContentsEntity().getSubTitle());
        result.put("period", recipeEntity.getPeriod());
        result.put("quantity", recipeEntity.getQuantity());
        result.put("timeTaken", recipeEntity.getTimeTakenId());
        result.put("open", recipeEntity.getOpen());
        if(!recipeEntity.getClipLink().isEmpty()) {
            result.put("clipLink", recipeEntity.getClipLink());
        }
        if(!recipeEntity.getYoutubeLink().isEmpty()) {
            result.put("youtubeLink", recipeEntity.getYoutubeLink());
        }

        /**  Tip **/
        List<TipEntity> tipEntities = recipeEntity.getTipEntities();

        List<Map<String, Object>> Tips = new ArrayList<Map<String, Object>>();
        if(!tipEntities.isEmpty()) {
            Map<String, Object> tip = new HashMap<>();
            for (int i = 0; i < tipEntities.size(); i++) {
                tip.put("orderNum", tipEntities.get(i).getOrderNum());
                tip.put("text", tipEntities.get(i).getText());

                System.out.println(tip);
                Tips.add(tip);
            }
        }
        result.put("tips", Tips);

        /** cooking order & images **/
        List <CookingOrderEntity> cookingOrderEntities = recipeEntity.getCookingOrderEntities();
        List <FileEntity> fileEntity=fileRepository.findByContentsId(contentsId);

        if(!cookingOrderEntities.isEmpty()) {
            List <Map<String, Object>> cookingDataWithFileInfo = new ArrayList<Map<String, Object>>();
            Long mainImgId = 0L;
            Long[] C_orderImgId = new Long[fileEntity.size()];
            for(int i=0; i<fileEntity.size(); i++) {
                String fileRealName = fileEntity.get(i).getFileRealName();
                String compareFileName =fileRealName.substring(fileRealName.lastIndexOf(".") - 2, fileRealName.lastIndexOf("."));
                if(fileRealName.charAt(0) =='M') {
                    mainImgId = fileEntity.get(i).getId();
                } else if (fileRealName.charAt(0) == 'C') {// && Integer.parseInt(compareFileName) == i) {
//                    System.out.println(fileEntity.get(i).getFileRealName());
                    C_orderImgId[Integer.parseInt(compareFileName)-1]=fileEntity.get(i).getId();
                    //System.out.println(fileRealName + " : " + Integer.parseInt(compareFileName));
                }
            }

            for (int i = 0; i < cookingOrderEntities.size(); i++) {
                Map<String, Object> C_orderAndImg = new HashMap<>();
                C_orderAndImg.put("contentsNo", cookingOrderEntities.get(i).getContentsNo());
                C_orderAndImg.put("contents", cookingOrderEntities.get(i).getContents());
                C_orderAndImg.put("imgId", C_orderImgId[i]);

                cookingDataWithFileInfo.add(C_orderAndImg);
            }

            //System.out.println("CookingOrder Data : " +cookingDataWithFileInfo);
            result.put("mainImgId", mainImgId);
            result.put("cookingOrder", cookingDataWithFileInfo);
        }

        /** ingredients with key and volume **/
        List <RecipeIngredientMapEntity> recipeIngredientMapEntities = recipeEntity.getRecipeIngredientMapEntities();
        List <Map<String, Object>> ingredients = new ArrayList<Map<String, Object>>();
        if(!recipeIngredientMapEntities.isEmpty()) {
            for(int i=0;i<recipeIngredientMapEntities.size();i++) {
                Map<String, Object> ingredientData = new HashMap<>();
                Long ingredientId = recipeIngredientMapEntities.get(i).getIngredientId();
//                System.out.println("ing_id : "+ingredientId);
                Optional <IngredientEntity> ingredientEntity = ingredientRepository.findById(ingredientId);
//                System.out.println(ingredientEntity.get().getKey());
                ingredientData.put("key", ingredientEntity.get().getKey());
                ingredientData.put("volume", recipeIngredientMapEntities.get(i).getVolume());

//                System.out.println(ingredientData);
                ingredients.add(ingredientData);
            }

//            System.out.println("Ing Data : "+ingredients);
            result.put("ingredients", ingredients);
        }

        return result;
    }
}
