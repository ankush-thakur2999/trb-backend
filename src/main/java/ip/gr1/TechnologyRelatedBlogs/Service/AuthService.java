package ip.gr1.TechnologyRelatedBlogs.Service;

import ip.gr1.TechnologyRelatedBlogs.Model.User;
import ip.gr1.TechnologyRelatedBlogs.Repository.UserRepository;
import ip.gr1.TechnologyRelatedBlogs.Security.JwtProvider;
import ip.gr1.TechnologyRelatedBlogs.dto.LoginRequest;
import ip.gr1.TechnologyRelatedBlogs.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
   private  AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        userRepository.save(user);
    }

    private String encodePassword(String password) {
    return passwordEncoder.encode(password);
    }

    public  AuthenticationResponse login(LoginRequest loginRequest) {
       Authentication authenciate = authenticationManager.authenticate((new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword())));
        SecurityContextHolder.getContext().setAuthentication(authenciate);
        String authenticationToken = jwtProvider.generateToken(authenciate);
       return  new AuthenticationResponse(authenticationToken,loginRequest.getUsername());


    }

    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }
}
