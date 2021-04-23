import { Component, OnInit ,ViewChild } from '@angular/core';
import {VsInstancesService} from '../../services/vs-instances.service';
import {VsInstanceInfo} from './vs-instance-info'
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import {MatTableDataSource} from '@angular/material';
import { Router } from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {ConfirmDialogModel,DialogConformationComponent} from '../dialog-conformation/dialog-conformation.component';

@Component({
  selector: 'app-vs-instance',
  templateUrl: './vs-instance.component.html',
  styleUrls: ['./vs-instance.component.scss'],
  
})
export class VsInstanceComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<VsInstanceInfo>;

  constructor(private vsInstancesService:VsInstancesService,private router: Router,public dialog: MatDialog) { }
  dataSource;
  vsiExist:boolean;
  vsInstanceInfos: VsInstanceInfo[] = [];
  
  displayedColumns = ['id','name','description','status','action'];

  groupLogin:string;
  ngOnInit(): void {
    let vsElements=[];
    this.vsInstancesService.getVsInstancesData().subscribe((vsInstancesInfos)=> {
      if(vsInstancesInfos.length!=0){
        this.vsiExist=true;
        for(var i=0;i<Object.keys(vsInstancesInfos).length;i++){
          this.vsInstancesService.getVsInstanceByIdData(vsInstancesInfos[i]).subscribe((vsInstanceInfos : VsInstanceInfo[] )=> {
            vsElements.push(     
               { 
                  vsiId:vsInstanceInfos['vsiId'],
                  name:vsInstanceInfos['name'],
                  description:vsInstanceInfos['description'],
                  vsdId:vsInstancesInfos['vsdId'],
                  status:vsInstanceInfos['status']
            });
          this.dataSource = new MatTableDataSource(vsElements);
          });
      }
    }else{
      this.vsiExist=false;
      (<HTMLInputElement>document.getElementById('vsiTable')).style.display="none";


    }
  });
    this.groupLogin=localStorage.getItem('group');

  }

  viewVsiDetails(vsiId){
    localStorage.setItem('vsiId', vsiId);
    this.router.navigate(["/vsi-details"]);
  
  }
  deleteVsi(vsiId,vsdName){
    const message = `Are you sure you want to delete `+vsdName+' ?';
    const dialogData = new ConfirmDialogModel("Confirm Action", message);
    const dialogRef = this.dialog.open(DialogConformationComponent, {
      maxWidth: "420px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
    if (dialogResult === true) {
      this.vsInstancesService.deleteVsInstance(vsiId).subscribe((res:any)=>{
        if(res!==undefined){
          window.location.reload();
     }  
      });
    }    
  });
  }  
  
  terminateVsi(vsiId,vsdName){
    const message = `Are you sure you want to terminate `+vsdName+' ?';
    const dialogData = new ConfirmDialogModel("Confirm Action", message);
    const dialogRef = this.dialog.open(DialogConformationComponent, {
      maxWidth: "420px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
    if (dialogResult === true) {
      this.vsInstancesService.terminateVsInstance(vsiId).subscribe((res:any)=>{
        if(res!==undefined){
          window.location.reload();
     }  
      });
    }    
  });
  }

}
