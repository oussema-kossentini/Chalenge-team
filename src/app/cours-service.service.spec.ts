import { TestBed } from '@angular/core/testing';

import { CourseService } from './cours-service.service';

describe('CoursServiceService', () => {
  let service: CourseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CourseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
