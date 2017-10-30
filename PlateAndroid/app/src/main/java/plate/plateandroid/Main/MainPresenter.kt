package plate.plateandroid.Main

/**
 * Created by rennerll on 10/20/17.
 */
class MainPresenter: MainContract.Presenter {

    lateinit var view: MainContract.View

    override fun init(view: MainContract.View) {
        this.view = view
    }

    override fun showMessage() {
        view.showToast("Let's go!!!")
    }

}