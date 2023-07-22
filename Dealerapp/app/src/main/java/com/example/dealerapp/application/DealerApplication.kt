package com.example.dealerapp.application

import android.app.Application
import com.example.dealerapp.repository.DealerRepository

class DealerApplication: Application() {
    val database by lazy { DealerDatabase.getDatabase(this) }
    val repository by lazy { DealerRepository(database.DealerDao()) }
}

