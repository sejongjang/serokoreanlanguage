package koreanlearning.hangul.serokorean.utility;

public class StringUtil {

    public static String relaceEmailDot(String email){
        String replaced = email;
        if(email.contains(".")){
            replaced = email.replace(".", "_dot_");
        }
        return replaced;
    }
}
