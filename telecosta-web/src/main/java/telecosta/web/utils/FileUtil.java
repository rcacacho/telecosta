package telecosta.web.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author rcacacho
 */
public class FileUtil {

    private static final Logger log = Logger.getLogger(FileUtil.class);

    public static boolean guardarArchivo(InputStream streamArchivo, String nombreArchivo, String ubicacionArchivo) {
        try {
            File targetFolder = new File(ubicacionArchivo + "");
            OutputStream out;
            out = new FileOutputStream(new File(targetFolder, nombreArchivo));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = streamArchivo.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();

        } catch (Exception ex) {
            log.error(ex.getCause());
            log.error(ex.getLocalizedMessage());
        }
        return false;
    }

    public static StreamedContent getStreamedContent(String directorio, String nombreArchivo) {
        StreamedContent file = null;
        try {
            FileInputStream stream = new FileInputStream(directorio + nombreArchivo);
            String[] nombres = nombreArchivo.replace(".", "/").split("/");
            file = new DefaultStreamedContent(stream, getFileType(nombres[nombres.length - 1]), nombreArchivo);
        } catch (FileNotFoundException fnf) {
            log.error("No se encuentra el archivo " + directorio + nombreArchivo);
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
        }
        return file;
    }

    public static String getFileType(String type) {
        String fileType = null;
        if (type.equalsIgnoreCase("pdf")) {
            fileType = "application/pdf";
        } else if (type.equalsIgnoreCase("jpeg") || type.equalsIgnoreCase("jpg")) {
            fileType = "image/jpeg";
        } else if (type.equalsIgnoreCase("png")) {
            fileType = "image/png";
        } else if (type.equalsIgnoreCase("doc") || type.equalsIgnoreCase("docx")) {
            fileType = "application/msword";
        } else if (type.equalsIgnoreCase("xls") || type.equalsIgnoreCase("xlsx")) {
            fileType = "application/vnd.ms-excel";
        } else if (type.equalsIgnoreCase("zip") || type.equalsIgnoreCase("rar")) {
            fileType = "application/zip";
        }
        return fileType;
    }
    
    
}
