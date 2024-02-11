package DBConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
  private DBProperties dbProperties;

  public DBConnection(DBProperties dbProperties) throws SQLException {
    Connection connection = DriverManager.getConnection(dbProperties.getUrl());
    dbProperties.setConnection(connection);
    dbProperties.setStatement(connection.createStatement());
    this.dbProperties = dbProperties;
  }
  public ResultSet getResultSet(DBProperties dbProperties, String query) throws SQLException {

    //String query = getQuery();
    ResultSet resultSet = dbProperties.getStatement().executeQuery(query);
    return resultSet;
  }

  public void closeConnection(Connection connection) throws SQLException {
    connection.close();
  }

}
