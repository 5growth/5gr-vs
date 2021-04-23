import { Component, OnInit,ViewChild } from '@angular/core';
import { VsDescriptorsService } from '../../services/vs-descriptors.service';
import {VsDescriptorInfo} from './vs-descriptor-info'
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import{VsDescriptorDataSource} from './vs-descriptor-datasource';
import {MatDialog} from '@angular/material/dialog';
import {ConfirmDialogModel,DialogConformationComponent} from '../dialog-conformation/dialog-conformation.component';

@Component({
  selector: 'app-vs-descriptor',
  templateUrl: './vs-descriptor.component.html',
  styleUrls: ['./vs-descriptor.component.scss']
})
export class VsDescriptorComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<VsDescriptorInfo>;
  name: string;
  dataSource: VsDescriptorDataSource;
  vsDescriptorInfos: VsDescriptorInfo[] = [];
  vsdExist:boolean;
  displayedColumns = ['id','name','version','vsb', 'qos', 'action'];
  constructor(private vsDescriptorsService:VsDescriptorsService,public dialog: MatDialog) { }
  groupLogin:string;
  ngOnInit(): void {
    this.vsDescriptorsService.getVsDescriptorsData().subscribe((vsDescriptorInfos : VsDescriptorInfo[] )=> {
      if(vsDescriptorInfos.length!=0){
          this.vsdExist=true;
          this.vsDescriptorInfos = vsDescriptorInfos;
          this.dataSource = new VsDescriptorDataSource(this.vsDescriptorInfos);
          this.dataSource.sort = this.sort;
          this.dataSource.paginator = this.paginator;
          this.table.dataSource = this.dataSource;
      }else{
        this.vsdExist=false;
        (<HTMLInputElement>document.getElementById('vsdTable')).style.display="none";

      }
    });    
    this.groupLogin=localStorage.getItem('group');
  }

  deleteVsDesc(vsDescId: string,vsDname: string){
    const message = `Are you sure you want to delete `+vsDname+' ?';
    const dialogData = new ConfirmDialogModel("Confirm Action", message);
    const dialogRef = this.dialog.open(DialogConformationComponent, {
      maxWidth: "420px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
    if (dialogResult === true) {
      this.vsDescriptorsService.deleteVsDescriptorsData(vsDescId).subscribe((res:any)=>{
      window.location.reload();

      });
    }    
  });

}
}