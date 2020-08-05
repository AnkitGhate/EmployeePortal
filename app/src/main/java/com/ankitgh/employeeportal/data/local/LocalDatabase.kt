/*
 * Copyright 2020 Ankit Ghate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ankitgh.employeeportal.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ankitgh.employeeportal.data.local.dao.NewsDao
import com.ankitgh.employeeportal.data.local.entity.NewsArticle
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(entities = arrayOf(NewsArticle::class), version = 1)
abstract class LocalDatabase() : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        fun initLocalDb(@ApplicationContext applicationContext: Context): LocalDatabase {
            return Room.databaseBuilder(
                applicationContext,
                LocalDatabase::class.java,
                "EmployeePortalDB"
            ).build()
        }
    }
}