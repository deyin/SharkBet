package io.sharkbet.sharkbet.persist.daos;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Transaction;

public interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    void insert(T... array);
}
