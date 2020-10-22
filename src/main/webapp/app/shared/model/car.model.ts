import { CarTypes } from 'app/shared/model/enumerations/car-types.model';

export interface ICar {
  id?: number;
  brand?: string;
  year?: number;
  carType?: CarTypes;
  personId?: number;
}

export class Car implements ICar {
  constructor(public id?: number, public brand?: string, public year?: number, public carType?: CarTypes, public personId?: number) {}
}
