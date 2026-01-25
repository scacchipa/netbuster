package ar.com.westsoft.netbuster.core.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import ar.com.westsoft.netbuster.databinding.ConfigLayoutBinding

class ConfigFragment(val callback: MainActivity) : Fragment() {

    private val binding: ConfigLayoutBinding by lazy {
        ConfigLayoutBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(callback.baseContext)

        binding.authMode.isChecked = sharedPref?.getBoolean("authMode", false) ?: false
        binding.authMode.setOnCheckedChangeListener { checkButton, _ ->
            refreshPasswordConsole(checkButton.isChecked)
        }
        binding.password.addTextChangedListener( textWatcher)
        binding.password.setText("")
        binding.repeat.addTextChangedListener( textWatcher )
        binding.repeat.setText("")

        refreshPasswordConsole(binding.authMode.isChecked)

        binding.saveButton.setOnClickListener {
            with(sharedPref?.edit()) {
                if (binding.authMode.isChecked) {
                    if ( passwordIsOk() ) {
                        this?.putBoolean("authMode", true)
                        this?.putString("password", binding.password.text.toString())
                        Toast.makeText(callback, "The password was stored", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    this?.putBoolean("authMode", false)
                    Toast.makeText(callback, "The password was removed", Toast.LENGTH_SHORT)
                        .show()
                }
                this?.apply()
            }
        }
        return binding.root
    }
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) { refreshPasswordConsole(true) }
    }

    fun refreshPasswordConsole(checkState: Boolean) {
        binding.password.isEnabled = checkState
        binding.repeat.isEnabled = checkState
        binding.okImage.isEnabled = checkState
        if (binding.authMode.isChecked) {
            if (passwordIsOk()) {
                binding.okImage.setImageResource(android.R.drawable.checkbox_on_background)
                binding.saveButton.isEnabled = true
            } else {
                binding.okImage.setImageResource(android.R.drawable.checkbox_off_background)
                binding.saveButton.isEnabled = false
            }
        } else {
            binding.saveButton.isEnabled = true
        }
    }
    fun passwordIsOk() =
        binding.password.text.toString() == binding.repeat.text.toString()
                && binding.password.text.toString().isNotEmpty()
}