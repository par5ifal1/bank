import { Component } from '@angular/core';
import {HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Router } from '@angular/router';
import { UserVerificationService } from '../services/user-verification.services';
import { AdminDetectionService } from '../services/admin-detection.services';
import { CardDetectionService } from '../services/card-detection.services';
import { TransactionService } from '../services/transaction.services';
import { catchError, timeout } from 'rxjs/operators';
import { throwError, TimeoutError  } from 'rxjs';

@Component({
    selector: 'app-phone-payment',
    templateUrl: './phone-payment.component.html',
    styleUrls: ['./phone-payment.component.css']
  })
  export class PhonePaymentComponent {
    userVerificationService!:UserVerificationService;
    adminDetectionService!:AdminDetectionService;
    cardDetectionService!:CardDetectionService;
    isATMOpened!:boolean;
    isVerified!:boolean;
    user!:any;
    cardNumber:number = 0;
    amount!: number;
    phoneNumber!: string;
    isCardPresent!:boolean;
    phoneErrorMessage: string = '';
    amountErrorMessage: string = '';

    date!:Date;
    transferInfo:string = "Поповнення мобільного: ";
    transferAmount!: number;
    atmId:number = 1;
    serviceId: number = 2;

    constructor(
      private adminService : AdminDetectionService,
      private verificationService : UserVerificationService, 
      private http:HttpClient,
      private cardService : CardDetectionService,
      private router:Router,
      private transactionService : TransactionService,
      ){}

    ngOnInit(): void {
      this.cardDetectionService = this.cardService
      this.isCardPresent = this.cardDetectionService.getCard()
  
      this.userVerificationService = this.verificationService
      this.isVerified = this.userVerificationService.getVerification();
  
      if(this.isVerified){
        this.user = this.userVerificationService.getUser();
      }
  
      this.adminDetectionService = this.adminService
      this.isATMOpened = this.adminDetectionService.getIsOpened();
  
      if(!this.isCardPresent){
        this.router.navigate(["/"]);
      }else if (!this.isVerified){
        this.router.navigate(["/verification"]);
      }
    }
  
    onConfirm(){
      if (!this.phoneNumber || !/^\d{10}$/.test(this.phoneNumber)) {
        this.phoneErrorMessage = 'Некоректний номер телефону (потрібно 10 цифр).';
        return;
      }

      this.phoneErrorMessage = '';

      if(this.amount > this.user.currentBalance){
        this.amountErrorMessage = 'На рахунку недостатньо коштів!';
      }else{
      const currentDate = new Date();
  
      const currentYear = currentDate.getFullYear();
      const currentMonth = currentDate.getMonth() + 1;
      const currentDay = currentDate.getDate();
      const currentHour = currentDate.getHours() + 2;
      const currentMinute = currentDate.getMinutes();
      const currentSecond = currentDate.getSeconds();
  
      const formattedDateTime = `${currentYear}-${currentMonth}-${currentDay} ${currentHour}:${currentMinute}:${currentSecond}`;
  
      let data = {
        date:formattedDateTime,
        transferInfo:this.transferInfo + this.phoneNumber,
        transferAmount: -Math.abs(this.amount),
        atmId:this.atmId,
        user:this.user,
        serviceId:this.serviceId
      }

      let userData = {
        cardNumber:this.user.cardNumber
      }

      this.http.post("http://localhost:8080/transactions/new", data)
      .pipe(
        timeout(500000),
        catchError((error: any) => {
          this.handleError(error);
          return throwError(error);
        })
      )
      .subscribe((val:any) => {
      this.transactionService.setTransaction(val)

        this.http.get("http://localhost:8080/transactions/user", { params: userData }).subscribe((val:any) => {
          this.userVerificationService.setUser(val)
          this.user = this.userVerificationService.getUser()
          this.router.navigate(["/transaction-info"]);
          console.log("Succsesfull");
        }, () => {
          console.log("There is error")
        });

      console.log("Succsesfull");
      
    }, () => {
      console.log("There is error")
    });
  
    this.amountErrorMessage = ';'
    }
    }

    private handleError(error: any) {
      if (error instanceof TimeoutError) {
        console.error('Request timed out.');
        this.router.navigate(['/error-page']);
      } else {
        console.error('An error occurred:', error);
      }
    }
  }
  
