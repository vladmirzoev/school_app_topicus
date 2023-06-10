package somtoday.registrations.api;
import jakarta.servlet.http.HttpServlet;

/**
 * Invoked the moment the export button of a registration. The ID of the registration will be taken and used for an
 * SQL query.
 *
 * The servlet should return a response in the form of the registration compiled as a file in either PDF or a separate
 * webpage.
 */
public class ExportRegistrationServlet extends HttpServlet {
}
