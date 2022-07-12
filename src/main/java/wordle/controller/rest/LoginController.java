package wordle.controller.rest;


import io.jsonwebtoken.JwtException;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wordle.model.dto.RefreshToken;
import wordle.model.dto.User;
import wordle.model.payload.TokenResponse;
import wordle.security.JwtTokenProvider;
import wordle.services.rest.RefreshTokenService;
import wordle.services.rest.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = "api/v1/login")
@Tag(name = "Вход", description = "REST контроллер формы входа")
public class LoginController extends TemplateController {

    public static final String USER_DOESNT_EXISTS = "User doesn't exists";
    private static final String INVALID_EMAIL_OR_PASSWORD = "Invalid email/password combination";
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, UserService userService, JwtTokenProvider jwtTokenProvider, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @Operation(summary = "Кнопка войти", description = "Кнопка войти", tags = {"Вход"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Вход успешен", content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "400", description = "Пользователь не найден", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "403", description = "Неправильный логин/пароль", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping("signIn")
    public ResponseEntity<?> getUserByEmail(@Parameter(required = true, description = "email пользователя") @RequestParam String email, @Parameter(required = true, description = "пароль пользователя") @RequestParam String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            User user = userService.getByEmail(email);
            if (user == null) {
                createResponseEntityWithHttpStatus(USER_DOESNT_EXISTS, HttpStatus.BAD_REQUEST);
            }
            String token = jwtTokenProvider.createToken(email, user.getRole().name());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
            return createObjectOkResponseEntity(new TokenResponse(token, refreshToken.getToken()));
        } catch (AuthenticationException e) {
            return createResponseEntityWithHttpStatus(INVALID_EMAIL_OR_PASSWORD, HttpStatus.FORBIDDEN);
        }
    }

    @Operation(summary = "Обновить токены", description = "Обновить токены", tags = {"Вход"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Новая пара токенов", content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "400", description = "Refresh токен не найден в базе", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "403", description = "Refresh токен истёк", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping("refreshToken")
    public ResponseEntity<?> refreshToken(@RequestParam(value = "refreshToken") String requestRefreshToken) {
        RefreshToken refreshToken = refreshTokenService.findByToken(requestRefreshToken);
        if (refreshToken != null) {
            try {
                refreshTokenService.verifyExpiration(refreshToken);
                User user = refreshToken.getUser();
                refreshTokenService.deleteByUser(user);
                return createObjectOkResponseEntity(new TokenResponse(jwtTokenProvider.createToken(user.getEmail(), user.getRole().name()), refreshTokenService.createRefreshToken(user.getEmail()).getToken()));
            } catch (JwtException jwtException) {
                return createResponseEntityWithHttpStatus("Refresh token is expired!", HttpStatus.FORBIDDEN);
            }
        }
        return createResponseEntityWithHttpStatus("Refresh token is not in database!", HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Выйти", description = "Выйти", tags = {"Вход"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный выход", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "Несуществующий пользователь", content = @Content(schema = @Schema(implementation = Response.class))),
    })
    @PostMapping("logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response, @RequestHeader(value = "Authorization") String token) {
        String email = jwtTokenProvider.getUsername(token);
        User user = userService.getByEmail(email);
        if (user != null) {
            refreshTokenService.deleteByUser(user);
            SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
            securityContextLogoutHandler.logout(request, response, null);
            return createStringOkResponseEntity("Log out successful!");
        }
        return createResponseEntityWithHttpStatus("User not found in dictionary", HttpStatus.BAD_REQUEST);
    }

}
