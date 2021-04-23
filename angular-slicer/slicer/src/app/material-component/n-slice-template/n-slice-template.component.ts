import { Component, OnInit,ViewChild } from '@angular/core';
import { NslicesService } from '../../services/n-slice-template.service';
import { NslicesInfo } from './n-slice-template-info';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import{NslicesDataSource} from './n-slice-template-datasource';
import {MatDialog} from '@angular/material/dialog';
import {ConfirmDialogModel,DialogConformationComponent} from '../dialog-conformation/dialog-conformation.component';

@Component({
  selector: 'app-n-slice-template',
  templateUrl: './n-slice-template.component.html',
  styleUrls: ['./n-slice-template.component.scss']
})
export class NSliceTemplateComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<NslicesInfo>;
  name: string;
  dataSource: NslicesDataSource;
  nslicesInfos: NslicesInfo[] = [];
  nstExist:boolean;
  displayedColumns = ['name','version','provider','nssts','action'];
  constructor(private nslicesService:NslicesService,public dialog: MatDialog) {  }

  ngOnInit(): void {
   
    this.nslicesService.getNslicesData().subscribe((nslicesInfos : NslicesInfo[] )=> {
      if(nslicesInfos.length!=0){
        this.nstExist=true;
        this.nslicesInfos = nslicesInfos;
        this.dataSource = new NslicesDataSource(this.nslicesInfos);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
        this.table.dataSource = this.dataSource;
      }else{
        this.nstExist=false;    
        (<HTMLInputElement>document.getElementById('nstTable')).style.display="none";
   
      }
  });  }

deleteNslices(nstId,nstName){
  const message = `Are you sure you want to delete `+nstName+' ?';
  const dialogData = new ConfirmDialogModel("Confirm Action", message);
  const dialogRef = this.dialog.open(DialogConformationComponent, {
    maxWidth: "420px",
    data: dialogData
  });
  dialogRef.afterClosed().subscribe(dialogResult => {
  if (dialogResult === true) {
  this.nslicesService.deleteNslicesData(nstId).subscribe((res:any)=>{
    window.location.reload();

    });
  }    
});
}
}