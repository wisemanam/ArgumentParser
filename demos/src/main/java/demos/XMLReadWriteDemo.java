package demos;

import edu.wofford.woclo.*;
import java.io.*;

public class XMLReadWriteDemo {
  public static void readWrite(String[] args) throws IOException {
    String xmlString = args[0];
    String fileName = args[1];
    ArgumentParser argParse = XMLparser.parseXML(xmlString);
    String resultingXMLString = XMLparser.parserToXML(argParse);

    FileWriter fw = new java.io.FileWriter(fileName);
    fw.write(resultingXMLString);
    fw.close();
  }

  public static void main(String... args) {
    try {
      readWrite(args);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
