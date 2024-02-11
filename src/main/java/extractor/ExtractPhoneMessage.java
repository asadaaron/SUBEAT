package extractor;

import DBConfig.DBConnection;
import DBConfig.DBProperties;
import anonymisation.Anonymize;
import converter.CSVConverter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import util.Utilities;

public class ExtractPhoneMessage {

  final String dbUrl;
  final String outputFileLocation;
  CSVConverter csvConverter;
  Map<String, String> placeholderMap;

  public ExtractPhoneMessage(String dbUrl, String outputFileLocation, CSVConverter csvConverter, Map<String, String> placeholderMap) {
    this.dbUrl = dbUrl;
    this.outputFileLocation = outputFileLocation;
    this.csvConverter = csvConverter;
    this.placeholderMap = placeholderMap;
  }

  public void jdbcConnection(){
    try  {
      //Class.forName("org.sqlite.JDBC"); // when jdbc connection does not work, then use the line.
      DBProperties dbProperties = new DBProperties(dbUrl);
      DBConnection dbConnection = new DBConnection(dbProperties);
      Long startTime = System.nanoTime();
      ResultSet resultSet = dbConnection.getResultSet(dbProperties, getQuery());//getResultSet(dbProperties);
      Long elapsedTime = System.nanoTime() - startTime;
      System.out.println("Phone message extraction time: "+ TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS));
      StringBuilder stringBuilder = getStringBuilder(resultSet);
      csvConverter.convertListToCsv(stringBuilder, outputFileLocation);

      dbConnection.closeConnection(dbProperties.getConnection());

    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());

    }
  }

  private StringBuilder getStringBuilder(ResultSet resultSet) throws SQLException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Source");
    stringBuilder.append(",");
    stringBuilder.append("Date");
    stringBuilder.append(",");
    stringBuilder.append("Sender");
    stringBuilder.append(",");
    stringBuilder.append("Receiver");
    stringBuilder.append(",");

    //stringBuilder.append("Type");
    // stringBuilder.append(",");
    stringBuilder.append("Text");
    stringBuilder.append(",");
    stringBuilder.append("No of words");
    stringBuilder.append("\n");
    Anonymize anonymize = new Anonymize();
    while (resultSet.next()) {
      stringBuilder.append(resultSet.getString("SMS_SOURCE"));
      stringBuilder.append(",");
      stringBuilder.append(resultSet.getString("MESSAGE_DATE"));
      stringBuilder.append(",");
      Optional<String> message = Optional.ofNullable(resultSet.getString("MESSAGE"));
      String sender = Utilities.numberExtractor(resultSet.getString("SENDER"),placeholderMap);
      String receiver = Utilities.numberExtractor(resultSet.getString("RECEIVER"),placeholderMap);
      stringBuilder.append(sender);
      stringBuilder.append(",");
      stringBuilder.append(receiver);
      stringBuilder.append(",");

      // stringBuilder.append(resultSet.getString("MESSAGE_TYPE"));
      //stringBuilder.append(",");
      //stringBuilder.append(resultSet.getString("GENERATED_BY_ME"));
      //stringBuilder.append(",");

      message.ifPresent(sms -> {
        try {
          stringBuilder.append(anonymize.anonymizer(sms.replace("\n", " ").replace("\r", "").replace(",",";"), placeholderMap));
        } catch (IOException e) {
          throw new RuntimeException(e);
        }

      });
      stringBuilder.append(",");
      if(!message.isEmpty()){
        StringTokenizer tokens = new StringTokenizer(message.get());
        stringBuilder.append(tokens.countTokens());
      }else{
        stringBuilder.append(0);
      }


      stringBuilder.append("\n");

    }
    return stringBuilder;
  }


  private static String getQuery() {
    final String query = "SELECT  'mobile_chat' as SMS_SOURCE,datetime(substr(date,1,9), 'unixepoch', '+31 year') AS MESSAGE_DATE,\n"
        + "        (CASE WHEN is_from_me == 1 THEN destination_caller_id ELSE h.id END) as SENDER,\n"
        + "        (CASE WHEN is_from_me == 1 THEN h.id ELSE destination_caller_id  END) as RECEIVER,\n"
        + "        is_from_me AS GENERATED_BY_ME,text AS MESSAGE\n"
        + "FROM message as ms\n"
        + "inner join handle h on ms.handle_id = h.ROWID";
    return query;
  }

}
