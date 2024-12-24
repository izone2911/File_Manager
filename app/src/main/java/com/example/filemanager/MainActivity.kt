package com.example.filemanager

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.Intent
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.ActionMode
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.io.File
import androidx.appcompat.view.ActionMode.Callback

class MainActivity : AppCompatActivity() {
    lateinit var rootOfDirectory:String
    var actionMode:ActionMode? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(Build.VERSION.SDK_INT<30){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED){
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1234)
                Log.v("TAG","permission denied")
            }else {
                Log.v("TAG","permission accepted")
            }
        }else {
            if(!Environment.isExternalStorageManager()){
                Log.v("TAG","permission denied")
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivity(intent)
            }else {
                Log.v("TAG","permission accepted")
            }
        }
        rootOfDirectory = Environment.getExternalStorageDirectory().path
        val firstFragment = BlankFragment.newInstance(rootOfDirectory)
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView,firstFragment).addToBackStack(null).commit()
        Log.v("TAG","added")
    }
    fun addFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView,fragment).addToBackStack(null).commit()
    }
    fun findFragment():BlankFragment{
       return  supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as BlankFragment
    }

    fun start(from:String,name:String) {
        val actionModeCallBack=ActionModeCallBack(name)
        actionModeCallBack.activity = this
        actionModeCallBack.from = from

        startActionMode(actionModeCallBack )
    }


}

class ActionModeCallBack(val name:String): ActionMode.Callback{
    lateinit var activity: MainActivity
    lateinit var from:String
    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        menu?.add(0,0,1,"Chuyển đến đây")
        menu?.add(0,1,0,"Quay lai")
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return  true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if(item?.itemId == 0) {
            val select = activity?.findFragment()?.rootOfDirectory!!+name
            Log.v("TAG",select!!)
            val fromFile = File(from)
            fromFile.copyTo(File(select!!),true)
            Toast.makeText(activity, "successfull", Toast.LENGTH_LONG).show()
            mode?.finish()
        }else{
            activity.supportFragmentManager.beginTransaction().remove((activity?.findFragment()) as Fragment).commit()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {

    }

}

