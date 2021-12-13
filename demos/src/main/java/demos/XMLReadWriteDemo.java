package demos;

import edu.wofford.woclo.*;
import java.io.*;

public class XMLReadWriteDemo {
  public void readWrite(String[] args) throws IOException {
    OutputStreamWriter fw = null;
    XMLparser x = new XMLparser();
    try {
      String xmlString = args[0];
      String fileName = args[1];
      ArgumentParser argParse = x.parseXML(xmlString);
      String resultingXMLString = x.parserToXML(argParse);

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
    XMLReadWriteDemo xmlReadWrite = new XMLReadWriteDemo();
    try {
      xmlReadWrite.readWrite(args);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
