import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { PhonePaymentComponent } from './phone-payment/phone-payment.component';
import { MainPageComponent } from './main-page/main-page.component';
import { PopUpComponent } from './pop-up/pop-up.component';
import { FundsWithdrawalComponent } from './funds-withdrawal/funds-withdrawal.component';
import { AnotherPaymentComponent } from './another-payment/another-payment.component';
import { VerificationComponent } from './verification/verification.component';
import { InsertCardPopUpComponent } from './insert-card-pop-up/insert-card-pop-up.component';
import { WarningPopUpComponent } from './warning-pop-up/warning-pop-up.component';
import { TransactionInfoComponent } from './transaction-info/transaction-info.component';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { ErrorPageComponent } from './error-page/error-page.component';

@NgModule({
  declarations: [
    AppComponent,
    PhonePaymentComponent,
    MainPageComponent,
    PopUpComponent,
    FundsWithdrawalComponent,
    AnotherPaymentComponent,
    VerificationComponent,
    InsertCardPopUpComponent,
    WarningPopUpComponent,
    TransactionInfoComponent,
    AdminPageComponent,
    ErrorPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatDialogModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
