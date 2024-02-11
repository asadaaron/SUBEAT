package org.example;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import converter.CSVConverter;
import extractor.*;
import util.FileLocationBo;
import util.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ProgressbarCls extends JFrame {

  JFrame frame = new JFrame();
  //    JProgressBar bar = new JProgressBar();
  private JTextField inputFileLocationTextField;
  private JTextField outputFileLocation;
  private JButton startButton;
  private JButton selectInputButton;
  private JButton selectFileButtonOutput;
  private JProgressBar progressBar;
  private JTextArea textArea;
  private JPanel mainPanel;
  private JTextField userIdText;
  private JButton autopsyInputBtn;
  private JTextField autopsyFileLocationText;
  private JLabel autopsyTextLabel;
  private JCheckBox androidCheckbox;
  private JCheckBox iPhoneCheckbox;
  private JScrollPane jScrollPane;
  private BorderLayout borderLayout;

  public BorderLayout getBorderLayout() {
    return borderLayout;
  }

  public void setBorderLayout(BorderLayout borderLayout) {
    this.borderLayout = borderLayout;
  }

  public JCheckBox getAndroidCheckbox() {
    return androidCheckbox;
  }

  public void setAndroidCheckbox(JCheckBox androidCheckbox) {
    this.androidCheckbox = androidCheckbox;
  }

  public JCheckBox getiPhoneCheckbox() {
    return iPhoneCheckbox;
  }

  public void setiPhoneCheckbox(JCheckBox iPhoneCheckbox) {
    this.iPhoneCheckbox = iPhoneCheckbox;
  }

  public JTextField getUserIdText() {
    return userIdText;
  }

  public void setUserIdText(JTextField userIdText) {
    this.userIdText = userIdText;
  }

  public JButton getAutopsyInputBtn() {
    return autopsyInputBtn;
  }

  public void setAutopsyInputBtn(JButton autopsyInputBtn) {
    this.autopsyInputBtn = autopsyInputBtn;
  }

  public JTextField getAutopsyFileLocationText() {
    return autopsyFileLocationText;
  }

  public void setAutopsyFileLocationText(JTextField autopsyFileLocationText) {
    this.autopsyFileLocationText = autopsyFileLocationText;
  }


  public JTextField getInputFileLocationTextField() {
    return inputFileLocationTextField;
  }

  public void setInputFileLocationTextField(JTextField inputFileLocationTextField) {
    this.inputFileLocationTextField = inputFileLocationTextField;
  }

  public JTextField getOutputFileLocation() {
    return outputFileLocation;
  }

  public void setOutputFileLocation(JTextField outputFileLocation) {
    this.outputFileLocation = outputFileLocation;
  }

  public JButton getStartButton() {
    return startButton;
  }

  public void setStartButton(JButton startButton) {
    this.startButton = startButton;
  }

  public JButton getSelectInputFileButton() {
    return selectInputButton;
  }

  public void setSelectInputFileButton(JButton selectInputFileButton) {
    this.selectInputButton = selectInputFileButton;
  }

  public JButton getSelectFileButtonOutput() {
    return selectFileButtonOutput;
  }

  public void setSelectFileButtonOutput(JButton selectFileButtonOutput) {
    this.selectFileButtonOutput = selectFileButtonOutput;
  }

  public JProgressBar getProgressBar() {
    return progressBar;
  }

  public void setProgressBar(JProgressBar progressBar) {
    this.progressBar = progressBar;
  }

  public JTextArea getTextArea() {
    return textArea;
  }

  public void setTextArea(JTextArea textArea) {
    JScrollPane scroll = new JScrollPane(textArea);
    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    this.textArea = textArea;
  }

  public JPanel getMainPanel() {
    return mainPanel;
  }

  public void setMainPanel(JPanel mainPanel) {

    this.mainPanel = mainPanel;

  }

  ProgressbarCls() {
    selectInputButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectInputButton) {
          JFileChooser fileChooser = new JFileChooser();
          fileChooser.setCurrentDirectory(new File("."));
          fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
          int showDialogueOption = fileChooser.showOpenDialog(null);
          if (showDialogueOption == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            inputFileLocationTextField.setText(file.getAbsolutePath());
            System.out.println(file.getAbsolutePath());
          }
        }
      }
    });
    selectFileButtonOutput.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectFileButtonOutput) {
          JFileChooser fileChooser = new JFileChooser();
          fileChooser.setCurrentDirectory(new File("."));
          fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
          int showDialogueOption = fileChooser.showOpenDialog(null);
          if (showDialogueOption == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            outputFileLocation.setText(file.getAbsolutePath());
            System.out.println(file.getAbsolutePath());
          }
        }
      }
    });
    autopsyInputBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (e.getSource() == autopsyInputBtn) {
          JFileChooser fileChooser = new JFileChooser();
          fileChooser.setCurrentDirectory(new File("."));
          fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
          int showDialogueOption = fileChooser.showOpenDialog(null);
          if (showDialogueOption == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            autopsyFileLocationText.setText(file.getAbsolutePath());
            System.out.println(file.getAbsolutePath());
          }
        }
      }
    });
    iPhoneCheckbox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (e.getSource() == iPhoneCheckbox && iPhoneCheckbox.isSelected()) {
          androidCheckbox.setEnabled(false);
          System.out.println(iPhoneCheckbox.isSelected());
        } else {
          androidCheckbox.setEnabled(true);
        }
      }
    });
    androidCheckbox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (e.getSource() == androidCheckbox && androidCheckbox.isSelected()) {
          iPhoneCheckbox.setEnabled(false);
          System.out.println(iPhoneCheckbox.isSelected());
        } else {
          iPhoneCheckbox.setEnabled(true);
        }
      }
    });

    startButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
          final String ROOT_INPUT_FILE = inputFileLocationTextField.getText() + "/";
          final String ROOT_OUTPUT_FILE = outputFileLocation.getText() + "/";
          final String AUTOPSY_FILE = autopsyFileLocationText.getText() + "/";
          final String TOOL_NAME = userIdText.getText();
          //startExecution(ROOT_INPUT_FILE, ROOT_OUTPUT_FILE, AUTOPSY_FILE, TOOL_NAME);
          startProgressBar(ROOT_INPUT_FILE, ROOT_OUTPUT_FILE, AUTOPSY_FILE, TOOL_NAME);

        }
      }


    });

  }


  private void startProgressBar(String ROOT_INPUT_FILE, String ROOT_OUTPUT_FILE,
                                String AUTOPSY_FILE, String TOOL_NAME) {
    startButton.setEnabled(false);
    autopsyInputBtn.setEnabled(false);
    selectFileButtonOutput.setEnabled(false);
    selectInputButton.setEnabled(false);

    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
      @Override
      protected Void doInBackground() throws Exception {

        Map<String, Long> timeCounter = new HashMap<>();
        Long startTime = 0l;
        Long elapsedTime = 0l;
        final String AUTOPSY_FILE_LOC = "autopsy.db";
        final String INSTALLED_PROGRAM_OUTPUT = "_installed_program.csv";
        final String INTERNET_HISTORY_OUTPUT = "_internet_history.csv";
        final String PHONE_MESSAGE_OUTPUT = "_phone_message.csv";
        final String PHOTOS_METADATA_OUTPUT = "_photos_metadata.csv";
        final String WHATS_APP_MESSAGE_OUTPUT = "_whatsapp_message.csv";

        final String PHOTOS_DB_FILE_LOC = "/CameraRollDomain/Media/PhotoData/Photos.sqlite";
        final String WHATS_APP_DB_FILE_LOC = "/AppDomainGroup-group.net.whatsapp.WhatsApp.shared/ChatStorage.sqlite";
        final String PHONE_DB_FILE_LOC = "/HomeDomain/Library/SMS/sms.db";


        final String ANDROID_CALL_LOGS_OUTPUT_FILE = "_call_logs.csv";
        final String ANDROID_CALL_LOGS_INPUT_FILE = ROOT_INPUT_FILE + "CallLogs.csv";

        final String ANDROID_PHOTOS_OUTPUT_FILE = "_photos.csv";
        final String ANDROID_PHOTOS_INPUT_FILE = ROOT_INPUT_FILE + "Excel/EXIF Metadata-Table 1.csv";

        final String ANDROID_MESSAGE_OUTPUT_FILE = "_phone_message.csv";
        final String ANDROID_MESSAGE_INPUT_LOCATION = ROOT_INPUT_FILE + "SMS.csv";

        Map<String, String> placeholderMap = new HashMap<>();

        CSVConverter csvConverter = new CSVConverter();
        StringBuilder progressMessage = new StringBuilder();

        if (androidCheckbox.isSelected()) {
          startTime = System.nanoTime();
          extractAndAnonymizeAndroidCallLogs(ANDROID_CALL_LOGS_OUTPUT_FILE, ANDROID_CALL_LOGS_INPUT_FILE, placeholderMap,
                  csvConverter,
                  TOOL_NAME, ROOT_INPUT_FILE, ROOT_OUTPUT_FILE, progressMessage);
          elapsedTime = System.nanoTime() - startTime;
          timeCounter.put("call logs", TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS));

          startTime = System.nanoTime();

          extractAndAnonymizeAndroidPhoneMessage(ANDROID_MESSAGE_OUTPUT_FILE, ANDROID_MESSAGE_INPUT_LOCATION, placeholderMap,
                  csvConverter, progressMessage);
          elapsedTime = System.nanoTime() - startTime;
          timeCounter.put("Phone Message", TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS));


          startTime = System.nanoTime();
          extractAndAnonymizeAndroidPhotosMetaData(ANDROID_PHOTOS_OUTPUT_FILE, ANDROID_PHOTOS_INPUT_FILE, placeholderMap,
                  csvConverter,
                  TOOL_NAME, ROOT_INPUT_FILE, ROOT_OUTPUT_FILE, progressMessage);
          elapsedTime = System.nanoTime() - startTime;
          timeCounter.put("Photos and videos", TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS));
          //csvConverter.timeCount(timeCounter, "datex/output/timecounter_android.txt");

        } else {
          startTime = System.nanoTime();
          extractAndAnonymizeBrowsingHistory(AUTOPSY_FILE_LOC, INTERNET_HISTORY_OUTPUT,
                  placeholderMap, csvConverter,
                  progressMessage);
          elapsedTime = System.nanoTime() - startTime;
          timeCounter.put("Internet", TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS));

          startTime = System.nanoTime();
          extractAndAnonymizeInstalledProgram(AUTOPSY_FILE_LOC, INSTALLED_PROGRAM_OUTPUT,
                  placeholderMap, csvConverter,
                  progressMessage);
          elapsedTime = System.nanoTime() - startTime;
          timeCounter.put("Installed Program List", TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS));

          startTime = System.nanoTime();
          extractAndAnonymizeMultiMedia(PHOTOS_DB_FILE_LOC, PHOTOS_METADATA_OUTPUT, placeholderMap,
                  csvConverter,
                  progressMessage);
          elapsedTime = System.nanoTime() - startTime;
          timeCounter.put("Photos and Videos", TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS));

          startTime = System.nanoTime();
          extractAndAnonymiseWhatsappMessage(WHATS_APP_DB_FILE_LOC, WHATS_APP_MESSAGE_OUTPUT,
                  placeholderMap, csvConverter,
                  progressMessage);

          elapsedTime = System.nanoTime() - startTime;
          timeCounter.put("Whatsapp message", TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS));

          startTime = System.nanoTime();
          extractAndAnonymisePhoneMessage(PHONE_DB_FILE_LOC, PHONE_MESSAGE_OUTPUT, placeholderMap,
                  csvConverter,
                  progressMessage);
          elapsedTime = System.nanoTime() - startTime;
          timeCounter.put("Phone message", TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS));
          //csvConverter.timeCount(timeCounter, "datex/output/timecounter_iphone.txt");

        }

        StringBuilder anonymizationMapperBuilder = Utilities.getAnonymizationMapperBuilder(
                placeholderMap);

        csvConverter.convertListToCsv(anonymizationMapperBuilder, Utilities.getFileLocationBo(
                TOOL_NAME, "", "", "", ROOT_OUTPUT_FILE).getAnonymizationMapperFileLocation());
        setProgress(100);
        return null;
      }

      private void extractAndAnonymizeAndroidPhoneMessage(String ANDROID_MESSAGE_OUTPUT_FILE,
                                                          String ANDROID_MESSAGE_INPUT_LOCATION, Map<String, String> placeholderMap,
                                                          CSVConverter csvConverter, StringBuilder progressMessage) throws InterruptedException {
        progressStatusVisualization(progressMessage, "Searching SMS.csv file ....\n");
        setProgress(40);
        progressStatusVisualization(progressMessage,
                "Extracting phone message from SMS.csv file ....\n");
        setProgress(50);
        AndroidExtractMessage androidExtractMessage = new AndroidExtractMessage(
                ANDROID_MESSAGE_INPUT_LOCATION,
                Utilities.getFileLocationBo(TOOL_NAME, "", ANDROID_MESSAGE_OUTPUT_FILE, ROOT_INPUT_FILE, ROOT_OUTPUT_FILE).getOutputFileLocation(),
                csvConverter, placeholderMap);
        androidExtractMessage.extract();
        progressStatusVisualization(progressMessage, "Anonymizing phone message data ....\n");
        setProgress(55);
        progressStatusVisualization(progressMessage, "Extraction and Anonymization completed. \n");
        setProgress(60);
      }

      private void extractAndAnonymisePhoneMessage(String PHONE_DB_FILE_LOC,
                                                   String PHONE_MESSAGE_OUTPUT,
                                                   Map<String, String> placeholderMap, CSVConverter csvConverter,
                                                   StringBuilder progressMessage) throws InterruptedException {
        progressStatusVisualization(progressMessage, "Searching sms.db file ....\n");
        setProgress(80);
        progressStatusVisualization(progressMessage,
                "Extracting phone message from sms.db file ....\n");
        setProgress(85);
        FileLocationBo phoneLocationBo = Utilities.getFileLocationBo(TOOL_NAME, PHONE_DB_FILE_LOC,
                PHONE_MESSAGE_OUTPUT,
                ROOT_INPUT_FILE, ROOT_OUTPUT_FILE);
        ExtractPhoneMessage phoneMessage = new ExtractPhoneMessage(phoneLocationBo.getDbUrl(),
                phoneLocationBo.getOutputFileLocation(), csvConverter, placeholderMap);
        phoneMessage.jdbcConnection();
        progressStatusVisualization(progressMessage, "Anonymizing phone message data ....\n");
        setProgress(90);
        progressStatusVisualization(progressMessage, "Extraction and Anonymization completed. \n");
        setProgress(95);
      }

      private void extractAndAnonymiseWhatsappMessage(String WHATS_APP_DB_FILE_LOC,
                                                      String WHATS_APP_MESSAGE_OUTPUT,
                                                      Map<String, String> placeholderMap, CSVConverter csvConverter,
                                                      StringBuilder progressMessage) throws InterruptedException {
        progressStatusVisualization(progressMessage, "Searching ChatStorage.sqlite file ....\n");
        setProgress(60);
        progressStatusVisualization(progressMessage,
                "Extracting Whatsapp message from ChatStorage.sqlite file ....\n");
        setProgress(65);
        FileLocationBo whatsAppLocationBo = Utilities.getFileLocationBo(TOOL_NAME,
                WHATS_APP_DB_FILE_LOC, WHATS_APP_MESSAGE_OUTPUT,
                ROOT_INPUT_FILE, ROOT_OUTPUT_FILE);
        ExtractWhatsAppMessage whatsAppMessage = new ExtractWhatsAppMessage(
                whatsAppLocationBo.getDbUrl(), whatsAppLocationBo.getOutputFileLocation(), csvConverter,
                placeholderMap);
        whatsAppMessage.jdbcConnection();
        progressStatusVisualization(progressMessage, "Anonymizing Whatsapp message data ....\n");
        setProgress(70);
        progressStatusVisualization(progressMessage, "Extraction and Anonymization completed. \n");
        setProgress(75);
      }

      private void extractAndAnonymizeMultiMedia(String PHOTOS_DB_FILE_LOC,
                                                 String PHOTOS_METADATA_OUTPUT,
                                                 Map<String, String> placeholderMap, CSVConverter csvConverter,
                                                 StringBuilder progressMessage) throws InterruptedException {
        progressStatusVisualization(progressMessage, "Searching Photos.sqlite file ....\n");
        setProgress(40);
        progressStatusVisualization(progressMessage,
                "Extracting multimedia from Photos.sqlite file ....\n");
        setProgress(45);
        FileLocationBo fileLocationBo = Utilities.getFileLocationBo(TOOL_NAME, PHOTOS_DB_FILE_LOC,
                PHOTOS_METADATA_OUTPUT, ROOT_INPUT_FILE, ROOT_OUTPUT_FILE);
        ExtractPhotosAndVideosMetadata extractPhotosAndVideosMetadata = new ExtractPhotosAndVideosMetadata(
                fileLocationBo.getDbUrl(), fileLocationBo.getOutputFileLocation(), csvConverter,
                placeholderMap);
        extractPhotosAndVideosMetadata.photosVideosExtractor();

        progressStatusVisualization(progressMessage, "Anonymizing multimedia data ....\n");
        setProgress(50);
        progressStatusVisualization(progressMessage, "Extraction and Anonymization completed. \n");
        setProgress(55);
      }

      private void extractAndAnonymizeInstalledProgram(String AUTOPSY_FILE_LOC,
                                                       String INSTALLED_PROGRAM_FILE,
                                                       Map<String, String> placeholderMap, CSVConverter csvConverter,
                                                       StringBuilder progressMessage) throws InterruptedException {

        setProgress(20);
        progressStatusVisualization(progressMessage, "Searching autopsy.db file ....\n");
        setProgress(25);
        FileLocationBo installedProgramFileLocation = Utilities.getFileLocationBo(TOOL_NAME,
                AUTOPSY_FILE_LOC, INSTALLED_PROGRAM_FILE,
                AUTOPSY_FILE, ROOT_OUTPUT_FILE);
        ExtractInstalledProgram extractInstalledProgram = new ExtractInstalledProgram(
                installedProgramFileLocation.getDbUrl(),
                installedProgramFileLocation.getOutputFileLocation(),
                csvConverter, placeholderMap);
        extractInstalledProgram.installedProgramExtractor();
        progressStatusVisualization(progressMessage,
                "Extracting installed program list from autopsy.db file \n");
        setProgress(30);
        progressStatusVisualization(progressMessage, "Extraction completed. \n");
        setProgress(35);
      }

      private void extractAndAnonymizeBrowsingHistory(String AUTOPSY_FILE_LOC,
                                                      String INTERNET_HISTORY_OUTPUT,
                                                      Map<String, String> placeholderMap, CSVConverter csvConverter,
                                                      StringBuilder progressMessage) throws InterruptedException {
        progressStatusVisualization(progressMessage, "Searching autopsy.db file ....\n");
        setProgress(5);
        progressStatusVisualization(progressMessage,
                "Extracting browsing history from autopsy.db file ....\n");
        setProgress(10);
        FileLocationBo eventHistoryFileLocation = Utilities.getFileLocationBo(TOOL_NAME,
                AUTOPSY_FILE_LOC, INTERNET_HISTORY_OUTPUT,
                AUTOPSY_FILE, ROOT_OUTPUT_FILE);
        EventHistory eventHistory = new EventHistory(eventHistoryFileLocation.getDbUrl(),
                eventHistoryFileLocation.getOutputFileLocation(), csvConverter, placeholderMap);
        eventHistory.eventHistoryExtractor();
        progressStatusVisualization(progressMessage, "Anonymizing browsing history data ....\n");
        setProgress(15);
        progressStatusVisualization(progressMessage, "Extraction and Anonymization completed. \n");
      }

      private void extractAndAnonymizeAndroidCallLogs(String ANDROID_CALL_LOGS_OUTPUT_FILE,
                                                      String ANDROID_CALL_LOGS_INPUT_FILE, Map<String, String> placeholderMap,
                                                      CSVConverter csvConverter, String TOOL_NAME, String ROOT_INPUT_FILE,
                                                      String ROOT_OUTPUT_FILE, StringBuilder progressMessage) throws InterruptedException {
        progressStatusVisualization(progressMessage, "Searching CallLogs.csv file ....\n");
        setProgress(10);
        progressStatusVisualization(progressMessage,
                "Extracting call logs from CallLogs.csv file ....\n");
        setProgress(20);
        AndroidExtractCallLogs androidExtractCallLogs = new AndroidExtractCallLogs(
                ANDROID_CALL_LOGS_INPUT_FILE,
                Utilities.getFileLocationBo(TOOL_NAME, "", ANDROID_CALL_LOGS_OUTPUT_FILE,
                        ROOT_INPUT_FILE, ROOT_OUTPUT_FILE).getOutputFileLocation(),
                csvConverter, placeholderMap);
        progressStatusVisualization(progressMessage, "Anonymizing call logs data ....\n");
        setProgress(30);
        androidExtractCallLogs.extract();
        progressStatusVisualization(progressMessage, "Extraction and Anonymization completed. \n");
      }

      private void extractAndAnonymizeAndroidPhotosMetaData(String ANDROID_PHOTOS_OUTPUT_FILE,
                                                            String ANDROID_PHOTOS_INPUT_FILE, Map<String, String> placeholderMap,
                                                            CSVConverter csvConverter, String TOOL_NAME, String ROOT_INPUT_FILE,
                                                            String ROOT_OUTPUT_FILE, StringBuilder progressMessage) throws InterruptedException {
        progressStatusVisualization(progressMessage, "Searching EXIF Metadata-Table 1.csv file ....\n");
        setProgress(70);
        progressStatusVisualization(progressMessage,
                "Extracting photos metadata from EXIF Metadata-Table 1.csv file ....\n");
        setProgress(80);
        AndroidExtractPhotosMetadata androidExtractPhotosMetadata = new AndroidExtractPhotosMetadata(
                ANDROID_PHOTOS_INPUT_FILE,
                Utilities.getFileLocationBo(TOOL_NAME, "", ANDROID_PHOTOS_OUTPUT_FILE,
                        ROOT_INPUT_FILE, ROOT_OUTPUT_FILE).getOutputFileLocation(),
                csvConverter, placeholderMap);
        progressStatusVisualization(progressMessage, "Anonymizing photos meta data ....\n");
        setProgress(90);
        androidExtractPhotosMetadata.extract();
        progressStatusVisualization(progressMessage, "Extraction and Anonymization completed. \n");
      }

      @Override
      protected void done() {
        startButton.setEnabled(true);
        autopsyInputBtn.setEnabled(true);
        selectFileButtonOutput.setEnabled(true);
        selectInputButton.setEnabled(true);
      }
    };

    worker.addPropertyChangeListener(e -> {
      if ("progress" == e.getPropertyName()) {
        int progress = (Integer) e.getNewValue();
        progressBar.setValue(progress);

      }
    });

    worker.execute();
  }


  private void progressStatusVisualization(StringBuilder progressMessage, String str)
          throws InterruptedException {
    progressMessage.append(str);
    Thread.sleep(500);
    textArea.setText(progressMessage.toString());

  }


  {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    $$$setupUI$$$();
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer
   * >>> IMPORTANT!! <<<
   * DO NOT edit this method OR call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayoutManager(8, 4, new Insets(10, 10, 10, 10), 10, 10));
    inputFileLocationTextField = new JTextField();
    inputFileLocationTextField.setText("Input File Location");
    mainPanel.add(inputFileLocationTextField, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    outputFileLocation = new JTextField();
    outputFileLocation.setText("Output File Location");
    mainPanel.add(outputFileLocation, new GridConstraints(4, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    final JLabel label1 = new JLabel();
    label1.setText("User Id");
    mainPanel.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    userIdText = new JTextField();
    userIdText.setFocusCycleRoot(false);
    userIdText.setText("user_");
    mainPanel.add(userIdText, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    progressBar = new JProgressBar();
    mainPanel.add(progressBar, new GridConstraints(6, 0, 1, 3, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    androidCheckbox = new JCheckBox();
    androidCheckbox.setText("Android");
    mainPanel.add(androidCheckbox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    iPhoneCheckbox = new JCheckBox();
    iPhoneCheckbox.setText("iPhone");
    mainPanel.add(iPhoneCheckbox, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(224, 25), null, 0, false));
    selectInputButton = new JButton();
    selectInputButton.setText("Select File");
    mainPanel.add(selectInputButton, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    selectFileButtonOutput = new JButton();
    selectFileButtonOutput.setText("Select File");
    mainPanel.add(selectFileButtonOutput, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    startButton = new JButton();
    startButton.setText("Start");
    mainPanel.add(startButton, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JLabel label2 = new JLabel();
    label2.setText("Input File Location");
    mainPanel.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JLabel label3 = new JLabel();
    label3.setText("Output File Location");
    mainPanel.add(label3, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    autopsyTextLabel = new JLabel();
    autopsyTextLabel.setText("Autopsy File Location");
    mainPanel.add(autopsyTextLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    autopsyFileLocationText = new JTextField();
    autopsyFileLocationText.setText("Autopsy File Location");
    mainPanel.add(autopsyFileLocationText, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    autopsyInputBtn = new JButton();
    autopsyInputBtn.setText("Select File");
    mainPanel.add(autopsyInputBtn, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    final JScrollPane scrollPane1 = new JScrollPane();
    mainPanel.add(scrollPane1, new GridConstraints(7, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    textArea = new JTextArea();
    textArea.setAutoscrolls(true);
    scrollPane1.setViewportView(textArea);
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return mainPanel;
  }
}
