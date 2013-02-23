package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.EditCommentRequest;
import com.google.api.services.plusi.model.EditCommentRequestJson;
import com.google.api.services.plusi.model.EditCommentResponse;
import com.google.api.services.plusi.model.EditCommentResponseJson;

public final class EditCommentStreamOperation extends PlusiOperation<EditCommentRequest, EditCommentResponse>
{
  private final String mActivityId;
  private final String mCommentId;
  private final String mContent;

  public EditCommentStreamOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString1, String paramString2, String paramString3)
  {
    super(paramContext, paramEsAccount, "editcomment", EditCommentRequestJson.getInstance(), EditCommentResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mActivityId = paramString1;
    this.mCommentId = paramString2;
    this.mContent = paramString3;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.EditCommentStreamOperation
 * JD-Core Version:    0.6.2
 */