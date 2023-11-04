package belajar.android.kotlinapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import belajar.android.kotlinapi.MainModel
import belajar.android.kotlinapi.R
import com.bumptech.glide.Glide

class MainAdapter(
    val results : ArrayList<MainModel.Result>
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    class ViewHolder(val view: View):RecyclerView.ViewHolder(view) {
        val text = view.findViewById<TextView>(R.id.textView)
        val image = view.findViewById<ImageView>(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_main, parent,false)
        )
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        val result = results[position]
        holder.text.text = result.title
        Glide.with(holder.view)
            .load(result.image)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.image)
    }

    override fun getItemCount(): Int = results.size

    fun setData(data : List<MainModel.Result>){
        results.clear()
        results.addAll(data)
//        refresh recyclerviewnya
        notifyDataSetChanged()
    }
}