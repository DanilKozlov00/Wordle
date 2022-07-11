package wordle.controller.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


@RestController
@RequestMapping(value = "api/v1/login")
@Tag(name = "Вход", description = "REST контроллер формы входа")
public class LoginController extends TemplateController {

    public static final String USER_DOESNT_EXISTS = "User doesn't exists";
    private static final String INVALID_EMAIL_OR_PASSWORD = "Invalid email/password combination";
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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Вход успешен", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Ошибка при проверке слова", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("signIn")
    public ResponseEntity<?> getUserByEmail(@Parameter(required = true, description = "email пользователя") @RequestParam String email, @Parameter(required = true, description = "пароль пользователя") @RequestParam String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            User user = userRepository.getByEmail(email);
            if (user == null) {
                createResponseEntityWithHttpStatus(USER_DOESNT_EXISTS, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String token = jwtTokenProvider.createToken(email, user.getRole().name());
            return createStringOkResponseEntity(token);
        } catch (AuthenticationException e) {
            return createResponseEntityWithHttpStatus(INVALID_EMAIL_OR_PASSWORD, HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "Кнопка выйти", description = "Кнопка выйти", tags = {"Вход"})
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

}
