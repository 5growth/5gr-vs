import 'hammerjs';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from '../demo-material-module';
import { CdkTableModule } from '@angular/cdk/table';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MaterialRoutes } from './material.routing';
import { DialogGroupComponent} from './dialog-group/dialog-group.component';
import { VsDescriptorComponent } from './vs-descriptor/vs-descriptor.component';
import { VsInstanceComponent } from './vs-instance/vs-instance.component';
import { VsBlueprintComponent } from './vs-blueprint/vs-blueprint.component';
import { NSliceTemplateComponent } from './n-slice-template/n-slice-template.component';
import { SlaComponent } from './sla/sla.component';
import {TenantComponent} from './tenant/tenant.component';
import { DialogTenantComponent } from './dialog-tenant/dialog-tenant.component';
import { VsBlueprintStepperComponent } from './vs-blueprint-stepper/vs-blueprint-stepper.component';
import { DialogTenantSlaMecComponent } from './dialog-tenant-sla-mec/dialog-tenant-sla-mec.component';
import { DialogTenantSlaCloudComponent } from './dialog-tenant-sla-cloud/dialog-tenant-sla-cloud.component';
import { DialogTenantSlaGlobalComponent } from './dialog-tenant-sla-global/dialog-tenant-sla-global.component';
import { DomainsComponent } from './domains/domains.component';
import { DomainsDetailsComponent } from './domains-details/domains-details.component';
import { DomainsStepperComponent } from './domains-stepper/domains-stepper.component';
import { VsDescriptorStepperComponent } from './vs-descriptor-stepper/vs-descriptor-stepper.component';
import { NSliceTemplateStepperComponent } from './n-slice-template-stepper/n-slice-template-stepper.component';
import { LoginComponent } from './login/login.component';
import{VsInstanceDetailsComponent} from './vs-instance-details/vs-instance-details.component';
import { VsInstanceStepperComponent } from './vs-instance-stepper/vs-instance-stepper.component';
import { VsBlueprintDetailsComponent } from './vs-blueprint-details/vs-blueprint-details.component';
import { BlueprintGraphComponent } from './blueprint-graph/blueprint-graph.component';
import { NSliceInstancesComponent } from './n-slice-instances/n-slice-instances.component';
import { NSliceInstancesStepperComponent } from './n-slice-instances-stepper/n-slice-instances-stepper.component';
import { DialogConformationComponent } from './dialog-conformation/dialog-conformation.component'
@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(MaterialRoutes),
    DemoMaterialModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    CdkTableModule
  ],
  providers: [],
  entryComponents: [],
  declarations: [
    DialogGroupComponent,
    VsDescriptorComponent,
    VsInstanceComponent,
    VsBlueprintComponent,
    NSliceTemplateComponent,
    SlaComponent,
    TenantComponent,
    DialogTenantComponent,
    VsBlueprintStepperComponent,
    DialogTenantSlaMecComponent,
    DialogTenantSlaCloudComponent,
    DialogTenantSlaGlobalComponent,
    DomainsComponent,
    VsInstanceDetailsComponent,
    DomainsDetailsComponent,
    DomainsStepperComponent,
    VsDescriptorStepperComponent,
    NSliceTemplateStepperComponent,
    LoginComponent,
    VsInstanceStepperComponent,
    VsBlueprintDetailsComponent,
    BlueprintGraphComponent,
    NSliceInstancesComponent,
    NSliceInstancesStepperComponent,
    DialogConformationComponent
  ]
})
export class MaterialComponentsModule {}
