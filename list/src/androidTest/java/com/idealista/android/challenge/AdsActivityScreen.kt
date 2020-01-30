package com.idealista.android.challenge

import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.swiperefresh.KSwipeRefreshLayout
import com.agoda.kakao.text.KTextView
import com.idealista.android.challenge.list.R
import com.idealista.android.challenge.list.TestData
import com.idealista.android.challenge.list.ui.detail.AdsActivity
import com.idealista.android.challenge.list.ui.toModel
import org.junit.Rule
import org.junit.Test

class AdsActivityScreen : Screen<AdsActivityScreen>() {
    val swipeRefreshLayout = KSwipeRefreshLayout { withId(R.id.ad_detail_swipe) }
    val container = KView { withId(R.id.ad_detail_container) }
    val titleTextView = KTextView { withId(R.id.ad_title) }
    val priceTextView = KTextView { withId(R.id.ad_price) }
    val neighborhoodTextView = KTextView { withId(R.id.ad_neighborhood) }
    val addressTextView = KTextView { withId(R.id.ad_address) }
    val roomsTextView = KTextView { withId(R.id.ad_rooms) }
    val bathroomsTextView = KTextView { withId(R.id.ad_bathrooms) }
}

class AdsActivityTest {

    @JvmField
    @Rule
    val testRule = ActivityTestRule<AdsActivity>(AdsActivity::class.java)

    @Test
    fun view_render_sets_information_correctly() {
        val model = TestData.sampleAd.toModel(true)
        testRule.activity.render(model)

        val activity = AdsActivityScreen()
        activity {
            swipeRefreshLayout.isNotRefreshing()
            container.isVisible()
            titleTextView.hasText(model.title)
            priceTextView.hasText(model.price)
            neighborhoodTextView.hasText(model.neighborhood)
            addressTextView.hasText(model.address)
            roomsTextView.hasText(model.rooms)
            bathroomsTextView.hasText(model.bathrooms)
        }
    }
}