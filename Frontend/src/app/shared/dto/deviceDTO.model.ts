export interface DeviceDTO {
  idDevice: string;
  description: string;
  address: string;
  maxHourlyEnergyConsumption: number;
  idUser?: string;
}
