import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Shoppingitem } from '../shoppingitem';
import { Finance } from '../finance';

@Component({
  selector: 'app-shopping-list',
  templateUrl: './shopping-list.component.html',
  styleUrl: './shopping-list.component.css'
})
export class ShoppingListComponent implements OnInit{

  shoppings:Array<Shoppingitem> = [];
  finances:Array<Finance> = [];
  description: any ='';
  productName: any = '';
  quantity: any ='';
  productPrice: any ='';
  category: any ='';

  totalcosts: any ='';


  selectedShoppingitem: Shoppingitem | null = null;
  searchedShoppingitem: string = '';
  apiTestUrl = '/api/shoppings';

  constructor(private httpClient: HttpClient) { }


ngOnInit(): void {
    const headers = new HttpHeaders()
      .set('Accept', 'application/json');

    this.httpClient.get<any>(this.apiTestUrl, {headers})
      .subscribe({
        next: (res: any) => {
          console.log(res);
          this.shoppings = res;
        },
        error: (err: any) => console.log(err.error)
      });  
  }


  search(): void {
    for(const shoppingitem of this.shoppings){
      if(shoppingitem.productName === this.searchedShoppingitem){
        this.select(shoppingitem);
      }
    }
  }

  select(r: Shoppingitem): void {
    this.selectedShoppingitem= r;
  }



  createShoppingItem(): void {
    const headers = new HttpHeaders()
      .set('Accept', 'application/json')
      .set('Content-Type', 'application/json');
  
      const itemData = {
      productName:this.productName,
      quantity: this.quantity,
      productPrice: this.productPrice,
      category: this.category

      };
    this.httpClient.post<Shoppingitem>(this.apiTestUrl, itemData, {headers})
      .subscribe({
        next: (shoppingitem) => {
          this.shoppings.push(shoppingitem);
          this.productName = '';
        },
        error: (err) => {
          console.error('Error', err);
        }
      });
  }

  deleteItem(id: number): void {
    const headers = new HttpHeaders()
      .set('Accept', 'application/json')
      .set('Content-Type', 'application/json');
  
    const url = `${this.apiTestUrl}/${id}`;
  
    this.httpClient.delete<Shoppingitem>(url, { headers })
      .subscribe({
        next: (shoppingitem) => { 
          const indexToRemove = this.shoppings.findIndex(item => item.id === shoppingitem.id);

          // only remove the removed item
          if (indexToRemove !== -1) {
              // remove the item
              this.shoppings.splice(indexToRemove, 1);
          }
      
          this.productName = '';
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
      const url = `/api/shoppings/totalcosts`;
    this.httpClient.get<number>(url,{headers})
      .subscribe({
          next: (res: any) => {
            console.log(res);
            this.totalcosts= res;
          },
          error: (err: any) => console.log(err.error)
        });  
  }




  buyMyShoppings(): void {
    const headers = new HttpHeaders()
      .set('Accept', 'application/json')
      .set('Content-Type', 'application/json');
  
      const url = `/api/finances/shoppings`;
    this.httpClient.post<Finance>(url, {headers})
      .subscribe({
        next: (finance) => {
          this.finances.push(finance);
          this.description =  '';
         
        },
        error: (err) => {
          console.error('Error', err);
        }
      });
  }
}
