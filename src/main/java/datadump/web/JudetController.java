package datadump.web;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import datadump.services.StorageService;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import datadump.services.JudetService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class JudetController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private JudetService judetService;

    @Autowired
    private StorageService storageService;

    @RequestMapping(value = "/judete", method = RequestMethod.GET)
    public String Index(Model model){
        List<Map<String, Object>> values = new LinkedList<>();

        values = judetService.getAll();

        model.addAttribute("headerList", values.get(0).keySet().toArray());
        model.addAttribute("valuesList", values);

        return "displayJudete";
    }

    @PostMapping("/judete")
    public String Index(@RequestBody String  data, RedirectAttributes redirectAttributes){
        Gson gson = new Gson();
        Map<String, Object> item = gson.fromJson(data, Map.class);
        System.out.println(item);

        Document judet = new Document(item);
        Document query = new Document("judet", judet.getString("judet"));
        MongoCollection collection = mongoTemplate.getCollection("Judet");

        Document oldValue =  (Document) collection.find(query).first();
        collection.replaceOne(oldValue ,judet);

        redirectAttributes.addFlashAttribute("message", "Modificare efectuata cu succes!");

        return "redirect:/judete";
    }

    @RequestMapping(value = "/judete/show", method = RequestMethod.GET)
    public String Show(Model model) throws IOException {
        List<Map<String, Object>> values = new LinkedList<>();

        File lastFilePath = storageService.lastFileModified(storageService.getCurrentDir());
        if ( lastFilePath != null){
            values = judetService.readCSV(lastFilePath.getAbsolutePath().toString());
        }

        model.addAttribute("headerList", values.get(0).keySet().toArray());
        model.addAttribute("valuesList", values);

        return "displayNewData";
    }

    @PostMapping("/judete/show")
    public String saveNewData(RedirectAttributes redirectAttributes) throws IOException {
        List<Map<String, Object>> newValues = new LinkedList<>();
        File lastFilePath = storageService.lastFileModified(storageService.getCurrentDir());
        if ( lastFilePath != null){
            newValues = judetService.readCSV(lastFilePath.getAbsolutePath().toString());
        }
       judetService.saveChanges(newValues);

        List<Map<String, Object>> values = new LinkedList<>();

        values = judetService.getAll();

        redirectAttributes.addFlashAttribute("headerList", values.get(0).keySet().toArray());
        redirectAttributes.addFlashAttribute("valuesList", values);
        redirectAttributes.addFlashAttribute("message", "Datele au fost salvate cu succes!");

       return "redirect:/judete";
    }
}