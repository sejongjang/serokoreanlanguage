package koreanlearning.hangul.serokorean.net.communicator;

import com.google.gson.Gson;

public class Serializer {

    public static String serialize(Object requestInfo) {
        return (new Gson()).toJson(requestInfo);
    }

    public static <T> T deserialize(String value, Class<T> returnType) {
        return (new Gson()).fromJson(value, returnType);
    }
}
