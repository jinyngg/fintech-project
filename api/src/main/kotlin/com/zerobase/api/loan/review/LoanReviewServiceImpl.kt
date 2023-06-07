package com.zerobase.api.loan.review

import com.zerobase.domain.repository.LoanReviewRepository
import org.springframework.stereotype.Service

@Service
class LoanReviewServiceImpl(
        private val loanReviewRepository: LoanReviewRepository
) : LoanReviewService {
    override fun loanReviewMain(userKey: String): LoanReviewDto.LoanReviewResponseDto {
        val loanResult = getLoanResult(userKey)

        return LoanReviewDto.LoanReviewResponseDto(
                userKey = userKey,
                loanResult = LoanReviewDto.LoanResult(
                        userLimitAmount = loanResult.userLimitAmount,
                        userLoanInterest = loanResult.userLoanInterest
                )
        )
    }

    // Only safe (?.) or non-null asserted (!!.) 관련 에러 발생
    // loanReview.userKey -> loanReview!!.userKey
    override fun getLoanResult(userKey: String): LoanReviewDto.LoanReview {
        val loanReview = loanReviewRepository.findByUserKey(userKey)
        return LoanReviewDto.LoanReview(
                loanReview!!.userKey,
                userLimitAmount = loanReview.loanLimitedAmount,
                userLoanInterest = loanReview.loanInterest
        )
    }
}