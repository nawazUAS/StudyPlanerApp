import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { EventListComponent } from './event-list/event-list.component';
import { TodoListComponent } from './todo-list/todo-list.component';
import { SubjectListComponent } from './subject-list/subject-list.component';
import { FinanceListComponent } from './finance-list/finance-list.component';
import { ShoppingListComponent } from './shopping-list/shopping-list.component';



import { RouterModule, Routes } from '@angular/router';
import { AboutUsComponent } from './about-us/about-us.component';
import { ListsComponent } from './lists/lists.component';
import { NavbarComponent } from './navbar/navbar.component';

const routes: Routes = [

  { path: '', redirectTo: '/events', pathMatch: 'full' },
  { path: 'about', component: AboutUsComponent },
  { path: 'events', component: EventListComponent },
  { path: 'todos', component: TodoListComponent },
  { path: 'subjects', component: SubjectListComponent },
  { path: 'shoppings', component: ShoppingListComponent },
  { path: 'finances', component: FinanceListComponent },
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    EventListComponent,
    TodoListComponent,
    SubjectListComponent,
    FinanceListComponent,
    ShoppingListComponent,
    AboutUsComponent,
    ListsComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,    
    RouterModule.forRoot(routes),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
