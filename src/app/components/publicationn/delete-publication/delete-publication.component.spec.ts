import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeletePublicationComponent } from './delete-publication.component';

describe('DeletePublicationComponent', () => {
  let component: DeletePublicationComponent;
  let fixture: ComponentFixture<DeletePublicationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DeletePublicationComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeletePublicationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
