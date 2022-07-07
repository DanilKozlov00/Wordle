package wordle.controller.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import wordle.services.rest.UserRatingService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/admin")
@PreAuthorize(("hasAuthority('admin')"))
@SecurityRequirement(name = "Authorization")
@Tag(name = "Администратор", description = "REST контроллер личного кабинета администратора")
public class AdminController implements TemplateController {

    private final UserRatingService userRatingService;

    @Autowired
    public AdminController(UserRatingService userRatingService) {
        this.userRatingService = userRatingService;
    }

    @PostMapping("addCoinsAsEndMouth")
    public ResponseEntity<?> addCoinsAsEndMouth() {
        if (userRatingService.addCoinsAsEndMouth()) {
            return createOkResponseEntity("Added");
        }
        return createErrorResponseEntity("Not added", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
