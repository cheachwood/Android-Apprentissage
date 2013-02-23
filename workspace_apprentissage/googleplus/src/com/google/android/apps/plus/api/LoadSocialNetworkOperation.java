package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.DataPersonList;
import com.google.api.services.plusi.model.DataSystemGroups;
import com.google.api.services.plusi.model.DataViewerCircles;
import com.google.api.services.plusi.model.LoadSocialNetworkRequest;
import com.google.api.services.plusi.model.LoadSocialNetworkRequestJson;
import com.google.api.services.plusi.model.LoadSocialNetworkResponse;
import com.google.api.services.plusi.model.LoadSocialNetworkResponseJson;

public final class LoadSocialNetworkOperation extends PlusiOperation<LoadSocialNetworkRequest, LoadSocialNetworkResponse>
{
  private DataViewerCircles mCircleList;
  private final boolean mLoadCircles;
  private final boolean mLoadPeople;
  private final int mMaxPeople;
  private DataPersonList mPersonList;
  private final String mSyncStateToken;
  private DataSystemGroups mSystemGroups;

  public LoadSocialNetworkOperation(Context paramContext, EsAccount paramEsAccount, boolean paramBoolean1, boolean paramBoolean2, int paramInt, String paramString, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "loadsocialnetwork", LoadSocialNetworkRequestJson.getInstance(), LoadSocialNetworkResponseJson.getInstance(), null, paramOperationListener);
    this.mSyncStateToken = paramString;
    this.mLoadCircles = paramBoolean1;
    this.mLoadPeople = paramBoolean2;
    this.mMaxPeople = paramInt;
  }

  public final DataViewerCircles getCircleList()
  {
    return this.mCircleList;
  }

  public final DataPersonList getPersonList()
  {
    return this.mPersonList;
  }

  public final DataSystemGroups getSystemGroups()
  {
    return this.mSystemGroups;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.LoadSocialNetworkOperation
 * JD-Core Version:    0.6.2
 */