package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.ForceLoadContentObserver;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.EsCursorLoader;

public final class PeopleListLoader extends EsCursorLoader
{
  private final EsAccount mAccount;
  private final String mCircleId;
  private final String mExcludedCircleId;
  private final boolean mFilterNullGaiaIds;
  private final boolean mIncludePlusPages;
  private final Loader<Cursor>.ForceLoadContentObserver mObserver = new Loader.ForceLoadContentObserver(this);
  private final String[] mProjection;

  public PeopleListLoader(Context paramContext, EsAccount paramEsAccount, String[] paramArrayOfString, String paramString)
  {
    super(paramContext);
    setUri(EsProvider.CONTACTS_URI);
    this.mAccount = paramEsAccount;
    this.mProjection = paramArrayOfString;
    this.mCircleId = paramString;
    this.mExcludedCircleId = null;
    this.mIncludePlusPages = true;
    this.mFilterNullGaiaIds = false;
  }

  public PeopleListLoader(Context paramContext, EsAccount paramEsAccount, String[] paramArrayOfString, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramContext);
    setUri(EsProvider.CONTACTS_URI);
    this.mAccount = paramEsAccount;
    this.mProjection = paramArrayOfString;
    this.mCircleId = null;
    this.mExcludedCircleId = paramString;
    this.mIncludePlusPages = paramBoolean1;
    this.mFilterNullGaiaIds = paramBoolean2;
  }

  public final Cursor esLoadInBackground()
  {
    String str = "in_my_circles=1";
    if (this.mFilterNullGaiaIds)
      str = str + " AND gaia_id IS NOT NULL";
    if (!this.mIncludePlusPages)
      str = str + " AND profile_type!=2";
    Cursor localCursor = EsPeopleData.getPeople(getContext(), this.mAccount, this.mCircleId, this.mExcludedCircleId, this.mProjection, str, null);
    if (localCursor != null)
      localCursor.registerContentObserver(this.mObserver);
    return localCursor;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.PeopleListLoader
 * JD-Core Version:    0.6.2
 */