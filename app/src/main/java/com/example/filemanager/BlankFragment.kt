package com.example.filemanager

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment : Fragment() {
    // TODO: Rename and change types of parameters

    var rootOfDirectory: String? = null
    lateinit var listEntry:Array<File>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            rootOfDirectory = it.getString(ARG_PARAM1)

        }
        Log.v("TAG","createfragment")
        Log.v("TAG","${rootOfDirectory} in createFragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv = view.findViewById<RecyclerView>(R.id.rc)
        listEntry = File(rootOfDirectory).listFiles()
        val adapter = Adapter(ArrayList<File>(listEntry!!.asList()))
        adapter.rootOfDirectory = rootOfDirectory!!
        adapter.activity= activity as MainActivity
        adapter.openNewFragment={
            (activity as MainActivity).addFragment(BlankFragment.newInstance(it))
        }

        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this.context)
        Log.v("TAG","onviewCreated")
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(rootOfDirectory: String) =
            BlankFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, rootOfDirectory)

                }
            }
    }
}