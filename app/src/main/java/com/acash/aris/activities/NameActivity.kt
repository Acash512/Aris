package com.acash.aris.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.acash.aris.data.UserPreferencesDataStore
import com.acash.aris.databinding.ActivityNameBinding
import kotlinx.coroutines.launch

class NameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnProceed.setOnClickListener {
                if (nameEt.text.isNullOrEmpty()) {
                    nameInput.error = "Name cannot be empty"
                } else {
                    lifecycleScope.launch {
                        UserPreferencesDataStore(this@NameActivity)
                            .saveNameToPreferencesStore(nameEt.text.toString(), this@NameActivity)

                        startActivity(
                            Intent(this@NameActivity, MainActivity::class.java).setFlags(
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            )
                        )
                    }
                }
            }

            nameEt.addTextChangedListener {
                nameInput.isErrorEnabled = false
            }
        }
    }

}