import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { RsvpComponent } from './components/rsvp.component';
import { ListComponent } from './components/list.component';
import { SearchComponent } from './components/search.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { RSVPService } from './rsvp.service';

const appRoutes: Routes = [
  { path: 'rsvp', component: RsvpComponent },
  { path: 'list', component: ListComponent },
  { path: 'search', component: SearchComponent},
  { path: '**', redirectTo: '/', pathMatch: 'full'}
]

@NgModule({
  declarations: [
    AppComponent,
    RsvpComponent,
    ListComponent,
    SearchComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes, {useHash: true}),
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [RSVPService],
  bootstrap: [AppComponent]
})
export class AppModule { }
