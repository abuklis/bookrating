package by.epam.bookrating.controller.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Class {@code LocaleFilter} is the class, that implements {@code Filter} interface to
 * deal with changing locale.
 * @author Anna Buklis
 */

public class LocaleFilter implements Filter {
    private static final String ATTR_LOCALE = "locale";
    private static final String EN_LOCALE = "en";
    private static final String RU_LOCALE = "ru";
    private String locale;
    private static final Logger logger = Logger.getLogger(LocaleFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        locale = EN_LOCALE;
        logger.info("LocaleFilter initialized locale = " + locale + ".");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getSession().getAttribute(ATTR_LOCALE) == null) {
            req.getSession().setAttribute(ATTR_LOCALE, locale);
        }
        if (req.getSession().getAttribute(ATTR_LOCALE) == EN_LOCALE) {
            req.getSession().setAttribute(ATTR_LOCALE, EN_LOCALE);
        }
        if (req.getSession().getAttribute(ATTR_LOCALE) == RU_LOCALE) {
            req.getSession().setAttribute(ATTR_LOCALE, RU_LOCALE);
        }
        chain.doFilter(req, response);
        logger.info("The locale " + req.getSession().getAttribute(ATTR_LOCALE) + " set.");
    }

    @Override
    public void destroy() {}
}
