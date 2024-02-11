package extractor;

import DBConfig.DBConnection;
import DBConfig.DBProperties;
import converter.CSVConverter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import util.Utilities;

public class ExtractPhotosAndVideosMetadata {

  final String dbUrl;
  final String outputFileLocation;
  CSVConverter csvConverter;
  Map<String, String> placeholderMap;

  public ExtractPhotosAndVideosMetadata(String dbUrl, String outputFileLocation, CSVConverter csvConverter, Map<String, String> placeholderMap) {
    this.dbUrl = dbUrl;
    this.outputFileLocation = outputFileLocation;
    this.csvConverter = csvConverter;
    this.placeholderMap = placeholderMap;
  }

  public void photosVideosExtractor(){
    try  {
      //Class.forName("org.sqlite.JDBC"); // when jdbc connection does not work, then use the line.
      DBProperties dbProperties = new DBProperties(dbUrl);
      DBConnection dbConnection = new DBConnection(dbProperties);
      Long startTime = System.nanoTime();
      ResultSet resultSet = dbConnection.getResultSet(dbProperties, getQuery());
      Long elapsedTime = System.nanoTime() - startTime;
      System.out.println("Photos/Videos List extraction time: "+ TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS));
      StringBuilder stringBuilder = getStringBuilder(resultSet);
      csvConverter.convertListToCsv(stringBuilder, outputFileLocation);

      dbConnection.closeConnection(dbProperties.getConnection());

    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());

    }
  }

  private StringBuilder getStringBuilder(ResultSet resultSet) throws SQLException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("latitude");
    stringBuilder.append(",");
    stringBuilder.append("longitude");
    stringBuilder.append(",");
    stringBuilder.append("creation_date");
    stringBuilder.append(",");
    stringBuilder.append("modification_date");
    stringBuilder.append(",");
    stringBuilder.append("file_name");
    stringBuilder.append(",");
    stringBuilder.append("duration");
    stringBuilder.append(",");
    stringBuilder.append("file_type");
    stringBuilder.append("\n");
    while (resultSet.next()) {
      stringBuilder.append(Utilities.latitudeLongitudeExtraction(resultSet.getString("lat"), placeholderMap));
      stringBuilder.append(",");
      stringBuilder.append(Utilities.latitudeLongitudeExtraction(resultSet.getString("lon"), placeholderMap));
      stringBuilder.append(",");
      stringBuilder.append(resultSet.getString("creation_date"));
      stringBuilder.append(",");
      stringBuilder.append(resultSet.getString("modification_date"));
      stringBuilder.append(",");
      stringBuilder.append(resultSet.getString("file_name"));
      stringBuilder.append(",");
      stringBuilder.append(resultSet.getString("duration"));
      stringBuilder.append(",");
      stringBuilder.append(resultSet.getString("file_type"));
      stringBuilder.append("\n");
    }
    return stringBuilder;
    //PROPHeT	Phenotyping Risk Of Psychiatric HospiTalization
  }


  private static String getQuery() {
    String query = "SELECT ZLATITUDE as lat, ZLONGITUDE as lon, datetime(ZDATECREATED, 'unixepoch', '+31 year') as creation_date, datetime(ZMODIFICATIONDATE, 'unixepoch', '+31 year') as modification_date, ZFILENAME as file_name, ZDURATION as duration,ZKIND as file_type\n"
        + "FROM ZASSET;";
    return query;
  }

}
