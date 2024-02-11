package util;

public class FileLocationBo {
  private String toolsName;
  private String dbUrl;
  private String anonymizationMapperFileLocation;
  private String outputFileLocation;

  public FileLocationBo(String toolsName, String dbUrl, String anonymizationMapperFileLocation, String outputFileLocation) {
    this.toolsName = toolsName;
    this.dbUrl = dbUrl;
    this.anonymizationMapperFileLocation = anonymizationMapperFileLocation;
    this.outputFileLocation = outputFileLocation;
  }

  public String getToolsName() {
    return toolsName;
  }

  public void setToolsName(String toolsName) {
    this.toolsName = toolsName;
  }

  public String getDbUrl() {
    return dbUrl;
  }

  public void setDbUrl(String dbUrl) {
    this.dbUrl = dbUrl;
  }

  public String getAnonymizationMapperFileLocation() {
    return anonymizationMapperFileLocation;
  }

  public void setAnonymizationMapperFileLocation(String anonymizationMapperFileLocation) {
    this.anonymizationMapperFileLocation = anonymizationMapperFileLocation;
  }

  public String getOutputFileLocation() {
    return outputFileLocation;
  }

  public void setOutputFileLocation(String outputFileLocation) {
    this.outputFileLocation = outputFileLocation;
  }
}
