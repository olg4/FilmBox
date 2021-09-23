package com.mobg5.filmbox.ui.login

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mobg5.filmbox.MainActivity
import com.mobg5.filmbox.R
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginState: LoginFormState
    private lateinit var context: Context
    private lateinit var listOfUsers: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        context = this@LoginActivity

        val username = findViewById<AutoCompleteTextView>(R.id.username)
        val login = findViewById<Button>(R.id.login)
        val constraintLayout = findViewById<ConstraintLayout>(R.id.container)
        val appLogo = findViewById<CircleImageView>(R.id.login_screen_logo)
        val signIn = findViewById<ConstraintLayout>(R.id.signInScreen)

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            loginState = it ?: return@Observer

            if (loginState.usernameError != null) {
                showLoginFailed()
            }

        })

        listOfUsers = mutableListOf()
        loginViewModel.getAllUsers(context).observe(this@LoginActivity, {
            if (it != null) {
                for(i in it.indices) {
                    listOfUsers.add(it[i].username)
                }
            }
        })
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, listOfUsers)
        username.setAdapter(adapter)

        loginViewModel.loginResult.observe(this@LoginActivity, Observer { it ->
            val loginResult = it ?: return@Observer
            val calendar: Calendar = Calendar.getInstance()
            val date: Date = calendar.time

            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            // load database to see if the user already exists otherwise add this user
            val exists: LiveData<Boolean> = loginViewModel.isUserExists(context, username.text.toString())
            exists.observe(this@LoginActivity, {
                if (it) loginViewModel.updateUser(context, username.text.toString(), date)
                else loginViewModel.addUser(context, username.text.toString(), date)
            })

            loginToMainActivity()
        })

        username.setOnEditorActionListener { _, actionId, _ ->
            loginViewModel.loginDataChanged(
                username.text.toString())
            if (loginState.usernameError == null) {
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> {
                        loginViewModel.login(
                            username.text.toString()
                        )
                    }
                }
            }
            false
        }

        constraintLayout.viewTreeObserver.addOnGlobalLayoutListener {
            val rec = Rect()
            constraintLayout.getWindowVisibleDisplayFrame(rec)

            //finding screen height
            val screenHeight = constraintLayout.rootView.height

            //finding keyboard height
            val keypadHeight = screenHeight - rec.bottom

            if (keypadHeight > screenHeight * 0.15) {
                ObjectAnimator.ofFloat(signIn, "translationY", -500f).apply {
                    appLogo.visibility = View.INVISIBLE
                    duration = 200
                    start()
                }

            } else {
                ObjectAnimator.ofFloat(signIn, "translationY", 0f).apply {
                    appLogo.visibility = View.VISIBLE
                    duration = 200
                    start()
                }
            }
        }

        login.setOnClickListener {
            loginViewModel.loginDataChanged(
                username.text.toString())

            if (loginState.usernameError == null) {
                loginViewModel.login(
                    username.text.toString())
            }
        }

        // hide keyboard when the user clicks anywhere but on editText or keyboard
        constraintLayout.setOnClickListener {
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(constraintLayout.windowToken, 0)
        }
    }

    private fun loginToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        //Complete and destroy login activity once successful
        finish()
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val loggedMsg = getString(R.string.welcome)
        val displayName = model.displayName

        val sharedPref = getSharedPreferences("pref", Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("username", displayName)
            apply()
        }

        Toast.makeText(
                applicationContext,
                "$loggedMsg $displayName",
                Toast.LENGTH_LONG
        ).show()

    }

    private fun showLoginFailed() {
        Toast.makeText(applicationContext, "invalid", Toast.LENGTH_SHORT).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}