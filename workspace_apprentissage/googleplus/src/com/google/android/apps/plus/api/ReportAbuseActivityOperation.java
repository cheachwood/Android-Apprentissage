package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.ReportAbuseActivityRequest;
import com.google.api.services.plusi.model.ReportAbuseActivityRequestJson;
import com.google.api.services.plusi.model.ReportAbuseActivityResponse;
import com.google.api.services.plusi.model.ReportAbuseActivityResponseJson;

public final class ReportAbuseActivityOperation extends PlusiOperation<ReportAbuseActivityRequest, ReportAbuseActivityResponse>
{
  private final String mAbuseType;
  private final String mActivityId;
  private final String mCommentId;
  private final boolean mDeleteComment;
  private final boolean mIsUndo;
  private final String mSourceStreamId;

  public ReportAbuseActivityOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString1, String paramString2, String paramString3)
  {
    this(paramContext, paramEsAccount, paramIntent, paramOperationListener, paramString1, paramString2, null, false, paramString3, false);
  }

  private ReportAbuseActivityOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString1, String paramString2, String paramString3, boolean paramBoolean1, String paramString4, boolean paramBoolean2)
  {
    super(paramContext, paramEsAccount, "reportabuseactivity", ReportAbuseActivityRequestJson.getInstance(), ReportAbuseActivityResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mActivityId = paramString1;
    this.mSourceStreamId = paramString2;
    this.mCommentId = paramString3;
    this.mAbuseType = paramString4;
    this.mDeleteComment = paramBoolean1;
    this.mIsUndo = paramBoolean2;
  }

  public ReportAbuseActivityOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this(paramContext, paramEsAccount, paramIntent, paramOperationListener, paramString1, null, paramString2, paramBoolean1, "SPAM", paramBoolean2);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.ReportAbuseActivityOperation
 * JD-Core Version:    0.6.2
 */