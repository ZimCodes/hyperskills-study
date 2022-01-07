package recipes.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private int id;
    @Pattern(regexp = "\\w+@\\w+\\.\\w+")
    @NotNull
    private String email;
    @Size(min = 8)
    @NotBlank
    private String password;
    private String role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Recipe> recipes = new ArrayList<>();
}