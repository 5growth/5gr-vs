import { Input } from '@angular/core';
import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { NslicesService } from '../../../services/n-slices.service';
import { VsBlueprintsService } from '../../../services/vs-Blueprints.service';
import { NslicesInfo } from '../n-slices/n-slices-info';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environments';

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
  isLinear = false;
  vsbObj: Object;
  nstObj: Object;
  nslicesInfos: NslicesInfo[] = [];
  nstInptArr = JSON.parse('{}');
  items: FormArray;
  itemsConf: FormArray;
  nstInputNumber = 0;
  confParamnInputNumber = 0;
  prevPId = []
  selected: string;
  nsdObj: Object;
  vnfObj: Object;
  paramsVsb: String[] = [];
  instLevels: String[] = [];
  showNst: boolean;

  constructor(private _formBuilder: FormBuilder,
    private nslicesService: NslicesService,
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
      vnfp: ['', Validators.required],
    });
    this.fifthFormGroup = this._formBuilder.group({
      nstGroup: this._formBuilder.array([this.addNstGroup()])
    });
  }

  ngOnInit(): void {
    this.selected = 'Nst Id'
    if (environment.deploymentType == 'VSMF/NSMF' ||
      environment.deploymentType == 'NSMF/VSMF' ||
      environment.deploymentType == 'VSMF'
    ) {
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

  getNslices() {
    this.nslicesService.getNslicesData().subscribe((nslicesInfos: NslicesInfo[]) => {
      this.nslicesInfos = nslicesInfos;
    });
  }

  createItemConfs(): FormGroup {
    return this._formBuilder.group({
      confComponents: ''
    });
  }

  createItem(): FormGroup {
    return this._formBuilder.group({
      parameterId: '',
      parameterName: '',
      parameterType: '',
      parameterDescription: '',
      applicabilityField: ''
    });
  }

  private addNstGroup(): FormGroup {
    return this._formBuilder.group({
      nsdId: '',
      nsdVersion: '',
      nsFlavourId: '',
      nsInstLevel: '',
      nstId: [],
      input: this._formBuilder.array([])
    });
  }

  /**************NST******************************* */
  addnst(): void {
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
  /************** END NST******************************* */

  onUploadedVsb(event: any, vsbs: File[]) {
    let promises = [];

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
      /*
      for (var paramVsb of this.vsbObj['parameters']) {
        this.paramsVsb.push(paramVsb['parameterId']);
      }
      */
    });
  }

  onUploadedNsd(event: any, nsds: File[]) {
    let promises = [];

    for (let nsd of nsds) {
      let nsdPromise = new Promise(resolve => {
        let reader = new FileReader();
        reader.readAsText(nsd);
        reader.onload = () => resolve(reader.result);
      });
      promises.push(nsdPromise);
    }

    Promise.all(promises).then(fileContents => {
      this.nsdObj = JSON.parse(fileContents[0]);
    });
  }

  onUploadedNst(event: any, nsts: File[]) {
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
    onBoardNsRequest['nst'] = this.nstObj
    onBoardNsRequest['nsd'] = this.nsdObj
    this.nslicesService.postNslicesData(onBoardNsRequest)
      .subscribe(
        nst => {
          this.getNslices();
          console.log("Successfully uploaded new nst " + nst);
        })
  }

  createVsb() {
    var onBoardDsRequest = JSON.parse('{}');
    onBoardDsRequest['vsBlueprint'] = this.vsbObj;

    if (this.nsdObj != null) {
      var nsdsArray = [];
      nsdsArray.push(this.nsdObj);
      onBoardDsRequest['nsds'] = nsdsArray;
    }
    
    if (this.vnfObj != null) {
      onBoardDsRequest['vnfPackages'] = this.vnfObj;
    }

    onBoardDsRequest['translationRules'] = [];
    var nstObj = [];

    for (var val of this.nstArray.value) {
      /*
      if(val['input'].length==0){
        val['input'].push({parameterId:'MaximumNumberUsers',minValue:0,maxValue:10})
      }
      */
      nstObj.push(val)
    }
    
    onBoardDsRequest['translationRules'] = nstObj;
    console.log("onBoardDsRequest",this.vsbObj)
    this.vsBlueprintsService.postVsBlueprintsData(this.vsbObj)
      .subscribe(res => {
        this.router.navigate(['/vsblueprint']);
      });
  }

  onClickNext() {

    var paramsRows = this.firstFormGroup.controls.items as FormArray;
    var controls = paramsRows.controls;
    var paramsObj = [];

    for (var j = 0; j < controls.length; j++) {
      paramsObj.push(controls[j].value);
    }

    for (var param of paramsObj) {
      this.prevPId.push(param.parameterId)
    }
  }
}

