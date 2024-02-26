import { Component } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import { Router, RouterLink } from '@angular/router';
import { AdminDetectionService } from '../services/admin-detection.services';
import { catchError, timeout } from 'rxjs/operators';
import { throwError, TimeoutError  } from 'rxjs';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent {
  denominations: number[] = [1, 2, 5, 10, 20, 50, 100, 200, 500, 1000];
  bills: Record<number, number> = {};
  pintMessage: string = '';
  billsDTOList: any[] = [];

  constructor(private http:HttpClient,
    private router:Router,
    private adminService : AdminDetectionService

    ){}

  ngOnInit(){
    if(!this.adminService.getConfirm()){
      this.router.navigate(["/"]);
    }
  }

  onSubmit() {
    for (const key in this.bills) {
      if (this.bills.hasOwnProperty(key) && this.bills[key] === null) {
        delete this.bills[key];
      }
    }

    for (const key in this.bills) {
      let el = {
        amount:Math.abs(this.bills[key]),
        denominations:key
      }

      this.billsDTOList.push(el);
    }

    let data = {
      billsDTOList:this.billsDTOList
    }

    this.http.post("http://localhost:8080/1/postBills", data)
    .pipe(
      timeout(500000),
      catchError((error: any) => {
        this.handleError(error);
        return throwError(error);
      })
    )
    .subscribe((val:any) => {
      console.log("Succsesfull");
      this.pintMessage = "Купюри додано до банкомату!"

      this.http.post("http://localhost:8080/1/close", {}).subscribe((val:any) => {
      }, () => {
        console.log("There is error")
      });

      this.router.navigate(["/"]);
      this.billsDTOList = [];
      this.bills = [];

  }, () => {
    console.log("There is error")
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
