package com.example.first_application


import android.os.Bundle
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*



class MainActivity : AppCompatActivity() {

    //Crear instancia de la base de datos  Firebase y referenciar el arbol "home"
    val database = FirebaseDatabase.getInstance()

    //se crea la referencia para apuntar al dato que se requiere en la base de datos.
    val refHome = database.getReference("home")
    val refLuces: DatabaseReference = refHome.child("luces")
    val refLuz_sala: DatabaseReference = refLuces.child("luz_sala")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //referencia del boton toggle
        val btntg = findViewById<ToggleButton>(R.id.toggle)

        //Se ejecuta la funcion para cambiar el estado del dato apuntado.
        control_LED(refLuz_sala,btntg);

    }

    //funcion para leer y modificar el estado de un dato en la base de datos firebase mediante boton
    private fun control_LED(refLED: DatabaseReference, TgButton: ToggleButton): Unit {

        //Si el boton es presionado, debe registrarse el valor actual en la base de datos
        TgButton.setOnCheckedChangeListener { buttonView, isChecked ->
            refLED.setValue(isChecked)
        }

        //Evento de escucha para cambios de estado del dato referenciado
        refLED.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val estado_led = dataSnapshot.value as Boolean
                //Si el estado cambia, el boton toggle tambien cambia su estado en la app
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