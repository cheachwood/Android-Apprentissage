package com.google.android.apps.plus.service;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsProvider;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class CircleMembershipManager
{
  private static String sAccountName;
  private static ConcurrentHashMap<String, String> sCompletedRequests = new ConcurrentHashMap();
  private static Handler sHandler;
  private static ConcurrentHashMap<String, String> sPendingRequests = new ConcurrentHashMap();
  private static boolean sPeopleListVisible;

  private static void checkAccount(EsAccount paramEsAccount)
  {
    if (!TextUtils.equals(sAccountName, paramEsAccount.getName()))
    {
      sPendingRequests.clear();
      sAccountName = paramEsAccount.getName();
    }
  }

  public static boolean isCircleMembershipRequestPending(String paramString)
  {
    return sPendingRequests.containsKey(paramString);
  }

  public static void onPeopleListVisibilityChange(boolean paramBoolean)
  {
    if (sPeopleListVisible != paramBoolean)
    {
      sPeopleListVisible = paramBoolean;
      if (paramBoolean)
        sCompletedRequests.clear();
    }
  }

  public static void onStartAddToCircleRequest$3608be29(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    checkAccount(paramEsAccount);
    sPendingRequests.put(paramString, "");
    paramContext.getContentResolver().notifyChange(EsProvider.CONTACTS_URI, null);
  }

  public static void setCircleMembershipResult(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, boolean paramBoolean)
  {
    checkAccount(paramEsAccount);
    sCompletedRequests.put(paramString1, "");
    sPendingRequests.remove(paramString1);
    if (!paramBoolean)
    {
      AndroidNotification.showCircleAddFailedNotification(paramContext, paramEsAccount, paramString1, paramString2);
      paramContext.getContentResolver().notifyChange(EsProvider.CONTACTS_URI, null);
    }
  }

  public static void showToastIfNeeded(Context paramContext, EsAccount paramEsAccount)
  {
    if (sPeopleListVisible)
      break label6;
    while (true)
    {
      label6: return;
      if (sPendingRequests.size() > 0)
        continue;
      int i = 0;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("in_my_circles!= 0 AND person_id IN (");
      Iterator localIterator = sCompletedRequests.keySet().iterator();
      while (localIterator.hasNext())
      {
        DatabaseUtils.appendEscapedSQLString(localStringBuilder, (String)localIterator.next());
        localStringBuilder.append(',');
        i = 1;
      }
      sCompletedRequests.clear();
      if (i == 0)
        break;
      localStringBuilder.setLength(-1 + localStringBuilder.length());
      localStringBuilder.append(")");
      Cursor localCursor = paramContext.getContentResolver().query(EsProvider.appendAccountParameter(EsProvider.CONTACTS_URI, paramEsAccount), new String[] { "person_id" }, localStringBuilder.toString(), null, null);
      int j = 0;
      if (localCursor != null);
      try
      {
        int m = localCursor.getCount();
        j = m;
        localCursor.close();
        if (j == 0)
          continue;
        Resources localResources = paramContext.getResources();
        int k = R.plurals.added_to_circle_notification_message;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(j);
        final String str = localResources.getQuantityString(k, j, arrayOfObject);
        if (sHandler == null)
          sHandler = new Handler(Looper.getMainLooper());
        sHandler.post(new Runnable()
        {
          public final void run()
          {
            Toast.makeText(this.val$context, str, 0).show();
          }
        });
      }
      finally
      {
        localCursor.close();
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.CircleMembershipManager
 * JD-Core Version:    0.6.2
 */