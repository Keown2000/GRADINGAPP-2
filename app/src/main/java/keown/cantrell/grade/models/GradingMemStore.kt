package keown.cantrell.grade.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class GradingMemStore : GradingStore, AnkoLogger {

    val gradings = ArrayList<GradingModel>()

    override fun findAll(): List<GradingModel> {
        return gradings
    }

    override fun create(grading: GradingModel) {
        gradings.add(grading)
        logAll();
    }
    override fun update(grading: GradingModel) {
        var foundGrading: GradingModel? = gradings.find { p -> p.id == grading.id }
        if (foundGrading != null) {
            foundGrading.title = grading.title
            foundGrading.description = grading.description
            foundGrading.Grade = grading.Grade
            foundGrading.Other = grading.Other
            foundGrading.image = grading.image
            logAll()
        }
    }
    override fun delete(grading: GradingModel) {
        gradings.remove(grading)
    }
    fun logAll() {
        gradings.forEach{ info("${it}") }
    }
}

