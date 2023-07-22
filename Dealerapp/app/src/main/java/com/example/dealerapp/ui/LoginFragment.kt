package com.example.dealerapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.R

class LoginFragment : Fragment() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(com.example.dealerapp.R.layout.fragment_login, container, false)

        usernameEditText = view.findViewById(com.example.dealerapp.R.id.usernameEditText)
        passwordEditText = view.findViewById(com.example.dealerapp.R.id.pinEditText)
        loginButton = view.findViewById(com.example.dealerapp.R.id.startButton)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Perform login authentication
            val isAuthenticated = authenticateUser(username, password)

            if (isAuthenticated) {
                // Login success, perform necessary actions
                Toast.makeText(activity, "login Successful", Toast.LENGTH_SHORT).show()
                val navController = Navigation.findNavController(
                    requireActivity(),
                    com.example.dealerapp.R.id.nav_host_fragment_content_main
                )
                navController.navigate(com.example.dealerapp.R.id.action_LoginFragment_to_FirstFragment)
            } else {
                // Login failed, show error message
                Toast.makeText(activity, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun authenticateUser(username: String, password: String): Boolean {
        // Implement your authentication logic here
        // This is just a sample implementation
        return username == "rexima" && password == "264108"
    }
}

