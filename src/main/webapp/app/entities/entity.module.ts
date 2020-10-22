import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'car',
        loadChildren: () => import('./car/car.module').then(m => m.CriteriaLearningCarModule),
      },
      {
        path: 'person',
        loadChildren: () => import('./person/person.module').then(m => m.CriteriaLearningPersonModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class CriteriaLearningEntityModule {}
