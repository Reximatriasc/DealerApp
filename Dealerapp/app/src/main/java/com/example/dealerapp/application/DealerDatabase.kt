package com.example.dealerapp.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.dealerapp.dao.DealerDao
import com.example.dealerapp.model.Dealer

@Database(entities = [Dealer::class], version = 1, exportSchema = false)
abstract class DealerDatabase: RoomDatabase(){
 abstract fun DealerDao(): DealerDao

 companion object{
  private var INSTANCE: DealerDatabase? = null

  private val migration1ToCallbacks2:Migration = object :Migration(1,2){
   override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("ALTER TABLE dealer_table ADD COLUMN latitude Double DEFAULT 0.0")
    database.execSQL("ALTER TABLE dealer_table ADD COLUMN longitude Double DEFAULT 0.0")
   }
  }

  fun getDatabase(context: Context): DealerDatabase {
   return  INSTANCE ?: synchronized(this){
    val instance = Room.databaseBuilder(
     context.applicationContext,
     DealerDatabase::class.java,
     "dealer_database_1"
    )
     .addMigrations(migration1ToCallbacks2)
     .allowMainThreadQueries()
     .build()

    INSTANCE= instance
    instance
   }
  }
 }
}