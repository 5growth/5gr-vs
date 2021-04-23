import { Component, OnInit ,Inject} from '@angular/core';
import { MatDialogRef ,MAT_DIALOG_DATA} from '@angular/material';
import { TenantService } from '../../services/tenant.service';

@Component({
  selector: 'app-dialog-tenant-sla-cloud',
  templateUrl: './dialog-tenant-sla-cloud.component.html',
  styleUrls: ['./dialog-tenant-sla-cloud.component.scss']
})
export class DialogTenantSlaCloudComponent implements OnInit {
  constructor(public dialogRef: MatDialogRef<DialogTenantSlaCloudComponent>,
    private tenantService:TenantService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }
  selectedAdmin : string;
  selected: 'admin';
  tenant =this.data['dialUsername'];
  ngOnInit() {
  }
  addCloudSla(slaStatus,location,memoryRAM,vCPU,diskStorage){
    var cloudSlatRequest = JSON.parse('{}');
    var status =(<HTMLInputElement>document.getElementById(slaStatus)).value;
    var loc =(<HTMLInputElement>document.getElementById(location)).value;
    var dStorage =(<HTMLInputElement>document.getElementById(diskStorage)).value;
    var mRAM =(<HTMLInputElement>document.getElementById(memoryRAM)).value;
    var vcPU =(<HTMLInputElement>document.getElementById(vCPU)).value;
    cloudSlatRequest['tenant']=this.data['dialUsername'];
    cloudSlatRequest['slaStatus']=status;
    cloudSlatRequest['slaConstraints']=[];
    cloudSlatRequest['slaConstraints'].push({'scope':'CLOUD_RESOURCE','location':loc,'maxResourceLimit':{'memoryRAM':mRAM,'vCPU':vcPU,'diskStorage':dStorage}})
    this.tenantService.postSlaData(cloudSlatRequest,this.data['dialGroup'],this.data['dialUsername']).subscribe(respId => console.log("cloud reponse id : " + respId))
  }
}
