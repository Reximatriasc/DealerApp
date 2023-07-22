package com.example.dealerapp.repository

import com.example.dealerapp.dao.DealerDao
import com.example.dealerapp.model.Dealer
import kotlinx.coroutines.flow.Flow

    class DealerRepository(private val DealerDao : DealerDao) {
        val allDealer: Flow<List<Dealer>> = DealerDao.getAllDealer()

        suspend fun insertDealer(dealer: Dealer){
            DealerDao.insertDealer(dealer)
        }
        suspend fun deleteDealer(dealer: Dealer){
            DealerDao.deleteDealer(dealer)
        }
        suspend fun updateDealer(dealer: Dealer){
            DealerDao.updateDealer(dealer)
        }
    }
