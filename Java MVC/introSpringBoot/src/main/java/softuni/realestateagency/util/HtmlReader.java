package softuni.realestateagency.util;

import java.io.*;

public class HtmlReader {

    public String readHtmlFile(String html) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(html))
        ));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null){
            sb.append(line).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
