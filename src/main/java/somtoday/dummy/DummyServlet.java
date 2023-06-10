package somtoday.dummy;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

// TODO make a proper servlet for all the necessary CRUD operations
public class DummyServlet extends HttpServlet {
    @Serial
    public static final long serialVersionUID = 1L;

    public DummyServlet middleman;

    public void init() throws ServletException{
        middleman = new DummyServlet();
    }

    /**
     * Parses requests by extracting necessary data, forwarding it to Java methods. A response will be given as a
     * result of the Java methods, which will be sent back to the client using a PrintWriter that changes the content
     * of the page using HTML.
     *
     * @param request The request coming from the client. We extract information here and forward it to Java methods.
     * @param response The reply given the request from the client. Contains the result which should be sent back.
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
    }
}
