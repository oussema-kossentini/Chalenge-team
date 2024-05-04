import { Component } from '@angular/core';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-sondage',
  templateUrl: './sondage.component.html',
  styleUrl: './sondage.component.scss'
})
export class SondageComponent {
  sondages: any[] = [];
  titre: string = '';
  options: string[] = [];
  showSuccessAlert: boolean = false;

  voteCount: number = 0;
  constructor() {
    // Récupérer les sondages depuis le stockage local du navigateur lors du chargement du composant
    const storedSondages = localStorage.getItem('sondages');
    if (storedSondages) {
      this.sondages = JSON.parse(storedSondages);
    }
  }

  creerSondage() {
    if (this.titre && this.options.length > 0) {
      const nouveauSondage = {
        titre: this.titre,
        options: this.options.map(option => ({ label: option, votes: 0 }))
      };
      this.sondages.push(nouveauSondage);
      // Sauvegarder les sondages dans le stockage local du navigateur
      localStorage.setItem('sondages', JSON.stringify(this.sondages));
      // Réinitialiser les champs du formulaire
      this.titre = '';
      this.options = [];
    }
  }

  ajouterOption() {
    if (this.options.length < 5) {
      this.options.push('');
    }
  }

  supprimerOption(index: number) {
    this.options.splice(index, 1);
  }
  nombreIncrements: number = 0;
  incrementArray: number[] = [];

  voter(sondage: any, option: any) {
    option.votes++;
    this.voteCount++;
    this.nombreIncrements++; // Incrémenter le nombre d'incréments à chaque clic
    this.incrementArray = Array.from({ length: this.nombreIncrements }, (_, index) => index);
    // Autre logique de vote...
  
  
    if (option.votes === 10) {
      Swal.fire({
        icon: 'success',
        title: 'Evénement planifié avec succès!',
        showConfirmButton: false,
        timer: 3000
      });
    }
  }
  
  afficherPopup() {
    this.showSuccessAlert = true;
    setTimeout(() => {
      this.showSuccessAlert = false;
    }, 3000);
  }
  
}
