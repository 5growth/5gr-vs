import { Component,OnInit ,ViewChild} from '@angular/core';
import { GroupService } from '../../services/group.service';
import{GroupInfo} from './group-info';
import{GroupDataSource} from './group-datasource';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import { DialogGroupComponent } from '../dialog-group/dialog-group.component';
import {MatDialog} from '@angular/material/dialog';
import {ConfirmDialogModel,DialogConformationComponent} from '../dialog-conformation/dialog-conformation.component';


@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.scss']
})
export class GroupComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<GroupInfo>;
  name: string;
  dataSource: GroupDataSource;
  groupInfos: GroupInfo[] = [];
  displayedColumns = ['name','tenants','action'];

  constructor(private groupService:GroupService,public dialog: MatDialog){}

  ngOnInit() {
      this.dataSource = new GroupDataSource(this.groupInfos);
      this.getGroup(); 
  }

  getGroup() {
      this.groupService.getGroupData().subscribe((groupInfos : GroupInfo[] )=> {
      this.groupInfos = groupInfos;
      this.dataSource = new GroupDataSource(this.groupInfos);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
      this.table.dataSource = this.dataSource;
   
    });
  }
  deleteGroup(groupId: string){
    const message = `Are you sure you want to delete `+groupId+' ?';
    const dialogData = new ConfirmDialogModel("Confirm Action", message);
    const dialogRef = this.dialog.open(DialogConformationComponent, {
      maxWidth: "420px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
    if (dialogResult === true) {
      this.groupService.deleteGroup(groupId).subscribe((res:any)=>{
      window.location.reload();

      });
    }    
  });
  }


 showDialog(){
  const dialogRef = this.dialog.open(DialogGroupComponent, {
    maxWidth: "420px"
  //  height: '25%'
  }); 
}
panelOpenState = false;
  step = 0;

  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }
}

