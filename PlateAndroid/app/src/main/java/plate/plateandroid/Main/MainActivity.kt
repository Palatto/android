package plate.plateandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import plate.plateandroid.Main.MainContract
import plate.plateandroid.Main.MainPresenter

class MainActivity : AppCompatActivity(), MainContract.View {
    val presenter: MainContract.Presenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.init(this)
        button_for_testing.setOnClickListener {
            presenter.showMessage()
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }
}
