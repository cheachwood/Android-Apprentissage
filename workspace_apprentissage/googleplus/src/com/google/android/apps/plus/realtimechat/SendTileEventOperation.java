package com.google.android.apps.plus.realtimechat;

import android.content.Context;
import com.google.android.apps.plus.content.EsAccount;
import java.util.HashMap;

public final class SendTileEventOperation extends RealTimeChatOperation
{
  private final String mConversationId;
  private final HashMap<String, String> mTileEventData;
  private final String mTileEventType;
  private final String mTileType;
  private final int mTileVersion;

  public SendTileEventOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, int paramInt, String paramString3, HashMap<String, String> paramHashMap)
  {
    super(paramContext, paramEsAccount);
    this.mConversationId = paramString1;
    this.mTileType = paramString2;
    this.mTileVersion = paramInt;
    this.mTileEventType = paramString3;
    this.mTileEventData = paramHashMap;
  }

  public final void execute()
  {
    this.mOperationState.addRequest(BunchCommands.tileEventRequest(this.mConversationId, this.mTileType, this.mTileVersion, this.mTileEventType, this.mTileEventData));
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.SendTileEventOperation
 * JD-Core Version:    0.6.2
 */