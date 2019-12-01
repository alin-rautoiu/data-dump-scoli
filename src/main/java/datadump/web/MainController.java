package datadump.web;

import com.mongodb.client.MongoCollection;
import datadump.accessingdatamongodb.JudetRepository;
import datadump.accessingdatamongodb.TestCollectionRepository;
import org.apache.juli.logging.Log;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class MainController {
    private static final Logger logger = Logger.getLogger(MainController.class.getName());

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/")
    public String Index(Model model){

        try{
            MongoCollection judet = mongoTemplate.getCollection("Judet");
            Long count2 = judet.countDocuments();

            //Adauga in modelul MVC atributul "count" cu valoare count
            model.addAttribute("count2", count2.toString());
        }
        catch (Exception e){
            logger.log(Level.ALL, e.getMessage());
            e.printStackTrace();
        }

        //Specifica lui thymeleaf ce template sa foloseasca
        return "main";
    }
}
