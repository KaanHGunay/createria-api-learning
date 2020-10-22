import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { CriteriaLearningSharedModule } from 'app/shared/shared.module';
import { CriteriaLearningCoreModule } from 'app/core/core.module';
import { CriteriaLearningAppRoutingModule } from './app-routing.module';
import { CriteriaLearningHomeModule } from './home/home.module';
import { CriteriaLearningEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    CriteriaLearningSharedModule,
    CriteriaLearningCoreModule,
    CriteriaLearningHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    CriteriaLearningEntityModule,
    CriteriaLearningAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class CriteriaLearningAppModule {}
