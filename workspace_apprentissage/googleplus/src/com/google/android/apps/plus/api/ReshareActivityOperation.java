package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.PostActivityRequest;
import com.google.api.services.plusi.model.PostActivityRequestJson;
import com.google.api.services.plusi.model.PostActivityResponse;
import com.google.api.services.plusi.model.PostActivityResponseJson;

public final class ReshareActivityOperation extends PlusiOperation<PostActivityRequest, PostActivityResponse>
{
  private final AudienceData mAudience;
  private final String mContent;
  private final String mReshareId;

  public ReshareActivityOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString1, String paramString2, AudienceData paramAudienceData)
  {
    super(paramContext, paramEsAccount, "postactivity", PostActivityRequestJson.getInstance(), PostActivityResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mReshareId = paramString1;
    this.mContent = paramString2;
    this.mAudience = paramAudienceData;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.ReshareActivityOperation
 * JD-Core Version:    0.6.2
 */