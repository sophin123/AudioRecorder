package com.example.microphone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation

class RecordFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    lateinit var viewList: ImageView

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

        viewList.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.view_record_list -> navController.navigate(R.id.action_recordFragment_to_audioListFragment)
        }
    }


}