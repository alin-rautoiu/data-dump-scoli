package datadump.web;

import com.mongodb.client.MongoCollection;
import datadump.services.JudetServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import datadump.services.JudetService;

import java.util.*;

@Controller
public class JudetController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private JudetService judetService;

    @RequestMapping(value = "/Judete", method = RequestMethod.GET)
    public String Index(Model model){

        MongoCollection collection = null;
        collection = mongoTemplate.getCollection("Judet");

        List<Map<String, Object>> values = new LinkedList<>();

        values = judetService.getAll(collection);

        model.addAttribute("headerList", values.get(0).keySet().toArray());
        model.addAttribute("valuesList", values);

        return "displayJudete";
    }
}