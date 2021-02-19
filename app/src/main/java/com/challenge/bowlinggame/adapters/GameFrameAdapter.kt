package com.challenge.bowlinggame.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.challenge.bowlinggame.views.FrameViewHolder
import com.challenge.bowlinggame_shared.GameFrame

class GameFrameAdapter(private var list:List<GameFrame>):RecyclerView.Adapter<FrameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrameViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        return FrameViewHolder(inflater,parent)
    }

    override fun onBindViewHolder(holder: FrameViewHolder, position: Int) {
        val frame=list[position]
        holder.bind(frame)
    }

    override fun getItemCount(): Int =list.size

    fun setlist(newlist:List<GameFrame>){
        list=newlist
        this.notifyDataSetChanged()
    }
}