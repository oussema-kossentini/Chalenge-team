import { Component, OnInit } from '@angular/core';
import { PublicationService } from '../publication.service';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { FormBuilder, FormsModule } from '@angular/forms';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Publication } from '../publication.model';
@Component({
  selector: 'app-retrieve-publication',
  templateUrl: './retrieve-publication.component.html',
  styleUrls: ['./retrieve-publication.component.scss']
})
export class RetrievePublicationComponent implements OnInit {
  PublicationList: any[] = [];
  Publication: any[] = [];
  constructor(
    public publicationService: PublicationService,
    private router: Router,
    private dialog: MatDialog, // Injection de MatDialog
    private sanitizer: DomSanitizer
  ) {    
  }

  imageFile!: File;
  publicationData: any = {};
  imageUrls: string[] = [];
  publications!: Publication[];

  getPublications(): void {
    this.publicationService.getPublications().subscribe(
      (data: Publication[]) => {
        this.publications = data;

        // Une fois que les publications sont récupérées, itérez sur chaque publication
        // et récupérez l'URL de l'image associée
        this.publications.forEach((publication, index) => {
          if (publication.idPublication) {
            this.publicationService.getPublicationById(publication.idPublication).subscribe(
              (imageData: any) => {
                // Si l'image est récupérée avec succès, stockez son URL dans la publication correspondante
                this.imageUrls[index] = imageData.fileUrl;
              },
              (error) => {
                console.error('Erreur lors de la récupération de l\'image pour la publication : ', error);
              }
            );
          }
        });
      },
      (error) => {
        console.error('Erreur lors de la récupération des publications : ', error);
      }
    );
  }

  ngOnInit(): void {
    this.loadPublications();
  }


  loadPublications(): void {
 
this.publicationService.getPublications().subscribe(
  (data: any[]) => {
    this.PublicationList = data.map(publication => {
      const creationDate = new Date(publication.creationDate);
      return {
        ...publication,
        formattedCreationDate: creationDate.toLocaleString(),
        showComments: false, // Ajouter une propriété pour contrôler l'affichage des commentaires
        comments: [] // Initialiser la liste des commentaires vide
      };
    });

 
  },
  error => {
    console.error('Error loading publications:', error);

  }
);
  }

  
 
  

  deletePublication(id: string): void {
    if (confirm('Are you sure to delete this class?')) {
      this.publicationService.removePublication(id).subscribe(
        () => {
          console.log('Class deleted successfully');
          this.loadPublications();
        },
        error => console.log('Error during class deletion', error)
      );
    }
  }
  editPublication(Publication: any): void {
    Publication.editing = true; // Activate edit mode for the user
  }

  saveChanges(publication: any): void {
    publication.editing = false; // Deactivate edit mode for the user
    this.publicationService.modifyPublication(publication).subscribe({
      next: (response) => {
        console.log('User modified successfully:', response);
        // Handle successful response, e.g., display confirmation message
      },
      error: (error) => {
        console.error('Error modifying user:', error);
        // Handle error, e.g., display error message to user
      }
    });
  }
  
 
}
