package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.PhotosPlusOneRequest;
import com.google.api.services.plusi.model.PhotosPlusOneRequestJson;
import com.google.api.services.plusi.model.PhotosPlusOneResponse;
import com.google.api.services.plusi.model.PhotosPlusOneResponseJson;

public final class PhotosPlusOneOperation extends PlusiOperation<PhotosPlusOneRequest, PhotosPlusOneResponse>
{
  private final long mAlbumId;
  private final boolean mIsPlusOne;
  private final String mOwnerId;
  private final String mPhotoId;

  public PhotosPlusOneOperation(Context paramContext, EsAccount paramEsAccount, long paramLong1, String paramString, long paramLong2, boolean paramBoolean, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "photosplusone", PhotosPlusOneRequestJson.getInstance(), PhotosPlusOneResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mPhotoId = Long.toString(paramLong1);
    this.mOwnerId = paramString;
    this.mAlbumId = paramLong2;
    this.mIsPlusOne = paramBoolean;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.PhotosPlusOneOperation
 * JD-Core Version:    0.6.2
 */