package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.PhotosCreateCommentRequest;
import com.google.api.services.plusi.model.PhotosCreateCommentRequestJson;
import com.google.api.services.plusi.model.PhotosCreateCommentResponse;
import com.google.api.services.plusi.model.PhotosCreateCommentResponseJson;

public final class PhotosCreateCommentOperation extends PlusiOperation<PhotosCreateCommentRequest, PhotosCreateCommentResponse>
{
  private final String mAuthkey;
  private final String mComment;
  private final String mOwnerId;
  private final long mPhotoId;

  public PhotosCreateCommentOperation(Context paramContext, EsAccount paramEsAccount, long paramLong, String paramString1, String paramString2, String paramString3, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "photoscreatecomment", PhotosCreateCommentRequestJson.getInstance(), PhotosCreateCommentResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mPhotoId = paramLong;
    this.mOwnerId = paramString1;
    this.mComment = paramString2;
    this.mAuthkey = paramString3;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.PhotosCreateCommentOperation
 * JD-Core Version:    0.6.2
 */