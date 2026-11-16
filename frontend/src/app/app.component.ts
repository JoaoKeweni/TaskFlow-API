import { CommonModule } from '@angular/common';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

interface Task { id: number; title: string; description: string; priority: string; columnName: string; userName: string; }
interface Column { id: number; name: string; position: number; boardName: string; tasks: Task[]; }
interface Board { id: number; name: string; description: string; ownerUsername: string; columns: Column[]; }
interface User { id: number; username: string; email: string; }

@Component({
  selector: 'app-root', standalone: true, imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
  apiUrl = localStorage.getItem('taskflow.apiUrl') || 'http://localhost:8080/api';
  user: User | null = this.readUser();
  boards: Board[] = [];
  activeBoard: Board | null = null;
  loading = false;
  error = '';
  notice = '';
  showUserForm = false;
  showBoardForm = false;
  showColumnForm = false;
  taskColumn: Column | null = null;
  userForm = { username: '', email: '', password: '' };
  boardForm = { name: '', description: '' };
  columnForm = { name: '' };
  taskForm = { title: '', description: '', priority: 'Média' };

  constructor(private http: HttpClient) {}

  ngOnInit(): void { this.loadBoards(); }

  get taskCount(): number { return this.activeBoard?.columns.reduce((sum, column) => sum + column.tasks.length, 0) ?? 0; }
  get completedCount(): number {
    return this.activeBoard?.columns.filter(c => /conclu|feito|done/i.test(c.name)).reduce((sum, c) => sum + c.tasks.length, 0) ?? 0;
  }

  saveApiUrl(): void {
    this.apiUrl = this.apiUrl.trim().replace(/\/$/, '');
    localStorage.setItem('taskflow.apiUrl', this.apiUrl);
    this.loadBoards();
  }

  loadBoards(): void {
    this.loading = true; this.error = '';
    this.http.get<Board[]>(`${this.apiUrl}/boards`).subscribe({
      next: boards => {
        this.boards = boards;
        this.activeBoard = boards.find(board => board.id === this.activeBoard?.id) ?? boards[0] ?? null;
        this.loading = false;
      },
      error: error => { this.loading = false; this.handleError(error); }
    });
  }

  createUser(): void {
    this.error = '';
    this.http.post<User>(`${this.apiUrl}/users`, this.userForm).subscribe({
      next: user => { this.user = user; localStorage.setItem('taskflow.user', JSON.stringify(user)); this.showUserForm = false; this.userForm = { username: '', email: '', password: '' }; this.flash('Conta criada. Agora você pode criar seu quadro.'); },
      error: error => this.handleError(error)
    });
  }

  createBoard(): void {
    if (!this.requireUser()) return;
    this.http.post<Board>(`${this.apiUrl}/boards`, { ...this.boardForm, userId: this.user!.id }).subscribe({
      next: board => {
        this.boardForm = { name: '', description: '' }; this.showBoardForm = false;
        const stages = ['A fazer', 'Em andamento', 'Concluído'];
        this.createColumns(board.id, stages, 0, () => this.loadBoards());
      }, error: error => this.handleError(error)
    });
  }

  private createColumns(boardId: number, names: string[], index: number, done: () => void): void {
    if (index >= names.length) { done(); return; }
    this.http.post<Column>(`${this.apiUrl}/columns`, { name: names[index], position: index, boardId }).subscribe({
      next: () => this.createColumns(boardId, names, index + 1, done),
      error: error => { this.handleError(error); done(); }
    });
  }

  addColumn(): void {
    if (!this.activeBoard) return;
    const position = this.activeBoard.columns.length;
    this.http.post<Column>(`${this.apiUrl}/columns`, { name: this.columnForm.name, position, boardId: this.activeBoard.id }).subscribe({
      next: () => { this.showColumnForm = false; this.columnForm.name = ''; this.loadBoards(); this.flash('Coluna adicionada.'); }, error: error => this.handleError(error)
    });
  }

  openTask(column: Column): void { this.taskColumn = column; this.taskForm = { title: '', description: '', priority: 'Média' }; }

  createTask(): void {
    if (!this.taskColumn || !this.requireUser()) return;
    this.http.post<Task>(`${this.apiUrl}/tasks`, { ...this.taskForm, columnId: this.taskColumn.id, userId: this.user!.id }).subscribe({
      next: () => { this.taskColumn = null; this.loadBoards(); this.flash('Tarefa criada.'); }, error: error => this.handleError(error)
    });
  }

  deleteTask(task: Task): void {
    if (!confirm(`Excluir “${task.title}”?`)) return;
    this.http.delete(`${this.apiUrl}/tasks/${task.id}`).subscribe({ next: () => { this.loadBoards(); this.flash('Tarefa excluída.'); }, error: error => this.handleError(error) });
  }

  priorityClass(priority: string): string {
    const value = priority.toLowerCase();
    if (value.includes('alta') || value.includes('high')) return 'high';
    if (value.includes('baixa') || value.includes('low')) return 'low';
    return 'medium';
  }

  private requireUser(): boolean {
    if (this.user) return true;
    this.showUserForm = true; this.error = 'Crie seu usuário primeiro para vincular as tarefas.'; return false;
  }

  private readUser(): User | null {
    try { const value = localStorage.getItem('taskflow.user'); return value ? JSON.parse(value) as User : null; } catch { return null; }
  }

  private handleError(error: HttpErrorResponse): void {
    const body = error.error;
    this.error = typeof body === 'string' ? body : body?.message || body?.error || (error.status === 0 ? 'Não foi possível conectar à API. Confira se ela está rodando e se o endereço está correto.' : `A solicitação falhou (${error.status}).`);
  }

  private flash(message: string): void { this.notice = message; setTimeout(() => this.notice = '', 3200); }
}
