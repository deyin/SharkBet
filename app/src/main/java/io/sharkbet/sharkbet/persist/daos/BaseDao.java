package io.sharkbet.sharkbet.persist.daos;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

public interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T... array);
}
