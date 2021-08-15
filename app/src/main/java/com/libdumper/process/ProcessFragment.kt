package com.libdumper.process

import android.app.DialogFragment
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.libdumper.R
import java.util.*


/**
 * @author Hao
 * A dialog fragment for display the processes of the selected app
 */
class ProcessFragment: DialogFragment() {
    private var processlist:ArrayList<ProcessDetail> ? = null
    private var onProcessListener: onProcessListener?=null

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
            var processAdapter= ProcessAdapter(activity,this).also {
                it.setProcessListener(object: onProcessListener {
                    override fun onSelect(process: ProcessDetail, position: Int) {
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
        fun newInstance(processlist:ArrayList<ProcessDetail>) =
            ProcessFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList("processlist", processlist)
                }
            }
    }
}
interface onProcessListener{
    fun onSelect(process: ProcessDetail, position:Int)
}

