package keown.cantrell.grade.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import keown.cantrell.grade.helpers.exists
import keown.cantrell.grade.helpers.read
import keown.cantrell.grade.helpers.write
import org.jetbrains.anko.AnkoLogger
import java.util.*

val JSON_FILE = "gradings.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<GradingModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class GradingJSONStore : GradingStore, AnkoLogger {

    val context: Context
    var gradings = mutableListOf<GradingModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<GradingModel> {
        return gradings
    }

    override fun create(grading: GradingModel) {
        grading.id = generateRandomId()
        gradings.add(grading)
        serialize()
    }


    override fun delete(grading: GradingModel) {
        gradings.remove(grading)
        serialize()
    }
    override fun update(grading: GradingModel) {
        val gradingsList = findAll() as ArrayList<GradingModel>
        var foundGrading: GradingModel? = gradingsList.find { p -> p.id == grading.id }
        if (foundGrading != null) {
            foundGrading.title = grading.title
            foundGrading.description = grading.description
            foundGrading.Other = grading.Other
            foundGrading.Grade = grading.Grade
            foundGrading.image = grading.image

        }
        serialize()
    }


    private fun serialize() {
        val jsonString = gsonBuilder.toJson(gradings, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        gradings = Gson().fromJson(jsonString, listType)
    }
}

