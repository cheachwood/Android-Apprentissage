package com.google.android.apps.plus.fragments;

import android.content.Context;
import android.database.Cursor;
import com.google.android.apps.plus.api.CheckStreamChangeOperation;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.phone.EsCursorLoader;

public final class StreamChangeLoader extends EsCursorLoader
{
  private final EsAccount mAccount;
  private final String mCircleId;
  private boolean mError;
  private final boolean mFromWidget;
  private final String mGaiaId;
  private boolean mHasStreamChanged;
  private final String mSquareStreamId;
  private final int mView;

  public StreamChangeLoader(Context paramContext, EsAccount paramEsAccount, int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    super(paramContext, null);
    this.mAccount = paramEsAccount;
    this.mView = paramInt;
    this.mCircleId = paramString1;
    this.mGaiaId = paramString2;
    this.mFromWidget = false;
    this.mSquareStreamId = paramString3;
  }

  public final Cursor esLoadInBackground()
  {
    CheckStreamChangeOperation localCheckStreamChangeOperation = new CheckStreamChangeOperation(getContext(), this.mAccount, this.mView, this.mCircleId, this.mGaiaId, this.mSquareStreamId, this.mFromWidget, null, null);
    localCheckStreamChangeOperation.start();
    this.mError = localCheckStreamChangeOperation.hasError();
    if (!this.mError)
      this.mHasStreamChanged = localCheckStreamChangeOperation.hasStreamChanged();
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
 * Qualified Name:     com.google.android.apps.plus.fragments.StreamChangeLoader
 * JD-Core Version:    0.6.2
 */