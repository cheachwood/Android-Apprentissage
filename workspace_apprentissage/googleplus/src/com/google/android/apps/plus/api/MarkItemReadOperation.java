package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.MarkItemReadRequest;
import com.google.api.services.plusi.model.MarkItemReadRequestJson;
import com.google.api.services.plusi.model.MarkItemReadResponse;
import com.google.api.services.plusi.model.MarkItemReadResponseJson;
import java.util.List;

public final class MarkItemReadOperation extends PlusiOperation<MarkItemReadRequest, MarkItemReadResponse>
{
  private final boolean mIsNotificationType;
  private final List<String> mItemIds;

  public MarkItemReadOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, List<String> paramList, boolean paramBoolean)
  {
    super(paramContext, paramEsAccount, "markitemread", MarkItemReadRequestJson.getInstance(), MarkItemReadResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mItemIds = paramList;
    this.mIsNotificationType = paramBoolean;
  }

  public final List<String> getItemIds()
  {
    return this.mItemIds;
  }

  public final boolean isNotificationType()
  {
    return this.mIsNotificationType;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.MarkItemReadOperation
 * JD-Core Version:    0.6.2
 */