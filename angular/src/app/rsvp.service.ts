import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { lastValueFrom } from "rxjs";
import { Count, RSVP } from "src/models";

const httpHeaders = {
    headers: new HttpHeaders()
    .set('Content-Type', 'application/json')
    .set('Accept', 'application/json')
}

@Injectable()
export class RSVPService {

    constructor(private http: HttpClient) {}

    insertRSVP(r: RSVP): Promise<any> {

        return lastValueFrom(
            this.http.post<RSVP>('api/rsvp', r, httpHeaders)
        )
    }

    getRSVPs(): Promise<RSVP[]> {

        return lastValueFrom(
            this.http.get<any>('api/rsvps', httpHeaders)
        )
    }

    getNumberOfRSVP(): Promise<Count> {

        return lastValueFrom(
            this.http.get<any>('api/rsvps/count', httpHeaders)
        )
    }

    getRSVPByName(q: string): Promise<RSVP[]> {
        
        const params = new HttpParams()
            .set("q", q)
        
        const headers = new HttpHeaders()
            .set('Content-Type', 'application/json')
            .set('Accept', 'application/json')
        
        return lastValueFrom(
            this.http.get<any>('api/rsvp', {params, headers})
        )
    }
}