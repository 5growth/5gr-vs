import { LoginComponent } from './login/login.component';
import { FullComponent } from './../layouts/full/full.component';
import { DomainsDetailsComponent } from './domains-details/domains-details.component';
import { DomainsComponent } from './domains/domains.component';
import { VsBlueprintComponent } from './vs-blueprint/vs-blueprint.component';
import { NSliceTemplateComponent } from './n-slice-template/n-slice-template.component';
import { VsInstanceComponent } from './vs-instance/vs-instance.component';
import { VsDescriptorComponent } from './vs-descriptor/vs-descriptor.component';
import { VsDescriptorStepperComponent } from './vs-descriptor-stepper/vs-descriptor-stepper.component';
import {VsBlueprintStepperComponent} from './vs-blueprint-stepper/vs-blueprint-stepper.component'
import { GroupComponent } from './group/group.component';
import { Routes } from '@angular/router';
import { TenantComponent } from './tenant/tenant.component';
import {DashboardComponent} from '../dashboard/dashboard.component';
import { SlaComponent } from './sla/sla.component';
import { DomainsStepperComponent } from './domains-stepper/domains-stepper.component';
import { NSliceTemplateStepperComponent } from './n-slice-template-stepper/n-slice-template-stepper.component';
import {VsInstanceDetailsComponent} from './vs-instance-details/vs-instance-details.component'
import {VsInstanceStepperComponent} from './vs-instance-stepper/vs-instance-stepper.component'
import {VsBlueprintDetailsComponent} from './vs-blueprint-details/vs-blueprint-details.component'
import {NSliceInstancesComponent} from './n-slice-instances/n-slice-instances.component'
import {NSliceInstancesStepperComponent} from './n-slice-instances-stepper/n-slice-instances-stepper.component'

export const MaterialRoutes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'dashboard',
    component: DashboardComponent
  },
  {
    path: 'tenant',
    component: TenantComponent
  },
  {
    path: 'vsdescriptor',
    component: VsDescriptorComponent
  },
  {
    path: 'vsdescriptor-add',
    component: VsDescriptorStepperComponent
  },
  {
    path: 'vsinstance',
    component: VsInstanceComponent
  },
  {
    path: 'vsinstance-add',
    component: VsInstanceStepperComponent
  },
  {
    path: 'ns-template',
    component: NSliceTemplateComponent
  }, 
  {
    path: 'vsblueprint',
    component: VsBlueprintComponent
  },
  {
    path: 'vsblueprint-add',
    component: VsBlueprintStepperComponent
  },
  {
    path: 'group',
    component: GroupComponent
  },
  {
    path: 'domains',
    component: DomainsComponent
  },
  {
    path: 'domains-add',
    component: DomainsStepperComponent
  },
  {
    path: 'ns-template-add',
    component: NSliceTemplateStepperComponent
  },
  
 {
  path: 'sla',
  component: SlaComponent
},  
{
  path: 'domains-details',
  component: DomainsDetailsComponent
},
{
  path: 'vsi-details',
  component: VsInstanceDetailsComponent
},
{
  path: 'vsblueprint-details',
  component: VsBlueprintDetailsComponent
},
{
  path: 'ns-instances',
  component: NSliceInstancesComponent
},
{
  path: 'ns-instances-add',
  component: NSliceInstancesStepperComponent
},

{
  path: 'full',
  component: FullComponent

},
{ path: '', redirectTo: '/login', pathMatch: 'full' }, // redirect to `first-component`
];
