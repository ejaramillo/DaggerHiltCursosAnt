package com.example.xpenses

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.xpenses.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ListenerRegistration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var workDay: WorkDay
    private var workDayListener: ListenerRegistration? = null

//    private val database: FirebaseFirestore = Firebase.firestore
//    @Inject lateinit var database: FirebaseFirestore
    @Inject lateinit var database: Database
//    private val utils: CommonUtils = CommonUtils()
    @Inject lateinit var utils: CommonUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWorDay()
        setupButtons()
    }

    private fun setupWorDay(workDay: WorkDay = WorkDay()) {
        this.workDay = workDay
        updateDataUI()
    }

    private fun updateDataUI() {
        with(binding) {
            etStartCapital.setText(workDay.startCapital.toString())
            etFinalCapital.setText(workDay.finalCapital.toString())
            etExpenses.setText(workDay.expenses.toString())
        }
        updateStatusImage()
    }

    private fun updateStatusImage() {
        workDay.startCapital = 3.5
        binding.imgStatus.setImageResource(
            when (workDay.getStatus()) {
                1, 2 -> R.drawable.ic_access_time_filled
                3 -> R.drawable.ic_check_circle
                else -> R.drawable.ic_history_toggle_off
            }
        )
        binding.tvStatus.setText(
            when (workDay.getStatus()) {
                1, 2 -> R.string.today_status_incomplete
                3 -> R.string.today_status_complete
                else -> R.string.today_status_new
            }
        )
    }

    private fun setupButtons() {
        with(binding) {
            btnStartSave.setOnClickListener {
                saveWorkDay(startCapital = etStartCapital.text.toString().toDouble())
            }
            btnFinalSave.setOnClickListener {
                saveWorkDay(startCapital = etFinalCapital.text.toString().toDouble())
            }
            btnExpensesSave.setOnClickListener {
                saveWorkDay(startCapital = etExpenses.text.toString().toDouble())
            }
            btnRecord.setOnClickListener {
                startActivity(Intent(this@MainActivity, RecordActivity::class.java))
            }
        }
    }

    private fun saveWorkDay(
        startCapital: Double? = null,
        finalCapital: Double? = null,
        expenses: Double? = null
    ) {
        utils.hideKeyboard(binding.root)

        val workDay = WorkDay()
        startCapital?.let { workDay.startCapital = it }
        finalCapital?.let { workDay.finalCapital = it }
        expenses?.let { workDay.expenses = it }
            database.getDayRef(utils.getToday())
            .set(workDay)
            .addOnSuccessListener {
                Snackbar.make(binding.root, R.string.today_save_success, Snackbar.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Snackbar.make(binding.root, R.string.today_save_error, Snackbar.LENGTH_SHORT).show()
            }
            .addOnCompleteListener {
                showProgressUI(false)
            }
    }

    private fun showProgressUI(isVisible: Boolean) {
        binding.progressView.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

//    private fun getDaysRef() = database.collection(Constants.COLL_DAYS)



    override fun onResume() {
        super.onResume()
        showProgressUI(isVisible = true)
        workDayListener = database.getDayRef(utils.getToday())
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("ANT", "onResume: Error al consultar")
                } else if(value != null && value.exists()) {
//                    val name = value?.data?.get("name").toString()
                    //binding.etStartCapital.setText(name)
                    val workDay = value.toObject(WorkDay::class.java)
                    setupWorDay(workDay = workDay ?: WorkDay())
                }
                showProgressUI(false)
            }
    }



    override fun onPause() {
        super.onPause()
        workDayListener?.remove()
    }
}
