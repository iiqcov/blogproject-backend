package iiqcov.blog.springbootdeveloper.service.user;

import iiqcov.blog.springbootdeveloper.domain.User;
import iiqcov.blog.springbootdeveloper.dto.user.AddUserReqeust;
import iiqcov.blog.springbootdeveloper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User save(AddUserReqeust dto){
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .build());
    }

    public User findById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("Unexpected user"));
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
