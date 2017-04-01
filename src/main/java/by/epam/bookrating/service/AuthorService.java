package by.epam.bookrating.service;
import by.epam.bookrating.dao.AuthorDAO;
import by.epam.bookrating.dao.impl.MySQLAuthorDAO;
import by.epam.bookrating.entity.Author;
import by.epam.bookrating.dao.DAOException;
import by.epam.bookrating.service.util.ImageLogic;
import org.apache.log4j.Logger;
import javax.servlet.http.Part;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p> Class contains methods for for processing data
 * received from controller layer, concerning with authors. </p>
 * @author anyab
 */
public class AuthorService {
    private static final String FULL_NAME = "fullName";
    private static final String BIRTH_YEAR = "birthYear";
    private static final String BIRTH_COUNTRY = "birthCountry";
    private static final String BIOGRAPHY = "biography";
    private static final String AUTHOR_ID = "authorId";
    private static final String OLD_IMAGE_URL = "oldImage";
    private static final String IMG_DIRECTORY_PATH = "img/";
    private static final int AUTHOR_NOT_FOUND = 0;

    private AuthorDAO authorDao;
    private AuthorService() {
        authorDao = MySQLAuthorDAO.getInstance();
    }
    private final static AuthorService instance = new AuthorService();
    public static AuthorService getInstance() {
        return instance;
    }
    private static Logger logger = Logger.getLogger(AuthorService.class);

    public boolean addAuthor(Map<String, String> parameters, Part imagePart, String absoluteDiskPath)
            throws ServiceException {
        try {
            logger.info("Checking if author with such name already exists...");
            String fullName = parameters.get(FULL_NAME);
            if (findAuthorIdByFullName(fullName)==AUTHOR_NOT_FOUND){
                String imageUrl = ImageLogic.addImage(imagePart, absoluteDiskPath);
                authorDao.addAuthor(parameters.get(FULL_NAME),
                        Integer.parseInt(parameters.get(BIRTH_YEAR)),
                        parameters.get(BIRTH_COUNTRY), parameters.get(BIOGRAPHY),imageUrl);
                logger.info("Author is successfully added.");
                return true;
            } else {
                logger.info("Cannot add author.");
                return false;
            }
        } catch (DAOException e) {
            throw new ServiceException("Exception occurred in addAuthor method.", e);
        }
    }

    public long addBasicAuthorInfo(String fullName) throws ServiceException {
        try {
            return authorDao.addBasicAuthorInfo(fullName);
        } catch (DAOException e) {
            throw new ServiceException("Exception occurred in addBasicAuthorInfo method.", e);
        }
    }

    public Author findAuthorById(long authorId) throws ServiceException {
        Author currentAuthor;
        try{
            currentAuthor = authorDao.findAuthorById(authorId);
            logger.info("Sending author with id = "+ authorId + " to the command.");
        } catch (DAOException e){
            throw new ServiceException("Exception is occurred in this method.", e);
        }
        return currentAuthor;
    }

    public long findAuthorIdByFullName(String fullName) throws ServiceException {
        Author currentAuthor;
        try{
            currentAuthor = authorDao.isAuthorExists(fullName);
            if (currentAuthor!=null){
                logger.info("Author founded. Id ="+currentAuthor.getAuthorId());
                return currentAuthor.getAuthorId();
            } else{
                logger.info("Author not found.");
                return AUTHOR_NOT_FOUND;
            }
        } catch (DAOException e){
            throw new ServiceException("Exception is occurred in ths method.", e);
        }
    }

    public void editAuthor(HashMap<String, String> parameters, Part imgPart, String absoluteDiskPath)
            throws ServiceException {
        try {
            String fileName = ImageLogic.getFileName(imgPart);
            String realImageURL = IMG_DIRECTORY_PATH + fileName;
            String imageUrl;
            if (!realImageURL.equals(IMG_DIRECTORY_PATH)) {
                imageUrl = ImageLogic.addImage(imgPart, absoluteDiskPath);
            } else {
                imageUrl =  parameters.get(OLD_IMAGE_URL);
            }
            logger.info("New image url " + imageUrl);
            int authorId = Integer.parseInt(parameters.get(AUTHOR_ID));
            logger.info("Trying to update author item with id = " +authorId );
            authorDao.updateAuthorInfo(parameters.get(FULL_NAME),
                    Integer.parseInt(parameters.get(BIRTH_YEAR)),
                    parameters.get(BIRTH_COUNTRY), parameters.get(BIOGRAPHY), imageUrl, authorId);
        } catch (DAOException e){
            throw new ServiceException("Service Exception is occurred in ths method.", e);
        }
    }

    public List<Author> findAuthorsByBookId(long bookId) throws ServiceException {
        try{
            return authorDao.findAuthorsByBookId(bookId);
        } catch (DAOException e){
            throw new ServiceException("Exception in findAuthorsListByBookId, Service layer",e);
        }
    }
}
