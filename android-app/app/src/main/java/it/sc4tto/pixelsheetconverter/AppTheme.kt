package it.sc4tto.pixelsheetconverter

import android.graphics.Color

data class AppTheme(
    val id: String,
    val label: String,
    val background: Int,
    val panelTop: Int,
    val panelBottom: Int,
    val input: Int,
    val text: Int,
    val muted: Int,
    val accent: Int,
    val accentText: Int,
    val border: Int,
    val headerTop: Int,
    val headerBottom: Int,
    val headerText: Int,
    val radiusDp: Int,
    val strokeDp: Int = 1,
    val elevationDp: Int = 3,
    val monospace: Boolean = false,
    val iconSizeDp: Int = 20,
    val iconGapDp: Int = 8,
)

object AppThemes {
    private fun c(value: String) = Color.parseColor(value)

    val all = listOf(
        AppTheme("windows2000", "Windows 2000", c("#D4D0C8"), c("#F2F2EE"), c("#C8C5BD"), c("#FFFFFF"), c("#101010"), c("#424242"), c("#000080"), Color.WHITE, c("#6B6B6B"), c("#0A3B91"), c("#000080"), Color.WHITE, 0, 2, 1),
        AppTheme("xp_luna", "XP Luna", c("#ECE9D8"), c("#FFFDF5"), c("#E7E2CE"), Color.WHITE, c("#102A56"), c("#4D5B70"), c("#2F7D22"), Color.WHITE, c("#2F63B6"), c("#4AA6FF"), c("#0754D8"), Color.WHITE, 12, 2, 5),
        AppTheme("aero", "Aero", c("#DCECF4"), c("#F9FEFF"), c("#C7E4F0"), c("#F7FDFF"), c("#102D43"), c("#496878"), c("#087FC1"), Color.WHITE, c("#5A91AA"), c("#BEE7F7"), c("#397C9C"), c("#0A3149"), 15, 1, 7),
        AppTheme("keramik", "KDE Keramik", c("#D9E8F8"), c("#FAFDFF"), c("#BDD6F2"), c("#F7FBFF"), c("#123B73"), c("#496787"), c("#286FD0"), Color.WHITE, c("#5A8CC8"), c("#D8EAFF"), c("#7EAFE4"), c("#123B73"), 22, 2, 8),
        AppTheme("oxygen", "KDE Oxygen", c("#E7EEF2"), c("#FFFFFF"), c("#D8E5EC"), c("#FBFDFF"), c("#172C3D"), c("#5D6C76"), c("#168BD2"), Color.WHITE, c("#70A7C7"), c("#FAFDFF"), c("#B7D5E5"), c("#172C3D"), 8, 1, 4),
        AppTheme("clearlooks", "GNOME Clearlooks", c("#EDEDEB"), c("#FAFAF8"), c("#DADBD8"), Color.WHITE, c("#202428"), c("#60676C"), c("#3B78B5"), Color.WHITE, c("#8B949A"), c("#F7F7F5"), c("#C7D6E3"), c("#26394A"), 7, 1, 2),
        AppTheme("ubuntu_human", "Ubuntu Human", c("#E9DCCB"), c("#FFF6E9"), c("#DFC5A5"), c("#FFF9F0"), c("#3A291F"), c("#715848"), c("#D95416"), Color.WHITE, c("#8A6048"), c("#7A482D"), c("#432A20"), c("#FFF5E9"), 13, 1, 5),
        AppTheme("terminal", "Terminale GNU/Linux", c("#020906"), c("#06140D"), c("#020906"), c("#04100B"), c("#58F4A5"), c("#83CBA5"), c("#31F28E"), c("#001A0D"), c("#31F28E"), c("#071B11"), c("#020906"), c("#58F4A5"), 0, 1, 0, true),
        AppTheme("flubber", "Flubber", c("#C8F4DE"), c("#F1FFF8"), c("#80E8B8"), c("#EEFFF7"), c("#063E31"), c("#337665"), c("#08C980"), Color.WHITE, c("#43DDA1"), c("#C9FFE8"), c("#58E0AD"), c("#07513E"), 28, 2, 12),
    )

    fun byId(id: String?) = all.firstOrNull { it.id == id } ?: all.first()
}
