package edu.wofford.woclo;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class XMLparser {
  /**
   * parseXML takes a string and parses the arguments into an ArgumentParser
   * @param giantXmlString an XML string that contains the arguments to be parsed
   * @return the ArgumentParser containing the arguments in the XML string
   */
  public ArgumentParser parseXML(String giantXmlString) {
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

      NodeList mutually_exclusive_list = doc.getElementsByTagName("mutuallyExclusive");
      if (mutually_exclusive_list != null) {
        argParse = parseMutuallyExclusive(argParse, mutually_exclusive_list);
      }

    } catch (ParserConfigurationException | SAXException | IOException e) {
      e.printStackTrace();
    }
    return argParse;
  }

  /**
   * parsePositionals takes an ArgumentParser and the names of the positional arguments and adds the positional
   * arguments to the ArgumentParser
   * @param argParse the ArgumentParser
   * @param pos_list the list of positional arguments
   * @return the ArgumentParser
   */
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
        if (name_list.item(0) != null) {
          name = name_list.item(0).getTextContent();
        } else {
          throw new MissingFromXMLException("name");
        }
        // was type there?
        if (type_list.item(0) != null) {
          type = type_list.item(0).getTextContent();
        } else {
          throw new MissingFromXMLException("type");
        }

        // was description there?
        if (description_list.item(0) != null) {
          description = description_list.item(0).getTextContent();
        }

        // does this positional contain accepted values?

        if (accepted_value_list.getLength() > 0) {
          for (int j = 0; j < accepted_value_list.item(0).getChildNodes().getLength(); j++) {
            String val = accepted_value_list.item(0).getChildNodes().item(j).getTextContent();
            if (val.trim().length() > 0) {
              accepted_values.add(val);
            }
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

  /**
   * parseNonpositionals takes an ArgumentParser and the names of the nonpositional arguments and adds the 
   * nonpositional arguments to the ArgumentParser
   * @param argParse the ArgumentParser
   * @param pos_list the list of nonpositional arguments
   * @return the ArgumentParser
   */
  public static ArgumentParser parseNonpositionals(ArgumentParser argParse, NodeList named_list) {
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
        NodeList required_list = element.getElementsByTagName("required");
        String name = "";
        String type = "";
        String description = "";
        String value = "";
        String short_name = "";
        boolean required = false;
        ArrayList<String> accepted_values = new ArrayList<String>();
        // was name there?
        if (name_list.item(0) != null) {
          name = name_list.item(0).getTextContent();
        } else {
          throw new MissingFromXMLException("name");
        }

        // was type there?
        if (type_list.item(0) != null) {
          type = type_list.item(0).getTextContent();
        } else {
          throw new MissingFromXMLException("type");
        }

        // was description there?
        if (description_list.item(0) != null) {
          description = description_list.item(0).getTextContent();
        }

        if (short_name_list.item(0) != null) {
          short_name = short_name_list.item(0).getTextContent();
        }

        if (required_list.getLength() != 0) {
          required = true;
        } else {
          // was default value there?
          if (value_list.getLength() > 0) {
            Node valu = value_list.item(0);
            Element e = (Element) valu;
            value = e.getElementsByTagName("value").item(0).getTextContent();
          } else {
            throw new MissingFromXMLException("default");
          }
        }

        // does this named contain accepted values?
        if (accepted_value_list2.getLength() > 0) {
          for (int j = 0; j < accepted_value_list2.item(0).getChildNodes().getLength(); j++) {
            String val = accepted_value_list2.item(0).getChildNodes().item(j).getTextContent();
            if (val.trim().length() > 0) {
              accepted_values.add(val);
            }
          }
        }

        // start putting in addnonpositional
        String[] accepted = new String[accepted_values.size()];
        accepted = accepted_values.toArray(accepted);
        if (!required && accepted_values.isEmpty() && short_name.equals("")) {
          argParse.addNonPositional(name, type, description, value);
        } else if (!required && accepted_values.isEmpty() && !short_name.equals("")) {
          argParse.addNonPositional(name, short_name, type, description, value);
        } else if (!required && !accepted_values.isEmpty() && short_name.equals("")) {
          argParse.addNonPositional(name, type, description, value, accepted);
        } else if (required && accepted_values.isEmpty() && short_name.equals("")) {
          argParse.addNonPositional(name, type, description, required);
        } else if (required && accepted_values.isEmpty() && !short_name.equals("")) {
          argParse.addNonPositional(name, short_name, type, description, required);
        } else if (required && !accepted_values.isEmpty() && short_name.equals("")) {
          argParse.addNonPositional(name, type, description, accepted, required);
        } else if (required && !accepted_values.isEmpty() && !short_name.equals("")) {
          argParse.addNonPositional(name, short_name, type, description, accepted, required);
        } else {
          argParse.addNonPositional(name, short_name, type, description, value, accepted);
        }
      }
    }
    return argParse;
  }

  /**
   * parseMutuallyExclusive takes the ArgumentParser and a list of mutually exclusive groups
   * and adds those groups to the ArgumentParser.
   * @param argParse the ArgumentParser
   * @param mut_exc_list the list of lists containing mutually exclusive arguments
   * @return the ArgumentParser
   */
  private static ArgumentParser parseMutuallyExclusive(
      ArgumentParser argParse, NodeList mut_exc_list) {
    for (int i = 0; i < mut_exc_list.getLength(); i++) {
      Node node = mut_exc_list.item(i); // another list
      if (node.getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) node;
        NodeList group_list = element.getElementsByTagName("group");
        for (int j = 0; j < group_list.getLength(); j++) {
          NodeList name_list = group_list.item(j).getChildNodes();
          List<String> mutually_exclusive = new ArrayList<String>();
          for (int k = 0; k < name_list.getLength(); k++) {
            String mut_name = "";
            if (name_list.item(k) != null) {
              mut_name = name_list.item(k).getTextContent();
              if (mut_name.trim().length() > 0) {
                mutually_exclusive.add(mut_name);
              }
            }
          }
          argParse.addMutuallyExclusiveGroup(mutually_exclusive);
        }
      }
    }
    return argParse;
  }

  /**
   * parserToXML takes an ArgumentParser and converts the arguments it contains into an XML string
   * @param argParse the ArgumentParser
   * @return the XML string containing the arguments in the ArgumentParser
   */
  public String parserToXML(ArgumentParser argParse) {
    String s = "<?xml version=\"1.0\"?><arguments>";
    if (!argParse.getPositionalNames().isEmpty()) {
      s += "<positionalArgs>";
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < argParse.getPositionalNames().size(); i++) {
        String name = argParse.getPositionalNames().get(i);
        Argument a = argParse.getArgument(name);
        sb.append(argumentToXML(argParse, a));
      }
      s += sb.toString();
      s += "</positionalArgs>";
    }

    if (!argParse.getNonPositionalNames().isEmpty()) {
      s += "<namedArgs>";
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < argParse.getNonPositionalNames().size(); i++) {
        String name = argParse.getNonPositionalNames().get(i);
        Argument a = argParse.getArgument(name);
        sb.append(argumentToXML(argParse, a));
      }
      s += sb.toString();
      s += "</namedArgs>";
    }

    if (!argParse.getMutuallyExclusive().isEmpty()) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < argParse.getMutuallyExclusive().size(); i++) {
        sb.append(mutualExclusiveGroupToXML(argParse.getMutuallyExclusive().get(i)));
      }
      s += sb.toString();
    }
    s += "</arguments>";
    return s;
  }

  /**
   * argumentToXML takes an Argument and converts it into XML format
   * @param argParse the ArgumentParser
   * @param a the Argument
   * @return the String containing the argument in XML format
   */
  @SuppressWarnings("unchecked")
  private static String argumentToXML(ArgumentParser argParse, Argument a) {
    String s = "";
    if (a instanceof OptionalArgument) {
      s += "<named>";
    } else {
      s += "<positional>";
    }

    s += "<name>" + a.getName() + "</name>";
    s += "<type>" + a.getType() + "</type>";
    if (!a.getDescription().equals("")) {
      s += "<description>" + a.getDescription() + "</description>";
    }

    boolean required = argParse.getRequiredNames().contains(a.getName());
    if (required) {
      s += "<required/>";
    }

    if (a instanceof OptionalArgument) {
      OptionalArgument optArg = (OptionalArgument) a;
      if (!optArg.getShortName().equals("")) {
        s += "<shortname>" + optArg.getShortName() + "</shortname>";
      }

      String value = optArg.getValueAsString();
      if (!value.equals("")) {
        s += "<default><value>" + value + "</value></default>";
      }
      if (optArg.hasAcceptedValues()) {
        s += "<restrictions>";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < optArg.getAcceptedValues().length; i++) {
          String g = optArg.getAcceptedValues()[i].trim();
          if (g.length() > 0) {
            sb.append("<restriction>");
            sb.append(optArg.getAcceptedValues()[i]);
            sb.append("</restriction>");
          }
        }
        s += sb.toString();
        s += "</restrictions>";
      }
      s += "</named>";
    } else {
      s += "</positional>";
    }
    return s;
  }

  /**
   * mutualExclusiveGroupToXML takes a group of mutually exclusive arguments and returns a string of those
   * arguments in XML format
   * @param group the group of mutually exclusive arguments
   * @return the String of arguments in XML format
   */
  public static String mutualExclusiveGroupToXML(List<String> group) {
    String s = "<mutuallyExclusive><group>";
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < group.size(); i++) {
      sb.append("<name>" + group.get(i) + "</name>");
    }
    s += sb.toString();
    s += "</group></mutuallyExclusive>";
    return s;
  }
}
