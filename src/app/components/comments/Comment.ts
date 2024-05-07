// Modèle de commentaire
export interface Comment {
    id?: string;
    content: string;
    creationDate?: Date;
    user?: string; // ID de l'utilisateur qui a posté le commentair
  }