package day22_workshop.server.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import day22_workshop.server.models.Count;
import day22_workshop.server.models.RSVP;
import day22_workshop.server.models.Response;
import day22_workshop.server.services.RSVPService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;

@RestController
@RequestMapping(path = "/api")
public class RSVPRestController {

    @Autowired
    private RSVPService rsvpSvc;
    
    @GetMapping(path = "/rsvps")
    public ResponseEntity<String> getAllRsvps() {

        List<RSVP> rsvps = new LinkedList<>();
        Response resp;

        rsvps = rsvpSvc.getAllRsvps();

        if (rsvps.isEmpty()) {
            resp = new Response();
            resp.setCode(404);
            resp.setMessage("List of RSVPs is empty!");
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(resp.toJson().toString());
        } else {
            JsonArrayBuilder arraybuilder = Json.createArrayBuilder();
            for (RSVP r: rsvps) {
                arraybuilder.add(RSVP.toJson(r));
            }
            // resp = new Response();
            // resp.setCode(200);
            // resp.setMessage("List of RSVPs retrieved successfully");
            // resp.setData(arraybuilder.build().toString());
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(arraybuilder.build().toString());
        }    
    }

    @GetMapping(path = "/rsvp")
    public ResponseEntity<String> getRSVPsByName(@RequestParam String q) {

        List<RSVP> rsvps_result = new LinkedList<>();
        Response resp;

        rsvps_result = rsvpSvc.getRsvpsByName(q);

        if (rsvps_result.isEmpty()) {
            resp = new Response();
            resp.setCode(404);
            resp.setMessage("No RSPVs for %s were found".formatted(q));
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(resp.toJson().toString());
        } else {
            JsonArrayBuilder arraybuilder = Json.createArrayBuilder();
            for (RSVP r: rsvps_result) {
                arraybuilder.add(RSVP.toJson(r));
            }
            // resp = new Response();
            // resp.setCode(200);
            // resp.setMessage("List of RSVPs for %s retrieved successfully".formatted(name));
            // resp.setData(arraybuilder.build().toString());
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(arraybuilder.build().toString());
        }   
           
    }

    @PostMapping(path = "/rsvp")
    public ResponseEntity<String> insertRsvp(@RequestBody String payload) {

        RSVP rsvp;
        Response resp;
        String rsvp_id = UUID.randomUUID().toString().substring(0, 8);

        System.out.println(payload);

        try {
            rsvp = RSVP.create(payload);
        } catch(Exception ex) {
            resp = new Response();
            resp.setCode(400);
            resp.setMessage("Unable to create RSVP from payload");
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resp.toJson().toString());
        }

        if (rsvp.getRsvp_id().equals("")) {
            try {        
                rsvpSvc.insertRsvp(rsvp_id, rsvp.getName(), rsvp.getEmail(), 
                    rsvp.getPhone(), rsvp.getDate(), rsvp.getComments());
                resp = new Response();
                resp.setCode(201);
                resp.setMessage("RSVP added successfully");
                return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(resp.toJson().toString());
            } catch (Exception ex) {
                resp = new Response();
                resp.setCode(400);
                resp.setMessage("Unable to add RSVP");
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(resp.toJson().toString());
            }
        } else {   
            try {
                rsvpSvc.updateRsvp(rsvp.getRsvp_id(), rsvp.getName(), rsvp.getEmail(), 
                    rsvp.getPhone(), rsvp.getDate(), rsvp.getComments());
                resp = new Response();
                resp.setCode(200);
                resp.setMessage("RSVP updated successfully");
                return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(resp.toJson().toString());
            } catch (Exception ex) {
                resp = new Response();
                resp.setCode(400);
                resp.setMessage("Error updating RSVP");
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(resp.toJson().toString());
            }
        }

    }

    @GetMapping(path = "/rsvps/count")
    public ResponseEntity<String> getNumberOfRSVP() {

        Count count;
        Response resp;

        Optional<Count> opt = rsvpSvc.getNumberOfRsvp();

        if (opt.isEmpty()) {
            resp = new Response();
            resp.setCode(400);
            resp.setMessage("Unable to get number of people who has RSVPs!");
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resp.toJson().toString());
        } else {
            count = opt.get();
            // resp = new Response();
            // resp.setCode(201);
            // resp.setMessage("Number of people who has RSVPs successfully retrieved");
            // resp.setData(Count.toJson(count).toString());
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(Count.toJson(count).toString());
        }
    }


    
}
