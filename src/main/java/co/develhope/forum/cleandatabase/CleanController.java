package co.develhope.forum.cleandatabase;


import co.develhope.forum.configuration.security.PublicEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


// ONLY FOR TEST, CLEAN DATABASE
@RestController
@RequestMapping("/clean")
public class CleanController {

    @Autowired
    CleanService cleanService;

    @GetMapping
    @PublicEndpoint
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void cleanDB(){
        cleanService.CleanDB();
    }
}
