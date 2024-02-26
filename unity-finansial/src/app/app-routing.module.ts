import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { MainPageComponent } from './main-page/main-page.component';
import { PhonePaymentComponent } from './phone-payment/phone-payment.component';
import { FundsWithdrawalComponent } from './funds-withdrawal/funds-withdrawal.component';
import { AnotherPaymentComponent } from './another-payment/another-payment.component'
import { VerificationComponent } from './verification/verification.component';
import { TransactionInfoComponent } from './transaction-info/transaction-info.component';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { ErrorPageComponent } from './error-page/error-page.component';

const routes: Routes = [
  {path: "", component:MainPageComponent},
  {path: "phone-payment", component:PhonePaymentComponent},
  {path: "withdraw-funds", component:FundsWithdrawalComponent},
  {path: "another-payment", component:AnotherPaymentComponent},
  {path: "verification", component:VerificationComponent},
  {path: "transaction-info", component:TransactionInfoComponent},
  {path: "admin-page" , component:AdminPageComponent},
  {path: "error-page" , component:ErrorPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
