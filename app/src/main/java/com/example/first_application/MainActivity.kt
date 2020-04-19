package com.example.first_application


import android.os.Bundle
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*



class MainActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    val refHome = database.getReference("home")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btntg = findViewById<ToggleButton>(R.id.toggle)

        val refLuces: DatabaseReference = refHome.child("luces")
        val refLuz_sala: DatabaseReference = refLuces.child("luz_sala")

        control_LED(refLuz_sala,btntg);

    }

    private fun control_LED(refLED: DatabaseReference, TgButton: ToggleButton): Unit {

        TgButton.setOnCheckedChangeListener { buttonView, isChecked ->
            refLED.setValue(isChecked)
        }

        refLED.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val estado_led = dataSnapshot.value as Boolean
                TgButton.setChecked(estado_led)
                if (estado_led) {
                    TgButton.setTextOn("APAGAR")
                } else {
                    TgButton.setTextOff("ENCENDER")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}