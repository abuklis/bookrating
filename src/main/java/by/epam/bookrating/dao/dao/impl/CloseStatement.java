package by.epam.bookrating.dao.dao.impl;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by anyab on 27.09.16.
 */
abstract class CloseStatement {
    private static final Logger logger = Logger.getLogger(CloseStatement.class);

    static void closeStatement(Statement statement){
        try {
            if(statement!=null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.warn("Exception is occurred during closing statement", e);
        }
    }
}
