package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.PeopleData;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.MuteUserRequest;
import com.google.api.services.plusi.model.MuteUserRequestJson;
import com.google.api.services.plusi.model.MuteUserResponse;
import com.google.api.services.plusi.model.MuteUserResponseJson;

public final class MuteUserOperation extends PlusiOperation<MuteUserRequest, MuteUserResponse>
{
  private static Factory sFactory = new Factory((byte)0);
  private final PeopleData mDb;
  private String mGaiaId;
  private boolean mIsMute;

  private MuteUserOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, PeopleData paramPeopleData)
  {
    super(paramContext, paramEsAccount, "muteuser", MuteUserRequestJson.getInstance(), MuteUserResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mDb = paramPeopleData;
  }

  public static Factory getFactory()
  {
    return sFactory;
  }

  public final void startThreaded(String paramString, boolean paramBoolean)
  {
    this.mGaiaId = paramString;
    this.mIsMute = paramBoolean;
    startThreaded();
  }

  public static final class Factory
  {
    public static MuteUserOperation build(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, PeopleData paramPeopleData)
    {
      return new MuteUserOperation(paramContext, paramEsAccount, paramIntent, paramOperationListener, paramPeopleData, (byte)0);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.MuteUserOperation
 * JD-Core Version:    0.6.2
 */