package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.model.User;
import recipes.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.repo = userRepo;
        this.encoder = passwordEncoder;
    }

    public boolean existsUserByEmail(String email) {
        return this.repo.existsUserByEmail(email);
    }

    public User addUser(User user) {
        user.setPassword(this.encoder.encode(user.getPassword()));
        return this.repo.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return this.repo.findUserByEmail(email);
    }
}