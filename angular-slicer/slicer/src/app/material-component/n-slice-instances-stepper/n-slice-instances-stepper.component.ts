
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NslicesService } from '../../services/n-slice-template.service';
import { NsliceInstancesService } from '../../services/n-slice-instances.service';
import { NslicesInfo } from '../n-slice-template/n-slice-template-info';

@Component({
  selector: 'app-n-slice-instances-stepper',
  templateUrl: './n-slice-instances-stepper.component.html',
  styleUrls: ['./n-slice-instances-stepper.component.scss']
})
export class NSliceInstancesStepperComponent implements OnInit {

  isLinear = true;
  nslicesInfos: NslicesInfo[] = [];  
  selected: string;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  nsiId:String;
  constructor(private router: Router, 
    private _formBuilder: FormBuilder,
    private nslicesService:NslicesService,
    private nsliceInstancesService:NsliceInstancesService,
    ) {
  }

  ngOnInit() {
    this.selected='Nst Id';
    this.getNslices();
    this.firstFormGroup = this._formBuilder.group({
      nstId: [''],
      name: [''],
      description:[''],
    });

    this.secondFormGroup = this._formBuilder.group({
      nsiId: ['', Validators.required],
      dfId: ['', Validators.required],
      instantiationLevelId: ['', Validators.required],
      latitude: ['', Validators.required],
      longitude: ['', Validators.required],
      altitude: ['', Validators.required],
      range: ['', Validators.required],
      ranEndPointId: ['', Validators.required]
     });

  
  }
  getNslices() {
    this.nslicesService.getNslicesData().subscribe((nslicesInfos : NslicesInfo[] )=> {
    this.nslicesInfos = nslicesInfos;
  });
} 

createNsi(){
  var onBoardNsiRequest = JSON.parse('{}');
  onBoardNsiRequest['nstUuid']=this.firstFormGroup.get('nstId').value;
  onBoardNsiRequest['name']=this.firstFormGroup.get('name').value;
  onBoardNsiRequest['description']=this.firstFormGroup.get('description').value;
  this.nsliceInstancesService.postNsliceInstancesData(onBoardNsiRequest)
  .subscribe(nsiId => {
    this.nsiId=nsiId;
  });
}

createNsiWithDetails(){
  var onBoardNsidRequest = JSON.parse('{}');
  onBoardNsidRequest['locationConstraints'] = JSON.parse('{}');
  onBoardNsidRequest['nstId']=this.firstFormGroup.get('nstId').value;
  onBoardNsidRequest['nsiId']=this.nsiId;
  onBoardNsidRequest['dfId']=this.secondFormGroup.get('dfId').value;
  onBoardNsidRequest['ilId']=this.secondFormGroup.get('instantiationLevelId').value;
  onBoardNsidRequest['locationConstraints']['latitude']=this.secondFormGroup.get('latitude').value;
  onBoardNsidRequest['locationConstraints']['longitude']=this.secondFormGroup.get('longitude').value;
  onBoardNsidRequest['locationConstraints']['altitude']=this.secondFormGroup.get('altitude').value;
  onBoardNsidRequest['locationConstraints']['range']=this.secondFormGroup.get('range').value;
  onBoardNsidRequest['ranEndPointId']=this.secondFormGroup.get('ranEndPointId').value;
  this.nsliceInstancesService.postNsliceInstancesInstantiateData(onBoardNsidRequest,this.nsiId)
  .subscribe(_ => {
    this.router.navigate(['/ns-instances']);
  });
  
}

}