import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Todo } from '../todo';
@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrl: './todo-list.component.css'
})
export class TodoListComponent implements OnInit{

  todos: Array<Todo> = [];
  title: any = '';
  date: any ='';
  description: any ='';
  completed: any ='';


  selectedTodo: Todo | null = null;
  searchedTodo: string = '';
  apiTestUrl = '/api/todos';

  constructor(private httpClient: HttpClient) { }

  ngOnInit(): void {
    const headers = new HttpHeaders()
      .set('Accept', 'application/json');

    this.httpClient.get<any>(this.apiTestUrl, {headers})
      .subscribe({
        next: (res: any) => {
          console.log(res);
          this.todos = res;
        },
        error: (err: any) => console.log(err.error)
      });  
  }
  search(): void {
    for(const todo of this.todos){
      if(todo.title === this.searchedTodo){
        this.select(todo);
      }
    }
  }

  select(r: Todo): void {
    this.selectedTodo = r;
  }

  createTodo(): void {
    const headers = new HttpHeaders()
      .set('Accept', 'application/json')
      .set('Content-Type', 'application/json');
  
      const todoData = {
        title: this.title,
        description: this.description,
        completed: this.completed
        
      };
    this.httpClient.post<Todo>(this.apiTestUrl, todoData, {headers})
      .subscribe({
        next: (todo) => {
          this.todos.push(todo);
          this.title = '';
        },
        error: (err) => {
          console.error('Error', err);
        }
      });
  }
  updateTodo(id: number): void {
    const headers = new HttpHeaders()
      .set('Accept', 'application/json')
      .set('Content-Type', 'application/json');
  
    const url = `${this.apiTestUrl}/${id}/status`;
    this.httpClient.put<Todo>(url, true, {headers})
      .subscribe({
        next: (todo) => {
          //find extisting todo because i just want do update
          const existingTodoIndex = this.todos.findIndex(existingTodo => existingTodo.id === todo.id);
      
          if (existingTodoIndex !== -1) {
              // uupdate the todo
              this.todos[existingTodoIndex] = todo;
          } 
      

          this.title = '';
        },
        error: (err) => {
          console.error('Error', err);
        }
      });
  }

  deleteTodo(id: number): void {
    const headers = new HttpHeaders()
      .set('Accept', 'application/json')
      .set('Content-Type', 'application/json');
  
    const url = `${this.apiTestUrl}/${id}`;
  
    this.httpClient.delete<Todo>(url, { headers })
      .subscribe({
        next: (todo) => {
          const indexToRemove = this.todos.findIndex(item => item.id === todo.id);

          // only remove the removed item
          if (indexToRemove !== -1) {
              // remove the item
              this.todos.splice(indexToRemove, 1);
          }
      
          this.title = '';
        },
        error: (err) => {
          console.error('Error', err);
        }
      });
  }
}
