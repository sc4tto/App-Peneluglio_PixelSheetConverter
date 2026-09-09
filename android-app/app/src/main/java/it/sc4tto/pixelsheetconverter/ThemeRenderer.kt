package it.sc4tto.pixelsheetconverter

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.appcompat.content.res.AppCompatResources
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
            it.background = if (theme.id == "windows2000") classicBevel(it, theme.panelTop) else gradient(theme.panelTop, theme.panelBottom, theme.border, theme.radiusDp, it, 1)
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
        applyAlignedIcons(binding, theme)

        val face = if (theme.monospace) Typeface.MONOSPACE else Typeface.create("sans-serif", Typeface.NORMAL)
        setTypeface(binding.rootLayout, face)
        binding.themeButton.text = "TEMA"
        binding.themeButton.contentDescription = "Tema attivo: ${theme.label}. Tocca per cambiare tema."
        if (theme.id == "windows2000") {
            binding.appTitle.setTypeface(Typeface.create("sans-serif", Typeface.BOLD), Typeface.BOLD)
            binding.appHeader.setPadding(dp(binding.appHeader, 7), dp(binding.appHeader, 4), dp(binding.appHeader, 5), dp(binding.appHeader, 4))
            binding.navigationBar.setPadding(dp(binding.navigationBar, 4), dp(binding.navigationBar, 3), dp(binding.navigationBar, 4), dp(binding.navigationBar, 3))
        } else {
            binding.appHeader.setPadding(dp(binding.appHeader, 14), dp(binding.appHeader, 9), dp(binding.appHeader, 14), dp(binding.appHeader, 9))
            binding.navigationBar.setPadding(dp(binding.navigationBar, 8), dp(binding.navigationBar, 4), dp(binding.navigationBar, 8), dp(binding.navigationBar, 4))
        }
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
        button.background = if (theme.id == "windows2000") classicBevel(button, if (primary) theme.accent else theme.background)
            else gradient(top, bottom, theme.border, theme.radiusDp, button, theme.strokeDp)
        button.setTextColor(if (primary) theme.accentText else theme.text)
        ViewCompat.setElevation(button, dp(button, if (primary) theme.elevationDp + 2 else theme.elevationDp).toFloat())
    }

    private fun applyAlignedIcons(binding: ActivityMainBinding, theme: AppTheme) {
        val icons = listOf(
            Triple(binding.themeButton, R.drawable.ic_theme_24, false),
            Triple(binding.navCameraButton, R.drawable.ic_camera_24, true),
            Triple(binding.navConvertButton, R.drawable.ic_tune_24, true),
            Triple(binding.navResultButton, R.drawable.ic_image_24, true),
            Triple(binding.galleryButton, R.drawable.ic_folder_24, false),
            Triple(binding.captureButton, R.drawable.ic_camera_24, false),
            Triple(binding.convertButton, R.drawable.ic_tune_24, false),
            Triple(binding.exportPngButton, R.drawable.ic_image_24, false),
            Triple(binding.exportXlsxButton, R.drawable.ic_grid_24, false),
            Triple(binding.backToCameraButton, R.drawable.ic_camera_24, false),
            Triple(binding.openSeaButton, R.drawable.ic_upload_24, false),
        )
        icons.forEach { (button, resource, compact) ->
            button.icon = AppCompatResources.getDrawable(button.context, resource)
            val iconColor = when (button) {
                binding.convertButton, binding.openSeaButton -> theme.accentText
                binding.galleryButton, binding.captureButton -> Color.WHITE
                else -> theme.text
            }
            button.iconTint = ColorStateList.valueOf(iconColor)
            button.iconGravity = MaterialButton.ICON_GRAVITY_TEXT_START
            button.iconSize = dp(button, if (compact) (theme.iconSizeDp - 4).coerceAtLeast(14) else theme.iconSizeDp)
            button.iconPadding = dp(button, if (compact) (theme.iconGapDp / 2).coerceAtLeast(2) else theme.iconGapDp)
        }
    }

    private fun classicBevel(view: View, faceColor: Int): LayerDrawable {
        val outer = GradientDrawable().apply { setColor(Color.rgb(64, 64, 64)) }
        val highlight = GradientDrawable().apply { setColor(Color.WHITE) }
        val shadow = GradientDrawable().apply { setColor(Color.rgb(128, 128, 128)) }
        val face = GradientDrawable().apply { setColor(faceColor) }
        return LayerDrawable(arrayOf(outer, highlight, shadow, face)).apply {
            setLayerInset(1, 0, 0, dp(view, 2), dp(view, 2))
            setLayerInset(2, dp(view, 1), dp(view, 1), dp(view, 1), dp(view, 1))
            setLayerInset(3, dp(view, 2), dp(view, 2), dp(view, 2), dp(view, 2))
        }
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
