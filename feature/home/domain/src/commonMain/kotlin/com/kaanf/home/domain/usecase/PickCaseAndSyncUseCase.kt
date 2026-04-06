package com.kaanf.home.domain.usecase

import com.kaanf.core.domain.repository.UserRepository
import com.kaanf.core.domain.util.DataError
import com.kaanf.core.domain.util.EmptyResult
import com.kaanf.core.domain.util.Result
import com.kaanf.core.domain.util.asEmptyResult
import com.kaanf.home.domain.repository.CaseRepository

class PickCaseAndSyncUseCase(
    private val caseRepository: CaseRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(caseId: String): EmptyResult<DataError.Remote> {
        return when (val pickResult = caseRepository.pickCase(caseId)) {
            is Result.Failure -> pickResult
            is Result.Success -> {
                when (val temporaryCasesResult = caseRepository.getTemporaryCases()) {
                    is Result.Failure -> {
                        temporaryCasesResult.asEmptyResult()
                    }
                    is Result.Success -> {
                        caseRepository.getCases().asEmptyResult()
                        userRepository.getUser().asEmptyResult()
                    }
                }
            }
        }
    }
}
