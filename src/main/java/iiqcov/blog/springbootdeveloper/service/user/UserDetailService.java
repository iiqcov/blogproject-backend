package iiqcov.blog.springbootdeveloper.service.user;

import iiqcov.blog.springbootdeveloper.domain.User;
import iiqcov.blog.springbootdeveloper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String ipAddress) throws UsernameNotFoundException {
        User user = userRepository.findByipAddress(ipAddress)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : "+ipAddress));
        String password="password";
        List<GrantedAuthority> authorities= Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return new org.springframework.security.core.userdetails.User(user.getIpAddress(), password, authorities);
    }
}
