package com.example.messenger.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.messenger.R

class ProfileFragment : Fragment() {

    private val tag = "ProfileFragment"
    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); Log.d(tag, "onCreate") }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(tag, "onCreateView")
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tag, "onViewCreated")

        val etName = view.findViewById<EditText>(R.id.et_name)
        val etStatus = view.findViewById<EditText>(R.id.et_status)

        viewModel.name.observe(viewLifecycleOwner) { if (etName.text.toString() != it) etName.setText(it) }
        viewModel.status.observe(viewLifecycleOwner) { if (etStatus.text.toString() != it) etStatus.setText(it) }

        etName.doAfterTextChanged { viewModel.updateName(it?.toString() ?: "") }
        etStatus.doAfterTextChanged { viewModel.updateStatus(it?.toString() ?: "") }
    }

    override fun onStart() { super.onStart(); Log.d(tag, "onStart") }
    override fun onResume() { super.onResume(); Log.d(tag, "onResume") }
    override fun onPause() { super.onPause(); Log.d(tag, "onPause") }
    override fun onStop() { super.onStop(); Log.d(tag, "onStop") }
    override fun onDestroyView() { super.onDestroyView(); Log.d(tag, "onDestroyView") }
    override fun onDestroy() { super.onDestroy(); Log.d(tag, "onDestroy") }
}
