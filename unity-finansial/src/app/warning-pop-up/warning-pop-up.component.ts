import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog'
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';


@Component({
  selector: 'app-warning-pop-up',
  templateUrl: './warning-pop-up.component.html',
  styleUrls: ['./warning-pop-up.component.css']
})
export class WarningPopUpComponent implements OnInit {
  errorMessage!: string;

  constructor(private dialogRef : MatDialog,
     @Inject(MAT_DIALOG_DATA) public data: any
  ) {}

  ngOnInit(): void {
    if (this.data && this.data.errorMessage) {
      this.errorMessage = this.data.errorMessage;
    }
  }

  openWarningDialog(errorMessage: string){
    this.errorMessage = errorMessage;
    this.dialogRef.closeAll();
    this.dialogRef.open(WarningPopUpComponent);
  }
}
