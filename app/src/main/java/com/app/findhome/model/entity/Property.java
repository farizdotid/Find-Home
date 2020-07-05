package com.app.findhome.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tbl_property", indices = {@Index(value = {"id"}, unique = true)})
public class Property {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "id")
    public String id;

    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "price")
    public int price;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "lat")
    public String lat;

    @ColumnInfo(name = "lng")
    public String lng;

    @ColumnInfo(name = "is_premium")
    public int isPremium;

    @ColumnInfo(name = "is_favorite")
    public int isFavorite;

    @ColumnInfo(name = "deskripsi")
    public String deskripsi;

    @ColumnInfo(name = "fasilitas")
    public String fasilitas;

    @ColumnInfo(name = "agent_avatar")
    public String agentAvatar;

    @ColumnInfo(name = "agent_name")
    public String agentName;

    @ColumnInfo(name = "agent_type")
    public String agentType;
}
