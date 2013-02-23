package com.google.android.apps.plus.phone;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.Uri.Builder;

public final class GoogleFeedback
{
  private static final Uri FEEDBACK_URI = Uri.parse("http://support.google.com/mobile/?p=plus_survey_android");

  public static void launch(Activity paramActivity)
  {
    ServiceConnection local1 = new ServiceConnection()
    {
      // ERROR //
      public final void onServiceConnected(ComponentName paramAnonymousComponentName, android.os.IBinder paramAnonymousIBinder)
      {
        // Byte code:
        //   0: invokestatic 33	android/os/Parcel:obtain	()Landroid/os/Parcel;
        //   3: astore 5
        //   5: iconst_1
        //   6: istore 6
        //   8: iconst_0
        //   9: istore 7
        //   11: aconst_null
        //   12: astore 8
        //   14: aload_0
        //   15: getfield 16	com/google/android/apps/plus/phone/GoogleFeedback$1:val$parentActivity	Landroid/app/Activity;
        //   18: invokevirtual 39	android/app/Activity:getWindow	()Landroid/view/Window;
        //   21: invokevirtual 45	android/view/Window:getDecorView	()Landroid/view/View;
        //   24: invokevirtual 50	android/view/View:getRootView	()Landroid/view/View;
        //   27: astore 8
        //   29: aload 8
        //   31: invokevirtual 54	android/view/View:isDrawingCacheEnabled	()Z
        //   34: istore 6
        //   36: iconst_0
        //   37: istore 7
        //   39: iload 6
        //   41: ifne +12 -> 53
        //   44: aload 8
        //   46: iconst_1
        //   47: invokevirtual 58	android/view/View:setDrawingCacheEnabled	(Z)V
        //   50: iconst_1
        //   51: istore 7
        //   53: aload 8
        //   55: invokevirtual 62	android/view/View:getDrawingCache	()Landroid/graphics/Bitmap;
        //   58: astore 13
        //   60: aload 13
        //   62: ifnull +12 -> 74
        //   65: aload 13
        //   67: ldc 63
        //   69: invokestatic 67	com/google/android/apps/plus/phone/GoogleFeedback:access$000	(Landroid/graphics/Bitmap;I)Landroid/graphics/Bitmap;
        //   72: astore 13
        //   74: aload 13
        //   76: ifnull +11 -> 87
        //   79: aload 13
        //   81: aload 5
        //   83: iconst_0
        //   84: invokevirtual 73	android/graphics/Bitmap:writeToParcel	(Landroid/os/Parcel;I)V
        //   87: iload 7
        //   89: ifeq +19 -> 108
        //   92: iload 6
        //   94: ifne +14 -> 108
        //   97: aload 8
        //   99: ifnull +9 -> 108
        //   102: aload 8
        //   104: iconst_0
        //   105: invokevirtual 58	android/view/View:setDrawingCacheEnabled	(Z)V
        //   108: aload_2
        //   109: iconst_1
        //   110: aload 5
        //   112: aconst_null
        //   113: iconst_0
        //   114: invokeinterface 79 5 0
        //   119: pop
        //   120: aload_0
        //   121: getfield 16	com/google/android/apps/plus/phone/GoogleFeedback$1:val$parentActivity	Landroid/app/Activity;
        //   124: aload_0
        //   125: invokevirtual 83	android/app/Activity:unbindService	(Landroid/content/ServiceConnection;)V
        //   128: return
        //   129: astore 12
        //   131: iload 7
        //   133: ifeq -25 -> 108
        //   136: iload 6
        //   138: ifne -30 -> 108
        //   141: aload 8
        //   143: ifnull -35 -> 108
        //   146: aload 8
        //   148: iconst_0
        //   149: invokevirtual 58	android/view/View:setDrawingCacheEnabled	(Z)V
        //   152: goto -44 -> 108
        //   155: astore 4
        //   157: aload 4
        //   159: invokevirtual 86	android/os/RemoteException:printStackTrace	()V
        //   162: aload_0
        //   163: getfield 16	com/google/android/apps/plus/phone/GoogleFeedback$1:val$parentActivity	Landroid/app/Activity;
        //   166: aload_0
        //   167: invokevirtual 83	android/app/Activity:unbindService	(Landroid/content/ServiceConnection;)V
        //   170: goto -42 -> 128
        //   173: astore 10
        //   175: iload 7
        //   177: ifeq -69 -> 108
        //   180: iload 6
        //   182: ifne -74 -> 108
        //   185: aload 8
        //   187: ifnull -79 -> 108
        //   190: aload 8
        //   192: iconst_0
        //   193: invokevirtual 58	android/view/View:setDrawingCacheEnabled	(Z)V
        //   196: goto -88 -> 108
        //   199: astore_3
        //   200: aload_0
        //   201: getfield 16	com/google/android/apps/plus/phone/GoogleFeedback$1:val$parentActivity	Landroid/app/Activity;
        //   204: aload_0
        //   205: invokevirtual 83	android/app/Activity:unbindService	(Landroid/content/ServiceConnection;)V
        //   208: aload_3
        //   209: athrow
        //   210: astore 9
        //   212: iload 7
        //   214: ifeq +19 -> 233
        //   217: iload 6
        //   219: ifne +14 -> 233
        //   222: aload 8
        //   224: ifnull +9 -> 233
        //   227: aload 8
        //   229: iconst_0
        //   230: invokevirtual 58	android/view/View:setDrawingCacheEnabled	(Z)V
        //   233: aload 9
        //   235: athrow
        //
        // Exception table:
        //   from	to	target	type
        //   14	87	129	java/lang/OutOfMemoryError
        //   0	5	155	android/os/RemoteException
        //   102	120	155	android/os/RemoteException
        //   146	152	155	android/os/RemoteException
        //   190	196	155	android/os/RemoteException
        //   227	236	155	android/os/RemoteException
        //   14	87	173	java/lang/Exception
        //   0	5	199	finally
        //   102	120	199	finally
        //   146	152	199	finally
        //   157	162	199	finally
        //   190	196	199	finally
        //   227	236	199	finally
        //   14	87	210	finally
      }

      public final void onServiceDisconnected(ComponentName paramAnonymousComponentName)
      {
      }
    };
    Uri.Builder localBuilder;
    if (!paramActivity.bindService(Intents.getGoogleFeedbackIntent$7ec49240(), local1, 1))
    {
      paramActivity.unbindService(local1);
      localBuilder = FEEDBACK_URI.buildUpon();
    }
    try
    {
      str = paramActivity.getPackageManager().getPackageInfo(paramActivity.getPackageName(), 0).versionName;
      localBuilder.appendQueryParameter("version", str);
      Intent localIntent = new Intent("android.intent.action.VIEW", localBuilder.build());
      localIntent.addFlags(524288);
      localIntent.addCategory("android.intent.category.BROWSABLE");
      localIntent.putExtra("com.android.browser.application_id", paramActivity.getPackageName());
      paramActivity.startActivity(localIntent);
      return;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
        String str = "unknown";
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.GoogleFeedback
 * JD-Core Version:    0.6.2
 */