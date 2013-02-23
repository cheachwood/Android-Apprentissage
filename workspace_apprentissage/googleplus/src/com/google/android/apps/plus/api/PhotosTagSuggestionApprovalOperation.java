package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.PhotosNameTagSuggestionApprovalRequest;
import com.google.api.services.plusi.model.PhotosNameTagSuggestionApprovalRequestJson;
import com.google.api.services.plusi.model.PhotosNameTagSuggestionApprovalResponse;
import com.google.api.services.plusi.model.PhotosNameTagSuggestionApprovalResponseJson;

public final class PhotosTagSuggestionApprovalOperation extends PlusiOperation<PhotosNameTagSuggestionApprovalRequest, PhotosNameTagSuggestionApprovalResponse>
{
  private final boolean mApprove;
  private final String mOwnerId;
  private final String mPhotoId;
  private final String mShapeId;
  private final String mTaggeeId;

  public PhotosTagSuggestionApprovalOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, boolean paramBoolean, String paramString2, String paramString3, String paramString4, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "photosnametagsuggestionapproval", PhotosNameTagSuggestionApprovalRequestJson.getInstance(), PhotosNameTagSuggestionApprovalResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mOwnerId = paramString1;
    this.mApprove = paramBoolean;
    this.mPhotoId = paramString2;
    this.mShapeId = paramString3;
    this.mTaggeeId = paramString4;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.PhotosTagSuggestionApprovalOperation
 * JD-Core Version:    0.6.2
 */