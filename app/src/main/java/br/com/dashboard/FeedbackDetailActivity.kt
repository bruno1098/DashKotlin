package br.com.dashboard

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class FeedbackDetailActivity : AppCompatActivity() {

    private lateinit var textViewFeedback: TextView
    private lateinit var textViewRating: TextView
    private lateinit var textViewUserName: TextView
    private lateinit var textViewDate: TextView
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_detail)


        textViewFeedback = findViewById(R.id.textViewFeedback)
        textViewRating = findViewById(R.id.textViewRating)
        textViewUserName = findViewById(R.id.textViewUserName)
        textViewDate = findViewById(R.id.textViewDate)


        database = FirebaseDatabase.getInstance().getReference("feedbacks")


        val feedbackId = intent.getStringExtra("feedbackId")

        if (feedbackId != null) {

            carregarDetalhesFeedback(feedbackId)
        } else {
            Toast.makeText(this, "Erro ao carregar feedback!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun carregarDetalhesFeedback(feedbackId: String) {
        database.child(feedbackId).get().addOnSuccessListener { dataSnapshot ->
            val feedback = dataSnapshot.child("feedback").getValue(String::class.java)
            val rating = dataSnapshot.child("rating").getValue(String::class.java)
            val userName = dataSnapshot.child("userName").getValue(String::class.java)
            val date = dataSnapshot.child("date").getValue(Long::class.java)


            val formatoDaData = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val dataFormatada = date?.let { formatoDaData.format(Date(it)) }


            textViewFeedback.text = feedback
            textViewRating.text = "Rating: $rating"
            textViewUserName.text = "Usuário: $userName"
            textViewDate.text = "Data: $dataFormatada"
        }.addOnFailureListener {
            Toast.makeText(this, "Erro ao carregar os detalhes", Toast.LENGTH_SHORT).show()
        }
    }
}
