package com.mehmetfatihaksu.sahibindencase.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mehmetfatihaksu.sahibindencase.R
import com.mehmetfatihaksu.sahibindencase.common.Constants
import com.mehmetfatihaksu.sahibindencase.common.DateTimeConverter
import com.mehmetfatihaksu.sahibindencase.databinding.ItemQuestionBinding
import com.mehmetfatihaksu.sahibindencase.domain.model.response.Item
import com.mehmetfatihaksu.sahibindencase.presentation.home.listener.QuestionItemClickListener

class QuestionAdapter(private val context: Context, private val questionItemClickListener: QuestionItemClickListener) : PagingDataAdapter<Item , QuestionAdapter.ViewHolder>(QUESTION_COMPARATOR) {

    class ViewHolder(private val itemBinding : ItemQuestionBinding , val context: Context, private val questionItemClickListener: QuestionItemClickListener) : RecyclerView.ViewHolder(itemBinding.root){

        fun bindItems(question : Item){
            itemBinding.textViewUserName.text = question.owner.display_name
            itemBinding.textViewTitle.text = question.title
            itemBinding.textViewCreatedDate.text = context.getString(R.string.created_date,DateTimeConverter.getDateTime(question.creation_date, Constants.LONG_DATE_TIME_FORMAT))
            itemBinding.textViewAnswerCount.text = context.getString(R.string.answer_count ,question.answer_count.toString())
            Glide.with(context)
                .load(question.owner.profile_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .centerCrop()
                .into(itemBinding.imageViewProfile)

            itemView.setOnClickListener { questionItemClickListener.onItemClick(question.question_id) }
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       getItem(position)?.let {
           holder.bindItems(it)
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false), context, questionItemClickListener)
    }

    companion object{
        val QUESTION_COMPARATOR = object : DiffUtil.ItemCallback<Item>(){
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.question_id == newItem.question_id
            }
            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }
        }
    }
}