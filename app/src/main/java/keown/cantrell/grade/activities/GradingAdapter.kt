package keown.cantrell.grade.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import keown.cantrell.grade.R
import keown.cantrell.grade.helpers.readImageFromPath
import keown.cantrell.grade.models.GradingModel
import kotlinx.android.synthetic.main.card_grading.view.*
import kotlinx.android.synthetic.main.card_grading.view.gradingTitle
import kotlinx.android.synthetic.main.card_grading.view.description
import kotlinx.android.synthetic.main.card_grading.view.Other
import kotlinx.android.synthetic.main.card_grading.view.Grade



interface GradingListener {
    fun onGradingClick(grading: GradingModel)
}

class GradingAdapter constructor(
        private var gradings: List<GradingModel>,
        private val listener: GradingListener
) : RecyclerView.Adapter<GradingAdapter.MainHolder>() {

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val grading = gradings[holder.adapterPosition]
        holder.bind(grading, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.card_grading,
                        parent,
                        false
                )
        )
    }
    override fun getItemCount(): Int = gradings.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(grading: GradingModel, listener: GradingListener) {
            itemView.gradingTitle.text = grading.title
            itemView.description.text = grading.description
            itemView.Other.text = grading.Other
            itemView.Grade.text = grading.Grade
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, grading.image))
            itemView.setOnClickListener { listener.onGradingClick(grading) }

        }
    }
}

