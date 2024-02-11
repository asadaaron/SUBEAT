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
import util.Utilities;

public class ExtractChatFromApp {
  Map<String, String> placeholderMap;
  private static final String rootPath = "/Users/My Docs/My Documents/Study/Master Thesis/Real Data/";

  public ExtractChatFromApp(Map<String, String> placeholderMap) {
    this.placeholderMap = placeholderMap;
  }


  public void printMessage(){
    try  {
      //Class.forName("org.sqlite.JDBC"); // when jdbc connection does not work, then use the line.
      final String filePath = "datex/output/chat_storage.csv";
      final String anonymizationMapper = "datex/output/message_anonymization_mapper.csv";
      CSVConverter csvConverter = new CSVConverter();
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Sms_source");
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
      stringBuilder.append("\n");
      connectToWhatsappDB(stringBuilder);
      connectToSmsDb(stringBuilder);
      csvConverter.convertListToCsv(stringBuilder, filePath);

      //StringBuilder anonymizationMapperBuilder = Utilities.getAnonymizationMapperBuilder(placeholderMap);
      //csvConverter.convertListToCsv(anonymizationMapperBuilder, anonymizationMapper);
      System.out.println(placeholderMap);

    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());

    }
  }



  private void connectToWhatsappDB(StringBuilder stringBuilder) throws SQLException {
    String url = "jdbc:sqlite:" + rootPath + "cc0f97d36abc8d1b18ec9eedd17c91d5a7a8a746_extract_1692056833437/AppDomainGroup-group.net.whatsapp.WhatsApp.shared/ChatStorage.sqlite";
    final String whatsappMessageQuery = "SELECT 'whatsapp_chat' as SMS_SOURCE,datetime(ZMESSAGEDATE, 'unixepoch', '+31 year') AS MESSAGE_DATE, ZFROMJID AS SENDER,ZTOJID AS RECEIVER,ZISFROMME AS GENERATED_BY_ME , ZMESSAGETYPE AS MESSAGE_TYPE, ZTEXT AS MESSAGE FROM ZWAMESSAGE";

    DBProperties dbProperties = new DBProperties(url);
    DBConnection dbConnection = new DBConnection(dbProperties);
    Anonymize anonymize = new Anonymize();
    ResultSet resultSet = dbProperties.getStatement().executeQuery(whatsappMessageQuery);
      while (resultSet.next()) {
        stringBuilder.append(resultSet.getString("SMS_SOURCE"));
        stringBuilder.append(",");
        stringBuilder.append(resultSet.getString("MESSAGE_DATE"));
        stringBuilder.append(",");
        Optional<String> message = Optional.ofNullable(resultSet.getString("MESSAGE"));
        Optional<String> sender = Optional.ofNullable(
            resultSet.getString("GENERATED_BY_ME").equals("1") ? "4917685562981" : resultSet.getString("SENDER"));
        Optional<String> receiver = Optional.ofNullable(resultSet.getString("RECEIVER"));
        stringBuilder.append(sender.isPresent()?Utilities.numberExtractor(sender.get(), placeholderMap):sender.get());
        stringBuilder.append(",");
        stringBuilder.append(receiver.isPresent()?Utilities.numberExtractor(receiver.get(), placeholderMap):Utilities.numberExtractor("4917685562981",placeholderMap));
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
        stringBuilder.append("\n");

      }

    dbConnection.closeConnection(dbProperties.getConnection());

  }

  private StringBuilder connectToSmsDb(StringBuilder stringBuilder) throws SQLException {
    Anonymize anonymize = new Anonymize();
    String smsDbPath = "jdbc:sqlite:" + rootPath + "cc0f97d36abc8d1b18ec9eedd17c91d5a7a8a746_extract_1692056833437/HomeDomain/Library/SMS/sms.db";
    DBProperties dbPropertiesSms = new DBProperties(smsDbPath);
    DBConnection dbConnectionSms = new DBConnection(dbPropertiesSms);
    final String mobileMessageQuery = "SELECT  'mobile_chat' as SMS_SOURCE,datetime(substr(date,1,9), 'unixepoch', '+31 year') AS MESSAGE_DATE,\n"
        + "        (CASE WHEN is_from_me == 1 THEN destination_caller_id ELSE h.id END) as SENDER,\n"
        + "        (CASE WHEN is_from_me == 1 THEN h.id ELSE destination_caller_id  END) as RECEIVER,\n"
        + "        is_from_me AS GENERATED_BY_ME,text AS MESSAGE\n"
        + "FROM message as ms\n"
        + "inner join handle h on ms.handle_id = h.ROWID";
    ResultSet resultSetSms = dbPropertiesSms.getStatement().executeQuery(mobileMessageQuery);
    while (resultSetSms.next()) {
      stringBuilder.append(resultSetSms.getString("SMS_SOURCE"));
      stringBuilder.append(",");
      stringBuilder.append(resultSetSms.getString("MESSAGE_DATE"));
      stringBuilder.append(",");
      Optional<String> message = Optional.ofNullable(resultSetSms.getString("MESSAGE"));
      String sender = Utilities.numberExtractor(resultSetSms.getString("SENDER"),placeholderMap);
      String receiver = Utilities.numberExtractor(resultSetSms.getString("RECEIVER"),placeholderMap);
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
      stringBuilder.append("\n");

    }
    dbConnectionSms.closeConnection(dbPropertiesSms.getConnection());
    return stringBuilder;
  }

}
