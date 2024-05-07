import { Component, ElementRef, HostListener, TemplateRef, ViewChild } from '@angular/core';
import { PublicationService } from '../publication.service';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Comment } from '../../comments/Comment';
import { CommentService } from '../../comments/CommentService';
import { AddPublicationComponent } from '../add-publication/add-publication.component';
import { Publication } from '../publication.model';
import { FormBuilder, FormControl } from '@angular/forms';
import Swal from 'sweetalert2';
import { Overlay } from '@angular/cdk/overlay';
import { ReactionType } from '../ReactionType.model';

@Component({
  selector: 'app-list-publication',
  templateUrl: './list-publication.component.html',
  styleUrls: ['./list-publication.component.scss']
})
export class ListPublicationComponent {
  PublicationList: any[] = [];
  sentimentResult: string = '';

  isLoading: boolean = false;
  currentPage: number = 1;
  @ViewChild('bottomOfPage') bottomOfPage!: ElementRef;
  @ViewChild('editRowPublication') editRowPublication!: TemplateRef<any>;

  @ViewChild('sidebar') sidebar!: ElementRef;
  @ViewChild('sidebarBtn') sidebarBtn!: ElementRef;
  isSidebarActive: boolean = false;
  menuItems: any[] = []; // Variable pour stocker les éléments de menu
  Comment: any[] = [];
Publications: any[]=[];
  publication: any;
  likes: number = 0; // Variable pour stocker le nombre de likes
  selectedPublication: any;
  searchCtrl!: FormControl;
  searchTypeCtrl!: FormControl;
  constructor(public publicationService: PublicationService,public dialog: MatDialog ,    private router: Router, private commentService: CommentService,  private overlay: Overlay,  private formBuilder: FormBuilder ) {

  }
  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = () => {
            // Mettre à jour l'URL de l'image dans la publication
            this.publication.imageUrl = reader.result as string;
        };
        reader.readAsDataURL(file);
    }
}
publicationId: string = ''; // ID de la publication
  ngOnInit(): void {

       this.loadPublications(); // Appel de la méthode pour charger les publications lors de l'initialisation du composant
    this.menuItems = [
      {
        label: 'Utilisateur',
        icon: 'pi pi-user',
        items: [
          { label: 'Profil', icon: 'pi pi-fw pi-user-edit' },
          { label: 'Messages', icon: 'pi pi-fw pi-comments' },
          { label: 'Notifications', icon: 'pi pi-fw pi-bell' }
        ]
      }
    ];
  }
  openAddPublicationDialog(): void {
    const dialogRef = this.dialog.open(AddPublicationComponent, {
      width: '400px', // Largeur fixe
      maxHeight: '90vh', // Hauteur maximale de 90% de la hauteur de la vue
      autoFocus: false, // Désactive la mise au point automatique sur le premier champ de saisie
      scrollStrategy: this.overlay.scrollStrategies.block(), // Ajoute un défilement vertical si nécessaire
      // Autres options de configuration si nécessaire

    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      // Traitez les données ou effectuez d'autres actions après la fermeture de la boîte de dialogue
    });
  }

  @HostListener('window:scroll', ['$event'])
  onScroll(event: any) {
    // Hauteur de la fenêtre du navigateur
    const windowHeight = 'innerHeight' in window ? window.innerHeight : document.documentElement.offsetHeight;

    // Hauteur totale de la page
    const docHeight = Math.max(document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight);

    // Position actuelle de la fenêtre par rapport au haut de la page
    const windowBottom = windowHeight + window.pageYOffset;

    // Si l'utilisateur a atteint le bas de la page et qu'aucun chargement n'est en cours
    if (windowBottom >= docHeight && !this.isLoading) {
      this.loadMorePublications(); // Charger plus de publications
    }
  }


  allPublicationsLoaded: boolean = false;

  toggleComments(publication: any): void {
    publication.showComments = !publication.showComments;
  }

  loadPublications(): void {
    this.isLoading = true;
    this.publicationService.getPublications().subscribe(
      (data: any[]) => {
        // Trier les publications par date de création (la plus récente d'abord)
        data.sort((a, b) => new Date(b.creationDate).getTime() - new Date(a.creationDate).getTime());

        this.PublicationList = data.slice(0, 10).map(publication => {
          // Traitement des données et chargement des commentaires
          const creationDate = new Date(publication.creationDate);
          return {
            ...publication,
            formattedCreationDate: creationDate.toLocaleString(),
            showComments: false,
            comments: []
          };
        });

        // Charger les commentaires pour chaque publication
        this.PublicationList.forEach(publication => {
          this.loadCommentsForPublication(publication.idPublication);
        });

        this.isLoading = false;
      },
      error => {
        console.error('Error loading publications:', error);
        this.isLoading = false;
      }
    );
  }


  loadCommentsForPublication(publicationId: string): void {
    this.commentService.getCommentsForPublication(publicationId).subscribe(
      (comments: any[]) => {
        // Trouver la publication correspondante dans PublicationList
        const publication = this.PublicationList.find(pub => pub.idPublication === publicationId);
        if (publication) {
          // Mettre à jour la liste des commentaires de la publication
          publication.comments = comments;
        }
      },
      error => {
        console.error(`Error loading comments for publication ${publicationId}:`, error);
      }
    );
  }

  showComments(publication: any): void {
    if (!publication.comments || publication.comments.length === 0) {
      // Charger les commentaires de la publication uniquement si la liste des commentaires est vide
      this.loadCommentsForPublication(publication.idPublication);
    }
    publication.showComments = !publication.showComments;

  }


  loadMorePublications(): void {
    this.isLoading = true;
    this.currentPage++;
    this.publicationService.getPublications().subscribe(
      (data: any[]) => {
        const newPublications = data.map(publication => {
          const creationDate = new Date(publication.creationDate);
          return {
            ...publication,
            formattedCreationDate: creationDate.toLocaleString()
          };
        });
        if (newPublications.length === 0) {
          this.allPublicationsLoaded = true;
        }
        this.PublicationList = [...this.PublicationList, ...newPublications];
        this.isLoading = false;
      },
      error => {
        console.error('Error loading more publications:', error);
        this.isLoading = false;
      }
    );
  }

   // Méthode pour supprimer un commentaire
   deleteComment(commentId: string) {
    if (confirm('Êtes-vous sûr de vouloir supprimer ce commentaire?')) {
      this.commentService.removeComment(commentId).subscribe(
        () => {
          console.log('Commentaire supprimé avec succès');
          // Mettre à jour les commentaires de la publication sélectionnée seulement si elle est définie
          if (this.selectedPublication && this.selectedPublication.idPublication) {
            this.loadCommentsForPublication(this.selectedPublication.idPublication);
          }
        },
        error => {
          console.error('Erreur lors de la suppression du commentaire :', error);
          // Gérer les erreurs, par exemple afficher un message d'erreur à l'utilisateur
        }
      );
    }
  }






  editPublication(Publications: any): void {
    Publications.editing = true; // Activate edit mode for the user
  }


  saveChange(publication: any): void {
    publication.editing = false; // Désactiver le mode d'édition pour l'utilisateur

    this.publicationService.modifyPublication(publication).subscribe({
      next: (response) => {
        // En cas de succès, afficher une alerte de succès
        Swal.fire({
          icon: 'success',
          title: 'Modification réussie',
          text: 'La publication a été modifiée avec succès.',
          showConfirmButton: false,
          timer: 1500 // Durée pendant laquelle l'alerte sera affichée en millisecondes (ici 1.5 seconde)
        });

        console.log('Publication modifiée avec succès:', response);
      },
      error: (error) => {
        // En cas d'erreur, afficher une alerte d'erreur
        Swal.fire({
          icon: 'success',
          title: 'Modification réussie',
          text: 'La publication a été modifiée avec succès.'
        });

        console.error('Erreur lors de la modification de la publication:', error);
      }
    });
  }

  animateBox(event: any) {
    // Animer la box lors du survol en augmentant sa taille et en changeant sa couleur de fond
    event.target.style.transform = 'scale(1.1)'; // Augmenter la taille de la box
    event.target.style.backgroundColor = '#f0f0f0'; // Changer la couleur de fond de la box
  }

  resetBox(event: any) {
    // Réinitialiser la box après le survol en remettant sa taille et sa couleur de fond par défaut
    event.target.style.transform = 'scale(1)'; // Remettre la taille de la box par défaut
    event.target.style.backgroundColor = '#ffffff'; // Remettre la couleur de fond par défaut de la box
  }

  addCommentToPublication(idPublication: string, content: string) {
    const newComment: Comment = {
      content: content,
      creationDate: new Date(), // Utilisation de 'new Date()' pour obtenir la date actuelle
    };

    this.commentService.addCommentToPublication(idPublication, newComment)
      .subscribe(
        () => {
          // Le commentaire a été ajouté avec succès, mettez à jour la liste des commentaires
          const publication = this.PublicationList.find(pub => pub.idPublication === idPublication);
          if (publication) {
            // Ajouter le nouveau commentaire à la liste des commentaires de la publication
            publication.comments.push(newComment);
          }
        },
        error => {
          // Gérer les erreurs ici
          console.error('Failed to add comment:', error);
        }
      );
  }

  showCommentInput(publication: any): void {
    publication.showCommentInput = true; // Afficher la zone de commentaire pour cette publication
  }

  addComment(publication: any): void {
    if (publication.idPublication) { // Vérifiez que l'ID de la publication est défini
      const content = publication.newComment.trim();
      if (content !== '') {
        this.addCommentToPublication(publication.idPublication, content);
        publication.newComment = ''; // Réinitialiser le champ d'entrée après l'ajout du commentaire
        publication.showCommentInput = false; // Masquer la zone de commentaire après l'ajout du commentaire

      }
    } else {
      console.error('Publication ID is undefined');
      // Gérez le cas où l'ID de la publication est manquant
      // Affichez un message d'erreur ou prenez d'autres mesures nécessaires
    }
  }
  editComment(Comment: any): void {
    Comment.editing = true; // Activer le mode d'édition du commentaire
  }
  saveChanges(comment: any): void {
    // Désactiver le mode d'édition du commentaire
    comment.editing = false;

    // Appeler le service pour enregistrer les modifications du commentaire
    this.commentService.modifyComment(comment).subscribe({
      next: () => {
        // Afficher une alerte de succès avec SweetAlert2
        Swal.fire({
          icon: 'success',
          title: 'Commentaire modifié avec succès',
          showConfirmButton: false,
          timer: 3000
        });
      },
      error: (error) => {
        console.error('Erreur lors de la modification du commentaire :', error);
        // Afficher une alerte d'erreur avec SweetAlert2 en cas d'échec
        Swal.fire({
          icon: 'error',
          title: 'Erreur',
          text: 'Une erreur s\'est produite lors de la modification du commentaire. Veuillez réessayer.',
          confirmButtonText: 'OK'
        });
      }
    });
  }
  increaseLikes(): void {
    this.likes++; // Incrémente le nombre de likes à chaque clic
  }
  publications: { id: number, likes: number }[] = [];
  showOptions: boolean = false; // Variable pour contrôler l'affichage des options
  selectedPublicationId: string = ''; // Variable pour stocker l'ID de la publication sélectionnée

  toggleOptions(publicationId: string): void {
    this.selectedPublicationId = publicationId; // Mettre à jour l'ID de la publication sélectionnée
    this.showOptions = !this.showOptions; // Inverser la valeur de showOptions
  }
  closeOptions(): void {
    this.showOptions = false; // Fermer la liste d'options
  }

  deletePublication(id: string): void {
    // Afficher une alerte de confirmation avec SweetAlert2
    Swal.fire({
      title: 'Confirmation',
      text: 'Êtes-vous sûr de vouloir supprimer cette publication ?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Oui, supprimer',
      cancelButtonText: 'Annuler'
    }).then((result) => {
      if (result.isConfirmed) {
        // Appeler le service pour supprimer la publication
        this.publicationService.removePublication(id).subscribe(
          () => {
            // Afficher une alerte de succès avec SweetAlert2
            Swal.fire({
              title: 'Supprimé!',
              text: 'La publication a été supprimée avec succès.',
              icon: 'success'
            }).then(() => {
              // Recharger la liste des publications après la suppression
              this.loadPublications();
            });
          },
          error => {
            // Afficher une alerte d'erreur avec SweetAlert2
            Swal.fire({
              title: 'Erreur',
              text: 'Une erreur s\'est produite lors de la suppression de la publication. Veuillez réessayer.',
              icon: 'error'
            });
            console.error('Erreur lors de la suppression de la publication :', error);
          }
        );
      }
    });
  }
  async sharePublication(publication: any): Promise<void> {
     this.publicationService.sharePublication(publication.idPublication).toPromise();
 publication.shareCount += 1;
     const shareUrl = `${this.publicationService.host}/api/publications/get/${publication.idPublication}`;
    const twitterShareUrl = `https://twitter.com/intent/tweet?url=${encodeURIComponent(shareUrl)}`;

    // URL de l'image de la publication
    const imageUrl = `${this.publicationService.host}/api/publications/Imgarticles/${publication.idPublication}`;

    // Ouvrir une nouvelle fenêtre pop-up avec le dialogue
    const popupWidth = 600;
    const popupHeight = 400;
    const left = (window.innerWidth - popupWidth) / 2;
    const top = (window.innerHeight - popupHeight) / 2;
    const popup = window.open('', '_blank', `width=${popupWidth}, height=${popupHeight}, top=${top}, left=${left}`);
    if (popup) {
        // Construire le contenu HTML pour la fenêtre pop-up
        popup.document.write(`<link rel="stylesheet" type="text/css"`);
        const popupContent = `
            <div>
                <h2>${publication.title}</h2>
                <p>${publication.content}</p>
                <img src="${imageUrl}" alt="${publication.title}" style="max-width: 100%;">
            </div>
        `;
        // Ajouter le contenu à la fenêtre pop-up
        popup.document.write(popupContent);
        // Ajouter des boutons de partage avec les liens appropriés
        popup.document.write(`
            <div>
            <button class="share-button" onclick="window.open('${twitterShareUrl}', '_blank')">
            <i class="fa fa-twitter"></i> Partager sur Twitter
          </button>            </div>
        `);
    } else {
        console.error('Impossible d\'ouvrir la fenêtre pop-up.');
    }}

    isEditClicked: boolean = false;
    isDeleteClicked: boolean = false;

    // Méthode pour basculer l'état du clic entre true et false
    toggleClickedState(type: string): void {
      if (type === 'edit') {
        this.isEditClicked = !this.isEditClicked;
        // Réinitialiser l'état de la suppression à false lorsque vous cliquez sur Modifier
        this.isDeleteClicked = false;
      } else if (type === 'delete') {
        this.isDeleteClicked = !this.isDeleteClicked;
        // Réinitialiser l'état de la modification à false lorsque vous cliquez sur Supprimer
        this.isEditClicked = false;
      }
    }


  searchTitle: string = ''

  searchPublicationsByTitle(): void {
  this.isLoading = true; // Activer le chargement

  this.publicationService.searchPublicationsByTitle(this.searchTitle).subscribe(
    (data: any[]) => {
      if (this.searchTitle.trim() === '') {
        // Si la recherche est vide, affichez toutes les publications
        this.PublicationList = data.map(publication => {
          // Traitement des données et chargement des commentaires
          const creationDate = new Date(publication.creationDate);
          return {
            ...publication,
            formattedCreationDate: creationDate.toLocaleString(),
            showComments: false,
            comments: []
          };
        });
      } else {
        // Filtrer et traiter les publications par titre
        const filteredPublications = data.filter(publication => {
          const title = publication.title.toLowerCase(); // Convertir le titre en minuscules pour une correspondance insensible à la casse
          return title.includes(this.searchTitle.toLowerCase()); // Vérifier si le titre contient la recherche
        }).map(publication => {
          // Traitement des données et chargement des commentaires
          const creationDate = new Date(publication.creationDate);
          return {
            ...publication,
            formattedCreationDate: creationDate.toLocaleString(),
            showComments: false,
            comments: []
          };
        });

        // Trier les publications par date de création (la plus récente d'abord)
        filteredPublications.sort((a, b) => new Date(b.creationDate).getTime() - new Date(a.creationDate).getTime());

        this.PublicationList = filteredPublications; // Mettez à jour la liste des publications filtrées
      }
      this.isLoading = false; // Désactiver le chargement
    },
    error => {
      console.error('Error loading filtered publications by title:', error);
      this.isLoading = false; // Désactiver le chargement en cas d'erreur
    }
  );
}




