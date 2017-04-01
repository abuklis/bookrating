package by.epam.bookrating.service.util;
import org.apache.log4j.Logger;

import javax.servlet.http.Part;
import java.io.*;

/**
 * Class contains methods needed for uploading images
 * @author anyab
 */

public class ImageLogic {
    private static Logger logger = Logger.getLogger(ImageLogic.class);
    private static final String DIRECTORY_NAME =  "img";
    private static final int BYTE_SIZE = 1024;

    public static String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    /**
     * <p>This method saves image to a local folder and returns a file name
     * (which will be added to a database).</p>
     *
     * @param imgPart is necessary for determining the file name
     * @return file name
     * @see IOException
     */
    public static String addImage(Part imgPart, String absolutePath) {
        final String fileName = getFileName(imgPart);
        final String pathToImgDir = absolutePath + DIRECTORY_NAME;
        File directory = new File(pathToImgDir);
        if (!directory.exists()){
            directory.mkdir();
        }
        String imageUrl = DIRECTORY_NAME + File.separator + fileName;
        try (OutputStream out = new FileOutputStream(new File(pathToImgDir + "/" + fileName));
             InputStream fileContent = imgPart.getInputStream()) {
            logger.info("Try to copy image to a file " + pathToImgDir +  "/" + fileName);
            int read;
            final byte[] bytes = new byte[BYTE_SIZE];
            while ((read = fileContent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (IOException e){
            logger.warn("Error during writing image.");
        }
        return imageUrl;
    }
}
