package converter;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CSVConverter {
  public void convertListToCsv(StringBuilder stringBuilder, String filePath) {
    try (FileWriter writer = new FileWriter(filePath)) {
      writer.append(stringBuilder.toString());
      writer.close();;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public List<String[]> csvReader(String filePath) {
    try {
      CSVReader csvReader = new CSVReader(new FileReader(filePath));
      List<String[]> allData = csvReader.readAll();
      csvReader.close();
      return allData;
    } catch (IOException | CsvException e) {
      throw new RuntimeException(e);
    }
  }

  public void timeCount(Map<String, Long> timeMapper, String filePath) {
    try (FileWriter myWriter = new FileWriter(filePath)) {
      myWriter.write(timeMapper.toString());
      myWriter.close();
      System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

}
