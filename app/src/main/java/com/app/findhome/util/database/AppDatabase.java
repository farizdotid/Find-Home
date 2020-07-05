package com.app.findhome.util.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.app.findhome.model.entity.Property;
import com.app.findhome.util.database.daos.PropertyDao;

@Database(entities = {Property.class},
        version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PropertyDao propertyDao();
}
