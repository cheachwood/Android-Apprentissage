package com.google.android.apps.plus.realtimechat;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsConversationsData;

public final class CheckMessageSentOperation extends RealTimeChatOperation
{
  private static final Handler sHandler = new Handler(Looper.getMainLooper());
  final int mFlags;
  final long mMessageRowId;
  boolean mSendFailed;

  public CheckMessageSentOperation(Context paramContext, EsAccount paramEsAccount, long paramLong, int paramInt)
  {
    super(paramContext, paramEsAccount);
    this.mMessageRowId = paramLong;
    this.mSendFailed = false;
    this.mFlags = paramInt;
  }

  private static void recordSystemEvent(Context paramContext, final EsAccount paramEsAccount, final OzActions paramOzActions)
  {
    sHandler.post(new Runnable()
    {
      public final void run()
      {
        OzViews localOzViews = OzViews.getViewForLogging(this.val$context);
        EsAnalytics.recordActionEvent(this.val$context, paramEsAccount, paramOzActions, localOzViews);
      }
    });
  }

  public final void execute()
  {
    int i = EsConversationsData.checkMessageSentLocally(this.mContext, this.mAccount, this.mMessageRowId, this.mOperationState);
    if (i == 7)
      sHandler.postDelayed(new Runnable()
      {
        public final void run()
        {
          RealTimeChatService.checkMessageSent(CheckMessageSentOperation.this.mContext, CheckMessageSentOperation.this.mAccount, CheckMessageSentOperation.this.mMessageRowId, 1);
        }
      }
      , 20000L);
    while (true)
    {
      return;
      if (i == 8)
      {
        this.mSendFailed = true;
        switch (this.mFlags)
        {
        default:
          break;
        case 1:
          recordSystemEvent(this.mContext, this.mAccount, OzActions.CONVERSATION_AUTO_RETRY_FAIL);
          break;
        case 2:
          recordSystemEvent(this.mContext, this.mAccount, OzActions.CONVERSATION_MANUAL_RETRY_FAIL);
          break;
        }
      }
      else
      {
        switch (this.mFlags)
        {
        default:
          break;
        case 1:
          recordSystemEvent(this.mContext, this.mAccount, OzActions.CONVERSATION_AUTO_RETRY_SUCCESS);
          break;
        case 2:
          recordSystemEvent(this.mContext, this.mAccount, OzActions.CONVERSATION_MANUAL_RETRY_SUCCESS);
        }
      }
    }
  }

  public final Object getResultValue()
  {
    return Boolean.valueOf(this.mSendFailed);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.CheckMessageSentOperation
 * JD-Core Version:    0.6.2
 */