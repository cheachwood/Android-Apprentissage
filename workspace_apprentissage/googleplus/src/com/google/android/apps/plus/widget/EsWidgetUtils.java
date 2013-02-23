package com.google.android.apps.plus.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.text.TextUtils;

public final class EsWidgetUtils
{
  public static String loadCircleId(Context paramContext, int paramInt)
  {
    String str = paramContext.getSharedPreferences("com.google.android.apps.plus.widget.EsWidgetUtils", 0).getString("circleId_" + paramInt, null);
    if (TextUtils.isEmpty(str))
      str = null;
    return str;
  }

  public static void saveCircleInfo(Context paramContext, int paramInt, String paramString1, String paramString2)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("com.google.android.apps.plus.widget.EsWidgetUtils", 0).edit();
    String str1 = "circleId_" + paramInt;
    if (paramString1 == null)
      paramString1 = "";
    localEditor.putString(str1, paramString1);
    String str2 = "circleName_" + paramInt;
    if (paramString2 == null)
      paramString2 = "";
    localEditor.putString(str2, paramString2);
    if (Build.VERSION.SDK_INT < 9)
      localEditor.commit();
    while (true)
    {
      return;
      localEditor.apply();
    }
  }

  public static void updateAllWidgets$1a552341(Context paramContext)
  {
    for (int k : AppWidgetManager.getInstance(paramContext).getAppWidgetIds(new ComponentName(paramContext, EsWidgetProvider.class)))
    {
      if (TextUtils.isEmpty(loadCircleId(paramContext, k)))
        saveCircleInfo(paramContext, k, "v.all.circles", null);
      EsWidgetProvider.updateWidget(paramContext, k, null);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.widget.EsWidgetUtils
 * JD-Core Version:    0.6.2
 */