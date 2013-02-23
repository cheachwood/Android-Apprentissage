package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.PeopleData;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.BlockUserRequest;
import com.google.api.services.plusi.model.BlockUserRequestJson;
import com.google.api.services.plusi.model.BlockUserResponse;
import com.google.api.services.plusi.model.BlockUserResponseJson;

public final class BlockUserOperation extends PlusiOperation<BlockUserRequest, BlockUserResponse>
{
  private static Factory sFactory = new Factory((byte)0);
  private final PeopleData mDb;
  private boolean mIsBlocked;
  private String mName;
  private String mPersonId;

  private BlockUserOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, PeopleData paramPeopleData)
  {
    super(paramContext, paramEsAccount, "blockuser", BlockUserRequestJson.getInstance(), BlockUserResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mDb = paramPeopleData;
  }

  public static Factory getFactory()
  {
    return sFactory;
  }

  public final void startThreaded(String paramString1, String paramString2, boolean paramBoolean)
  {
    this.mPersonId = paramString1;
    this.mName = paramString2;
    this.mIsBlocked = paramBoolean;
    startThreaded();
  }

  public static final class Factory
  {
    public static BlockUserOperation build(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, PeopleData paramPeopleData)
    {
      return new BlockUserOperation(paramContext, paramEsAccount, paramIntent, paramOperationListener, paramPeopleData, (byte)0);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.BlockUserOperation
 * JD-Core Version:    0.6.2
 */