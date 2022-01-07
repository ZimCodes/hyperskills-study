package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.model.Recipe;

import java.util.List;
import java.util.Optional;


@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
    Optional<Recipe> findRecipeById(int id);
    boolean existsRecipeById(int id);
    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);
    List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);
}