package demos;

import edu.wofford.woclo.*;
import java.io.*;

public class XMLReadWriteDemo {
  public static void readWrite(String[] args) throws IOException {
    OutputStreamWriter fw = null;
    try {
      String xmlString = args[0];
      String fileName = args[1];
      ArgumentParser argParse = XMLparser.parseXML(xmlString);
      String resultingXMLString = XMLparser.parserToXML(argParse);

      FileOutputStream fileStream = new FileOutputStream(fileName);
      fw = new OutputStreamWriter(fileStream, "UTF-8");
      fw.write(resultingXMLString);
      fw.close();
    } catch (IOException e) {
      if (fw != null) {
        fw.close();
      }
    }
  }

  public static void main(String... args) {
    try {
      readWrite(args);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
