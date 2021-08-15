package com.libdumper.process

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.libdumper.R

/**
 * @author
 * The adapter of processes from the selected app
 */
class ProcessAdapter(var context: Context, var processlist:ArrayList<ProcessDetail>) :RecyclerView.Adapter<ProcessAdapter.ProcessHolder>(){
    private var onProcessListener: onProcessListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcessHolder {

        return ProcessHolder(LayoutInflater.from(context).inflate(R.layout.row_process,null))
    }

    override fun onBindViewHolder(holder: ProcessHolder, position: Int) {
        holder.processname.text=processlist[position].processname
        holder.view.setOnClickListener {
            onProcessListener?.onSelect(processlist[position],position)
        }
    }

    fun setProcessListener(onProcessListener: onProcessListener){
        this.onProcessListener=onProcessListener
    }
    override fun getItemCount(): Int {
        return processlist.size
    }
    class ProcessHolder(view:View):RecyclerView.ViewHolder(view){
        var view=view
        var processname=view.findViewById<TextView>(R.id.processname)
    }
}