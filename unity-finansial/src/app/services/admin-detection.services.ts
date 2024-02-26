import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";


@Injectable({
  providedIn: 'root',
})
export class AdminDetectionService{

    constructor (private http:HttpClient){
    }
  
    isATMOpened:boolean = false;
    isConfirmed:boolean = false;
  
    getIsOpened(){
      return this.isATMOpened;
    }

    setIsOpened(isATMOpened:boolean){
        this.isATMOpened = isATMOpened;
    }

    setConfirm(isConfirmed:boolean){
      this.isConfirmed = isConfirmed;
    }
  
    getConfirm(){
      return this.isConfirmed;
    }

    changeState(){
      this.isATMOpened = !this.isATMOpened;
      console.log(this.isATMOpened)
    }
  }