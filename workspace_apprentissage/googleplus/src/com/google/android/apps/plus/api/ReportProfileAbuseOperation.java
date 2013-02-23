package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.ReportProfileRequest;
import com.google.api.services.plusi.model.ReportProfileRequestJson;
import com.google.api.services.plusi.model.ReportProfileResponse;
import com.google.api.services.plusi.model.ReportProfileResponseJson;

public final class ReportProfileAbuseOperation extends PlusiOperation<ReportProfileRequest, ReportProfileResponse>
{
  private final String mAbuseType;
  private final String mGaiaId;

  public ReportProfileAbuseOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "reportprofile", ReportProfileRequestJson.getInstance(), ReportProfileResponseJson.getInstance(), null, null);
    this.mGaiaId = paramString1;
    this.mAbuseType = paramString2;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.ReportProfileAbuseOperation
 * JD-Core Version:    0.6.2
 */