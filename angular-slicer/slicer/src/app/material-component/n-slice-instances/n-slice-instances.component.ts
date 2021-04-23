import { Component, OnInit,ViewChild } from '@angular/core';
import { NsliceInstancesService } from '../../services/n-slice-instances.service';
import { NsliceInstancesInfo } from './n-slice-instances-info';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import{NsliceInstancesDataSource} from './n-slice-instances-datasource';
import {MatDialog} from '@angular/material/dialog';
import {ConfirmDialogModel,DialogConformationComponent} from '../dialog-conformation/dialog-conformation.component';

@Component({
  selector: 'app-n-slice-instances',
  templateUrl: './n-slice-instances.component.html',
  styleUrls: ['./n-slice-instances.component.scss']
})
export class NSliceInstancesComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<NsliceInstancesInfo>;
  name: string;
  nsiExist:boolean;
  dataSource: NsliceInstancesDataSource;
  nslicesInfos: NsliceInstancesInfo[] = [];
  displayedColumns = ['id','name','description','status','nssis','action'];
  constructor(private nsliceInstancesService:NsliceInstancesService,public dialog: MatDialog) {  }

  ngOnInit(): void {
  
    this.nsliceInstancesService.getNsliceInstancesData().subscribe((nsliceInstancesInfos : NsliceInstancesInfo[] )=> {
      if(nsliceInstancesInfos.length!=0){
        this.nsiExist=true;
        this.nslicesInfos = nsliceInstancesInfos;
        this.dataSource = new NsliceInstancesDataSource(this.nslicesInfos);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
        this.table.dataSource = this.dataSource;
     
      }else{
        this.nsiExist=false;
        (<HTMLInputElement>document.getElementById('nsiTable')).style.display="none";

      }
  });  
}
  
deleteNsliceInstances(nsiId,nsiName){

  const message = `Are you sure you want to delete `+nsiName+' ?';
  const dialogData = new ConfirmDialogModel("Confirm Action", message);
  const dialogRef = this.dialog.open(DialogConformationComponent, {
    maxWidth: "420px",
    data: dialogData
  });
  dialogRef.afterClosed().subscribe(dialogResult => {
  if (dialogResult === true) {
    this.nsliceInstancesService.deleteNsliceInstancesData(nsiId).subscribe((res:any)=>{
    window.location.reload();

    });
  }    
});
}
}
