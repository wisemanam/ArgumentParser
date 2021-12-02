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
          try {
            NodeList pos_list = doc.getElementsByTagName("positional");
          } catch (NullPointerException e) {
            e.printStackTrace();
          }
          for (int i = 0; i < pos_list.getLength(); i++) {
            Node node = pos_list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
              Element element = (Element) node;
              NodeList name_list = element.getElementsByTagName("name"); // name
              NodeList type_list = element.getElementsByTagName("type"); // type
              NodeList description_list = element.getElementsByTagName("description"); // description
              NodeList accepted_value_list =
                  element.getElementsByTagName("restrictions"); // restrictions
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
              if (accepted_value_list != null) {
                for (int j = 0; j < accepted_value_list.getLength(); j++) {
                  Node restrict_val = accepted_value_list.item(j);
                  if (restrict_val.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) node;
                    String val = e.getElementsByTagName("restriction").item(0).getTextContent();
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
          NodeList named_list = doc.getElementsByTagName("named");
          for (int i = 0; i < pos_list.getLength(); i++) {
            Node node = named_list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
              Element element = (Element) node;
              NodeList name_list = element.getElementsByTagName("name"); // name
              NodeList type_list = element.getElementsByTagName("type"); // type
              NodeList description_list = element.getElementsByTagName("description"); // description
              NodeList accepted_value_list =
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
    
              if (short_name_list != null) {
                short_name = short_name_list.item(0).getTextContent();
              }
    
              // was default value there?
              if (value_list != null) {
                Node restrict_val = accepted_value_list.item(0);
                if (restrict_val.getNodeType() == Node.ELEMENT_NODE) {
                  Element e = (Element) node;
                  String val = e.getElementsByTagName("value").item(0).getTextContent();
                  value = val;
                }
              } else {
                throw new MissingFromXMLException("default");
              }
    
              // does this named contain accepted values?
              if (accepted_value_list != null) {
                for (int j = 0; j < accepted_value_list.getLength(); j++) {
                  Node restrict_val = accepted_value_list.item(j);
                  if (restrict_val.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) node;
                    String val = e.getElementsByTagName("restriction").item(0).getTextContent();
                    accepted_values.add(val);
                  }
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
        } catch (ParserConfigurationException | SAXException | IOException e) {
          e.printStackTrace();
        }
      }
}