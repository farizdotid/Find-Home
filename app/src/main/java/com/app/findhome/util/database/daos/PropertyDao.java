package com.app.findhome.util.database.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.app.findhome.model.entity.Property;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface PropertyDao {
    @Query("SELECT * FROM tbl_property")
    Single<List<Property>> getProperty();
}
