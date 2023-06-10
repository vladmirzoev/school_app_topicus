package somtoday.registrations.api;
import jakarta.servlet.http.HttpServlet;

/**
 * Invoked the moment the user lands on the homepage or is looking at a specific registraiton. All relevant details of
 * the registration is fetched using the getRegistrationSQL class, which will take in the user ID and display all
 * registrations that concern them (either the children of the parent, or the registrations of a school admin's school).
 *
 * The response will be written on the webpage the user is looking at, which will be displayed differently depending
 * on the current webpage. The overview of registrations will only show basic information such as name and registration
 * ID, while a specific single registration will show all information of the registration.
 */
public class FetchRegistrationServlet extends HttpServlet {
}
