package keown.cantrell.grade.models

interface GradingStore {
    fun findAll(): List<GradingModel>
    fun create(grading: GradingModel)
    fun update(grading: GradingModel)
    fun delete(grading: GradingModel)
}

