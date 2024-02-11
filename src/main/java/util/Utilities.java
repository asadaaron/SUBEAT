package util;

import exception.exceptionHandler;
import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {

  // Regular expression to match sentences ending with "*"
  //private final static String REGEX_SENTENCE_ENDED_WITH_ASTERISK = "[^?!]+\\*";
  private final static String REGEX_SENTENCE_ENDED_WITH_ASTERISK = "[^!]+\\*";
  private final static String DATE_DETECTOR_REGEX = "\\*\\d{13}+\\*";
  private final static String LATITUDE_LONGITUDE_REGEX = "(-?\\d{1,3}\\.\\d+)";

  public static String numberExtractor(String input, Map<String, String> placeholderMap) {
    final String phoneNumberRegex = "\\b\\w*\\d+\\w*\\b";

    return detectPhoneNumberPattern(phoneNumberRegex, input, placeholderMap);

  }

  public static String urlExtractor(String input, Map<String, String> placeholderMap) {
    final String URL_REGEX = "([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:\\/~+#-]*[\\w@?^=%&\\/~+#-])";
    return detectUrlPattern(URL_REGEX, input, placeholderMap);
  }
  public static String latitudeLongitudeExtraction(String input, Map<String, String> placeholderMap){
    return detectLatitudeLongitude(LATITUDE_LONGITUDE_REGEX, input, placeholderMap);
  }

  public static String removeStars(String input) {
    if (input != null) {
      return input.replaceAll("\\*", "");
    }
    return new String();

  }

  public static String toDate(String dateTime) {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      Date date = new Date(Long.parseLong(dateTime));
      return date.toInstant()
          .atZone(ZoneId.systemDefault())
          .toLocalDateTime().format(formatter);
    } catch (NumberFormatException e) {
      throw new RuntimeException(e);
    }
  }

  private static String detectPhoneNumberPattern(String regex, String input,
      Map<String, String> placeholderMap) {
    // Compile the regular expression pattern
    StringBuffer result = new StringBuffer();
    if(input == null)
      return result.toString();
    Matcher matcher = getMatcher(regex, input);
    while (matcher.find()) {
      String match = matcher.group();
      if (!isNumber(match)) {
        return input;
      }
      String placeholder = updatePlaceholderIfNotAvailable(placeholderMap, match);
      matcher.appendReplacement(result, placeholder);
    }
    if (result.isEmpty()) {
      result.append(input);
    }

    return result.toString();
  }

  private static String detectUrlPattern(String regex, String input,
      Map<String, String> placeholderMap) {
    // Compile the regular expression pattern
    Matcher matcher = getMatcher(regex, input);

    StringBuffer result = new StringBuffer();
    while (matcher.find()) {
      String match = matcher.group();
      String placeholder = updatePlaceholderIfNotAvailable(placeholderMap, match);
      matcher.appendReplacement(result, placeholder);
    }
    if (result.isEmpty()) {
      result.append(input);
    }

    return result.toString();
  }
  private static String detectLatitudeLongitude(String regex, String input,
      Map<String, String> placeholderMap) {
    // Compile the regular expression pattern
    Matcher matcher = getMatcher(regex, input);

    StringBuffer result = new StringBuffer();
    while (matcher.find()) {
      String match = matcher.group();
      String placeholder = updatePlaceholderIfNotAvailable(placeholderMap, match);
      matcher.appendReplacement(result, placeholder);
    }
    if (result.isEmpty()) {
      result.append(input);
    }

    return result.toString();
  }

  public static boolean detectSentenceEndedByAsterisk(String text) {
    //String text = "This is a sentence.* This is another sentence. This is a third sentence.*";
    boolean isMatched = false;

    Pattern pattern = Pattern.compile(REGEX_SENTENCE_ENDED_WITH_ASTERISK);
    Matcher matcher = pattern.matcher(text);

    System.out.println("Sentences ending with '*':");
    while (matcher.find()) {
      isMatched = true;
      System.out.println(matcher.group().trim());
    }
    return isMatched;
  }

  public static boolean detectDate(String text) {
    boolean isMatched = false;

    Pattern pattern = Pattern.compile(DATE_DETECTOR_REGEX);
    Matcher matcher = pattern.matcher(text);

    while (matcher.find()) {
      isMatched = true;
      System.out.println(matcher.group().trim());
    }
    return isMatched;
  }

  private static String updatePlaceholderIfNotAvailable(Map<String, String> placeholderMap,
      String match) {
    String placeholder = placeholderMap.get(match);
    if (placeholder == null || placeholder.isEmpty()) {
      placeholder = generatePlaceholder();
      placeholderMap.put(match, placeholder);
    }
    return placeholder;
  }

  private static Matcher getMatcher(String regex, String input) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(input);
    return matcher;
  }

  private static String generatePlaceholder() {
    Random randomNum = new Random();
    int randomNumber = randomNum.nextInt(1000);
    return "pl_" + randomNumber;
  }

  private static boolean isNumber(String input) {
    try {
      Double.parseDouble(input); // Try to parse the input as a double
      return true; // If successful, it's a valid number
    } catch (NumberFormatException e) {
      return false; // Parsing failed; it's not a valid number
    }
  }

  public static StringBuilder getAnonymizationMapperBuilder(Map<String, String> placeholderMap) {
    StringBuilder anonymizationMapperBuilder = new StringBuilder();
    anonymizationMapperBuilder.append("real_text");
    anonymizationMapperBuilder.append(",");
    anonymizationMapperBuilder.append("anonymize_text");
    anonymizationMapperBuilder.append("\n");
    placeholderMap.entrySet().forEach(placeholder -> {
      anonymizationMapperBuilder.append(placeholder.getKey());
      anonymizationMapperBuilder.append(",");
      anonymizationMapperBuilder.append(placeholder.getValue());
      anonymizationMapperBuilder.append("\n");
    });
    return anonymizationMapperBuilder;
  }

  public static FileLocationBo getFileLocationBo(String extractionToolName,
      String inputFileLocation, String outputFileName, String rootFile, String outputLocation) {

      final String dbUrl = "jdbc:sqlite:" + rootFile + inputFileLocation;
      final String finalOutputFileName = createFileWithVersion(outputLocation, extractionToolName,
          outputFileName);
      final String OUTPUT_FILE_LOCATION =
          outputLocation + extractionToolName + finalOutputFileName;
      final String anonymizationMapper = outputLocation + "url_anonymization_mapper.csv";

      FileLocationBo fileLocationBo = new FileLocationBo(extractionToolName, dbUrl, anonymizationMapper, OUTPUT_FILE_LOCATION);

    return fileLocationBo;
  }

  public static String createFileWithVersion(String baseOutputFileLocation, String extractionToolName, String baseFileName) {
    int version = 1;
    String fileName = baseFileName;

    while (fileExists(baseOutputFileLocation,extractionToolName, fileName)) {
      version++;
      fileName = incrementFileName(baseFileName, version);
    }

    return fileName;
  }

  public static boolean fileExists(String baseOutputFileLocation, String extractionToolName, String fileName) {
    File file = new File(baseOutputFileLocation + extractionToolName + fileName);
    return file.exists();
  }
  public static boolean inputFileExists(String inputFile){
    File file = new File(inputFile);
    return file.exists();
  }

  public static String incrementFileName(String baseFileName, int version) {
    int dotIndex = baseFileName.lastIndexOf('.');
    String fileNameWithoutExtension;
    String fileExtension;

    if (dotIndex != -1) {
      fileNameWithoutExtension = baseFileName.substring(0, dotIndex);
      fileExtension = baseFileName.substring(dotIndex);
    } else {
      fileNameWithoutExtension = baseFileName;
      fileExtension = "";
    }

    return fileNameWithoutExtension + "_v" + version + fileExtension;
  }

  public static String callType(String type) {
    String callType = null;
    switch (type) {
      case "1":
        callType = "incoming";
        break;
      case "2":
        callType = "outgoing";
        break;
      case "3":
        callType = "missed";
        break;
      default:
        callType = "rejected";
    }
    return callType;
  }

}
