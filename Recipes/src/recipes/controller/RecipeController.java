package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import recipes.model.Recipe;
import recipes.security.UserDetailsImpl;
import recipes.service.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RestController
public class RecipeController {
    RecipeService service;

    @Autowired
    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping("/api/recipe/{id}")
    public Map<String, Object> getRecipe(@PathVariable int id) {
        Optional<Recipe> recipe = this.service.findRecipeById(id);
        if (recipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return this.service.convertNoId(recipe.get());
    }

    @GetMapping("/api/recipe/search")
    public List<Map<String, Object>> getRecipeByCategory(@RequestParam(required = false) String category,
                                                         @RequestParam(required = false) String name) {
        List<Recipe> recipes = null;
        List<Map<String, Object>> sortedRecipes = null;
        if ((name == null && category == null) || (name != null && category != null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if (category != null) {
            recipes = this.service.findAllByCategory(category);
            sortedRecipes = this.service.convertGroupNoId(recipes);
        } else {
            recipes = this.service.findAllByName(name);
            sortedRecipes = this.service.convertGroupNoId(recipes);
        }
        return sortedRecipes;
    }

    @PostMapping("/api/recipe/new")
    public Map<String, Integer> newRecipe(@AuthenticationPrincipal UserDetailsImpl auth,
                                          @Valid @RequestBody Recipe recipe) {
        verifyUser(auth);
        Recipe newRecipe = this.service.addRecipe(auth,recipe);
        return Map.of("id", newRecipe.getId());
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<HttpStatus> deleteRecipe(@AuthenticationPrincipal UserDetailsImpl auth,
                                                   @PathVariable int id) {
        verifyUser(auth);
        boolean recipeExists = this.service.existsRecipeById(id);
        if (!recipeExists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        this.service.deleteRecipeByUserAndId(auth, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<HttpStatus> updateRecipe(@AuthenticationPrincipal UserDetailsImpl auth,
                                                   @PathVariable int id,
                                                   @Valid @RequestBody Recipe recipe) {
        verifyUser(auth);
        recipe.setId(id);
        this.service.updateRecipe(auth, recipe);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void verifyUser(UserDetails user) throws ResponseStatusException {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}