package koreanlearning.hangul.serokorean.net.service;

import koreanlearning.hangul.serokorean.model.Vocab;
import koreanlearning.hangul.serokorean.net.facade.ServerFacade;
import koreanlearning.hangul.serokorean.net.request.VocabRequest;
import koreanlearning.hangul.serokorean.net.response.VocabResponse;

public class VocabService {
    private ServerFacade serverFacade;
    private VocabService instance;

    public VocabService() {
        this.serverFacade = new ServerFacade();
    }

//    public static VocabService getInstance(){
//        if(instance == null){
//            instance = new VocabService();
//        }
//
//        return instance;
//    }

    public VocabResponse postVocab(VocabRequest vocabRequest){
        return serverFacade.putVocab(vocabRequest);
    }

    public VocabResponse getVocabs(VocabRequest vocabRequest){
        try{
            return serverFacade.getVocabs(vocabRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new VocabResponse("error", false);
    }

    public VocabResponse getVocab(VocabRequest vocabRequest){
        return null;
    }
}
