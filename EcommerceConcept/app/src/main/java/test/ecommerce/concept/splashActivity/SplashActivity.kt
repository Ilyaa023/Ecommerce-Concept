package test.ecommerce.concept.splashActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import test.ecommerce.concept.mainActivity.MainActivity
import test.ecommerce.concept.databinding.ActivitySplashBinding

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivitySplashBinding.inflate(layoutInflater).root)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}