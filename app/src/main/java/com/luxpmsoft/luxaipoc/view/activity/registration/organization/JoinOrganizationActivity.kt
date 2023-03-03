package com.luxpmsoft.luxaipoc.view.activity.registration.organization

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.luxpmsoft.luxaipoc.R
import com.luxpmsoft.luxaipoc.databinding.ActivityJoinOrganizationBinding
import com.luxpmsoft.luxaipoc.utils.MyUtils

class JoinOrganizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinOrganizationBinding

    private var isOrganizationNameFiled: Boolean = false
    private var role: String? = null
    private var whiteColorList: ColorStateList? = null
    private var purpleColorList: ColorStateList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyUtils.setStatusBarTransparentFlagBlack(this)
        binding = ActivityJoinOrganizationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setColors()
        setListeners()
    }

    private fun setColors() {
        whiteColorList = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_enabled)
            ), intArrayOf(
                getColor(R.color.white),
                getColor(R.color.white)
            )
        )
        purpleColorList = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_enabled)
            ), intArrayOf(
                getColor(R.color.secondary_purple_600),
                getColor(R.color.secondary_purple_600)
            )
        )
    }

    private fun setListeners() {
        binding.topBar.buttonBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.editTextOrganizationName.doOnTextChanged { text, _, _, _ ->
            onOrganizationNameChange(text.toString().trim())
        }
        binding.radioButtonUser.setOnClickListener { onRadioButtonClicked(it) }
        binding.radioButtonAdmin.setOnClickListener { onRadioButtonClicked(it) }
        binding.buttonContinue.setOnClickListener {
            SubscriptionManager().organizationJoinExisting(
                binding.editTextOrganizationName.text.toString().trim(),
                this,
                binding.flProgress
            )
        }
    }

    private fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.getId()) {
                binding.radioButtonUser.id -> {
                    if (checked) {
                        role = binding.radioButtonUser.text.toString()
                        binding.radioButtonUser.buttonTintList = purpleColorList
                        binding.radioButtonAdmin.buttonTintList = whiteColorList
                        setContinueButtonState()
                    }
                }
                binding.radioButtonAdmin.id -> {
                    if (checked) {
                        role = binding.radioButtonUser.text.toString()
                        binding.radioButtonUser.buttonTintList = whiteColorList
                        binding.radioButtonAdmin.buttonTintList = purpleColorList
                        setContinueButtonState()
                    }
                }
            }
        }
    }

    private fun onOrganizationNameChange(name: String) {
        isOrganizationNameFiled = name.isNotEmpty()
        setContinueButtonState()
    }

    private fun setContinueButtonState() {
        if (role != null && isOrganizationNameFiled) {
            binding.buttonContinue.isEnabled = true
            binding.buttonContinue.background = ContextCompat.getDrawable(
                this,
                R.drawable.bg_purple_8
            )
        } else {
            binding.buttonContinue.isEnabled = false
            binding.buttonContinue.background = ContextCompat.getDrawable(
                this,
                R.drawable.bg_purple_8_disabled
            )
        }
    }
}
