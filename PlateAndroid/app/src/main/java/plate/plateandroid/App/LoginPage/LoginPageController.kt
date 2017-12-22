package plate.plateandroid.App.LoginPage

import plate.plateandroid.App.LoginPage.Complements.LoginAPI
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

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
        if(username == "") { // future regex validation
            view.showToast("Invalid username!")
            return
        }
        val observableWithResponse = loginAPI.registerUser(username)

        observableWithResponse.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { successfullyRegistered ->
                            if(successfullyRegistered) {
                                view.showToast("Signed up with success!")
                                view.goToPromotions(username)
                            } else {
                                view.showToast("This username is already taken!") // assumes username duplicate error always
                            }
                        },
                        { error -> // do some status code here to identify more errors - connection, etc...
                            view.showToast("Something went wrong. Please, try again!")
                            error.printStackTrace()
                        }
                )
    }

    override fun tryToLogin(username: String) {
        if(username == "") { //future regex validation
            view.showToast("Invalid username!")
            return
        }
        val observableWithResponse = loginAPI.checkUser(username)

        observableWithResponse.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { successfullyChecked ->
                            if(successfullyChecked) {
                                view.showToast("Logged in with success!")
                                view.goToPromotions(username)
                            } else {
                                view.showToast("This username does not exist!") // assumes username not exists error always
                            }
                        },
                        { error -> // do some status code here to identify errors - connection, etc...
                            view.showToast("Something went wrong. Please, try again!")
                            error.printStackTrace()
                        }
                )
    }
}