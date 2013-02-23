package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.PhotosNameTagApprovalRequest;
import com.google.api.services.plusi.model.PhotosNameTagApprovalRequestJson;
import com.google.api.services.plusi.model.PhotosNameTagApprovalResponse;
import com.google.api.services.plusi.model.PhotosNameTagApprovalResponseJson;

public final class PhotosNameTagApprovalOperation extends PlusiOperation<PhotosNameTagApprovalRequest, PhotosNameTagApprovalResponse>
{
  private final boolean mApprove;
  private final String mOwnerId;
  private final long mPhotoId;
  private final long mShapeId;

  public PhotosNameTagApprovalOperation(Context paramContext, EsAccount paramEsAccount, long paramLong1, String paramString, long paramLong2, boolean paramBoolean, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "photosnametagapproval", PhotosNameTagApprovalRequestJson.getInstance(), PhotosNameTagApprovalResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mOwnerId = paramString;
    this.mPhotoId = paramLong1;
    this.mShapeId = paramLong2;
    this.mApprove = paramBoolean;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.PhotosNameTagApprovalOperation
 * JD-Core Version:    0.6.2
 */