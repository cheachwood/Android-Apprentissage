package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.ApiaryActivity;
import com.google.android.apps.plus.network.ApiaryApiInfo;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlatformHttpRequestConfiguration;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.android.apps.plus.util.Property;
import com.google.api.services.plusi.model.LinkPreviewRequest;
import com.google.api.services.plusi.model.LinkPreviewRequestJson;
import com.google.api.services.plusi.model.LinkPreviewResponse;
import com.google.api.services.plusi.model.LinkPreviewResponseJson;

public final class LinkPreviewOperation extends PlusiOperation<LinkPreviewRequest, LinkPreviewResponse>
{
  private ApiaryActivity mActivity;
  private final CallToActionData mCallToAction;
  private final String mSourceUrl;

  public LinkPreviewOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString, CallToActionData paramCallToActionData, ApiaryApiInfo paramApiaryApiInfo)
  {
    super(paramContext, paramEsAccount, "linkpreview", LinkPreviewRequestJson.getInstance(), LinkPreviewResponseJson.getInstance(), null, null, new PlatformHttpRequestConfiguration(paramContext, paramEsAccount, "oauth2:https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/plus.stream.read https://www.googleapis.com/auth/plus.stream.write https://www.googleapis.com/auth/plus.circles.write https://www.googleapis.com/auth/plus.circles.read https://www.googleapis.com/auth/plus.photos.readwrite https://www.googleapis.com/auth/plus.native", Property.PLUS_BACKEND_URL.get(), paramApiaryApiInfo));
    this.mSourceUrl = paramString;
    this.mCallToAction = paramCallToActionData;
  }

  public final ApiaryActivity getActivity()
  {
    return this.mActivity;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.LinkPreviewOperation
 * JD-Core Version:    0.6.2
 */