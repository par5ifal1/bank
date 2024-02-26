import { Component } from '@angular/core';
import { TransactionService } from '../services/transaction.services';
import { UserVerificationService } from '../services/user-verification.services';
import { CardDetectionService } from '../services/card-detection.services';
import { Router } from '@angular/router';



@Component({
  selector: 'app-transaction-info',
  templateUrl: './transaction-info.component.html',
  styleUrls: ['./transaction-info.component.css']
})
export class TransactionInfoComponent {
  transaction!:any;
  user!:any;

constructor(
  private transactionService : TransactionService,
  private verificationService : UserVerificationService, 
  private cardService : CardDetectionService,
  private router:Router
){}

ngOnInit(): void {
  this.transaction = this.transactionService.getTransaction();
  this.user = this.verificationService.getUser();

  if(!this.cardService.getCard()){
    this.router.navigate(["/"]);
  }
}

onConfirm(){
  this.verificationService.changeState();
  this.verificationService.setUser(null);
  this.transactionService.setTransaction(null);
  this.cardService.changeState();
  this.cardService.setCardNumber(0);
  this.router.navigate(["/"]);
}
}
