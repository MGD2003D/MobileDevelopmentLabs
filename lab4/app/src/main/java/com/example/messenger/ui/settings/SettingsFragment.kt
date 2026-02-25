package com.example.messenger.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.messenger.R
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment() {

    private val tag = "SettingsFragment"
    private val viewModel: SettingsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); Log.d(tag, "onCreate") }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(tag, "onCreateView")
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tag, "onViewCreated")

        val themeSwitch = view.findViewById<SwitchMaterial>(R.id.switch_theme)
        viewModel.isDarkTheme.observe(viewLifecycleOwner) { if (themeSwitch.isChecked != it) themeSwitch.isChecked = it }
        themeSwitch.setOnCheckedChangeListener { _, isChecked -> viewModel.setDarkTheme(isChecked) }
    }

    override fun onStart() { super.onStart(); Log.d(tag, "onStart") }
    override fun onResume() { super.onResume(); Log.d(tag, "onResume") }
    override fun onPause() { super.onPause(); Log.d(tag, "onPause") }
    override fun onStop() { super.onStop(); Log.d(tag, "onStop") }
    override fun onDestroyView() { super.onDestroyView(); Log.d(tag, "onDestroyView") }
    override fun onDestroy() { super.onDestroy(); Log.d(tag, "onDestroy") }
}
