package com.idealista.android.challenge.list.ui.list

import android.os.Bundle
import android.os.Parcelable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.idealista.android.challenge.core.Addressable
import com.idealista.android.challenge.core.BaseActivity
import com.idealista.android.challenge.core.api.model.CommonError
import com.idealista.android.challenge.core.intentTo
import com.idealista.android.challenge.list.R
import com.idealista.android.challenge.list.ui.AdModel
import com.idealista.android.challenge.list.ui.detail.AdsActivity.Companion.AD_MODEL_KEY
import org.kodein.di.generic.instance

class ListActivity : BaseActivity<ListView, ListPresenter>(), ListView {

    companion object {
        private const val RECYCLER_VIEW_STATUS_ID = "ad_list_status"
        private const val RECYCLER_VIEW_DATA_SET = "ad_list_data"
    }

    override val presenter: ListPresenter by instance()
    override val view: ListView = this

    private lateinit var listAdapter: ListAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        initializeRecyclerView()
        populateAds(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val recyclerState = adsRecyclerView.layoutManager?.onSaveInstanceState()

        recyclerState?.let { state ->
            outState.putParcelable(RECYCLER_VIEW_STATUS_ID, state)
            listAdapter.getItems().takeIf { it.isNotEmpty() }?.let { items ->
                outState.putParcelableArrayList(RECYCLER_VIEW_DATA_SET, ArrayList(items))
            }
        }
        super.onSaveInstanceState(outState)
    }

    override fun render(list: ListModel) {
        listAdapter.set(list)
    }

    override fun refreshing(refreshing: Boolean) {
        swipeRefreshLayout.isRefreshing = refreshing
    }

    override fun renderError(error: CommonError) {
        errorHandler.showErrorMessage(error)
    }

    override fun renderFavoriteAds(newFavoriteAds: Set<String>) {
        listAdapter.updateFavorites(newFavoriteAds)
    }

    override fun navigateToAdDetail(ad: AdModel) {
        startActivity(Addressable.Activity.Ads.intentTo().apply { putExtra(AD_MODEL_KEY, ad) })
    }

    private fun initializeRecyclerView() {
        listAdapter = ListAdapter().apply {
            listener(object : ListAdapter.AdListener {
                override fun onAdClicked(ad: AdModel) = presenter.onAdClicked(ad)
                override fun onAdFavorite(ad: AdModel) = presenter.onAdMarkedAsFavorite(ad)
            })
        }

        swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.ads_swipe).apply {
            setOnRefreshListener { presenter.onListNeeded() }
        }

        adsRecyclerView = findViewById<RecyclerView>(R.id.ads_recycler).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ListActivity)
            adapter = listAdapter
        }
    }

    private fun populateAds(savedInstanceState: Bundle?) {
        val savedStateDataSet =
            savedInstanceState?.getParcelableArrayList<AdModel>(RECYCLER_VIEW_DATA_SET)

        if (savedStateDataSet == null) {
            presenter.onListNeeded()
            return
        }
        listAdapter.set(ListModel(savedStateDataSet))

        val savedStatePosition =
            savedInstanceState.getParcelable<Parcelable>(RECYCLER_VIEW_STATUS_ID) ?: return

        adsRecyclerView.layoutManager?.onRestoreInstanceState(savedStatePosition)
    }

}