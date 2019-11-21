package datadump.web;

import datadump.accessingdatamongodb.JudetRepository;
import datadump.accessingdatamongodb.TestCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private TestCollectionRepository repository;

    @Autowired
    private JudetRepository repositoryJudet;

    @GetMapping("/")
    public String Index(Model model){

        //Numara cate documente sunt in colectia TestCollection
        Long count = repository.count();
        Long count2 = repositoryJudet.count();

        //Adauga in modelul MVC atributul "count" cu valoare count
        model.addAttribute("count", count.toString());
        model.addAttribute("count2", count2.toString());

        //Specifica lui thymeleaf ce template sa foloseasca
        return "main";
    }
}
