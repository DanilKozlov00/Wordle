package wordle.controller.rest;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wordle.services.rest.UserStatisticService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/rating")
@Tag(name = "Рейтинг", description = "REST контроллер формы рейтинга")
public class RatingController implements TemplateController {

    private final UserStatisticService userStatisticService;

    @Autowired
    public RatingController(UserStatisticService userStatisticService) {
        this.userStatisticService = userStatisticService;
    }

    @GetMapping("users")
    public ResponseEntity<?> getUsersRating(
            @Parameter(description = "старт выборки") @RequestParam int start,
            @Parameter(description = "конец выборки") @RequestParam int end
    ) {
        return createOkResponseEntity(userStatisticService.getUsersStatistic(start, end));
    }

    @GetMapping("statisticsCount")
    public ResponseEntity<?> getStatisticsCount() {
        return createOkResponseEntity(userStatisticService.getUsersStatisticsCount());
    }
}
