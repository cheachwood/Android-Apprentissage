package com.google.android.apps.plus.content;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.google.android.apps.plus.util.EsLog;
import com.google.wireless.realtimechat.proto.Client.Suggestion;
import com.google.wireless.realtimechat.proto.Client.SuggestionsResponse;
import com.google.wireless.realtimechat.proto.Data.Participant;
import java.util.Iterator;
import java.util.List;

public final class EsAudienceData
{
  public static void processSuggestionsResponse(Context paramContext, EsAccount paramEsAccount, Client.SuggestionsResponse paramSuggestionsResponse)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "processSuggestionsResponse");
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    while (true)
    {
      int j;
      try
      {
        StringBuilder localStringBuilder = new StringBuilder();
        ContentValues localContentValues = new ContentValues();
        i = 0;
        Iterator localIterator1 = paramSuggestionsResponse.getSuggestionList().iterator();
        if (localIterator1.hasNext())
        {
          Iterator localIterator2 = ((Client.Suggestion)localIterator1.next()).getSuggestedUserList().iterator();
          j = i;
          if (localIterator2.hasNext())
          {
            Data.Participant localParticipant = (Data.Participant)localIterator2.next();
            if (j > 0)
              localStringBuilder.append(',');
            localStringBuilder.append("'").append(localParticipant.getParticipantId()).append("'");
            localContentValues.clear();
            localContentValues.put("full_name", localParticipant.getFullName());
            localContentValues.put("first_name", localParticipant.getFirstName());
            localContentValues.put("participant_id", localParticipant.getParticipantId());
            int k = j + 1;
            localContentValues.put("sequence", Integer.valueOf(j));
            localSQLiteDatabase.insertWithOnConflict("hangout_suggestions", null, localContentValues, 5);
            j = k;
            continue;
          }
        }
        else
        {
          localSQLiteDatabase.delete("hangout_suggestions", "participant_id NOT IN (" + localStringBuilder.toString() + ")", null);
          localSQLiteDatabase.setTransactionSuccessful();
          return;
        }
      }
      finally
      {
        localSQLiteDatabase.endTransaction();
      }
      int i = j;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EsAudienceData
 * JD-Core Version:    0.6.2
 */