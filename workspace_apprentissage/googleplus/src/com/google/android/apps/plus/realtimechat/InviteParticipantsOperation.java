package com.google.android.apps.plus.realtimechat;

import android.content.Context;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsConversationsData;
import java.util.List;

public final class InviteParticipantsOperation extends RealTimeChatOperation
{
  AudienceData mAudience;
  long mConversationRowId;

  public InviteParticipantsOperation(Context paramContext, EsAccount paramEsAccount, long paramLong, AudienceData paramAudienceData)
  {
    super(paramContext, paramEsAccount);
    this.mConversationRowId = paramLong;
    this.mAudience = paramAudienceData;
  }

  public final void execute()
  {
    List localList = ParticipantUtils.getParticipantListFromAudience(this.mContext, this.mAccount, this.mAudience);
    EsConversationsData.inviteParticipantsLocally(this.mContext, this.mAccount, this.mConversationRowId, localList, this.mOperationState);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.InviteParticipantsOperation
 * JD-Core Version:    0.6.2
 */