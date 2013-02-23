package com.google.android.apps.plus.realtimechat;

import android.content.Context;
import android.util.Log;
import com.google.android.apps.plus.service.ServiceResult;
import com.google.android.apps.plus.util.EsLog;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class BlockingC2DMClient
{
  private final CountDownLatch mEvent = new CountDownLatch(1);
  private final RealTimeChatServiceListener mRealTimeChatListener = new OnC2dmReceivedListener((byte)0);
  private String mRegistrationToken;
  private ServiceResult mServiceResult;
  private final long mTimeoutMilliseconds = 30000L;
  private boolean mUsed;

  public BlockingC2DMClient(long paramLong)
  {
  }

  public final void blockingGetC2dmToken(Context paramContext)
  {
    if (this.mUsed)
      throw new IllegalStateException("This class is single-use.");
    this.mUsed = true;
    RealTimeChatService.registerListener(this.mRealTimeChatListener);
    try
    {
      this.mRegistrationToken = RealTimeChatService.getOrRequestC2dmId(paramContext);
      if (this.mRegistrationToken != null)
        this.mServiceResult = new ServiceResult();
      while (true)
      {
        return;
        try
        {
          if (!this.mEvent.await(this.mTimeoutMilliseconds, TimeUnit.MILLISECONDS))
          {
            if (EsLog.isLoggable("BlockingC2DMClient", 6))
              Log.e("BlockingC2DMClient", "Waiting for C2DM registration timed out.");
            this.mServiceResult = new ServiceResult(-2, "Waiting for C2DM registration timed out.", null);
          }
          if (this.mServiceResult == null)
          {
            if (EsLog.isLoggable("BlockingC2DMClient", 5))
              Log.w("BlockingC2DMClient", "Result was not set by service.");
            this.mServiceResult = new ServiceResult(0, "Result was not set by service.", null);
          }
        }
        catch (InterruptedException localInterruptedException)
        {
          if (EsLog.isLoggable("BlockingC2DMClient", 6))
            Log.e("BlockingC2DMClient", "Waiting for C2DM registration interrupted.", localInterruptedException);
          this.mServiceResult = new ServiceResult(-1, "Waiting for C2DM registration interrupted.", localInterruptedException);
        }
      }
    }
    finally
    {
      RealTimeChatService.unregisterListener(this.mRealTimeChatListener);
    }
  }

  public final boolean hasError()
  {
    if ((this.mRegistrationToken == null) || (this.mServiceResult == null) || (this.mServiceResult.hasError()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private final class OnC2dmReceivedListener extends RealTimeChatServiceListener
  {
    private OnC2dmReceivedListener()
    {
    }

    final void onC2dmRegistration(ServiceResult paramServiceResult, String paramString)
    {
      BlockingC2DMClient.access$102(BlockingC2DMClient.this, paramServiceResult);
      BlockingC2DMClient.access$202(BlockingC2DMClient.this, paramString);
      BlockingC2DMClient.this.mEvent.countDown();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.BlockingC2DMClient
 * JD-Core Version:    0.6.2
 */