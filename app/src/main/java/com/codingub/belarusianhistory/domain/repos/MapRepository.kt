package com.codingub.belarusianhistory.domain.repos

import com.codingub.belarusianhistory.data.models.map.MapDto
import com.codingub.belarusianhistory.data.models.map.MapTypeDto
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.requests.LabelRequest

interface MapRepository {

    suspend fun addLabelOnMap(label: LabelRequest) : ServerResponse<Unit>
    suspend fun updateLabelOnMap(label: LabelRequest) :ServerResponse<Unit>
    suspend fun deleteLabelFromMap(id: String):  ServerResponse<Unit>
    suspend fun getMapTypes(): ServerResponse<List<MapTypeDto>>
    suspend fun getMap(periodId: String): ServerResponse<MapDto>
}