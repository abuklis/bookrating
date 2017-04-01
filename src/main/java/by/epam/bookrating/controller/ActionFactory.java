package by.epam.bookrating.controller;

import by.epam.bookrating.command.Command;
import by.epam.bookrating.command.CommandEnum;
import by.epam.bookrating.command.impl.EmptyCommand;
import by.epam.bookrating.command.ParameterConstant;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

class ActionFactory {
    private static final String PARAMETER_COMMAND = "command";
    private static Logger logger = Logger.getLogger(ActionFactory.class);

    Command defineCommand(HttpServletRequest request) {
        Command currentCommand = EmptyCommand.getInstance();
        String action = request.getParameter(PARAMETER_COMMAND);
        logger.info("Received action : "+ action);
        if (action == null || action.isEmpty()) {
            return currentCommand;
        }
        try {
            CommandEnum currentAction = CommandEnum.valueOf(action.toUpperCase());
            currentCommand = currentAction.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute(ParameterConstant.ERROR_MESSAGE, "Wrong command received.");
            //todo на страницу с ошибкой и сообщение
        }
        return currentCommand;
    }
}
