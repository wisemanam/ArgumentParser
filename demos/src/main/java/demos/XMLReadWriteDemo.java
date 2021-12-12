package demos;

import edu.wofford.woclo.*;

public class XMLReadWriteDemo {
  public static String readWrite(args) {
    String xmlString = args[0];
    String fileName = args[1];
    ArgumentParser argParse = XMLparser.parseXML(xmlString);
    String resultingXMLString = XMLparser.parserToXML(argParse);
    return resultingXMLString;
  }

  public static void main(String... args) {
    System.out.println(readWrite(args));
  }
}
