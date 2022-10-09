package com.rs.examples.thenewboston.controller

import com.rs.examples.thenewboston.model.Bank
import com.rs.examples.thenewboston.service.BankService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/banks")
class BankController(private val bankService: BankService) {

    @GetMapping
    fun getBanks():Collection<Bank>{
        return bankService.getBanks()
    }
}