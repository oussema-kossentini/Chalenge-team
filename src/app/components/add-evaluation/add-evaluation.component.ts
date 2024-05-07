import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-evaluation',
  templateUrl: './add-evaluation.component.html',
  styleUrls: ['./add-evaluation.component.scss']
})
export class AddEvaluationComponent implements OnInit {
  evaluationForm!: FormGroup;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.evaluationForm = this.fb.group({
      idEvaluation: ['', Validators.required],
      title: ['', Validators.required],
      description: ['', Validators.required],
      evolutionType: ['', Validators.required],
      debutDate: ['', Validators.required],
      endDate: [''],
      duration: ['']
    });
  }

  onSubmit(): void {
    if (this.evaluationForm.valid) {
      console.log(this.evaluationForm.value);
      // Ici, vous pouvez ajouter la logique pour soumettre le formulaire
    }
  }
}
