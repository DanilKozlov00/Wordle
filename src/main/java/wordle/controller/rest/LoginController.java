package wordle.controller.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wordle.model.dto.User;
import wordle.security.JwtTokenProvider;
import wordle.services.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/login")
@Tag(name = "Вход", description = "REST контроллер формы входа")
public class LoginController implements TemplateController {

    private final AuthenticationManager authenticationManager;
    private final UserDao userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, UserDao userRepository, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Operation(summary = "Кнопка войти", description = "Кнопка войти", tags = {"Вход"})
    @PostMapping("signIn")
    public ResponseEntity<?> getUserByEmail(@Parameter(required = true, description = "email пользователя") @RequestParam String email, @Parameter(required = true, description = "пароль пользователя") @RequestParam String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            User user = userRepository.getByEmail(email);
            if (user == null) {
                createErrorResponseEntity("User doesn't exists", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String token = jwtTokenProvider.createToken(email, user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("user", user);
            response.put("token", token);
            return createOkResponseEntity(response);
        } catch (AuthenticationException e) {
            return createErrorResponseEntity("Invalid email/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

}
