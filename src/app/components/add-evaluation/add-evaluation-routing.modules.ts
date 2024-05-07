import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddEvaluationComponent } from './add-evaluation.component'; // Assurez-vous que le chemin est correct

const routes: Routes = [
  {
     path: 'add-evaluation', component: AddEvaluationComponent ,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AddEvaluationRoutingModule { }
