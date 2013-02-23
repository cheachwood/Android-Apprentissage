package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.EditActivityRequest;
import com.google.api.services.plusi.model.EditActivityRequestJson;
import com.google.api.services.plusi.model.EditActivityResponse;
import com.google.api.services.plusi.model.EditActivityResponseJson;

public final class EditActivityOperation extends PlusiOperation<EditActivityRequest, EditActivityResponse>
{
  private final String mActivityId;
  private final String mContent;
  private final boolean mReshare;

  public EditActivityOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString1, String paramString2, boolean paramBoolean)
  {
    super(paramContext, paramEsAccount, "editactivity", EditActivityRequestJson.getInstance(), EditActivityResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mActivityId = paramString1;
    this.mContent = paramString2;
    this.mReshare = paramBoolean;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.EditActivityOperation
 * JD-Core Version:    0.6.2
 */