package com.example.microphone

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import java.security.Permission
import java.util.jar.Manifest

class RecordFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    lateinit var viewList: ImageButton
    lateinit var recordBtn: ImageButton
    lateinit var text: TextView

    private var isRecording: Boolean = false

    //Record Permission
    private var recordPermission = android.Manifest.permission.RECORD_AUDIO
    private val PERMISSION_CODE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_record, container, false)

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        viewList = view.findViewById(R.id.view_record_list)
        recordBtn = view.findViewById(R.id.record_audio)
        text = view.findViewById(R.id.record_filename)

        viewList.setOnClickListener(this)
        recordBtn.setOnClickListener(this)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.record_audio -> {
                if (checkPermissions()) {
                    isRecording = if (isRecording) {
                        //Stop Recording
                        recordBtn.setImageDrawable(resources.getDrawable(R.drawable.mic_enabled))
                        false
                    } else {
                        //Start Recording
                        recordBtn.setImageDrawable(resources.getDrawable(R.drawable.mic_disabled))
                        true
                    }
                }
            }

            R.id.view_record_list -> {
                navController.navigate(R.id.action_recordFragment_to_audioListFragment)
                Log.d("Tag", "Record list Clicked")
            }
        }
    }

    private fun checkPermissions(): Boolean {
        return if (context?.let { ActivityCompat.checkSelfPermission(it, recordPermission) } == PackageManager.PERMISSION_GRANTED){
            true
        }else{
            activity?.let { ActivityCompat.requestPermissions(it, arrayOf(recordPermission), PERMISSION_CODE) }
            false
        }

    }
}