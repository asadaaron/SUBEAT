package anonymisation;

import ner.NameFinder;

import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Anonymize {
  public String anonymizer(String input, Map<String, String> placeholderMap) throws IOException {
    NameFinder nameFinder = new NameFinder();
    StringBuilder nameRegex = nameFinder.nameFinderMethod(input);
    final String WEEKDAYS_MONTHS_REGEX = "\\b((?i)Sunday|Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|January|February|March|April|May|June|July|August|September|October|November|December)\\b";
    final String DATE_REGEX = "\\b(?:\\d{4}[./-]\\d{1,2}[./-]\\d{1,2}|\\d{1,2}[./-]\\d{1,2}[./-]\\d{4}|\\d{4}-\\d{2}-\\d{2}|\\d{2}-\\d{2}-\\d{4}|\\d{1,2} [A-Za-z]{3,} \\d{4}|\\d{4} [A-Za-z]{3,} \\d{1,2}|\\d{1,2} [A-Za-z]{3,} \\d{1,2})\\b";
    final String URL_REGEX = "([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:\\/~+#-]*[\\w@?^=%&\\/~+#-])";
    final String PLACE_REGEX = "\\b\\w+\\b\\s+((?i)street|road|st|rd|avenue|ave|lane|ln|boulevard|blvd|drive|dr|court|ct|place|pl|way|path|circle|circular|square|sq|highway|hwy|route|rte)\\b";
    String regex = new String();
    if(!nameRegex.isEmpty() && !nameRegex.equals("") && !nameRegex.toString().equals("\\b(?)\\b")){
      regex = nameRegex.toString() + "|" + PLACE_REGEX + "|" +WEEKDAYS_MONTHS_REGEX+ "|"+ URL_REGEX + "|" +DATE_REGEX +"|\\b\\d{2}(?: ?\\d+)*$\\b|\\b([0-1]?[0-9]|2[0-3]):[0-5][0-9]\\b|\\b[\\w.+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b|\\b\\d+\\b|\\b[A-Z.\\d.]{2,}\\b";
    }else {
      regex = WEEKDAYS_MONTHS_REGEX+ "|"+ DATE_REGEX + "|"+ PLACE_REGEX + "|" +URL_REGEX +"|\\b\\d{2}(?: ?\\d+)*$\\b|\\b([0-1]?[0-9]|2[0-3]):[0-5][0-9]\\b|\\b[\\w.+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b|\\b\\d+\\b|\\b[A-Z.\\d.]{2,}\\b";
    }

    // Compile the regular expression pattern
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(input);

    StringBuffer result = new StringBuffer();

    // Find and replace alphanumeric words with asterisks
    while (matcher.find()) {
      String match = matcher.group();
      String placeholder = placeholderMap.get(match);
      if(placeholder == null ||placeholder.isEmpty()){
        placeholder = generatePlaceholder();
        placeholderMap.put(match, placeholder);
      }
      //String asterisks = "*".repeat(match.length());
      matcher.appendReplacement(result, placeholder);
    }
    matcher.appendTail(result);

    return result.toString();
  }
  private String generatePlaceholder(){
    Random randomNum = new Random();
    int randomNumber = randomNum. nextInt(1000);
    return  "pl_"+ randomNumber;
  }

}
