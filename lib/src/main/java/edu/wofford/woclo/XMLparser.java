package edu.wofford.woclo;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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

        if (short_name_list.item(0) != null) {
          short_name = short_name_list.item(0).getTextContent();
        }

        // was default value there?
        if (value_list.getLength() > 0) {
          Node valu = value_list.item(0);
          Element e = (Element) valu;
          value = e.getElementsByTagName("value").item(0).getTextContent();
        } else {
          throw new MissingFromXMLException("default");
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

  @SuppressWarnings("unchecked")
  public String toXML(ArgumentParser argParse) {
    List<String> positional_names = argParse.getPositionalNames();
    List<String> nonpositional_names = argParse.getNonPositionalNames();
    StringWriter strWriter = new StringWriter();
    try {
      DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
      Document doc = documentBuilder.newDocument();

      // root element (arguments)
      Element root = doc.createElement("arguments");
      doc.appendChild(root);

      // add positionalArgs element
      Element positionalArgs = doc.createElement("positionalArgs");
      root.appendChild(positionalArgs);

      // loop through and add positionals
      for (int i = 0; i < positional_names.size(); i++) {
        Element positional = doc.createElement("positional");
        positionalArgs.appendChild(positional);

        // add name
        String name_str = positional_names.get(i);
        Argument a = argParse.getArgument(name_str);
        Element name = doc.createElement("name");
        name.appendChild(doc.createTextNode(name_str));
        positional.appendChild(name);

        // add type
        String type_str = a.getType();
        Element type = doc.createElement("type");
        type.appendChild(doc.createTextNode(type_str));
        positional.appendChild(type);

        // add description
        String desc_str = a.getDescription();
        Element description = doc.createElement("description");
        description.appendChild(doc.createTextNode(desc_str));
        positional.appendChild(description);

        // add restriction if they have it
        if (a.hasAcceptedValues()) {
          String[] restrict = a.getAcceptedValues();
          Element restrictions = doc.createElement("restrictions");
          for (int r = 0; r < restrict.length; r++) {
            String res_str = restrict[r];
            Element restriction = doc.createElement("restriction");
            restriction.appendChild(doc.createTextNode(res_str));
            restrictions.appendChild(restriction);
          }
          positional.appendChild(restrictions);
        }
      }

      // add namedArgs element
      Element namedArgs = doc.createElement("namedArgs");
      root.appendChild(namedArgs);

      // loop through and add positionals
      for (int i = 0; i < nonpositional_names.size(); i++) {
        Element named = doc.createElement("named");
        namedArgs.appendChild(named);

        // add name
        String name_str = positional_names.get(i);
        Argument a = argParse.getArgument(name_str);
        Element name = doc.createElement("name");
        name.appendChild(doc.createTextNode(name_str));
        named.appendChild(name);

        // add type
        String type_str = a.getType();
        Element type = doc.createElement("type");
        type.appendChild(doc.createTextNode(type_str));
        named.appendChild(type);

        // add description
        String desc_str = a.getDescription();
        Element description = doc.createElement("description");
        description.appendChild(doc.createTextNode(desc_str));
        named.appendChild(description);

        // add default
        if (type_str.equals("integer")) {
          String val = Integer.toString(a.getValue());
          Element def = doc.createElement("default");
          Element value = doc.createElement("value");
          value.appendChild(doc.createTextNode(val));
          def.appendChild(value);
          named.appendChild(def);
        } else if (type_str.equals("float")) {
          String val = Float.toString(a.getValue());
          Element def = doc.createElement("default");
          Element value = doc.createElement("value");
          value.appendChild(doc.createTextNode(val));
          def.appendChild(value);
          named.appendChild(def);
        } else {
          String val = a.getValue();
          Element def = doc.createElement("default");
          Element value = doc.createElement("value");
          value.appendChild(doc.createTextNode(val));
          def.appendChild(value);
          named.appendChild(def);
        }

        // add restriction if has it
        if (a.hasAcceptedValues()) {
          String[] restrict = a.getAcceptedValues();
          Element restrictions = doc.createElement("restrictions");
          for (int r = 0; r < restrict.length; r++) {
            String res_str = restrict[r];
            Element restriction = doc.createElement("restriction");
            restriction.appendChild(doc.createTextNode(res_str));
            restrictions.appendChild(restriction);
          }
          named.appendChild(restrictions);
        }

        // add short name if has it
        if (a instanceof OptionalArgument) {
          OptionalArgument o = (OptionalArgument) a;
          String short_str = o.getShortName();
          if (!short_str.equals("")) {
            Element shortname = doc.createElement("shortname");
            shortname.appendChild(doc.createTextNode(short_str));
            named.appendChild(shortname);
          }
        }
      }

      // create XML file
      // transform DOM object to XML file

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource domSource = new DOMSource(doc);
      StreamResult result = new StreamResult(strWriter);
      // StreamResult streamResult = new StreamResult(new File(xmlpath));
      // transformer.transform(domSource, streamResult);
      transformer.transform(domSource, result);
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (TransformerException e) {
      e.printStackTrace();
    }
    return strWriter.getBuffer().toString();
  }

  private String parserToXML(ArgumentParser argParse) {
    String s = "<?xml version=\"1.0\"?><arguments>";
    if (!argParse.getPositionalNames().isEmpty()) {
      s += "<positionalArgs>";
      for (int i = 0; i < argParse.getPositionalNames().size(); i++) {
        String name = argParse.getPositionalNames().get(i);
        Argument a = argParse.getArgument(name);
        s += argumentToXML(a);
      }
      s += "</positionalArgs>";
    }

    if (!argParse.getNonPositionalNames().isEmpty()) {
      s += "<namedArgs>";
      for (int i = 0; i < argParse.getNonPositionalNames().size(); i++) {
        String name = argParse.getNonPositionalNames().get(i);
        Argument a = argParse.getArgument(name);
        s += argumentToXML(a);
      }
      s += "</namedArgs>";
    }
    s += "</arguments>";
    return s;
  }

  private String argumentToXML(Argument a) {
    String s = "";
    if (a instanceof OptionalArgument) {
      s += "<named>";
    } else {
      s += "<positional>";
    }

    s += "<name>" + a.getName() + "</name>";
    s += "<type>" + a.getType() + "</type>";
    s += "<description>" + a.getDescription() + "</description>";

    if (a instanceof OptionalArgument) {
      if (!a.getShortName().equals("")) {
        s += "<shortname>" + a.getShortName() + "</shortname>";
      }
      s += "<default><value>" + a.getValue() + "</value></default>";
      if (a.hasAcceptedValues()) {
        s += "<restrictions>";
        for (int i = 0; i < a.getAcceptedValues().length; i++) {
          s += "<restriction>" + a.getAcceptedValues()[i] + "</restriction>";
        }
        s += "</restrictions>";
      }
      s += "</named>";
    } else {
      s += "</positional>";
    }
    return s;
  }
}
