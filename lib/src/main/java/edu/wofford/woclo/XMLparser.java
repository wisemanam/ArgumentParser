package edu.wofford.woclo;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class XMLparser {
  // will return argumentParser with all the arguments added to it
  public static ArgumentParser parseXML(String giantXmlString) {
    ArgumentParser argParse = new ArgumentParser();
    // has args, short_args, positional, nonpositional_names, and short_name_names

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder db = dbf.newDocumentBuilder();

      // we need to convert giantXmlString to InputStream and pass it into parse
      InputStream stream = new ByteArrayInputStream(giantXmlString.getBytes("UTF-8"));

      Document doc = db.parse(stream);
      // looking for positional arguments (<positional> ... </positional>)
      NodeList pos_list = doc.getElementsByTagName("positional");
      argParse = parsePositionals(argParse, pos_list);

      NodeList named_list = doc.getElementsByTagName("named");
      argParse = parseNonpositionals(argParse, named_list);

    } catch (ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
    }
    return argParse;
  }

  private static ArgumentParser parsePositionals(ArgumentParser argParse, NodeList pos_list) {
    for (int i = 0; i < pos_list.getLength(); i++) {
      Node node = pos_list.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) node;
        NodeList name_list = element.getElementsByTagName("name"); // name
        NodeList type_list = element.getElementsByTagName("type"); // type
        NodeList description_list = element.getElementsByTagName("description"); // description
        NodeList accepted_value_list = element.getElementsByTagName("restrictions"); // restrictions
        String name = "";
        String type = "";
        String description = "";
        ArrayList<String> accepted_values = new ArrayList<String>();
        // was name there?
        if (name_list != null) {
          name = name_list.item(0).getTextContent();
        } else {
          throw new MissingFromXMLException("name");
        }
        // was type there?
        if (type_list != null) {
          type = type_list.item(0).getTextContent();
        } else {
          throw new MissingFromXMLException("type");
        }

        // was description there?
        if (description_list != null) {
          description = description_list.item(0).getTextContent();
        } else {
          throw new MissingFromXMLException("description");
        }

        // does this positional contain accepted values?

        for (int j = 0; j < accepted_value_list.getLength(); j++) {
          Node restrict_val = accepted_value_list.item(j);
          if (restrict_val.getNodeType() == Node.ELEMENT_NODE) {
            Element e = (Element) node;
            String val = e.getElementsByTagName("restriction").item(0).getTextContent();
            accepted_values.add(val);
          }
        }

        if (accepted_values.isEmpty()) {
          argParse.addPositional(name, type, description);
        } else {
          String[] accepted = new String[accepted_values.size()];
          accepted = accepted_values.toArray(accepted);
          argParse.addPositional(name, type, description, accepted);
        }
      }
    }
    return argParse;
  }

  private static ArgumentParser parseNonpositionals(ArgumentParser argParse, NodeList named_list) {
    for (int i = 0; i < named_list.getLength(); i++) {
      Node node = named_list.item(i);
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) node;
        NodeList name_list = element.getElementsByTagName("name"); // name
        NodeList type_list = element.getElementsByTagName("type"); // type
        NodeList description_list = element.getElementsByTagName("description"); // description
        NodeList accepted_value_list2 =
            element.getElementsByTagName("restrictions"); // restrictions
        NodeList short_name_list = element.getElementsByTagName("shortname");
        NodeList value_list = element.getElementsByTagName("default");
        String name = "";
        String type = "";
        String description = "";
        String value = "";
        String short_name = "";
        ArrayList<String> accepted_values = new ArrayList<String>();
        // was name there?
        if (name_list != null) {
          name = name_list.item(0).getTextContent();
        } else {
          throw new MissingFromXMLException("name");
        }

        // was type there?
        if (type_list != null) {
          type = type_list.item(0).getTextContent();
        } else {
          throw new MissingFromXMLException("type");
        }

        // was description there?
        if (description_list != null) {
          description = description_list.item(0).getTextContent();
        } else {
          throw new MissingFromXMLException("description");
        }

        short_name = short_name_list.item(0).getTextContent();

        // was default value there?
        if (value_list != null) {
          // Node restrict_val = accepted_value_list2.item(0);
          Element e = (Element) node;
          String val = e.getElementsByTagName("value").item(0).getTextContent();
          value = val;
        } else {
          throw new MissingFromXMLException("default");
        }

        // does this named contain accepted values?

        for (int j = 0; j < accepted_value_list2.getLength(); j++) {
          // Node restrict_val = accepted_value_list2.item(j);
          Element e = (Element) node;
          String val = e.getElementsByTagName("restriction").item(0).getTextContent();
          accepted_values.add(val);
        }

        // start putting in addnonpositional
        String[] accepted = new String[accepted_values.size()];
        accepted = accepted_values.toArray(accepted);
        if (accepted_values.isEmpty() && short_name.equals("")) {
          argParse.addNonPositional(name, type, description, value);
        } else if (accepted_values.isEmpty() && !short_name.equals("")) {
          argParse.addNonPositional(name, short_name, type, description, value);
        } else if (!accepted_values.isEmpty() && short_name.equals("")) {
          argParse.addNonPositional(name, type, description, value, accepted);
        } else {
          argParse.addNonPositional(name, short_name, type, description, value, accepted);
        }
      }
    }
    return argParse;
  }

  public static String toXML(ArgumentParser argParse) {
    String xmlString = "";

    List<String> positional_names = argParse.getPositionalNames();
    List<String> nonpositional_names = argParse.getNonPositionalNames();

    xmlString += positionalsToXML(positional_names, argParse);
    xmlString += nonpositionalsToXML(nonpositional_names, argParse);

    return xmlString;
  }

  private static String positionalsToXML(List<String> positional_names, ArgumentParser argParse) {
    String positionalString = "<positionalArgs>";
    for (int i = 0; i < positional_names.size(); i++) {
      positionalString += "<positional>";
      String name = positional_names.get(i);
      positionalString += name;
      Argument arg = argParse.getArgument(name);
      positionalString += arg.getType();
      positionalString += arg.getDescription();
      // if restrictions, add restrictions
      positionalString += "</positional>";
    }
    positionalString += "</positionalArgs>";
    return positionalString;
  }

  private static String nonpositionalsToXML(
      List<String> nonpositional_names, ArgumentParser argParse) {
    // loop through nonppositionals and add to XML string
    return "";
  }
}
