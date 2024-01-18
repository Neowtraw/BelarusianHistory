package com.codingub.belarusianhistory.domain.use_cases

import com.codingub.belarusianhistory.data.models.map.MapLabelDto
import com.codingub.belarusianhistory.data.remote.network.requests.LabelRequest
import com.codingub.belarusianhistory.domain.repos.MapRepository
import javax.inject.Inject

class AddLabelOnMapUseCase @Inject constructor(
    private val repository: MapRepository
) {

    suspend operator fun invoke(label: MapLabelDto) =
        repository.addLabelOnMap(LabelRequest(label))
}

class UpdateLabelOnMapUseCase @Inject constructor(
    private val repository: MapRepository
) {

    suspend operator fun invoke(label: MapLabelDto) =
        repository.updateLabelOnMap(LabelRequest(label))
}

class DeleteLabelFromMapUseCase @Inject constructor(
    private val repository: MapRepository
) {

    suspend operator fun invoke(id: String) =
        repository.deleteLabelFromMap(id)
}

class GetMapTypesUseCase @Inject constructor(
    private val repository: MapRepository
) {

    suspend operator fun invoke() =
        repository.getMapTypes()
}

class GetMapUseCase @Inject constructor(
    private val repository: MapRepository
) {

    suspend operator fun invoke(periodId: String) =
        repository.getMap(periodId)
}