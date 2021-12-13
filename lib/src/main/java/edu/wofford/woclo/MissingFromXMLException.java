package edu.wofford.woclo;

/**
 * The MissingFromXMLException is thrown when there is something needed to construct the ArgumentParser that
 * is not included in the XML string.
 */
public class MissingFromXMLException extends RuntimeException {
  private String missing;

  /**
   * The constructor takes the name of the missing argument.
   * @param missing the name of the missing argument
   */
  public MissingFromXMLException(String missing) {
    this.missing = missing;
  }

  /**
   * Returns the name of the missing argument.
   * @return the name of thie missing argument.
   */
  public String getMissing() {
    return missing;
  }
}
