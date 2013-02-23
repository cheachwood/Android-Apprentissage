package com.google.android.apps.plus.realtimechat;

import com.google.wireless.realtimechat.proto.Client.BunchClientRequest;
import java.util.LinkedList;
import java.util.List;

public final class RealTimeChatOperationState
{
  private int mClientVersion;
  private boolean mClientVersionChanged;
  private final Long mCurrentConversationRowId;
  private final List<Client.BunchClientRequest> mRequests;
  private boolean mShouldTriggerNotification;

  public RealTimeChatOperationState(Long paramLong)
  {
    this.mCurrentConversationRowId = paramLong;
    this.mRequests = new LinkedList();
    this.mShouldTriggerNotification = false;
    this.mClientVersionChanged = false;
    this.mClientVersion = 0;
  }

  public final void addRequest(Client.BunchClientRequest paramBunchClientRequest)
  {
    this.mRequests.add(paramBunchClientRequest);
  }

  public final boolean getClientVersionChanged()
  {
    return this.mClientVersionChanged;
  }

  public final Long getCurrentConversationRowId()
  {
    return this.mCurrentConversationRowId;
  }

  public final List<Client.BunchClientRequest> getRequests()
  {
    return this.mRequests;
  }

  public final void setClientVersion(int paramInt)
  {
    this.mClientVersion = paramInt;
    this.mClientVersionChanged = true;
  }

  public final void setShouldTriggerNotifications()
  {
    this.mShouldTriggerNotification = true;
  }

  public final boolean shouldTriggerNotifications()
  {
    return this.mShouldTriggerNotification;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.RealTimeChatOperationState
 * JD-Core Version:    0.6.2
 */