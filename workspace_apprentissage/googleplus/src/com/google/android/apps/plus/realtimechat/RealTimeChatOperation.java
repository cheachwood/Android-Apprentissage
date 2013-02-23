package com.google.android.apps.plus.realtimechat;

import android.content.Context;
import com.google.android.apps.plus.content.EsAccount;
import com.google.wireless.realtimechat.proto.Client.BunchClientRequest;
import java.util.Collection;

public abstract class RealTimeChatOperation
{
  protected final EsAccount mAccount;
  protected final Context mContext;
  protected final RealTimeChatOperationState mOperationState;

  public RealTimeChatOperation(Context paramContext, EsAccount paramEsAccount)
  {
    this.mContext = paramContext;
    this.mAccount = paramEsAccount;
    this.mOperationState = new RealTimeChatOperationState(null);
  }

  public void execute()
  {
  }

  public final Collection<Client.BunchClientRequest> getResponses()
  {
    return this.mOperationState.getRequests();
  }

  public int getResultCode()
  {
    return 1;
  }

  public Object getResultValue()
  {
    return null;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.RealTimeChatOperation
 * JD-Core Version:    0.6.2
 */