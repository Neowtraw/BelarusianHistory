package com.codingub.belarusianhistory.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codingub.belarusianhistory.data.local.db.entity.ResultEntity

@Database(
    entities = [ResultEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(AppConverter::class)
abstract class AppDatabase : RoomDatabase() {

}