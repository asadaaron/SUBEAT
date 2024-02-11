package DBConfig;

import java.sql.Connection;
import java.sql.Statement;

public class DBProperties {
  private Connection connection;
  private Statement statement;
  private String url;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public DBProperties(String url) {
    this.url = url;
  }

  public Connection getConnection() {
    return connection;
  }

  public void setConnection(Connection connection) {
    this.connection = connection;
  }

  public Statement getStatement() {
    return statement;
  }

  public void setStatement(Statement statement) {
    this.statement = statement;
  }
}
