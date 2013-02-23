package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.MuteActivityRequest;
import com.google.api.services.plusi.model.MuteActivityRequestJson;
import com.google.api.services.plusi.model.MuteActivityResponse;
import com.google.api.services.plusi.model.MuteActivityResponseJson;

public final class MuteActivityOperation extends PlusiOperation<MuteActivityRequest, MuteActivityResponse>
{
  private final String mActivityId;
  private final boolean mIsMuted;

  public MuteActivityOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString, boolean paramBoolean)
  {
    super(paramContext, paramEsAccount, "muteactivity", MuteActivityRequestJson.getInstance(), MuteActivityResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mActivityId = paramString;
    this.mIsMuted = paramBoolean;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.MuteActivityOperation
 * JD-Core Version:    0.6.2
 */