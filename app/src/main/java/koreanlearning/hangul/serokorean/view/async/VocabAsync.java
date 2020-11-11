package koreanlearning.hangul.serokorean.view.async;

import android.os.AsyncTask;

import koreanlearning.hangul.serokorean.net.request.VocabRequest;
import koreanlearning.hangul.serokorean.net.response.VocabResponse;
import koreanlearning.hangul.serokorean.net.service.VocabService;

public class VocabAsync extends AsyncTask<VocabRequest, Void, VocabResponse> {

    private VocabService vocabService;
    private VocabRequest vocabRequest;

    public VocabAsync(VocabService vocabService) {
        this.vocabService = vocabService;
    }

    @Override
    protected VocabResponse doInBackground(VocabRequest... vocabRequests) {
        return vocabService.getVocabs(vocabRequests[0]);
    }
}
