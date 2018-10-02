package io.sharkbet.sharkbet.asynctasks;

import android.os.AsyncTask;

import java.util.List;

import io.sharkbet.sharkbet.models.Odds;


public class DownloadOddsTask extends AsyncTask<Void, Void, Void> {

    final private static String URL_ODDS_LIST_OF_MATCH = "http://i.sporttery.cn/api/fb_match_info/get_europe/?mid=" + "matchId"; // matchId should be replaced real id

    final String matchId;

    public DownloadOddsTask(String matchId) {
        this.matchId = matchId;
    }

    @Override
    protected Void doInBackground(Void... voids) {



        return null;
    }

    static class DownloadOddsChangeTask extends AsyncTask<Void, Void, List<Odds.OddsChange>> {

        final String URL_ODDS_OF_COMPANY_OF_MATCH = "http://i.sporttery.cn/api/fb_match_info/get_book_europe/?mid=" + "matchId" + "&bid=" + "companyId"; // matchId&companyId should be replaced real id

        final String companyId;

        DownloadOddsChangeTask(String companyId) {
            this.companyId = companyId;
        }

        @Override
        protected List<Odds.OddsChange> doInBackground(Void... voids) {
            return null;
        }
    }
}