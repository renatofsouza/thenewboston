package com.rs.examples.thenewboston.datasource

import com.rs.examples.thenewboston.model.Bank

interface BankDataSource {
    fun retrieveBanks():Collection<Bank>
}