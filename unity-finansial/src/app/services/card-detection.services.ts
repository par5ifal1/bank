import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";


@Injectable({
  providedIn: 'root',
})
export class CardDetectionService{
  cardNumber: number = 0;

    constructor (private http:HttpClient){
    }
  
    isCardPresent:boolean = false;
  
    getCard(){
      return this.isCardPresent;
    }

    setCard(isCardPresent:boolean){
        this.isCardPresent = isCardPresent;
    }
  
    changeState(){
      this.isCardPresent = !this.isCardPresent;
      console.log(this.isCardPresent)
    }

    setCardNumber(cardNumber: number){
      this.cardNumber = cardNumber;
    }

    getCardNumber(){
      return this.cardNumber;
    }
  }