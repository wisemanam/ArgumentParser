package edu.wofford.woclo;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class XMLparser {
  // will return argumentParser with all the arguments added to it
  public static ArgumentParser parseXML(File XMLfile) {
    // convert file to giant string
    String giantString = "";
    return parseXML(giantString);
  }

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

      NodeList mutually_exclusive_list = doc.getElementsByTagName("mutuallyExclusive");
      if (mutually_exclusive_list != null) {
        argParse = parseMutuallyExclusive(argParse, mutually_exclusive_list);
      }

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
        } else {
          throw new MissingFromXMLException("description");
        }

        // does this positional contain accepted values?

        if (accepted_value_list.getLength() > 0) {
          for (int j = 0; j < accepted_value_list.item(0).getChildNodes().getLength(); j++) {
            String val = accepted_value_list.item(0).getChildNodes().item(j).getTextContent();
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
            accepted_values.add(val);
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
              mutually_exclusive.add(mut_name);
            }
          }
          argParse.addMutuallyExclusiveGroup(mutually_exclusive);
        }
      }
    }
    return argParse;
  }

  public static String parserToXML(ArgumentParser argParse) {
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
      System.out.println("Name: " + a.getName());
      System.out.println(a.getType());
      String value = "";
      if (a.getType().equals("integer")) {
        System.out.println("integer");
        int v = optArg.getValue();
        value = Integer.toString(v);
      } else if (a.getType().equals("float")) {
        System.out.println("float");
        value = Float.toString(optArg.getValue());
      } else if (a.getType().equals("boolean")) {
        System.out.println("boolean");
        value = Boolean.toString(optArg.getValue());
      } else {
        System.out.println("string");
        value = a.getValue();
      }
      if (!value.equals("")) {
        s += "<default><value>" + value + "</value></default>";
      }
      if (optArg.hasAcceptedValues()) {
        s += "<restrictions>";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < optArg.getAcceptedValues().length; i++) {
          sb.append("<restriction>");
          sb.append(optArg.getAcceptedValues()[i]);
          sb.append("</restriction>");
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
