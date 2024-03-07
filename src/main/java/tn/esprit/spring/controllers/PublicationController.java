package tn.esprit.spring.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.ServletContext;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.spring.entities.Publication;
import tn.esprit.spring.entities.QA;
import tn.esprit.spring.services.IPublicationService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;



@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("api/publications")
@CrossOrigin(origins = "http://localhost:4200")
public class PublicationController {
    IPublicationService publicationService;
    @Autowired
    ServletContext context;

    @PostMapping(value = "/add", consumes = { "multipart/form-data", "application/xml", "application/json" })
    public ResponseEntity<Response> createPublication(@RequestParam("publication") String publication,
                                                      @RequestParam("file") MultipartFile file) throws JsonParseException, JsonMappingException, Exception {
        System.out.println("Ok .............");
        Publication arti = new ObjectMapper().readValue(publication, Publication.class);
        boolean isExit = new File(context.getRealPath("/Images/")).exists();
        if (!isExit) {
            new File(context.getRealPath("/Images/")).mkdir();
            System.out.println("mk dir.............");
        }
        String filename = file.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
        File serverFile = new File(context.getRealPath("/Images/" + File.separator + newFileName));
        try {
            System.out.println("Image");
            FileUtils.writeByteArrayToFile(serverFile, file.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }

        arti.setFileName(newFileName);
        Publication art = publicationService.addPublication(arti);
        if (art != null) {
            return new ResponseEntity<Response>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Response>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(path="/Imgarticles/{id}")
    public byte[] getPhoto(@PathVariable("id") String id) throws Exception{
        Publication publication   = publicationService.getPublicationById(id);
        return Files.readAllBytes(Paths.get(context.getRealPath("/Images/")+publication.getFileName()));
    }














    /*  // Multiple File upload
    @PostMapping(value = "/rest/multipleupload", consumes = {
            "multipart/form-data"
    })

    @Operation(summary = "Upload multiple Files")

    public ResponseEntity uploadFiles(@RequestPart String metaData, @RequestPart(required = true) MultipartFile[] uploadfiles) {

        String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename()).filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));
        if (StringUtils.isEmpty(uploadedFileName)) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
            saveUploadedFiles(Arrays.asList(uploadfiles));
        } catch (IOException e) {
            return new ResponseEntity < > (HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded - " + uploadedFileName, HttpStatus.OK);
    }
    private static String UPLOADED_FOLDER = "C:/Users/oumai/OneDrive/Bureau/imagesvidpii/obba";

    // save file
    private void saveUploadedFiles(List < MultipartFile > files) throws IOException {
        File folder = new File(UPLOADED_FOLDER);
        if (!folder.exists()) {
            folder.mkdir();
        }
        for (MultipartFile file: files) {
            if (file.isEmpty()) {
                continue;
                // next pls
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
        }
    }*/
    @GetMapping("/retrieve")
    public List<Publication> getPublications() {
        List<Publication> listChambres = publicationService.retrieveAllPublications();
        return listChambres;
    }
    @DeleteMapping("/remove-publication/{publication-id}")
    public void removePublication(@PathVariable("publication-id") String chId) {
        publicationService.removePublication(chId);
    }

    @PutMapping("/modify-Publication")
    public ResponseEntity<Publication> modifyPublication(@RequestBody Publication publication){

        Publication updatedUser = publicationService.modifyPublication(publication); // Assuming there's a method in userService to modify the user
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/get")
    public Publication gettingPublication(@RequestParam("publication-id") String idPublication){
        return publicationService.getPublicationById(idPublication);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Publication>> searchPublicationsByTitle(@RequestParam(required = false) String title) {
        List<Publication> publications = publicationService.searchPublicationsByTitle(title);
        return ResponseEntity.ok().body(publications);
    }

    @PostMapping(value = "/share/{publication-id}")
    public ResponseEntity<?> sharePublication(@PathVariable("publication-id") String publicationId) {
        try {
            // Récupérer la publication à partager en utilisant son identifiant
            Publication publication = publicationService.getPublicationById(publicationId);

            // Vérifier si la publication existe
            if (publication == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publication not found");
            }

            // Appeler la méthode sharePublication du service de publication
            publicationService.sharePublication(publicationId);

            return ResponseEntity.status(HttpStatus.OK). body("ok");
        } catch (Exception e) {
            log.error("Error sharing publication", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to share publication");
        }
    }


}




