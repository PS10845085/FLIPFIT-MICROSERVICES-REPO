
// src/app/components/user/user.component.ts
import { Component, OnInit } from '@angular/core';
import { UserserviceService } from '../../services/userservice.service';
import { User } from '../../models/user';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  users: User[] = [];

  addUserForm: User = {
    id: 0,
    firstname: '',
    lastname: '',
    email: ''
  };

  editingId: number | null = null;
  editModel: User | null = null;

  constructor(private userService: UserserviceService) {}

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers(): void {
    this.userService.getUsers().subscribe(data => this.users = data || []);
  }

  createUser(): void {
    this.userService.createUser(this.addUserForm).subscribe(newUser => {
      this.users.push(newUser);
      this.addUserForm = { id: 0, firstname: '', lastname: '', email: '' };
    });
  }

  startEdit(user: User): void {
    this.editingId = user.id;
    this.editModel = { ...user };
  }

  cancelEdit(): void {
    this.editingId = null;
    this.editModel = null;
  }

  saveEdit(): void {
    if (!this.editModel) return;
    const id = this.editModel.id;
    this.userService.updateUser(id, this.editModel).subscribe(updated => {
      const idx = this.users.findIndex(u => u.id === id);
      if (idx > -1) this.users[idx] = updated;
      this.cancelEdit();
    });
  }

  deleteUser(id: number): void {
    this.userService.deleteUser(id).subscribe(() => {
      this.users = this.users.filter(u => u.id !== id);
    });
  }
}
