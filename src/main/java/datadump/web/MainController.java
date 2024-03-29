package datadump.web;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import datadump.accessingdatamongodb.JudetRepository;
import datadump.accessingdatamongodb.TestCollectionRepository;
import datadump.services.JudetService;
import org.apache.juli.logging.Log;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class MainController {
    private static final Logger logger = Logger.getLogger(MainController.class.getName());

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private JudetService judetService;

    @GetMapping("/")
    public String index(Model model){

        try{
            List<Map<String, Object>> judete = judetService.getAll();
            Gson gson = new Gson();
            String judeteAsJSON = gson.toJson(judete);

            //model.addAttribute("judete", judeteAsJSON);
        }
        catch (Exception e){
            logger.log(Level.ALL, e.getMessage());
            e.printStackTrace();
        }

        //Specifica lui thymeleaf ce template sa foloseasca
        return "main";
    }
    @GetMapping("/getJudete")
    @ResponseBody
    public String getJudete() {
        try{
            List<Map<String, Object>> judete = judetService.getAll();
            Gson gson = new Gson();
            String judeteAsJSON = gson.toJson(judete);

            return judeteAsJSON;
        }

        catch (Exception e){
            logger.log(Level.ALL, e.getMessage());
            e.printStackTrace();
        }

        return "";
    }
}
