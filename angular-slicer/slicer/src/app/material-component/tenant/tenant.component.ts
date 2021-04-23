import { DialogTenantSlaMecComponent } from '../dialog-tenant-sla-mec/dialog-tenant-sla-mec.component';
import { DialogTenantSlaCloudComponent } from '../dialog-tenant-sla-cloud/dialog-tenant-sla-cloud.component';
import { DialogTenantSlaGlobalComponent } from '../dialog-tenant-sla-global/dialog-tenant-sla-global.component';
import { Component,OnInit ,ViewChild} from '@angular/core';
import { GroupService } from '../../services/group.service';
import { TenantService } from '../../services/tenant.service';
import{TenantInfo} from './tenant-info';
import{GroupInfo} from '../group/group-info';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import { MatDialog } from '@angular/material';
import { DialogTenantComponent } from '../dialog-tenant/dialog-tenant.component';
import{TenantDataSource} from './tenant.datasource';
import { Router } from '@angular/router';
import {ConfirmDialogModel,DialogConformationComponent} from '../dialog-conformation/dialog-conformation.component';

@Component({
  selector: 'app-group',
  templateUrl: './tenant.component.html',
  styleUrls: ['./tenant.component.scss']
})
export class TenantComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<TenantInfo>;

  name: string;
  dataSource: TenantDataSource;
  adminTenantInfos: TenantInfo[] = [];
  displayedColumns = ['Username','UsedResources','action'];
  showTest: boolean;
  showReal: boolean;
  admin : string
  selectedTenant: TenantInfo[];
  selectedGroup: GroupInfo[];
  filterElement: string;
  sGroup : string;
  selected: 'admin';
  dialUsername : string;
  haveData : boolean;
  groupLogin:string;
  tenantExist:boolean;
  
  constructor(private groupService:GroupService,
              public dialog: MatDialog,
              private tenantService:TenantService,
              private router: Router){
    
  }

  ngOnInit() {
    this.groupLogin=localStorage.getItem('group')

    this.dataSource = new TenantDataSource(this.adminTenantInfos);
      
      this.sGroup="admin";
      this.filterElement=this.sGroup;
      this.getGroups();
      this.getTenants(this.filterElement); 
      this.haveData=true;

  }

  getTenants(filterElement) {
      this.tenantService.getTenantData(filterElement).subscribe((tenantInfos : TenantInfo[] )=> {
        if(tenantInfos['tenants']){
          this.tenantExist=true;
          this.selectedTenant = tenantInfos
          this.adminTenantInfos = tenantInfos['tenants'];
            this.haveData=true;
            this.dataSource = new TenantDataSource(this.adminTenantInfos);
            this.dataSource.sort = this.sort;
            this.dataSource.paginator = this.paginator;
            this.table.dataSource = this.dataSource;
        }else{
          this.tenantExist=false;
          (<HTMLInputElement>document.getElementById('tenantTable')).style.display="none";

        }

    });
     
  }


  getGroups() {
    this.groupService.getGroupData().subscribe((groupInfos : GroupInfo[] )=> {
    this.selectedGroup = groupInfos
      
  });
}
  deleteTenant(sGroup,userId){
    const message = `Are you sure you want to delete `+userId+' ?';
    const dialogData = new ConfirmDialogModel("Confirm Action", message);
    const dialogRef = this.dialog.open(DialogConformationComponent, {
      maxWidth: "420px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
    if (dialogResult === true) {
      this.tenantService.deleteTenant(sGroup,userId).subscribe((res:any)=>{
      window.location.reload();

      });
    }    
  });
  }


 showDialogAddTenant(){
  const dialogRef = this.dialog.open(DialogTenantComponent, {
    maxWidth: '400px',
    //height: '450px'
  }); 
}
showSlaMecDialog(username,sGroup){
  const dialogRef = this.dialog.open(DialogTenantSlaMecComponent, {
    maxWidth: '420px',
  //  height: '80%',
    data: {
      dialUsername: username,
      dialGroup:sGroup
    } 
  }); 
}

showSlaGlobalDialog(username,sGroup){
  const dialogRef = this.dialog.open(DialogTenantSlaGlobalComponent, {
    maxWidth: '420px',
  //  height: '75%',
    data: {
      dialUsername: username,
      dialGroup:sGroup
    }  
  }); 
}

showSlaCloudDialog(username,sGroup){
  const dialogRef = this.dialog.open(DialogTenantSlaCloudComponent, {
    maxWidth: '420px',
 //   height: '75%',
    data: {
      dialUsername: username,
      dialGroup:sGroup
    }
  }); 
}


onGroupSelection(){
  this.filterElement=this.sGroup;
  this.getTenants(this.filterElement)
}
showSla(userId){
  this.router.navigate(['/sla', userId]);
}
notShow(){
  console.log(document.getElementById("divShow").innerHTML);  
}
viewSla(sGroup,username){
  this.router.navigate(['/sla'], { queryParams: { username: username,group: sGroup} });
/*
  localStorage.setItem('slaUsername', username);
  localStorage.setItem('slaGroup', sGroup);
  this.router.navigate(["/sla"]);
  */
}
}

