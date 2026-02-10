import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { hasRole } from '../../core/guard/authorization.guard';
import { InvalidAccessComponent } from '../../shared/components/invalid-access/invalid-access.component';
import { NotFoundComponent } from '../../shared/components/not-found/not-found.component';
import { AdministratorComponent } from './administrator/administrator.component';
import { PersonComponent } from './user/person.component';
import { EditDeviceComponent } from './device/edit-device/edit-device.component';
import { DeviceComponent } from './device/device.component';
import { DeviceSaveComponent } from './device/device-save/device-save.component';
import { ClientComponent } from './client/client.component';
import { EditUserComponent } from "./user/edit-user/edit-user.component";
import {LoginComponent} from "../authentication/login/login.component";
import {ChatComponent} from "./chat/chat.component";

export const routes: Routes = [
  {
    path: 'administrator',
    component: AdministratorComponent,
    canActivate: [ hasRole ],
    data: {
      requiredRoles: ['ADMIN']
    }
  },
  {
    path: 'person',
    component: PersonComponent,
    canActivate: [ hasRole ],
    data: {
      requiredRoles: ['ADMIN']
    }
  },

  {
    path: 'edit-user/:name',
    component: EditUserComponent,
    canActivate: [ hasRole ],
    data: {
      requiredRoles: ['ADMIN']
    }
  },
  {
    path: 'device',
    component: DeviceComponent,
    canActivate: [ hasRole ],
    data: {
      requiredRoles: [ 'ADMIN']
    }
  },
  {
    path: 'chat',
    component: ChatComponent,
    canActivate: [ hasRole ],
    data: {
      requiredRoles: [ 'ADMIN', 'CLIENT']
    }
  },
  {
    path: 'device-save',
    component: DeviceSaveComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['ADMIN']
    }
  },

  {
    path: 'edit-device/:idDevice',
    component: EditDeviceComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['ADMIN']
    }
  },
  {
    path: 'client',
    component: ClientComponent,
    canActivate: [hasRole],
    data: {
      requiredRoles: ['CLIENT']
    }
  },
  {
    path: 'invalid-access',
    component: InvalidAccessComponent
  },
  {
    path: 'not-found',
    component: NotFoundComponent
  },
  {
    path: '**',
    component: LoginComponent
  },
];

@NgModule({
  imports: [
    InvalidAccessComponent,
    NotFoundComponent,
    RouterModule.forChild(routes)
  ],
  exports: [ RouterModule ]
})
export class DashboardRoutingModule {
}
