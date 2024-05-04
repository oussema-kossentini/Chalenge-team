import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserrService } from '../service/userr.service';
import { ChatService } from '../service/chat.service';
import { Chat } from '../../models/chat.model';
import { User } from '../../models/user.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-mainn',
  templateUrl: './mainn.component.html',
  styleUrl: './mainn.component.scss'
})
export class MainnComponent implements OnInit{
  username: string = '';
  alluser: any[] = [];

  // Propriétés
  chatId: any = 0;
  registerForm!: FormGroup;
  loginForm!: FormGroup;
  successregister: boolean = false;
  registermsg = "";
  alert = "";
  loginmsg = "";
  alert2 = "";
  successlogin: boolean = false;
  userObj: User = {} as User;
  secondUsername = "";
  chatObj: Chat = {} as Chat;
  chatData: any = [];
  check = "";
  loggedIn: boolean = false;
  loggedOut: boolean = true;
  chatbox: boolean = true;
  constructor(private router: Router, private userService: UserrService, private chatService: ChatService,     private formBuilder: FormBuilder // Injectez le FormBuilder
) { }
  ngOnInit(): void {
    this.loadAllUsers();
    this.initForms(); // Initialisation des formulaires lors de l'initialisation du composant

  }
 // Initialisation des formulaires
 initForms() {
  this.registerForm = this.formBuilder.group({
    username: ['', Validators.required] // Définissez des validateurs requis pour le nom d'utilisateur
  });

  this.loginForm = this.formBuilder.group({
    username: ['', Validators.required]
  });
}

  loadAllUsers() {
    this.userService.getAllUsers().subscribe(
      users => {
        this.alluser = users;
      },
      error => {
        console.error('Une erreur s\'est produite lors de la récupération des utilisateurs : ', error);
      }
    );
  }
  login() {
    if (this.loginForm.valid) {
      this.userService.getUserByUsername(this.loginForm.value.username).subscribe(
        (data: any) => {
          console.log(data);
  
          this.showAlert('success', 'Successfully LoggedIn');
          sessionStorage.setItem("username", this.loginForm.value.username);
          this.check = this.loginForm.value.username;
          this.loginForm.reset();
          this.loggedIn = true;
          this.loggedOut = false;
        },
        (error) => {
          console.log(error.error);
          if (error.status == 404) {
            this.showAlert('danger', 'Not a registered user');
          } else {
            this.showAlert('danger', 'Error');
          }
        }
      );
    }
  }
  
  
  
  logout() {
    this.router.navigateByUrl('');
  }
  

 startChatWithUser(selectedUsername: string) {
  this.chatService.getChatByUsernames(this.username, selectedUsername).subscribe(
    (chat) => {
      if (chat) {
        this.showAlert('success', 'Navigating to existing conversation');
        this.router.navigate(['/messaging', chat.chatId]);
      } else {
        const newChat = {
          firstUserName: this.username,
          secondUserName: selectedUsername,
          messageList: []
        };
        this.chatService.createChat(newChat).subscribe(
          (createdChat) => {
            this.showAlert('success', 'Navigating to new conversation');
            this.router.navigate(['/messaging', createdChat.chatId]);
          },
          (error) => {
            this.showAlert('danger', 'Error creating chat: ' + error.message);
          }
        );
      }
    },
    (error) => {
      this.showAlert('danger', 'Error retrieving chat: ' + error.message);
    }
  );
}

showAlert2(type: string, message: string) {
  console.log(message); // Affiche le message dans la console
}

addUser() {
  if (this.registerForm.valid) {
    this.userObj.userName = this.registerForm.value.username;
    this.userService.addUser(this.userObj).subscribe(
      (data: any) => {
        console.log(data);

        this.showAlert('success', 'Successfully added');
        this.registerForm.reset();
      },
      (error) => {
        console.log(error.error);
        if (error.status == 409) {
          this.showAlert('danger', 'Already registered');
        } else {
          this.showAlert('danger', 'Error');
        }
      }
    );
  } else {
    // Afficher un message d'erreur si le formulaire n'est pas valide
    this.showAlert('danger', 'Please enter a valid username');
  }
}

  
  showAlert(type: string, message: string) {
    this.successregister = true;
    this.alert = type;
    this.registermsg = message;
  }
  
}
