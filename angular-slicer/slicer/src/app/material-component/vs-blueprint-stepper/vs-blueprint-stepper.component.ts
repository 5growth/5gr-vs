import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { VsBlueprintsService } from '../../services/vs-Blueprints.service';
import { Router } from '@angular/router';
import { environment } from '../../environments/environments';

@Component({
  selector: 'app-vs-blueprint-stepper',
  templateUrl: './vs-blueprint-stepper.component.html',
  styleUrls: ['./vs-blueprint-stepper.component.scss']
})
export class VsBlueprintStepperComponent implements OnInit {
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  fourthFormGroup: FormGroup;
  fifthFormGroup: FormGroup;
  sixthFormGroup:FormGroup;
  seventhFormGroup:FormGroup;

  
  isLinear = false;
  vsbObj: Object;
  nstObj: Object;
  dfs: String[] = [];
  nstInptArr = JSON.parse('{}');
  items: FormArray;
  nstInputNumber = 0;
  selected: string;
  nsdObj: Object;
  vnfObj: Object;
  pndfObj:object;
  confObj:object;
  paramsVsb: String[] = [];
  instLevels: String[] = [];
  showNst: boolean;
  nsdIdentifier:String;
  nstId:String;
  nsdName:String;
  nstName:String;
  nsdVersion:String;
  vnfItems:FormArray;
  nsdObjArray=[];
  nsdIds=[];
  nsdNames=[];
  nsdInfo=JSON.parse('{}');
  nsdInfoArr=[];
  pndfIds=[];
  pndfObjArr=[];
  confObjArr=[];
  confNames=[];
  constructor(private _formBuilder: FormBuilder,
    private vsBlueprintsService: VsBlueprintsService,
    private router: Router
  ) {
    this.firstFormGroup = this._formBuilder.group({
      vsb: ['', Validators.required],
    });
    this.secondFormGroup = this._formBuilder.group({
      nst: ['', Validators.required],
    });
    this.thirdFormGroup = this._formBuilder.group({
      nsd: ['', Validators.required],
    });
    this.fourthFormGroup = this._formBuilder.group({
      vnfItems: this._formBuilder.array([this.addVnf()])
    });
    this.fifthFormGroup = this._formBuilder.group({
      nstGroup: this._formBuilder.array([this.addNstGroup()])
    });
    this.sixthFormGroup = this._formBuilder.group({
      pnfd: ['', Validators.required],
    });
    this.seventhFormGroup = this._formBuilder.group({
      configurationRules: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.selected = 'Nst Id';
    if (environment.deploymentType == 'VSMF/NSMF' || environment.deploymentType == 'VSMF') {
      this.showNst = true;
    }
  }

  envCondition() {
    if (environment.deploymentType == 'VSMF') {
      return false;
    } else {
      return true;
    }
  }

  createItemConfs(): FormGroup {
    return this._formBuilder.group({
      confComponents: ''
    });
  }

  createItem(): FormGroup {
    return this._formBuilder.group({
      parameterId:['', Validators.required],
      parameterName:['', Validators.required],
      parameterType: ['', Validators.required],
      parameterDescription: ['', Validators.required],
      applicabilityField: ['', Validators.required],
    });
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

  private addNstGroup(): FormGroup {
    return this._formBuilder.group({
      nsdId: ['', Validators.required],
      nsdVersion: ['', Validators.required],
      nsFlavourId: ['', Validators.required],
      nsInstantiationLevelId: ['', Validators.required],
      nstId: [[], Validators.required],
      input: this._formBuilder.array([])
    });
  }

  onUploadedVsb(vsbs: File[]) {
    let promises = [];
    this.paramsVsb=[];
    for (let vsb of vsbs) {
      let vsbPromise = new Promise(resolve => {
        let reader = new FileReader();
        reader.readAsText(vsb);
        reader.onload = () => resolve(reader.result);
      });
      promises.push(vsbPromise);
    }

    Promise.all(promises).then(fileContents => {
      this.vsbObj = JSON.parse(fileContents[0]);

      
      for (var paramVsb of this.vsbObj['parameters']) {
        this.paramsVsb.push(paramVsb['parameterId']);
      }
      
    });
  }

onUploadedPnfds(pnfds: File[]) {
  let promises = [];
  this.pndfObjArr=[];
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
      this.pndfIds.push(this.pndfObj['pnfdId'])
      j++; 
      });
    });
}

