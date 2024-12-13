package com.example.artefacto001.ui.templearchives.artefacts
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.artefacto001.R

class ArtefactsActivity : AppCompatActivity() {

    private lateinit var artefactsViewModel: ArtefactsViewModel
    private lateinit var templeImageView: ImageView
    private lateinit var templeTitleTextView: TextView
    private lateinit var templeDescriptionTextView: TextView
    private lateinit var funFactTitleTextView: TextView
    private lateinit var funFactDescriptionTextView: TextView
    private lateinit var locationButton: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var artifactsRecyclerView: RecyclerView
    private lateinit var artefactsAdapter: ArtefactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_artefacts)
        supportActionBar?.hide()

        templeImageView = findViewById(R.id.ivTemplesImage)
        templeTitleTextView = findViewById(R.id.tvTemplesTitle)
        templeDescriptionTextView = findViewById(R.id.tvTemplesDescription)
        funFactTitleTextView = findViewById(R.id.tvFunFactTitle)
        funFactDescriptionTextView = findViewById(R.id.tvFunFactTemple)
        locationButton = findViewById(R.id.locationButton)
        progressBar = findViewById(R.id.progressBar)
        artifactsRecyclerView = findViewById(R.id.rvArtefacts)

        artifactsRecyclerView.layoutManager = LinearLayoutManager(this)
        artefactsAdapter = ArtefactsAdapter(mutableListOf()) { artefact ->

        }
        artifactsRecyclerView.adapter = artefactsAdapter

        artefactsViewModel = ViewModelProvider(this).get(ArtefactsViewModel::class.java)

        artefactsViewModel.templeData.observe(this, Observer { temple ->
            temple?.let {
                Glide.with(this)
                    .load(it.imageUrl)
                    .into(templeImageView)

                templeTitleTextView.text = it.title
                templeDescriptionTextView.text = it.description
                funFactTitleTextView.text = it.funfactTitle
                funFactDescriptionTextView.text = it.funfactDescription

                locationButton.setOnClickListener {
                    val url = temple.location
                    val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url))
                    startActivity(intent)
                }
            }
        })

        artefactsViewModel.artifactsData.observe(this, Observer { artifacts ->
            artifacts?.let {
                println("Artifacts data in observer: $it")
                artefactsAdapter.updateData(it)
            }
        })

        artefactsViewModel.loading.observe(this, Observer { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        artefactsViewModel.error.observe(this, Observer { errorMessage ->
            errorMessage?.let {

            }
        })

        val templeID = intent.getIntExtra("1", 0)

        artefactsViewModel.fetchTempleData(1, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZW1haWwiOiJBcmllaXJBQGdtYWlsLmNvbSIsImlhdCI6MTczNDAyNjU0NH0.EEpO6pvC2C88HEcifxDvUw6QMawTKpDgUeqt1JmZixM")

        artefactsViewModel.fetchArtifactsData(1, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZW1haWwiOiJBcmllaXJBQGdtYWlsLmNvbSIsImlhdCI6MTczNDAyNjU0NH0.EEpO6pvC2C88HEcifxDvUw6QMawTKpDgUeqt1JmZixM")
    }
}
