package com.github.grubber.xtorrent.settings

import com.github.grubber.xtorrent.R
import com.github.grubber.xtorrent.base.XFragment

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