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
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { DadminComponent } from './components/dadmin/dadmin.component';
import { AddPublicationComponent } from './components/publicationn/add-publication/add-publication.component';
import { AddSpecialiterComponent } from './components/add-specialiter/add-specialiter.component';
import { DashbordlatbarComponent } from './components/dashbordlatbar/dashbordlatbar.component';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { ProfscheduleComponent } from './components/profschedule/profschedule.component';
import { SessionComponent } from './session/session.component';
import { HttpClientModule } from '@angular/common/http';
import { ModifscheduleComponent } from './components/schedule/modifschedule/modifschedule.component';
import { EmploiComponent } from './components/schedule/emploi/emploi.component';





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
    AddSpecialiterComponent,
    DashbordlatbarComponent,
    ProfscheduleComponent,
    SessionComponent,
    ModifscheduleComponent,
    EmploiComponent,






  ],
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
    InputTextModule,
 
    ButtonModule,
    FormsModule,
    HttpClientModule


  ],
  providers: [
    provideClientHydration(),
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
