package com.optic.uberclonekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.optic.uberclonekotlin.databinding.ActivityMainBinding
import com.optic.uberclonekotlin.providers.AuthProvider
import com.optic.uberclonekotlin.ui.MapActivity2

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val authProvider = AuthProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        binding.btnRegister.setOnClickListener { goToRegister() }
        binding.btnLogin.setOnClickListener { login() }

    }

    private fun login() {
        val email = binding.textFieldEmail.text.toString()
        val password = binding.textFieldPassword.text.toString()
        
        if (isValidForm(email, password)) {
            authProvider.login(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    goToMap()
                } else {
                    Toast.makeText(this, "usuario no registrado", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
    
    private fun isValidForm(email: String, password: String): Boolean {
        
        if (email.isEmpty()) {
            Toast.makeText(this, "Ingresa tu correo electronico", Toast.LENGTH_SHORT).show()
            return false
        }
        
        if (password.isEmpty()) {
            Toast.makeText(this, "Ingresa tu contraseña", Toast.LENGTH_SHORT).show()
            return false
        }
        
        return true
    }

    private fun goToRegister() {
        val i = Intent(this, RegisterActivity::class.java)
        startActivity(i)
    }

    private fun goToMap(){
        val intent = Intent(this, MapActivity2::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        if (authProvider.existSession()){
            goToMap()
        }
    }

}