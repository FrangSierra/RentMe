package com.idealista.android.challenge.list.domain

import com.idealista.android.challenge.core.api.model.CommonError
import com.idealista.android.challenge.core.wrench.type.Either
import com.idealista.android.challenge.list.data.ListRepository

fun list(repository: ListRepository): () -> Either<CommonError, List> = {
    repository.list()
}

fun ad(adId: String, repository: ListRepository) : () -> Either<CommonError, Ad> = {
    repository.ad(adId)
}