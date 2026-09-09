package it.sc4tto.pixelsheetconverter

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.google.android.material.button.MaterialButton
import it.sc4tto.pixelsheetconverter.databinding.ActivityMainBinding

object ThemeRenderer {
    fun apply(binding: ActivityMainBinding, theme: AppTheme) {
        binding.rootLayout.setBackgroundColor(theme.background)
        binding.conversionContent.setBackgroundColor(theme.background)
        binding.resultContent.setBackgroundColor(theme.background)

        decorate(binding.appHeader, theme.headerTop, theme.headerBottom, theme.border, theme.radiusDp)
        decorate(binding.navigationBar, theme.panelTop, theme.panelBottom, theme.border, 0)
        decorate(binding.statusText, theme.panelTop, theme.panelBottom, theme.border, 0)
        listOf(binding.dimensionsPanel, binding.colorsPanel, binding.statisticsPanel).forEach {
            decorate(it, theme.panelTop, theme.panelBottom, theme.border, theme.radiusDp)
            ViewCompat.setElevation(it, dp(it, theme.elevationDp).toFloat())
        }
        listOf(binding.sourcePreview, binding.resultPreview).forEach {
            decorate(it, theme.input, theme.panelBottom, theme.border, theme.radiusDp)
        }

        binding.appTitle.setTextColor(theme.headerText)
        binding.versionLabel.setTextColor(theme.headerText)
        listOf(binding.conversionTitle, binding.resultTitle, binding.dimensionsTitle, binding.colorsTitle,
            binding.statisticsTitle, binding.exportTitle).forEach { it.setTextColor(theme.accent) }
        binding.statisticsText.setTextColor(theme.text)
        binding.logoExportCheck.setTextColor(theme.text)
        binding.logoExportCheck.buttonTintList = ColorStateList.valueOf(theme.accent)
        binding.openSeaHelp.setTextColor(theme.muted)
        binding.statusText.setTextColor(theme.muted)

        styleTree(binding.conversionContent, theme)
        styleTree(binding.resultContent, theme)
        styleButton(binding.themeButton, theme, false)
        listOf(binding.navCameraButton, binding.navConvertButton, binding.navResultButton).forEach { styleButton(it, theme, false) }
        listOf(binding.convertButton, binding.openSeaButton).forEach { styleButton(it, theme, true) }
        listOf(binding.exportPngButton, binding.exportXlsxButton, binding.backToCameraButton).forEach { styleButton(it, theme, false) }

        val face = if (theme.monospace) Typeface.MONOSPACE else Typeface.create("sans-serif", Typeface.NORMAL)
        setTypeface(binding.rootLayout, face)
        binding.themeButton.text = "TEMA"
        binding.themeButton.contentDescription = "Tema attivo: ${theme.label}. Tocca per cambiare tema."
    }

    private fun styleTree(view: View, theme: AppTheme) {
        when (view) {
            is MaterialButton -> Unit
            is EditText -> {
                view.setTextColor(theme.text); view.setHintTextColor(theme.muted)
                decorate(view, theme.input, theme.input, theme.border, (theme.radiusDp / 2).coerceAtLeast(0))
            }
            is Spinner -> {
                decorate(view, theme.input, theme.input, theme.border, (theme.radiusDp / 2).coerceAtLeast(0))
                view.setPopupBackgroundDrawable(solid(theme.panelTop, theme.border, theme.radiusDp / 2, view, 1))
            }
            is TextView -> if (view.id !in setOf(
                    R.id.conversionTitle, R.id.resultTitle, R.id.dimensionsTitle, R.id.colorsTitle,
                    R.id.statisticsTitle, R.id.exportTitle, R.id.statisticsText, R.id.openSeaHelp
                )) view.setTextColor(theme.text)
        }
        if (view is ViewGroup) for (index in 0 until view.childCount) styleTree(view.getChildAt(index), theme)
    }

    private fun styleButton(button: MaterialButton, theme: AppTheme, primary: Boolean) {
        val top = if (primary) theme.accent else theme.panelTop
        val bottom = if (primary) darken(theme.accent, 24) else theme.panelBottom
        button.backgroundTintList = null
        button.background = gradient(top, bottom, theme.border, theme.radiusDp, button, theme.strokeDp)
        button.setTextColor(if (primary) theme.accentText else theme.text)
        ViewCompat.setElevation(button, dp(button, if (primary) theme.elevationDp + 2 else theme.elevationDp).toFloat())
    }

    private fun decorate(view: View, top: Int, bottom: Int, border: Int, radius: Int) {
        view.background = gradient(top, bottom, border, radius, view, 1)
    }

    private fun gradient(top: Int, bottom: Int, border: Int, radius: Int, view: View, stroke: Int) =
        GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(top, bottom)).apply {
            cornerRadius = dp(view, radius).toFloat()
            setStroke(dp(view, stroke), border)
        }

    private fun solid(color: Int, border: Int, radius: Int, view: View, stroke: Int) = GradientDrawable().apply {
        setColor(color); cornerRadius = dp(view, radius).toFloat(); setStroke(dp(view, stroke), border)
    }

    private fun setTypeface(view: View, typeface: Typeface) {
        if (view is TextView) view.typeface = Typeface.create(typeface, if (view.typeface?.isBold == true) Typeface.BOLD else Typeface.NORMAL)
        if (view is ViewGroup) for (index in 0 until view.childCount) setTypeface(view.getChildAt(index), typeface)
    }

    private fun darken(color: Int, amount: Int) = Color.rgb(
        (Color.red(color) - amount).coerceAtLeast(0),
        (Color.green(color) - amount).coerceAtLeast(0),
        (Color.blue(color) - amount).coerceAtLeast(0),
    )

    private fun dp(view: View, value: Int) = (value * view.resources.displayMetrics.density).toInt().coerceAtLeast(if (value > 0) 1 else 0)
}
