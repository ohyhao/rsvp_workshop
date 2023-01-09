import { Component, OnInit } from '@angular/core';
import { Count, RSVP } from 'src/models';
import { RSVPService } from '../rsvp.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

  c!: Count
  RSVPs: RSVP[] = []

  constructor(private rsvpSvc: RSVPService) { }

  ngOnInit(): void {
    this.rsvpSvc.getNumberOfRSVP()
      .then(result => {
        this.c = result
        console.log('>>> count: ', result)
      }).catch(error => {
        console.log(error)
      })

    this.rsvpSvc.getRSVPs()
      .then(result => {
        this.RSVPs = result
        console.log('>>> list: ', result)
      }).catch(error => {
        console.log(error)
      })
    
  }

}
