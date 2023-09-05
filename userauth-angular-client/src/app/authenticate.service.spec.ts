import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthenticateService } from './authenticate.service';
import { auditTime, of } from 'rxjs';
import { User } from './login/User';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';



class MockHttpClient{
  constructor() { }
  post = jasmine.createSpy('post').and.returnValue(of(new User()));
 
}
describe('AuthenticateService', () => {
  let service: AuthenticateService;
  let mockHttpClient: MockHttpClient = new MockHttpClient();
  let mockUser =  {
      firstName :"John",
      lastName : "Doe",
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
     
      providers: [
        {
          provide: HttpClient,
          useValue:mockHttpClient,
        }
      ]
    });

    service = TestBed.inject(AuthenticateService);
    mockHttpClient.post = jasmine.createSpy('post').and.returnValue(of(mockUser));

  });

  it('should return user when authentication succeeds', () => {
    let response = service.authenticate('','');
    response.subscribe( data => {
      expect(data.firstName).toEqual('John');
    })
  });

    it('should not throw expection', () => {
      mockHttpClient.post = jasmine.createSpy('post').and.returnValue(of(null));
      let response = service.authenticate('','');
      response.subscribe( data => {
        expect(data).toBeFalsy();
      })
    
  });
});
