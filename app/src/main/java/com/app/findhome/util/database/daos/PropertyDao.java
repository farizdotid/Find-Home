package com.app.findhome.util.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.app.findhome.model.entity.Property;

import java.util.List;

@Dao
public interface PropertyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertData(Property... properties);

    @Query("SELECT * FROM tbl_property")
    List<Property> getProperty();

    @Query("SELECT * FROM tbl_property WHERE title LIKE :title")
    List<Property> searchProperty(String title);

    @Query("SELECT * FROM tbl_property WHERE is_favorite = 1")
    List<Property> getPropertyFavorites();

    @Query("DELETE FROM tbl_property")
    void deleteAllData();

    @Query("SELECT * FROM tbl_property WHERE id = :id")
    Property getPropertyById(String id);

    @Query("UPDATE tbl_property SET is_favorite = :isFavorite WHERE id =:id")
    void updateFavorite(int isFavorite, String id);

    @Query("SELECT id FROM tbl_property WHERE id = :id")
    int isContainId(String id);
}
