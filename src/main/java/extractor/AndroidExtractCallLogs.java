package extractor;

import converter.CSVConverter;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import util.Utilities;

public class AndroidExtractCallLogs {
  final String inputLoc;
  final String outputLoc;
  CSVConverter csvConverter;
  Map<String, String> placeholderMap;

  public AndroidExtractCallLogs(String inputLoc, String outputLoc, CSVConverter csvConverter, Map<String, String> placeholderMap) {
    this.inputLoc = inputLoc;
    this.outputLoc = outputLoc;
    this.csvConverter = csvConverter;
    this.placeholderMap = placeholderMap;
  }
  public void extract(){
    CSVConverter csvConverter = new CSVConverter();
    Long startTime = System.nanoTime();
    List<String[]> callLogsData = csvConverter.csvReader(inputLoc);
    Long elapsedTime = System.nanoTime() - startTime;
    System.out.println("Call logs extraction Time in android: "+ TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS));
    StringBuilder stringBuilder = getStringBuilder(callLogsData, placeholderMap);
    csvConverter.convertListToCsv(stringBuilder, outputLoc);
  }

  private StringBuilder getStringBuilder(List<String[]> resultSet, Map<String, String> placeholderMap)  {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("caller");
    stringBuilder.append(",");
    stringBuilder.append("receiver");
    stringBuilder.append(",");
    stringBuilder.append("type");
    stringBuilder.append(",");
    stringBuilder.append("date_time");
    stringBuilder.append("\n");

    for (String[] row : resultSet) {
      // Assuming the CSV has 4 columns
      if (row.length >= 4) {
        String caller = Utilities.callType(Utilities.removeStars(row[5])).equals("outgoing")? "user" : Utilities.numberExtractor(Utilities.removeStars(row[0]), placeholderMap);
        String receiver = Utilities.callType(Utilities.removeStars(row[5])).equals("outgoing")? Utilities.numberExtractor(Utilities.removeStars(row[0]), placeholderMap):"user";
        String dateTime = Utilities.toDate(Utilities.removeStars(row[3]));
        String callType = Utilities.callType(Utilities.removeStars(row[5]));
        stringBuilder.append(caller);
        stringBuilder.append(",");
        stringBuilder.append(receiver);
        stringBuilder.append(",");
        stringBuilder.append(callType);
        stringBuilder.append(",");
        stringBuilder.append(dateTime);
        stringBuilder.append("\n");



      }
    }

    return stringBuilder;
  }

}
