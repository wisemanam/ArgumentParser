package edu.wofford.woclo;


public class OptionalArgument extends Argument {
    private String value;

    public OptionalArgument(String name, String type, String description, String value) {
        super(name, type, description);
        this.value = value;
    }
}
