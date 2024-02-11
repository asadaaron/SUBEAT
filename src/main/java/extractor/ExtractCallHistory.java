package extractor;

import DBConfig.DBConnection;
import DBConfig.DBProperties;
import converter.CSVConverter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import util.Utilities;

public class ExtractCallHistory {

  private static final String rootPath = "/Users/My Docs/My Documents/Study/Master Thesis/Real Data/";
  final String dbUrl = "jdbc:sqlite:"+ rootPath + "cc0f97d36abc8d1b18ec9eedd17c91d5a7a8a746_extract_1692056833437/AppDomainGroup-group.net.whatsapp.WhatsApp.shared/CallHistory.sqlite";
  final String CALL_LOGS_OUTPUT = "datex/output/extractor_tool_1_call_logs.csv";
  CSVConverter csvConverter;
  Map<String, String> placeholderMap;

  public ExtractCallHistory(CSVConverter csvConverter, Map<String, String> placeholderMap) {
    this.csvConverter = csvConverter;
    this.placeholderMap = placeholderMap;
  }

  public void callLogsExtractor(){
    try  {
      //Class.forName("org.sqlite.JDBC"); // when jdbc connection does not work, then use the line.
      DBProperties dbProperties = new DBProperties(dbUrl);
      DBConnection dbConnection = new DBConnection(dbProperties);


      ResultSet resultSet = getResultSet(dbConnection, dbProperties);
      StringBuilder stringBuilder = getStringBuilder(resultSet);
      csvConverter.convertListToCsv(stringBuilder, CALL_LOGS_OUTPUT);

      dbConnection.closeConnection(dbProperties.getConnection());

    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());

    }
  }

  private StringBuilder getStringBuilder(ResultSet resultSet) throws SQLException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("group_call_creator");
    stringBuilder.append(",");
    stringBuilder.append("date_time");
    stringBuilder.append(",");
    stringBuilder.append("duration");
    stringBuilder.append("\n");
    while (resultSet.next()) {
      stringBuilder.append(
          Utilities.numberExtractor(resultSet.getString("group_call_creator"), placeholderMap));
      stringBuilder.append(",");
      stringBuilder.append(resultSet.getString("date_time"));
      stringBuilder.append(",");
      stringBuilder.append(resultSet.getString("duration"));
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
    String query = "select ZGROUPCALLCREATORUSERJIDSTRING as group_call_creator, datetime(ZDATE, 'unixepoch', '+31 year') as date_time, ZDURATION as duration from ZWACDCALLEVENT;";
    return query;
  }

}
