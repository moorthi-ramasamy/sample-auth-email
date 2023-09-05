import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import { AuthenticateService } from '../authenticate.service';
import { Observable, of } from 'rxjs';
import { Router } from '@angular/router';

class MockAuthenticateService{
  constructor() { }
  public authenticate = jasmine.createSpy('authenticate').and.returnValue(of(true));
}

class MockRouter{
  constructor() { }
  public navigate = jasmine.createSpy('navigate');
}

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let mockAuthenticateService = new MockAuthenticateService();
  let mockRouter = new MockRouter();

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [
        {
          provide: AuthenticateService,
          useValue: mockAuthenticateService,
        },
        {
          provide: Router,
          useValue: mockRouter,
        }
      ],
    });
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    mockAuthenticateService.authenticate = jasmine.createSpy('authenticate').and.returnValue(of(true));
    mockRouter.navigate=   jasmine.createSpy('navigate');

    let userName = "testUser";
    let password = "testPwd";
    component.userName = userName;
    component.password = password;
  });

  it('should call the authenticate method in Authenticate Service only once', () => {
    component.doAuthenticate();
    expect(mockAuthenticateService.authenticate).toHaveBeenCalledTimes(1);
  });

  it('should authenticate for valid user', () => {
     component.doAuthenticate();
     expect(component.isAuthenticated).toBeTrue();
   });

  it('should not authenticate for invalid user', () => {
     mockAuthenticateService.authenticate = jasmine.createSpy('authenticate').and.returnValue(of(false));
     component.doAuthenticate();
     expect(component.isAuthenticated).toBeFalse();
   });
});
