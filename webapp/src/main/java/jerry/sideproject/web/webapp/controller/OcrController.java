package jerry.sideproject.web.webapp.controller;
//      jerry.sideproject.web.webapp;

import jerry.sideproject.web.webapp.bean.Item;
import jerry.sideproject.web.webapp.services.ItemService;
import jerry.sideproject.web.webapp.services.ocr.OcrServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/items")
public class OcrController {

    private static final String UPLOADED_FOLDER = "src/main/resources/image/";

    @Autowired
    private OcrServices ocrServices;
    @Autowired
    private ItemService itemService;

    @GetMapping("/uploadForm")
    public String index() {
        return "uploadForm";
    }

    @PostMapping("/uploadImage")
    public String singleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty() || !isValidImageFile(file)) {
            model.addAttribute("message", "Please select .jpg or .png file to upload");
            return "uploadForm";
        }
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            model.addAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");
            itemService.saveAll(parseImage(path));
            model.addAttribute("items", itemService.findAll());
            return "allItems";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    private List<Item> parseImage(Path filePath) {
        return ocrServices.imageRecognize(filePath);
    }

    private boolean isValidImageFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName.endsWith("png") || fileName.endsWith("jpg")) {
            return true;
        } else {
            return false;
        }
    }
}
