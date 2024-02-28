import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Subject } from '../subject';
@Component({
  selector: 'app-subject-list',
  templateUrl: './subject-list.component.html',
  styleUrl: './subject-list.component.css'
})
export class SubjectListComponent implements OnInit{
subjects:  Array<Subject> = [];
   name:any = '';
  subjectName: any = '';
  grade : any = '';
  averagePoints:any= '';

  selectedSubject: Subject | null = null;
  searchedSubject: string = '';

  apiTestUrl = '/api/semesters/1/subjects';

  constructor(private httpClient: HttpClient) { }

  ngOnInit(): void {
    const headers = new HttpHeaders()
      .set('Accept', 'application/json');

    this.httpClient.get<any>(this.apiTestUrl, {headers})
      .subscribe({
        next: (res: any) => {
          console.log(res);
          this.subjects= res;
        },
        error: (err: any) => console.log(err.error)
      });  
  }

  search(): void {
    for(const subject of this.subjects){
      if(subject.subjectName === this.searchedSubject){
        this.select(subject);
      }
    }
  }

  select(r: Subject): void {
    this.selectedSubject= r;
  }

  createSubject(): void {
    const headers = new HttpHeaders()
      .set('Accept', 'application/json')
      .set('Content-Type', 'application/json');
  
      const subjectData = {
        subjectName: this.subjectName,
        grade: this.grade,  
 
        
      };
    this.httpClient.put<Subject>(this.apiTestUrl, subjectData, {headers})
      .subscribe({
        next: (subject) => {
          this.subjects.push(subject);
          this.subjectName = '';
        },
        error: (err) => {
          console.error('Error', err);
        }
      });
  }
  getAveragePoints(): void {
    const headers = new HttpHeaders()
      .set('Accept', 'application/json')
      .set('Content-Type', 'application/json');
      const url = `/api/semesters/1/averagepoints`;
    this.httpClient.get<number>(url,{headers})
      .subscribe({
          next: (res: any) => {
            console.log(res);
            this.averagePoints= res;
          },
          error: (err: any) => console.log(err.error)
        });  
  }


}
