package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import com.google.android.apps.plus.api.GetActivityOperation;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.phone.EsCursorLoader;
import com.google.android.apps.plus.phone.EsMatrixCursor;
import com.google.android.apps.plus.util.EsLog;
import java.util.ArrayList;

public final class DesktopActivityIdLoader extends EsCursorLoader
{
  private final EsAccount mAccount;
  private final String mDesktopActivityId;
  private final String mOwnerGaiaId;

  public DesktopActivityIdLoader(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2)
  {
    super(paramContext);
    this.mAccount = paramEsAccount;
    this.mDesktopActivityId = paramString1;
    this.mOwnerGaiaId = paramString2;
  }

  public final Cursor esLoadInBackground()
  {
    new ArrayList().add(this.mDesktopActivityId);
    GetActivityOperation localGetActivityOperation = new GetActivityOperation(getContext(), this.mAccount, this.mDesktopActivityId, this.mOwnerGaiaId, null, null, null);
    localGetActivityOperation.start();
    Object localObject;
    if (localGetActivityOperation.getException() != null)
    {
      if (EsLog.isLoggable("DesktopActivityIdLoader", 6))
        Log.e("DesktopActivityIdLoader", "Cannot resolve desktop activity ID: " + this.mDesktopActivityId, localGetActivityOperation.getException());
      localObject = null;
    }
    while (true)
    {
      return localObject;
      if (localGetActivityOperation.hasError())
      {
        if (!EsLog.isLoggable("DesktopActivityIdLoader", 6))
          break;
        Log.e("DesktopActivityIdLoader", "Cannot resolve  desktop activity ID: " + this.mDesktopActivityId + ": " + localGetActivityOperation.getErrorCode());
        break;
      }
      localObject = new EsMatrixCursor(new String[] { "activity_id" });
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localGetActivityOperation.getResponseUpdateId();
      ((EsMatrixCursor)localObject).addRow(arrayOfObject);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.DesktopActivityIdLoader
 * JD-Core Version:    0.6.2
 */