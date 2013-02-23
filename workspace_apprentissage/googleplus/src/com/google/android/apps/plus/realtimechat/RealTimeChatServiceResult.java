package com.google.android.apps.plus.realtimechat;

import com.google.wireless.realtimechat.proto.Client.BunchServerResponse;

public final class RealTimeChatServiceResult
{
  private final Client.BunchServerResponse mCommand;
  private final int mErrorCode;
  private int mRequestId;

  RealTimeChatServiceResult()
  {
    this(0, 1, null);
  }

  RealTimeChatServiceResult(int paramInt1, int paramInt2, Client.BunchServerResponse paramBunchServerResponse)
  {
    this.mRequestId = paramInt1;
    this.mErrorCode = paramInt2;
    this.mCommand = paramBunchServerResponse;
  }

  public final Client.BunchServerResponse getCommand()
  {
    return this.mCommand;
  }

  public final int getErrorCode()
  {
    return this.mErrorCode;
  }

  public final int getRequestId()
  {
    return this.mRequestId;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.RealTimeChatServiceResult
 * JD-Core Version:    0.6.2
 */