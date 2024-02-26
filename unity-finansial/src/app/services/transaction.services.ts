import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";


@Injectable({
  providedIn: 'root',
})
export class TransactionService{

    constructor (private http:HttpClient){
    }
  
    transaction!:any;
  
    getTransaction(){
      return this.transaction;
    }
  
    setTransaction(transaction:any){
      this.transaction = transaction
    }
  }