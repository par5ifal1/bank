import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";


@Injectable({
  providedIn: 'root',
})
export class UserVerificationService{

    constructor (private http:HttpClient){
    }
  
    user!:any;
    isVerified:boolean = false;
  
    getVerification(){
      return this.isVerified;
    }

    setVeriefication(isVerified:boolean){
        this.isVerified = isVerified;
    }

    getUser(){
      return this.user;
    }

    setUser(user: any){
      this.user = user;
    }
  
    changeState(){
      this.isVerified = !this.isVerified;
      console.log(this.isVerified)
    }
  }