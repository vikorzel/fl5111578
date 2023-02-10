package exceptions;

public class Exceptions extends Exception{
    public static final int NO_STRING_CELL_FORMAT = 1;

    private final int code;

    public Exceptions(int code){
        this.code = code;
    }

    public String getMessage() {
        switch (code) {
            case NO_STRING_CELL_FORMAT:
                return "ERROR_STRING_CELL_FORMAT";
        }
        return "";
    }

}
