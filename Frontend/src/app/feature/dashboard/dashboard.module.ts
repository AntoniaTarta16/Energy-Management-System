import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { AdministratorComponent } from './administrator/administrator.component';
import { PersonComponent } from './user/person.component';
import { EditDeviceComponent } from './device/edit-device/edit-device.component';
import { DeviceSaveComponent } from './device/device-save/device-save.component';
import { DeviceComponent } from './device/device.component';
import { ClientComponent } from './client/client.component';
import { RegisterComponent } from './register/register.component';
import {EditUserComponent} from "./user/edit-user/edit-user.component";
import {ChatComponent} from "./chat/chat.component";

@NgModule({
  declarations: [
    AdministratorComponent,
    PersonComponent,
    EditDeviceComponent,
    DeviceSaveComponent,
    DeviceComponent,
    ClientComponent,
    RegisterComponent,
    EditUserComponent,
    ChatComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class DashboardModule {
}
