package com.idealista.android.challenge.list.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.idealista.android.challenge.core.BaseActivity
import com.idealista.android.challenge.core.api.model.CommonError
import com.idealista.android.challenge.core.utils.makeGone
import com.idealista.android.challenge.core.utils.makeVisible
import com.idealista.android.challenge.list.R
import com.idealista.android.challenge.list.ui.AdModel
import com.squareup.picasso.Picasso
import org.kodein.di.generic.instance


class AdsActivity : BaseActivity<AdsView, AdsPresenter>(), AdsView {

    companion object {
        const val AD_MODEL_KEY = "ad_model_key"
    }

    override val presenter: AdsPresenter by instance()
    override val view: AdsView = this

    private lateinit var currentAd: AdModel

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var container: View
    private lateinit var titleTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var neighborhoodTextView: TextView
    private lateinit var addressTextView: TextView
    private lateinit var roomsTextView: TextView
    private lateinit var bathroomsTextView: TextView
    private lateinit var adImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_ad)

        currentAd = intent.getParcelableExtra(AD_MODEL_KEY)

        swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.ad_detail_swipe).apply {
            setOnRefreshListener { presenter.onAdNeeded(currentAd.id) }
        }
        container = findViewById(R.id.ad_detail_container)
        titleTextView = findViewById(R.id.ad_title)
        priceTextView = findViewById(R.id.ad_price)
        adImageView = findViewById(R.id.ad_image)
        neighborhoodTextView = findViewById(R.id.ad_neighborhood)
        addressTextView = findViewById(R.id.ad_address)
        roomsTextView = findViewById(R.id.ad_rooms)
        bathroomsTextView = findViewById(R.id.ad_bathrooms)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        render(currentAd)
    }

    override fun render(ad: AdModel) {
        with(ad){
            titleTextView.text = title
            priceTextView.text = price
            neighborhoodTextView.text = neighborhood
            addressTextView.text = address
            roomsTextView.text = rooms
            bathroomsTextView.text = bathrooms
            if (thumbnail.isNotEmpty())
                Picasso.with(this@AdsActivity).load(thumbnail).into(adImageView)
        }
    }

    override fun renderError(error: CommonError) {
        errorHandler.showErrorMessage(error)
    }

    override fun refreshing(refreshing: Boolean) {
        swipeRefreshLayout.isRefreshing = refreshing
        if (refreshing) container.makeGone() else container.makeVisible()
    }

    override fun markAsFavorite(mark: Boolean) {
        currentAd = currentAd.copy(isFavorite = mark)
        invalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val favoriteDrawable = if (currentAd.isFavorite) R.drawable.ic_favorite_black_24dp
        else R.drawable.ic_favorite_border_black_24dp
        menu.findItem(R.id.ad_detail_menu_favorite).icon = getDrawable(favoriteDrawable)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.ad_detail_menu_favorite -> {
                presenter.markAsFavorite(currentAd)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ad_detail_menu, menu)
        return true
    }

}