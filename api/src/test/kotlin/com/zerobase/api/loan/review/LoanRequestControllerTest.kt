package com.zerobase.api.loan.review

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.zerobase.api.loan.GenerateKey
import com.zerobase.api.loan.encrypt.EncryptComponent
import com.zerobase.api.loan.request.LoanRequestController
import com.zerobase.api.loan.request.LoanRequestDto
import com.zerobase.api.loan.request.LoanRequestServiceImpl
import com.zerobase.domain.domain.UserInfo
import com.zerobase.domain.repository.UserInfoRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders

// @WebMvcTest => @Controller, @RestController 등 어노테이션이 붙은 애들만 Bean으로 띄어준다.
// @WebMvcTest => JPA 관련 Bean 생성 필요, ApiApplication.class (@EnableJpaAuditing, @EnableJpaRepositories)
@WebMvcTest(LoanRequestController::class)
internal class LoanRequestControllerTest {

    private lateinit var mockMvc: MockMvc
    private lateinit var loanRequestController: LoanRequestController
    private lateinit var generateKey: GenerateKey
    private lateinit var encryptComponent: EncryptComponent

    private var userInfoRepository: UserInfoRepository = mockk()

    private lateinit var mapper: ObjectMapper

    @MockBean
    private lateinit var loanRequestServiceImpl: LoanRequestServiceImpl

    companion object {
        private const val baseUrl = "/fintech/api/v1"
    }

    @BeforeEach
    fun init() {
        generateKey = GenerateKey()
        encryptComponent = EncryptComponent()
        loanRequestServiceImpl = LoanRequestServiceImpl(
                generateKey, userInfoRepository, encryptComponent
        )

        // 생성자로 의존성 주입
        loanRequestController = LoanRequestController(loanRequestServiceImpl)

        // Controller 를 mockMvc로 받아서 띄어준다.
        mockMvc = MockMvcBuilders.standaloneSetup(loanRequestController).build()

        // 기본 생성자가 없어도 코틀린 모듈이 있기 때문에 ObjectMapper가 정상 작동
        mapper = ObjectMapper().registerModule(KotlinModule.Builder().build())
    }

    @Test
    @DisplayName("유저 요청이 들어올 때, 정상 응답 처리")
    fun testNormalCase() {
        // given
        val loanRequestInfoDto: LoanRequestDto.LoanRequestInputDto =
                LoanRequestDto.LoanRequestInputDto(
                        userName = "John",
                        userIncomeAmount = 10000,
                        userRegistrationNumber = "970301-1234567"
                )

        every { userInfoRepository.save(any()) } returns UserInfo("", "", "", 1)

        // when
        // then
        mockMvc.post(
                "$baseUrl/request"
        ) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            // Json 형태로 변환하는 Mapper 필요
            content = mapper.writeValueAsString(loanRequestInfoDto)
        }.andExpect {
            status { isOk() }
        }
    }
}