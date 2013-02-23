package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.ApiaryApiInfo;
import com.google.android.apps.plus.network.ApiaryOperation;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlatformHttpRequestConfiguration;
import com.google.api.services.pos.model.Plusones;
import com.google.api.services.pos.model.PlusonesJson;
import org.apache.http.HttpEntity;

public final class WritePlusOneOperation extends ApiaryOperation<Plusones, Plusones>
{
  private final String mAbtk;
  private final boolean mAdd;
  private Plusones mPlusones;

  public WritePlusOneOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, ApiaryApiInfo paramApiaryApiInfo, String paramString1, boolean paramBoolean, String paramString2)
  {
  }

  protected final HttpEntity createPostData()
  {
    if (!this.mAdd);
    for (HttpEntity localHttpEntity = null; ; localHttpEntity = super.createPostData())
      return localHttpEntity;
  }

  public final Plusones getPlusones()
  {
    if (this.mPlusones == null)
      this.mPlusones = new Plusones();
    return this.mPlusones;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.WritePlusOneOperation
 * JD-Core Version:    0.6.2
 */