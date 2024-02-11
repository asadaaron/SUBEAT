package extractor;

import converter.CSVConverter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import util.Utilities;

public class AndroidExtractPhotosMetadata {
  final String inputLoc;
  final String outputLoc;
  CSVConverter csvConverter;
  Map<String, String> placeholderMap;

  public AndroidExtractPhotosMetadata(String inputLoc, String outputLoc, CSVConverter csvConverter, Map<String, String> placeholderMap) {
    this.inputLoc = inputLoc;
    this.outputLoc = outputLoc;
    this.csvConverter = csvConverter;
    this.placeholderMap = placeholderMap;
  }
  public void extract(){
    CSVConverter csvConverter = new CSVConverter();
    Long startTime = System.nanoTime();
    List<String[]> photosMetadata = csvConverter.csvReader(inputLoc);
    Long elapsedTime = System.nanoTime() - startTime;
    System.out.println("Photos extraction Time in android: "+ TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS));
    StringBuilder stringBuilder = getStringBuilder(photosMetadata, placeholderMap);
    csvConverter.convertListToCsv(stringBuilder, outputLoc);
  }

  private StringBuilder getStringBuilder(List<String[]> resultSet, Map<String, String> placeholderMap)  {
    StringBuilder stringBuilder = new StringBuilder();
    /*stringBuilder.append("Date Time");
    stringBuilder.append(",");
    stringBuilder.append("Latitude");
    stringBuilder.append(",");
    stringBuilder.append("Longitude");
    stringBuilder.append(",");
    stringBuilder.append("Altitude");
    stringBuilder.append(",");
    stringBuilder.append("File Name");
    stringBuilder.append("\n");*/

    for (String[] row : resultSet) {
      // Assuming the CSV has 4 columns
      if (row.length >= 4) {
        String dateTime = row[0];
        String latitude = Utilities.latitudeLongitudeExtraction(row[3], placeholderMap);
        String longitude = Utilities.latitudeLongitudeExtraction(row[4], placeholderMap);
        String altitude = Utilities.latitudeLongitudeExtraction(row[5], placeholderMap);
        String fileName = row[6];
        stringBuilder.append(dateTime);
        stringBuilder.append(",");
        stringBuilder.append(latitude);
        stringBuilder.append(",");
        stringBuilder.append(longitude);
        stringBuilder.append(",");
        stringBuilder.append(altitude);
        stringBuilder.append(",");
        stringBuilder.append(fileName);
        stringBuilder.append("\n");



      }
    }

    return stringBuilder;
  }

}
