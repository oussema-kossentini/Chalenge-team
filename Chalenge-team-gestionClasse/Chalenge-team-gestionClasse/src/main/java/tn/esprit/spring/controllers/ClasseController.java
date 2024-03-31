package tn.esprit.spring.controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.spring.entities.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Level;
import tn.esprit.spring.repositories.ClasseRepository;
import tn.esprit.spring.services.IClasseService;

import javax.persistence.IdClass;

@RestController
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("/api/classes")
@CrossOrigin(origins = "http://localhost:4200/**")
public class ClasseController {

    IClasseService classeService;
ClasseRepository classeRepository;
    @PostMapping("add/classe")
    public Classe addinClasse(@RequestBody Classe us) {

        return classeService.addClasse(us);
    }

    @GetMapping("/retrieve-all-classe")
    public List<Classe> getClasse() {
        log.info("Retrieving all classes");
        List<Classe> listClasse = classeService.retrieveAllClasses();
        log.info("Classes retrieved: {}", listClasse);
        return listClasse;
    }


    @DeleteMapping("/remove-classes/{classe-id}")
    public void removeChambre(@PathVariable("classe-id") String chId) {
        classeService.removeClasse(chId);
    }

    //@PutMapping("/modify-classe/{id}")
    //public Classe modifyChambre(@PathVariable String id, @RequestBody Classe c) {
       // c.setIdClasse(id);
      //  return classeService.addClasse(c);

   // }

    @PutMapping("/modify-classe")
    public ResponseEntity<Classe> modifyUser(@RequestBody Classe classe){

    Classe updatedUser = classeService.modifyClasse(classe); // Assuming there's a method in userService to modify the user
        return ResponseEntity.ok(updatedUser);
}

    @GetMapping("/level")
    public Level[] getLevels() {
        return Level.values();
    }


    @GetMapping("/{id}")
    public Classe gettingClasse(@RequestParam("classe-id") String idClasse){
        return classeService.getClasseById(idClasse);
    }

    @PostMapping("ajouter-affecter/{idSpecialite}")
    Classe ajouterFoyerEtAffecterAUniversite (@RequestBody Classe classe, @PathVariable String idSpecialite){
        return classeService.ajouterFoyerEtAffecterAUniversite(classe,idSpecialite);
    }


    @Operation(description = "récupérer toutes les Posts pour un user ")
    @GetMapping("/posts/{idSpecialite}")
    public ResponseEntity<List<Classe>> getPostsBySpecialite(@PathVariable("idSpecialite") String idSpecialite) {
        List<Classe> posts = classeService.retrievePostsByidUser(idSpecialite);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    //hedhom tjibli list mta3 etudiant  fi wost el classe

    @GetMapping("/getEtudiantFromClass/{idClasse}")
    public List<User> getEtudiantFromClass(@PathVariable("idClasse") String idClasse){
        return classeService.getEtudiantFromClass(idClasse);
    }
//hedhoum tjibli list mta3 el claase
    @GetMapping("/getProfessorFromClass/{idClasse}")
    public List<User> getProfessorFromClass(@PathVariable("idClasse") String idClasse){
        return classeService.getProfessorFromClass(idClasse);
    }

    @PostMapping("/affecterUserInClass/{idUser}/{idClasse}")
    public Classe affecterUserInClass(@PathVariable String idUser,@PathVariable  String idClasse) {
        return classeService.affecterUserInClass(idUser,idClasse);

    }

    @GetMapping("/getEtudiant")
    public List<User> getEtudiant(){
        return classeService.getEtudiant();
    }
    @GetMapping("/getEnsignat")
    public List<User> getEnseignat(){
        return classeService.getEnsignat();
    }




    @DeleteMapping("/deleteAndUnassignSpecialite/{idClasse}")
    public void deleteClasseAndUnassignSpecialite(@PathVariable String idClasse) {
        classeService.deleteClasseAndSpecialiteAssociation(idClasse);
    }
    @GetMapping("/pdf")
    public void exportClassesPdf(HttpServletResponse response) throws IOException, DocumentException {
        // Set the content type and attachment header
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"classes_etudiants.pdf\"");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        // Inclure un logo
        // Inclure un logo
        // Obtenez le ClassLoader
// Obtenez le ClassLoader
       // ClassLoader classLoader = getClass().getClassLoader();
// Spécifiez le chemin complet vers l'image

        try {
            String logoPath = "C:/Users/ASUS/Downloads/wetransfer_testalpha-rar_2024-02-15_1309/courszelloback/courszelloback/src/main/java/tn/esprit/spring/assests/logo.png";

            File logoFile = new File(logoPath);
            Image logo = Image.getInstance(logoFile.toURI().toURL());
            logo.scalePercent(10);
            logo.setAlignment(Element.ALIGN_CENTER);
            document.add(logo);
        } catch (BadElementException | IOException e) {
            e.printStackTrace();
        }


        // Définir des polices et des couleurs
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, new BaseColor(255, 0, 0));
        Font headingFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
        Font textFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);

        Paragraph title = new Paragraph("Liste des Classes", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);


        // Récupérer la liste des classes
        List<Classe> classes = classeService.retrieveAllClasses();

        if (classes != null) {
            for (Classe classe : classes) {
                // Ajouter le nom de la classe comme un titre
                Paragraph classeTitle = new Paragraph("Classe: " + classe.getNameClasse(), titleFont);
                classeTitle.setAlignment(Element.ALIGN_CENTER);
                classeTitle.setSpacingAfter(20);
                document.add(classeTitle);

                // Récupérer les étudiants de la classe
                List<User> etudiants = getEtudiantFromClass(classe.getIdClasse());

                // Créer une table pour les étudiants de cette classe avec 2 colonnes pour Nom et Prénom
                PdfPTable table = new PdfPTable(2); // Ajustement ici pour 2 colonnes
                table.setWidthPercentage(100);
                float[] columnWidths = {2f, 2f}; // Ajustement des largeurs de colonnes
                table.setWidths(columnWidths);

                // Ajouter l'en-tête de la table
                PdfPCell cell2 = new PdfPCell(new Paragraph("Nom", headingFont));
                PdfPCell cell3 = new PdfPCell(new Paragraph("Prénom", headingFont));

                cell2.setBackgroundColor(new BaseColor(255, 0, 0));
                cell3.setBackgroundColor(new BaseColor(255, 0, 0));

                cell2.setPadding(10);
                cell3.setPadding(10);

                table.addCell(cell2);
                table.addCell(cell3);

                // Ajouter les données des étudiants
                for (User etudiant : etudiants) {
                    PdfPCell studentCell2 = new PdfPCell(new Paragraph(etudiant.getLastName(), textFont));
                    PdfPCell studentCell3 = new PdfPCell(new Paragraph(etudiant.getFirstName(), textFont));

                    studentCell2.setPadding(10);
                    studentCell3.setPadding(10);

                    table.addCell(studentCell2);
                    table.addCell(studentCell3);
                }

                document.add(table);
                // Ajouter un espace après chaque table des étudiants d'une classe
                document.add(new Paragraph(" "));
            }
        }

        document.close();
    }


}
