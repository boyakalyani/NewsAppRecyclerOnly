package com.example.newsapprecycleronly


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private var listener: MainActivity):RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val items=ArrayList<NewsDataClass>()
    class NewsViewHolder (item: View):RecyclerView.ViewHolder(item){
        var titleView:TextView=itemView.findViewById(R.id.title_id)
        var authorView:TextView=itemView.findViewById(R.id.author_id)
        var imgView:ImageView=itemView.findViewById(R.id.image_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        val viewHolder=NewsViewHolder(view)
        view.setOnClickListener(){
            listener.onClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        var CurrentNews=items[position]
        holder.titleView.text=CurrentNews.description
        holder.authorView.text=CurrentNews.price
        //now we will aceess image from image url to display it we will use glide
        Glide.with(holder.itemView.context).load(CurrentNews.imageUrl).into(holder.imgView)

    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun updateData(newData:java.util.ArrayList<NewsDataClass>){
        items.clear()
        items.addAll(newData)
        notifyDataSetChanged()
    }
}
interface OnNewsClick{
    fun onClicked(newsDataClass: NewsDataClass)
}