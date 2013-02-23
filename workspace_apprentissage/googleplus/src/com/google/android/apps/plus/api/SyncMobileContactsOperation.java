package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.MobileContact;
import com.google.api.services.plusi.model.SyncMobileContactsRequest;
import com.google.api.services.plusi.model.SyncMobileContactsRequestJson;
import com.google.api.services.plusi.model.SyncMobileContactsResponse;
import com.google.api.services.plusi.model.SyncMobileContactsResponseJson;
import java.util.List;

public final class SyncMobileContactsOperation extends PlusiOperation<SyncMobileContactsRequest, SyncMobileContactsResponse>
{
  private final List<MobileContact> mContacts;
  private final String mDevice;
  private boolean mSuccess = false;
  private String mSyncType = "FULL";

  public SyncMobileContactsOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, List<MobileContact> paramList, String paramString2, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "syncmobilecontacts", SyncMobileContactsRequestJson.getInstance(), SyncMobileContactsResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mDevice = paramString1;
    this.mContacts = paramList;
    this.mSyncType = paramString2;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.SyncMobileContactsOperation
 * JD-Core Version:    0.6.2
 */