onUploadeConfRules(confRules: File[]) {
  let promises = [];
  this.confObjArr=[];
  this.confNames=[];
  for (let confRule of confRules) {

    let confPromise = new Promise(resolve => {
      let reader = new FileReader();
      reader.readAsText(confRule);
      reader.onload = () => resolve(reader.result);
    });
    promises.push(confPromise);
  }

  var k=0;
  promises.forEach(res=> {
  Promise.all(promises).then(fileContents => {
      this.confObj = JSON.parse(fileContents[k]);
      this.confObjArr.push(this.confObj);
      this.confNames.push(this.confObj['name'])

      k++; 
      });

    });
}

  onUploadedNsds(nsds: File[]) {
    this.nsdInfoArr=[];
    let promises = [];
    this.nsdObjArray=[];
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

  onNsdIdSelected(event:any) {
    var selectedNsd = event.value;
    for(var nsdObj of this.nsdObjArray){
      if(nsdObj['nsdIdentifier']==selectedNsd){
        this.nsdVersion=nsdObj['version'];
        for (var i = 0; i < nsdObj['nsDf'].length; i++) {
          this.dfs = nsdObj['nsDf'];
          this.instLevels = nsdObj['nsDf'][i]['nsInstantiationLevel'];
        }
      }

    }
  }

  onNsDfSelected(event:any) {
    var selectedDf = event.value;

    for (var i = 0; i < this.nsdObj['nsDf'].length; i++) {
      if (this.nsdObj['nsDf'][i]['nsDfId'] == selectedDf) {
        this.instLevels = this.nsdObj['nsDf'][i]['nsInstantiationLevel'];
      }
    }
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
      this.nstId=this.nstObj['nstId'];
      this.nstName=this.nstObj['nstName'];
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

  createOnBoardNstRequest() {
    var onBoardNsRequest = JSON.parse('{}');
    onBoardNsRequest['nst'] = this.nstObj;
    onBoardNsRequest['nsd'] = this.nsdObj;
  }


  /**************NST******************************* */
  addnst(): void {
    this.nsdIdentifier=this.nsdObj['nsdIdentifier'];
    this.nsdVersion=this.nsdObj['version'];
    this.nstArray.push(this.addNstGroup());
  }

  removeNst(): void {
    this.nstArray.removeAt(this.nstArray.length - 1);
  }

  get nstArray(): FormArray {
    return <FormArray>this.fifthFormGroup.get('nstGroup');
  }

  addNstInput(index): void {
    (<FormArray>(<FormGroup>this.nstArray.controls[index]).controls.input).push(this.nstsGroup());
    this.nstInputNumber = (<FormArray>(<FormGroup>this.nstArray.controls[index]).controls.input).length
    this.nstInptArr[index] = this.nstInputNumber;
  }

  removeNstInput(index): void {
    (<FormArray>(<FormGroup>this.nstArray.controls[index]).controls.input).removeAt((<FormArray>(<FormGroup>this.nstArray.controls[index]).controls.input).length - 1);
    this.nstInputNumber = (<FormArray>(<FormGroup>this.nstArray.controls[index]).controls.input).length
    this.nstInptArr[index] = this.nstInputNumber;
  }

  private nstsGroup(): FormGroup {
    return this._formBuilder.group({
      parameterId: [],
      minValue: [],
      maxValue: [],
    });
  }

  addVnfItem(): void {
    this.vnfItems = this.fourthFormGroup.get('vnfItems') as FormArray;
    this.vnfItems.push(this.addVnf());
  }

  removeVnfItem() {
    this.vnfItems = this.fourthFormGroup.get('vnfItems') as FormArray;
    this.vnfItems.removeAt(this.vnfItems.length - 1);
  }

  /************** END NST******************************* */
  createVsb() {
    var onBoardDsRequest = JSON.parse('{}');
    onBoardDsRequest['vsBlueprint'] = this.vsbObj;
    var paramsRows = this.fourthFormGroup.controls.vnfItems as FormArray;
    var controls = paramsRows.controls;
    var paramsObj = [];

    for (var j = 0; j < controls.length; j++) {
      paramsObj.push(controls[j].value);
    }
    onBoardDsRequest['translationRules'] = this.nstArray.value;

    onBoardDsRequest['vnfPackages'] = paramsObj;
    
    if (this.nstObj != null) {
      var nstsArray = [];
      nstsArray.push(this.nstObj);
      onBoardDsRequest['nsts'] = nstsArray;
    }

   if(this.nsdObjArray.length!=0){
    onBoardDsRequest['nsds'] = this.nsdObjArray;
   }

   if(this.pndfObjArr.length!=0){
    onBoardDsRequest['pnfds'] = this.pndfObjArr;
   }

   if(this.confObjArr.length!=0){
    onBoardDsRequest['configurationRules'] = this.confObjArr;

   }
   
    this.vsBlueprintsService.postVsBlueprintsData(onBoardDsRequest)
      .subscribe(res => {
         this.router.navigate(['/vsblueprint']);
      });
  }


}

