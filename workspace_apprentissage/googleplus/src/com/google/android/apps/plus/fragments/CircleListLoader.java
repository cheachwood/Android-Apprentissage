package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.ForceLoadContentObserver;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.EsCursorLoader;

public final class CircleListLoader extends EsCursorLoader
{
  private final EsAccount mAccount;
  private final int mMaxResults;
  private final Loader<Cursor>.ForceLoadContentObserver mObserver = new Loader.ForceLoadContentObserver(this);
  private final String[] mProjection;
  private final String mQuery;
  private final int mUsageType;

  public CircleListLoader(Context paramContext, EsAccount paramEsAccount, int paramInt, String[] paramArrayOfString)
  {
    this(paramContext, paramEsAccount, paramInt, paramArrayOfString, null, 0);
  }

  public CircleListLoader(Context paramContext, EsAccount paramEsAccount, int paramInt1, String[] paramArrayOfString, String paramString, int paramInt2)
  {
    super(paramContext);
    setUri(EsProvider.CIRCLES_URI);
    this.mAccount = paramEsAccount;
    this.mProjection = paramArrayOfString;
    this.mUsageType = paramInt1;
    this.mQuery = paramString;
    this.mMaxResults = paramInt2;
  }

  public final Cursor esLoadInBackground()
  {
    Cursor localCursor = EsPeopleData.getCircles(getContext(), this.mAccount, this.mUsageType, this.mProjection, this.mQuery, this.mMaxResults);
    if (localCursor != null)
      localCursor.registerContentObserver(this.mObserver);
    return localCursor;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.CircleListLoader
 * JD-Core Version:    0.6.2
 */