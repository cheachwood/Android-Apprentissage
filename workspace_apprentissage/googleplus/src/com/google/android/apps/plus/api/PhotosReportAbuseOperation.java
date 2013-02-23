package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.ReportAbusePhotoRequest;
import com.google.api.services.plusi.model.ReportAbusePhotoRequestJson;
import com.google.api.services.plusi.model.ReportAbusePhotoResponse;
import com.google.api.services.plusi.model.ReportAbusePhotoResponseJson;

public final class PhotosReportAbuseOperation extends PlusiOperation<ReportAbusePhotoRequest, ReportAbusePhotoResponse>
{
  private final String mOwnerId;
  private final long mPhotoId;

  public PhotosReportAbuseOperation(Context paramContext, EsAccount paramEsAccount, long paramLong, String paramString, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "reportabusephoto", ReportAbusePhotoRequestJson.getInstance(), ReportAbusePhotoResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mPhotoId = paramLong;
    this.mOwnerId = paramString;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.PhotosReportAbuseOperation
 * JD-Core Version:    0.6.2
 */