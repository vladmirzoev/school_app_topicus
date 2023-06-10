package somtoday.registrations.api;
import jakarta.servlet.http.HttpServlet;

/**
 * Invoked the moment a submit button of a form is pressed. It will take all the named fields' values and use them as
 * input parameters within the createRegistrationSQL class.
 *
 * This servlet will then return a response that dictates whether or not the operation was successful.
 */
public class CreateRegistrationServlet extends HttpServlet {
}
