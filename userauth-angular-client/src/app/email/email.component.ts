import { Component, OnInit, ViewChild } from '@angular/core';
import { EmailnotifyService } from '../emailnotify.service';
import { Email } from './Email';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
})
export class EmailComponent  implements OnInit {
  public sessionStorage = sessionStorage;
  emails: Email[] = [];
  displayedColumns = ['id', 'fromEmail', 'toEmail', 'uploadUser', 'uploadDate', 'fileName'];
  dataSource: MatTableDataSource<Email> = new MatTableDataSource();
  @ViewChild(MatSort) sort: MatSort = new MatSort();
  toEmail: string='';
  fromEmail: string='';
  file: File | undefined;
  
  
  constructor(private emailnotifyService: EmailnotifyService){
    this.dataSource = new MatTableDataSource(this.emails);
  }

  ngOnInit(): void {
    this.getAllSentEmails();
    console.log('ngOnInit - this.emails ', this.emails);
    this.dataSource = new MatTableDataSource(this.emails);
    
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
  }

  applyFilter(event: Event) {
    if(event && event.target){
      let filterValue = (event.target as HTMLTextAreaElement).value.trim(); // Remove whitespace
      filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
      this.dataSource.filter = filterValue;
    }
  }

  onFilechange(event: any) {
    console.log(event.target.files[0])
    this.file = event.target.files[0]
  }

  upload() {
    if (this.file) {
      this.emailnotifyService.sendEmailWithAttachment(this.file, this.fromEmail, this.toEmail).subscribe(resp => {
        alert("Email with attachment sent successfully!");
        this.getAllSentEmails();
      })
    } else {
      alert("Attach a file!")
    }
  }

  getAllSentEmails(): void {
    this.emailnotifyService.getAllSentEmails()
    .subscribe(emails => {
      this.emails = emails;
      this.dataSource = new MatTableDataSource(this.emails);
      this.dataSource.sort = this.sort;
    });
  }
}
