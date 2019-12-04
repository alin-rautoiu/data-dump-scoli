package datadump.web;

import com.mongodb.client.MongoCollection;
import datadump.services.FileSystemStorageService;
import datadump.services.JudetServiceImplementation;
import datadump.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import datadump.services.JudetService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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