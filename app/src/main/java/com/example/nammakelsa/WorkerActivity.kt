package com.example.nammakelsa

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class WorkerActivity : AppCompatActivity() {

    private lateinit var etPhoneLookup: EditText
    private lateinit var btnFindProfile: Button
    private lateinit var etName: EditText
    private lateinit var spinnerSkill: Spinner
    private lateinit var etDailyRate: EditText
    private lateinit var etLocation: EditText
    private lateinit var etPhone: EditText
    private lateinit var switchAvailable: SwitchCompat
    private lateinit var btnSave: Button
    private val db = FirebaseFirestore.getInstance()
    private var existingDocumentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker)

        etPhoneLookup = findViewById(R.id.etPhoneLookup)
        btnFindProfile = findViewById(R.id.btnFindProfile)
        etName = findViewById(R.id.etName)
        spinnerSkill = findViewById(R.id.spinnerSkill)
        etDailyRate = findViewById(R.id.etDailyRate)
        etLocation = findViewById(R.id.etLocation)
        etPhone = findViewById(R.id.etPhone)
        switchAvailable = findViewById(R.id.switchAvailable)
        btnSave = findViewById(R.id.btnSave)

        val skills = arrayOf("Select Skill", "Painter",
            "Plumber", "Electrician", "Gardener",
            "Carpenter", "Tiler")
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, skills)
        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)
        spinnerSkill.adapter = adapter

        btnFindProfile.setOnClickListener {
            val phone = etPhoneLookup.text.toString().trim()
            if (phone.isEmpty()) {
                Toast.makeText(this,
                    "Enter your phone number first",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            findProfileByPhone(phone)
        }

        btnSave.setOnClickListener { saveProfile() }
    }

    private fun findProfileByPhone(phone: String) {
        btnFindProfile.isEnabled = false
        btnFindProfile.text = "Searching..."

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = db.collection("workers")
                    .whereEqualTo("phone", phone)
                    .get()
                    .await()

                withContext(Dispatchers.Main) {
                    btnFindProfile.isEnabled = true
                    btnFindProfile.text = "Find My Profile"

                    if (!result.isEmpty) {
                        val doc = result.documents[0]
                        existingDocumentId = doc.id

                        etName.setText(
                            doc.getString("name") ?: "")
                        etDailyRate.setText(
                            doc.getString("daily_rate") ?: "")
                        etLocation.setText(
                            doc.getString("location") ?: "")
                        etPhone.setText(
                            doc.getString("phone") ?: "")
                        switchAvailable.isChecked =
                            doc.getBoolean("is_available") ?: false

                        val skill = doc.getString("skill") ?: ""
                        for (i in 0 until spinnerSkill.count) {
                            if (spinnerSkill.getItemAtPosition(i)
                                    .toString() == skill) {
                                spinnerSkill.setSelection(i)
                                break
                            }
                        }

                        btnSave.text = "Update Profile"
                        Toast.makeText(this@WorkerActivity,
                            "Profile found! You can edit it.",
                            Toast.LENGTH_SHORT).show()

                    } else {
                        existingDocumentId = null
                        clearForm()
                        etPhone.setText(phone)
                        btnSave.text = "Save Profile"
                        Toast.makeText(this@WorkerActivity,
                            "No profile found. Create one!",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    btnFindProfile.isEnabled = true
                    btnFindProfile.text = "Find My Profile"
                    Toast.makeText(this@WorkerActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveProfile() {
        val name = etName.text.toString().trim()
        val skill = spinnerSkill.selectedItem.toString()
        val dailyRate = etDailyRate.text.toString().trim()
        val location = etLocation.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val isAvailable = switchAvailable.isChecked

        if (name.isEmpty() || dailyRate.isEmpty() ||
            location.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this,
                "Please fill all fields",
                Toast.LENGTH_SHORT).show()
            return
        }

        if (skill == "Select Skill") {
            Toast.makeText(this,
                "Please select a skill",
                Toast.LENGTH_SHORT).show()
            return
        }

        val workerData = hashMapOf(
            "name" to name,
            "skill" to skill,
            "daily_rate" to dailyRate,
            "location" to location,
            "phone" to phone,
            "is_available" to isAvailable,
            "timestamp" to System.currentTimeMillis()
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (existingDocumentId != null) {
                    db.collection("workers")
                        .document(existingDocumentId!!)
                        .set(workerData)
                        .await()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@WorkerActivity,
                            "Profile Updated!",
                            Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val docRef = db.collection("workers")
                        .add(workerData)
                        .await()
                    existingDocumentId = docRef.id
                    withContext(Dispatchers.Main) {
                        btnSave.text = "Update Profile"
                        Toast.makeText(this@WorkerActivity,
                            "Profile Saved!",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@WorkerActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun clearForm() {
        etName.text.clear()
        spinnerSkill.setSelection(0)
        etDailyRate.text.clear()
        etLocation.text.clear()
        etPhone.text.clear()
        switchAvailable.isChecked = false
        existingDocumentId = null
    }
}