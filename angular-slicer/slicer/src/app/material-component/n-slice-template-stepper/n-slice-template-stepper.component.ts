
import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators,FormArray } from '@angular/forms';
import { DOCUMENT } from '@angular/common';
import { NslicesService } from '../../services/n-slice-template.service';
import { environment } from '../../environments/environments';
import { Router } from '@angular/router';

@Component({
  selector: 'app-n-slice-template-stepper',
  templateUrl: './n-slice-template-stepper.component.html',
  styleUrls: ['./n-slice-template-stepper.component.scss']
})
export class NSliceTemplateStepperComponent implements OnInit {

  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  fourthFormGroup: FormGroup;
  fifthFormGroup: FormGroup;
  nsdIdentifier:String;
  vnfObj:Object;
  nstObj: Object;
  nsdObj: Object;
  confObj:object;
  showNsd:boolean;
  nsdInfoArr=[];
  nsmfMode:boolean;
  vsmfMode:boolean;
  isLinear = true;
  vnfItems:FormArray;
  pndfIds=[];
  pndfObjArr=[];
  pndfObj:object;
  confObjArr=[];
  confNames=[];
  nsdObjArray=[];
  nsdIds=[];
  constructor(@Inject(DOCUMENT) private document,
    private _formBuilder: FormBuilder,
    private nslicesService:NslicesService, private router: Router)
     { }

  ngOnInit() {
    if(environment.deploymentType=='VSMF/NSMF' || 
        environment.deploymentType=='NSMF'
        ){
          this.showNsd=true;
    }
    if(environment.deploymentType=='NSMF'){
      this.nsmfMode=true
    }
    if(environment.deploymentType=='VSMF'){
      this.vsmfMode=true
    }  
   this.firstFormGroup = this._formBuilder.group({
    firstCtrl: ['', Validators.required],

  });
  this.secondFormGroup = this._formBuilder.group({
    nsd: ['', Validators.required],

  });

  this.thirdFormGroup = this._formBuilder.group({
    vnfItems: this._formBuilder.array([this.addVnf()])
  });

  this.fourthFormGroup = this._formBuilder.group({
    pnfd: ['', Validators.required],
  });

  this.fifthFormGroup = this._formBuilder.group({
    configurationRules: ['', Validators.required],
  });
  
  }
  onUploadedVnfP(event: any, vnfp: File[]) {
    let promises = [];
  
    for (let vnf of vnfp) {
        let vnfPromise = new Promise(resolve => {
            let reader = new FileReader();
            reader.readAsText(vnf);
            reader.onload = () => resolve(reader.result);
        });
        promises.push(vnfPromise);
    }
  
    Promise.all(promises).then(fileContents => {
        this.vnfObj = JSON.parse(fileContents[0]);
  
    });
  }

  onUploadedNst(nsts: File[]) {

    let promises = [];

    for (let nst of nsts) {
        let nstPromise = new Promise(resolve => {
            let reader = new FileReader();
            reader.readAsText(nst);
            reader.onload = () => resolve(reader.result);
        });
        promises.push(nstPromise);
    }

    Promise.all(promises).then(fileContents => {
        this.nstObj = JSON.parse(fileContents[0]);

        //console.log(JSON.stringify(this.nstObj, null, 4));
    });
  }

  onUploadedNsds(nsds: File[]) {
    this.nsdInfoArr=[];
    let promises = [];
    for (let nsd of nsds) {
      let nsdPromise = new Promise(resolve => {
        let reader = new FileReader();
        reader.readAsText(nsd);
        reader.onload = () => resolve(reader.result);
      });
      promises.push(nsdPromise);
    }
    var i=0;
    promises.forEach(res=> {
      Promise.all(promises).then(fileContents => {
            this.nsdObj = JSON.parse(fileContents[i]);
            this.nsdObjArray.push(this.nsdObj);
            this.nsdIdentifier=this.nsdObj['nsdIdentifier'];
            this.nsdIds.push(this.nsdIdentifier);
            this.nsdInfoArr.push({'nsdId':this.nsdIdentifier,'nsdName':this.nsdObj['nsdName']});
           i++;
         });
        
    });
  }


  addVnfItem(): void {
    this.vnfItems = this.thirdFormGroup.get('vnfItems') as FormArray;
    this.vnfItems.push(this.addVnf());
  }

  removeVnfItem() {
    this.vnfItems = this.thirdFormGroup.get('vnfItems') as FormArray;
    this.vnfItems.removeAt(this.vnfItems.length - 1);
  }


  addVnf(): FormGroup {
    return this._formBuilder.group({
      name: ['', Validators.required],
      provider: ['', Validators.required],
      version: ['', Validators.required],
      checksum: ['', Validators.required],
      vnfPackagePath: ['', Validators.required],
    });
  }

  onUploadeConfRules(confRules: File[]) {
    let promises = [];
    this.confNames=[];
    for (let confRule of confRules) {
  
      let pnfdPromise = new Promise(resolve => {
        let reader = new FileReader();
        reader.readAsText(confRule);
        reader.onload = () => resolve(reader.result);
      });
      promises.push(pnfdPromise);
    }
  
    var k=0;
    promises.forEach(res=> {
    Promise.all(promises).then(fileContents => {
        this.confObj = JSON.parse(fileContents[k]);
        this.confObjArr.push(this.confObj);
     //   this.confNames.push(this.pndfObj['pnfd']['pnfdId'])
        k++; 
        });
      });
  }
  createOnBoardNstRequest() {
    var onBoardNsRequest = JSON.parse('{}');
  
    var paramsRows = this.thirdFormGroup.controls.vnfItems as FormArray;
    var controls = paramsRows.controls;
    var paramsObj = [];

    for (var j = 0; j < controls.length; j++) {
      paramsObj.push(controls[j].value);
    }


    if (this.nstObj != null) {
      var nstsArray = [];
      nstsArray.push(this.nstObj);
      onBoardNsRequest['nst'] = nstsArray[0];
    }

   if(this.nsdObjArray.length!=0){
    onBoardNsRequest['nsds'] = this.nsdObjArray;
   }
/*
   if(this.pndfObjArr.length!=0){
    onBoardNsRequest['pnfds'] = this.pndfObjArr;
   }*/
    onBoardNsRequest['vnfPackages'] = paramsObj;
    
    if(this.vnfObj!=null){
      onBoardNsRequest['vnfPackages']=this.vnfObj
    }
    
    this.nslicesService.postNslicesData(onBoardNsRequest)
      .subscribe(
        nst => {
        console.log("Successfully uploaded new nst " + nst);
        this.router.navigate(['/ns-template']);
      })    
    }

   
    onUploadedPnfds(pnfds: File[]) {
      let promises = [];
      this.pndfIds=[];
      for (let pnfd of pnfds) {
    
        let pnfdPromise = new Promise(resolve => {
          let reader = new FileReader();
          reader.readAsText(pnfd);
          reader.onload = () => resolve(reader.result);
        });
        promises.push(pnfdPromise);
      }
    
      var j=0;
      promises.forEach(res=> {
      Promise.all(promises).then(fileContents => {
          this.pndfObj = JSON.parse(fileContents[j]);
          this.pndfObjArr.push(this.pndfObj);
          this.pndfIds.push(this.pndfObj['pnfd']['pnfdId'])
          j++; 
          });
        });
    }
}
