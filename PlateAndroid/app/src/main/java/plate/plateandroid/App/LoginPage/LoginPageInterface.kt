package plate.plateandroid.App.LoginPage

/**
 * Created by rennerll on 10/20/17.
 */
interface LoginPageInterface {
    interface View {
        fun showToast(message: String)
        fun goToPromotions(username: String)
    }

    interface Controller {
        fun init(view : View)
        fun tryToSignup(username: String)
        fun tryToLogin(username: String)
    }
}