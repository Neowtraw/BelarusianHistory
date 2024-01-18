package com.codingub.belarusianhistory.data.repos

import com.codingub.belarusianhistory.data.models.map.MapDto
import com.codingub.belarusianhistory.data.models.map.MapTypeDto
import com.codingub.belarusianhistory.data.remote.HistoryAppApi
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.requests.LabelRequest
import com.codingub.belarusianhistory.domain.repos.MapRepository
import retrofit2.HttpException
import java.util.concurrent.CancellationException
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val api: HistoryAppApi
) : MapRepository {

    override suspend fun addLabelOnMap(label: LabelRequest): ServerResponse<Unit> {
        return try {
            api.addLabelOnMap(label)
            ServerResponse.OK()
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun updateLabelOnMap(label: LabelRequest): ServerResponse<Unit> {
        return try {
            api.updateLabelOnMap(label)
            ServerResponse.OK()
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }    }

    override suspend fun deleteLabelFromMap(id: String): ServerResponse<Unit> {
        return try {
            api.deleteLabelFromMap(id)
            ServerResponse.OK()
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }    }

    override suspend fun getMapTypes(): ServerResponse<List<MapTypeDto>> {
        return try {
            ServerResponse.OK(api.getMapTypes())
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }    }

    override suspend fun getMap(periodId: String): ServerResponse<MapDto> {
        return try {
            ServerResponse.OK(api.getMap(periodId))
        } catch (e: HttpException) {
            ServerResponse.Error(e.response()?.errorBody()?.string() ?: "Unknown error")
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            ServerResponse.Error(e.message ?: "Unknown error")
        }    }
}