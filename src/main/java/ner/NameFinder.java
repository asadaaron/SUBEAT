package ner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class NameFinder {
  public StringBuilder  nameFinderMethod(String sentence) throws IOException {
    TokenizerME tokenizer = tokenizerProvider();
    NameFinderME nameFinder = nameFinderProvider();
    NameFinderME locationFinder = LocationFinderProvider();
    String tokens[] = tokenizer.tokenize(sentence);
    Span nameSpans[] = nameFinder.find(tokens);
    Span locationSpans[] = locationFinder.find(tokens);
    if(nameSpans.length<1 && locationSpans.length<1){
      return new StringBuilder();
    }
    List<String> nameLocationList = new ArrayList<>();
    Arrays.stream(nameSpans).sequential().forEach(span -> {
      nameLocationList.add(tokens[span.getStart()]);
    });
    Arrays.stream(locationSpans).sequential().forEach(span -> {
      nameLocationList.add(tokens[span.getStart()]);
    });
    if(nameLocationList.size() == 1 && nameLocationList.contains("Hi")){
      return new StringBuilder();
    }

    StringBuilder nameRegexBuilder = new StringBuilder();
    nameRegexBuilder.append("\\b(?:");
    nameLocationList.stream().filter(nl -> !(nl.equalsIgnoreCase("Hi"))).forEach(nl -> {
      nameRegexBuilder.append(nl);
      nameRegexBuilder.append("|");
    });
    nameRegexBuilder.setLength(nameRegexBuilder.length()-1);
    nameRegexBuilder.append(")\\b");
    //System.out.println(nameRegexBuilder);
    return nameRegexBuilder;

  }
  private TokenizerME tokenizerProvider() throws IOException {
    InputStream inputStreamTokenizer = null;
    TokenizerME tokenizer = null;
    try {
      inputStreamTokenizer = getClass().getResourceAsStream("/nlpmodel/en-token.bin");
      TokenizerModel tokenModel = new TokenizerModel(inputStreamTokenizer);
      //Instantiating the TokenizerME class
     tokenizer = new TokenizerME(tokenModel);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }


    return tokenizer;

  }
  private NameFinderME nameFinderProvider() throws IOException {
    InputStream inputStreamNameFinder = null;
    try {
      inputStreamNameFinder = getClass().getResourceAsStream("/nlpmodel/en-ner-person.bin");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    TokenNameFinderModel model = new TokenNameFinderModel(inputStreamNameFinder);
    NameFinderME nameFinder = new NameFinderME(model);
    return nameFinder;
  }
  private NameFinderME LocationFinderProvider() throws IOException {
    InputStream inputStreamNameFinder = null;
    try {
      inputStreamNameFinder = getClass().getResourceAsStream("/nlpmodel/en-ner-location.bin");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    TokenNameFinderModel model = new TokenNameFinderModel(inputStreamNameFinder);
    NameFinderME nameFinder = new NameFinderME(model);
    return nameFinder;
  }


}

