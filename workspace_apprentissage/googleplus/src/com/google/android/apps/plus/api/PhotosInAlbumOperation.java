package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.android.apps.plus.service.EsSyncAdapterService.SyncState;
import com.google.api.services.plusi.model.PhotosInAlbumRequest;
import com.google.api.services.plusi.model.PhotosInAlbumRequestJson;
import com.google.api.services.plusi.model.PhotosInAlbumResponse;
import com.google.api.services.plusi.model.PhotosInAlbumResponseJson;

public final class PhotosInAlbumOperation extends PlusiOperation<PhotosInAlbumRequest, PhotosInAlbumResponse>
{
  private final String mAuthkey;
  private final String mCollectionId;
  private final boolean mCoverOnly;
  private boolean mIsAlbum;
  private final String mOwnerId;
  private final EsSyncAdapterService.SyncState mSyncState;

  public PhotosInAlbumOperation(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState, String paramString1, String paramString2, boolean paramBoolean, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    this(paramContext, paramEsAccount, paramSyncState, paramString1, paramString2, paramBoolean, null, null, null);
  }

  private PhotosInAlbumOperation(Context paramContext, EsAccount paramEsAccount, EsSyncAdapterService.SyncState paramSyncState, String paramString1, String paramString2, boolean paramBoolean, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString3)
  {
    super(paramContext, paramEsAccount, "photosinalbum", PhotosInAlbumRequestJson.getInstance(), PhotosInAlbumResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mCollectionId = paramString1;
    this.mOwnerId = paramString2;
    this.mCoverOnly = paramBoolean;
    this.mSyncState = paramSyncState;
    this.mAuthkey = paramString3;
    try
    {
      Long.parseLong(this.mCollectionId);
      this.mIsAlbum = true;
      label60: return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      break label60;
    }
  }

  public PhotosInAlbumOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString3)
  {
    this(paramContext, paramEsAccount, null, paramString1, paramString2, false, paramIntent, paramOperationListener, paramString3);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.PhotosInAlbumOperation
 * JD-Core Version:    0.6.2
 */