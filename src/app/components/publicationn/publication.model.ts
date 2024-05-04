import { ReactionType } from "./ReactionType.model";


export interface Publication {
 
  idPublication?: string;
  title: string;
  content: string;
  fileUrl?: string; // Ajout d'un champ pour l'URL du fichier
  creationDate?: Date; // Ajoutez cette propriété pour stocker la date de création
  fileName?: string; // Ajoutez une propriété pour le nom du fichier
  imageToken?: string; // Ajoutez le champ pour stocker le token JWT de l'image
  likes?: number; // Déclaration de l'attribut likes de type number
  shareCount ?: number; // Nombre de partages de la publication

  comments?: Comment[]; // Liste des commentaires associés à la publication
 // Ajouter la nouvelle propriété
 reactionType?: ReactionType; // Nouvelle propriété pour le type de réaction

}

