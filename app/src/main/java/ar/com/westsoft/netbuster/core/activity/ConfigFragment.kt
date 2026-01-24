package ar.com.westsoft.netbuster.core.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import ar.com.westsoft.netbuster.R

class ConfigFragment(val callback: MainActivity) : Fragment() {
    var authModeCB: CheckBox? = null
    var passwordET: EditText? = null
    var repeatPWET: EditText? = null
    var okIV: ImageView? = null
    var saveB: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.config_layout, container, false)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(callback.baseContext)

        authModeCB = rootView.findViewById(R.id.auth_mode)
        passwordET = rootView.findViewById(R.id.password)
        repeatPWET = rootView.findViewById(R.id.repeat)
        okIV = rootView.findViewById(R.id.ok_image)
        saveB = rootView.findViewById(R.id.save_password)

        authModeCB?.isChecked = sharedPref?.getBoolean("authMode", false) ?: false
        authModeCB?.setOnCheckedChangeListener { checkButton, _ ->
            refreshPasswordConsole(checkButton.isChecked)
        }
        passwordET?.addTextChangedListener( textWatcher)
        passwordET?.setText("")
        repeatPWET?.addTextChangedListener( textWatcher )
        repeatPWET?.setText("")

        refreshPasswordConsole(authModeCB?.isChecked == true)

        saveB?.setOnClickListener {
            with(sharedPref?.edit()) {
                if ( authModeCB?.isChecked == true ) {
                    if ( passwordIsOk() ) {
                        this?.putBoolean("authMode", true)
                        this?.putString("password", passwordET?.text.toString())
                        Toast.makeText(callback, "The password was stored",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    this?.putBoolean("authMode", false)
                    Toast.makeText(callback, "The password was removed",
                        Toast.LENGTH_SHORT)
                        .show()
                }
                this?.apply()
            }
        }
        return rootView
    }
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) { refreshPasswordConsole(true) }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        authModeCB = null
        passwordET = null
        repeatPWET = null
        okIV = null
        saveB = null
    }

    fun refreshPasswordConsole(checkState: Boolean) {
        passwordET?.isEnabled = checkState
        repeatPWET?.isEnabled = checkState
        okIV?.isEnabled = checkState
        if (authModeCB?.isChecked == true) {
            if (passwordIsOk()) {
                okIV?.setImageResource(android.R.drawable.checkbox_on_background)
                saveB?.isEnabled = true
            } else {
                okIV?.setImageResource(android.R.drawable.checkbox_off_background)
                saveB?.isEnabled = false
            }
        } else {
            saveB?.isEnabled = true
        }
    }
    fun passwordIsOk() = passwordET?.text.toString() == repeatPWET?.text.toString()
            && passwordET?.text.toString().isNotEmpty()
}