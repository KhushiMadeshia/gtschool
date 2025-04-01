package com.example.gtschool


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class SignUp : AppCompatActivity() {


    private lateinit var loginTextView: TextView
    private lateinit var roleSpinner: Spinner
    private lateinit var selectedRole:String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        roleSpinner = findViewById(R.id.roleSpinner)
        loginTextView = findViewById(R.id.login_text)

        // Set up role spinner
        val roles = arrayOf("Sign Up as ", "Teacher", "Student") // Add your own roles
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, roles)
        roleSpinner.adapter = adapter

        roleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    selectedRole = ""  // Invalid selection
                } else {
                    selectedRole = roles[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }



        // Navigate to Login Page
        loginTextView.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}
