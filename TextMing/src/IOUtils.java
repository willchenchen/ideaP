import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by will on 14-12-20.
 */
public class IOUtils {

    public static void readFile(String filePath, LineHandler handler) {
        BufferedReader r = null;
        try {
            r = new BufferedReader(new FileReader(filePath));
            String line;
            while(null != (line = r.readLine())) {
                handler.parse(line);
            }
        }catch (IOException ex) {
            ex.printStackTrace();
        }finally {
            try {
                r.close();
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }
}
