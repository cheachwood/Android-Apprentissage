package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.ForceLoadContentObserver;
import com.google.android.apps.plus.api.GetSquaresOperation;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.content.EsSquaresData;
import com.google.android.apps.plus.phone.EsCursorLoader;

public final class SquareListLoader extends EsCursorLoader
{
  private final EsAccount mAccount;
  private boolean mIsDataStale;
  private final Loader<Cursor>.ForceLoadContentObserver mObserver = new Loader.ForceLoadContentObserver(this);
  private final String[] mProjection;

  public SquareListLoader(Context paramContext, EsAccount paramEsAccount, String[] paramArrayOfString)
  {
    super(paramContext);
    setUri(EsProvider.SQUARES_URI);
    this.mAccount = paramEsAccount;
    this.mProjection = paramArrayOfString;
  }

  public final Cursor esLoadInBackground()
  {
    Context localContext = getContext();
    long l = EsSquaresData.queryLastSquaresSyncTimestamp(getContext(), this.mAccount);
    boolean bool1;
    Object localObject;
    if (System.currentTimeMillis() - l > 900000L)
    {
      bool1 = true;
      this.mIsDataStale = bool1;
      if (l > 0L)
        break label90;
      GetSquaresOperation localGetSquaresOperation = new GetSquaresOperation(localContext, this.mAccount, null, null, null);
      localGetSquaresOperation.start();
      boolean bool2 = localGetSquaresOperation.hasError();
      localObject = null;
      if (!bool2)
        break label90;
    }
    while (true)
    {
      return localObject;
      bool1 = false;
      break;
      label90: Cursor localCursor = EsSquaresData.getJoinedSquares(getContext(), this.mAccount, this.mProjection, "square_name");
      if (localCursor != null)
        localCursor.registerContentObserver(this.mObserver);
      localObject = localCursor;
    }
  }

  public final boolean isDataStale()
  {
    return this.mIsDataStale;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.SquareListLoader
 * JD-Core Version:    0.6.2
 */