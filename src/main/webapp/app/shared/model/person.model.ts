import { ICar } from 'app/shared/model/car.model';

export interface IPerson {
  id?: number;
  name?: string;
  surname?: string;
  cars?: ICar[];
}

export class Person implements IPerson {
  constructor(public id?: number, public name?: string, public surname?: string, public cars?: ICar[]) {}
}
