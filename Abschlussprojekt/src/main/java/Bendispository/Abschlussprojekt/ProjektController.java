package Bendispository.Abschlussprojekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ProjektController {
    //@Autowired

    @GetMapping(path = "/addItem/")
    public String addItemPage(Model model){
        return "AddItem";
    }

    @GetMapping(path = "/Item/{id}")
    public String ItemProfile(Model model){
        return "AddItem";
    }


}
