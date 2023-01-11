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
  rsvp!: RSVP
  update!: string
  id: boolean = false;

  constructor(private fb: FormBuilder, private rsvpSvc: RSVPService) { }

  ngOnInit(): void {

    this.update = localStorage.getItem("update") || "false"
    if (this.update == 'true') {
      this.id = true
      this.rsvp = JSON.parse(localStorage.getItem("user") || '{}');

      this.rsvpForm = this.fb.group({
        // rsvp_id: this.fb.control<string>(this.rsvp.rsvp_id),
        name: this.fb.control<string>(this.rsvp.name),
        email: this.fb.control<string>(this.rsvp.email, [ Validators.required, Validators.email ]),
        phone: this.fb.control<string>(this.rsvp.phone, [ Validators.required, Validators.minLength(8) ]),
        date: this.fb.control<Date>(this.rsvp.date, [ Validators.required ]),
        comments: this.fb.control<string>(this.rsvp.comments, [ Validators.required ])
      })

      localStorage.removeItem("user")
      localStorage.removeItem("update")
    } else {
      this.rsvpForm = this.fb.group({
        rsvp_id: this.fb.control<string>(''),
        name: this.fb.control<string>('', [ Validators.required ]),
        email: this.fb.control<string>('', [ Validators.required, Validators.email ]),
        phone: this.fb.control<string>('', [ Validators.required, Validators.minLength(8) ]),
        date: this.fb.control<Date>(new Date, [ Validators.required ]),
        comments: this.fb.control<string>('', [ Validators.required ])
      })
    }
  }

  processForm() {
    const RSVP = this.rsvpForm.value as RSVP
    console.log('>>> rsvpForm: ', RSVP)
    this.rsvpSvc.insertRSVP(RSVP)
      .then(result => {
        alert(result.message)
        this.ngOnInit()
        console.log('RSVP inserted/updated')
      }).catch(error => {
        console.log(error)
      })
  }

}
