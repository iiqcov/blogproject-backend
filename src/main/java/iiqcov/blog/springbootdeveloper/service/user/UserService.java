package iiqcov.blog.springbootdeveloper.service.user;

import iiqcov.blog.springbootdeveloper.domain.User;
import iiqcov.blog.springbootdeveloper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("Unexpected user"));
    }

    public User save(User user){
        return userRepository.save(user);
    }
}
