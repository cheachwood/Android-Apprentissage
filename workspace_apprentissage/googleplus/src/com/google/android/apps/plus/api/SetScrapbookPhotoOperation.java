package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.SetScrapbookCoverPhotoRequest;
import com.google.api.services.plusi.model.SetScrapbookCoverPhotoRequestJson;
import com.google.api.services.plusi.model.SetScrapbookCoverPhotoResponse;
import com.google.api.services.plusi.model.SetScrapbookCoverPhotoResponseJson;

public final class SetScrapbookPhotoOperation extends PlusiOperation<SetScrapbookCoverPhotoRequest, SetScrapbookCoverPhotoResponse>
{
  private boolean mIsGalleryPhoto;
  private String mPhotoId;
  private int mTopOffset;

  public SetScrapbookPhotoOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString, int paramInt, boolean paramBoolean)
  {
    super(paramContext, paramEsAccount, "setscrapbookcoverphoto", SetScrapbookCoverPhotoRequestJson.getInstance(), SetScrapbookCoverPhotoResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mPhotoId = paramString;
    this.mTopOffset = paramInt;
    this.mIsGalleryPhoto = paramBoolean;
  }

  public static final class SetScrapbookPhotoException extends ProtocolException
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.SetScrapbookPhotoOperation
 * JD-Core Version:    0.6.2
 */