import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by will on 14-12-20.
 */
public class GoodsAnaly {
    static final Pattern PP = Pattern.compile("save[\\w\\W]+?[$]?([0-9]+.[0-9]{2})");

    public static void main(String[] args) {
        IOUtils.readFile("/home/will/data/GOODS.tsv", new LineHandler() {
            @Override
            public void parse(String line) {
                String[] s=line.split("\t");
//                System.out.println(s[10]);
                if (null != s[10] && s[10].trim().length() > 0) {
                    String ss =s[10];
                    Matcher m = PP.matcher(s[10].toLowerCase());
                    if (m.find()) {
//                        System.out.println(ss + "    " + m.group(1));
                    } else {
                        System.out.println(s[10]);
                    }
                }

            }
        });
    }
}
