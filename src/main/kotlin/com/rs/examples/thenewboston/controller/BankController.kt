package com.rs.examples.thenewboston.controller

import com.rs.examples.thenewboston.model.Bank
import com.rs.examples.thenewboston.service.BankService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.ExceptionHandler

@RestController
@RequestMapping("api/banks")
class BankController(private val bankService: BankService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException):ResponseEntity<String> =
        ResponseEntity(e.message,HttpStatus.NOT_FOUND)

    @GetMapping
    fun getBanks():Collection<Bank> = bankService.getBanks()

    @GetMapping("{accountNumber}")
    fun getBank(@PathVariable accountNumber: String) = bankService.getBank(accountNumber)
}