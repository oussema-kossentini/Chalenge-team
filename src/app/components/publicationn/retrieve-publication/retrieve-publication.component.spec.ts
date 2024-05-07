import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RetrievePublicationComponent } from './retrieve-publication.component';

describe('RetrievePublicationComponent', () => {
  let component: RetrievePublicationComponent;
  let fixture: ComponentFixture<RetrievePublicationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RetrievePublicationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RetrievePublicationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
