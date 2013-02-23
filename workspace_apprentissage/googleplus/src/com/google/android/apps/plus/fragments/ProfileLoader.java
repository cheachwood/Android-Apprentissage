package com.google.android.apps.plus.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.ForceLoadContentObserver;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData.ProfileAndContactData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.phone.EsAsyncTaskLoader;

public final class ProfileLoader extends EsAsyncTaskLoader<EsPeopleData.ProfileAndContactData>
{
  private final EsAccount mAccount;
  private EsPeopleData.ProfileAndContactData mData;
  private final boolean mFullProfileNeeded;
  private final Loader<EsPeopleData.ProfileAndContactData>.ForceLoadContentObserver mObserver = new Loader.ForceLoadContentObserver(this);
  private boolean mObserverRegistered;
  private final String mPersonId;

  public ProfileLoader(Context paramContext, EsAccount paramEsAccount, String paramString, boolean paramBoolean)
  {
    super(paramContext);
    this.mAccount = paramEsAccount;
    this.mPersonId = paramString;
    this.mFullProfileNeeded = paramBoolean;
  }

  protected final void onAbandon()
  {
    if (this.mObserverRegistered)
    {
      getContext().getContentResolver().unregisterContentObserver(this.mObserver);
      this.mObserverRegistered = false;
    }
  }

  protected final void onReset()
  {
    super.onReset();
    this.mData = null;
  }

  protected final void onStartLoading()
  {
    if (!this.mObserverRegistered)
    {
      getContext().getContentResolver().registerContentObserver(Uri.withAppendedPath(EsProvider.CONTACT_BY_PERSON_ID_URI, this.mPersonId), false, this.mObserver);
      this.mObserverRegistered = true;
    }
    if (this.mData == null)
      forceLoad();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.ProfileLoader
 * JD-Core Version:    0.6.2
 */