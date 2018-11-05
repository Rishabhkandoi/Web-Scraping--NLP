
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FacutlyDetails {
    
    public static void main (String[] args) throws IOException {
        String url = "https://www.niituniversity.in/faculty";
        Document doc = Jsoup.connect(url).get();
        
        //To Extract all elements with class name profileImageSec and extract "a" tag inside it
        Elements link = doc.select(".profileImageSec").select("a");            
        
        for (Element links : link) {
            //System.out.println (links.attr("abs:href"));
            
            //To Extract absolute value of href attribute, i.e. all required links of faculties
            String urlNew = links.attr("abs:href");                            
            
            Document docNew = Jsoup.connect(urlNew).get();
            
            //To Extract class in which faculty details are available and then the table of contents inside it
            Elements linkNew = docNew.select(".fc-details-wrap").select("table");          
            
            //Regex for email id matching
            Matcher ma = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(linkNew.html());
            
            //To find if the faculty belongs to the given department or not
            Matcher mat = Pattern.compile("Computer Science and Engineering").matcher(linkNew.html());
            
            //To filter only those faculties who are from CSE department and whose mail id are given on website
            if (ma.find() && mat.find()) {
                System.out.println (urlNew.split("faculty/")[1]);
                System.out.println(ma.group()+"\n\n");
            }
        }
    }
}
