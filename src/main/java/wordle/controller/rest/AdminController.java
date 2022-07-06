package wordle.controller.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/admin")
@PreAuthorize(("hasAuthority('admin')"))
@SecurityRequirement(name="Authorization")
public class AdminController implements TemplateController {

}
