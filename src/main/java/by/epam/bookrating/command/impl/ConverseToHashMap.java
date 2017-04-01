package by.epam.bookrating.command.impl;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Created by anyab on 22.10.16.
 */
public abstract class ConverseToHashMap {
    private static Logger logger = Logger.getLogger(ConverseToHashMap.class);

    public static HashMap<String, String> createParametersMap(Enumeration<String> parameters, HttpServletRequest request){
        HashMap<String, String> toService = new HashMap<>();
        String parameter;
        logger.info("Received parameters:");
        while (parameters.hasMoreElements()){
            parameter = parameters.nextElement();
            logger.info("   parameter: " + parameter + " = " + request.getParameter(parameter));
            toService.put(parameter, request.getParameter(parameter));
        }
        return toService;
    }
}
