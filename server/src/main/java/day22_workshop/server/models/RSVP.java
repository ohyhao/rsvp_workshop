package day22_workshop.server.models;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class RSVP {

    private String rsvp_id;
    private String name;
    private String email;
    private String phone;
    private Date date;
    private String comments;

    public String getRsvp_id() { return rsvp_id; }
    public void setRsvp_id(String rsvp_id) { this.rsvp_id = rsvp_id;}
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public static RSVP convert(SqlRowSet rs) {

        RSVP rsvp = new RSVP();
        rsvp.setRsvp_id(rs.getString("rsvp_id"));
        rsvp.setName(rs.getString("name"));
        rsvp.setEmail(rs.getString("email"));
        rsvp.setPhone(rs.getString("phone"));
        rsvp.setDate(rs.getDate("c_date"));
        rsvp.setComments(rs.getString("comments"));
        return rsvp;
    }

    public static RSVP create(String json) {

        RSVP rsvp = new RSVP();

        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject data = reader.readObject();

        // String rsvp_id = UUID.randomUUID().toString().substring(0, 8);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // if (data.getString("id").equals("")) {
        //     rsvp.setRsvp_id(rsvp_id);
        // } else {
        //     rsvp.setRsvp_id(data.getString("id"));
        // }
        rsvp.setRsvp_id(data.getString("rsvp_id"));
        rsvp.setName(data.getString("name"));
        rsvp.setEmail(data.getString("email"));
        try {
            rsvp.setDate(sdf.parse(data.getString("date")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        rsvp.setPhone(data.getString("phone"));
        rsvp.setComments(data.getString("comments"));

        return rsvp;
    }

    public static JsonObject toJson(RSVP rsvp) {
        return Json.createObjectBuilder()
            .add("rsvp_id", rsvp.getRsvp_id())
            .add("name", rsvp.getName())
            .add("email", rsvp.getEmail())
            .add("phone", rsvp.getPhone())
            .add("date", rsvp.getDate().toString())
            .add("comments", rsvp.getComments())
            .build();
    }

    
    
}
