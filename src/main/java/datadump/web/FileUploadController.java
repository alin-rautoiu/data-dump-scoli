package datadump.web;

import datadump.common.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import datadump.services.StorageService;

import java.io.IOException;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {
    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/uploads")
    public String listUploadedFiles(Model model) throws IOException {

        try {
            model.addAttribute("files", storageService
                    .loadAll()
                    .map(path ->
                        MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                            "serveFile",
                                path.getFileName().toString()).build().toString())
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "uploadForm";
    }


    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename){
        Resource file = storageService.loadAsResource(filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/uploads")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        if(!file.isEmpty()){
            //storageService.deleteOldFiles(storageService.getCurrentDir());
            storageService.store(file);
            redirectAttributes.addFlashAttribute("message", "Ai incarcat cu succes " + file.getOriginalFilename() + " !");
            return "redirect:/judete/show";
        }
        else {
           redirectAttributes.addFlashAttribute("message", "Nu ati atasat niciun fisier!");
           return "redirect:/uploads";
        }
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc){
        return ResponseEntity.notFound().build();
    }

}
