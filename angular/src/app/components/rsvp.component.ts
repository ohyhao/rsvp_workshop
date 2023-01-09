import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RSVP } from 'src/models';
import { RSVPService } from '../rsvp.service';

@Component({
  selector: 'app-rsvp',
  templateUrl: './rsvp.component.html',
  styleUrls: ['./rsvp.component.css']
})
export class RsvpComponent implements OnInit {

  rsvpForm!: FormGroup

  constructor(private fb: FormBuilder, private rsvpSvc: RSVPService) { }

  ngOnInit(): void {
    this.rsvpForm = this.fb.group({
      rsvp_id: this.fb.control<string>(''),
      name: this.fb.control<string>('', [ Validators.required ]),
      email: this.fb.control<string>('', [ Validators.required, Validators.email ]),
      phone: this.fb.control<string>('', [ Validators.required, Validators.minLength(8) ]),
      date: this.fb.control<Date>(new Date, [ Validators.required ]),
      comments: this.fb.control<string>('', [ Validators.required ])
    })
  }

  processForm() {
    const RSVP = this.rsvpForm.value as RSVP
    console.log('>>> rsvpForm: ', RSVP)
    this.rsvpSvc.insertRSVP(RSVP)
      .then(result => {
        alert(result.message)
        console.log('RSVP inserted/updated')
      }).catch(error => {
        console.log(error)
      })
  }

}
