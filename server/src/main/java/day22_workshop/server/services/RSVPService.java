package day22_workshop.server.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import day22_workshop.server.models.Count;
import day22_workshop.server.models.RSVP;
import day22_workshop.server.repositories.RSVPRepository;

@Service
public class RSVPService {
    
    @Autowired
    private RSVPRepository rsvpRepo;

    public List<RSVP> getAllRsvps() {
        return rsvpRepo.getAllRsvps();
    }

    public List<RSVP> getRsvpsByName (String name) {
        return rsvpRepo.getRsvpsByName(name);
    }

    public void insertRsvp(String rsvp_id, String name, String email, 
        String phone, Date date, String comments) {

        try {
            boolean insert = rsvpRepo.insertRsvp(rsvp_id, name, email, phone, date, comments);
            if (insert != true) 
                throw new IllegalArgumentException("Unable to add rsvp!");
            } catch (Exception ex) {
                ex.getStackTrace();
                throw ex;
            }
    }

    public Optional<RSVP> getRsvpById(String rsvp_id) {

        Optional<RSVP> opt = rsvpRepo.getRsvpById(rsvp_id);
        if (opt.isEmpty())
            return Optional.empty();
        RSVP rsvp = opt.get();
        return Optional.of(rsvp);
    }

    @Transactional
    public void updateRsvp(String rsvp_id, String name, String email, 
        String phone, Date date, String comments) {

        Optional<RSVP> opt = rsvpRepo.getRsvpById(rsvp_id);
        if (opt.isEmpty())
            throw new IllegalArgumentException("RSVP does not exist!");      

        try {
            boolean update = rsvpRepo.updateRsvp(rsvp_id, name, email, phone, date, comments);
            if (update != true) 
                throw new IllegalArgumentException("Unable to update RSVP!");
            } catch (Exception ex) {
                ex.getStackTrace();
                throw ex;
            }
    }

    public Optional<Count> getNumberOfRsvp() {

        Optional<Count> opt = rsvpRepo.getNumberOfRsvp();
        if (opt.isEmpty())
            return Optional.empty();
        Count count = opt.get();
        return Optional.of(count);
    }
    
    
}
