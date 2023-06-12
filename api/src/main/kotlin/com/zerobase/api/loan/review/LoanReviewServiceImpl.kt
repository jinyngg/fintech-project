package com.zerobase.api.loan.review

import com.zerobase.api.exception.CustomErrorCode
import com.zerobase.api.exception.CustomException
import com.zerobase.domain.domain.LoanReview
import com.zerobase.domain.repository.LoanReviewRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class LoanReviewServiceImpl(
        private val loanReviewRepository: LoanReviewRepository
) : LoanReviewService {
    override fun loanReviewMain(userKey: String): LoanReviewDto.LoanReviewResponseDto {
        val loanResult = getLoanResult(userKey)

        return LoanReviewDto.LoanReviewResponseDto(
                userKey = userKey,
                loanResult = getLoanResult(userKey)?.toResponseDto()
                        // UserKey 조회 -> User 정보가 없을 때 예외처리
                        ?: throw CustomException(CustomErrorCode.RESULT_NOT_FOUND)
        )
    }

    // Only safe (?.) or non-null asserted (!!.) 관련 에러 발생
    // loanReview.userKey -> loanReview!!.userKey
    @Cacheable(value = ["REVIEW"], key = "#userKey", cacheManager = "redisCacheManager")
    override fun getLoanResult(userKey: String) =
            loanReviewRepository.findByUserKey(userKey)

    private fun LoanReview.toResponseDto() =
            LoanReviewDto.LoanResult(
                    userLimitAmount = this.loanLimitedAmount,
                    userLoanInterest = this.loanInterest
            )
}