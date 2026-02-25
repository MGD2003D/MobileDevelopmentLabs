package com.example.messenger.ui.feed

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.example.messenger.data.model.Message

class MessageAdapter(
    private val onLikeClick: (Message) -> Unit
) : ListAdapter<Message, MessageAdapter.ViewHolder>(DiffCallback) {

    private val avatarColors = listOf(
        0xFF6200EE.toInt(),
        0xFF03DAC5.toInt(),
        0xFFE91E63.toInt(),
        0xFF4CAF50.toInt(),
        0xFFFF9800.toInt(),
        0xFF2196F3.toInt()
    )

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAvatar: TextView = view.findViewById(R.id.tv_avatar)
        val tvUserName: TextView = view.findViewById(R.id.tv_user_name)
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val tvBody: TextView = view.findViewById(R.id.tv_body)
        val ivLike: ImageView = view.findViewById(R.id.iv_like)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = getItem(position)

        val avatarBg = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(avatarColors[message.userId % avatarColors.size])
        }
        holder.tvAvatar.background = avatarBg
        holder.tvAvatar.text = message.title.first().uppercase()

        holder.tvUserName.text = "Пользователь ${message.userId}"
        holder.tvTitle.text = message.title
        holder.tvBody.text = message.body

        holder.ivLike.setImageResource(
            if (message.isLiked) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
        )
        holder.ivLike.setOnClickListener { onLikeClick(message) }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Message, newItem: Message) =
            oldItem == newItem
    }
}
