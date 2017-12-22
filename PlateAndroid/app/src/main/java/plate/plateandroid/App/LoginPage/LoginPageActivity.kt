package plate.plateandroid

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.login_page.*
import plate.plateandroid.App.LoginPage.LoginPageController
import plate.plateandroid.App.LoginPage.LoginPageInterface
import plate.plateandroid.App.PromotionList.PromotionListActivity


class LoginPageActivity : AppCompatActivity(), LoginPageInterface.View {
    companion object {
        val USER_NAME = "userName"
        val IS_LOGGED_IN = "isLoggedIn"
        val LOGIN_PREFERENCE = "loginPreference"
    }

    val controller: LoginPageInterface.Controller = LoginPageController()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        controller.init(this)
        signup_button.setOnClickListener {
            controller.tryToSignup(username_input.text.toString())
        }
        login_button.setOnClickListener {
            controller.tryToLogin(username_input.text.toString())
        }
    }

    override fun goToPromotions(username: String) {
        val settings = getSharedPreferences(LOGIN_PREFERENCE, 0)
        settings.edit().apply {
            putBoolean(IS_LOGGED_IN, true)
            putString(USER_NAME, username)
            commit()
        }

        val intent = Intent(this, PromotionListActivity::class.java)
        val bundle = Bundle()
        bundle.putString(USER_NAME, username)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