loadAllPublications(): void {
  // Charge toutes les publications
  this.publicationService.getPublications().subscribe(
    (data: any[]) => {
      // Traitement des données
      const filteredPublications = data.filter(publication => /[a-zA-Z]/.test(publication.title));

      this.PublicationList = filteredPublications.slice(0, 10).map(publication => {
        // Traitement des données et chargement des commentaires
        const creationDate = new Date(publication.creationDate);
        return {
          ...publication,
          formattedCreationDate: creationDate.toLocaleString(),
          showComments: false,
          comments: []
        };
      });
    },
    error => {
      console.error('Error loading publications:', error);
    }
  );
}
edittPublication(publicationId: string): void {
  this.publicationService.getPublicationById(publicationId).subscribe(
    (publication: Publication) => {
      // Ouvrir le formulaire de modification avec les détails de la publication
      this.openModifyPublicationForm(publication);
    },
    error => {
      console.error('Error loading publication:', error);
    }
  );
}

openModifyPublicationForm(publication: Publication): void {
  const dialogRef = this.dialog.open(AddPublicationComponent, {
    width: '50%', // Ajustez la largeur selon vos besoins
    data: { publication } // Transmettez les détails de la publication au composant de modification
  });

  dialogRef.afterClosed().subscribe(result => {
    console.log('The dialog was closed');
    // Traitez les données ou effectuez d'autres actions après la fermeture de la boîte de dialogue
  });
}


