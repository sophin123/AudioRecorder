package com.example.microphone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.io.File
import java.util.*


class AudioListFragment : Fragment() {

    lateinit var playerSheet: ConstraintLayout
    lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    lateinit var audioList: RecyclerView
    lateinit var allFiles: Array<File>

    lateinit var audioListAdapter: AudioListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_audio_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerSheet = view.findViewById(R.id.record_sheet)
        bottomSheetBehavior = BottomSheetBehavior.from(playerSheet)

        audioList = view.findViewById(R.id.audio_list_item)

        var path = activity?.getExternalFilesDir("/")?.absolutePath
        var directory = File(path)
        allFiles = directory.listFiles()

        audioListAdapter = AudioListAdapter(allFiles)
        audioList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = audioListAdapter
        }

        bottomSheetBehavior.addBottomSheetCallback(object:
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN){
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })


    }
}