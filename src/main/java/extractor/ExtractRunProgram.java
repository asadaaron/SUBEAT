package extractor;

import DBConfig.DBConnection;
import DBConfig.DBProperties;
import converter.CSVConverter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ExtractRunProgram {

  private static final String rootPath = "/Users/My Docs/My Documents/Study/Master Thesis/Real Data/";
  final String dbUrl = "jdbc:sqlite:"+ rootPath + "autopsy.db";
  final String INSTALLED_PROGRAM_OUTPUT = "datex/output/extractor_tool_1_installed_program.csv";
  CSVConverter csvConverter;
  Map<String, String> placeholderMap;

  public ExtractRunProgram(CSVConverter csvConverter, Map<String, String> placeholderMap) {
    this.csvConverter = csvConverter;
    this.placeholderMap = placeholderMap;
  }

  public void installedProgramExtractor(){
    try  {
      //Class.forName("org.sqlite.JDBC"); // when jdbc connection does not work, then use the line.
      DBProperties dbProperties = new DBProperties(dbUrl);
      DBConnection dbConnection = new DBConnection(dbProperties);


      ResultSet resultSet = getResultSet(dbProperties);
      StringBuilder stringBuilder = getStringBuilder(resultSet);
      csvConverter.convertListToCsv(stringBuilder, INSTALLED_PROGRAM_OUTPUT);

      dbConnection.closeConnection(dbProperties.getConnection());

    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());

    }
  }

  private StringBuilder getStringBuilder(ResultSet resultSet) throws SQLException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("installed_program");

    stringBuilder.append("\n");
    while (resultSet.next()) {
      stringBuilder.append(resultSet.getString("installed_program"));
      stringBuilder.append("\n");
    }
    return stringBuilder;
  }

  private ResultSet getResultSet(DBProperties dbProperties) throws SQLException {

    String query = getQuery();
    ResultSet resultSet = dbProperties.getStatement().executeQuery(query);
    return resultSet;
  }

  private static String getQuery() {
    String query = "Select distinct value_text as installed_program from blackboard_attributes bat\n"
        + "                  inner join blackboard_attribute_types t on bat.attribute_type_id = t.attribute_type_id\n"
        + "                  inner join blackboard_artifact_types b on bat.artifact_type_id = b.artifact_type_id\n"
        + "                  inner join blackboard_artifacts ba on bat.artifact_id = ba.artifact_id\n"
        + "where t.type_name = 'TSK_PROG_NAME';";
    return query;
  }

}
