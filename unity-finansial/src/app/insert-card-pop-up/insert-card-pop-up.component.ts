import { Component, Inject, OnInit } from '@angular/core';
import {MAT_DIALOG_DATA } from '@angular/material/dialog'
import {HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Router, RouterLink } from '@angular/router';
import { MatDialog } from '@angular/material/dialog'
import { CardDetectionService } from '../services/card-detection.services';
import { catchError, timeout } from 'rxjs/operators';
import { throwError, TimeoutError  } from 'rxjs';

@Component({
  selector: 'app-insert-card-pop-up',
  templateUrl: './insert-card-pop-up.component.html',
  styleUrls: ['./insert-card-pop-up.component.css']
})
export class InsertCardPopUpComponent {
    cardDetectionService!:CardDetectionService;
    cardNumber!: number;
    cardDate!: number;
    cardCVV!: number;
    month!: number;
    year!: number;

  constructor(
    private http:HttpClient,
    private router:Router,
    private dialogRef : MatDialog,
    private cardService : CardDetectionService
    ){}

    onConfirm(){
        if(this.validateCard()){
            let data = {
                cardNumber:this.cardNumber,
                cardDate:this.month + this.year,
                cardCVV:this.cardCVV
            }
            this.http.get("http://localhost:8080/user",  { params: data })
            .subscribe((val:any) => {
                if(val[0] && !val[1]){

                    this.addCard();
                    this.router.navigate(["/verification"]);
                    this.dialogRef.closeAll();
                }else{
                    this.printError("Картку заблоковано, зверніться до банку!", false)
                }
            }, (val:any) => {
                    this.printError("Дані картки введено неправильно!", false)
                    console.log("There is error")
            });
        }
    }

    validateCard(): boolean {
      const cardNumberInput = document.getElementById('cardNumber') as HTMLInputElement;
      const cardNumber = cardNumberInput.value.replace(/ /g, ''); // Remove spaces
      const isValid = this.luhnAlgorithm(cardNumber);

      this.printError("Введено неправильний номер картки!", isValid);
  
      return isValid;
  }

  addCard(){
    this.cardDetectionService = this.cardService
    this.cardDetectionService.setCardNumber(this.cardNumber);
    this.cardDetectionService.setCard(true);
  }

  printError(errorMessageTextContent: string, val: boolean){
    const cardNumberInput = document.getElementById('cardNumber') as HTMLInputElement;
    const errorMessage = document.createElement('div');
    errorMessage.className = 'error-message';
    errorMessage.style.color = "#FF0000";

    if (!val) {
        errorMessage.textContent = errorMessageTextContent;
        const existingErrorMessage = document.querySelector('.error-message');

        if(existingErrorMessage){
            existingErrorMessage.remove();
            cardNumberInput.parentElement?.appendChild(errorMessage);
        }

        if (!existingErrorMessage) {
        cardNumberInput.parentElement?.appendChild(errorMessage);
        }
    } else {
        const existingErrorMessage = document.querySelector('.error-message');
        if (existingErrorMessage) {
            existingErrorMessage.remove();
        }  
    }
}
  
  luhnAlgorithm(cardNumber: string): boolean {
      const reversedCardNumber: number[] = cardNumber.split('').reverse().map(Number);
      let sum = 0;
      if (reversedCardNumber.length > 16){
        return false;
      }
      
      for (let i = 0; i < reversedCardNumber.length; i++) {
          let digit = reversedCardNumber[i];
  
          if (i % 2 !== 0) {
              digit *= 2;
              if (digit > 9) {
                  digit -= 9;
              }
          }
  
          sum += digit;
      }
  
      return sum % 10 === 0;
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
