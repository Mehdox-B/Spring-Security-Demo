import { TestBed } from '@angular/core/testing';

import { SecuritysvcService } from './securitysvc.service';

describe('SecuritysvcService', () => {
  let service: SecuritysvcService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SecuritysvcService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
