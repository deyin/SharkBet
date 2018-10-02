package io.sharkbet.sharkbet.asynctasks;

import android.os.AsyncTask;

import java.util.List;

import io.sharkbet.sharkbet.models.Handicap;


public class DownloadHandicapTask extends AsyncTask<Void, Void, Void> {

    final String URL_HANDICAP_LIST_OF_MATCH = "http://i.sporttery.cn/api/fb_match_info/get_asia/?mid=" + "matchId"; // matchId should be replaced real id

    final String matchId;

    public DownloadHandicapTask(String matchId) {
        this.matchId = matchId;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return null;
    }

    static class DownloadHandicapChangeTask extends AsyncTask<Void, Void, List<Handicap.HandicapChange>> {

        final String URL_HANDICAP_OF_COMPANY_OF_MATCH = "http://i.sporttery.cn/api/fb_match_info/get_book_asia/?mid=" + "matchId" + "&bid=" + "companyId"; // matchId&companyId should be replaced real id

        final String companyId;

        DownloadHandicapChangeTask(String companyId) {
            this.companyId = companyId;
        }

        @Override
        protected List<Handicap.HandicapChange> doInBackground(Void... voids) {
            return null;
        }
    }
}