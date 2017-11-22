package plate.plateandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import plate.plateandroid.Main.MainContract
import plate.plateandroid.Main.MainPresenter
import plate.plateandroid.Main.PromotionAdapter
import plate.plateandroid.Main.PromotionModel

class MainActivity : AppCompatActivity(), MainContract.View {
    val presenter: MainContract.Presenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.init(this)
        presenter.initializePromotionList()
    }

    override fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showAlert() {
        // fragment work here
    }

    override fun setItemsList(promotions: MutableList<PromotionModel>) {
        with(promotionListItems) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = PromotionAdapter(promotions)
        }
    }
}
