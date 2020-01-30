package com.idealista.android.challenge

import android.content.Context
import com.idealista.android.challenge.core.StringsProvider

class StringsProvider(private val context: Context): StringsProvider {

    override fun string(id: Int, vararg args: Any?) = if (args.isEmpty()) context.getString(id) else context.getString(id, *args)
    override fun quantityString(id: Int, quantity: Int, vararg args: Any?) =
     if (args.isEmpty()) context.resources.getQuantityString(id, quantity, quantity) else context.resources.getQuantityString(id, quantity,*args)
}