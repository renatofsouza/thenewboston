package com.rs.examples.thenewboston.service

import com.rs.examples.thenewboston.datasource.BankDataSource
import com.rs.examples.thenewboston.model.Bank
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class BankService(private val dataSource: BankDataSource) {
    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()
    fun getBank(accountNumber: String) = dataSource.retriveBank(accountNumber)
    fun addBank(bank: Bank): Bank = dataSource.addBank(bank)
    fun updateBank(bank: Bank): Bank = dataSource.updateBank(bank)
    fun deleteBank(accountNumber: String) = dataSource.deleteBank(accountNumber)

}