package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.model.User;
import recipes.repository.UserRepository;
import recipes.security.UserDetailsImpl;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    UserRepository repo;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.repo = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = this.repo.findUserByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Not found: " + email);
        }
        return new UserDetailsImpl(user.get());
    }
}