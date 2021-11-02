package edu.wofford.woclo;

public class ArgumentNameNotSpecifiedException extends RuntimeException{
    String arg_name;

    public ArgumentNameNotSpecifiedException(String arg_name) {
        this.arg_name = arg_name;
    }

    public String getArgName() {
        return arg_name;
    }
}
