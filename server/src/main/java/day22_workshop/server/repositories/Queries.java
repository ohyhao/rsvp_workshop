package day22_workshop.server.repositories;

public interface Queries {

    public static final String SQL_FIND_ALL_RSVP = "select * from rsvp";

    public static final String SQL_FIND_RSVP_BY_ID = "select * from rsvp where rsvp_id = ?";

    public static final String SQL_UPDATE_RSVP_BY_ID = "update rsvp set name = ?, email = ?, phone = ?, c_date = ?, comments = ? where rsvp_id = ?";

    public static final String SQL_SEARCH_RSVP = "select * from rsvp where name like ?";

    public static final String SQL_INSERT_RSVP = "insert into rsvp (rsvp_id, name, email, phone, c_date, comments) values (?, ?, ?, ?, ?, ?)";

    public static final String SQL_COUNT_RSVP = "select count(distinct name) as count from rsvp";
    
}
