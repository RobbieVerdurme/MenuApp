package com.example.boeferrob.menuapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.boeferrob.menuapp.R
import com.example.boeferrob.menuapp.utils.LOGIN
import com.example.boeferrob.menuapp.utils.RC_SIGN_IN
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        toolbar.setLogo(R.drawable.menu_logo_navbar)
        toolbar.titleMarginStart = 50
        setSupportActionBar(toolbar)

        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setAlwaysShowSignInMethodScreen(true)
                .setIsSmartLockEnabled(true)
                .setLogo(R.drawable.menu_logo)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == RESULT_OK) {
                //val user = FirebaseAuth.getInstance().currentUser
                val intent = Intent(this,MainActivity::class.java)
                intent.putExtra(LOGIN, 0)
                startActivity(intent)
                finish()
            } else if (resultCode == RESULT_CANCELED) {
                val intent = Intent(this,MainActivity::class.java)
                Toast.makeText(this, R.string.offline, Toast.LENGTH_LONG).show()
                intent.putExtra(LOGIN, -1)
                startActivity(intent)
                finish()

            }
        }
    }
}
