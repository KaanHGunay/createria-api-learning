export interface ICar {
  id?: number;
  brand?: string;
  year?: number;
}

export class Car implements ICar {
  constructor(public id?: number, public brand?: string, public year?: number) {}
}
