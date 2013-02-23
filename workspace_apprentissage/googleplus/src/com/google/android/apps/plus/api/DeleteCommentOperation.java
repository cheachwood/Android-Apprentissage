package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.DeleteCommentRequest;
import com.google.api.services.plusi.model.DeleteCommentRequestJson;
import com.google.api.services.plusi.model.DeleteCommentResponse;
import com.google.api.services.plusi.model.DeleteCommentResponseJson;

public final class DeleteCommentOperation extends PlusiOperation<DeleteCommentRequest, DeleteCommentResponse>
{
  private final String mCommentId;

  public DeleteCommentOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString)
  {
    super(paramContext, paramEsAccount, "deletecomment", DeleteCommentRequestJson.getInstance(), DeleteCommentResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mCommentId = paramString;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.DeleteCommentOperation
 * JD-Core Version:    0.6.2
 */