import { DomainsService } from './../../services/domains.service';
import { Component, OnInit,ViewChild } from '@angular/core';
import {DomainsInfo} from './domains-info'
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import{DomainsDataSource} from './domains-datasource';
import { Router } from '@angular/router';
@Component({
  selector: 'app-domains',
  templateUrl: './domains.component.html',
  styleUrls: ['./domains.component.scss']
})
export class DomainsComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<DomainsInfo>;
  name: string;
  dataSource: DomainsDataSource;
  domainsInfos: DomainsInfo[] = [];
  domainExist:boolean;
  displayedColumns = ['name','description','owner','admin','domainStatus','details','action'];
  constructor(private domainsService:DomainsService,private router: Router) { }

  ngOnInit(): void {
    this.domainsService.getDomainsData().subscribe((domainsInfos : DomainsInfo[] )=> {
    
      if(domainsInfos.length!=0){
        this.domainExist=true;
        this.domainsInfos = domainsInfos;
        this.dataSource = new DomainsDataSource(this.domainsInfos);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
        this.table.dataSource = this.dataSource;
      }else{
        this.domainExist=false;
      }
});
  
}

viewDomainDetails(domainId){
  localStorage.setItem('domainId', domainId);
  this.router.navigate(["/domains-details"]);

}

deleteDomain(domainId){
  this.domainsService.deleteDomain(domainId).subscribe();
  alert(domainId+" is deleted");
  window.location.reload();

}
}
