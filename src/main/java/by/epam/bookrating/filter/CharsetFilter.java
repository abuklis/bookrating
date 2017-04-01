package by.epam.bookrating.filter;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Class {@code CharsetFilter} is the class, that implements {@code Filter} interface to
 * process with different encodings of Russian and English languages.
 * @author Anna Buklis
 */

public class CharsetFilter implements Filter{
    private static final String ENCODING = "UTF-8";
    private static Logger logger = Logger.getLogger(CharsetFilter.class);
    //// TODO: 29.03.2017 что написано в getLogger

    @Override
    public void init(FilterConfig filterConfig){
        logger.info("ContentTypeFilter is initialized.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        req.setCharacterEncoding(ENCODING);
        logger.info(ENCODING + " encoding set.");
        chain.doFilter(req, response);
    }

    @Override
    public void destroy(){}
}