package com.rs.examples.thenewboston.service

import com.rs.examples.thenewboston.datasource.BankDataSource
import com.rs.examples.thenewboston.model.Bank
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class BankService(private val dataSource: BankDataSource) {
    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()

}