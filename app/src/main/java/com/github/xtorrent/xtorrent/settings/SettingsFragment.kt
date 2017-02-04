package com.github.xtorrent.xtorrent.settings

import com.github.xtorrent.xtorrent.R
import com.github.xtorrent.xtorrent.base.XFragment

/**
 * Created by grubber on 16/11/29.
 */
class SettingsFragment : XFragment() {
    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    override fun getTitle(): String? {
        return getString(R.string.drawer_menu_settings)
    }
}