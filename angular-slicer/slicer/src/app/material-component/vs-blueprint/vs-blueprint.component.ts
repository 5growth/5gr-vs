import { Component, OnInit,ViewChild } from '@angular/core';
import { VsBlueprintsService } from '../../services/vs-Blueprints.service';
import {VsBlueprintInfo} from './vs-blueprint-info'
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import{VsBlueprintDataSource} from './vs-blueprint-datasource';
import { VsBlueprintDetailsService } from '../../services/vs-blueprint-details.service';
import { Router } from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {ConfirmDialogModel,DialogConformationComponent} from '../dialog-conformation/dialog-conformation.component';

@Component({
  selector: 'app-blueprint',
  templateUrl: './vs-blueprint.component.html',
  styleUrls: ['./vs-blueprint.component.scss']
})
export class VsBlueprintComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<VsBlueprintInfo>;
  name: string;
  dataSource: VsBlueprintDataSource;
  vsBlueprintInfos: VsBlueprintInfo[] = [];
  groupLogin:string;
  vsbExist:boolean;
  displayedColumns = ['id','name','version','description','vsds','details','action'];
  constructor(private vsBlueprintsService:VsBlueprintsService,
    private vsbDetailsService: VsBlueprintDetailsService,private router: Router,public dialog: MatDialog) { }

  ngOnInit(): void {
    this.vsBlueprintsService.getVsBlueprintsData().subscribe((vsBlueprintInfos : VsBlueprintInfo[] )=> {
      if(vsBlueprintInfos.length!=0){
        this.vsbExist=true;
        this.vsBlueprintInfos = vsBlueprintInfos;
        this.dataSource = new VsBlueprintDataSource(this.vsBlueprintInfos);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
        this.table.dataSource = this.dataSource;
      }else{
        this.vsbExist=false;
        (<HTMLInputElement>document.getElementById('vsbTable')).style.display="none";

      }

    });    
    this.groupLogin=localStorage.getItem('group');

  }
deleteVsBlueprint(vsBlueprintId,vsBlueprintName){
    const message = `Are you sure you want to delete `+vsBlueprintName+' ?';
    const dialogData = new ConfirmDialogModel("Confirm Action", message);
    const dialogRef = this.dialog.open(DialogConformationComponent, {
      maxWidth: "420px",
      data: dialogData
    });
    dialogRef.afterClosed().subscribe(dialogResult => {
      
    if (dialogResult === true) {
      this.vsBlueprintsService.deleteVsBlueprintsData(vsBlueprintId).subscribe((res:any)=>{

        console.log("res",res)
        if(res!==undefined){
             window.location.reload();
        }
      });
     
    }     
  });
}


viewVsBlueprintGraph(vsBlueprintId: string) {
  //console.log(vsBlueprintId);
  this.vsbDetailsService.updateVSBId(vsBlueprintId);

  //routerLink="/blueprints_vs_graph"
  this.router.navigate(["/vsblueprint-details"]);
}
}



