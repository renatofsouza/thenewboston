package com.rs.examples.thenewboston.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.rs.examples.thenewboston.model.Bank
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
){

    val baseUrl ="/api/banks"

    @Nested
    @DisplayName("GET /api/bank")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class getBanks {
        @Test
        fun `should return all banks`() {

            //when/then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber"){value("12345")}
                }

        }

    }


    @Nested
    @DisplayName("GET /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class getBank {
        @Test
        fun `should return the bank with the given account number`() {
            //given
            val accountNumber = "12345"

            //when/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust"){value("3.14")}
                    jsonPath("$.transactionFee"){value("17")}
                }
        }

        @Test
        fun `Should return NOT FOUND if account number does not exist`(){
            //given
            val accountNumber ="Does Not Exist"

            //when/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect{
                    status { isNotFound() }
                }
        }
    }

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank  {
        @Test
        fun `should post a bank with given trust and transaction fee`() {
            //given
            val bank = Bank("55055",12.5, 3)
            
            //when
            val performPost = mockMvc.post("$baseUrl"){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(bank)
            }

            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust"){value("12.5")}
                    jsonPath("$.transactionFee"){value("3")}
                }
            
        }

        @Test
        fun `should return BAD REQUEST if bank with account number already exists`() {
            //given
            val invalidBank = Bank("12345",12.5, 3)

            //when
            val performPost = mockMvc.post("$baseUrl"){
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            //then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }

        }


    }

    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistingBank {

        @Test
        fun `should update an existing bank`() {
            //given
            val existingBank = Bank("12345", 44.0, 2)

            //when
            val performPatch = mockMvc.patch("$baseUrl") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(existingBank)
            }

            //then
            performPatch
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(existingBank))
                    }

                }
            mockMvc.get("$baseUrl/${existingBank.accountNumber}")
                .andExpect {
                    content { json(objectMapper.writeValueAsString(existingBank)) }
                }
        }


        @Test
        fun `should return NOT FOUND if bank with given account number does not exist`() {
            //given
            val existingBank = Bank("123456", 44.0, 2)

            //when
            val performPatch = mockMvc.patch("$baseUrl") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(existingBank)
            }

            //then
            performPatch
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }

        }

    }

    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteBank {
        @Test
        fun `should delete bank with given account number`() {
            //given
            val accountNumber = "12345"

            //when
            val performDelete = mockMvc.delete("$baseUrl/$accountNumber"){
                contentType = MediaType.APPLICATION_JSON

            }

            //then
            performDelete.andDo { print() }
                .andExpect { status { isNoContent() } }
            mockMvc.get("$baseUrl/$accountNumber")
                .andExpect { status { isNotFound() } }
        }

        @Test
        fun `should return NOT FOUND if no bank with given account number`() {
            //given
            val accountNumber = "11"

            //when
            val performDelete = mockMvc.delete("$baseUrl/$accountNumber")

            //then
            performDelete.andDo { print() }
                .andExpect { status { isNotFound() } }
        }


    }


}




