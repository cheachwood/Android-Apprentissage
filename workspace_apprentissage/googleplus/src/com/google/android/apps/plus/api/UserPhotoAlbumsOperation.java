package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.android.apps.plus.service.EsSyncAdapterService.SyncState;
import com.google.api.services.plusi.model.UserPhotoAlbumsRequest;
import com.google.api.services.plusi.model.UserPhotoAlbumsRequestJson;
import com.google.api.services.plusi.model.UserPhotoAlbumsResponse;
import com.google.api.services.plusi.model.UserPhotoAlbumsResponseJson;

public final class UserPhotoAlbumsOperation extends PlusiOperation<UserPhotoAlbumsRequest, UserPhotoAlbumsResponse>
{
  private final String mOwnerId;
  private final EsSyncAdapterService.SyncState mSyncState;

  public UserPhotoAlbumsOperation(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState, String paramString, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "userphotoalbums", UserPhotoAlbumsRequestJson.getInstance(), UserPhotoAlbumsResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mSyncState = paramSyncState;
    this.mOwnerId = paramString;
  }

  public UserPhotoAlbumsOperation(Context paramContext, EsAccount paramEsAccount, String paramString, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    this(paramContext, paramEsAccount, null, paramString, paramIntent, paramOperationListener);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.UserPhotoAlbumsOperation
 * JD-Core Version:    0.6.2
 */