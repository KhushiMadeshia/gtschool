package com.example.gtschool


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gtschool.databinding.ActivitySignUpBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {



    private lateinit var selectedRole:String

    private val binding:ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Intialize firebase auth
        auth =FirebaseAuth.getInstance()


        // Set up role spinner
        val roles = arrayOf("Select your role ", "Teacher", "Student") // Add your own roles
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, roles)
        binding.roleSpinner.adapter = adapter

        binding.roleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    selectedRole = ""  // Invalid selection
                } else {
                    selectedRole = roles[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        //sigup button
        binding.SignUpButton.setOnClickListener{
            //get values
            val email=binding.editTextEmailAddressSignUp.text.toString()
            val name=binding.editTextName.text.toString()
            val password=binding.editTextPasswordSignUp.text.toString()
            val confirmpassword =binding.editTextConfirmPasswordSignUp.text.toString()


            //check
            if(email.isEmpty()||name.isEmpty()||password.isEmpty()||confirmpassword.isEmpty()){
                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
            }
            else if (password !=confirmpassword)
            {
                Toast.makeText(this, "Password and Confirm password must be same", Toast.LENGTH_SHORT).show()
            }
            else if (selectedRole.isEmpty())
            {
                Toast.makeText(this, "Please select your role", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this){ task ->
                        if (task.isSuccessful)
                        {
                            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,Login::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this, "Registration Failed :${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }

            }
        }





        // Navigate to Login Page
        binding.loginText.setOnClickListener{
            startActivity(Intent(this,Login::class.java))
            finish()
        }
    }
}

