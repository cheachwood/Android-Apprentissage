package com.google.android.apps.plus.phone;

import android.app.Activity;
import android.content.Intent;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.hangout.HangoutTile;
import com.google.wireless.realtimechat.proto.Data.Participant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class ParticipantHelper
{
  public static void inviteMoreParticipants(Activity paramActivity, Collection<Data.Participant> paramCollection, boolean paramBoolean1, EsAccount paramEsAccount, boolean paramBoolean2)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramCollection.iterator();
    if (localIterator.hasNext())
    {
      Data.Participant localParticipant = (Data.Participant)localIterator.next();
      String str1 = localParticipant.getParticipantId();
      String str2 = null;
      String str3;
      if (str1.startsWith("g:"))
        str3 = EsPeopleData.extractGaiaId(str1);
      while (true)
      {
        localArrayList.add(new PersonData(str3, localParticipant.getFullName(), str2));
        break;
        if (str1.startsWith("e:"))
        {
          str2 = str1.substring(2);
          str3 = null;
        }
        else
        {
          boolean bool = str1.startsWith("p:");
          str2 = null;
          str3 = null;
          if (bool)
          {
            str2 = str1;
            str3 = null;
          }
        }
      }
    }
    AudienceData localAudienceData = new AudienceData(localArrayList, null);
    if (paramBoolean1)
      paramActivity.startActivityForResult(Intents.getEditAudienceActivityIntent(paramActivity, paramEsAccount, paramActivity.getString(R.string.realtimechat_edit_audience_activity_title), localAudienceData, 6, true, true, true, false), 1);
    while (true)
    {
      return;
      Intent localIntent = Intents.getNewConversationActivityIntent(paramActivity, paramEsAccount, localAudienceData);
      if (paramBoolean2)
        localIntent.putExtra("tile", HangoutTile.class.getName());
      paramActivity.startActivity(localIntent);
      paramActivity.finish();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.ParticipantHelper
 * JD-Core Version:    0.6.2
 */