import { Component, ElementRef, OnInit, QueryList, ViewChildren } from '@angular/core';
import {HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Router, RouterLink } from '@angular/router';
import { CardDetectionService } from '../services/card-detection.services';
import { UserVerificationService } from '../services/user-verification.services';
import { AdminDetectionService } from '../services/admin-detection.services';
import { catchError, timeout } from 'rxjs/operators';
import { throwError, TimeoutError  } from 'rxjs';

@Component({
  selector: 'app-verification',
  templateUrl: './verification.component.html',
  styleUrls: ['./verification.component.css']
})
export class VerificationComponent{
  userVerificationService!:UserVerificationService;
  cardDetectionService!:CardDetectionService;
  @ViewChildren('pinDigit') pinDigits!: QueryList<ElementRef>;
  id: number = 0;
  pin: number = 0;
  pinError: string = '';
  isConfirmed: boolean = false;

  constructor(
    private adminService : AdminDetectionService,
    private http:HttpClient,
    private router:Router,
    private cardService : CardDetectionService,
    private verificationService : UserVerificationService
    ) { }

  ngOnInit(): void {
    this.userVerificationService = this.verificationService
    this.cardDetectionService = this.cardService
    this.id = this.cardDetectionService.getCardNumber();

    if(!this.cardService.getCard()){
      this.router.navigate(["/"]);
    }
  }

  onDigitInput(event: any, index: number): void {
    const inputValue = event.target.value;

    const digitValue = parseInt(inputValue, 10);

    if (!isNaN(digitValue)) {
      this.pin = this.pin * 10 + digitValue;
    }

    if (inputValue && inputValue.length === 1) {
      const nextIndex = index + 1;
      if (nextIndex < this.pinDigits.length) {
        this.pinDigits.toArray()[nextIndex].nativeElement.focus();
      }
    } else {
      const nextIndex = index - 1;
      if (nextIndex >= 0) {
        this.pinDigits.toArray()[nextIndex].nativeElement.focus();
      }
    }
    this.pinError = '';
  }

  confirmPin(): void {
    if (this.pin === 0) {
      this.pinError = 'PIN-код не може бути порожнім та повинен містити лише цифри.';
      this.resetPinValues();
      return;
    }

    if(this.adminService.getIsOpened()){

      let atmData = {
        id: 1,
        atmKey:this.pin
      }

      this.http.get("http://localhost:8080/compare",  { params: atmData })
     .subscribe((val:any) => {
        if(!val){
          this.pinError = 'PIN-код введено неправильно!';
          this.resetPinValues();
        }else{
          this.adminService.setConfirm(true);
          this.router.navigate(["/admin-page"]);
        }
    }, (val:any) => {
      
    });
    }else{

      let data = {
        id:this.id,
        pin:this.pin
      }

      this.http.get("http://localhost:8080/user/pin",  { params: data })
      .subscribe((val:any) => {
          this.userVerificationService.setVeriefication(true)
          this.userVerificationService.setUser(val);
          this.router.navigate(["/"]);
      }, (val:any) => {
        console.log(val)
        if (val.error.blocked){
          this.pinError = 'Картку заблоковано, зверніться до банку!';
          this.resetPinValues();
        }else{
          this.pinError = 'PIN-код введено неправильно!';
          this.resetPinValues();
        }
        
          console.log("There is error")
      });
    }
    this.resetPinValues();
  }

  private resetPinValues(): void {
    this.pin = 0;
    this.pinDigits.forEach((digit, index) => {
      digit.nativeElement.value = '';
      if (index === 0) {
        digit.nativeElement.focus();
      }
    });
  }

  private handleError(error: any) {
    if (error instanceof HttpErrorResponse) {
      console.error(`Server-side error: ${error.status}\nMessage: ${error.message}`);
      this.router.navigate(['/error-page']);
    } else if (error instanceof TimeoutError) {
      console.error('Request timed out.');
      this.router.navigate(['/error-page']);
    } else {
      console.error('An error occurred:', error);
    }
  }
}
