package com.mobg5.filmbox.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.mobg5.filmbox.data.LoginRepository
import com.mobg5.filmbox.data.Result
import com.mobg5.filmbox.R
import com.mobg5.filmbox.data.model.LoggedInUser
import java.util.*

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String) {
        // can be launched UserListsMoviesDao a separate asynchronous job
        val result = loginRepository.login(username)

        if (result is Result.Success) {
            _loginResult.value =
                LoginResult(success = LoggedInUserView(displayName = result.data.username))
        } else {
            _loginResult.value = LoginResult(error = R.string.login_failed)
        }
    }

    fun addUser(context: Context, username: String, date: Date) {
        loginRepository.addUser(context, username, date)
    }

    fun isUserExists(context: Context, username: String): LiveData<Boolean> {
        return loginRepository.isUserExists(context, username)
    }

    fun updateUser(context: Context, username: String, date: Date) {
        loginRepository.updateUser(context, username, date)
    }

    fun getAllUsers(context: Context): LiveData<List<LoggedInUser>> {
        return loginRepository.getAllUsers(context)
    }

    fun loginDataChanged(username: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            // line 46 used if both login and email is accepted
            //username.isNotBlank()
            false
        }
    }
}