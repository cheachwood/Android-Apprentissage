package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.ForceLoadContentObserver;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.EsCursorLoader;

public final class SuggestedPeopleListLoader extends EsCursorLoader
{
  private final EsAccount mAccount;
  private boolean mFirstRun = true;
  private final Loader<Cursor>.ForceLoadContentObserver mObserver;
  private final String[] mProjection;
  private final boolean mRefreshDataOnStart;

  public SuggestedPeopleListLoader(Context paramContext, EsAccount paramEsAccount, String[] paramArrayOfString, boolean paramBoolean)
  {
    super(paramContext);
    this.mRefreshDataOnStart = paramBoolean;
    setUri(EsProvider.CONTACTS_URI);
    this.mObserver = new Loader.ForceLoadContentObserver(this);
    this.mAccount = paramEsAccount;
    this.mProjection = paramArrayOfString;
  }

  public final Cursor esLoadInBackground()
  {
    Context localContext = getContext();
    EsAccount localEsAccount = this.mAccount;
    String[] arrayOfString = this.mProjection;
    if ((this.mFirstRun) && (this.mRefreshDataOnStart));
    for (boolean bool = true; ; bool = false)
    {
      Cursor localCursor = EsPeopleData.getSuggestedPeople(localContext, localEsAccount, arrayOfString, bool, false);
      if (localCursor != null)
      {
        this.mFirstRun = false;
        localCursor.registerContentObserver(this.mObserver);
      }
      return localCursor;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.SuggestedPeopleListLoader
 * JD-Core Version:    0.6.2
 */