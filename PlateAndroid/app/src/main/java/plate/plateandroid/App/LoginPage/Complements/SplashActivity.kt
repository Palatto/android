package plate.plateandroid.App.LoginPage.Complements

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import plate.plateandroid.App.PromotionList.PromotionListActivity
import plate.plateandroid.LoginPageActivity
import plate.plateandroid.R


/**
 * Created by rennerll on 12/19/17.
 */
public class SplashActivity: AppCompatActivity() {
    companion object {
        val USER_NAME = "userName"
        val IS_LOGGED_IN = "isLoggedIn"
        val LOGIN_PREFERENCE = "loginPreference"
    }

    val SPLASH_DISPLAY_LENGTH = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        Handler().postDelayed({
            val settings = getSharedPreferences(LOGIN_PREFERENCE, 0)
            val isLoggedIn = settings.getBoolean(IS_LOGGED_IN, false)

            if (isLoggedIn) {
                val intent = Intent(this, PromotionListActivity::class.java)
                val bundle = Bundle()
                val username = settings.getString(USER_NAME, "")
                bundle.putString(USER_NAME, username)
                intent.putExtras(bundle)
                startActivity(intent)
            } else {
                val intent = Intent(this, LoginPageActivity::class.java)
                startActivity(intent)
            }

            finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}
