package edu.wofford.woclo;

public class MissingFromXMLException extends RuntimeException{
    private String missing;

    public MissingFromXMLException(String missing) {
        this.missing = missing;
    }

    public String getMissing() {
        return missing;
    }
}
