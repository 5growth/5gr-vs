import { Component, OnInit,ViewChild } from '@angular/core';
import { TenantService } from '../../services/tenant.service';
import { SlaInfo } from './sla-info';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import{ SlaDataSource } from './sla-datasource';
import { ActivatedRoute } from "@angular/router";
import {ConfirmDialogModel,DialogConformationComponent} from '../dialog-conformation/dialog-conformation.component';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-sla',
  templateUrl: './sla.component.html',
  styleUrls: ['./sla.component.scss']
})
export class SlaComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<SlaInfo>;
  name: string;
  dataSource: SlaDataSource;
  slaInfos: SlaInfo[] = [];
  displayedColumns = ['Id','Scope','MaxRAM','MaxVCPUs','MaxVStorage','Location','Status','action'];
  constructor(private tenantService:TenantService,private route: ActivatedRoute,public dialog: MatDialog) { }
  groupId : string;
  userName : string;
  ngOnInit(): void {
    this.route.queryParams
    .subscribe(params => {
    this.userName=params['username'];
    this.groupId=params['group'];
    }
    );
    this.getTenantSLAs(this.groupId,this.userName);
  }
  
  getTenantSLAs(groupId,userId){
    this.tenantService.getTenantSLAsData(groupId,userId).subscribe((slaInfos : SlaInfo[])=> {
    this.slaInfos = slaInfos;
    this.dataSource = new SlaDataSource(this.slaInfos);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  });
}

deleteSla(slaId){
  const message = `Are you sure you want to delete ?`;
  const dialogData = new ConfirmDialogModel("Confirm Action", message);
  const dialogRef = this.dialog.open(DialogConformationComponent, {
    maxWidth: "420px",
    data: dialogData
  });
  dialogRef.afterClosed().subscribe(dialogResult => {
  if (dialogResult === true) {
    this.tenantService.deleteSLAs(this.groupId,this.userName,slaId).subscribe((res:any)=>{
    window.location.reload();
    });
  }    
});
}
}
