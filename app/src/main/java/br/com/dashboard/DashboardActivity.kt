package br.com.dashboard

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.*
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.database.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var listView: ListView
    private lateinit var dataList: MutableList<String>
    private lateinit var feedbackIds: MutableList<String>
    private lateinit var btnAddFeedback: Button
    private lateinit var textViewWelcome: TextView
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        listView = findViewById(R.id.dashboardListView)
        btnAddFeedback = findViewById(R.id.btnAddFeedback)
        textViewWelcome = findViewById(R.id.textViewWelcome)
        dataList = mutableListOf()
        feedbackIds = mutableListOf()
        preferencesManager = PreferencesManager(applicationContext)

        lifecycleScope.launch {
            val userName = preferencesManager.userNameFlow.first() ?: "Usuário"
            textViewWelcome.text = "Olá, $userName"
        }

        database = FirebaseDatabase.getInstance().getReference("feedbacks")

        buscarFeedbacksNoFirebase()

        btnAddFeedback.setOnClickListener {
            mostrarFeedbackDialog()
        }
    }
    private fun mostrarFeedbackDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Novo Feedback")


        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.dialog_add_feedback, null)


        val editTextFeedback = view.findViewById<EditText>(R.id.editTextFeedback)
        val spinnerRating = view.findViewById<Spinner>(R.id.spinnerRating)

        builder.setView(view)


        builder.setPositiveButton("Salvar") { _, _ ->
            val feedback = editTextFeedback.text.toString()
            val selectedRating = spinnerRating.selectedItem.toString()
            val userName = intent.getStringExtra("userName") ?: "Anônimo"

            if (feedback.isNotEmpty()) {
                salvarFeedbackNoFirebase(feedback, selectedRating, userName)
            } else {
                Toast.makeText(this, "Insira um feedback", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancelar", null)
        builder.create().show()
    }

    private fun salvarFeedbackNoFirebase(feedback: String, rating: String, userName: String) {

        val feedbackId = database.push().key
        val currentTime = System.currentTimeMillis()
        feedbackId?.let {

            val feedbackData = mapOf(
                "feedback" to feedback,
                "rating" to rating,
                "userName" to userName,
                "date" to currentTime
            )


            database.child(it).setValue(feedbackData).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Feedback salvo", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Erro ao salvar feedback", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun buscarFeedbacksNoFirebase() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                feedbackIds.clear()

                var countBom = 0
                var countRuim = 0
                var countNeutro = 0

                for (dataSnapshot in snapshot.children) {

                    val feedback = dataSnapshot.child("feedback").getValue(String::class.java)
                    val rating = dataSnapshot.child("rating").getValue(String::class.java)
                    val date = dataSnapshot.child("date").getValue(Long::class.java)

                    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                    val formattedDate = date?.let { dateFormat.format(Date(it)) }


                    feedback?.let {

                        dataList.add("Feedback: $it\nData: $formattedDate")
                        feedbackIds.add(dataSnapshot.key!!)


                        when (rating) {
                            "Bom" -> countBom++
                            "Ruim" -> countRuim++
                            "Neutro" -> countNeutro++
                            else -> {}
                        }
                    }
                }


                val adapter = ArrayAdapter(this@DashboardActivity, android.R.layout.simple_list_item_1, dataList)
                listView.adapter = adapter


                atualizarGrafico(countBom, countRuim, countNeutro)


                listView.setOnItemClickListener { _, _, position, _ ->
                    val feedbackId = feedbackIds[position]
                    feedbackId?.let {
                        val intent = Intent(this@DashboardActivity, FeedbackDetailActivity::class.java)
                        intent.putExtra("feedbackId", it)
                        startActivity(intent)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DashboardActivity, "Erro ao carregar feedbacks!", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun atualizarGrafico(countBom: Int, countRuim: Int, countNeutro: Int) {
        val pieChart = findViewById<PieChart>(R.id.ratingsPieChart)

        val entries = ArrayList<PieEntry>()
        if (countBom > 0) entries.add(PieEntry(countBom.toFloat(), "Bom"))
        if (countRuim > 0) entries.add(PieEntry(countRuim.toFloat(), "Ruim"))
        if (countNeutro > 0) entries.add(PieEntry(countNeutro.toFloat(), "Neutro"))

        val dataSet = PieDataSet(entries, "Ratings")
        dataSet.colors = listOf(
            Color.GREEN,
            Color.RED,
            Color.GRAY
        )

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.invalidate()
    }

}