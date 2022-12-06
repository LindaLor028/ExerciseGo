package com.macalester.exercisego

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.macalester.exercisego.data.Park
import com.macalester.exercisego.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle("Park Details")

        var park = intent.getSerializableExtra("CITY_TAG")!! as Park

        binding.tvParkName.text = park.name
        binding.tvAddress.text = park.address

        setContentView(binding.root)
        setFavoriteLogo(park)

        binding.ibFavorite.setOnClickListener {
            park.isFavorite = !park.isFavorite
            setFavoriteLogo(park)
        }

        binding.btnReview.setOnClickListener {
            // you can probably implement the firebase/ait-forum demo thing :))
        }
    }

    private fun setFavoriteLogo(park : Park) {
        if (park.isFavorite) {
            // set star logo to turned on
            binding.ibFavorite.setImageResource(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_light_normal)
        }
        else {
            // set star logo to turned off
            binding.ibFavorite.setImageResource(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_disabled)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}