package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.PostCommentRequest;
import com.google.api.services.plusi.model.PostCommentRequestJson;
import com.google.api.services.plusi.model.PostCommentResponse;
import com.google.api.services.plusi.model.PostCommentResponseJson;
import java.util.Random;

public final class PostCommentStreamOperation extends PlusiOperation<PostCommentRequest, PostCommentResponse>
{
  private static final Random sRandom = new Random();
  private final String mActivityId;
  private final String mContent;

  public PostCommentStreamOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString1, String paramString2)
  {
    super(paramContext, paramEsAccount, "postcomment", PostCommentRequestJson.getInstance(), PostCommentResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mActivityId = paramString1;
    this.mContent = paramString2;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.PostCommentStreamOperation
 * JD-Core Version:    0.6.2
 */