package by.epam.bookrating.controller;

import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.PageConstant;
import by.epam.bookrating.command.CommandException;
import by.epam.bookrating.pool.impl.ConnectionPool;
import by.epam.bookrating.pool.ConnectionPoolException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Class {@code FrontController} is the class, that extends {@code HttpServlet} class to
 * deal with get and post-requests.
 * @author Anna Buklis
 */

@WebServlet("/controller")
@MultipartConfig
public class FrontController extends HttpServlet {
    private static Logger logger = Logger.getLogger(FrontController.class);
    //todo properties файл для логгера
    //todo сделать первую команда инита

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page;
        ActionFactory client = new ActionFactory();
        Command command = client.defineCommand(request);
        try {
            page = command.execute(request);
        } catch (CommandException e) {
            logger.trace(e);
            page = PageConstant.ERROR_PAGE;
        }
        logger.info("Forwarding to the page " + page);
        request.getServletContext().getRequestDispatcher(page).forward(request, response);
//        response.sendRedirect(page);  //todo разделить на get и post для защиты от F5
    }

    @Override
    public void destroy(){
        try{
            ConnectionPool.getInstance().destroyConnectionPool();
            //// TODO: 28.03.2017 сделать дерегистрацию драйвера
        } catch (ConnectionPoolException e){
            logger.error("Exception occurred during destroying ConnectionPool in FC", e);
        }
    }
}
