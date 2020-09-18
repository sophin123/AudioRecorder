package com.example.microphone

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import java.security.Permission
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class RecordFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    lateinit var viewList: ImageButton
    lateinit var recordBtn: ImageButton
    lateinit var filename: TextView

    private var isRecording: Boolean = false

    //Record Permission
    private var recordPermission = android.Manifest.permission.RECORD_AUDIO
    private val PERMISSION_CODE = 100

    //MediaRecorder
    lateinit var mediaRecorder: MediaRecorder
    lateinit var recordFile: String

    //Chronometer
    lateinit var timer: Chronometer

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
        timer = view.findViewById(R.id.record_timer)
        filename = view.findViewById(R.id.record_filename)



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
                        stopRecording()

                        Log.d("Tag", "Stop Recording")
                        false
                    } else {
                        //Start Recording
                        recordBtn.setImageDrawable(resources.getDrawable(R.drawable.mic_disabled))
                        startRecording()

                        Log.d("Tag", "Start Recording")
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

    private fun startRecording() {
        var recordPath = activity?.getExternalFilesDir("/")?.absolutePath
        val formatter = SimpleDateFormat("yy_mm_dd_hh_mm_ss", Locale.ENGLISH)
        val now = Date()


        recordFile = "filename + ${formatter.format(now)}.3gp"
        filename.setText("Recording File + $recordFile")

        mediaRecorder = MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder.setOutputFile("$recordPath/$recordFile")
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        mediaRecorder.prepare()
        mediaRecorder.start()

        timer.base = SystemClock.elapsedRealtime()
        timer.start()

        Toast.makeText(context, "Record Starting", Toast.LENGTH_SHORT).show()

    }

    private fun stopRecording() {
        mediaRecorder.stop()
        mediaRecorder.reset()
        mediaRecorder.release()
        timer.stop()
        filename.setText("Recording Stopped + $recordFile")
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