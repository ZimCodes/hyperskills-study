package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.model.Recipe;
import recipes.model.User;
import recipes.repository.RecipeRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository repo;
    private final UserService userService;

    @Autowired
    public RecipeService(RecipeRepository repository, UserService userService) {
        this.repo = repository;
        this.userService = userService;
    }

    public Optional<Recipe> findRecipeById(int id) {
        return this.repo.findRecipeById(id);
    }

    public List<Recipe> findAllByCategory(String category) {
        return this.repo.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> findAllByName(String name) {
        return this.repo.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    public Recipe addRecipe(UserDetails user, Recipe newRecipe) {
        newRecipe.setDate(LocalDateTime.now());
        User foundUser = this.userService.findUserByEmail(user.getUsername()).get();
        newRecipe.setUser(foundUser);
        return this.repo.save(newRecipe);
    }

    public void deleteRecipeByUserAndId(UserDetails currentUser, int id) throws ResponseStatusException {
        Recipe recipe = this.repo.findRecipeById(id).get();
        User author = recipe.getUser();
        if (!currentUser.getUsername().equals(author.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        this.repo.deleteById(id);
    }

    public boolean existsRecipeById(int id) {
        return this.repo.existsRecipeById(id);
    }

    public void updateRecipe(UserDetails user, Recipe recipe) throws ResponseStatusException {
        Optional<Recipe> dbRecipe = this.repo.findRecipeById(recipe.getId());
        if (dbRecipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            Recipe foundRecipe = dbRecipe.get();
            if (!foundRecipe.getUser().getEmail().equals(user.getUsername())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
            foundRecipe.setName(recipe.getName());
            foundRecipe.setCategory(recipe.getCategory());
            foundRecipe.setDescription(recipe.getDescription());
            foundRecipe.setDirections(recipe.getDirections());
            foundRecipe.setIngredients(recipe.getIngredients());
            foundRecipe.setDate(LocalDateTime.now());
            this.repo.save(foundRecipe);
        }
    }

    public Map<String, Object> convertNoId(Recipe recipe) {
        return Map.of("name", recipe.getName(),
                "category", recipe.getCategory(),
                "date", recipe.getDate(),
                "description", recipe.getDescription(),
                "ingredients", recipe.getIngredients(),
                "directions", recipe.getDirections());
    }

    public List<Map<String, Object>> convertGroupNoId(List<Recipe> recipes) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Recipe recipe : recipes) {
            list.add(this.convertNoId(recipe));
        }
        return list;
    }
}