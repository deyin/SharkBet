package io.sharkbet.sharkbet.persist.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;
import java.util.List;

import io.sharkbet.sharkbet.converters.DateConverter;
import io.sharkbet.sharkbet.models.Match;

@Dao
@TypeConverters({DateConverter.class})
public interface MatchDao extends BaseDao<Match> {

    @Query("SELECT * FROM t_match WHERE time BETWEEN :start AND :end")
    LiveData<List<Match>> getMatchesByDate(Date start, Date end);

}
