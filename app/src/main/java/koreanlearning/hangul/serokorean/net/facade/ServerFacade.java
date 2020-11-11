package koreanlearning.hangul.serokorean.net.facade;

import koreanlearning.hangul.serokorean.model.Vocab;
import koreanlearning.hangul.serokorean.net.communicator.ClientCommunicator;
import koreanlearning.hangul.serokorean.net.communicator.Serializer;
import koreanlearning.hangul.serokorean.net.request.VocabRequest;
import koreanlearning.hangul.serokorean.net.response.VocabResponse;
import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class ServerFacade {

    private String serverHost = "10.0.2.2";
    private String serverPort = "8080";
    private Map<String, String> BASE_HEADER = new TreeMap<>();

    public VocabResponse postVocab(VocabRequest vocabRequest){

        try {
            Gson gson = new Gson();
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/vocab");

            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.connect();

            String reqData = gson.toJson(vocabRequest.getVocab());
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

    public VocabResponse getVocabs(VocabRequest vocabRequest) throws IOException {
        BASE_HEADER.put("Accept", "application/json");
        BASE_HEADER.put("Content-Type", "application/json");
        ClientCommunicator clientCommunicator = new ClientCommunicator("http://" + serverHost + ":" + serverPort);
        return clientCommunicator.doPost("/vocab", vocabRequest, BASE_HEADER, VocabResponse.class);
    }

//    public VocabResponse getVocabs(VocabRequest vocabRequest) {
//        try {
//            Gson gson = new Gson();
//            URL url = new URL("http://" + serverHost + ":" + serverPort + "/vocab");
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setReadTimeout(10000);
//            connection.addRequestProperty("Accept", "application/json");
//
//            connection.setDoOutput(false);
//            connection.connect();
//
//            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                InputStream respBody = connection.getInputStream();
//                String respData = readString(respBody);
//
//                VocabResponse vocabResponse = gson.fromJson(respData, VocabResponse.class);
//
//                return vocabResponse;
//            }
//
//        } catch (IOException e ) {
//            e.printStackTrace();
//        }
//        return new VocabResponse("fail to get response", false);
//    }

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
