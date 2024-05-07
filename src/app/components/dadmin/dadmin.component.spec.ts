import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DadminComponent } from './dadmin.component';

describe('DadminComponent', () => {
  let component: DadminComponent;
  let fixture: ComponentFixture<DadminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DadminComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DadminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
