package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.ForceLoadContentObserver;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.EsCursorLoader;
import java.util.ArrayList;

public final class BlockedPeopleListLoader extends EsCursorLoader
{
  private final EsAccount mAccount;
  private final ArrayList<String> mIncludedPersonIds;
  private final Loader<Cursor>.ForceLoadContentObserver mObserver;
  private final String[] mProjection;

  public BlockedPeopleListLoader(Context paramContext, EsAccount paramEsAccount, String[] paramArrayOfString, ArrayList<String> paramArrayList)
  {
    super(paramContext);
    setUri(EsProvider.CONTACTS_URI);
    this.mObserver = new Loader.ForceLoadContentObserver(this);
    this.mAccount = paramEsAccount;
    this.mProjection = paramArrayOfString;
    this.mIncludedPersonIds = paramArrayList;
  }

  public final Cursor esLoadInBackground()
  {
    Cursor localCursor = EsPeopleData.getBlockedPeople(getContext(), this.mAccount, this.mProjection, this.mIncludedPersonIds);
    if (localCursor != null)
      localCursor.registerContentObserver(this.mObserver);
    return localCursor;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.BlockedPeopleListLoader
 * JD-Core Version:    0.6.2
 */