package by.epam.bookrating.controller.controller;

import by.epam.bookrating.controller.command.Command;
import by.epam.bookrating.controller.constant.PageConstant;
import by.epam.bookrating.controller.exception.CommandException;
import by.epam.bookrating.dao.connection.impl.ConnectionPool;
import by.epam.bookrating.dao.exception.ConnectionPoolException;
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
    private static final Logger logger = Logger.getLogger(FrontController.class);
    //todo properties файл для логгера
    //todo сделать первую команда инита
    public void init(){
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (ServletException | IOException | CommandException e) {
            logger.error("Exception in doGet method.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (ServletException | IOException | CommandException e) {
            logger.error("Exception in doPost method." ,e);
        }
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, CommandException {
        String page;
        ActionFactory client = new ActionFactory();
        Command command = client.defineCommand(request);
        try {
            logger.info("----------------------------");
            page = command.execute(request);
            logger.info("----------------------------");
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
            //// TODO: 18.02.2017 Поправить destroy
        } catch (ConnectionPoolException e){
            logger.error("Exception occurred during destroying ConnectionPool in FC", e);
        }
    }
}
