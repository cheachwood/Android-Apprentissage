package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.DataPhoto;
import com.google.api.services.plusi.model.GetPhotoRequest;
import com.google.api.services.plusi.model.GetPhotoRequestJson;
import com.google.api.services.plusi.model.GetPhotoResponse;
import com.google.api.services.plusi.model.GetPhotoResponseJson;

public final class GetPhotoOperation extends PlusiOperation<GetPhotoRequest, GetPhotoResponse>
{
  private DataPhoto mDataPhoto;
  private final long mPhotoId;
  private final String mUserId;

  public GetPhotoOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, long paramLong, String paramString)
  {
    super(paramContext, paramEsAccount, "getphoto", GetPhotoRequestJson.getInstance(), GetPhotoResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mPhotoId = paramLong;
    this.mUserId = paramString;
  }

  public final DataPhoto getDataPhoto()
  {
    return this.mDataPhoto;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetPhotoOperation
 * JD-Core Version:    0.6.2
 */