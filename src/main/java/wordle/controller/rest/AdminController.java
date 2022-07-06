package wordle.controller.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/admin")
@PreAuthorize(("hasAuthority('admin')"))
@SecurityRequirement(name = "Authorization")
@Tag(name = "Администратор", description = "REST контроллер личного кабинета администратора")
public class AdminController implements TemplateController {

}
