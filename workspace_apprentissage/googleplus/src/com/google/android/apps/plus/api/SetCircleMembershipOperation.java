package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.android.apps.plus.service.CircleMembershipManager;
import com.google.api.services.plusi.model.ModifyMembershipsRequest;
import com.google.api.services.plusi.model.ModifyMembershipsRequestJson;
import com.google.api.services.plusi.model.ModifyMembershipsResponse;
import com.google.api.services.plusi.model.ModifyMembershipsResponseJson;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.Header;

public final class SetCircleMembershipOperation extends PlusiOperation<ModifyMembershipsRequest, ModifyMembershipsResponse>
{
  private final String[] mCirclesToAdd;
  private final String[] mCirclesToRemove;
  private final boolean mFireAndForget;
  private final String mPersonId;
  private final String mPersonName;
  private final boolean mUpdateNotification;

  public SetCircleMembershipOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean1, boolean paramBoolean2, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "modifymemberships", ModifyMembershipsRequestJson.getInstance(), ModifyMembershipsResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mPersonId = paramString1;
    this.mPersonName = paramString2;
    this.mCirclesToAdd = paramArrayOfString1;
    this.mCirclesToRemove = paramArrayOfString2;
    this.mFireAndForget = paramBoolean1;
    this.mUpdateNotification = paramBoolean2;
  }

  public final void onHttpReadErrorFromStream(InputStream paramInputStream, String paramString, int paramInt1, Header[] paramArrayOfHeader, int paramInt2)
    throws IOException
  {
    CircleMembershipManager.setCircleMembershipResult(this.mContext, this.mAccount, this.mPersonId, this.mPersonName, false);
    super.onHttpReadErrorFromStream(paramInputStream, paramString, paramInt1, paramArrayOfHeader, paramInt2);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.SetCircleMembershipOperation
 * JD-Core Version:    0.6.2
 */