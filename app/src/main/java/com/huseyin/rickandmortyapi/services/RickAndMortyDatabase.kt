package com.huseyin.rickandmortyapi.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.huseyin.rickandmortyapi.model.Converters
import com.huseyin.rickandmortyapi.model.RickAndMorty


@Database(entities = [RickAndMorty::class], version = 2, exportSchema = true)
@TypeConverters(Converters::class)
 public abstract class RickAndMortyDatabase : RoomDatabase() {

    abstract fun rickAndMortyDao(): RickAndMortyDao

    companion object {
        @Volatile
        private var instance: RickAndMortyDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context): RickAndMortyDatabase {
            val MIGRATION_1_2 = object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE rick_and_morty ADD COLUMN new_column TEXT")
                }
            }

            return Room.databaseBuilder(
                context.applicationContext,
                RickAndMortyDatabase::class.java,
                "rick_and_morty_database"
            )
                .addMigrations(MIGRATION_1_2)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}