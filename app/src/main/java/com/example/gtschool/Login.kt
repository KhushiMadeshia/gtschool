package com.example.gtschool

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gtschool.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Login : AppCompatActivity() {
    private val  binding : ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth


    override fun onStart() {
        super.onStart()

        //check user loged in
        val currentUser :FirebaseUser?=auth.currentUser
        if(currentUser!=null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //Initialize firebase auth
        auth= FirebaseAuth.getInstance()



        binding.loginButton.setOnClickListener{
            val email=binding.editTextEmailLogin.text.toString()
            val password= binding.editTextPasswordLogin.text.toString()

            if(email.isEmpty())
            {
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
            }
            else if (password.isEmpty())
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
            else {
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener{task->
                        if(task.isSuccessful)
                        {
                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this, "Login Failed:${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        // Navigate to Login Page
        binding.signuptext.setOnClickListener {
            startActivity(Intent(this,SignUp::class.java))
            finish()
        }
    }
}