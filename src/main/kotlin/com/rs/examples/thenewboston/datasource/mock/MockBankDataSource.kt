package com.rs.examples.thenewboston.datasource.mock

import com.rs.examples.thenewboston.datasource.BankDataSource
import com.rs.examples.thenewboston.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource: BankDataSource {

    val banks = mutableListOf(
        Bank("12345",3.14,17),
        Bank("1010",17.14,0),
        Bank("5678",0.0,100)
    )

    override fun retrieveBanks(): Collection<Bank> = banks
    override fun retriveBank(accountNumber: String): Bank =
        banks.firstOrNull(){it.accountNumber == accountNumber}
            ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber .")

    override fun addBank(bank: Bank): Bank {
        if (banks.any(){it.accountNumber == bank.accountNumber}){
            throw IllegalArgumentException("Bank account already exists.")
        }
        banks.add(bank)
        return bank
    }
}