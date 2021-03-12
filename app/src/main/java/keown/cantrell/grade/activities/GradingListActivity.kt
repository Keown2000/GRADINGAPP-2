package keown.cantrell.grade.activities

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import keown.cantrell.grade.R

import keown.cantrell.grade.main.MainApp
import keown.cantrell.grade.models.GradingModel
import kotlinx.android.synthetic.main.activity_grading_list.*
import kotlinx.android.synthetic.main.card_grading.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult

class GradingListActivity : AppCompatActivity(), GradingListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grading_list)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = GradingAdapter(app.gradings.findAll(), this)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.nav_donate -> startActivityForResult<GradingActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onGradingClick(grading: GradingModel) {
        startActivityForResult(intentFor<GradingActivity>().putExtra("grading_edit", grading), 0)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

}

