package it.sc4tto.pixelsheetconverter

import android.graphics.Color
import org.json.JSONObject

object ThemeJson {
    fun decode(source: String): AppTheme {
        val json = JSONObject(source)
        require(json.optInt("schemaVersion", 1) == 1) { "Versione del tema non supportata" }
        val fallback = AppThemes.all.first()
        val panel = color(json, "panel", fallback.background)
        val title = color(json, "title", fallback.headerBottom)
        val accent = color(json, "accent", fallback.accent)
        val text = color(json, "text", fallback.text)
        val radius = integer(json, "radius", fallback.radiusDp, 0, 32)
        val stroke = integer(json, "border", fallback.strokeDp, 1, 5)
        val elevation = integer(json, "shadow", fallback.elevationDp, 0, 20)
        return AppTheme(
            id = "custom",
            label = json.optString("name", "Tema personale").take(60).ifBlank { "Tema personale" },
            background = panel,
            panelTop = blend(panel, Color.WHITE, .18f),
            panelBottom = blend(panel, Color.BLACK, .07f),
            input = blend(panel, Color.WHITE, .58f),
            text = text,
            muted = blend(text, panel, .45f),
            accent = accent,
            accentText = readableText(accent),
            border = blend(title, Color.GRAY, .70f),
            headerTop = blend(title, Color.WHITE, .18f),
            headerBottom = title,
            headerText = readableText(title),
            radiusDp = radius,
            strokeDp = stroke,
            elevationDp = elevation,
            monospace = json.optString("id") == "terminal" || json.optString("name").contains("terminal", true),
            iconSizeDp = integer(json, "iconSize", 20, 14, 28),
            iconGapDp = integer(json, "iconGap", 8, 2, 16),
        )
    }

    private fun color(json: JSONObject, key: String, fallback: Int): Int {
        if (!json.has(key)) return fallback
        val value = json.getString(key)
        require(Regex("^#[0-9a-fA-F]{6}$").matches(value)) { "Colore $key non valido" }
        return Color.parseColor(value)
    }

    private fun integer(json: JSONObject, key: String, fallback: Int, min: Int, max: Int): Int {
        val value = if (json.has(key)) json.getDouble(key).toInt() else fallback
        return value.coerceIn(min, max)
    }

    private fun blend(a: Int, b: Int, amount: Float) = Color.rgb(
        (Color.red(a) * (1 - amount) + Color.red(b) * amount).toInt(),
        (Color.green(a) * (1 - amount) + Color.green(b) * amount).toInt(),
        (Color.blue(a) * (1 - amount) + Color.blue(b) * amount).toInt(),
    )

    private fun readableText(background: Int): Int {
        val luminance = .299 * Color.red(background) + .587 * Color.green(background) + .114 * Color.blue(background)
        return if (luminance > 150) Color.BLACK else Color.WHITE
    }
}
