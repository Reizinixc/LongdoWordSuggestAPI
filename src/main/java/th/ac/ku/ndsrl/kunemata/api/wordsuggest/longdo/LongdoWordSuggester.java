package th.ac.ku.ndsrl.kunemata.api.wordsuggest.longdo;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LongdoWordSuggester implements WordSuggester {
   private static final String SUGGEST_API_URL_FORMAT = "http://search.longdo.com/BWTSearch/HeadSearch?json=1&ds=head&num=%d&count=0&key=%s";
   private static final String ENCODING = "UTF-8";
   private static final String DELIMITER = ";";
   private static final Comparator<String> SUGGEST_WORD_SET_POLICY = new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
         return o1.compareToIgnoreCase(o2);
      }
   };
   private int limit;

   public LongdoWordSuggester(int limit) {
      this.limit = limit;
   }

   @Override
   public Set<String> getSuggestWords(String word) {
      Set<String> suggestWordSet = new TreeSet<String>(SUGGEST_WORD_SET_POLICY);
      String stmt;

      if (word.length() > 1) {
         try {
            InputStream inputStream = new URL(getSuggestApiUrl(word)).openStream();

            // Longdo API Suggest word return the suggest words in JavaScript
            // first statement. So let scanner handle content from website.
            Scanner scanner = new Scanner(inputStream, ENCODING).useDelimiter(DELIMITER);
            stmt = scanner.next();

            // We have a suggest words list. Let's close connection!
            scanner.close();

            // Next, we will get word from JSON array
            Matcher matcher = Pattern.compile("\"w\": *\"([^\"]+?)\"").matcher(stmt);

            while (matcher.find()) {
               suggestWordSet.add(matcher.group(1));
            }
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      }

      return suggestWordSet;
   }

   private String getSuggestApiUrl(String suggestWord) {
      try {
         return String.format(SUGGEST_API_URL_FORMAT, limit, URLEncoder.encode(suggestWord, ENCODING));
      } catch (UnsupportedEncodingException e) {
         throw new RuntimeException(e);
      }
   }

}
