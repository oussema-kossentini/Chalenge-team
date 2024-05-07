import { Component, OnInit, ElementRef, ViewChild, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import AOS from 'aos';
import { PublicationService } from '../publication.service';
import {  Publication } from '../publication.model';
import { Router } from '@angular/router';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-add-publication',
  templateUrl: './add-publication.component.html',
  styleUrls: ['./add-publication.component.scss']
})
export class AddPublicationComponent implements OnInit {
  
publicationForm!: FormGroup;
  fileNames: string[] = [];
  fileUrl: string | undefined; // Déclaration de la propriété fileUrl

  @ViewChild('fileInput') fileInput!: ElementRef<HTMLInputElement>;

  
  
  constructor(
    private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private publicationService: PublicationService,
    private router: Router, // Injection du Router
    @Inject(MAT_DIALOG_DATA) public data: { publicationId: string },
    private dialogRef: MatDialogRef<AddPublicationComponent>

  ) {}

  ngOnInit() {
    this.publicationForm = this.fb.group({
      content: ['', Validators.required],
      title:['', Validators.required],
      file: [null] // Ajoutez un champ pour le fichier, initialisé à null

      
      
    });
    this.publicationService.dataForm = this.publicationForm; // Initialiser dataForm dans le service

    this.publicationService.publication$.subscribe(publication => {
      if (publication) {
        this.publicationForm.patchValue({
          content: publication.content,
          title: publication.title
        });
      }
    });
    if (this.data && this.data.publicationId) {
      this.loadPublication(this.data.publicationId);
    }
  }

  loadPublication(publicationId: string): void {
    this.publicationService.getPublicationById(publicationId).subscribe(
      (publication: Publication) => {
        this.publicationForm.patchValue({
          content: publication.content,
          title: publication.title,
          // Patch other form fields here as needed
        });
      },
      error => {
        console.error('Error loading publication:', error);
      }
    );
  }

  // Other methods for handling file input, emojis, etc.

  saveChanges(): void {
    // Implement the logic to save changes
    // You can access form values using this.publicationForm.value
  }

  cancel(): void {
    this.dialogRef.close();
  
  }
  get f() { return this.publicationService.dataForm.controls; }
  addData() {
    const formData = new FormData();
    const publication = this.publicationService.dataForm.value;
    formData.append('publication', JSON.stringify(publication));
    formData.append('file', this.userFile);
    this.publicationService.createData(formData).subscribe(data => {
      // La navigation vers une autre URL est retirée
    });
  }
  
  userFile: any ;
  public message!: string;
  imgURL: any;
  public imagePath: any;

  onSelectedFile(event: Event) {
    const inputElement = event.target as HTMLInputElement;
    if (inputElement.files && inputElement.files.length > 0)
    {
      const file = inputElement.files[0];
      this.userFile = file;
      // this.f['profile'].setValue(file);
 
      var mimeType = file.type;
      if (mimeType.match(/image\/*/) == null) {
        this.message = "Only images are supported.";
        return;
      }
 
      var reader = new FileReader();
    
      this.imagePath = file;
      reader.readAsDataURL(file); 
      reader.onload = (_event) => { 
        this.imgURL = reader.result as string; 
      }
    }
}





  showEmojis = false;
  content = '';
  emojis = ['😀', '😂', '👍', '🚀', '💻', '🎉', '❤️']; // Liste d'exemple
title='';


toggleEmojis() {
  this.showEmojis = !this.showEmojis; // Inverse la valeur de showEmojis pour afficher ou masquer la liste
}


  addEmoji(emoji: string) {
    const contentControl = this.publicationForm.get('content');
    const currentContent = contentControl?.value || ''; // Obtenez la valeur actuelle du contenu
    contentControl?.setValue(currentContent + emoji); // Ajoutez l'emoji à la valeur existante
    this.toggleEmojis(); //Fermer la liste d'emojis après un choix

  }
  PublicationList: Publication[] = [];

  publish(): void {
    if (this.publicationForm.valid ) {
      const publicationData: Publication = {
        content: this.publicationForm.value.content,
        title: this.publicationForm.value.title,
        creationDate: new Date() // Utiliser la date actuelle comme date de création
      };

      // Vérifier si un fichier a été sélectionné
      if (this.userFile) {
        const formData = new FormData();
        formData.append('publication', JSON.stringify(publicationData));
        formData.append('file', this.userFile);

        // Envoyer l'objet Publication et le fichier au service de publication
        this.publicationService.createData(formData).subscribe({
          next: () => {
            // Ajouter la nouvelle publication à PublicationList
            this.PublicationList.push(publicationData);
            
            // Afficher une alerte de succès avec SweetAlert2
            Swal.fire({
              icon: 'success',
              title: 'Publication ajoutée avec succès',
              showConfirmButton: false,
              timer: 3000
            });

            this.publicationForm.reset();
          },
          error: (err) => console.error(err)
        });
      } else {
        console.error('Aucun fichier sélectionné');
      }
    } else {
      // Afficher une alerte d'erreur avec SweetAlert2
      Swal.fire({
        icon: 'error',
        title: 'Erreur',
        text: 'Veuillez remplir tous les champs du formulaire.'
      });
    }
  }
  
  imageUrls:string[]=[];



   removeImages(i:number) {
      this.imageUrls.splice(i, 1);
   
    }
    
   
  

    triggerFileInputClick() {
      this.fileInput.nativeElement.click();
    }
    areFieldsValid(): boolean {
  // Vérifiez ici si tous les champs obligatoires sont remplis
  // Si oui, retournez true, sinon retournez false

  // Par exemple, si vous avez un champ obligatoire 'title' :
  if (!this.title || this.title.trim() === '') {
    return false;
  }

  // Vérifiez le champ 'content'
  if (!this.content || this.content.trim() === '') {
    return false;
  }

  // Vérifiez d'autres champs obligatoires de la même manière

  return true; // Si tous les champs sont valides
}

   
  }
