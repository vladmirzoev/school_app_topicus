package somtoday.registrations.sql;

/**
 * Fetches a registration from the database to be displayed in the current webpage, and is the READ function of the
 * CRUD operations. This class will find registrations depending on the ID of the user and separate the contents of
 * the SQL SELECT into different values, which will be written on the HTML page by using a PrintWriter, which is used
 * in the getRegistrationServlet.
 *
 * If the userID is a school admin, then all registrations concerning their school will be displayed. Otherwise, if the
 * userID is a parent, then all registration concerning their children will be displayed.
 *
 * A check will be run if the fetched entries matches the ID of the website user.
 */
public class GetRegistrationSQL {
}
