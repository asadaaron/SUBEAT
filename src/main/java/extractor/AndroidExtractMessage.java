package extractor;

import anonymisation.Anonymize;
import converter.CSVConverter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import util.Utilities;

public class AndroidExtractMessage {
  final String inputLoc;
  final String outputLoc;
  CSVConverter csvConverter;
  Map<String, String> placeholderMap;

  public AndroidExtractMessage(String inputLoc, String outputLoc, CSVConverter csvConverter, Map<String, String> placeholderMap) {
    this.inputLoc = inputLoc;
    this.outputLoc = outputLoc;
    this.csvConverter = csvConverter;
    this.placeholderMap = placeholderMap;
  }

  public void extract() {
    CSVConverter csvConverter = new CSVConverter();
    Long startTime = System.nanoTime();
    List<String[]> messages = csvConverter.csvReader(inputLoc);
    Long elapsedTime = System.nanoTime() - startTime;
    System.out.println("Phone message extraction Time in android: "+ TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS));
    StringBuilder stringBuilder = getStringBuilder(messages, placeholderMap);
    csvConverter.convertListToCsv(stringBuilder, outputLoc);
  }

  private StringBuilder getStringBuilder(List<String[]> resultSet, Map<String, String> placeholderMap) {
    Anonymize anonymize = new Anonymize();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Source");
    stringBuilder.append(",");
    stringBuilder.append("Date");
    stringBuilder.append(",");
    stringBuilder.append("partner");
    stringBuilder.append(",");
    stringBuilder.append("Text");
    stringBuilder.append(",");
    stringBuilder.append("No of words");
    stringBuilder.append("\n");
    String tempMessage = null;
    String tempSource = null;
    String tempPartner = null;
    String source = "mobile_chat";
    for (String[] row : resultSet) {
      if (tempMessage == null) {
        StringBuilder text = new StringBuilder();

          String date = null;
          String partner = Utilities.removeStars(row[0]);
          int i = 1;
          for (; i < row.length; i++) {
            text.append(row[i]);
            if (i == row.length-1 && !Utilities.detectSentenceEndedByAsterisk(row[i])) {
              tempMessage = text.toString()+" ";
              tempSource = source;
              tempPartner = partner;

            }
            if (Utilities.detectSentenceEndedByAsterisk(row[i])) {
              break;
            }
          }
          for(int dateIndex = 0; dateIndex < row.length; dateIndex ++ ){
            if(Utilities.detectDate(row[dateIndex])){
              date = row[dateIndex];
            }

          }
          if(tempMessage == null){


            stringBuilder.append(source);
            stringBuilder.append(",");
            stringBuilder.append(Utilities.toDate(Utilities.removeStars(date)));
            stringBuilder.append(",");
            stringBuilder.append(Utilities.numberExtractor(partner,placeholderMap));
            stringBuilder.append(",");
            try {
              String smsWithoutStar = text.toString() != null ? Utilities.removeStars(text.toString()): new String();
              String sms = anonymize.anonymizer(smsWithoutStar.replace("\n", " ").replace("\r", "").replace(",",";"), placeholderMap);
              stringBuilder.append(sms);
              stringBuilder.append(",");
              if(!sms.isEmpty()){
                StringTokenizer tokens = new StringTokenizer(sms);
                stringBuilder.append(tokens.countTokens());
              }else{
                stringBuilder.append(0);
              }
            } catch (IOException e) {
              throw new RuntimeException(e);
            }

            stringBuilder.append("\n");
          }



      } else {
        StringBuilder text = new StringBuilder();
        boolean foundRow = false;

          String partner = tempPartner;
          String date = null;
          int i = 0;
          text.append(tempMessage);
          for (; i < row.length; i++) {
            text.append(row[i]);

            if (i == row.length-1 && !Utilities.detectSentenceEndedByAsterisk(row[i])) {
              tempMessage = text.toString()+" ";
              tempSource = source;
              tempPartner = partner;

            }
            if (Utilities.detectSentenceEndedByAsterisk(row[i])) {
              foundRow = true;
              break;
            }
          }
          for(int dateIndex = 0; dateIndex < row.length; dateIndex ++ ){
            if(Utilities.detectDate(row[dateIndex])){
              date = row[dateIndex];
            }

          }
          //String date = Utilities.toDate(Utilities.removeStars(row[i + 2]));
        if(foundRow){
          stringBuilder.append(source);
          stringBuilder.append(",");
          stringBuilder.append(Utilities.toDate(Utilities.removeStars(date)));
          stringBuilder.append(",");
          stringBuilder.append(Utilities.numberExtractor(partner,placeholderMap));
          stringBuilder.append(",");
          try {
            String smsWithoutStar = text.toString() != null ? Utilities.removeStars(text.toString()): new String();
            String sms = anonymize.anonymizer(smsWithoutStar.replace("\n", " ").replace("\r", "").replace(",",";"), placeholderMap);
            stringBuilder.append(sms);
            stringBuilder.append(",");
            if(!sms.isEmpty()){
              StringTokenizer tokens = new StringTokenizer(sms);
              stringBuilder.append(tokens.countTokens());
            }else{
              stringBuilder.append(0);
            }
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
          stringBuilder.append("\n");
          tempMessage = null;
        }

      }

    }

    return stringBuilder;
  }

}
