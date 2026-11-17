package com.wangshaojie.libretv;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;

/**
 * 梓榕TV 显示设置：MiSans 字体 + 全局字号档位
 * 档位 0=小 1=标准 2=大 3=超大，默认 1（标准，已比原版收小一档）
 */
final class ZiFont {
    private static final String PREF = "zirong_display";
    private static final String KEY_SCALE = "font_scale";
    private static Typeface cached = null;
    private static final float[] LEVELS = {0.82f, 0.90f, 1.00f, 1.12f};

    static int level(Context c) {
        SharedPreferences p = c.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        return Math.max(0, Math.min(3, p.getInt(KEY_SCALE, 1)));
    }

    static void setLevel(Context c, int lv) {
        c.getSharedPreferences(PREF, Context.MODE_PRIVATE).edit()
                .putInt(KEY_SCALE, Math.max(0, Math.min(3, lv))).apply();
    }

    static String levelName(Context c) {
        return new String[]{"小", "标准", "大", "超大"}[level(c)];
    }

    /** 把设计基准字号按当前档位缩放 */
    static float sz(Context c, float base) {
        return base * LEVELS[level(c)];
    }

    /** 全局 MiSans 字体，失败回落系统默认 */
    static Typeface face(Context c) {
        if (cached != null) return cached;
        try {
            cached = Typeface.createFromAsset(c.getAssets(), "fonts/MiSans-Regular.ttf");
        } catch (Throwable t) {
            Log.w("ZiFont", "MiSans load failed", t);
            cached = Typeface.DEFAULT;
        }
        return cached;
    }
}
