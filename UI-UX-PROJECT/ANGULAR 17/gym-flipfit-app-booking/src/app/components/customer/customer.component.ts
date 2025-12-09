import { Component } from '@angular/core';
import { Customer } from '../../models/customer';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrl: './customer.component.css'
})
export class CustomerComponent {


  customers: Customer[] = [];
  newCustomer: Customer = { name: '', age: 0 };

  addCustomer() {
    this.customers.push({ ...this.newCustomer });
    this.newCustomer = { name: '', age: 0 }; // Reset form
  }


  editCustomer(index: number) {
    this.newCustomer = { ...this.customers[index] };
    this.customers.splice(index, 1); // Remove old entry, will re-add after edit
  }

  
 deleteCustomer(index: number) {
    this.customers.splice(index, 1);
  }


}
