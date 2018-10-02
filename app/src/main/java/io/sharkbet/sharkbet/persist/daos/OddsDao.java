package io.sharkbet.sharkbet.persist.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import io.sharkbet.sharkbet.models.Handicap;
import io.sharkbet.sharkbet.models.Odds;

@Dao
public interface OddsDao extends BaseDao<Handicap> {

    @Query("SELECT * FROM t_odds WHERE matchId = :matchId")
    LiveData<List<Odds>> getAllOdds(String matchId);

}
