import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog'
import { InsertCardPopUpComponent } from '../insert-card-pop-up/insert-card-pop-up.component';


@Component({
  selector: 'app-pop-up',
  templateUrl: './pop-up.component.html',
  styleUrls: ['./pop-up.component.css']
})
export class PopUpComponent implements OnInit {
  timerSeconds: number = 20;
  timerInterval: any;

  constructor(private dialogRef : MatDialog) {}

  startTimer() {
    this.timerInterval = setInterval(() => {
      this.timerSeconds--;

      if (this.timerSeconds < 0) {
        this.dialogRef.closeAll()
        clearInterval(this.timerInterval);
        this.timerSeconds = 20;
      }
    }, 1000);
  }

  ngOnInit(): void {
    this.startTimer();
  }

  openInsertCardDialog(){
      this.dialogRef.closeAll();
      this.dialogRef.open(InsertCardPopUpComponent);
  }
}
