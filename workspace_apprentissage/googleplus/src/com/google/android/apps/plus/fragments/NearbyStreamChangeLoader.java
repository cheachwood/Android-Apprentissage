package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.database.Cursor;
import com.google.android.apps.plus.api.CheckNearbyStreamChangeOperation;
import com.google.android.apps.plus.content.DbLocation;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.phone.EsCursorLoader;

public final class NearbyStreamChangeLoader extends EsCursorLoader
{
  private final EsAccount mAccount;
  private boolean mError;
  private boolean mHasStreamChanged;
  private final DbLocation mLocation;

  public NearbyStreamChangeLoader(Context paramContext, EsAccount paramEsAccount, DbLocation paramDbLocation)
  {
    super(paramContext, null);
    this.mAccount = paramEsAccount;
    this.mLocation = paramDbLocation;
  }

  public final Cursor esLoadInBackground()
  {
    CheckNearbyStreamChangeOperation localCheckNearbyStreamChangeOperation = new CheckNearbyStreamChangeOperation(getContext(), this.mAccount, this.mLocation, null, null);
    localCheckNearbyStreamChangeOperation.start();
    this.mError = localCheckNearbyStreamChangeOperation.hasError();
    if (!this.mError)
      this.mHasStreamChanged = localCheckNearbyStreamChangeOperation.hasStreamChanged();
    return null;
  }

  public final boolean hasError()
  {
    return this.mError;
  }

  public final boolean hasStreamChanged()
  {
    return this.mHasStreamChanged;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.fragments.NearbyStreamChangeLoader
 * JD-Core Version:    0.6.2
 */