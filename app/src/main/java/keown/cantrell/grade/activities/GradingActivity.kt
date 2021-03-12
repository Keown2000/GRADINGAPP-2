package keown.cantrell.grade.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import keown.cantrell.grade.FavoriteFragment
import keown.cantrell.grade.HomeFragment
import keown.cantrell.grade.ProgressFragment
import keown.cantrell.grade.R
import keown.cantrell.grade.helpers.readImage
import keown.cantrell.grade.helpers.readImageFromPath
import keown.cantrell.grade.helpers.showImagePicker
import keown.cantrell.grade.main.MainApp

import keown.cantrell.grade.models.GradingModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.description
import kotlinx.android.synthetic.main.activity_main.gradingTitle
import kotlinx.android.synthetic.main.activity_main.Grade
import kotlinx.android.synthetic.main.activity_main.Other
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_grading_list.*
import kotlinx.android.synthetic.main.card_grading.*

import org.jetbrains.anko.toast

class GradingActivity : AppCompatActivity(), AnkoLogger {
    lateinit var homeFragment: HomeFragment
    lateinit var favoriteFragment:FavoriteFragment
    lateinit var progressFragment: ProgressFragment
    var grading = GradingModel()
    lateinit var app: MainApp
    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  //inflates the layout
        app = application as MainApp
        var edit = false

        btnAdd.setOnClickListener() {
            grading.title = gradingTitle.text.toString()
            grading.description = description.text.toString()
            grading.Other = Other.text.toString()
            grading.Grade = Grade.text.toString()
            if (grading.title.isEmpty()) {
                toast("Please enter the first field")
            } else {
                if (edit) {
                    app.gradings.update(grading.copy())  //if first field is filled, it will allow gradings to update
                } else {
                    app.gradings.create(grading.copy())
                }
            }

            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

        //creating a bottom navigation to transition through fragments using a fragment manager and using the item IDs
        val bottomNavigation : BottomNavigationView = findViewById(R.id.btm_nav)
        bottomNavigation.setOnNavigationItemSelectedListener{ item ->

            when (item.itemId) {


                R.id.home -> {
                    homeFragment = HomeFragment()
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_layout, homeFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()

                }
                R.id.favorite -> {
                    favoriteFragment = FavoriteFragment()
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_layout, favoriteFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()

                }
                R.id.progress -> {
                    progressFragment = ProgressFragment()
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.frame_layout, progressFragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .commit()

                }
            }
            true
        }


        if (intent.hasExtra("grading_edit")) {
            edit = true
            grading = intent.extras?.getParcelable<GradingModel>("grading_edit")!!
            gradingTitle.setText(grading.title)
            description.setText(grading.description)
            Other.setText(grading.Other)
            Grade.setText(grading.Grade)
            gradingImage.setImageBitmap(readImageFromPath(this, grading.image))
            if (grading.image != null) {
                chooseImage.setText(R.string.change_grading_image)
            }
            btnAdd.setText(R.string.save_grading)
        }
        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)   //if image button presses.. it will open the image picker to open
        }
    }


//function to allow the exit and delete button to be viewed through the navigation bar using an inflater
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_grading, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //allows image to be viewed in the list activity and allows it to be saved in the main activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    grading.image = data.getData().toString()
                    gradingImage.setImageBitmap(readImage(this, resultCode, data))
                }
            }
        }
    }

//function to allow a delete or edit event to happen through clickable item
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                app.gradings.delete(grading)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}



