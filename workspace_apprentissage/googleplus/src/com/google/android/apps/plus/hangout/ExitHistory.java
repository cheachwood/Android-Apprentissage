package com.google.android.apps.plus.hangout;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.android.apps.plus.service.Hangout.Info;
import com.google.android.apps.plus.service.Hangout.LaunchSource;
import com.google.android.apps.plus.service.Hangout.RoomType;

public class ExitHistory
{
  static
  {
    if (!ExitHistory.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public static boolean exitReported(Context paramContext, Hangout.Info paramInfo)
  {
    SharedPreferences localSharedPreferences = findPrefs(paramContext, paramInfo);
    boolean bool = false;
    if (localSharedPreferences != null)
      bool = localSharedPreferences.getBoolean("EXIT_REPORTED", false);
    return bool;
  }

  public static boolean exitedNormally(Context paramContext, Hangout.Info paramInfo)
  {
    SharedPreferences localSharedPreferences = findPrefs(paramContext, paramInfo);
    boolean bool = false;
    if (localSharedPreferences != null)
    {
      int i = localSharedPreferences.getInt("LAST_ERROR", -1);
      bool = false;
      if (i == -1)
        bool = true;
    }
    return bool;
  }

  private static SharedPreferences findPrefs(Context paramContext, Hangout.Info paramInfo)
  {
    SharedPreferences localSharedPreferences1 = paramContext.getSharedPreferences(ExitHistory.class.getName(), 0);
    Hangout.Info localInfo;
    SharedPreferences localSharedPreferences2;
    if (!localSharedPreferences1.getBoolean("INFO_HAS_INFO", false))
    {
      localInfo = null;
      localSharedPreferences2 = null;
      if (localInfo != null)
        break label85;
    }
    while (true)
    {
      return localSharedPreferences2;
      localInfo = new Hangout.Info(Hangout.RoomType.values()[localSharedPreferences1.getInt("INFO_ROOM_TYPE", 0)], localSharedPreferences1.getString("INFO_DOMAIN", ""), null, localSharedPreferences1.getString("INFO_ID", ""), null, Hangout.LaunchSource.None, false);
      break;
      label85: boolean bool = localInfo.equals(paramInfo);
      localSharedPreferences2 = null;
      if (bool)
        localSharedPreferences2 = localSharedPreferences1;
    }
  }

  public static GCommNativeWrapper.Error getError(Context paramContext, Hangout.Info paramInfo)
  {
    SharedPreferences localSharedPreferences = findPrefs(paramContext, paramInfo);
    GCommNativeWrapper.Error localError = null;
    int i;
    if (localSharedPreferences != null)
    {
      i = localSharedPreferences.getInt("LAST_ERROR", -1);
      localError = null;
      if (i != -1)
        break label33;
    }
    while (true)
    {
      return localError;
      label33: localError = GCommNativeWrapper.Error.values()[i];
    }
  }

  public static void recordErrorExit(Context paramContext, Hangout.Info paramInfo, GCommNativeWrapper.Error paramError, boolean paramBoolean)
  {
    assert (paramError.ordinal() != -1);
    recordExit(paramContext, paramInfo, paramError.ordinal(), paramBoolean);
  }

  private static void recordExit(Context paramContext, Hangout.Info paramInfo, int paramInt, boolean paramBoolean)
  {
    if (paramInfo == null);
    while (true)
    {
      return;
      SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(ExitHistory.class.getName(), 0).edit();
      localEditor.putBoolean("INFO_HAS_INFO", true);
      localEditor.putInt("INFO_ROOM_TYPE", paramInfo.getRoomType().ordinal());
      localEditor.putString("INFO_DOMAIN", paramInfo.getDomain());
      localEditor.putString("INFO_ID", paramInfo.getId());
      localEditor.putInt("LAST_ERROR", paramInt);
      localEditor.putBoolean("EXIT_REPORTED", paramBoolean);
      localEditor.commit();
    }
  }

  public static void recordExitReported(Context paramContext, Hangout.Info paramInfo)
  {
    recordExit(paramContext, paramInfo, -1, true);
  }

  public static void recordNormalExit(Context paramContext, Hangout.Info paramInfo, boolean paramBoolean)
  {
    recordExit(paramContext, paramInfo, -1, paramBoolean);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.ExitHistory
 * JD-Core Version:    0.6.2
 */