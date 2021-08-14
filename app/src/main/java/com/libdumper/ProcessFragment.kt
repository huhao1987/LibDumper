package com.libdumper

import android.app.AlertDialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.libdumper.dumper.Process
import java.util.*


class ProcessFragment: DialogFragment() {
    private var processlist:ArrayList<Process> ? = null
    private var onProcessListener:onProcessListener?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            processlist = it.getParcelableArrayList("processlist")
        }
    }

    override fun onResume() {
        val params: ViewGroup.LayoutParams = getDialog().window!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        getDialog().window!!.attributes = params as WindowManager.LayoutParams

        super.onResume()
    }
    override fun onCreateView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater?.inflate(R.layout.fragment_processlist,container)
        processlist?.apply {
            var processlistview= view?.findViewById<RecyclerView>(R.id.processlistview)
            var llm = LinearLayoutManager(activity)
            llm!!.orientation = RecyclerView.VERTICAL
            processlistview?.layoutManager = llm
            var processAdapter=ProcessAdapter(activity,this).also {
                it.setProcessListener(object:onProcessListener{
                    override fun onSelect(process: Process, position: Int) {
                        onProcessListener?.onSelect(process,position)
                        this@ProcessFragment.dismiss()
                    }
                })
            }
            processlistview?.adapter = processAdapter
        }
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE)

        return view
    }

    fun setProcessListener(onProcessListener: onProcessListener){
        this.onProcessListener=onProcessListener
    }

    companion object {
        @JvmStatic
        fun newInstance(processlist:ArrayList<Process>) =
            ProcessFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList("processlist", processlist)
                }
            }
    }
}
interface onProcessListener{
    fun onSelect(process: Process,position:Int)
}

