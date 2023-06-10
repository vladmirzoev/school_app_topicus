package somtoday.registrations.api;
import jakarta.servlet.http.HttpServlet;

/**
 * Invoked the moment a specific form is chosen to be deleted, via. a delete button. Given some unique ID of the form,
 * this ID will then be used as an input parameter for the a method in class deleteRegistrationServlet, which will
 * call relevant SQL commands to delete the entry.
 *
 * The class will then return a response that dictates whether or not deletion was successful.
 */
public class DeleteRegistrationServlet extends HttpServlet {
}
