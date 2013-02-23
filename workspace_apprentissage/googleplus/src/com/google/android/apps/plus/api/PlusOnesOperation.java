package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.json.GenericJson;
import com.google.android.apps.plus.network.ApiaryApiInfo;
import com.google.android.apps.plus.network.ApiaryOperation;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlatformHttpRequestConfiguration;
import com.google.android.apps.plus.util.Property;
import com.google.api.services.pos.model.Plusones;
import com.google.api.services.pos.model.PlusonesJson;
import org.apache.http.HttpEntity;

public final class PlusOnesOperation extends ApiaryOperation<GenericJson, Plusones>
{
  private Plusones mPlusones;

  public PlusOnesOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, ApiaryApiInfo paramApiaryApiInfo, String paramString)
  {
    super(paramContext, paramEsAccount, str, null, PlusonesJson.getInstance(), null, null, new PlatformHttpRequestConfiguration(paramContext, paramEsAccount, "Manage your +1's", Property.POS_BACKEND_URL.get(), paramApiaryApiInfo), "GET");
  }

  protected final HttpEntity createPostData()
  {
    return null;
  }

  public final Plusones getPlusones()
  {
    return this.mPlusones;
  }

  protected final void populateRequest(GenericJson paramGenericJson)
  {
    throw new UnsupportedOperationException();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.PlusOnesOperation
 * JD-Core Version:    0.6.2
 */