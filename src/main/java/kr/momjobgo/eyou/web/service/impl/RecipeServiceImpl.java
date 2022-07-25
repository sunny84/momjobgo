package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.config.security.UserManager;
import kr.momjobgo.eyou.web.jpa.entity.*;
import kr.momjobgo.eyou.web.jpa.repository.*;
import kr.momjobgo.eyou.web.service.RecipeService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.*;

import static java.lang.Integer.parseInt;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final FileRepository fileRepository;
    private final IngredientRepository ingredientRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository, ContentsRepository contentsRepository,
                             FileRepository fileRepository, IngredientRepository ingredientRepository,
                             ScoreRepository scoreRepository, TimeTakenRepository timeTakenRepository) {
        this.recipeRepository = recipeRepository;
        this.fileRepository = fileRepository;
        this.ingredientRepository = ingredientRepository;
        this.scoreRepository = scoreRepository;
        this.timeTakenRepository = timeTakenRepository;
     }

    @Override
    public List<Map<String, Object>> findByFilter(Pageable pageable, Long sort, Long period, Long timeTakenId, List<Long> Ids) {
        return recipeRepository.findByFilter(pageable, sort, period, timeTakenId, Ids, Long.valueOf(Ids.size()), 0L);
    }

    @Override
    public List<RecipeEntity> getAll() { return recipeRepository.findAll(); }

    @Override
    public List<Map<String, Object>> findByWriter(){
        List recipes = new ArrayList<>();
        contentsRepository.findByWriter(UserManager.getUser().getId()).forEach(contents -> {
            recipeRepository.findByContentsId(contents.getId()).forEach(recipe -> {
                Long recipeId = recipe.getId();
                System.out.println(recipeId);
//                recipes.add(getDetailById(recipeId));
                RecipeEntity recipeEntity = recipeRepository.getById(recipeId);
                Map<String, Object> result = new HashMap<>();
                result.put("recipeId", recipeId);

                /** Recipe 기본정보 **/
                Long contentsId = recipeEntity.getContentsEntity().getId();
                result.put("contentsId", contentsId);
                result.put("writer", recipeEntity.getContentsEntity().getWriter());

                result.put("title", recipeEntity.getContentsEntity().getTitle());
                result.put("subTitle", recipeEntity.getContentsEntity().getSubTitle());
                result.put("period", recipeEntity.getPeriod());
                result.put("quantity", recipeEntity.getQuantity());
                result.put("timeTaken", recipeEntity.getTimeTakenId());
                result.put("open", recipeEntity.getOpen());

                /** cooking order & images **/
                List <CookingOrderEntity> cookingOrderEntities = recipeEntity.getCookingOrderEntities();
                List <FileEntity> fileEntity=fileRepository.findByContentsId(contentsId);
                List <Map<String, Object>> cookingDataWithFileInfo = new ArrayList<Map<String, Object>>();
                result.put("cookingOrderExist", cookingOrderEntities.isEmpty()?"N":"Y");
                if(!cookingOrderEntities.isEmpty()) {
                    Long mainImgId = 0L;
                    Long[] C_orderImgId = new Long[fileEntity.size()];
                    for(int i=0; i<fileEntity.size(); i++) {
                        String fileRealName = fileEntity.get(i).getFileRealName();
                        String compareFileName =fileRealName.substring(fileRealName.lastIndexOf(".") - 2, fileRealName.lastIndexOf("."));
                        if(fileRealName.charAt(0) =='M') {
                            mainImgId = fileEntity.get(i).getId();
                        } else if (fileRealName.charAt(0) == 'C') {
                            C_orderImgId[Integer.parseInt(compareFileName)-1]=fileEntity.get(i).getId();
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
                recipes.add(result);
            });
        });
        return recipes;
    }

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

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getDetailById(Long id) {
        Optional <RecipeEntity> recipeEntity = recipeRepository.findById(id);
        Map<String, Object> result = new HashMap<>();

        if(recipeEntity.isPresent()) {
            RecipeEntity recipe = recipeEntity.get();

            /** Recipe 기본정보 **/
            Long contentsId = recipeEntity.get().getContentsId();

            result.put("contentsId", contentsId);
            result.put("writer", recipe.getContentsEntity().getWriter());

            // user table id대신 snsid를 보여줘야할 경우 사용할 코드
            //result.put("writer", UserManager.getUser().getSnsId());

            result.put("title", recipe.getContentsEntity().getTitle());
            result.put("subTitle", recipe.getContentsEntity().getSubTitle());
            result.put("period", recipe.getPeriod());
            result.put("quantity", recipe.getQuantity());
            result.put("timeTaken", recipe.getTimeTakenId());

            /** ingredients with key and volume **/
            List<RecipeIngredientMapEntity> recipeIngredientMapEntities = recipe.getRecipeIngredientMapEntities();
            List<Map<String, Object>> ingredients = new ArrayList<Map<String, Object>>();
            if (!recipeIngredientMapEntities.isEmpty()) {
                for (int i = 0; i < recipeIngredientMapEntities.size(); i++) {
                    Map<String, Object> ingredientData = new HashMap<>();
                    Long ingredientId = recipeIngredientMapEntities.get(i).getIngredientId();
                    Optional<IngredientEntity> ingredientEntity = ingredientRepository.findById(ingredientId);
                    ingredientData.put("key", ingredientEntity.get().getKey());
                    ingredientData.put("volume", recipeIngredientMapEntities.get(i).getVolume());

                    ingredients.add(ingredientData);
                }

                result.put("ingredients", ingredients);
            }

            /** cooking order & images **/
            List<CookingOrderEntity> cookingOrderEntities = recipe.getCookingOrderEntities();
            List<FileEntity> fileEntity = fileRepository.findByContentsId(contentsId);

            if (!cookingOrderEntities.isEmpty()) {
                List<Map<String, Object>> cookingDataWithFileInfo = new ArrayList<Map<String, Object>>();
                Long mainImgId = 0L;
                Long[] C_orderImgId = new Long[fileEntity.size()];
                for (int i = 0; i < fileEntity.size(); i++) {
                    String fileRealName = fileEntity.get(i).getFileRealName();
                    String compareFileName = fileRealName.substring(fileRealName.lastIndexOf(".") - 2, fileRealName.lastIndexOf("."));
                    if (fileRealName.charAt(0) == 'M') {
                        mainImgId = fileEntity.get(i).getId();
                    } else if (fileRealName.charAt(0) == 'C') {
//                    System.out.println(fileEntity.get(i).getFileRealName());
                        C_orderImgId[Integer.parseInt(compareFileName) - 1] = fileEntity.get(i).getId();
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

                result.put("mainImgId", mainImgId);
                result.put("cookingOrder", cookingDataWithFileInfo);
            }

            /**  Tip **/
            List<TipEntity> tipEntities = recipe.getTipEntities();

            System.out.println(tipEntities);
            List<Map<String, Object>> Tips = new ArrayList<Map<String, Object>>();
            if (tipEntities != null && !tipEntities.isEmpty()) {
                Map<String, Object> tip = new HashMap<>();
                for (int i = 0; i < tipEntities.size(); i++) {
                    tip.put("orderNum", tipEntities.get(i).getOrderNum());
                    tip.put("text", tipEntities.get(i).getText());

                    System.out.println(tip);
                    Tips.add(tip);
                }
                result.put("tips", Tips);
            }

            if (recipe.getClipLink() != null && !recipe.getClipLink().isEmpty()) {
                result.put("clipLink", recipe.getClipLink());
            }
            if (recipe.getClipLink() != null && !recipe.getYoutubeLink().isEmpty()) {
                result.put("youtubeLink", recipe.getYoutubeLink());
            }

            result.put("open", recipe.getOpen());
        }

        return result;
    }
}
