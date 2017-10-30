package plate.plateandroid.Main

/**
 * Created by rennerll on 10/20/17.
 */
interface MainContract {
    interface View {
        fun showToast(message: String)
    }

    interface Presenter{
        fun init(view : View)
        fun showMessage()
    }
}