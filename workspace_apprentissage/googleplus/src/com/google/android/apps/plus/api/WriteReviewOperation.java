package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.GooglePlaceReview;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.WritePlaceReviewRequest;
import com.google.api.services.plusi.model.WritePlaceReviewRequestJson;
import com.google.api.services.plusi.model.WritePlaceReviewResponse;
import com.google.api.services.plusi.model.WritePlaceReviewResponseJson;

public final class WriteReviewOperation extends PlusiOperation<WritePlaceReviewRequest, WritePlaceReviewResponse>
{
  private String cid;
  private GooglePlaceReview review;

  public WriteReviewOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, GooglePlaceReview paramGooglePlaceReview, String paramString)
  {
    super(paramContext, paramEsAccount, "writeplacereview", WritePlaceReviewRequestJson.getInstance(), WritePlaceReviewResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.review = paramGooglePlaceReview;
    this.cid = paramString;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.WriteReviewOperation
 * JD-Core Version:    0.6.2
 */