package com.example.aquariumshopapp.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.aquariumshopapp.data.model.Customer
import com.google.gson.Gson
import kotlinx.coroutines.flow.first

class AccountDataStore(private val context: Context) {
    companion object {
        private val Context.dataStore by preferencesDataStore("account_pref")
        private val ACCOUNT_KEY = stringPreferencesKey("account")
    }

    suspend fun saveAccount(account: Customer) {
        val json = Gson().toJson(account)
        context.dataStore.edit { pref ->
            pref[ACCOUNT_KEY] = json
        }
    }

    suspend fun getAccount(): Customer? {
        val pref = context.dataStore.data.first()
        val account = Gson().fromJson(pref[ACCOUNT_KEY], Customer::class.java)
        return account
    }

    suspend fun clearAccount() {
        context.dataStore.edit {
            it.remove(ACCOUNT_KEY)
        }
    }
}