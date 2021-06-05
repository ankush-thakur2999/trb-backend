package ip.gr1.TechnologyRelatedBlogs.Controller;

import ip.gr1.TechnologyRelatedBlogs.Service.AuthService;
import ip.gr1.TechnologyRelatedBlogs.Service.AuthenticationResponse;
import ip.gr1.TechnologyRelatedBlogs.dto.LoginRequest;
import ip.gr1.TechnologyRelatedBlogs.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping(value = "/signup",consumes= {"text/plain","application/json"})
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest){
    authService.signup(registerRequest);
    return  new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping(value = "/login",consumes = { "text/plain","application/json",})
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest){
       return  authService.login(loginRequest);
    }
}
