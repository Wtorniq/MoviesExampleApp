package com.example.moviesexampleapp.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import com.example.moviesexampleapp.R
import com.example.moviesexampleapp.databinding.FragmentSettingsBinding
import com.example.moviesexampleapp.model.entities.getDefaultSetting
import com.google.android.material.button.MaterialButton


const val argType = "argType"
const val argGenre = "argGenre"
const val argCountry = "argCountry"
const val argRatingFrom = "argRatingFrom"
const val argRatingTo = "argRatingTo"
const val argYearFrom = "argYearFrom"
const val argYearTo = "argYearTo"

class SettingsFragment : Fragment(){

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var settings = getDefaultSetting()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSettings()
        with(binding) {
            settingsToolbar.setNavigationOnClickListener {
                parentFragmentManager.popBackStack()
            }
            initSettings()
            initYearPicker(yearFromPicker)
            initYearPicker(yearToPicker)
            initRatingPicker(ratingFromPicker)
            initRatingPicker(ratingToPicker)
        }
    }

    private fun initSettings() = with(binding) {
        typeSelector.text = settings.type
        genreSelector.text = settings.genre
        countriesSelector.text = settings.country
        yearFromPicker.value = settings.yearFrom
        yearToPicker.value = settings.yearTo
        ratingFromPicker.value = settings.ratingFrom
        ratingToPicker.value = settings.ratingTo
        setSelectorClickListener(typeSelector)
        setSelectorClickListener(genreSelector)
        setSelectorClickListener(countriesSelector)
    }

    private fun setSelectorClickListener(selector: MaterialButton) {
        val arrayList = java.util.ArrayList<String>()
        var index: Int? = null
        with (binding){
            when(selector){
                typeSelector -> {
                    arrayList.addAll(resources.getStringArray(R.array.types))
                    index = 0
                }
                genreSelector -> {
                    arrayList.addAll(resources.getStringArray(R.array.genres))
                    index = 1
                }
                countriesSelector -> {
                    arrayList.addAll(resources.getStringArray(R.array.countries))
                    index = 2
                }
                else -> {}
            }
        }

        selector.setOnClickListener {
            parentFragmentManager.beginTransaction().
            replace(R.id.container, SelectorFragment.newInstance(arrayList, index?:0)).
            addToBackStack(null).
            commit()
        }
    }

    private fun initYearPicker(picker: NumberPicker){
        picker.minValue = 1800
        picker.maxValue = 2050
        with(binding){
            when(picker){
                yearFromPicker-> {
                    picker.value = settings.yearFrom
                    picker.setOnValueChangedListener { _, _, newVal ->
                        savePickerData(argYearFrom, newVal)
                    }
                }
                yearToPicker-> {
                    picker.value = settings.yearTo
                    picker.setOnValueChangedListener { _, _, newVal ->
                        savePickerData(argYearTo, newVal)
                    }
                }
            }
        }
    }

    private fun initRatingPicker(picker: NumberPicker) {
        picker.minValue = 0
        picker.maxValue = 10

        with(binding){
            when(picker){
                ratingFromPicker->{
                    picker.value = settings.ratingFrom
                    picker.setOnValueChangedListener { _, _, newVal ->
                        savePickerData(argRatingFrom, newVal)
                    }
                }
                ratingToPicker->{
                    picker.value = settings.ratingTo
                    picker.setOnValueChangedListener { _, _, newVal ->
                        savePickerData(argRatingTo, newVal)
                    }
                }
            }
        }
    }

    private fun savePickerData(arg: String, value: Int) {
        activity?.getPreferences(Context.MODE_PRIVATE)?.edit()
            ?.apply {
                putInt(arg, value)
            }?.apply()
    }

    private fun getSettings() {
        activity?.let {
            settings.type = activity
                ?.getPreferences(Context.MODE_PRIVATE)
                ?.getString(argType, getDefaultSetting().type)?: getDefaultSetting().type
            settings.country = activity
                ?.getPreferences(Context.MODE_PRIVATE)
                ?.getString(argCountry, getDefaultSetting().country)?: getDefaultSetting().country
            settings.genre = activity
                ?.getPreferences(Context.MODE_PRIVATE)
                ?.getString(argGenre, getDefaultSetting().genre)?: getDefaultSetting().genre
            settings.yearFrom = activity
                ?.getPreferences(Context.MODE_PRIVATE)
                ?.getInt(argYearFrom, getDefaultSetting().yearFrom)?: getDefaultSetting().yearFrom
            settings.yearTo = activity
                ?.getPreferences(Context.MODE_PRIVATE)
                ?.getInt(argYearTo, getDefaultSetting().yearTo)?: getDefaultSetting().yearTo
            settings.ratingFrom = activity
                ?.getPreferences(Context.MODE_PRIVATE)
                ?.getInt(argRatingFrom, getDefaultSetting().ratingFrom)?: getDefaultSetting().ratingFrom
            settings.ratingTo = activity
                ?.getPreferences(Context.MODE_PRIVATE)
                ?.getInt(argRatingTo, getDefaultSetting().ratingTo)?: getDefaultSetting().ratingTo

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    companion object {
        fun newInstance() = SettingsFragment()
    }

}