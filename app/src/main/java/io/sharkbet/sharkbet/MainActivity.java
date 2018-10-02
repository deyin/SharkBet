package io.sharkbet.sharkbet;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import io.sharkbet.sharkbet.adapters.MatchAdapter;
import io.sharkbet.sharkbet.asynctasks.DownloadMatchTask;
import io.sharkbet.sharkbet.converters.DateConverter;
import io.sharkbet.sharkbet.models.Match;
import io.sharkbet.sharkbet.models.MatchParent;
import io.sharkbet.sharkbet.persist.AppViewModel;
import io.sharkbet.sharkbet.utils.DateUtils;


public class MainActivity extends AppCompatActivity {

    private AppViewModel mAppViewModel;

    /// UI
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    MatchAdapter matchAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUi();

        mAppViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
    }

    private void setupUi() {
        initSwipeRefresh();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.rv_matches);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        matchAdapter = new MatchAdapter(new ArrayList<MatchParent>());
        recyclerView.setAdapter(matchAdapter);
    }

    private void initSwipeRefresh() {
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMatchList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadMatchList();
    }

    private void loadMatchList() {
        Date today = new Date();
        Date daysBefore = DateUtils.getDaysBefore(today, 1);
        Date daysAfter = DateUtils.getDaysAfter(today, 2);

        mAppViewModel.getMatchesByDate(daysBefore, daysAfter).observe(this,
                new Observer<List<Match>>() {
            @Override
            public void onChanged(@Nullable List<Match> matches) {
                if (matches == null || matches.isEmpty()) {
                    // not found in db, download from network and insert into db
                    new DownloadMatchTask(MainActivity.this).execute();
                    return;
                }
                List<MatchParent> parentList = buildMatchParentList(matches);
                matchAdapter.setParentList(parentList, true);
                matchAdapter.notifyDataSetChanged();
            }
        });
    }

    private List<MatchParent> buildMatchParentList(@NonNull List<Match> matches) {

        List<MatchParent> parentList = new ArrayList<>();

        Map<Date, List<Match>> map = groupByMatchTime(matches);

        Set<Map.Entry<Date, List<Match>>> entries = map.entrySet();
        for (Map.Entry<Date, List<Match>> entry : entries) {
            Date date = entry.getKey();
            List<Match> matchList = entry.getValue();
            int size = matchList.size();
            String title = DateConverter.dateToYmdString(date) + " 共 "  + size + "场比赛";
            MatchParent parent = new MatchParent(title, matchList);
            parentList.add(parent);
        }

        return parentList;
    }

    @NonNull
    private Map<Date, List<Match>> groupByMatchTime(@NonNull List<Match> matches) {
        Map<Date, List<Match>> map = new TreeMap<>();
        for (Match m : matches) {
            Date time = m.getTime();
            Date startTime = DateUtils.getMatchStartTime(time);
            List<Match> matchList = map.get(startTime);
            if (matchList == null) {
                matchList = new ArrayList<>();
            }
            matchList.add(m);
            map.put(startTime, matchList);
        }
        return map;
    }
}
