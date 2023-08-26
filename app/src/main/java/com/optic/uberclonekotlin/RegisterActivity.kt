package com.optic.uberclonekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.optic.uberclonekotlin.databinding.ActivityRegisterBinding
import com.optic.uberclonekotlin.model.Client
import com.optic.uberclonekotlin.providers.AuthProvider
import com.optic.uberclonekotlin.providers.ClientProvider

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val authProvider = AuthProvider()
    private val clientProvider = ClientProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        binding.btnGoToLogin.setOnClickListener { goToLogin() }
        binding.btnRegister.setOnClickListener { register() }
    }

    private fun register() {
        val name = binding.textFieldName.text.toString()
        val lastname = binding.textFieldLastname.text.toString()
        val email = binding.textFieldEmail.text.toString()
        val phone = binding.textFieldPhone.text.toString()
        val password = binding.textFieldPassword.text.toString()
        val confirmPassword = binding.textFieldConfirmPassword.text.toString()

        if (isValidForm(name, lastname, email, phone, password, confirmPassword)) {
            Toast.makeText(this, "Formulario es valido", Toast.LENGTH_SHORT).show()
            authProvider.register(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    val client = Client(
                        id = authProvider.getId(),
                        name = name,
                        lastname = lastname,
                        email = email,
                        phone = phone
                    )
                    clientProvider.create(client).addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Ok no", Toast.LENGTH_SHORT).show()
                        }
                    }

                }else{
                    Toast.makeText(this, "Ok no", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun isValidForm(
        name: String,
        lastname: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ): Boolean {

        if (name.isEmpty()) {
            Toast.makeText(this, "Debes ingresar tu nombre", Toast.LENGTH_SHORT).show()
            return false
        }
        if (lastname.isEmpty()) {
            Toast.makeText(this, "Debes ingresar tu apellido", Toast.LENGTH_SHORT).show()
            return false
        }
        if (email.isEmpty()) {
            Toast.makeText(this, "Debes ingresar tu correo electronico", Toast.LENGTH_SHORT).show()
            return false
        }
        if (phone.isEmpty()) {
            Toast.makeText(this, "Debes ingresar tu telefono", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Debes ingresar la contraseña", Toast.LENGTH_SHORT).show()
            return false
        }
        if (confirmPassword.isEmpty()) {
            Toast.makeText(this, "Debes ingresar la confirmacion de la contraseña", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password != confirmPassword) {
            Toast.makeText(this, "las contraseñas deben coincidir", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.length < 6) {
            Toast.makeText(this, "la contraseña deben tener al menos 6 caracteres", Toast.LENGTH_LONG).show()
            return false
        }

        return true

    }

    private fun goToLogin() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}