package com.rs.examples.thenewboston.datasource

import com.rs.examples.thenewboston.model.Bank

interface BankDataSource {
    fun retrieveBanks():Collection<Bank>
    fun retriveBank(accountNumber: String): Bank
    fun addBank(bank: Bank): Bank
    fun updateBank(bank: Bank): Bank
    fun deleteBank(accountNumber: String)
}