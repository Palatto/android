package plate.plateandroid.App.LoginPage

import plate.plateandroid.App.LoginPage.Complements.LoginAPI
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.regex.Pattern


/**
 * Created by rennerll on 10/20/17.
 */
class LoginPageController : LoginPageInterface.Controller {
    lateinit var view: LoginPageInterface.View
    lateinit var loginAPI: LoginAPI

    override fun init(view: LoginPageInterface.View) {
        this.view = view
        this.loginAPI = Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://plate-heroku-database.herokuapp.com/")
                .build()
                .create(LoginAPI::class.java)
    }


    override fun tryToSignup(username: String) {
        val pattern = Pattern.compile("^[-_+.a-zA-Z0-9!@#\$%^*()]{1,18}\$")
        val matcher = pattern.matcher(username)

        if(!matcher.matches()) {
            view.showToast("Invalid username entered. Please, try again.")
            return
        }

        val observableWithResponse = loginAPI.registerUser(username)

        observableWithResponse.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { successfullyRegistered ->
                            if(successfullyRegistered) {
                                view.showToast("Signed up with success.")
                                view.goToPromotions(username)
                            } else {
                                view.showToast("This username is already taken.")
                            }
                        },
                        { error ->
                            view.showToast("Something went wrong. Please, check your internet connection and try again.")
                            error.printStackTrace()
                        }
                )
    }

    override fun tryToLogin(username: String) {
        val pattern = Pattern.compile("^[-_+.a-zA-Z0-9!@#\$%^*()]{1,18}\$")
        val matcher = pattern.matcher(username)

        if(!matcher.matches()) {
            view.showToast("Invalid username entered. Please, try again.")
            return
        }
        val observableWithResponse = loginAPI.checkUser(username)

        observableWithResponse.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { successfullyChecked ->
                            if(successfullyChecked) {
                                view.showToast("Logged in with success.")
                                view.goToPromotions(username)
                            } else {
                                view.showToast("This username is not in our database.")
                            }
                        },
                        { error -> // do some status code here to identify errors - connection, etc...
                            view.showToast("Something went wrong. Please, check your internet connection and try again.")
                            error.printStackTrace()
                        }
                )
    }
}