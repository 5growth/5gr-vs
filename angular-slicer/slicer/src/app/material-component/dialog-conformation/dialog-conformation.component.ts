import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Component, OnInit, Inject } from '@angular/core';

@Component({
  selector: 'app-dialog-conformation',
  templateUrl: './dialog-conformation.component.html',
  styleUrls: ['./dialog-conformation.component.scss']
})
export class DialogConformationComponent implements OnInit {
  title: string;
  message: string;

  constructor(public dialogRef: MatDialogRef<DialogConformationComponent>,
    @Inject(MAT_DIALOG_DATA) public data: ConfirmDialogModel) {
    // Update view with given values
    this.title = data.title;
    this.message = data.message;
  }

  ngOnInit() {
  }

  onConfirm(): void {
    this.dialogRef.close(true);
  }

  onDismiss(): void {
    this.dialogRef.close(false);
  }
}

export class ConfirmDialogModel {
  constructor(public title: string, public message: string) {
  }
}
