package extractor;

import DBConfig.DBConnection;
import DBConfig.DBProperties;
import converter.CSVConverter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import util.Utilities;

public class EventHistory {

  //private static final String rootPath = "/Users/My Docs/My Documents/Study/Master Thesis/Real Data/";
  //final String dbUrl = "jdbc:sqlite:"+ rootPath + "autopsy.db";
  //final String INTERNET_HISTORY_OUTPUT = "datex/output/extractor_tool_1_internet_history.csv";
  final String dbUrl;
  final String outputFileLocation;
  CSVConverter csvConverter;
  Map<String, String> placeholderMap;

  public EventHistory(String dbUrl, String outputFileLocation, CSVConverter csvConverter, Map<String, String> placeholderMap) {
    this.dbUrl = dbUrl;
    this.outputFileLocation = outputFileLocation;
    this.csvConverter = csvConverter;
    this.placeholderMap = placeholderMap;
  }

  public void eventHistoryExtractor(){
    try  {
      //Class.forName("org.sqlite.JDBC"); // when jdbc connection does not work, then use the line.
      DBProperties dbProperties = new DBProperties(dbUrl);
      DBConnection dbConnection = new DBConnection(dbProperties);

      Long startTime = System.nanoTime();
      ResultSet resultSet = getResultSet(dbConnection, dbProperties);
      Long elapsedTime = System.nanoTime() - startTime;
      System.out.println("Browsing History extraction time: "+ TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS));
      StringBuilder stringBuilder = getStringBuilder(resultSet);
      csvConverter.convertListToCsv(stringBuilder, outputFileLocation);
      dbConnection.closeConnection(dbProperties.getConnection());

    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());

    }
  }

  private StringBuilder getStringBuilder(ResultSet resultSet) throws SQLException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("browsing_history");
    stringBuilder.append(",");
    stringBuilder.append("date_time");
    stringBuilder.append("\n");
    while (resultSet.next()) {
      stringBuilder.append(Utilities.urlExtractor(resultSet.getString("browsing_history"), placeholderMap));
      stringBuilder.append(",");
      stringBuilder.append(resultSet.getString("date_time"));
      stringBuilder.append("\n");
    }
    return stringBuilder;
  }

  private ResultSet getResultSet(DBConnection dbConnection, DBProperties dbProperties) throws SQLException {

    String query = getQuery();
    ResultSet resultSet = dbProperties.getStatement().executeQuery(query);
    return resultSet;
  }

  private static String getQuery() {
    String query = "SELECT full_description as browsing_history ,datetime(time, 'unixepoch') as date_time \n"
        + "FROM tsk_events events\n"
        + "inner join tsk_event_descriptions ted on events.event_description_id = ted.event_description_id\n"
        + "inner join tsk_event_types tet on events.event_type_id = tet.event_type_id\n"
        + "left join blackboard_artifacts ba on ted.artifact_id = ba.artifact_id\n"
        + "left join blackboard_artifact_types bat on bat.artifact_type_id = ba.artifact_type_id\n"
        + "WHERE tet.event_type_id = 9; ";
    return query;
  }


}
