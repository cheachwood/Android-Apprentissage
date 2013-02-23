package com.google.android.apps.plus.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.ForceLoadContentObserver;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.EsCursorLoader;

public final class PeopleSearchListLoader extends EsCursorLoader
{
  private final EsAccount mAccount;
  private final Loader<Cursor>.ForceLoadContentObserver mObserver = new Loader.ForceLoadContentObserver(this);
  private final String[] mProjection;
  private Uri mQueryUri;

  public PeopleSearchListLoader(Context paramContext, EsAccount paramEsAccount, String[] paramArrayOfString, String paramString1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, String paramString2, int paramInt)
  {
    super(paramContext);
    setUri(EsProvider.CONTACTS_URI);
    if (paramBoolean3);
    for (String str = "gaia_id IS NOT NULL"; ; str = null)
    {
      setSelection(str);
      this.mAccount = paramEsAccount;
      this.mProjection = paramArrayOfString;
      this.mQueryUri = EsProvider.buildPeopleQueryUri(this.mAccount, paramString1, paramBoolean1, paramBoolean2, paramString2, 10);
      return;
    }
  }

  public final Cursor esLoadInBackground()
  {
    Context localContext = getContext();
    boolean bool = EsPeopleData.ensurePeopleSynced(localContext, this.mAccount);
    Object localObject = null;
    if (!bool);
    while (true)
    {
      return localObject;
      Cursor localCursor = localContext.getContentResolver().query(this.mQueryUri, this.mProjection, getSelection(), null, null);
      if (localCursor != null)
        localCursor.registerContentObserver(this.mObserver);
      localObject = localCursor;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.PeopleSearchListLoader
 * JD-Core Version:    0.6.2
 */