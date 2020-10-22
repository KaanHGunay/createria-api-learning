import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICar, Car } from 'app/shared/model/car.model';
import { CarService } from './car.service';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person/person.service';

@Component({
  selector: 'jhi-car-update',
  templateUrl: './car-update.component.html',
})
export class CarUpdateComponent implements OnInit {
  isSaving = false;
  people: IPerson[] = [];

  editForm = this.fb.group({
    id: [],
    brand: [],
    year: [],
    carType: [],
    personId: [],
  });

  constructor(
    protected carService: CarService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ car }) => {
      this.updateForm(car);

      this.personService.query().subscribe((res: HttpResponse<IPerson[]>) => (this.people = res.body || []));
    });
  }

  updateForm(car: ICar): void {
    this.editForm.patchValue({
      id: car.id,
      brand: car.brand,
      year: car.year,
      carType: car.carType,
      personId: car.personId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const car = this.createFromForm();
    if (car.id !== undefined) {
      this.subscribeToSaveResponse(this.carService.update(car));
    } else {
      this.subscribeToSaveResponse(this.carService.create(car));
    }
  }

  private createFromForm(): ICar {
    return {
      ...new Car(),
      id: this.editForm.get(['id'])!.value,
      brand: this.editForm.get(['brand'])!.value,
      year: this.editForm.get(['year'])!.value,
      carType: this.editForm.get(['carType'])!.value,
      personId: this.editForm.get(['personId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICar>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IPerson): any {
    return item.id;
  }
}
