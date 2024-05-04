import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { HomeComponent } from './components/home/home.component';
import { FooterComponent } from './components/footer/footer.component';
import { CoursComponent } from './components/cours/cours.component';
import { ScheduleComponent } from './components/schedule/schedule.component';
import { EvaluationComponent } from './components/evaluation/evaluation.component';
import { ClasseComponent } from './components/classe/classe.component';
import { SpecialiteComponent } from './components/specialite/specialite.component';
import { PublicationComponent } from './components/publication/publication.component';
import { RegisterComponent } from './components/register/register.component';
import { AddEvaluationComponent } from './components/add-evaluation/add-evaluation.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { ReactiveFormsModule } from '@angular/forms';
import { DadminComponent } from './components/dadmin/dadmin.component';
import { AddPublicationComponent } from './components/publicationn/add-publication/add-publication.component';
import { ButtonModule } from 'primeng/button';
import {CalendarModule} from 'primeng/calendar';
import { FormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { HttpClientModule } from '@angular/common/http';
import {MatGridListModule} from '@angular/material/grid-list';
import { MatListModule } from '@angular/material/list';
import { RetrievePublicationComponent } from './components/publicationn/retrieve-publication/retrieve-publication.component';
import { DeletePublicationComponent } from './components/publicationn/delete-publication/delete-publication.component';
import { ListPublicationComponent } from './components/publicationn/list-publication/list-publication.component';
import { v4 as uuidv4 } from 'uuid' // Importer uuid
import { MenubarModule } from 'primeng/menubar';
import { AvatarModule } from 'primeng/avatar';
import { FouterComponent } from './components/publicationn/fouter/fouter.component';
import { CommentComponent } from './components/comments/comment/comment.component';
import { MainnComponent } from './components/mainn/mainn.component';
import { MessagingComponent } from './components/messaging/messaging.component';
import { SondageComponent } from './components/sondage/sondage.component';
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    FooterComponent,

    CoursComponent,
      ScheduleComponent,
      EvaluationComponent,
      ClasseComponent,
      SpecialiteComponent,
      PublicationComponent,
      RegisterComponent,
      AddEvaluationComponent,
      DadminComponent,
  
      AddPublicationComponent,
      RetrievePublicationComponent,
      DeletePublicationComponent,
      ListPublicationComponent,
      FouterComponent,
      CommentComponent,
      MainnComponent,
      MessagingComponent,
      SondageComponent  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    ReactiveFormsModule,
    ButtonModule,
    CalendarModule,
    FormsModule , 
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule,
    HttpClientModule,
   MatGridListModule,
   MatListModule,
   MenubarModule,
    ButtonModule,
    AvatarModule
  ],
  providers: [
    provideClientHydration(),
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
