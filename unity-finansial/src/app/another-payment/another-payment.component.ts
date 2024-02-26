import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {HttpClient, HttpErrorResponse } from "@angular/common/http";
import { UserVerificationService } from '../services/user-verification.services';
import { AdminDetectionService } from '../services/admin-detection.services';
import { CardDetectionService } from '../services/card-detection.services';
import { TransactionService } from '../services/transaction.services';
import { catchError, timeout } from 'rxjs/operators';
import { throwError, TimeoutError  } from 'rxjs';

@Component({
  selector: 'app-another-payment',
  templateUrl: './another-payment.component.html',
  styleUrls: ['./another-payment.component.css']
})
export class AnotherPaymentComponent {
  userVerificationService!:UserVerificationService;
  adminDetectionService!:AdminDetectionService;
  cardDetectionService!:CardDetectionService;
  isATMOpened!:boolean;
  isVerified!:boolean;
  user!:any;
  isCardPresent!:boolean;
  page!: any;
  serviceNumber!: String;
  amount!: number;
  amountErrorMessage: string = '';

  date!:Date;
  transferInfo!:string;
  transferAmount!: number;
  atmId:number = 1;
  serviceId!: number;

constructor(
  private http:HttpClient,
  private router:Router,
  private adminService : AdminDetectionService,
  private verificationService : UserVerificationService, 
  private cardService : CardDetectionService,
  private transactionService : TransactionService
  ){
}

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
  

  this.http.get("http://localhost:8080/services/all")
  .pipe(
    timeout(500000),
    catchError((error: any) => {
      this.handleError(error);
      return throwError(error);
    })
  )
  .subscribe((val:any) => {
    const newData = val.map((item: any) => {
      const serviceNumber = "";
      const amount = null;
      const amountErrorMessage = "";
    
      return {
        ...item,
        serviceNumber,
        amount
      };
    });

    this.page = newData;
  }, () => {
    console.log("There is error")
  });
}

onConfirm(item:any){
  if(item.amount > this.user.currentBalance){
    item.amountErrorMessage = 'На рахунку недостатньо коштів!';
  }else if (item.amount == null)
  {
    item.amountErrorMessage = 'Сума оплати не введена!';
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
    transferInfo:item.name + ": " + item.serviceNumber,
    transferAmount: -Math.abs(item.amount),
    atmId:this.atmId,
    user:this.user,
    serviceId:item.id
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

item.amountErrorMessage = ''
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