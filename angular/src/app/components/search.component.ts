import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RSVP } from 'src/models';
import { RSVPService } from '../rsvp.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  searchForm!: FormGroup
  RSVPs: RSVP[] = [];

  constructor(private fb: FormBuilder, private rsvpSvc: RSVPService) { }

  ngOnInit(): void {
    this.searchForm = this.fb.group({
      q: this.fb.control<string>('', [ Validators.required ])
    })
  }

  processForm() {
    this.rsvpSvc.getRSVPByName(this.searchForm.value.q)
      .then(result => {
        this.RSVPs = result
        console.log('>>> search: ', result)
     }).catch(error => {
      console.log(error)
    })
  }

}