loadFilteredPublications(): void {
  this.isLoading = true; // Activer le chargement

  this.publicationService.searchPublicationsByTitle('').subscribe(
    (data: any[]) => {
      // Filtrer les publications ayant un titre contenant des caractères spécifiques
      this.PublicationList = data.filter(publication => {
        const title = publication.title.toLowerCase(); // Convertir le titre en minuscules pour une correspondance insensible à la casse
        return /[a-zA-Z]/.test(title); // Vérifier si le titre contient au moins un caractère alphabétique
      }).map(publication => {
        // Traitement des données et chargement des commentaires
        const creationDate = new Date(publication.creationDate);
        return {
          ...publication,
          formattedCreationDate: creationDate.toLocaleString(),
          showComments: false,
          comments: []
        };
      });
      this.isLoading = false; // Désactiver le chargement
    },
    error => {
      console.error('Error loading filtered publications:', error);
      this.isLoading = false; // Désactiver le chargement en cas d'erreur
    }
  );
}


  analyzeSentiment(sentence: string): void {
    this.commentService.analyzeSentiment(sentence).subscribe(
      (response: any) => {
        this.sentimentResult = response.result;  // Assuming 'result' is the key for the sentiment result
        console.log('Sentiment analysis result:', this.sentimentResult);
      },
      error => {
        console.error('Error analyzing sentiment:', error);
        this.sentimentResult = ''; // Reset on error
      }
    );
  }




}
