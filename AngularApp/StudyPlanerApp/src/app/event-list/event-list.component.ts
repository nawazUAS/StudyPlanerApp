import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Event } from '../event';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrl: './event-list.component.css'
})
export class EventListComponent implements OnInit{

  events:Array<Event> = [];
  title: any = '';
  day: any ='';
  month: any ='';
  year: any ='';
  hours: any ='';
  minutes: any ='';

  selectedEvent: Event | null = null;
  searchedEvent: string = '';
  apiTestUrl = '/api/events';

  constructor(private httpClient: HttpClient) { }

  ngOnInit(): void {
    const headers = new HttpHeaders()
      .set('Accept', 'application/json');

    this.httpClient.get<any>(this.apiTestUrl, {headers})
      .subscribe({
        next: (res: any) => {
          console.log(res);
          this.events = res;
        },
        error: (err: any) => console.log(err.error)
      });  
  }
  search(): void {
    for(const event of this.events){
      if(event.title === this.searchedEvent){
        this.select(event);
      }
    }
  }

  select(r: Event): void {
    this.selectedEvent = r;
  }
  createEvent(): void {
    const headers = new HttpHeaders()
      .set('Accept', 'application/json')
      .set('Content-Type', 'application/json');
  
      const eventData = {
        title: this.title,
        day: this.day,  
        month: this.month,
        year: this.year,

        hours: this.hours,
        minutes: this.minutes
        
      };
    this.httpClient.post<Event>(this.apiTestUrl, eventData, {headers})
      .subscribe({
        next: (event) => {
          this.events.push(event);
          this.title = '';
        },
        error: (err) => {
          console.error('Error', err);
        }
      });
  }

  deleteEvent(id: number): void {
    const headers = new HttpHeaders()
      .set('Accept', 'application/json')
      .set('Content-Type', 'application/json');
  
    const url = `${this.apiTestUrl}/${id}`;
  
    this.httpClient.delete<Event>(url, { headers })
      .subscribe({
        next: (event) => {
          const indexToRemove = this.events.findIndex(item => item.id === event.id);

          // only remove the removed item
          if (indexToRemove !== -1) {
              // remove the item
              this.events.splice(indexToRemove, 1);
          }
      
          this.title= '';
        },
        error: (err) => {
          console.error('Error', err);
        }
      });
  }
}
