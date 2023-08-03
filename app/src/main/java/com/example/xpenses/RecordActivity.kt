package com.example.xpenses

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xpenses.databinding.ActivityRecordBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecordBinding

    @Inject
    lateinit var database: Database
    @Inject
    lateinit var adapter: RecordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@RecordActivity)
            adapter = this@RecordActivity.adapter
        }
    }

    override fun onResume() {
        super.onResume()
        getAllWorkDay()
    }

    private fun getAllWorkDay() {
        showProgressUI(true)
        database.getDaysRef()
            .get()
            .addOnCompleteListener { tasks ->
                if (tasks.isSuccessful) {
                    val workDays = mutableListOf<WorkDay>()
                    tasks.result.documents.forEach { doc ->
                        doc.toObject(WorkDay::class.java)?.let { workDay ->
                            workDay.uid = doc.id
                            workDays.add(workDay)
                        }
                    }
                    adapter.submitList(workDays)
                } else {
                    Snackbar.make(binding.root, R.string.record_query_error, Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        showProgressUI(false)
    }

    private fun showProgressUI(isVisible: Boolean) {
        binding.progressView.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}
