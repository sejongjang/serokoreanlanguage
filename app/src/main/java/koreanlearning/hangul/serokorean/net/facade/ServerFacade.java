package koreanlearning.hangul.serokorean.net.facade;

import koreanlearning.hangul.serokorean.model.Vocab;
import koreanlearning.hangul.serokorean.net.request.VocabRequest;
import koreanlearning.hangul.serokorean.net.response.VocabResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerFacade {

    private Gson gson = new Gson();
    private String serverHost = "localhost";
    private String serverPort = "8080";

    public VocabResponse putVocab(VocabRequest vocabRequest){

        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/vocab");

            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.connect();

            String reqData = gson.toJson(vocabRequest);
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                VocabResponse vocabResponse = gson.fromJson(respData, VocabResponse.class);

                return vocabResponse;
            }

        } catch ( IOException e ) {
            e.printStackTrace();
        }

        return new VocabResponse("fail to get response", false);
    }

    public VocabResponse getVocabs(VocabRequest vocabRequest) {
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/vocab");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", "auth");
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);

                VocabResponse vocabResponse = gson.fromJson(respData, VocabResponse.class);

                return vocabResponse;
            }

        } catch (IOException e ) {
            e.printStackTrace();
        }
        return new VocabResponse("fail to get response", false);
    }

    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
