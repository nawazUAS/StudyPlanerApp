import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Finance } from '../finance';
@Component({
  selector: 'app-finance-list',
  templateUrl: './finance-list.component.html',
  styleUrl: './finance-list.component.css'
})
export class FinanceListComponent implements OnInit{
  finances:Array<Finance> = [];
  description: any ='';
  amount: any ='';
  date: any ='';
  type:any ='';
  category: any ='';

  totalcosts: any ='';

  selectedFinance: Finance | null = null;
  searchedFinance: string = '';
  apiTestUrl = '/api/finances';

  constructor(private httpClient: HttpClient) { }

 
  ngOnInit(): void {
    const headers = new HttpHeaders()
      .set('Accept', 'application/json');

    this.httpClient.get<any>(this.apiTestUrl, {headers})
      .subscribe({
        next: (res: any) => {
          console.log(res);
          this.finances = res;
        },
        error: (err: any) => console.log(err.error)
      });  
  }

  search(): void {
    for(const finance of this.finances){
      if(finance.description === this.searchedFinance){
        this.select(finance);
      }
    }
  }

  select(r: Finance): void {
    this.selectedFinance = r;
  }

  createFinance(): void {
    const headers = new HttpHeaders()
      .set('Accept', 'application/json')
      .set('Content-Type', 'application/json');
  
      const financeData = {
        description: this.description,
        amount: this.amount,
        type:this.type,
        category: this.category

        
      };
    this.httpClient.post<Finance>(this.apiTestUrl, financeData, {headers})
      .subscribe({
        next: (finance) => {
          this.finances.push(finance);
          this.description= '';
        },
        error: (err) => {
          console.error('Error', err);
        }
      });
  }


  getTotalCosts(): void {
    const headers = new HttpHeaders()
      .set('Accept', 'application/json')
      .set('Content-Type', 'application/json');
      const url = `/api/finances/total`;
    this.httpClient.get<number>(url,{headers})
      .subscribe({
          next: (res: any) => {
            console.log(res);
            this.totalcosts= res;
          },
          error: (err: any) => console.log(err.error)
        });  
  }

}
