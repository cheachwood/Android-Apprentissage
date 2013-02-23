package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.android.apps.plus.service.EsSyncAdapterService.SyncState;
import com.google.api.services.plusi.model.PhotosOfUserRequest;
import com.google.api.services.plusi.model.PhotosOfUserRequestJson;
import com.google.api.services.plusi.model.PhotosOfUserResponse;
import com.google.api.services.plusi.model.PhotosOfUserResponseJson;

public final class PhotosOfUserOperation extends PlusiOperation<PhotosOfUserRequest, PhotosOfUserResponse>
{
  private final boolean mCoverOnly;
  private final EsSyncAdapterService.SyncState mSyncState;
  private final String mUserId;

  public PhotosOfUserOperation(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState, String paramString, boolean paramBoolean, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "photosofuser", PhotosOfUserRequestJson.getInstance(), PhotosOfUserResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mSyncState = paramSyncState;
    this.mUserId = paramString;
    this.mCoverOnly = paramBoolean;
  }

  public PhotosOfUserOperation(Context paramContext, EsAccount paramEsAccount, String paramString, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    this(paramContext, paramEsAccount, null, paramString, false, paramIntent, paramOperationListener);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.PhotosOfUserOperation
 * JD-Core Version:    0.6.2
 */