package com.example.artefacto001.ui.templearchives.artefacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artefacto001.data.retrofit.ApiConfig
import com.example.artefacto001.data.retrofit.ApiServiceCandi
import com.example.artefacto001.data.retrofit.DataArtifact
import com.example.artefacto001.data.retrofit.DataCandi
import kotlinx.coroutines.launch

class ArtefactsViewModel : ViewModel() {

    private val _templeData = MutableLiveData<DataCandi?>()
    val templeData: LiveData<DataCandi?> get() = _templeData

    private val _artifactsData = MutableLiveData<List<DataArtifact>?>()
    val artifactsData: LiveData<List<DataArtifact>?> get() = _artifactsData

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // Menggunakan coroutine untuk memanggil fungsi suspend
    fun fetchTempleData(templeID: Int, jwtToken: String) {
        _loading.value = true

        val apiService: ApiServiceCandi = ApiConfig.getTempleApi()

        // Memanggil fungsi suspend dalam coroutine
        viewModelScope.launch {
            try {
                val response = apiService.getTemples("Bearer $jwtToken")

                _loading.value = false

                if (response.isSuccessful) {
                    val temples = response.body()?.data
                    val temple = temples?.find { it.templeID == templeID }
                    _templeData.value = temple
                } else {
                    _error.value = "Error fetching temple data"
                }
            } catch (e: Exception) {
                _loading.value = false
                _error.value = e.message
            }
        }
    }

    // Fungsi untuk memanggil data artefak dengan cara yang sama
    fun fetchArtifactsData(templeID: Int, jwtToken: String) {
        _loading.value = true

        val apiService = ApiConfig.getArtefactApi()

        viewModelScope.launch {
            try {
                val response = apiService.getArtifacts(templeID, "Bearer $jwtToken")
                _loading.value = false

                if (response.isSuccessful) {
                    _artifactsData.value = response.body()
                    println("Artifacts fetched: ${response.body()}") // Tambahkan log ini
                } else {
                    _error.value = "Error fetching artifacts data"
                    println("API Error: ${response.errorBody()?.string()}") // Tambahkan log error ini
                }
            } catch (e: Exception) {
                _loading.value = false
                _error.value = e.message
                println("Exception: ${e.message}") // Tambahkan log exception ini
            }
        }
    }
}
