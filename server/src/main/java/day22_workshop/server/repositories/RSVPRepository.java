package day22_workshop.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import day22_workshop.server.models.Count;
import day22_workshop.server.models.RSVP;

import static day22_workshop.server.repositories.Queries.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class RSVPRepository {
    
    @Autowired
    private JdbcTemplate template;

    public List<RSVP> getAllRsvps() {

        List<RSVP> rsvp_list = new LinkedList<>();

        SqlRowSet rs = template.queryForRowSet(SQL_FIND_ALL_RSVP);
        while (rs.next()) {
            RSVP rsvp = RSVP.convert(rs);
            rsvp_list.add(rsvp);
        }
        return rsvp_list;
    }

    public List<RSVP> getRsvpsByName(String name) {

        List<RSVP> rsvp_list_by_name = new LinkedList<>();

        SqlRowSet rs = template.queryForRowSet(SQL_SEARCH_RSVP, "%" + name + "%");
        while (rs.next()) {
            RSVP rsvp = RSVP.convert(rs);
            rsvp_list_by_name.add(rsvp);
        }
        return rsvp_list_by_name;
    }

    public Optional<RSVP> getRsvpById(String rsvp_id) {

        final SqlRowSet rs = template.queryForRowSet(SQL_FIND_RSVP_BY_ID ,rsvp_id);
        if (!rs.next()) {
            return Optional.empty();
        }
        return Optional.of(RSVP.convert(rs));
    }

    public boolean insertRsvp(String rsvp_id, String name, String email, 
        String phone, Date date, String comments) {
        
        // String rsvp_id = UUID.randomUUID().toString().substring(0, 8);

        int count = template.update(SQL_INSERT_RSVP, rsvp_id, name, email, 
            phone, date, comments);
        return 1 == count;
    }

    public boolean updateRsvp(String rsvp_id, String name, String email, 
    String phone, Date date, String comments) {

        int count = template.update(SQL_UPDATE_RSVP_BY_ID, name, email, 
            phone, date, comments, rsvp_id);
        return 1 == count;
    }

    public Optional<Count> getNumberOfRsvp() {

        final SqlRowSet rs = template.queryForRowSet(SQL_COUNT_RSVP);
        while (!rs.next()) {
            return Optional.empty();
        }
        return Optional.of(Count.convert(rs));
    }

}
