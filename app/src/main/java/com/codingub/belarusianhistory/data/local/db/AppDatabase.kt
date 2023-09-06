package com.codingub.belarusianhistory.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codingub.belarusianhistory.data.local.db.dao.AppDao
import com.codingub.belarusianhistory.data.local.db.entity.achieves.PracticeAchievesRef
import com.codingub.belarusianhistory.data.local.db.entity.achieves.TicketAchievesRef
import com.codingub.belarusianhistory.data.local.db.entity.questions.PracticeQuestionRef
import com.codingub.belarusianhistory.data.local.db.entity.questions.TicketQuestionRef
import com.codingub.belarusianhistory.data.local.db.entity.ticket.TicketRef

@Database(
entities =[TicketAchievesRef::class, PracticeAchievesRef::class,
    TicketQuestionRef::class, PracticeQuestionRef::class, TicketRef::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val dao:AppDao
}