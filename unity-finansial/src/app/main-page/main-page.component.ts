import { Component } from '@angular/core';
import {HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Router, RouterLink } from '@angular/router';
import { MatDialog } from '@angular/material/dialog'
import { PopUpComponent } from '../pop-up/pop-up.component';
import { CardDetectionService } from '../services/card-detection.services';
import { UserVerificationService } from '../services/user-verification.services';
import { AdminDetectionService } from '../services/admin-detection.services';
import { InsertCardPopUpComponent } from '../insert-card-pop-up/insert-card-pop-up.component';
import { WarningPopUpComponent } from '../warning-pop-up/warning-pop-up.component';
import { TransactionService } from '../services/transaction.services';
import { catchError, timeout } from 'rxjs/operators';
import { throwError, TimeoutError  } from 'rxjs';
import { environment } from 'src/environment';

@Component({
    selector: 'app-main-page',
    templateUrl: './main-page.component.html',
    styleUrls: ['./main-page.component.css']
  })
  export class MainPageComponent {
    cardDetectionService!:CardDetectionService;
    userVerificationService!:UserVerificationService;
    adminDetectionService!:AdminDetectionService;
    isATMOpened!:boolean;
    isCardPresent!:boolean;
    timerSeconds: number = 20;
    timerInterval: any;
    isVerified!:boolean;
    atm!: any;
    bills!: any;
    isEmpty!: boolean;
    isConfirmed: boolean = false;
    user!: any;

    constructor(
      private adminService : AdminDetectionService,
      private verificationService : UserVerificationService, 
      private cardService : CardDetectionService,
      private http:HttpClient,
      private router:Router, 
      private dialogRef : MatDialog,
      private transactionService :TransactionService
      ){
    }

    ngOnInit(): void {
      this.adminDetectionService = this.adminService
      this.isATMOpened = this.adminDetectionService.getIsOpened();

      this.http.get(environment.apiUrl + "/1")
      .pipe(
        timeout(500000),
        catchError((error: any) => {
          this.handleError(error);
          return throwError(error);
        })
      )
      .subscribe((val:any) => {
      this.atm = val;

      if (val.maintenanceStatus === 'UNDER_MAINTENANCE' && !this.isVerified){
        this.adminDetectionService.setIsOpened(true);
        this.router.navigate(["/verification"]);
      }else{
        this.adminDetectionService.setIsOpened(false);
      }
    
     }, (error) => {
        console.error('Request error:', error);

        if (error instanceof TimeoutError) {
          this.router.navigate(['/error-page']);
        }
      });

      this.cardDetectionService = this.cardService
      this.isCardPresent = this.cardDetectionService.getCard()

      this.userVerificationService = this.verificationService
      this.isVerified = this.userVerificationService.getVerification();

      if (this.isVerified){
        this.user = this.userVerificationService.getUser();
        this.isConfirmed = true;
      }

    this.http.get(environment.apiUrl + "/1/bills").subscribe((val:any) => {
      this.bills = val;
    }, (error) => {
      console.error('Request error:', error);

        if (error instanceof TimeoutError) {
          this.router.navigate(['/error-page']);
        }
    });

    this.openWarningDialog();
    }

    onConfirm(){
      this.verificationService.changeState();
      this.verificationService.setUser(null);
      this.transactionService.setTransaction(null);
      this.cardService.changeState();
      this.cardService.setCardNumber(0);
      location.reload();
        }
    

    openNoCardDialog(routerLink:String){
      if (!this.isCardPresent){
        this.dialogRef.open(PopUpComponent);
      }else if (this.isCardPresent && !this.isVerified){
        this.router.navigate(["/verification"]);

      }else{
        this.router.navigate([routerLink]);
      }
    }
    

    async openWarningDialog(){
      await this.delay(3000);

      this.isEmpty = true;

      for (let i = 0; i < 10; i++) {
        if (this.bills[i].amount > 0){
          this.isEmpty = false;

        }
      }


      if (this.isEmpty){
        
        this.dialogRef.open(WarningPopUpComponent, { data: { errorMessage: "У банкоматі відсутня готівка!" }});
      };
    }
    

    openInsertCardDialog(){
      if (!this.isCardPresent){
        this.dialogRef.open(InsertCardPopUpComponent);
      }else if (this.isCardPresent && !this.isVerified){
        this.router.navigate(["/verification"]);
      }
    }

    private delay(ms: number): Promise<void> {
      return new Promise(resolve => setTimeout(resolve, ms));
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
    

