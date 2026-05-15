// Namma Kelsa - AI powered labor marketplace
package com.example.nammakelsa

import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONObject
import org.json.JSONArray

import android.os.Bundle
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.firestore.FirebaseFirestore

class CustomerActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvEmptyState: TextView
    private lateinit var chipGroup: ChipGroup
    private lateinit var adapter: WorkerAdapter 
    private lateinit var etAiSearch: EditText
    private lateinit var btnAiSearch: Button
    private var fullList: MutableList<Worker> = mutableListOf()
    private val db = FirebaseFirestore.getInstance()
    private val client = OkHttpClient()
    private val MISTRAL_API_KEY = "your-new-key-here"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)

        recyclerView = findViewById(R.id.recyclerView)
        tvEmptyState = findViewById(R.id.tvEmptyState)
        chipGroup = findViewById(R.id.chipGroup)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = WorkerAdapter(mutableListOf())
        recyclerView.adapter = adapter

        etAiSearch = findViewById(R.id.etAiSearch)
        btnAiSearch = findViewById(R.id.btnAiSearch)
        btnAiSearch.setOnClickListener {
            val userInput = etAiSearch.text.toString().trim()
            if (userInput.isEmpty()) {
                Toast.makeText(this,
                    "Please describe your problem",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            detectSkillWithAI(userInput)
        }

        val btnRefresh = findViewById<View>(R.id.btnRefresh)
        btnRefresh?.setOnClickListener { loadWorkers() }

        findViewById<Chip>(R.id.chipAll).setOnClickListener {
            filterAndSort("All")
        }
        findViewById<Chip>(R.id.chipPainter).setOnClickListener {
            filterAndSort("Painter")
        }
        findViewById<Chip>(R.id.chipPlumber).setOnClickListener {
            filterAndSort("Plumber")
        }
        findViewById<Chip>(R.id.chipElectrician).setOnClickListener {
            filterAndSort("Electrician")
        }
        findViewById<Chip>(R.id.chipGardener).setOnClickListener {
            filterAndSort("Gardener")
        }
        findViewById<Chip>(R.id.chipCarpenter).setOnClickListener {
            filterAndSort("Carpenter")
        }
        findViewById<Chip>(R.id.chipTiler).setOnClickListener {
            filterAndSort("Tiler")
        }

        loadWorkers()
    }

    private fun loadWorkers() {
        db.collection("workers")
            .get()
            .addOnSuccessListener { documents ->
                fullList.clear()
                for (document in documents) {
                    val worker = Worker(
                        id = document.id,
                        name = document.getString("name") ?: "",
                        skill = document.getString("skill") ?: "",
                        daily_rate = document.getString("daily_rate") ?: "",
                        location = document.getString("location") ?: "",
                        phone = document.getString("phone") ?: "",
                        is_available = document.getBoolean("is_available") ?: false
                    )
                    fullList.add(worker)
                }
                filterAndSort("All")

                // Reset chipAll to checked state
                findViewById<Chip>(R.id.chipAll).isChecked = true

                // Scroll chips back to start
                findViewById<HorizontalScrollView>(R.id.horizontalScrollView).scrollTo(0, 0)
            }
            .addOnFailureListener {
                tvEmptyState.visibility = View.VISIBLE
                tvEmptyState.text = "Failed to load workers"
            }
    }

    private fun filterAndSort(skill: String) {
        val filtered = if (skill == "All") {
            fullList.toList()
        } else {
            fullList.filter { worker ->
                worker.skill.trim() == skill.trim()
            }
        }

        val sorted = filtered.sortedByDescending { it.is_available }

        adapter.updateList(sorted.toMutableList())

        if (sorted.isEmpty()) {
            tvEmptyState.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            tvEmptyState.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun detectSkillWithAI(userInput: String) {
        btnAiSearch.isEnabled = false
        btnAiSearch.text = "..."

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val prompt = """
                    You are a helpful assistant for a worker hiring app in India.
                    The user will describe their problem in any language
                    (Kannada, Hindi, English, Tamil, Telugu etc).
                    Based on their description identify which ONE skill
                    is needed from this list ONLY:
                    Painter, Plumber, Electrician, Gardener, Carpenter, Tiler

                    User said: "$userInput"

                    Reply with ONLY the skill name from the list above.
                    Nothing else. Just one word.
                """.trimIndent()

                val jsonBody = JSONObject().apply {
                    put("model", "mistral-small-latest")
                    put("messages", JSONArray().apply {
                        put(JSONObject().apply {
                            put("role", "user")
                            put("content", prompt)
                        })
                    })
                    put("max_tokens", 10)
                    put("temperature", 0.1)
                }

                val request = Request.Builder()
                    .url("https://api.mistral.ai/v1/chat/completions")
                    .addHeader("Authorization", "Bearer $MISTRAL_API_KEY")
                    .addHeader("Content-Type", "application/json")
                    .post(jsonBody.toString()
                        .toRequestBody("application/json".toMediaType()))
                    .build()

                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()

                if (response.isSuccessful && responseBody != null) {
                    val skill = JSONObject(responseBody)
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                        .trim()

                    val validSkills = listOf("Painter", "Plumber",
                        "Electrician", "Gardener", "Carpenter", "Tiler")

                    withContext(Dispatchers.Main) {
                        btnAiSearch.isEnabled = true
                        btnAiSearch.text = "🤖 Find"

                        if (validSkills.contains(skill)) {
                            filterAndSort(skill)
                            Toast.makeText(this@CustomerActivity,
                                "🤖 AI detected: $skill needed",
                                Toast.LENGTH_LONG).show()

                            // Visually select the right chip
                            val chipIds = mapOf(
                                "Painter" to R.id.chipPainter,
                                "Plumber" to R.id.chipPlumber,
                                "Electrician" to R.id.chipElectrician,
                                "Gardener" to R.id.chipGardener,
                                "Carpenter" to R.id.chipCarpenter,
                                "Tiler" to R.id.chipTiler
                            )
                            chipIds[skill]?.let { chipId ->
                                findViewById<com.google.android.material.chip.Chip>(chipId)
                                    .isChecked = true
                            }
                        } else {
                            Toast.makeText(this@CustomerActivity,
                                "Could not detect skill. Try again.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        btnAiSearch.isEnabled = true
                        btnAiSearch.text = "🤖 Find"
                        Toast.makeText(this@CustomerActivity,
                            "AI unavailable. Try manual search.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    btnAiSearch.isEnabled = true
                    btnAiSearch.text = "🤖 Find"
                    Toast.makeText(this@CustomerActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
