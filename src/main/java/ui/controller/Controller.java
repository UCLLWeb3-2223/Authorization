package ui.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    ControllerFactory controllerFactory = new ControllerFactory();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String destination;
        String action = request.getParameter("action");
        if (action == null || action.isEmpty())
            action = "Home";
        RequestHandler handler = controllerFactory.getController(action);

        // extra info bij de demo
        System.out.println("Pagina: "+action + "; Ingelogde gebruiker: " + request.getSession().getAttribute("user"));

        try {
            destination = handler.handleRequest(request, response);
        } catch (NotAuthorizedException e) {
            // alle handlers gooien een NotAuthorizedException als gebruiker niet de juiste rechten heeft
            // zodat authorization altijd op dezelfde manier afgehandeld wordt
            request.setAttribute("notAuthorized", "You have insufficient rights to have a look at the requested page.");
            destination = "Controller?action=Home";
        }
        if (!response.isCommitted()) {
            request.getRequestDispatcher(destination).forward(request, response);
        }
    }


}
