package com.google.android.apps.plus.content;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.realtimechat.BunchCommands;
import com.google.android.apps.plus.realtimechat.RealTimeChatNotifications;
import com.google.android.apps.plus.realtimechat.RealTimeChatOperationState;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.StringUtils;
import com.google.wireless.realtimechat.proto.Client.BunchClientRequest;
import com.google.wireless.realtimechat.proto.Client.ChatMessage;
import com.google.wireless.realtimechat.proto.Client.ChatMessage.ReceiverState;
import com.google.wireless.realtimechat.proto.Client.ChatMessageResponse;
import com.google.wireless.realtimechat.proto.Client.ClientConversation;
import com.google.wireless.realtimechat.proto.Client.ConversationPreferenceResponse;
import com.google.wireless.realtimechat.proto.Client.EventStreamResponse;
import com.google.wireless.realtimechat.proto.Client.EventStreamResponse.Event;
import com.google.wireless.realtimechat.proto.Client.EventStreamResponse.ReceiverState;
import com.google.wireless.realtimechat.proto.Client.GroupConversationRename;
import com.google.wireless.realtimechat.proto.Client.InviteRequest;
import com.google.wireless.realtimechat.proto.Client.InviteResponse;
import com.google.wireless.realtimechat.proto.Client.LeaveConversationRequest;
import com.google.wireless.realtimechat.proto.Client.MembershipChange;
import com.google.wireless.realtimechat.proto.Client.MembershipChange.Type;
import com.google.wireless.realtimechat.proto.Client.NewConversationResponse;
import com.google.wireless.realtimechat.proto.Client.ParticipantError;
import com.google.wireless.realtimechat.proto.Client.Receipt;
import com.google.wireless.realtimechat.proto.Client.Receipt.Type;
import com.google.wireless.realtimechat.proto.Client.Suggestion;
import com.google.wireless.realtimechat.proto.Client.SuggestionsRequest;
import com.google.wireless.realtimechat.proto.Client.SuggestionsRequest.SuggestionsType;
import com.google.wireless.realtimechat.proto.Client.SuggestionsResponse;
import com.google.wireless.realtimechat.proto.Client.TileEvent;
import com.google.wireless.realtimechat.proto.Client.Typing.Type;
import com.google.wireless.realtimechat.proto.Client.UserCreationResponse;
import com.google.wireless.realtimechat.proto.Data.Content;
import com.google.wireless.realtimechat.proto.Data.ConversationType;
import com.google.wireless.realtimechat.proto.Data.KeyValue;
import com.google.wireless.realtimechat.proto.Data.Participant;
import com.google.wireless.realtimechat.proto.Data.Participant.Type;
import com.google.wireless.realtimechat.proto.Data.PhotoMetadata;
import com.google.wireless.realtimechat.proto.Data.ResponseStatus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class EsConversationsData
{
  private static final Comparator<Client.ClientConversation> sConversationComparator = new Comparator()
  {
  };
  private static final Handler sHandler = new Handler(Looper.getMainLooper());

  public static void acceptConversationLocally(Context paramContext, EsAccount paramEsAccount, long paramLong, String paramString, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "acceptConversationLocally conversationRowId: " + paramLong);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("is_pending_accept", Integer.valueOf(0));
      String[] arrayOfString = new String[1];
      arrayOfString[0] = String.valueOf(paramLong);
      localSQLiteDatabase.update("conversations", localContentValues, "_id=?", arrayOfString);
      String str = queryConversationId(localSQLiteDatabase, paramLong);
      paramRealTimeChatOperationState.addRequest(BunchCommands.replyToInviteRequest(str, paramString));
      paramRealTimeChatOperationState.addRequest(BunchCommands.getEventStream(str, 0L, 0L, BunchCommands.MAX_EVENTS_PER_REQUEST));
      if (EsLog.isLoggable("EsConversationsData", 3))
        Log.d("EsConversationsData", "requesting 20 events since 0 for " + str);
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      notifyConversationsChanged(paramContext, paramEsAccount);
      return;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  // ERROR //
  public static int checkMessageSentLocally(Context paramContext, EsAccount paramEsAccount, long paramLong, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    // Byte code:
    //   0: ldc 35
    //   2: iconst_3
    //   3: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   6: ifeq +25 -> 31
    //   9: ldc 35
    //   11: new 43	java/lang/StringBuilder
    //   14: dup
    //   15: ldc 148
    //   17: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   20: lload_2
    //   21: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   24: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   27: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   30: pop
    //   31: aload_0
    //   32: aload_1
    //   33: invokestatic 68	com/google/android/apps/plus/content/EsDatabaseHelper:getDatabaseHelper	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)Lcom/google/android/apps/plus/content/EsDatabaseHelper;
    //   36: invokevirtual 72	com/google/android/apps/plus/content/EsDatabaseHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   39: astore 5
    //   41: aload 5
    //   43: invokevirtual 77	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   46: aconst_null
    //   47: astore 6
    //   49: aload 5
    //   51: ldc 150
    //   53: iconst_2
    //   54: anewarray 94	java/lang/String
    //   57: dup
    //   58: iconst_0
    //   59: ldc 152
    //   61: aastore
    //   62: dup
    //   63: iconst_1
    //   64: ldc 154
    //   66: aastore
    //   67: new 43	java/lang/StringBuilder
    //   70: dup
    //   71: ldc 156
    //   73: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   76: lload_2
    //   77: invokestatic 97	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   80: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   83: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   86: aconst_null
    //   87: aconst_null
    //   88: aconst_null
    //   89: aconst_null
    //   90: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   93: astore 6
    //   95: aload 6
    //   97: invokeinterface 166 1 0
    //   102: istore 9
    //   104: aconst_null
    //   105: astore 10
    //   107: iload 9
    //   109: ifeq +25 -> 134
    //   112: aload 6
    //   114: iconst_0
    //   115: invokeinterface 170 2 0
    //   120: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   123: astore 10
    //   125: aload 6
    //   127: iconst_1
    //   128: invokeinterface 179 2 0
    //   133: pop
    //   134: aload 6
    //   136: ifnull +10 -> 146
    //   139: aload 6
    //   141: invokeinterface 182 1 0
    //   146: aload 5
    //   148: lload_2
    //   149: invokestatic 186	com/google/android/apps/plus/content/EsConversationsData:queryMessageStatus	(Landroid/database/sqlite/SQLiteDatabase;J)I
    //   152: istore 11
    //   154: iload 11
    //   156: ifne +100 -> 256
    //   159: aload 5
    //   161: lload_2
    //   162: invokestatic 190	com/google/android/apps/plus/content/EsConversationsData:hasLocalPhotoUri	(Landroid/database/sqlite/SQLiteDatabase;J)Z
    //   165: ifne +36 -> 201
    //   168: ldc 35
    //   170: iconst_3
    //   171: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   174: ifeq +11 -> 185
    //   177: ldc 35
    //   179: ldc 192
    //   181: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   184: pop
    //   185: bipush 7
    //   187: istore 11
    //   189: aload 5
    //   191: aload_1
    //   192: lload_2
    //   193: iconst_1
    //   194: iconst_0
    //   195: aload 4
    //   197: invokestatic 196	com/google/android/apps/plus/content/EsConversationsData:sendMessageInDatabase$728fb81e	(Landroid/database/sqlite/SQLiteDatabase;Lcom/google/android/apps/plus/content/EsAccount;JZZLcom/google/android/apps/plus/realtimechat/RealTimeChatOperationState;)Landroid/os/Bundle;
    //   200: pop
    //   201: aload 5
    //   203: invokevirtual 137	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   206: aload 5
    //   208: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   211: aload 10
    //   213: ifnull +13 -> 226
    //   216: aload_0
    //   217: aload_1
    //   218: aload 10
    //   220: invokevirtual 200	java/lang/Long:longValue	()J
    //   223: invokestatic 204	com/google/android/apps/plus/content/EsConversationsData:notifyMessagesChanged	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;J)V
    //   226: iload 11
    //   228: ireturn
    //   229: astore 7
    //   231: aload 6
    //   233: ifnull +10 -> 243
    //   236: aload 6
    //   238: invokeinterface 182 1 0
    //   243: aload 7
    //   245: athrow
    //   246: astore 8
    //   248: aload 5
    //   250: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   253: aload 8
    //   255: athrow
    //   256: iload 11
    //   258: iconst_1
    //   259: if_icmpne +39 -> 298
    //   262: ldc 35
    //   264: iconst_3
    //   265: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   268: ifeq +11 -> 279
    //   271: ldc 35
    //   273: ldc 206
    //   275: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   278: pop
    //   279: bipush 7
    //   281: istore 11
    //   283: aload 5
    //   285: aload_1
    //   286: lload_2
    //   287: iconst_1
    //   288: iconst_0
    //   289: aload 4
    //   291: invokestatic 196	com/google/android/apps/plus/content/EsConversationsData:sendMessageInDatabase$728fb81e	(Landroid/database/sqlite/SQLiteDatabase;Lcom/google/android/apps/plus/content/EsAccount;JZZLcom/google/android/apps/plus/realtimechat/RealTimeChatOperationState;)Landroid/os/Bundle;
    //   294: pop
    //   295: goto -94 -> 201
    //   298: iload 11
    //   300: bipush 7
    //   302: if_icmpne +35 -> 337
    //   305: ldc 35
    //   307: iconst_3
    //   308: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   311: ifeq +11 -> 322
    //   314: ldc 35
    //   316: ldc 208
    //   318: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   321: pop
    //   322: bipush 8
    //   324: istore 11
    //   326: aload 5
    //   328: lload_2
    //   329: bipush 8
    //   331: invokestatic 212	com/google/android/apps/plus/content/EsConversationsData:updateMessageStatus$4372adf	(Landroid/database/sqlite/SQLiteDatabase;JI)V
    //   334: goto -133 -> 201
    //   337: ldc 35
    //   339: iconst_3
    //   340: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   343: ifeq -142 -> 201
    //   346: ldc 35
    //   348: ldc 214
    //   350: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   353: pop
    //   354: goto -153 -> 201
    //
    // Exception table:
    //   from	to	target	type
    //   49	134	229	finally
    //   139	206	246	finally
    //   236	246	246	finally
    //   262	354	246	finally
  }

  public static void cleanupData$3105fef4(SQLiteDatabase paramSQLiteDatabase)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "cleanupData");
    paramSQLiteDatabase.delete("participants", "(SELECT COUNT(participant_id) FROM conversation_participants WHERE participants.participant_id=conversation_participants.participant_id)=0", null);
  }

  public static void connectionStarted(Context paramContext, EsAccount paramEsAccount)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("status", Integer.valueOf(2));
      localSQLiteDatabase.update("messages", localContentValues, "status=0 OR status=1", null);
      localContentValues.put("status", Integer.valueOf(8));
      localSQLiteDatabase.update("messages", localContentValues, "status=7", null);
      localContentValues.clear();
      localContentValues.put("key", "awaiting_conversation_list");
      localContentValues.put("value", Integer.valueOf(1));
      localSQLiteDatabase.insertWithOnConflict("realtimechat_metadata", null, localContentValues, 5);
      localSQLiteDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  public static final int convertParticipantType(Data.Participant paramParticipant)
  {
    if (paramParticipant.hasType());
    int i;
    switch (4.$SwitchMap$com$google$wireless$realtimechat$proto$Data$Participant$Type[paramParticipant.getType().ordinal()])
    {
    default:
      if (EsLog.isLoggable("EsConversationsData", 5))
        Log.w("EsConversationsData", "Unknown participant type of " + paramParticipant.getType());
      i = 0;
    case 1:
    case 2:
    case 3:
    case 4:
    }
    while (true)
    {
      return i;
      i = 1;
      continue;
      i = 2;
      continue;
      i = 3;
      continue;
      i = 4;
    }
  }

  public static final Data.Participant.Type convertParticipantType(int paramInt)
  {
    Data.Participant.Type localType;
    switch (paramInt)
    {
    default:
      localType = Data.Participant.Type.ANDROID;
    case 1:
    case 2:
    case 3:
    case 4:
    }
    while (true)
    {
      return localType;
      localType = Data.Participant.Type.INVITED;
      continue;
      localType = Data.Participant.Type.SMS;
      continue;
      localType = Data.Participant.Type.ANDROID;
      continue;
      localType = Data.Participant.Type.IPHONE;
    }
  }

  // ERROR //
  public static android.os.Bundle createConversationLocally(Context paramContext, EsAccount paramEsAccount, Client.ClientConversation paramClientConversation, String paramString, boolean paramBoolean, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    // Byte code:
    //   0: ldc 35
    //   2: iconst_3
    //   3: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   6: ifeq +12 -> 18
    //   9: ldc 35
    //   11: ldc_w 298
    //   14: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   17: pop
    //   18: aload_0
    //   19: aload_1
    //   20: invokestatic 68	com/google/android/apps/plus/content/EsDatabaseHelper:getDatabaseHelper	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)Lcom/google/android/apps/plus/content/EsDatabaseHelper;
    //   23: invokevirtual 72	com/google/android/apps/plus/content/EsDatabaseHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   26: astore 6
    //   28: aload 6
    //   30: invokevirtual 77	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   33: aload_2
    //   34: invokevirtual 304	com/google/wireless/realtimechat/proto/Client$ClientConversation:getParticipantList	()Ljava/util/List;
    //   37: astore 8
    //   39: aload 8
    //   41: invokeinterface 309 1 0
    //   46: istore 9
    //   48: aconst_null
    //   49: astore 10
    //   51: iload 9
    //   53: iconst_1
    //   54: if_icmpne +24 -> 78
    //   57: aload 6
    //   59: aload 8
    //   61: iconst_0
    //   62: invokeinterface 313 2 0
    //   67: checkcast 253	com/google/wireless/realtimechat/proto/Data$Participant
    //   70: invokevirtual 316	com/google/wireless/realtimechat/proto/Data$Participant:getParticipantId	()Ljava/lang/String;
    //   73: invokestatic 320	com/google/android/apps/plus/content/EsConversationsData:queryOneToOneConversation$51a85815	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Ljava/lang/Long;
    //   76: astore 10
    //   78: ldc2_w 321
    //   81: invokestatic 327	java/lang/System:currentTimeMillis	()J
    //   84: lmul
    //   85: lstore 11
    //   87: new 43	java/lang/StringBuilder
    //   90: dup
    //   91: ldc_w 329
    //   94: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   97: bipush 32
    //   99: invokestatic 335	com/google/android/apps/plus/util/StringUtils:randomString	(I)Ljava/lang/String;
    //   102: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   108: astore 13
    //   110: aload 10
    //   112: ifnonnull +602 -> 714
    //   115: ldc 35
    //   117: iconst_3
    //   118: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   121: ifeq +12 -> 133
    //   124: ldc 35
    //   126: ldc_w 337
    //   129: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   132: pop
    //   133: aload 6
    //   135: aload_1
    //   136: aload_2
    //   137: lload 11
    //   139: invokestatic 341	com/google/android/apps/plus/content/EsConversationsData:insertConversation$2157227a	(Landroid/database/sqlite/SQLiteDatabase;Lcom/google/android/apps/plus/content/EsAccount;Lcom/google/wireless/realtimechat/proto/Client$ClientConversation;J)J
    //   142: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   145: astore 35
    //   147: aload 35
    //   149: astore 10
    //   151: iconst_1
    //   152: anewarray 94	java/lang/String
    //   155: dup
    //   156: iconst_0
    //   157: ldc_w 343
    //   160: aastore
    //   161: astore 36
    //   163: iconst_1
    //   164: anewarray 94	java/lang/String
    //   167: astore 39
    //   169: aload 39
    //   171: iconst_0
    //   172: aload_1
    //   173: invokevirtual 348	com/google/android/apps/plus/content/EsAccount:getRealTimeChatParticipantId	()Ljava/lang/String;
    //   176: aastore
    //   177: aload 6
    //   179: ldc 220
    //   181: aload 36
    //   183: ldc_w 350
    //   186: aload 39
    //   188: aconst_null
    //   189: aconst_null
    //   190: aconst_null
    //   191: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   194: astore 40
    //   196: aload 40
    //   198: astore 38
    //   200: aload 38
    //   202: invokeinterface 166 1 0
    //   207: ifne +47 -> 254
    //   210: new 79	android/content/ContentValues
    //   213: dup
    //   214: invokespecial 80	android/content/ContentValues:<init>	()V
    //   217: astore 41
    //   219: aload 41
    //   221: ldc_w 343
    //   224: aload_1
    //   225: invokevirtual 348	com/google/android/apps/plus/content/EsAccount:getRealTimeChatParticipantId	()Ljava/lang/String;
    //   228: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   231: aload 41
    //   233: ldc_w 352
    //   236: aload_1
    //   237: invokevirtual 355	com/google/android/apps/plus/content/EsAccount:getDisplayName	()Ljava/lang/String;
    //   240: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   243: aload 6
    //   245: ldc 220
    //   247: aconst_null
    //   248: aload 41
    //   250: invokevirtual 359	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   253: pop2
    //   254: aload 38
    //   256: ifnull +10 -> 266
    //   259: aload 38
    //   261: invokeinterface 182 1 0
    //   266: iload 4
    //   268: ifeq +440 -> 708
    //   271: iconst_1
    //   272: istore 21
    //   274: aload 5
    //   276: aload_2
    //   277: aload 13
    //   279: aload_3
    //   280: aconst_null
    //   281: iconst_0
    //   282: invokestatic 363	com/google/android/apps/plus/realtimechat/BunchCommands:createConversation	(Lcom/google/wireless/realtimechat/proto/Client$ClientConversation;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   285: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   288: aload_2
    //   289: invokevirtual 364	com/google/wireless/realtimechat/proto/Client$ClientConversation:hasType	()Z
    //   292: istore 22
    //   294: iconst_0
    //   295: istore 23
    //   297: iload 22
    //   299: ifeq +83 -> 382
    //   302: aload_2
    //   303: invokevirtual 367	com/google/wireless/realtimechat/proto/Client$ClientConversation:getType	()Lcom/google/wireless/realtimechat/proto/Data$ConversationType;
    //   306: astore 28
    //   308: getstatic 373	com/google/wireless/realtimechat/proto/Data$ConversationType:ONE_TO_ONE	Lcom/google/wireless/realtimechat/proto/Data$ConversationType;
    //   311: astore 29
    //   313: iconst_0
    //   314: istore 23
    //   316: aload 28
    //   318: aload 29
    //   320: if_acmpne +62 -> 382
    //   323: aload_2
    //   324: invokevirtual 376	com/google/wireless/realtimechat/proto/Client$ClientConversation:getParticipantCount	()I
    //   327: istore 30
    //   329: iconst_0
    //   330: istore 23
    //   332: iload 30
    //   334: ifle +48 -> 382
    //   337: aload 6
    //   339: ldc_w 378
    //   342: ldc_w 350
    //   345: iconst_1
    //   346: anewarray 94	java/lang/String
    //   349: dup
    //   350: iconst_0
    //   351: aload 8
    //   353: iconst_0
    //   354: invokeinterface 313 2 0
    //   359: checkcast 253	com/google/wireless/realtimechat/proto/Data$Participant
    //   362: invokevirtual 316	com/google/wireless/realtimechat/proto/Data$Participant:getParticipantId	()Ljava/lang/String;
    //   365: aastore
    //   366: invokevirtual 226	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   369: istore 31
    //   371: iconst_0
    //   372: istore 23
    //   374: iload 31
    //   376: ifle +6 -> 382
    //   379: iconst_1
    //   380: istore 23
    //   382: aconst_null
    //   383: astore 24
    //   385: aload_3
    //   386: ifnull +219 -> 605
    //   389: new 79	android/content/ContentValues
    //   392: dup
    //   393: invokespecial 80	android/content/ContentValues:<init>	()V
    //   396: astore 25
    //   398: aload 25
    //   400: ldc_w 380
    //   403: aload 13
    //   405: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   408: aload 25
    //   410: ldc 152
    //   412: aload 10
    //   414: invokevirtual 383	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   417: aload 25
    //   419: ldc_w 385
    //   422: aload_1
    //   423: invokevirtual 348	com/google/android/apps/plus/content/EsAccount:getRealTimeChatParticipantId	()Ljava/lang/String;
    //   426: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   429: aload 25
    //   431: ldc_w 387
    //   434: aload_3
    //   435: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   438: aload 25
    //   440: ldc 154
    //   442: iload 21
    //   444: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   447: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   450: aload 25
    //   452: ldc_w 389
    //   455: iconst_1
    //   456: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   459: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   462: aload 25
    //   464: ldc_w 391
    //   467: lload 11
    //   469: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   472: invokevirtual 383	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   475: aload 25
    //   477: ldc_w 393
    //   480: iconst_1
    //   481: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   484: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   487: aload 6
    //   489: ldc 150
    //   491: aconst_null
    //   492: aload 25
    //   494: invokevirtual 359	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   497: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   500: astore 24
    //   502: aload 25
    //   504: invokevirtual 234	android/content/ContentValues:clear	()V
    //   507: aload 25
    //   509: ldc_w 395
    //   512: iconst_1
    //   513: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   516: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   519: aload 25
    //   521: ldc_w 397
    //   524: lload 11
    //   526: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   529: invokevirtual 383	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   532: aload 25
    //   534: ldc_w 399
    //   537: aload_1
    //   538: invokevirtual 348	com/google/android/apps/plus/content/EsAccount:getRealTimeChatParticipantId	()Ljava/lang/String;
    //   541: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   544: aload 25
    //   546: ldc_w 401
    //   549: aload_3
    //   550: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   553: aload 25
    //   555: ldc_w 403
    //   558: aconst_null
    //   559: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   562: aload 25
    //   564: ldc_w 405
    //   567: iconst_1
    //   568: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   571: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   574: aload 6
    //   576: ldc 99
    //   578: aload 25
    //   580: new 43	java/lang/StringBuilder
    //   583: dup
    //   584: ldc 156
    //   586: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   589: aload 10
    //   591: invokestatic 408	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   594: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   597: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   600: aconst_null
    //   601: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   604: pop
    //   605: aload 6
    //   607: invokevirtual 137	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   610: aload 6
    //   612: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   615: aload_0
    //   616: aload_1
    //   617: aload 10
    //   619: invokevirtual 200	java/lang/Long:longValue	()J
    //   622: invokestatic 204	com/google/android/apps/plus/content/EsConversationsData:notifyMessagesChanged	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;J)V
    //   625: aload_0
    //   626: aload_1
    //   627: invokestatic 144	com/google/android/apps/plus/content/EsConversationsData:notifyConversationsChanged	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)V
    //   630: iload 23
    //   632: ifeq +8 -> 640
    //   635: aload_0
    //   636: aload_1
    //   637: invokestatic 411	com/google/android/apps/plus/content/EsConversationsData:notifySuggestionsChanged	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)V
    //   640: new 413	android/os/Bundle
    //   643: dup
    //   644: invokespecial 414	android/os/Bundle:<init>	()V
    //   647: astore 27
    //   649: aload 27
    //   651: ldc_w 416
    //   654: aload 10
    //   656: invokevirtual 200	java/lang/Long:longValue	()J
    //   659: invokevirtual 420	android/os/Bundle:putLong	(Ljava/lang/String;J)V
    //   662: aload 27
    //   664: ldc_w 422
    //   667: aload 24
    //   669: invokevirtual 200	java/lang/Long:longValue	()J
    //   672: invokevirtual 420	android/os/Bundle:putLong	(Ljava/lang/String;J)V
    //   675: aload 27
    //   677: areturn
    //   678: astore 37
    //   680: aconst_null
    //   681: astore 38
    //   683: aload 38
    //   685: ifnull +10 -> 695
    //   688: aload 38
    //   690: invokeinterface 182 1 0
    //   695: aload 37
    //   697: athrow
    //   698: astore 7
    //   700: aload 6
    //   702: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   705: aload 7
    //   707: athrow
    //   708: iconst_2
    //   709: istore 21
    //   711: goto -423 -> 288
    //   714: ldc 35
    //   716: iconst_3
    //   717: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   720: ifeq +27 -> 747
    //   723: ldc 35
    //   725: new 43	java/lang/StringBuilder
    //   728: dup
    //   729: ldc_w 424
    //   732: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   735: aload 10
    //   737: invokevirtual 277	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   740: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   743: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   746: pop
    //   747: lconst_0
    //   748: lstore 14
    //   750: aconst_null
    //   751: astore 16
    //   753: aload 6
    //   755: ldc 99
    //   757: iconst_1
    //   758: anewarray 94	java/lang/String
    //   761: dup
    //   762: iconst_0
    //   763: ldc_w 397
    //   766: aastore
    //   767: new 43	java/lang/StringBuilder
    //   770: dup
    //   771: ldc 156
    //   773: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   776: aload 10
    //   778: invokestatic 408	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   781: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   784: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   787: aconst_null
    //   788: aconst_null
    //   789: aconst_null
    //   790: aconst_null
    //   791: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   794: astore 16
    //   796: aload 16
    //   798: invokeinterface 166 1 0
    //   803: ifeq +17 -> 820
    //   806: aload 16
    //   808: iconst_0
    //   809: invokeinterface 170 2 0
    //   814: lstore 32
    //   816: lload 32
    //   818: lstore 14
    //   820: aload 16
    //   822: ifnull +131 -> 953
    //   825: aload 16
    //   827: invokeinterface 182 1 0
    //   832: goto +121 -> 953
    //   835: new 79	android/content/ContentValues
    //   838: dup
    //   839: invokespecial 80	android/content/ContentValues:<init>	()V
    //   842: astore 18
    //   844: aload 18
    //   846: ldc_w 426
    //   849: iconst_0
    //   850: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   853: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   856: aload 6
    //   858: ldc 99
    //   860: aload 18
    //   862: new 43	java/lang/StringBuilder
    //   865: dup
    //   866: ldc 156
    //   868: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   871: aload 10
    //   873: invokestatic 408	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   876: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   879: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   882: aconst_null
    //   883: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   886: pop
    //   887: aload 6
    //   889: aload 10
    //   891: invokevirtual 200	java/lang/Long:longValue	()J
    //   894: invokestatic 109	com/google/android/apps/plus/content/EsConversationsData:queryConversationId	(Landroid/database/sqlite/SQLiteDatabase;J)Ljava/lang/String;
    //   897: astore 20
    //   899: iload 4
    //   901: ifeq +41 -> 942
    //   904: iconst_1
    //   905: istore 21
    //   907: aload 5
    //   909: aload 20
    //   911: aload 13
    //   913: aload_3
    //   914: aconst_null
    //   915: iconst_0
    //   916: invokestatic 430	com/google/android/apps/plus/realtimechat/BunchCommands:sendMessage	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   919: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   922: goto -634 -> 288
    //   925: astore 17
    //   927: aload 16
    //   929: ifnull +10 -> 939
    //   932: aload 16
    //   934: invokeinterface 182 1 0
    //   939: aload 17
    //   941: athrow
    //   942: iconst_2
    //   943: istore 21
    //   945: goto -657 -> 288
    //   948: astore 37
    //   950: goto -267 -> 683
    //   953: lload 14
    //   955: lload 11
    //   957: lcmp
    //   958: ifle -123 -> 835
    //   961: lload 14
    //   963: lconst_1
    //   964: ladd
    //   965: lstore 11
    //   967: goto -132 -> 835
    //
    // Exception table:
    //   from	to	target	type
    //   151	196	678	finally
    //   33	147	698	finally
    //   259	610	698	finally
    //   688	698	698	finally
    //   714	747	698	finally
    //   825	942	698	finally
    //   753	816	925	finally
    //   200	254	948	finally
  }

  private static int determineMessageState(Client.ChatMessage paramChatMessage)
  {
    int i = 3;
    switch (4.$SwitchMap$com$google$wireless$realtimechat$proto$Client$ChatMessage$ReceiverState[paramChatMessage.getReceiverState().ordinal()])
    {
    default:
      if (EsLog.isLoggable("EsConversationsData", 6))
        Log.e("EsConversationsData", "ChatMessage's read state could not be determined.");
      i = 0;
    case 1:
    case 4:
    case 2:
    case 3:
    }
    while (true)
    {
      return i;
      i = 4;
      continue;
      i = 5;
    }
  }

  private static boolean hasLocalPhotoUri(SQLiteDatabase paramSQLiteDatabase, long paramLong)
  {
    Cursor localCursor = null;
    try
    {
      String[] arrayOfString1 = { "image_url" };
      String[] arrayOfString2 = new String[1];
      arrayOfString2[0] = String.valueOf(paramLong);
      localCursor = paramSQLiteDatabase.query("messages", arrayOfString1, "_id=?", arrayOfString2, null, null, null);
      String str;
      boolean bool1;
      if (localCursor.moveToFirst())
      {
        str = localCursor.getString(0);
        boolean bool2 = TextUtils.isEmpty(str);
        if (bool2)
        {
          if (localCursor != null)
            localCursor.close();
          bool1 = false;
        }
      }
      while (true)
      {
        return bool1;
        boolean bool3 = str.startsWith("content://");
        if (bool3)
        {
          if (localCursor != null)
            localCursor.close();
          bool1 = true;
        }
        else
        {
          if (localCursor != null)
            localCursor.close();
          bool1 = false;
          continue;
          if (localCursor != null)
            localCursor.close();
          bool1 = false;
        }
      }
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
  }

  private static long insertConversation$2157227a(SQLiteDatabase paramSQLiteDatabase, EsAccount paramEsAccount, Client.ClientConversation paramClientConversation, long paramLong)
  {
    ContentValues localContentValues = new ContentValues();
    int i;
    boolean bool1;
    label52: label84: int j;
    label116: int k;
    label124: Data.Participant localParticipant;
    if (paramClientConversation.getMuted())
    {
      i = 1;
      localContentValues.put("is_muted", Integer.valueOf(i));
      if ((!paramClientConversation.hasType()) || (paramClientConversation.getType() != Data.ConversationType.GROUP))
        break label367;
      bool1 = true;
      localContentValues.put("is_group", Boolean.valueOf(bool1));
      if (!paramClientConversation.hasName())
        break label373;
      localContentValues.put("name", paramClientConversation.getName());
      localContentValues.put("unread_count", Long.valueOf(paramClientConversation.getUnreadCount()));
      if ((!paramClientConversation.hasHidden()) || (!paramClientConversation.getHidden()))
        break label384;
      j = 1;
      if (j == 0)
        break label390;
      k = 0;
      localContentValues.put("is_visible", Integer.valueOf(k));
      localContentValues.put("is_pending_leave", Integer.valueOf(0));
      localContentValues.put("is_awaiting_event_stream", Integer.valueOf(0));
      localContentValues.put("latest_message_timestamp", Long.valueOf(paramLong));
      if (paramClientConversation.hasFirstEventTimestamp())
      {
        if (EsLog.isLoggable("EsConversationsData", 3))
          Log.d("EsConversationsData", "setting first event timestamp " + paramClientConversation.getFirstEventTimestamp());
        localContentValues.put("first_event_timestamp", Long.valueOf(paramClientConversation.getFirstEventTimestamp()));
      }
      if (!paramClientConversation.hasInviter())
        break label396;
      localParticipant = paramClientConversation.getInviter();
      if (EsLog.isLoggable("EsConversationsData", 3))
        Log.d("EsConversationsData", "conversation inviter " + localParticipant.getParticipantId());
      label279: if (localParticipant != null)
        localContentValues.put("inviter_id", localParticipant.getParticipantId());
      if ((!paramClientConversation.hasNeedsAccept()) || (!paramClientConversation.getNeedsAccept()))
        break label427;
    }
    label384: label390: label396: label427: for (int m = 1; ; m = 0)
    {
      localContentValues.put("is_pending_accept", Integer.valueOf(m));
      localContentValues.put("conversation_id", paramClientConversation.getId());
      long l = paramSQLiteDatabase.insert("conversations", null, localContentValues);
      syncParticipants(paramSQLiteDatabase, paramEsAccount, l, bool1, paramClientConversation);
      return l;
      i = 0;
      break;
      label367: bool1 = false;
      break label52;
      label373: localContentValues.putNull("name");
      break label84;
      j = 0;
      break label116;
      k = 1;
      break label124;
      boolean bool2 = EsLog.isLoggable("EsConversationsData", 3);
      localParticipant = null;
      if (!bool2)
        break label279;
      Log.d("EsConversationsData", "no inviter");
      localParticipant = null;
      break label279;
    }
  }

  // ERROR //
  public static android.os.Bundle insertLocalPhotoLocally$341823c7(Context paramContext, EsAccount paramEsAccount, long paramLong, String paramString)
  {
    // Byte code:
    //   0: ldc 35
    //   2: iconst_3
    //   3: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   6: ifeq +26 -> 32
    //   9: ldc 35
    //   11: new 43	java/lang/StringBuilder
    //   14: dup
    //   15: ldc_w 548
    //   18: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   21: lload_2
    //   22: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   25: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   28: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   31: pop
    //   32: new 413	android/os/Bundle
    //   35: dup
    //   36: invokespecial 414	android/os/Bundle:<init>	()V
    //   39: astore 5
    //   41: aload_0
    //   42: aload_1
    //   43: invokestatic 68	com/google/android/apps/plus/content/EsDatabaseHelper:getDatabaseHelper	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)Lcom/google/android/apps/plus/content/EsDatabaseHelper;
    //   46: invokevirtual 72	com/google/android/apps/plus/content/EsDatabaseHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   49: astore 6
    //   51: aload 6
    //   53: invokevirtual 77	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   56: lconst_0
    //   57: lstore 7
    //   59: aconst_null
    //   60: astore 9
    //   62: aload 6
    //   64: ldc 99
    //   66: iconst_4
    //   67: anewarray 94	java/lang/String
    //   70: dup
    //   71: iconst_0
    //   72: ldc 152
    //   74: aastore
    //   75: dup
    //   76: iconst_1
    //   77: ldc_w 489
    //   80: aastore
    //   81: dup
    //   82: iconst_2
    //   83: ldc_w 550
    //   86: aastore
    //   87: dup
    //   88: iconst_3
    //   89: ldc_w 397
    //   92: aastore
    //   93: new 43	java/lang/StringBuilder
    //   96: dup
    //   97: ldc 156
    //   99: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   102: lload_2
    //   103: invokestatic 97	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   106: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   109: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   112: aconst_null
    //   113: aconst_null
    //   114: aconst_null
    //   115: aconst_null
    //   116: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   119: astore 9
    //   121: aload 9
    //   123: invokeinterface 166 1 0
    //   128: istore 12
    //   130: aconst_null
    //   131: astore 13
    //   133: aconst_null
    //   134: astore 14
    //   136: iload 12
    //   138: ifeq +55 -> 193
    //   141: aload 9
    //   143: iconst_0
    //   144: invokeinterface 454 2 0
    //   149: astore 13
    //   151: aload 9
    //   153: iconst_1
    //   154: invokeinterface 454 2 0
    //   159: astore 14
    //   161: aload 14
    //   163: invokestatic 460	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   166: ifeq +13 -> 179
    //   169: aload 9
    //   171: iconst_2
    //   172: invokeinterface 454 2 0
    //   177: astore 14
    //   179: aload 9
    //   181: iconst_3
    //   182: invokeinterface 170 2 0
    //   187: lstore 22
    //   189: lload 22
    //   191: lstore 7
    //   193: aload 9
    //   195: ifnull +10 -> 205
    //   198: aload 9
    //   200: invokeinterface 182 1 0
    //   205: ldc2_w 321
    //   208: invokestatic 327	java/lang/System:currentTimeMillis	()J
    //   211: lmul
    //   212: lstore 15
    //   214: lload 15
    //   216: lload 7
    //   218: lcmp
    //   219: ifgt +9 -> 228
    //   222: lload 7
    //   224: lconst_1
    //   225: ladd
    //   226: lstore 15
    //   228: new 43	java/lang/StringBuilder
    //   231: dup
    //   232: ldc_w 329
    //   235: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   238: bipush 32
    //   240: invokestatic 335	com/google/android/apps/plus/util/StringUtils:randomString	(I)Ljava/lang/String;
    //   243: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   246: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   249: astore 17
    //   251: new 79	android/content/ContentValues
    //   254: dup
    //   255: invokespecial 80	android/content/ContentValues:<init>	()V
    //   258: astore 18
    //   260: aload 18
    //   262: ldc_w 403
    //   265: aload 4
    //   267: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   270: aload 18
    //   272: ldc_w 399
    //   275: aload_1
    //   276: invokevirtual 348	com/google/android/apps/plus/content/EsAccount:getRealTimeChatParticipantId	()Ljava/lang/String;
    //   279: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   282: aload 18
    //   284: ldc_w 397
    //   287: lload 15
    //   289: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   292: invokevirtual 383	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   295: iconst_1
    //   296: anewarray 94	java/lang/String
    //   299: astore 19
    //   301: aload 19
    //   303: iconst_0
    //   304: lload_2
    //   305: invokestatic 97	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   308: aastore
    //   309: aload 6
    //   311: ldc 99
    //   313: aload 18
    //   315: ldc 101
    //   317: aload 19
    //   319: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   322: pop
    //   323: aload 18
    //   325: invokevirtual 234	android/content/ContentValues:clear	()V
    //   328: aload 18
    //   330: ldc_w 380
    //   333: aload 17
    //   335: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   338: aload 18
    //   340: ldc 152
    //   342: lload_2
    //   343: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   346: invokevirtual 383	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   349: aload 18
    //   351: ldc_w 385
    //   354: aload_1
    //   355: invokevirtual 348	com/google/android/apps/plus/content/EsAccount:getRealTimeChatParticipantId	()Ljava/lang/String;
    //   358: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   361: aload 18
    //   363: ldc 154
    //   365: iconst_0
    //   366: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   369: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   372: aload 18
    //   374: ldc_w 389
    //   377: iconst_1
    //   378: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   381: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   384: aload 18
    //   386: ldc_w 391
    //   389: lload 15
    //   391: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   394: invokevirtual 383	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   397: aload 18
    //   399: ldc_w 393
    //   402: iconst_1
    //   403: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   406: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   409: aload 18
    //   411: ldc_w 451
    //   414: aload 4
    //   416: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   419: aload 6
    //   421: ldc 150
    //   423: aconst_null
    //   424: aload 18
    //   426: invokevirtual 359	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   429: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   432: astore 21
    //   434: aload 5
    //   436: ldc 152
    //   438: aload 13
    //   440: invokevirtual 553	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   443: aload 5
    //   445: ldc_w 555
    //   448: aload 14
    //   450: invokevirtual 553	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   453: aload 5
    //   455: ldc_w 422
    //   458: aload 21
    //   460: invokevirtual 200	java/lang/Long:longValue	()J
    //   463: invokevirtual 420	android/os/Bundle:putLong	(Ljava/lang/String;J)V
    //   466: aload 6
    //   468: invokevirtual 137	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   471: aload 6
    //   473: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   476: aload_0
    //   477: aload_1
    //   478: lload_2
    //   479: invokestatic 204	com/google/android/apps/plus/content/EsConversationsData:notifyMessagesChanged	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;J)V
    //   482: aload_0
    //   483: aload_1
    //   484: invokestatic 144	com/google/android/apps/plus/content/EsConversationsData:notifyConversationsChanged	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)V
    //   487: aload 5
    //   489: areturn
    //   490: astore 10
    //   492: aload 9
    //   494: ifnull +10 -> 504
    //   497: aload 9
    //   499: invokeinterface 182 1 0
    //   504: aload 10
    //   506: athrow
    //   507: astore 11
    //   509: aload 6
    //   511: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   514: aload 11
    //   516: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   62	189	490	finally
    //   198	471	507	finally
    //   497	507	507	finally
  }

  private static long insertSystemMessage(SQLiteDatabase paramSQLiteDatabase, Context paramContext, final EsAccount paramEsAccount, long paramLong1, String paramString1, int paramInt1, boolean paramBoolean1, String paramString2, int paramInt2, long paramLong2, boolean paramBoolean2, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "insertSystemMessage  text: " + paramString1 + " messageType: " + paramInt2 + " senderId: " + paramString2 + " messageState: " + paramInt1 + " allowNotification: " + paramBoolean1 + " timestamp: " + paramLong2);
    Long localLong1 = queryMessageRowId(paramSQLiteDatabase, paramLong1, paramLong2);
    boolean bool1;
    if (localLong1 == null)
      if ((paramInt1 == 5) || (!paramBoolean1) || (paramString2.equals(paramEsAccount.getRealTimeChatParticipantId())) || (paramLong2 < 1331854482138000L))
        bool1 = true;
    while (true)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("conversation_id", Long.valueOf(paramLong1));
      localContentValues.put("author_id", paramString2);
      localContentValues.put("text", paramString1);
      localContentValues.put("status", Integer.valueOf(paramInt1));
      localContentValues.put("type", Integer.valueOf(paramInt2));
      localContentValues.put("timestamp", Long.valueOf(paramLong2));
      localContentValues.put("notification_seen", Boolean.valueOf(bool1));
      Long localLong2 = Long.valueOf(paramSQLiteDatabase.insert("messages", null, localContentValues));
      long l2;
      Cursor localCursor;
      if (paramBoolean1)
      {
        l2 = 0L;
        localCursor = null;
      }
      long l1;
      try
      {
        String[] arrayOfString1 = { "conversation_id", "latest_message_timestamp" };
        String[] arrayOfString2 = new String[1];
        arrayOfString2[0] = String.valueOf(paramLong1);
        localCursor = paramSQLiteDatabase.query("conversations", arrayOfString1, "_id=?", arrayOfString2, null, null, null);
        boolean bool2 = localCursor.moveToFirst();
        String str = null;
        if (bool2)
        {
          str = localCursor.getString(0);
          long l3 = localCursor.getLong(1);
          l2 = l3;
        }
        if (localCursor != null)
          localCursor.close();
        if (EsLog.isLoggable("EsConversationsData", 3))
          Log.d("EsConversationsData", "new message timestamp " + paramLong2 + " conversation latest " + l2);
        if (paramLong2 >= l2)
        {
          if (EsLog.isLoggable("EsConversationsData", 3))
            Log.d("EsConversationsData", "updating latest message");
          localContentValues.clear();
          localContentValues.put("is_visible", Integer.valueOf(1));
          localContentValues.put("latest_message_timestamp", Long.valueOf(paramLong2));
          localContentValues.put("latest_message_author_id", paramString2);
          localContentValues.put("latest_message_text", paramString1);
          localContentValues.put("latest_message_image_url", null);
          localContentValues.put("latest_message_type", Integer.valueOf(paramInt2));
          String[] arrayOfString3 = new String[1];
          arrayOfString3[0] = String.valueOf(paramLong1);
          paramSQLiteDatabase.update("conversations", localContentValues, "_id=?", arrayOfString3);
        }
        sHandler.post(new Runnable()
        {
          public final void run()
          {
            OzViews localOzViews = OzViews.getViewForLogging(this.val$context);
            EsAnalytics.recordActionEvent(this.val$context, paramEsAccount, OzActions.MESSAGE_RECEIVED, localOzViews);
          }
        });
        if ((!paramEsAccount.getRealTimeChatParticipantId().equals(paramString2)) && (paramBoolean2) && (paramInt1 == 3))
        {
          paramRealTimeChatOperationState.addRequest(BunchCommands.sendReceipt(str, paramLong2, Client.Receipt.Type.DELIVERED));
          if ((paramRealTimeChatOperationState.getCurrentConversationRowId() == null) || (paramRealTimeChatOperationState.getCurrentConversationRowId().longValue() != paramLong1));
        }
        return l1;
        bool1 = false;
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
    }
  }

  public static void inviteParticipantsLocally(Context paramContext, EsAccount paramEsAccount, long paramLong, List<Data.Participant> paramList, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "inviteParticipantsLocally  conversationRowId: " + paramLong);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    HashSet localHashSet;
    Cursor localCursor;
    try
    {
      localHashSet = new HashSet();
      localCursor = null;
    }
    finally
    {
      try
      {
        localCursor = localSQLiteDatabase.query("participants_view", new String[] { "participant_id" }, "conversation_id=" + String.valueOf(paramLong) + " AND active" + "=1", null, null, null, null);
        if (localCursor.moveToNext())
          localHashSet.add(localCursor.getString(0));
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
    }
    if (localCursor != null)
      localCursor.close();
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Data.Participant localParticipant = (Data.Participant)localIterator.next();
      if (!localHashSet.contains(localParticipant.getParticipantId()))
      {
        if (EsLog.isLoggable("EsConversationsData", 3))
          Log.d("EsConversationsData", "inviting " + localParticipant.getParticipantId() + " name " + localParticipant.getFullName());
        localArrayList.add(localParticipant);
      }
    }
    paramRealTimeChatOperationState.addRequest(BunchCommands.inviteParticipants(queryConversationId(localSQLiteDatabase, paramLong), localArrayList));
    localSQLiteDatabase.setTransactionSuccessful();
    localSQLiteDatabase.endTransaction();
  }

  public static void leaveConversationLocally(Context paramContext, EsAccount paramEsAccount, long paramLong, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "leaveConversationLocally  conversationRowId: " + paramLong);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      String str = queryConversationId(localSQLiteDatabase, paramLong);
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("is_pending_leave", Integer.valueOf(1));
      localContentValues.put("latest_event_timestamp", Integer.valueOf(0));
      localContentValues.put("earliest_event_timestamp", Integer.valueOf(0));
      Log.d("EsConversationsData", "updating latest event timestamp 0");
      localSQLiteDatabase.update("conversations", localContentValues, "_id=" + String.valueOf(paramLong), null);
      localSQLiteDatabase.delete("messages", "conversation_id=" + String.valueOf(paramLong), null);
      paramRealTimeChatOperationState.addRequest(BunchCommands.leaveConversation(str));
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      notifyConversationsChanged(paramContext, paramEsAccount);
      return;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  public static void markAllNotificationsAsSeen(Context paramContext, EsAccount paramEsAccount)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("notification_seen", Integer.valueOf(1));
      localSQLiteDatabase.update("messages", localContentValues, null, null);
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      RealTimeChatNotifications.cancel(paramContext, paramEsAccount);
      return;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  public static void markConversationReadLocally(Context paramContext, EsAccount paramEsAccount, long paramLong, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "markConversationReadLocally conversationRowId: " + paramLong);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    String str;
    Cursor localCursor;
    LinkedList localLinkedList;
    try
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("notification_seen", Integer.valueOf(1));
      String[] arrayOfString1 = new String[1];
      arrayOfString1[0] = String.valueOf(paramLong);
      localSQLiteDatabase.update("messages", localContentValues, "conversation_id=?", arrayOfString1);
      localContentValues.clear();
      localContentValues.put("unread_count", Integer.valueOf(0));
      String[] arrayOfString2 = new String[1];
      arrayOfString2[0] = String.valueOf(paramLong);
      localSQLiteDatabase.update("conversations", localContentValues, "_id=?", arrayOfString2);
      str = queryConversationId(localSQLiteDatabase, paramLong);
      localCursor = null;
      try
      {
        String[] arrayOfString3 = { "timestamp", "status" };
        String[] arrayOfString4 = new String[4];
        arrayOfString4[0] = String.valueOf(3);
        arrayOfString4[1] = String.valueOf(4);
        arrayOfString4[2] = paramEsAccount.getRealTimeChatParticipantId();
        arrayOfString4[3] = String.valueOf(paramLong);
        localCursor = localSQLiteDatabase.query("messages_view", arrayOfString3, "(status=? OR status=?) AND author_id!=? AND conversation_id=?", arrayOfString4, null, null, null);
        localLinkedList = new LinkedList();
        while (localCursor.moveToNext())
        {
          if (EsLog.isLoggable("EsConversationsData", 3))
            Log.d("EsConversationsData", "sending read receipt " + localCursor.getString(0));
          long l = localCursor.getLong(0);
          int i = localCursor.getInt(1);
          localLinkedList.add(Long.valueOf(l));
          paramRealTimeChatOperationState.setShouldTriggerNotifications();
          if (i != 4)
            paramRealTimeChatOperationState.addRequest(BunchCommands.sendReceipt(str, l, Client.Receipt.Type.DELIVERED));
        }
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
    if (!localLinkedList.isEmpty())
      paramRealTimeChatOperationState.addRequest(BunchCommands.sendReadReceipts(str, localLinkedList));
    if (localCursor != null)
      localCursor.close();
    localSQLiteDatabase.setTransactionSuccessful();
    localSQLiteDatabase.endTransaction();
    notifyMessagesChanged(paramContext, paramEsAccount, paramLong);
    notifyConversationsChanged(paramContext, paramEsAccount);
  }

  public static void markNotificationsSeenLocally$785b8fa1(Context paramContext, EsAccount paramEsAccount, long paramLong)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "markNotificationsSeenLocally conversationRowId: " + paramLong);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("notification_seen", Integer.valueOf(1));
      String[] arrayOfString = new String[1];
      arrayOfString[0] = String.valueOf(paramLong);
      localSQLiteDatabase.update("messages", localContentValues, "conversation_id=?", arrayOfString);
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      notifyMessagesChanged(paramContext, paramEsAccount, paramLong);
      return;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  private static void notifyConversationsChanged(Context paramContext, EsAccount paramEsAccount)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "NOTIFY CONVERSATIONS");
    paramContext.getContentResolver().notifyChange(EsProvider.appendAccountParameter(EsProvider.CONVERSATIONS_URI, paramEsAccount), null);
  }

  private static void notifyMessagesChanged(Context paramContext, EsAccount paramEsAccount, long paramLong)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "NOTIFY MESSAGES");
    paramContext.getContentResolver().notifyChange(EsProvider.appendAccountParameter(EsProvider.CONVERSATIONS_URI, paramEsAccount), null);
    Uri localUri = EsProvider.buildMessagesUri(paramEsAccount, paramLong);
    paramContext.getContentResolver().notifyChange(localUri, null);
  }

  private static void notifyParticipantsChanged(Context paramContext, EsAccount paramEsAccount, long paramLong)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "NOTIFY PARTICIPANTS");
    paramContext.getContentResolver().notifyChange(EsProvider.appendAccountParameter(EsProvider.CONVERSATIONS_URI, paramEsAccount), null);
    Uri localUri = EsProvider.buildParticipantsUri(paramEsAccount, paramLong);
    paramContext.getContentResolver().notifyChange(localUri, null);
  }

  private static void notifySuggestionsChanged(Context paramContext, EsAccount paramEsAccount)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "NOTIFY SUGGESTIONS");
    paramContext.getContentResolver().notifyChange(EsProvider.appendAccountParameter(EsProvider.MESSENGER_SUGGESTIONS_URI, paramEsAccount), null);
  }

  // ERROR //
  public static void processBunchServerUpdate(Context paramContext, EsAccount paramEsAccount, com.google.wireless.realtimechat.proto.Client.BunchServerStateUpdate paramBunchServerStateUpdate, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokestatic 68	com/google/android/apps/plus/content/EsDatabaseHelper:getDatabaseHelper	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)Lcom/google/android/apps/plus/content/EsDatabaseHelper;
    //   5: invokevirtual 72	com/google/android/apps/plus/content/EsDatabaseHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   8: astore 4
    //   10: aload 4
    //   12: invokevirtual 77	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   15: aload_2
    //   16: invokevirtual 766	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:hasEventMetadata	()Z
    //   19: istore 6
    //   21: aconst_null
    //   22: astore 7
    //   24: iload 6
    //   26: ifeq +84 -> 110
    //   29: aload_2
    //   30: invokevirtual 770	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:getEventMetadata	()Lcom/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate$EventMetadata;
    //   33: invokevirtual 775	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate$EventMetadata:getConversationId	()Ljava/lang/String;
    //   36: astore 93
    //   38: aload_2
    //   39: invokevirtual 770	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:getEventMetadata	()Lcom/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate$EventMetadata;
    //   42: invokevirtual 778	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate$EventMetadata:getEventTimestamp	()J
    //   45: lstore 94
    //   47: aload 4
    //   49: aload_2
    //   50: invokevirtual 770	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:getEventMetadata	()Lcom/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate$EventMetadata;
    //   53: invokevirtual 775	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate$EventMetadata:getConversationId	()Ljava/lang/String;
    //   56: invokestatic 781	com/google/android/apps/plus/content/EsConversationsData:queryConversationRowId	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Ljava/lang/Long;
    //   59: astore 7
    //   61: aload 7
    //   63: ifnull +47 -> 110
    //   66: aload_3
    //   67: aload 93
    //   69: lload 94
    //   71: getstatic 605	com/google/wireless/realtimechat/proto/Client$Receipt$Type:DELIVERED	Lcom/google/wireless/realtimechat/proto/Client$Receipt$Type;
    //   74: invokestatic 609	com/google/android/apps/plus/realtimechat/BunchCommands:sendReceipt	(Ljava/lang/String;JLcom/google/wireless/realtimechat/proto/Client$Receipt$Type;)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   77: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   80: aload_3
    //   81: invokevirtual 613	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:getCurrentConversationRowId	()Ljava/lang/Long;
    //   84: ifnull +26 -> 110
    //   87: aload_3
    //   88: invokevirtual 613	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:getCurrentConversationRowId	()Ljava/lang/Long;
    //   91: aload 7
    //   93: if_acmpne +17 -> 110
    //   96: aload_3
    //   97: aload 93
    //   99: lload 94
    //   101: getstatic 616	com/google/wireless/realtimechat/proto/Client$Receipt$Type:READ	Lcom/google/wireless/realtimechat/proto/Client$Receipt$Type;
    //   104: invokestatic 609	com/google/android/apps/plus/realtimechat/BunchCommands:sendReceipt	(Ljava/lang/String;JLcom/google/wireless/realtimechat/proto/Client$Receipt$Type;)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   107: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   110: aload_2
    //   111: invokevirtual 784	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:hasInvalidateLocalCache	()Z
    //   114: ifeq +375 -> 489
    //   117: aload_2
    //   118: invokevirtual 788	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:getInvalidateLocalCache	()Lcom/google/wireless/realtimechat/proto/Client$InvalidateLocalCache;
    //   121: astore 78
    //   123: ldc 35
    //   125: iconst_3
    //   126: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   129: ifeq +30 -> 159
    //   132: ldc 35
    //   134: new 43	java/lang/StringBuilder
    //   137: dup
    //   138: ldc_w 790
    //   141: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   144: aload 78
    //   146: invokevirtual 795	com/google/wireless/realtimechat/proto/Client$InvalidateLocalCache:getVersion	()I
    //   149: invokevirtual 564	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   152: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   155: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   158: pop
    //   159: aload 78
    //   161: invokevirtual 798	com/google/wireless/realtimechat/proto/Client$InvalidateLocalCache:hasVersion	()Z
    //   164: ifeq +304 -> 468
    //   167: aload 4
    //   169: invokestatic 802	com/google/android/apps/plus/content/EsConversationsData:queryDatastoreVersion	(Landroid/database/sqlite/SQLiteDatabase;)Ljava/lang/String;
    //   172: astore 80
    //   174: iconst_m1
    //   175: istore 81
    //   177: aload 80
    //   179: ifnull +14 -> 193
    //   182: aload 80
    //   184: invokestatic 806	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   187: istore 91
    //   189: iload 91
    //   191: istore 81
    //   193: iload 81
    //   195: aload 78
    //   197: invokevirtual 795	com/google/wireless/realtimechat/proto/Client$InvalidateLocalCache:getVersion	()I
    //   200: if_icmpge +198 -> 398
    //   203: ldc 35
    //   205: iconst_3
    //   206: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   209: ifeq +30 -> 239
    //   212: ldc 35
    //   214: new 43	java/lang/StringBuilder
    //   217: dup
    //   218: ldc_w 808
    //   221: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   224: aload 78
    //   226: invokevirtual 795	com/google/wireless/realtimechat/proto/Client$InvalidateLocalCache:getVersion	()I
    //   229: invokevirtual 564	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   232: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   235: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   238: pop
    //   239: aload 4
    //   241: ldc 99
    //   243: aconst_null
    //   244: aconst_null
    //   245: invokevirtual 226	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   248: pop
    //   249: aload 78
    //   251: invokevirtual 795	com/google/wireless/realtimechat/proto/Client$InvalidateLocalCache:getVersion	()I
    //   254: invokestatic 810	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   257: astore 84
    //   259: new 79	android/content/ContentValues
    //   262: dup
    //   263: invokespecial 80	android/content/ContentValues:<init>	()V
    //   266: astore 85
    //   268: aload 85
    //   270: ldc 236
    //   272: ldc_w 812
    //   275: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   278: aload 85
    //   280: ldc 243
    //   282: aload 84
    //   284: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   287: aload 4
    //   289: ldc 245
    //   291: aconst_null
    //   292: aload 85
    //   294: iconst_5
    //   295: invokevirtual 249	android/database/sqlite/SQLiteDatabase:insertWithOnConflict	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;I)J
    //   298: pop2
    //   299: aload_3
    //   300: aload 78
    //   302: invokevirtual 795	com/google/wireless/realtimechat/proto/Client$InvalidateLocalCache:getVersion	()I
    //   305: invokevirtual 816	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:setClientVersion	(I)V
    //   308: aload_3
    //   309: lconst_0
    //   310: invokestatic 820	com/google/android/apps/plus/realtimechat/BunchCommands:getConversationList	(J)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   313: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   316: goto +2195 -> 2511
    //   319: aload 4
    //   321: invokevirtual 137	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   324: aload 4
    //   326: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   329: aload 7
    //   331: ifnull +43 -> 374
    //   334: iload 9
    //   336: ifeq +8 -> 344
    //   339: aload_0
    //   340: aload_1
    //   341: invokestatic 144	com/google/android/apps/plus/content/EsConversationsData:notifyConversationsChanged	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)V
    //   344: iload 11
    //   346: ifeq +13 -> 359
    //   349: aload_0
    //   350: aload_1
    //   351: aload 7
    //   353: invokevirtual 200	java/lang/Long:longValue	()J
    //   356: invokestatic 822	com/google/android/apps/plus/content/EsConversationsData:notifyParticipantsChanged	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;J)V
    //   359: iload 10
    //   361: ifeq +13 -> 374
    //   364: aload_0
    //   365: aload_1
    //   366: aload 7
    //   368: invokevirtual 200	java/lang/Long:longValue	()J
    //   371: invokestatic 204	com/google/android/apps/plus/content/EsConversationsData:notifyMessagesChanged	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;J)V
    //   374: return
    //   375: astore 89
    //   377: ldc 35
    //   379: iconst_5
    //   380: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   383: ifeq +2140 -> 2523
    //   386: ldc 35
    //   388: ldc_w 824
    //   391: invokestatic 280	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   394: pop
    //   395: goto +2128 -> 2523
    //   398: iload 81
    //   400: aload 78
    //   402: invokevirtual 795	com/google/wireless/realtimechat/proto/Client$InvalidateLocalCache:getVersion	()I
    //   405: if_icmple +2106 -> 2511
    //   408: ldc 35
    //   410: iconst_5
    //   411: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   414: ifeq +2097 -> 2511
    //   417: ldc 35
    //   419: new 43	java/lang/StringBuilder
    //   422: dup
    //   423: ldc_w 826
    //   426: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   429: aload 80
    //   431: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   434: ldc_w 828
    //   437: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   440: aload 78
    //   442: invokevirtual 795	com/google/wireless/realtimechat/proto/Client$InvalidateLocalCache:getVersion	()I
    //   445: invokevirtual 564	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   448: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   451: invokestatic 280	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   454: pop
    //   455: goto +2056 -> 2511
    //   458: astore 5
    //   460: aload 4
    //   462: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   465: aload 5
    //   467: athrow
    //   468: ldc 35
    //   470: iconst_5
    //   471: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   474: ifeq +2037 -> 2511
    //   477: ldc 35
    //   479: ldc_w 830
    //   482: invokestatic 280	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   485: pop
    //   486: goto +2025 -> 2511
    //   489: aload_2
    //   490: invokevirtual 833	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:hasMigration	()Z
    //   493: ifeq +218 -> 711
    //   496: aload_2
    //   497: invokevirtual 837	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:getMigration	()Lcom/google/wireless/realtimechat/proto/Client$Migration;
    //   500: astore 66
    //   502: ldc 35
    //   504: iconst_3
    //   505: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   508: ifeq +12 -> 520
    //   511: ldc 35
    //   513: ldc_w 839
    //   516: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   519: pop
    //   520: aload 66
    //   522: invokevirtual 844	com/google/wireless/realtimechat/proto/Client$Migration:getOldId	()Ljava/lang/String;
    //   525: astore 67
    //   527: aload 66
    //   529: invokevirtual 847	com/google/wireless/realtimechat/proto/Client$Migration:getNewId	()Ljava/lang/String;
    //   532: astore 68
    //   534: new 79	android/content/ContentValues
    //   537: dup
    //   538: invokespecial 80	android/content/ContentValues:<init>	()V
    //   541: astore 69
    //   543: aload 69
    //   545: ldc_w 343
    //   548: aload 68
    //   550: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   553: aload 4
    //   555: ldc_w 849
    //   558: aload 69
    //   560: ldc_w 350
    //   563: iconst_1
    //   564: anewarray 94	java/lang/String
    //   567: dup
    //   568: iconst_0
    //   569: aload 67
    //   571: aastore
    //   572: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   575: pop
    //   576: new 79	android/content/ContentValues
    //   579: dup
    //   580: invokespecial 80	android/content/ContentValues:<init>	()V
    //   583: astore 71
    //   585: aload 71
    //   587: ldc_w 385
    //   590: aload 68
    //   592: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   595: aload 4
    //   597: ldc 150
    //   599: aload 71
    //   601: ldc_w 851
    //   604: iconst_1
    //   605: anewarray 94	java/lang/String
    //   608: dup
    //   609: iconst_0
    //   610: aload 67
    //   612: aastore
    //   613: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   616: pop
    //   617: new 79	android/content/ContentValues
    //   620: dup
    //   621: invokespecial 80	android/content/ContentValues:<init>	()V
    //   624: astore 73
    //   626: aload 73
    //   628: ldc_w 399
    //   631: aload 68
    //   633: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   636: aload 4
    //   638: ldc 99
    //   640: aload 73
    //   642: ldc_w 853
    //   645: iconst_1
    //   646: anewarray 94	java/lang/String
    //   649: dup
    //   650: iconst_0
    //   651: aload 67
    //   653: aastore
    //   654: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   657: pop
    //   658: new 79	android/content/ContentValues
    //   661: dup
    //   662: invokespecial 80	android/content/ContentValues:<init>	()V
    //   665: astore 75
    //   667: aload 75
    //   669: ldc_w 526
    //   672: aload 68
    //   674: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   677: aload 4
    //   679: ldc 99
    //   681: aload 75
    //   683: ldc_w 855
    //   686: iconst_1
    //   687: anewarray 94	java/lang/String
    //   690: dup
    //   691: iconst_0
    //   692: aload 67
    //   694: aastore
    //   695: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   698: pop
    //   699: iconst_0
    //   700: istore 9
    //   702: iconst_0
    //   703: istore 10
    //   705: iconst_0
    //   706: istore 11
    //   708: goto -389 -> 319
    //   711: aload_2
    //   712: invokevirtual 858	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:hasNewConversation	()Z
    //   715: ifeq +194 -> 909
    //   718: aload_2
    //   719: invokevirtual 862	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:getNewConversation	()Lcom/google/wireless/realtimechat/proto/Client$NewConversation;
    //   722: astore 58
    //   724: ldc 35
    //   726: iconst_3
    //   727: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   730: ifeq +33 -> 763
    //   733: ldc 35
    //   735: new 43	java/lang/StringBuilder
    //   738: dup
    //   739: ldc_w 864
    //   742: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   745: aload 58
    //   747: invokevirtual 870	com/google/wireless/realtimechat/proto/Client$NewConversation:getClientConversation	()Lcom/google/wireless/realtimechat/proto/Client$ClientConversation;
    //   750: invokevirtual 535	com/google/wireless/realtimechat/proto/Client$ClientConversation:getId	()Ljava/lang/String;
    //   753: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   756: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   759: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   762: pop
    //   763: aload 58
    //   765: invokevirtual 870	com/google/wireless/realtimechat/proto/Client$NewConversation:getClientConversation	()Lcom/google/wireless/realtimechat/proto/Client$ClientConversation;
    //   768: astore 59
    //   770: aload 4
    //   772: aload_0
    //   773: aload_1
    //   774: aload 59
    //   776: aload_3
    //   777: invokestatic 874	com/google/android/apps/plus/content/EsConversationsData:processConversation	(Landroid/database/sqlite/SQLiteDatabase;Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;Lcom/google/wireless/realtimechat/proto/Client$ClientConversation;Lcom/google/android/apps/plus/realtimechat/RealTimeChatOperationState;)J
    //   780: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   783: astore 60
    //   785: new 79	android/content/ContentValues
    //   788: dup
    //   789: invokespecial 80	android/content/ContentValues:<init>	()V
    //   792: astore 61
    //   794: aload 61
    //   796: ldc_w 505
    //   799: iconst_1
    //   800: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   803: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   806: iconst_1
    //   807: anewarray 94	java/lang/String
    //   810: astore 62
    //   812: aload 62
    //   814: iconst_0
    //   815: aload 59
    //   817: invokevirtual 535	com/google/wireless/realtimechat/proto/Client$ClientConversation:getId	()Ljava/lang/String;
    //   820: aastore
    //   821: aload 4
    //   823: ldc 99
    //   825: aload 61
    //   827: ldc_w 693
    //   830: aload 62
    //   832: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   835: pop
    //   836: aload_3
    //   837: aload 59
    //   839: invokevirtual 535	com/google/wireless/realtimechat/proto/Client$ClientConversation:getId	()Ljava/lang/String;
    //   842: lconst_0
    //   843: lconst_0
    //   844: bipush 20
    //   846: invokestatic 129	com/google/android/apps/plus/realtimechat/BunchCommands:getEventStream	(Ljava/lang/String;JJI)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   849: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   852: ldc 35
    //   854: iconst_3
    //   855: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   858: ifeq +29 -> 887
    //   861: ldc 35
    //   863: new 43	java/lang/StringBuilder
    //   866: dup
    //   867: ldc 131
    //   869: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   872: aload 59
    //   874: invokevirtual 535	com/google/wireless/realtimechat/proto/Client$ClientConversation:getId	()Ljava/lang/String;
    //   877: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   880: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   883: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   886: pop
    //   887: aload 60
    //   889: invokevirtual 200	java/lang/Long:longValue	()J
    //   892: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   895: astore 7
    //   897: iconst_1
    //   898: istore 9
    //   900: iconst_1
    //   901: istore 10
    //   903: iconst_1
    //   904: istore 11
    //   906: goto -587 -> 319
    //   909: aload_2
    //   910: invokevirtual 877	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:hasChatMessage	()Z
    //   913: ifeq +426 -> 1339
    //   916: aload_2
    //   917: invokevirtual 881	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:getChatMessage	()Lcom/google/wireless/realtimechat/proto/Client$ChatMessage;
    //   920: astore 43
    //   922: aload 4
    //   924: aload 43
    //   926: invokevirtual 882	com/google/wireless/realtimechat/proto/Client$ChatMessage:getConversationId	()Ljava/lang/String;
    //   929: invokestatic 781	com/google/android/apps/plus/content/EsConversationsData:queryConversationRowId	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Ljava/lang/Long;
    //   932: astore 7
    //   934: aload 7
    //   936: ifnonnull +79 -> 1015
    //   939: ldc 35
    //   941: iconst_3
    //   942: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   945: ifeq +50 -> 995
    //   948: ldc 35
    //   950: new 43	java/lang/StringBuilder
    //   953: dup
    //   954: ldc_w 884
    //   957: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   960: aload 43
    //   962: invokevirtual 887	com/google/wireless/realtimechat/proto/Client$ChatMessage:getTimestamp	()J
    //   965: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   968: ldc_w 889
    //   971: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   974: aload 43
    //   976: invokevirtual 882	com/google/wireless/realtimechat/proto/Client$ChatMessage:getConversationId	()Ljava/lang/String;
    //   979: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   982: ldc_w 891
    //   985: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   988: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   991: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   994: pop
    //   995: aload_3
    //   996: aload 43
    //   998: invokevirtual 882	com/google/wireless/realtimechat/proto/Client$ChatMessage:getConversationId	()Ljava/lang/String;
    //   1001: invokestatic 894	com/google/android/apps/plus/realtimechat/BunchCommands:getConversationListForConversation	(Ljava/lang/String;)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   1004: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   1007: aload 4
    //   1009: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   1012: goto -638 -> 374
    //   1015: aload 7
    //   1017: invokevirtual 200	java/lang/Long:longValue	()J
    //   1020: lstore 44
    //   1022: ldc 35
    //   1024: iconst_3
    //   1025: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   1028: ifeq +72 -> 1100
    //   1031: ldc 35
    //   1033: new 43	java/lang/StringBuilder
    //   1036: dup
    //   1037: ldc_w 896
    //   1040: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1043: aload 43
    //   1045: invokevirtual 899	com/google/wireless/realtimechat/proto/Client$ChatMessage:getMessageClientId	()Ljava/lang/String;
    //   1048: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1051: ldc_w 901
    //   1054: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1057: aload 43
    //   1059: invokevirtual 882	com/google/wireless/realtimechat/proto/Client$ChatMessage:getConversationId	()Ljava/lang/String;
    //   1062: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1065: ldc_w 575
    //   1068: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1071: aload 43
    //   1073: invokevirtual 887	com/google/wireless/realtimechat/proto/Client$ChatMessage:getTimestamp	()J
    //   1076: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1079: ldc_w 903
    //   1082: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1085: aload 43
    //   1087: invokevirtual 441	com/google/wireless/realtimechat/proto/Client$ChatMessage:getReceiverState	()Lcom/google/wireless/realtimechat/proto/Client$ChatMessage$ReceiverState;
    //   1090: invokevirtual 277	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1093: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1096: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   1099: pop
    //   1100: aload 43
    //   1102: invokevirtual 882	com/google/wireless/realtimechat/proto/Client$ChatMessage:getConversationId	()Ljava/lang/String;
    //   1105: astore 46
    //   1107: aload 43
    //   1109: invokestatic 905	com/google/android/apps/plus/content/EsConversationsData:determineMessageState	(Lcom/google/wireless/realtimechat/proto/Client$ChatMessage;)I
    //   1112: istore 47
    //   1114: aload_3
    //   1115: invokevirtual 613	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:getCurrentConversationRowId	()Ljava/lang/Long;
    //   1118: ifnull +16 -> 1134
    //   1121: lload 44
    //   1123: aload_3
    //   1124: invokevirtual 613	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:getCurrentConversationRowId	()Ljava/lang/Long;
    //   1127: invokevirtual 200	java/lang/Long:longValue	()J
    //   1130: lcmp
    //   1131: ifeq +166 -> 1297
    //   1134: aload_1
    //   1135: invokevirtual 348	com/google/android/apps/plus/content/EsAccount:getRealTimeChatParticipantId	()Ljava/lang/String;
    //   1138: aload 43
    //   1140: invokevirtual 908	com/google/wireless/realtimechat/proto/Client$ChatMessage:getSenderId	()Ljava/lang/String;
    //   1143: invokevirtual 583	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1146: istore 48
    //   1148: iload 48
    //   1150: ifne +147 -> 1297
    //   1153: iload 47
    //   1155: iconst_3
    //   1156: if_icmpeq +9 -> 1165
    //   1159: iload 47
    //   1161: iconst_4
    //   1162: if_icmpne +135 -> 1297
    //   1165: aload 4
    //   1167: ldc 99
    //   1169: iconst_1
    //   1170: anewarray 94	java/lang/String
    //   1173: dup
    //   1174: iconst_0
    //   1175: ldc_w 494
    //   1178: aastore
    //   1179: ldc_w 693
    //   1182: iconst_1
    //   1183: anewarray 94	java/lang/String
    //   1186: dup
    //   1187: iconst_0
    //   1188: aload 46
    //   1190: aastore
    //   1191: aconst_null
    //   1192: aconst_null
    //   1193: aconst_null
    //   1194: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   1197: astore 51
    //   1199: aload 51
    //   1201: astore 50
    //   1203: aload 50
    //   1205: invokeinterface 166 1 0
    //   1210: ifeq +1295 -> 2505
    //   1213: aload 50
    //   1215: iconst_0
    //   1216: invokeinterface 179 2 0
    //   1221: istore 55
    //   1223: iload 55
    //   1225: istore 52
    //   1227: aload 50
    //   1229: ifnull +10 -> 1239
    //   1232: aload 50
    //   1234: invokeinterface 182 1 0
    //   1239: new 79	android/content/ContentValues
    //   1242: dup
    //   1243: invokespecial 80	android/content/ContentValues:<init>	()V
    //   1246: astore 53
    //   1248: aload 53
    //   1250: ldc_w 494
    //   1253: iload 52
    //   1255: iconst_1
    //   1256: iadd
    //   1257: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1260: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   1263: aload 53
    //   1265: ldc_w 395
    //   1268: iconst_1
    //   1269: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1272: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   1275: aload 4
    //   1277: ldc 99
    //   1279: aload 53
    //   1281: ldc_w 693
    //   1284: iconst_1
    //   1285: anewarray 94	java/lang/String
    //   1288: dup
    //   1289: iconst_0
    //   1290: aload 46
    //   1292: aastore
    //   1293: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   1296: pop
    //   1297: aload 4
    //   1299: aload_0
    //   1300: aload_1
    //   1301: aload 43
    //   1303: aload 46
    //   1305: lload 44
    //   1307: iconst_0
    //   1308: aload_3
    //   1309: invokestatic 912	com/google/android/apps/plus/content/EsConversationsData:processMessage	(Landroid/database/sqlite/SQLiteDatabase;Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;Lcom/google/wireless/realtimechat/proto/Client$ChatMessage;Ljava/lang/String;JZLcom/google/android/apps/plus/realtimechat/RealTimeChatOperationState;)V
    //   1312: iconst_1
    //   1313: istore 9
    //   1315: iconst_1
    //   1316: istore 10
    //   1318: iconst_0
    //   1319: istore 11
    //   1321: goto -1002 -> 319
    //   1324: aload 50
    //   1326: ifnull +10 -> 1336
    //   1329: aload 50
    //   1331: invokeinterface 182 1 0
    //   1336: aload 49
    //   1338: athrow
    //   1339: aload_2
    //   1340: invokevirtual 915	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:hasMembershipChange	()Z
    //   1343: ifeq +131 -> 1474
    //   1346: aload_2
    //   1347: invokevirtual 919	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:getMembershipChange	()Lcom/google/wireless/realtimechat/proto/Client$MembershipChange;
    //   1350: astore 41
    //   1352: aload 4
    //   1354: aload 41
    //   1356: invokevirtual 922	com/google/wireless/realtimechat/proto/Client$MembershipChange:getConversationId	()Ljava/lang/String;
    //   1359: invokestatic 781	com/google/android/apps/plus/content/EsConversationsData:queryConversationRowId	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Ljava/lang/Long;
    //   1362: astore 7
    //   1364: aload 7
    //   1366: ifnonnull +79 -> 1445
    //   1369: ldc 35
    //   1371: iconst_3
    //   1372: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   1375: ifeq +50 -> 1425
    //   1378: ldc 35
    //   1380: new 43	java/lang/StringBuilder
    //   1383: dup
    //   1384: ldc_w 924
    //   1387: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1390: aload 41
    //   1392: invokevirtual 925	com/google/wireless/realtimechat/proto/Client$MembershipChange:getTimestamp	()J
    //   1395: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1398: ldc_w 889
    //   1401: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1404: aload 41
    //   1406: invokevirtual 922	com/google/wireless/realtimechat/proto/Client$MembershipChange:getConversationId	()Ljava/lang/String;
    //   1409: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1412: ldc_w 891
    //   1415: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1418: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1421: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   1424: pop
    //   1425: aload_3
    //   1426: aload 41
    //   1428: invokevirtual 922	com/google/wireless/realtimechat/proto/Client$MembershipChange:getConversationId	()Ljava/lang/String;
    //   1431: invokestatic 894	com/google/android/apps/plus/realtimechat/BunchCommands:getConversationListForConversation	(Ljava/lang/String;)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   1434: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   1437: aload 4
    //   1439: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   1442: goto -1068 -> 374
    //   1445: aload 4
    //   1447: aload_0
    //   1448: aload_1
    //   1449: aload 7
    //   1451: invokevirtual 200	java/lang/Long:longValue	()J
    //   1454: aload 41
    //   1456: iconst_3
    //   1457: iconst_0
    //   1458: aload_3
    //   1459: invokestatic 929	com/google/android/apps/plus/content/EsConversationsData:processMembershipChange	(Landroid/database/sqlite/SQLiteDatabase;Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;JLcom/google/wireless/realtimechat/proto/Client$MembershipChange;IZLcom/google/android/apps/plus/realtimechat/RealTimeChatOperationState;)V
    //   1462: iconst_1
    //   1463: istore 9
    //   1465: iconst_1
    //   1466: istore 10
    //   1468: iconst_1
    //   1469: istore 11
    //   1471: goto -1152 -> 319
    //   1474: aload_2
    //   1475: invokevirtual 932	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:hasGroupConversationRename	()Z
    //   1478: ifeq +132 -> 1610
    //   1481: aload_2
    //   1482: invokevirtual 936	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:getGroupConversationRename	()Lcom/google/wireless/realtimechat/proto/Client$GroupConversationRename;
    //   1485: astore 39
    //   1487: aload 4
    //   1489: aload 39
    //   1491: invokevirtual 939	com/google/wireless/realtimechat/proto/Client$GroupConversationRename:getConversationId	()Ljava/lang/String;
    //   1494: invokestatic 781	com/google/android/apps/plus/content/EsConversationsData:queryConversationRowId	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Ljava/lang/Long;
    //   1497: astore 7
    //   1499: aload 7
    //   1501: ifnonnull +79 -> 1580
    //   1504: ldc 35
    //   1506: iconst_3
    //   1507: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   1510: ifeq +50 -> 1560
    //   1513: ldc 35
    //   1515: new 43	java/lang/StringBuilder
    //   1518: dup
    //   1519: ldc_w 941
    //   1522: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1525: aload 39
    //   1527: invokevirtual 942	com/google/wireless/realtimechat/proto/Client$GroupConversationRename:getTimestamp	()J
    //   1530: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1533: ldc_w 889
    //   1536: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1539: aload 39
    //   1541: invokevirtual 939	com/google/wireless/realtimechat/proto/Client$GroupConversationRename:getConversationId	()Ljava/lang/String;
    //   1544: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1547: ldc_w 891
    //   1550: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1553: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1556: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   1559: pop
    //   1560: aload_3
    //   1561: aload 39
    //   1563: invokevirtual 939	com/google/wireless/realtimechat/proto/Client$GroupConversationRename:getConversationId	()Ljava/lang/String;
    //   1566: invokestatic 894	com/google/android/apps/plus/realtimechat/BunchCommands:getConversationListForConversation	(Ljava/lang/String;)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   1569: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   1572: aload 4
    //   1574: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   1577: goto -1203 -> 374
    //   1580: aload 4
    //   1582: aload_0
    //   1583: aload_1
    //   1584: aload 7
    //   1586: invokevirtual 200	java/lang/Long:longValue	()J
    //   1589: aload 39
    //   1591: iconst_1
    //   1592: iconst_3
    //   1593: iconst_0
    //   1594: aload_3
    //   1595: invokestatic 946	com/google/android/apps/plus/content/EsConversationsData:processGroupConversationRename	(Landroid/database/sqlite/SQLiteDatabase;Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;JLcom/google/wireless/realtimechat/proto/Client$GroupConversationRename;ZIZLcom/google/android/apps/plus/realtimechat/RealTimeChatOperationState;)V
    //   1598: iconst_1
    //   1599: istore 9
    //   1601: iconst_1
    //   1602: istore 10
    //   1604: iconst_0
    //   1605: istore 11
    //   1607: goto -1288 -> 319
    //   1610: aload_2
    //   1611: invokevirtual 949	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:hasTileEvent	()Z
    //   1614: ifeq +133 -> 1747
    //   1617: aload_2
    //   1618: invokevirtual 953	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:getTileEvent	()Lcom/google/wireless/realtimechat/proto/Client$TileEvent;
    //   1621: astore 37
    //   1623: aload 4
    //   1625: aload 37
    //   1627: invokevirtual 956	com/google/wireless/realtimechat/proto/Client$TileEvent:getConversationId	()Ljava/lang/String;
    //   1630: invokestatic 781	com/google/android/apps/plus/content/EsConversationsData:queryConversationRowId	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Ljava/lang/Long;
    //   1633: astore 7
    //   1635: aload 7
    //   1637: ifnonnull +79 -> 1716
    //   1640: ldc 35
    //   1642: iconst_3
    //   1643: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   1646: ifeq +50 -> 1696
    //   1649: ldc 35
    //   1651: new 43	java/lang/StringBuilder
    //   1654: dup
    //   1655: ldc_w 958
    //   1658: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1661: aload 37
    //   1663: invokevirtual 959	com/google/wireless/realtimechat/proto/Client$TileEvent:getTimestamp	()J
    //   1666: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1669: ldc_w 889
    //   1672: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1675: aload 37
    //   1677: invokevirtual 956	com/google/wireless/realtimechat/proto/Client$TileEvent:getConversationId	()Ljava/lang/String;
    //   1680: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1683: ldc_w 891
    //   1686: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1689: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1692: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   1695: pop
    //   1696: aload_3
    //   1697: aload 37
    //   1699: invokevirtual 956	com/google/wireless/realtimechat/proto/Client$TileEvent:getConversationId	()Ljava/lang/String;
    //   1702: invokestatic 894	com/google/android/apps/plus/realtimechat/BunchCommands:getConversationListForConversation	(Ljava/lang/String;)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   1705: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   1708: aload 4
    //   1710: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   1713: goto -1339 -> 374
    //   1716: aload 4
    //   1718: aload_0
    //   1719: aload_1
    //   1720: aload 7
    //   1722: invokevirtual 200	java/lang/Long:longValue	()J
    //   1725: aload_2
    //   1726: invokevirtual 953	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:getTileEvent	()Lcom/google/wireless/realtimechat/proto/Client$TileEvent;
    //   1729: iconst_3
    //   1730: iconst_0
    //   1731: aload_3
    //   1732: invokestatic 963	com/google/android/apps/plus/content/EsConversationsData:processTileEvent	(Landroid/database/sqlite/SQLiteDatabase;Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;JLcom/google/wireless/realtimechat/proto/Client$TileEvent;IZLcom/google/android/apps/plus/realtimechat/RealTimeChatOperationState;)V
    //   1735: iconst_1
    //   1736: istore 9
    //   1738: iconst_1
    //   1739: istore 10
    //   1741: iconst_0
    //   1742: istore 11
    //   1744: goto -1425 -> 319
    //   1747: aload_2
    //   1748: invokevirtual 966	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:hasPresence	()Z
    //   1751: ifeq +342 -> 2093
    //   1754: aload_2
    //   1755: invokevirtual 970	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:getPresence	()Lcom/google/wireless/realtimechat/proto/Client$Presence;
    //   1758: astore 25
    //   1760: aload 4
    //   1762: aload 25
    //   1764: invokevirtual 973	com/google/wireless/realtimechat/proto/Client$Presence:getConversationId	()Ljava/lang/String;
    //   1767: invokestatic 781	com/google/android/apps/plus/content/EsConversationsData:queryConversationRowId	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Ljava/lang/Long;
    //   1770: astore 7
    //   1772: aload 7
    //   1774: ifnonnull +79 -> 1853
    //   1777: ldc 35
    //   1779: iconst_3
    //   1780: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   1783: ifeq +50 -> 1833
    //   1786: ldc 35
    //   1788: new 43	java/lang/StringBuilder
    //   1791: dup
    //   1792: ldc_w 975
    //   1795: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1798: aload 25
    //   1800: invokevirtual 976	com/google/wireless/realtimechat/proto/Client$Presence:getTimestamp	()J
    //   1803: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1806: ldc_w 889
    //   1809: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1812: aload 25
    //   1814: invokevirtual 973	com/google/wireless/realtimechat/proto/Client$Presence:getConversationId	()Ljava/lang/String;
    //   1817: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1820: ldc_w 891
    //   1823: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1826: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1829: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   1832: pop
    //   1833: aload_3
    //   1834: aload 25
    //   1836: invokevirtual 973	com/google/wireless/realtimechat/proto/Client$Presence:getConversationId	()Ljava/lang/String;
    //   1839: invokestatic 894	com/google/android/apps/plus/realtimechat/BunchCommands:getConversationListForConversation	(Ljava/lang/String;)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   1842: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   1845: aload 4
    //   1847: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   1850: goto -1476 -> 374
    //   1853: aload 7
    //   1855: invokevirtual 200	java/lang/Long:longValue	()J
    //   1858: lstore 26
    //   1860: ldc 35
    //   1862: iconst_3
    //   1863: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   1866: ifeq +30 -> 1896
    //   1869: ldc 35
    //   1871: new 43	java/lang/StringBuilder
    //   1874: dup
    //   1875: ldc_w 978
    //   1878: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1881: aload 25
    //   1883: invokevirtual 973	com/google/wireless/realtimechat/proto/Client$Presence:getConversationId	()Ljava/lang/String;
    //   1886: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1889: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1892: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   1895: pop
    //   1896: aload 25
    //   1898: invokevirtual 981	com/google/wireless/realtimechat/proto/Client$Presence:hasConversationId	()Z
    //   1901: ifne +47 -> 1948
    //   1904: ldc 35
    //   1906: bipush 6
    //   1908: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   1911: istore 33
    //   1913: iconst_0
    //   1914: istore 9
    //   1916: iconst_0
    //   1917: istore 10
    //   1919: iconst_0
    //   1920: istore 11
    //   1922: iload 33
    //   1924: ifeq -1605 -> 319
    //   1927: ldc 35
    //   1929: ldc_w 983
    //   1932: invokestatic 449	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   1935: pop
    //   1936: iconst_0
    //   1937: istore 9
    //   1939: iconst_0
    //   1940: istore 10
    //   1942: iconst_0
    //   1943: istore 11
    //   1945: goto -1626 -> 319
    //   1948: aload 25
    //   1950: invokevirtual 973	com/google/wireless/realtimechat/proto/Client$Presence:getConversationId	()Ljava/lang/String;
    //   1953: astore 28
    //   1955: aload_3
    //   1956: invokevirtual 613	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:getCurrentConversationRowId	()Ljava/lang/Long;
    //   1959: astore 29
    //   1961: aload 25
    //   1963: invokevirtual 986	com/google/wireless/realtimechat/proto/Client$Presence:hasReciprocate	()Z
    //   1966: ifeq +57 -> 2023
    //   1969: aload 25
    //   1971: invokevirtual 989	com/google/wireless/realtimechat/proto/Client$Presence:getReciprocate	()Z
    //   1974: ifeq +49 -> 2023
    //   1977: aload 29
    //   1979: ifnull +44 -> 2023
    //   1982: aload 29
    //   1984: invokevirtual 200	java/lang/Long:longValue	()J
    //   1987: lconst_0
    //   1988: lcmp
    //   1989: ifeq +34 -> 2023
    //   1992: aload 4
    //   1994: aload_3
    //   1995: invokevirtual 613	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:getCurrentConversationRowId	()Ljava/lang/Long;
    //   1998: invokevirtual 200	java/lang/Long:longValue	()J
    //   2001: invokestatic 109	com/google/android/apps/plus/content/EsConversationsData:queryConversationId	(Landroid/database/sqlite/SQLiteDatabase;J)Ljava/lang/String;
    //   2004: aload 28
    //   2006: invokevirtual 583	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   2009: ifeq +14 -> 2023
    //   2012: aload_3
    //   2013: aload 28
    //   2015: iconst_1
    //   2016: iconst_0
    //   2017: invokestatic 993	com/google/android/apps/plus/realtimechat/BunchCommands:presenceRequest	(Ljava/lang/String;ZZ)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   2020: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   2023: aload_1
    //   2024: invokevirtual 348	com/google/android/apps/plus/content/EsAccount:getRealTimeChatParticipantId	()Ljava/lang/String;
    //   2027: aload 25
    //   2029: invokevirtual 994	com/google/wireless/realtimechat/proto/Client$Presence:getSenderId	()Ljava/lang/String;
    //   2032: invokevirtual 583	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   2035: istore 30
    //   2037: iconst_0
    //   2038: istore 9
    //   2040: iconst_0
    //   2041: istore 10
    //   2043: iconst_0
    //   2044: istore 11
    //   2046: iload 30
    //   2048: ifne -1729 -> 319
    //   2051: aload 25
    //   2053: invokevirtual 994	com/google/wireless/realtimechat/proto/Client$Presence:getSenderId	()Ljava/lang/String;
    //   2056: astore 31
    //   2058: aload 25
    //   2060: invokevirtual 997	com/google/wireless/realtimechat/proto/Client$Presence:getType	()Lcom/google/wireless/realtimechat/proto/Client$Presence$Type;
    //   2063: getstatic 1003	com/google/wireless/realtimechat/proto/Client$Presence$Type:FOCUS	Lcom/google/wireless/realtimechat/proto/Client$Presence$Type;
    //   2066: if_acmpne +471 -> 2537
    //   2069: iconst_1
    //   2070: istore 32
    //   2072: lload 26
    //   2074: aload 31
    //   2076: iload 32
    //   2078: invokestatic 1009	com/google/android/apps/plus/realtimechat/RealTimeChatService:notifyUserPresenceChanged	(JLjava/lang/String;Z)V
    //   2081: iconst_0
    //   2082: istore 9
    //   2084: iconst_0
    //   2085: istore 10
    //   2087: iconst_0
    //   2088: istore 11
    //   2090: goto -1771 -> 319
    //   2093: aload_2
    //   2094: invokevirtual 1012	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:hasTyping	()Z
    //   2097: ifeq +230 -> 2327
    //   2100: aload_2
    //   2101: invokevirtual 1016	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:getTyping	()Lcom/google/wireless/realtimechat/proto/Client$Typing;
    //   2104: astore 15
    //   2106: aload 4
    //   2108: aload 15
    //   2110: invokevirtual 1019	com/google/wireless/realtimechat/proto/Client$Typing:getConversationId	()Ljava/lang/String;
    //   2113: invokestatic 781	com/google/android/apps/plus/content/EsConversationsData:queryConversationRowId	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Ljava/lang/Long;
    //   2116: astore 7
    //   2118: aload 7
    //   2120: ifnonnull +79 -> 2199
    //   2123: ldc 35
    //   2125: iconst_3
    //   2126: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   2129: ifeq +50 -> 2179
    //   2132: ldc 35
    //   2134: new 43	java/lang/StringBuilder
    //   2137: dup
    //   2138: ldc_w 1021
    //   2141: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   2144: aload 15
    //   2146: invokevirtual 1022	com/google/wireless/realtimechat/proto/Client$Typing:getTimestamp	()J
    //   2149: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   2152: ldc_w 889
    //   2155: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2158: aload 15
    //   2160: invokevirtual 1019	com/google/wireless/realtimechat/proto/Client$Typing:getConversationId	()Ljava/lang/String;
    //   2163: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2166: ldc_w 891
    //   2169: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2172: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2175: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   2178: pop
    //   2179: aload_3
    //   2180: aload 15
    //   2182: invokevirtual 1019	com/google/wireless/realtimechat/proto/Client$Typing:getConversationId	()Ljava/lang/String;
    //   2185: invokestatic 894	com/google/android/apps/plus/realtimechat/BunchCommands:getConversationListForConversation	(Ljava/lang/String;)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   2188: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   2191: aload 4
    //   2193: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   2196: goto -1822 -> 374
    //   2199: aload 7
    //   2201: invokevirtual 200	java/lang/Long:longValue	()J
    //   2204: lstore 16
    //   2206: aload_2
    //   2207: invokevirtual 1016	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:getTyping	()Lcom/google/wireless/realtimechat/proto/Client$Typing;
    //   2210: astore 18
    //   2212: ldc 35
    //   2214: iconst_3
    //   2215: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   2218: ifeq +30 -> 2248
    //   2221: ldc 35
    //   2223: new 43	java/lang/StringBuilder
    //   2226: dup
    //   2227: ldc_w 1024
    //   2230: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   2233: aload 18
    //   2235: invokevirtual 1019	com/google/wireless/realtimechat/proto/Client$Typing:getConversationId	()Ljava/lang/String;
    //   2238: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2241: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2244: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   2247: pop
    //   2248: aload_1
    //   2249: invokevirtual 348	com/google/android/apps/plus/content/EsAccount:getRealTimeChatParticipantId	()Ljava/lang/String;
    //   2252: aload 18
    //   2254: invokevirtual 1025	com/google/wireless/realtimechat/proto/Client$Typing:getSenderId	()Ljava/lang/String;
    //   2257: invokevirtual 583	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   2260: istore 19
    //   2262: iconst_0
    //   2263: istore 9
    //   2265: iconst_0
    //   2266: istore 10
    //   2268: iconst_0
    //   2269: istore 11
    //   2271: iload 19
    //   2273: ifne -1954 -> 319
    //   2276: aload 18
    //   2278: invokevirtual 1019	com/google/wireless/realtimechat/proto/Client$Typing:getConversationId	()Ljava/lang/String;
    //   2281: astore 20
    //   2283: aload 18
    //   2285: invokevirtual 1025	com/google/wireless/realtimechat/proto/Client$Typing:getSenderId	()Ljava/lang/String;
    //   2288: astore 21
    //   2290: aload 18
    //   2292: invokevirtual 1028	com/google/wireless/realtimechat/proto/Client$Typing:getType	()Lcom/google/wireless/realtimechat/proto/Client$Typing$Type;
    //   2295: getstatic 1034	com/google/wireless/realtimechat/proto/Client$Typing$Type:START	Lcom/google/wireless/realtimechat/proto/Client$Typing$Type;
    //   2298: if_acmpne +245 -> 2543
    //   2301: iconst_1
    //   2302: istore 22
    //   2304: lload 16
    //   2306: aload 20
    //   2308: aload 21
    //   2310: iload 22
    //   2312: invokestatic 1038	com/google/android/apps/plus/realtimechat/RealTimeChatService:notifyUserTypingStatusChanged	(JLjava/lang/String;Ljava/lang/String;Z)V
    //   2315: iconst_0
    //   2316: istore 9
    //   2318: iconst_0
    //   2319: istore 10
    //   2321: iconst_0
    //   2322: istore 11
    //   2324: goto -2005 -> 319
    //   2327: aload_2
    //   2328: invokevirtual 1041	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:hasReceipt	()Z
    //   2331: ifeq +126 -> 2457
    //   2334: aload_2
    //   2335: invokevirtual 1045	com/google/wireless/realtimechat/proto/Client$BunchServerStateUpdate:getReceipt	()Lcom/google/wireless/realtimechat/proto/Client$Receipt;
    //   2338: astore 13
    //   2340: aload 4
    //   2342: aload 13
    //   2344: invokevirtual 1048	com/google/wireless/realtimechat/proto/Client$Receipt:getConversationId	()Ljava/lang/String;
    //   2347: invokestatic 781	com/google/android/apps/plus/content/EsConversationsData:queryConversationRowId	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Ljava/lang/Long;
    //   2350: astore 7
    //   2352: aload 7
    //   2354: ifnonnull +79 -> 2433
    //   2357: ldc 35
    //   2359: iconst_3
    //   2360: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   2363: ifeq +50 -> 2413
    //   2366: ldc 35
    //   2368: new 43	java/lang/StringBuilder
    //   2371: dup
    //   2372: ldc_w 1050
    //   2375: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   2378: aload 13
    //   2380: invokevirtual 1051	com/google/wireless/realtimechat/proto/Client$Receipt:getTimestamp	()J
    //   2383: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   2386: ldc_w 889
    //   2389: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2392: aload 13
    //   2394: invokevirtual 1048	com/google/wireless/realtimechat/proto/Client$Receipt:getConversationId	()Ljava/lang/String;
    //   2397: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2400: ldc_w 891
    //   2403: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2406: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2409: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   2412: pop
    //   2413: aload_3
    //   2414: aload 13
    //   2416: invokevirtual 1048	com/google/wireless/realtimechat/proto/Client$Receipt:getConversationId	()Ljava/lang/String;
    //   2419: invokestatic 894	com/google/android/apps/plus/realtimechat/BunchCommands:getConversationListForConversation	(Ljava/lang/String;)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   2422: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   2425: aload 4
    //   2427: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   2430: goto -2056 -> 374
    //   2433: aload 4
    //   2435: aload 7
    //   2437: invokevirtual 200	java/lang/Long:longValue	()J
    //   2440: aload 13
    //   2442: invokestatic 1055	com/google/android/apps/plus/content/EsConversationsData:processReceipt$4fede216	(Landroid/database/sqlite/SQLiteDatabase;JLcom/google/wireless/realtimechat/proto/Client$Receipt;)V
    //   2445: iconst_1
    //   2446: istore 10
    //   2448: iconst_0
    //   2449: istore 9
    //   2451: iconst_0
    //   2452: istore 11
    //   2454: goto -2135 -> 319
    //   2457: ldc 35
    //   2459: iconst_5
    //   2460: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   2463: istore 8
    //   2465: iconst_0
    //   2466: istore 9
    //   2468: iconst_0
    //   2469: istore 10
    //   2471: iconst_0
    //   2472: istore 11
    //   2474: iload 8
    //   2476: ifeq -2157 -> 319
    //   2479: ldc 35
    //   2481: ldc_w 1057
    //   2484: invokestatic 280	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   2487: pop
    //   2488: iconst_0
    //   2489: istore 9
    //   2491: iconst_0
    //   2492: istore 10
    //   2494: iconst_0
    //   2495: istore 11
    //   2497: goto -2178 -> 319
    //   2500: astore 49
    //   2502: goto -1178 -> 1324
    //   2505: iconst_0
    //   2506: istore 52
    //   2508: goto -1281 -> 1227
    //   2511: iconst_1
    //   2512: istore 9
    //   2514: iconst_0
    //   2515: istore 10
    //   2517: iconst_0
    //   2518: istore 11
    //   2520: goto -2201 -> 319
    //   2523: iconst_m1
    //   2524: istore 81
    //   2526: goto -2333 -> 193
    //   2529: astore 49
    //   2531: aconst_null
    //   2532: astore 50
    //   2534: goto -1210 -> 1324
    //   2537: iconst_0
    //   2538: istore 32
    //   2540: goto -468 -> 2072
    //   2543: iconst_0
    //   2544: istore 22
    //   2546: goto -242 -> 2304
    //
    // Exception table:
    //   from	to	target	type
    //   182	189	375	java/lang/Exception
    //   15	174	458	finally
    //   182	189	458	finally
    //   193	324	458	finally
    //   377	455	458	finally
    //   468	1007	458	finally
    //   1015	1148	458	finally
    //   1232	1437	458	finally
    //   1445	1572	458	finally
    //   1580	1708	458	finally
    //   1716	1845	458	finally
    //   1853	2191	458	finally
    //   2199	2425	458	finally
    //   2433	2488	458	finally
    //   1203	1223	2500	finally
    //   1165	1199	2529	finally
  }

  public static void processChatMessageResponse(Context paramContext, EsAccount paramEsAccount, Client.ChatMessageResponse paramChatMessageResponse, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    boolean bool = paramChatMessageResponse.hasMessageClientId();
    String str = null;
    if (bool)
      str = paramChatMessageResponse.getMessageClientId();
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "processChatMessageResponse ConversationId: " + paramChatMessageResponse.getConversationId() + " clientMessageId: " + str + " Timestamp: " + paramChatMessageResponse.getTimestamp());
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      Long localLong = queryConversationRowId(localSQLiteDatabase, paramChatMessageResponse.getConversationId());
      if (localLong == null)
      {
        if (EsLog.isLoggable("EsConversationsData", 3))
          Log.d("EsConversationsData", "attempt to process ChatMessageResponse for a nonexistant conversation id [" + paramChatMessageResponse.getConversationId() + "]");
        paramRealTimeChatOperationState.addRequest(BunchCommands.getConversationListForConversation(paramChatMessageResponse.getConversationId()));
      }
      while (true)
      {
        return;
        processChatMessageResponse$7be9be32(localSQLiteDatabase, localLong.longValue(), paramChatMessageResponse);
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        notifyConversationsChanged(paramContext, paramEsAccount);
        notifyMessagesChanged(paramContext, paramEsAccount, localLong.longValue());
      }
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  private static void processChatMessageResponse$7be9be32(SQLiteDatabase paramSQLiteDatabase, long paramLong, Client.ChatMessageResponse paramChatMessageResponse)
  {
    String str = paramChatMessageResponse.getMessageClientId();
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "processChatMessageResponse Conversation row id: " + paramLong + ", clientMessageId: " + str + ", Timestamp: " + paramChatMessageResponse.getTimestamp());
    ContentValues localContentValues = new ContentValues();
    long l = paramChatMessageResponse.getTimestamp();
    Cursor localCursor = null;
    try
    {
      String[] arrayOfString1 = { "timestamp" };
      String[] arrayOfString2 = new String[2];
      arrayOfString2[0] = String.valueOf(paramLong);
      arrayOfString2[1] = str;
      localCursor = paramSQLiteDatabase.query("messages", arrayOfString1, "conversation_id=? AND message_id=?", arrayOfString2, null, null, null);
      boolean bool = localCursor.moveToFirst();
      localObject2 = null;
      if (bool)
      {
        Long localLong = Long.valueOf(localCursor.getLong(0));
        localObject2 = localLong;
      }
      if (localCursor != null)
        localCursor.close();
      localContentValues.put("status", Integer.valueOf(3));
      if (str != null)
      {
        localContentValues.put("timestamp", Long.valueOf(l));
        String[] arrayOfString6 = new String[2];
        arrayOfString6[0] = String.valueOf(str);
        arrayOfString6[1] = String.valueOf(paramLong);
        paramSQLiteDatabase.update("messages", localContentValues, "message_id=? AND conversation_id=?", arrayOfString6);
      }
    }
    finally
    {
      Object localObject2;
      String[] arrayOfString3;
      String[] arrayOfString4;
      String[] arrayOfString5;
      localCursor.close();
    }
    if (localCursor != null)
      localCursor.close();
  }

  private static long processConversation(SQLiteDatabase paramSQLiteDatabase, Context paramContext, EsAccount paramEsAccount, Client.ClientConversation paramClientConversation, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    String str1 = paramClientConversation.getId();
    if (paramClientConversation.hasConversationClientId())
      updateConversationId(paramSQLiteDatabase, paramClientConversation.getConversationClientId(), str1);
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "processConversation conversationId: " + paramClientConversation.getId() + " conversationClientId: " + paramClientConversation.getConversationClientId() + " participantCount: " + paramClientConversation.getParticipantCount() + " isPendingAccept: " + paramClientConversation.getNeedsAccept() + " hidden: " + paramClientConversation.getHidden() + " inactiveParticipantCount: " + paramClientConversation.getInactiveParticipantCount() + " unreadCount: " + paramClientConversation.getUnreadCount());
    Cursor localCursor = null;
    while (true)
    {
      try
      {
        localCursor = paramSQLiteDatabase.query("conversations", new String[] { "_id", "is_pending_leave" }, "conversation_id=?", new String[] { str1 }, null, null, null);
        boolean bool1 = localCursor.moveToFirst();
        localLong = null;
        int i = 0;
        if (bool1)
        {
          localLong = Long.valueOf(localCursor.getLong(0));
          if (!localCursor.isNull(1))
          {
            int i1 = localCursor.getInt(1);
            if (i1 != 0)
              i = 1;
          }
        }
        else
        {
          if (localCursor != null)
            localCursor.close();
          if (i == 0)
            continue;
          return l4;
        }
        i = 0;
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
      Log.d("EsConversationsData", " creating conversation");
      long l5 = 0L;
      if (paramClientConversation.hasLastPreviewEvent())
      {
        l5 = paramClientConversation.getLastPreviewEvent().getTimestamp();
        if (EsLog.isLoggable("EsConversationsData", 3))
          Log.d("EsConversationsData", "last preview event timestamp " + paramClientConversation.getLastPreviewEvent().getTimestamp());
      }
      Long localLong = Long.valueOf(insertConversation$2157227a(paramSQLiteDatabase, paramEsAccount, paramClientConversation, l5));
      Client.EventStreamResponse.Event localEvent;
      if (paramClientConversation.hasLastPreviewEvent())
      {
        localEvent = paramClientConversation.getLastPreviewEvent();
        if (!localEvent.hasChatMessage())
          break label1087;
        if (EsLog.isLoggable("EsConversationsData", 3))
          Log.d("EsConversationsData", "Got ChatMessage");
        processPreviewMessage$43824df1(paramSQLiteDatabase, localEvent.getChatMessage(), str1, localLong.longValue());
      }
      while (true)
      {
        try
        {
          long l3 = localLong.longValue();
          String[] arrayOfString2 = { "message_id", "text", "image_url" };
          String[] arrayOfString3 = new String[1];
          arrayOfString3[0] = String.valueOf(l3);
          localCursor = paramSQLiteDatabase.query("messages", arrayOfString2, "status=0 AND conversation_id=?", arrayOfString3, null, null, "timestamp ASC", null);
          if (EsLog.isLoggable("EsConversationsData", 3))
            Log.d("EsConversationsData", "sending " + localCursor.getCount() + " pending messages");
          if (!localCursor.moveToNext())
            break label1662;
          paramRealTimeChatOperationState.addRequest(BunchCommands.sendMessage(str1, localCursor.getString(0), localCursor.getString(1), localCursor.getString(2), false));
        }
        finally
        {
          if (localCursor != null)
            localCursor.close();
        }
        long l1 = localLong.longValue();
        String str9;
        label737: ContentValues localContentValues1;
        int j;
        label772: boolean bool2;
        if (EsLog.isLoggable("EsConversationsData", 3))
        {
          StringBuilder localStringBuilder = new StringBuilder("updateConversation Conversation id: ").append(paramClientConversation.getId()).append(", Muted: ").append(paramClientConversation.getMuted()).append(", UnreadCount: ").append(paramClientConversation.getUnreadCount()).append(", Name: ");
          if (paramClientConversation.hasName())
          {
            str9 = paramClientConversation.getName();
            Log.d("EsConversationsData", str9);
          }
        }
        else
        {
          localContentValues1 = new ContentValues();
          if (!paramClientConversation.getMuted())
            break label1039;
          j = 1;
          localContentValues1.put("is_muted", Integer.valueOf(j));
          if (!paramClientConversation.hasId())
            break label1045;
          bool2 = paramClientConversation.getId().startsWith("g");
          label804: localContentValues1.put("is_group", Boolean.valueOf(bool2));
          if (!paramClientConversation.hasName())
            break label1070;
          localContentValues1.put("name", paramClientConversation.getName());
          label836: long l2 = paramClientConversation.getUnreadCount();
          if ((paramRealTimeChatOperationState.getCurrentConversationRowId() != null) && (paramRealTimeChatOperationState.getCurrentConversationRowId().longValue() == l1))
            l2 = 0L;
          localContentValues1.put("unread_count", Long.valueOf(l2));
          localContentValues1.put("conversation_id", paramClientConversation.getId());
          if ((!paramClientConversation.hasHidden()) || (!paramClientConversation.getHidden()))
            break label1081;
        }
        label1039: label1045: label1070: label1081: for (int k = 0; ; k = 1)
        {
          localContentValues1.put("is_visible", Integer.valueOf(k));
          localContentValues1.put("is_awaiting_event_stream", Integer.valueOf(1));
          if (paramClientConversation.hasFirstEventTimestamp())
          {
            if (EsLog.isLoggable("EsConversationsData", 3))
              Log.d("EsConversationsData", "setting first event timestamp " + paramClientConversation.getFirstEventTimestamp());
            localContentValues1.put("first_event_timestamp", Long.valueOf(paramClientConversation.getFirstEventTimestamp()));
          }
          String[] arrayOfString1 = new String[1];
          arrayOfString1[0] = String.valueOf(l1);
          paramSQLiteDatabase.update("conversations", localContentValues1, "_id=?", arrayOfString1);
          syncParticipants(paramSQLiteDatabase, paramEsAccount, l1, bool2, paramClientConversation);
          break;
          str9 = "";
          break label737;
          j = 0;
          break label772;
          if (paramClientConversation.getParticipantList().size() > 1)
          {
            bool2 = true;
            break label804;
          }
          bool2 = false;
          break label804;
          localContentValues1.putNull("name");
          break label836;
        }
        label1087: if (localEvent.hasMembershipChange())
        {
          if (EsLog.isLoggable("EsConversationsData", 3))
            Log.d("EsConversationsData", "Got MembershipChange");
          Client.MembershipChange localMembershipChange = localEvent.getMembershipChange();
          Iterator localIterator2 = localMembershipChange.getParticipantList().iterator();
          while (localIterator2.hasNext())
          {
            Data.Participant localParticipant = (Data.Participant)localIterator2.next();
            if ((localParticipant.hasParticipantId()) && (localMembershipChange.getType() == Client.MembershipChange.Type.JOIN))
            {
              String str7 = queryParticipantFullName(paramSQLiteDatabase, localMembershipChange.getSenderId());
              if (str7 != null)
              {
                int n = R.string.realtimechat_conversation_system_message_participant_added;
                Object[] arrayOfObject2 = new Object[2];
                arrayOfObject2[0] = ("<b>" + str7 + "</b>");
                arrayOfObject2[1] = ("<b>" + localParticipant.getFullName() + "</b>");
                String str8 = paramContext.getString(n, arrayOfObject2);
                updatePreviewSystemMessage$5091a27(paramSQLiteDatabase, localLong.longValue(), str8, localMembershipChange.getSenderId(), 3, localMembershipChange.getTimestamp());
              }
            }
          }
        }
        else if (localEvent.hasGroupConversationRename())
        {
          if (EsLog.isLoggable("EsConversationsData", 3))
            Log.d("EsConversationsData", "Got GroupConversationRename");
          Client.GroupConversationRename localGroupConversationRename = localEvent.getGroupConversationRename();
          String str5 = queryParticipantFullName(paramSQLiteDatabase, localGroupConversationRename.getSenderId());
          if (str5 != null)
          {
            int m = R.string.realtimechat_conversation_system_message_rename;
            Object[] arrayOfObject1 = new Object[2];
            arrayOfObject1[0] = ("<b>" + str5 + "</b>");
            arrayOfObject1[1] = ("<i>" + localGroupConversationRename.getNewDisplayName() + "</i>");
            String str6 = paramContext.getString(m, arrayOfObject1);
            updatePreviewSystemMessage$5091a27(paramSQLiteDatabase, localLong.longValue(), str6, localGroupConversationRename.getSenderId(), 3, localGroupConversationRename.getTimestamp());
          }
        }
        else if (localEvent.hasTileEvent())
        {
          if (EsLog.isLoggable("EsConversationsData", 3))
            Log.d("EsConversationsData", "Got TileEvent");
          Client.TileEvent localTileEvent = localEvent.getTileEvent();
          if (localTileEvent.getEventType().equals("JOIN_HANGOUT"))
          {
            String str2 = null;
            Iterator localIterator1 = localTileEvent.getEventDataList().iterator();
            while (localIterator1.hasNext())
            {
              Data.KeyValue localKeyValue = (Data.KeyValue)localIterator1.next();
              if (localKeyValue.getKey().equals("AUTHOR_PROFILE_ID"))
                str2 = localKeyValue.getValue();
              else if (EsLog.isLoggable("EsConversationsData", 5))
                Log.w("EsConversationsData", "Got unexpected data in join hangout tile event: " + localKeyValue.getKey() + " - " + localKeyValue.getValue());
            }
            String str3 = queryParticipantFirstName(paramSQLiteDatabase, str2);
            if (str3 != null)
            {
              String str4 = paramContext.getString(R.string.realtimechat_conversation_system_message_joins_hangout_tile, new Object[] { str3 });
              updatePreviewSystemMessage$5091a27(paramSQLiteDatabase, localLong.longValue(), str4, str2, 3, localTileEvent.getTimestamp());
            }
          }
        }
      }
      label1662: if (localCursor != null)
        localCursor.close();
      ContentValues localContentValues2 = new ContentValues();
      localContentValues2.put("status", Integer.valueOf(1));
      paramSQLiteDatabase.update("messages", localContentValues2, "conversation_id=" + localLong + " AND status" + "=0", null);
      long l4 = localLong.longValue();
    }
  }

  // ERROR //
  public static void processConversationListResponse(Context paramContext, EsAccount paramEsAccount, com.google.wireless.realtimechat.proto.Client.ConversationListResponse paramConversationListResponse, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    // Byte code:
    //   0: ldc 35
    //   2: iconst_3
    //   3: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   6: ifeq +29 -> 35
    //   9: ldc 35
    //   11: new 43	java/lang/StringBuilder
    //   14: dup
    //   15: ldc_w 1270
    //   18: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   21: aload_2
    //   22: invokevirtual 1275	com/google/wireless/realtimechat/proto/Client$ConversationListResponse:getClientConversationCount	()I
    //   25: invokevirtual 564	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   28: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   31: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   34: pop
    //   35: aload_0
    //   36: aload_1
    //   37: invokestatic 68	com/google/android/apps/plus/content/EsDatabaseHelper:getDatabaseHelper	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)Lcom/google/android/apps/plus/content/EsDatabaseHelper;
    //   40: invokevirtual 72	com/google/android/apps/plus/content/EsDatabaseHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   43: astore 4
    //   45: aload_2
    //   46: invokevirtual 1278	com/google/wireless/realtimechat/proto/Client$ConversationListResponse:getClientConversationList	()Ljava/util/List;
    //   49: invokeinterface 709 1 0
    //   54: ifeq +4 -> 58
    //   57: return
    //   58: aload 4
    //   60: invokevirtual 77	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   63: aload_2
    //   64: invokevirtual 1278	com/google/wireless/realtimechat/proto/Client$ConversationListResponse:getClientConversationList	()Ljava/util/List;
    //   67: astore 6
    //   69: new 1280	java/util/PriorityQueue
    //   72: dup
    //   73: aload 6
    //   75: invokeinterface 309 1 0
    //   80: getstatic 31	com/google/android/apps/plus/content/EsConversationsData:sConversationComparator	Ljava/util/Comparator;
    //   83: invokespecial 1283	java/util/PriorityQueue:<init>	(ILjava/util/Comparator;)V
    //   86: astore 7
    //   88: aload 6
    //   90: invokeinterface 644 1 0
    //   95: astore 8
    //   97: aload 8
    //   99: invokeinterface 649 1 0
    //   104: ifeq +47 -> 151
    //   107: aload 8
    //   109: invokeinterface 653 1 0
    //   114: checkcast 300	com/google/wireless/realtimechat/proto/Client$ClientConversation
    //   117: astore 30
    //   119: aload 4
    //   121: aload_0
    //   122: aload_1
    //   123: aload 30
    //   125: aload_3
    //   126: invokestatic 874	com/google/android/apps/plus/content/EsConversationsData:processConversation	(Landroid/database/sqlite/SQLiteDatabase;Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;Lcom/google/wireless/realtimechat/proto/Client$ClientConversation;Lcom/google/android/apps/plus/realtimechat/RealTimeChatOperationState;)J
    //   129: pop2
    //   130: aload 7
    //   132: aload 30
    //   134: invokevirtual 1284	java/util/PriorityQueue:add	(Ljava/lang/Object;)Z
    //   137: pop
    //   138: goto -41 -> 97
    //   141: astore 5
    //   143: aload 4
    //   145: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   148: aload 5
    //   150: athrow
    //   151: iconst_0
    //   152: istore 9
    //   154: aconst_null
    //   155: astore 10
    //   157: aload 4
    //   159: ldc 99
    //   161: iconst_2
    //   162: anewarray 94	java/lang/String
    //   165: dup
    //   166: iconst_0
    //   167: ldc 152
    //   169: aastore
    //   170: dup
    //   171: iconst_1
    //   172: ldc_w 674
    //   175: aastore
    //   176: ldc_w 1286
    //   179: aconst_null
    //   180: aconst_null
    //   181: aconst_null
    //   182: aconst_null
    //   183: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   186: astore 10
    //   188: aload 10
    //   190: invokeinterface 634 1 0
    //   195: ifeq +100 -> 295
    //   198: aload 10
    //   200: iconst_0
    //   201: invokeinterface 454 2 0
    //   206: astore 26
    //   208: aload 10
    //   210: iconst_1
    //   211: invokeinterface 170 2 0
    //   216: lstore 27
    //   218: lload 27
    //   220: lconst_0
    //   221: lcmp
    //   222: ifle +9 -> 231
    //   225: lload 27
    //   227: lconst_1
    //   228: lsub
    //   229: lstore 27
    //   231: aload_3
    //   232: aload 26
    //   234: lload 27
    //   236: lconst_0
    //   237: bipush 20
    //   239: invokestatic 129	com/google/android/apps/plus/realtimechat/BunchCommands:getEventStream	(Ljava/lang/String;JJI)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   242: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   245: ldc 35
    //   247: iconst_3
    //   248: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   251: ifeq +38 -> 289
    //   254: ldc 35
    //   256: new 43	java/lang/StringBuilder
    //   259: dup
    //   260: ldc_w 1288
    //   263: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   266: lload 27
    //   268: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   271: ldc_w 1290
    //   274: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   277: aload 26
    //   279: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   282: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   285: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   288: pop
    //   289: iinc 9 1
    //   292: goto -104 -> 188
    //   295: aload 10
    //   297: ifnull +10 -> 307
    //   300: aload 10
    //   302: invokeinterface 182 1 0
    //   307: new 79	android/content/ContentValues
    //   310: dup
    //   311: invokespecial 80	android/content/ContentValues:<init>	()V
    //   314: astore 12
    //   316: aload 12
    //   318: ldc_w 505
    //   321: iconst_1
    //   322: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   325: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   328: bipush 25
    //   330: istore 13
    //   332: iload 9
    //   334: bipush 40
    //   336: if_icmpge +269 -> 605
    //   339: aload 7
    //   341: invokevirtual 1293	java/util/PriorityQueue:poll	()Ljava/lang/Object;
    //   344: checkcast 300	com/google/wireless/realtimechat/proto/Client$ClientConversation
    //   347: astore 19
    //   349: aload 19
    //   351: ifnull +254 -> 605
    //   354: iload 9
    //   356: bipush 20
    //   358: if_icmplt +7 -> 365
    //   361: bipush 10
    //   363: istore 13
    //   365: aload 4
    //   367: ldc 99
    //   369: iconst_1
    //   370: anewarray 94	java/lang/String
    //   373: dup
    //   374: iconst_0
    //   375: ldc_w 674
    //   378: aastore
    //   379: new 43	java/lang/StringBuilder
    //   382: dup
    //   383: ldc_w 1295
    //   386: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   389: aload 19
    //   391: invokevirtual 535	com/google/wireless/realtimechat/proto/Client$ClientConversation:getId	()Ljava/lang/String;
    //   394: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   397: ldc_w 1297
    //   400: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   403: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   406: aconst_null
    //   407: aconst_null
    //   408: aconst_null
    //   409: aconst_null
    //   410: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   413: astore 10
    //   415: aload 10
    //   417: invokeinterface 166 1 0
    //   422: ifeq +101 -> 523
    //   425: aload 10
    //   427: iconst_0
    //   428: invokeinterface 170 2 0
    //   433: lstore 23
    //   435: lload 23
    //   437: lconst_0
    //   438: lcmp
    //   439: ifle +9 -> 448
    //   442: lload 23
    //   444: lconst_1
    //   445: lsub
    //   446: lstore 23
    //   448: aload_3
    //   449: aload 19
    //   451: invokevirtual 535	com/google/wireless/realtimechat/proto/Client$ClientConversation:getId	()Ljava/lang/String;
    //   454: lload 23
    //   456: lconst_0
    //   457: iload 13
    //   459: invokestatic 129	com/google/android/apps/plus/realtimechat/BunchCommands:getEventStream	(Ljava/lang/String;JJI)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   462: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   465: ldc 35
    //   467: iconst_3
    //   468: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   471: ifeq +52 -> 523
    //   474: ldc 35
    //   476: new 43	java/lang/StringBuilder
    //   479: dup
    //   480: ldc_w 1299
    //   483: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   486: iload 13
    //   488: invokevirtual 564	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   491: ldc_w 1301
    //   494: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   497: lload 23
    //   499: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   502: ldc_w 1290
    //   505: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   508: aload 19
    //   510: invokevirtual 535	com/google/wireless/realtimechat/proto/Client$ClientConversation:getId	()Ljava/lang/String;
    //   513: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   516: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   519: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   522: pop
    //   523: aload 10
    //   525: ifnull +10 -> 535
    //   528: aload 10
    //   530: invokeinterface 182 1 0
    //   535: iconst_1
    //   536: anewarray 94	java/lang/String
    //   539: astore 21
    //   541: aload 21
    //   543: iconst_0
    //   544: aload 19
    //   546: invokevirtual 535	com/google/wireless/realtimechat/proto/Client$ClientConversation:getId	()Ljava/lang/String;
    //   549: aastore
    //   550: aload 4
    //   552: ldc 99
    //   554: aload 12
    //   556: ldc_w 693
    //   559: aload 21
    //   561: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   564: pop
    //   565: iinc 9 1
    //   568: goto -236 -> 332
    //   571: astore 11
    //   573: aload 10
    //   575: ifnull +10 -> 585
    //   578: aload 10
    //   580: invokeinterface 182 1 0
    //   585: aload 11
    //   587: athrow
    //   588: astore 20
    //   590: aload 10
    //   592: ifnull +10 -> 602
    //   595: aload 10
    //   597: invokeinterface 182 1 0
    //   602: aload 20
    //   604: athrow
    //   605: aload 12
    //   607: invokevirtual 234	android/content/ContentValues:clear	()V
    //   610: aload 12
    //   612: ldc 236
    //   614: ldc 238
    //   616: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   619: aload 12
    //   621: ldc 243
    //   623: iconst_0
    //   624: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   627: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   630: aload 4
    //   632: ldc 245
    //   634: aconst_null
    //   635: aload 12
    //   637: iconst_5
    //   638: invokevirtual 249	android/database/sqlite/SQLiteDatabase:insertWithOnConflict	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;I)J
    //   641: pop2
    //   642: aload 4
    //   644: ldc 245
    //   646: ldc_w 1303
    //   649: aconst_null
    //   650: invokevirtual 226	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   653: pop
    //   654: aload 4
    //   656: ldc 99
    //   658: ldc_w 1305
    //   661: aconst_null
    //   662: invokevirtual 226	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   665: istore 17
    //   667: ldc 35
    //   669: iconst_3
    //   670: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   673: ifeq +27 -> 700
    //   676: ldc 35
    //   678: new 43	java/lang/StringBuilder
    //   681: dup
    //   682: ldc_w 1307
    //   685: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   688: iload 17
    //   690: invokevirtual 564	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   693: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   696: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   699: pop
    //   700: aload 4
    //   702: invokevirtual 137	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   705: aload 4
    //   707: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   710: aload_0
    //   711: aload_1
    //   712: invokestatic 144	com/google/android/apps/plus/content/EsConversationsData:notifyConversationsChanged	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)V
    //   715: goto -658 -> 57
    //
    // Exception table:
    //   from	to	target	type
    //   63	138	141	finally
    //   300	349	141	finally
    //   528	705	141	finally
    //   157	289	571	finally
    //   365	523	588	finally
  }

  public static void processConversationPreferenceResponse$43e73c50(Client.ConversationPreferenceResponse paramConversationPreferenceResponse)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "processConversationPreferenceResponse");
    if (paramConversationPreferenceResponse.getStatus() == Data.ResponseStatus.OK)
      if (EsLog.isLoggable("EsConversationsData", 3))
        Log.d("EsConversationsData", "Conversation preference set successfully");
    while (true)
    {
      return;
      if (EsLog.isLoggable("EsConversationsData", 5))
        Log.w("EsConversationsData", "Failed to set conversation preference with code " + paramConversationPreferenceResponse.getStatus());
    }
  }

  public static void processConversationResponse(Context paramContext, EsAccount paramEsAccount, Client.NewConversationResponse paramNewConversationResponse, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "processConversationResponse conversationClientId: " + paramNewConversationResponse.getConversationClientId());
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    Client.ClientConversation localClientConversation;
    Data.ResponseStatus localResponseStatus;
    Long localLong;
    try
    {
      localClientConversation = paramNewConversationResponse.getClientConversation();
      localResponseStatus = paramNewConversationResponse.getStatus();
      if (localResponseStatus != Data.ResponseStatus.OK)
        break label257;
      if (paramNewConversationResponse.hasConversationClientId())
        updateConversationId(localSQLiteDatabase, paramNewConversationResponse.getConversationClientId(), localClientConversation.getId());
      localLong = Long.valueOf(processConversation(localSQLiteDatabase, paramContext, paramEsAccount, localClientConversation, paramRealTimeChatOperationState));
      Iterator localIterator = paramNewConversationResponse.getParticipantErrorList().iterator();
      while (localIterator.hasNext())
        processParticipantError(localSQLiteDatabase, paramContext, paramEsAccount, (Client.ParticipantError)localIterator.next(), localLong.longValue(), localClientConversation.getCreatedAt(), true, paramRealTimeChatOperationState);
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
    if (paramNewConversationResponse.hasReceipt())
      processReceipt$4fede216(localSQLiteDatabase, localLong.longValue(), paramNewConversationResponse.getReceipt());
    if (paramNewConversationResponse.hasRecentMessage())
      processPreviewMessage$43824df1(localSQLiteDatabase, paramNewConversationResponse.getRecentMessage(), localClientConversation.getId(), localLong.longValue());
    label257: boolean bool;
    do
    {
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      if (localLong != null)
      {
        notifyConversationsChanged(paramContext, paramEsAccount);
        notifyParticipantsChanged(paramContext, paramEsAccount, localLong.longValue());
        notifyMessagesChanged(paramContext, paramEsAccount, localLong.longValue());
      }
      return;
      if (EsLog.isLoggable("EsConversationsData", 3))
        Log.d("EsConversationsData", "Received conversation response error " + localResponseStatus);
      bool = paramNewConversationResponse.hasConversationClientId();
      localLong = null;
    }
    while (!bool);
    String str = paramNewConversationResponse.getConversationClientId();
    int i = 1;
    switch (4.$SwitchMap$com$google$wireless$realtimechat$proto$Data$ResponseStatus[localResponseStatus.ordinal()])
    {
    default:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      if (EsLog.isLoggable("EsConversationsData", 3))
        Log.d("EsConversationsData", "updateConversationErrorType Conversation client Id: " + str + ", FatalErrorType: " + i);
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("fatal_error_type", Integer.valueOf(i));
      String[] arrayOfString = new String[1];
      arrayOfString[0] = String.valueOf(str);
      localSQLiteDatabase.update("conversations", localContentValues, "conversation_id=?", arrayOfString);
      localLong = null;
      break;
      i = 2;
      continue;
      i = 3;
      continue;
      i = 4;
    }
  }

  public static void processEventStreamResponse(Context paramContext, EsAccount paramEsAccount, Client.EventStreamResponse paramEventStreamResponse, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "processEventStreamResponse conversationId: " + paramEventStreamResponse.getConversationId() + " earliest: " + paramEventStreamResponse.getEarliest() + " latest: " + paramEventStreamResponse.getLatest() + " eventCount: " + paramEventStreamResponse.getEventCount());
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    String str;
    Long localLong1;
    long l1;
    long l2;
    ContentValues localContentValues;
    int m;
    int n;
    long l3;
    long l4;
    try
    {
      str = paramEventStreamResponse.getConversationId();
      Cursor localCursor = null;
      try
      {
        localCursor = localSQLiteDatabase.query("conversations", new String[] { "_id", "earliest_event_timestamp", "latest_event_timestamp", "is_awaiting_older_events" }, "conversation_id=?", new String[] { str }, null, null, null);
        int j;
        if (localCursor.moveToFirst())
        {
          localLong1 = Long.valueOf(localCursor.getLong(0));
          l1 = localCursor.getLong(1);
          l2 = localCursor.getLong(2);
          int i = localCursor.getInt(3);
          if (i == 1)
          {
            j = 1;
            if (localCursor != null)
              localCursor.close();
            localContentValues = new ContentValues();
            localContentValues.put("is_awaiting_event_stream", Integer.valueOf(0));
            int k = paramEventStreamResponse.getEventCount();
            m = 0;
            n = 0;
            if (k <= 0)
              break label1147;
            l3 = paramEventStreamResponse.getEarliest();
            l4 = paramEventStreamResponse.getLatest();
            if (EsLog.isLoggable("EsConversationsData", 3))
              Log.d("EsConversationsData", "currentLatestEventTimestamp " + l2 + " eventStreamEarliest " + l3);
            if ((l3 >= l1) || (j != 0))
              break label450;
            if (EsLog.isLoggable("EsConversationsData", 3))
              Log.d("EsConversationsData", "isAwaitingOlderEvents is blank, ignoring");
          }
        }
        while (true)
        {
          return;
          j = 0;
          break;
          if (EsLog.isLoggable("EsConversationsData", 3))
            Log.d("EsConversationsData", "eventStream for unknown conversation " + str);
          paramRealTimeChatOperationState.addRequest(BunchCommands.getConversationListForConversation(str));
          if (localCursor != null)
            localCursor.close();
          localSQLiteDatabase.endTransaction();
        }
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
    label450: Iterator localIterator = paramEventStreamResponse.getEventList().iterator();
    int i4;
    while (localIterator.hasNext())
    {
      Client.EventStreamResponse.Event localEvent = (Client.EventStreamResponse.Event)localIterator.next();
      long l6 = localEvent.getTimestamp();
      Client.EventStreamResponse.ReceiverState localReceiverState = localEvent.getReceiverState();
      switch (4.$SwitchMap$com$google$wireless$realtimechat$proto$Client$EventStreamResponse$ReceiverState[localReceiverState.ordinal()])
      {
      default:
        if (!EsLog.isLoggable("EsConversationsData", 6))
          break label1402;
        Log.e("EsConversationsData", "ChatMessage's read state could not be determined.");
        break label1402;
        if (EsLog.isLoggable("EsConversationsData", 3))
          Log.d("EsConversationsData", "event timestamp " + l6 + " current conversation " + l1 + " " + l2 + " state " + localEvent.getReceiverState());
        if (localEvent.hasReceipt())
        {
          if (EsLog.isLoggable("EsConversationsData", 3))
            Log.d("EsConversationsData", "Got Receipt");
          m = 1;
          processReceipt$4fede216(localSQLiteDatabase, localLong1.longValue(), localEvent.getReceipt());
        }
        else if (localEvent.hasChatMessage())
        {
          if (EsLog.isLoggable("EsConversationsData", 3))
            Log.d("EsConversationsData", "Got ChatMessage");
          m = 1;
          processMessage(localSQLiteDatabase, paramContext, paramEsAccount, localEvent.getChatMessage(), str, localLong1.longValue(), true, paramRealTimeChatOperationState);
        }
        else if (localEvent.hasMembershipChange())
        {
          if (EsLog.isLoggable("EsConversationsData", 3))
            Log.d("EsConversationsData", "Got MembershipChange");
          n = 1;
          Client.MembershipChange localMembershipChange = localEvent.getMembershipChange();
          processMembershipChange(localSQLiteDatabase, paramContext, paramEsAccount, localLong1.longValue(), localMembershipChange, i4, true, paramRealTimeChatOperationState);
        }
        else if (localEvent.hasGroupConversationRename())
        {
          if (EsLog.isLoggable("EsConversationsData", 3))
            Log.d("EsConversationsData", "Got GroupConversationRename");
          Client.GroupConversationRename localGroupConversationRename = localEvent.getGroupConversationRename();
          processGroupConversationRename(localSQLiteDatabase, paramContext, paramEsAccount, localLong1.longValue(), localGroupConversationRename, false, i4, true, paramRealTimeChatOperationState);
        }
        else if ((!localEvent.hasMigration()) && (localEvent.hasTileEvent()))
        {
          if (EsLog.isLoggable("EsConversationsData", 3))
            Log.d("EsConversationsData", "Got TileEvent");
          Client.TileEvent localTileEvent = localEvent.getTileEvent();
          processTileEvent(localSQLiteDatabase, paramContext, paramEsAccount, localLong1.longValue(), localTileEvent, i4, true, paramRealTimeChatOperationState);
        }
        break;
      case 1:
      case 2:
      case 3:
      case 4:
      }
    }
    int i3;
    label1036: Object localObject3;
    label1043: int i1;
    label1147: int i2;
    if (l2 < l3)
    {
      if (EsLog.isLoggable("EsConversationsData", 3))
        Log.d("EsConversationsData", "newer messages with gap");
      long l5 = localLong1.longValue();
      String[] arrayOfString2 = new String[2];
      arrayOfString2[0] = String.valueOf(l5);
      arrayOfString2[1] = String.valueOf(l3);
      i3 = localSQLiteDatabase.delete("messages", "conversation_id=? AND timestamp<?", arrayOfString2);
      if (EsLog.isLoggable("EsConversationsData", 3))
      {
        Log.d("EsConversationsData", "Latest current event " + l2 + " earliest in eventStream " + l3 + " deleted " + i3 + " older messages");
        break label1432;
        localObject3 = Long.valueOf(l3);
      }
    }
    else
    {
      Long localLong4;
      do
      {
        Long localLong3;
        do
        {
          if (i1 != 0)
          {
            if (EsLog.isLoggable("EsConversationsData", 3))
              Log.d("EsConversationsData", "clearing is_awaiting_older_events");
            localContentValues.put("is_awaiting_older_events", Integer.valueOf(0));
          }
          if (localObject3 != null)
          {
            if (EsLog.isLoggable("EsConversationsData", 3))
              Log.d("EsConversationsData", "updating earliest event timestamp " + localObject3);
            localContentValues.put("earliest_event_timestamp", (Long)localObject3);
          }
          if (l4 > l2)
            localContentValues.put("latest_event_timestamp", Long.valueOf(l4));
          if (localContentValues.size() > 0)
          {
            String[] arrayOfString1 = new String[1];
            arrayOfString1[0] = String.valueOf(localLong1);
            localSQLiteDatabase.update("conversations", localContentValues, "_id=?", arrayOfString1);
          }
          localSQLiteDatabase.setTransactionSuccessful();
          localSQLiteDatabase.endTransaction();
          notifyConversationsChanged(paramContext, paramEsAccount);
          if (m != 0)
            notifyMessagesChanged(paramContext, paramEsAccount, localLong1.longValue());
          if (n == 0)
            break;
          notifyParticipantsChanged(paramContext, paramEsAccount, localLong1.longValue());
          break;
          if ((l3 <= l1) || (l1 == 0L))
            break label1367;
          if (EsLog.isLoggable("EsConversationsData", 3))
            Log.d("EsConversationsData", "newer messages with overlap");
          localLong3 = paramRealTimeChatOperationState.getCurrentConversationRowId();
          i1 = 0;
          localObject3 = null;
        }
        while (localLong3 == null);
        localLong4 = paramRealTimeChatOperationState.getCurrentConversationRowId();
        i1 = 0;
        localObject3 = null;
      }
      while (localLong1 == localLong4);
      i2 = removeExcessMessages(localSQLiteDatabase, localLong1.longValue());
      if (!EsLog.isLoggable("EsConversationsData", 3))
        break label1446;
      Log.d("EsConversationsData", "trim excess messages removed " + i2);
      break label1446;
    }
    while (true)
    {
      localObject3 = Long.valueOf(queryOldestMessageTimestamp(localSQLiteDatabase, localLong1.longValue()));
      break label1043;
      label1367: if (EsLog.isLoggable("EsConversationsData", 3))
        Log.d("EsConversationsData", "older messages");
      i1 = 1;
      Long localLong2 = Long.valueOf(l3);
      localObject3 = localLong2;
      break label1043;
      label1402: i4 = 0;
      break;
      i4 = 3;
      break;
      i4 = 4;
      break;
      i4 = 5;
      break;
      i4 = 5;
      break;
      label1432: i1 = 0;
      if (i3 <= 0)
        break label1036;
      i1 = 1;
      break label1036;
      label1446: i1 = 0;
      if (i2 > 0)
        i1 = 1;
    }
  }

  private static void processGroupConversationRename(SQLiteDatabase paramSQLiteDatabase, Context paramContext, EsAccount paramEsAccount, long paramLong, Client.GroupConversationRename paramGroupConversationRename, boolean paramBoolean1, int paramInt, boolean paramBoolean2, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "processGroupConversationRename conversationId: " + paramGroupConversationRename.getConversationId());
    if (paramGroupConversationRename.hasNewDisplayName())
    {
      String str1 = paramGroupConversationRename.getNewDisplayName();
      if (paramBoolean1)
      {
        if (EsLog.isLoggable("EsConversationsData", 3))
          Log.d("EsConversationsData", "updateConversationName Conversation Row id: " + paramLong);
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("name", str1);
        String[] arrayOfString = new String[1];
        arrayOfString[0] = String.valueOf(paramLong);
        paramSQLiteDatabase.update("conversations", localContentValues, "_id=?", arrayOfString);
        notifyConversationsChanged(paramContext, paramEsAccount);
      }
      long l = paramGroupConversationRename.getTimestamp();
      updateLatestEventTimestamp(paramSQLiteDatabase, paramLong, l);
      String str2 = queryParticipantFullName(paramSQLiteDatabase, paramGroupConversationRename.getSenderId());
      if (str2 == null)
        break label315;
      int i = R.string.realtimechat_conversation_system_message_rename;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = ("<b>" + str2 + "</b>");
      arrayOfObject[1] = ("<i>" + str1 + "</i>");
      insertSystemMessage(paramSQLiteDatabase, paramContext, paramEsAccount, paramLong, paramContext.getString(i, arrayOfObject), paramInt, true, paramGroupConversationRename.getSenderId(), 2, l, paramBoolean2, paramRealTimeChatOperationState);
      if (((paramRealTimeChatOperationState.getCurrentConversationRowId() == null) || (paramRealTimeChatOperationState.getCurrentConversationRowId().longValue() != paramLong)) && (!paramGroupConversationRename.getSenderId().equals(paramEsAccount.getRealTimeChatParticipantId())) && (paramInt != 5))
        paramRealTimeChatOperationState.setShouldTriggerNotifications();
    }
    while (true)
    {
      return;
      label315: if (EsLog.isLoggable("EsConversationsData", 5))
        Log.w("EsConversationsData", "Participant who changed conversation name could not be found");
    }
  }

  private static void processHangoutTile(SQLiteDatabase paramSQLiteDatabase, Context paramContext, EsAccount paramEsAccount, Client.TileEvent paramTileEvent, long paramLong, int paramInt, boolean paramBoolean, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    String str1 = null;
    Iterator localIterator = paramTileEvent.getEventDataList().iterator();
    while (localIterator.hasNext())
    {
      Data.KeyValue localKeyValue = (Data.KeyValue)localIterator.next();
      if (localKeyValue.getKey().equals("AUTHOR_PROFILE_ID"))
        str1 = localKeyValue.getValue();
      else if (EsLog.isLoggable("EsConversationsData", 5))
        Log.w("EsConversationsData", "Got unexpected data in join hangout tile event: " + localKeyValue.getKey() + " - " + localKeyValue.getValue());
    }
    String str2 = queryParticipantFirstName(paramSQLiteDatabase, str1);
    if (str2 != null)
    {
      insertSystemMessage(paramSQLiteDatabase, paramContext, paramEsAccount, paramLong, paramContext.getString(R.string.realtimechat_conversation_system_message_joins_hangout_tile, new Object[] { str2 }), paramInt, true, str1, 6, paramTileEvent.getTimestamp(), paramBoolean, paramRealTimeChatOperationState);
      if (((paramRealTimeChatOperationState.getCurrentConversationRowId() == null) || (paramRealTimeChatOperationState.getCurrentConversationRowId().longValue() != paramLong)) && (!str1.equals(paramEsAccount.getRealTimeChatParticipantId())) && (paramInt != 5))
        paramRealTimeChatOperationState.setShouldTriggerNotifications();
    }
    while (true)
    {
      return;
      if (EsLog.isLoggable("EsConversationsData", 5))
        Log.w("EsConversationsData", "Participant who joined hangout could not be found locally");
    }
  }

  public static void processInviteResponse(Context paramContext, EsAccount paramEsAccount, Client.InviteResponse paramInviteResponse, Client.BunchClientRequest paramBunchClientRequest, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "processInviteResponse participantErrorCount: " + paramInviteResponse.getParticipantErrorCount() + " requestError: " + paramInviteResponse.getRequestError());
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    while (true)
    {
      try
      {
        if (!paramBunchClientRequest.hasInviteRequest())
          break label272;
        String str = paramBunchClientRequest.getInviteRequest().getConversationId();
        Long localLong = queryConversationRowId(localSQLiteDatabase, str);
        if (localLong == null)
        {
          if (EsLog.isLoggable("EsConversationsData", 5))
            Log.w("EsConversationsData", "attempt to process invite response for a nonexistant conversation id [" + str + "]");
          paramRealTimeChatOperationState.addRequest(BunchCommands.getConversationListForConversation(str));
          return;
        }
        long l = 1000L * System.currentTimeMillis();
        Iterator localIterator = paramInviteResponse.getParticipantErrorList().iterator();
        if (localIterator.hasNext())
        {
          processParticipantError(localSQLiteDatabase, paramContext, paramEsAccount, (Client.ParticipantError)localIterator.next(), localLong.longValue(), l, true, paramRealTimeChatOperationState);
          continue;
        }
      }
      finally
      {
        localSQLiteDatabase.endTransaction();
      }
      if (EsLog.isLoggable("EsConversationsData", 3))
        Log.d("EsConversationsData", "NOTIFY ALL PARTICIPANTS");
      paramContext.getContentResolver().notifyChange(EsProvider.appendAccountParameter(EsProvider.CONVERSATIONS_URI, paramEsAccount), null);
      Uri localUri = EsProvider.buildParticipantsUri(paramEsAccount);
      paramContext.getContentResolver().notifyChange(localUri, null);
      notifyConversationsChanged(paramContext, paramEsAccount);
      label272: localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
    }
  }

  public static void processLeaveConversationResponse$6cb3bb58(Context paramContext, EsAccount paramEsAccount, Client.BunchClientRequest paramBunchClientRequest)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "processLeaveConversationResponse " + paramBunchClientRequest.getLeaveConversationRequest().getConversationId());
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    while (true)
    {
      Long localLong;
      int i;
      try
      {
        if (!paramBunchClientRequest.hasLeaveConversationRequest())
          break label285;
        str = paramBunchClientRequest.getLeaveConversationRequest().getConversationId();
        localCursor = null;
      }
      finally
      {
        try
        {
          String str;
          localCursor = localSQLiteDatabase.query("conversations", new String[] { "_id", "is_pending_leave" }, "conversation_id=?", new String[] { str }, null, null, null);
          boolean bool = localCursor.moveToFirst();
          localLong = null;
          i = 0;
          if (bool)
          {
            localLong = Long.valueOf(localCursor.getLong(0));
            int j = localCursor.getInt(1);
            if (j == 1)
              i = 1;
          }
          else
          {
            if (localCursor != null)
              localCursor.close();
            if (localLong != null)
              break label254;
            if (EsLog.isLoggable("EsConversationsData", 5));
            return;
          }
          i = 0;
        }
        finally
        {
          Cursor localCursor;
          if (localCursor != null)
            localCursor.close();
        }
      }
      label254: if (i != 0)
      {
        String[] arrayOfString = new String[1];
        arrayOfString[0] = String.valueOf(localLong);
        localSQLiteDatabase.delete("conversations", "_id=?", arrayOfString);
      }
      label285: localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      notifyConversationsChanged(paramContext, paramEsAccount);
    }
  }

  // ERROR //
  private static void processMembershipChange(SQLiteDatabase paramSQLiteDatabase, Context paramContext, EsAccount paramEsAccount, long paramLong, Client.MembershipChange paramMembershipChange, int paramInt, boolean paramBoolean, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    // Byte code:
    //   0: ldc 35
    //   2: iconst_3
    //   3: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   6: ifeq +44 -> 50
    //   9: ldc 35
    //   11: new 43	java/lang/StringBuilder
    //   14: dup
    //   15: ldc_w 1527
    //   18: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   21: aload 5
    //   23: invokevirtual 922	com/google/wireless/realtimechat/proto/Client$MembershipChange:getConversationId	()Ljava/lang/String;
    //   26: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   29: ldc_w 1529
    //   32: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   35: aload 5
    //   37: invokevirtual 1188	com/google/wireless/realtimechat/proto/Client$MembershipChange:getType	()Lcom/google/wireless/realtimechat/proto/Client$MembershipChange$Type;
    //   40: invokevirtual 277	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   43: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   46: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   49: pop
    //   50: aload 5
    //   52: invokevirtual 925	com/google/wireless/realtimechat/proto/Client$MembershipChange:getTimestamp	()J
    //   55: lstore 9
    //   57: aload 5
    //   59: invokevirtual 1182	com/google/wireless/realtimechat/proto/Client$MembershipChange:getParticipantList	()Ljava/util/List;
    //   62: invokeinterface 644 1 0
    //   67: astore 11
    //   69: aload 11
    //   71: invokeinterface 649 1 0
    //   76: ifeq +694 -> 770
    //   79: aload 11
    //   81: invokeinterface 653 1 0
    //   86: checkcast 253	com/google/wireless/realtimechat/proto/Data$Participant
    //   89: astore 44
    //   91: aload 44
    //   93: invokevirtual 1185	com/google/wireless/realtimechat/proto/Data$Participant:hasParticipantId	()Z
    //   96: ifeq -27 -> 69
    //   99: getstatic 1532	com/google/android/apps/plus/content/EsConversationsData$4:$SwitchMap$com$google$wireless$realtimechat$proto$Client$MembershipChange$Type	[I
    //   102: aload 5
    //   104: invokevirtual 1188	com/google/wireless/realtimechat/proto/Client$MembershipChange:getType	()Lcom/google/wireless/realtimechat/proto/Client$MembershipChange$Type;
    //   107: invokevirtual 1533	com/google/wireless/realtimechat/proto/Client$MembershipChange$Type:ordinal	()I
    //   110: iaload
    //   111: tableswitch	default:+21 -> 132, 1:+42->153, 2:+476->587
    //   133: fload_1
    //   134: iconst_5
    //   135: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   138: ifeq -69 -> 69
    //   141: ldc 35
    //   143: ldc_w 1535
    //   146: invokestatic 280	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   149: pop
    //   150: goto -81 -> 69
    //   153: aload_0
    //   154: ldc_w 849
    //   157: iconst_1
    //   158: anewarray 94	java/lang/String
    //   161: dup
    //   162: iconst_0
    //   163: ldc_w 1537
    //   166: aastore
    //   167: new 43	java/lang/StringBuilder
    //   170: dup
    //   171: ldc_w 627
    //   174: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   177: lload_3
    //   178: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   181: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   184: aconst_null
    //   185: aconst_null
    //   186: aconst_null
    //   187: aconst_null
    //   188: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   191: astore 56
    //   193: aload 56
    //   195: astore 55
    //   197: aload 55
    //   199: invokeinterface 166 1 0
    //   204: ifeq +1149 -> 1353
    //   207: aload 55
    //   209: iconst_0
    //   210: invokeinterface 179 2 0
    //   215: istore 69
    //   217: iload 69
    //   219: istore 57
    //   221: aload 55
    //   223: ifnull +10 -> 233
    //   226: aload 55
    //   228: invokeinterface 182 1 0
    //   233: new 79	android/content/ContentValues
    //   236: dup
    //   237: invokespecial 80	android/content/ContentValues:<init>	()V
    //   240: astore 58
    //   242: aload 58
    //   244: ldc_w 343
    //   247: aload 44
    //   249: invokevirtual 316	com/google/wireless/realtimechat/proto/Data$Participant:getParticipantId	()Ljava/lang/String;
    //   252: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   255: aload 58
    //   257: ldc_w 1539
    //   260: aload 44
    //   262: invokevirtual 1542	com/google/wireless/realtimechat/proto/Data$Participant:getFirstName	()Ljava/lang/String;
    //   265: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   268: aload 58
    //   270: ldc_w 352
    //   273: aload 44
    //   275: invokevirtual 663	com/google/wireless/realtimechat/proto/Data$Participant:getFullName	()Ljava/lang/String;
    //   278: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   281: aload 58
    //   283: ldc_w 389
    //   286: aload 44
    //   288: invokestatic 1544	com/google/android/apps/plus/content/EsConversationsData:convertParticipantType	(Lcom/google/wireless/realtimechat/proto/Data$Participant;)I
    //   291: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   294: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   297: iconst_1
    //   298: anewarray 94	java/lang/String
    //   301: astore 59
    //   303: aload 59
    //   305: iconst_0
    //   306: aload 44
    //   308: invokevirtual 316	com/google/wireless/realtimechat/proto/Data$Participant:getParticipantId	()Ljava/lang/String;
    //   311: aastore
    //   312: aload_0
    //   313: ldc 220
    //   315: aload 58
    //   317: ldc_w 350
    //   320: aload 59
    //   322: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   325: ifne +13 -> 338
    //   328: aload_0
    //   329: ldc 220
    //   331: aconst_null
    //   332: aload 58
    //   334: invokevirtual 359	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   337: pop2
    //   338: aload 58
    //   340: invokevirtual 234	android/content/ContentValues:clear	()V
    //   343: aload 58
    //   345: ldc 152
    //   347: lload_3
    //   348: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   351: invokevirtual 383	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   354: aload 58
    //   356: ldc_w 343
    //   359: aload 44
    //   361: invokevirtual 316	com/google/wireless/realtimechat/proto/Data$Participant:getParticipantId	()Ljava/lang/String;
    //   364: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   367: aload 58
    //   369: ldc_w 1546
    //   372: iconst_1
    //   373: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   376: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   379: aload 58
    //   381: ldc_w 1548
    //   384: iload 57
    //   386: iconst_1
    //   387: iadd
    //   388: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   391: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   394: aload_0
    //   395: ldc_w 849
    //   398: aconst_null
    //   399: aload 58
    //   401: invokevirtual 359	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   404: pop2
    //   405: aload_0
    //   406: aload 5
    //   408: invokevirtual 1195	com/google/wireless/realtimechat/proto/Client$MembershipChange:getSenderId	()Ljava/lang/String;
    //   411: invokestatic 1199	com/google/android/apps/plus/content/EsConversationsData:queryParticipantFullName	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Ljava/lang/String;
    //   414: astore 62
    //   416: getstatic 1204	com/google/android/apps/plus/R$string:realtimechat_conversation_system_message_participant_added	I
    //   419: istore 63
    //   421: iconst_2
    //   422: anewarray 4	java/lang/Object
    //   425: astore 64
    //   427: aload 64
    //   429: iconst_0
    //   430: new 43	java/lang/StringBuilder
    //   433: dup
    //   434: ldc_w 1206
    //   437: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   440: aload 62
    //   442: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   445: ldc_w 1208
    //   448: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   451: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   454: aastore
    //   455: aload 64
    //   457: iconst_1
    //   458: new 43	java/lang/StringBuilder
    //   461: dup
    //   462: ldc_w 1206
    //   465: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   468: aload 44
    //   470: invokevirtual 663	com/google/wireless/realtimechat/proto/Data$Participant:getFullName	()Ljava/lang/String;
    //   473: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   476: ldc_w 1208
    //   479: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   482: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   485: aastore
    //   486: aload_0
    //   487: aload_1
    //   488: aload_2
    //   489: lload_3
    //   490: aload_1
    //   491: iload 63
    //   493: aload 64
    //   495: invokevirtual 1211	android/content/Context:getString	(I[Ljava/lang/Object;)Ljava/lang/String;
    //   498: iload 6
    //   500: iconst_1
    //   501: aload 5
    //   503: invokevirtual 1195	com/google/wireless/realtimechat/proto/Client$MembershipChange:getSenderId	()Ljava/lang/String;
    //   506: iconst_3
    //   507: lload 9
    //   509: iload 7
    //   511: aload 8
    //   513: invokestatic 1469	com/google/android/apps/plus/content/EsConversationsData:insertSystemMessage	(Landroid/database/sqlite/SQLiteDatabase;Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;JLjava/lang/String;IZLjava/lang/String;IJZLcom/google/android/apps/plus/realtimechat/RealTimeChatOperationState;)J
    //   516: pop2
    //   517: aload 8
    //   519: invokevirtual 613	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:getCurrentConversationRowId	()Ljava/lang/Long;
    //   522: ifnull +16 -> 538
    //   525: aload 8
    //   527: invokevirtual 613	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:getCurrentConversationRowId	()Ljava/lang/Long;
    //   530: invokevirtual 200	java/lang/Long:longValue	()J
    //   533: lload_3
    //   534: lcmp
    //   535: ifeq -466 -> 69
    //   538: aload 5
    //   540: invokevirtual 1195	com/google/wireless/realtimechat/proto/Client$MembershipChange:getSenderId	()Ljava/lang/String;
    //   543: aload_2
    //   544: invokevirtual 348	com/google/android/apps/plus/content/EsAccount:getRealTimeChatParticipantId	()Ljava/lang/String;
    //   547: invokevirtual 583	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   550: ifne -481 -> 69
    //   553: iload 6
    //   555: iconst_5
    //   556: if_icmpeq -487 -> 69
    //   559: aload 8
    //   561: invokevirtual 707	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:setShouldTriggerNotifications	()V
    //   564: goto -495 -> 69
    //   567: astore 54
    //   569: aconst_null
    //   570: astore 55
    //   572: aload 55
    //   574: ifnull +10 -> 584
    //   577: aload 55
    //   579: invokeinterface 182 1 0
    //   584: aload 54
    //   586: athrow
    //   587: aload 44
    //   589: invokevirtual 316	com/google/wireless/realtimechat/proto/Data$Participant:getParticipantId	()Ljava/lang/String;
    //   592: astore 45
    //   594: ldc 35
    //   596: iconst_3
    //   597: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   600: ifeq +37 -> 637
    //   603: ldc 35
    //   605: new 43	java/lang/StringBuilder
    //   608: dup
    //   609: ldc_w 1550
    //   612: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   615: lload_3
    //   616: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   619: ldc_w 1552
    //   622: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   625: aload 45
    //   627: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   630: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   633: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   636: pop
    //   637: new 79	android/content/ContentValues
    //   640: dup
    //   641: invokespecial 80	android/content/ContentValues:<init>	()V
    //   644: astore 46
    //   646: aload 46
    //   648: ldc_w 1546
    //   651: iconst_0
    //   652: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   655: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   658: iconst_2
    //   659: anewarray 94	java/lang/String
    //   662: astore 47
    //   664: aload 47
    //   666: iconst_0
    //   667: lload_3
    //   668: invokestatic 97	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   671: aastore
    //   672: aload 47
    //   674: iconst_1
    //   675: aload 45
    //   677: aastore
    //   678: aload_0
    //   679: ldc_w 849
    //   682: aload 46
    //   684: ldc_w 1554
    //   687: aload 47
    //   689: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   692: pop
    //   693: getstatic 1557	com/google/android/apps/plus/R$string:realtimechat_conversation_system_message_participant_left	I
    //   696: istore 49
    //   698: iconst_1
    //   699: anewarray 4	java/lang/Object
    //   702: astore 50
    //   704: aload 50
    //   706: iconst_0
    //   707: new 43	java/lang/StringBuilder
    //   710: dup
    //   711: ldc_w 1206
    //   714: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   717: aload 44
    //   719: invokevirtual 663	com/google/wireless/realtimechat/proto/Data$Participant:getFullName	()Ljava/lang/String;
    //   722: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   725: ldc_w 1208
    //   728: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   731: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   734: aastore
    //   735: aload_0
    //   736: aload_1
    //   737: aload_2
    //   738: lload_3
    //   739: aload_1
    //   740: iload 49
    //   742: aload 50
    //   744: invokevirtual 1211	android/content/Context:getString	(I[Ljava/lang/Object;)Ljava/lang/String;
    //   747: iload 6
    //   749: iconst_0
    //   750: aload 5
    //   752: invokevirtual 1195	com/google/wireless/realtimechat/proto/Client$MembershipChange:getSenderId	()Ljava/lang/String;
    //   755: bipush 7
    //   757: lload 9
    //   759: iload 7
    //   761: aload 8
    //   763: invokestatic 1469	com/google/android/apps/plus/content/EsConversationsData:insertSystemMessage	(Landroid/database/sqlite/SQLiteDatabase;Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;JLjava/lang/String;IZLjava/lang/String;IJZLcom/google/android/apps/plus/realtimechat/RealTimeChatOperationState;)J
    //   766: pop2
    //   767: goto -698 -> 69
    //   770: ldc 35
    //   772: iconst_3
    //   773: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   776: ifeq +12 -> 788
    //   779: ldc 35
    //   781: ldc_w 1559
    //   784: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   787: pop
    //   788: aconst_null
    //   789: astore 12
    //   791: lconst_0
    //   792: lstore 13
    //   794: iconst_3
    //   795: anewarray 94	java/lang/String
    //   798: dup
    //   799: iconst_0
    //   800: ldc_w 505
    //   803: aastore
    //   804: dup
    //   805: iconst_1
    //   806: ldc_w 674
    //   809: aastore
    //   810: dup
    //   811: iconst_2
    //   812: ldc_w 471
    //   815: aastore
    //   816: astore 15
    //   818: iconst_1
    //   819: anewarray 94	java/lang/String
    //   822: astore 17
    //   824: aload 17
    //   826: iconst_0
    //   827: lload_3
    //   828: invokestatic 97	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   831: aastore
    //   832: aload_0
    //   833: ldc 99
    //   835: aload 15
    //   837: ldc 101
    //   839: aload 17
    //   841: aconst_null
    //   842: aconst_null
    //   843: aconst_null
    //   844: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   847: astore 12
    //   849: aload 12
    //   851: invokeinterface 166 1 0
    //   856: istore 18
    //   858: iconst_0
    //   859: istore 19
    //   861: iload 18
    //   863: ifeq +42 -> 905
    //   866: aload 12
    //   868: iconst_0
    //   869: invokeinterface 1126 2 0
    //   874: ifne +245 -> 1119
    //   877: aload 12
    //   879: iconst_0
    //   880: invokeinterface 179 2 0
    //   885: ifeq +234 -> 1119
    //   888: iconst_1
    //   889: istore 19
    //   891: aload 12
    //   893: iconst_1
    //   894: invokeinterface 170 2 0
    //   899: lstore 41
    //   901: lload 41
    //   903: lstore 13
    //   905: aload 12
    //   907: ifnull +10 -> 917
    //   910: aload 12
    //   912: invokeinterface 182 1 0
    //   917: new 43	java/lang/StringBuilder
    //   920: dup
    //   921: invokespecial 1560	java/lang/StringBuilder:<init>	()V
    //   924: astore 20
    //   926: new 43	java/lang/StringBuilder
    //   929: dup
    //   930: invokespecial 1560	java/lang/StringBuilder:<init>	()V
    //   933: astore 21
    //   935: aconst_null
    //   936: astore 22
    //   938: iconst_2
    //   939: anewarray 94	java/lang/String
    //   942: dup
    //   943: iconst_0
    //   944: ldc_w 343
    //   947: aastore
    //   948: dup
    //   949: iconst_1
    //   950: ldc_w 1539
    //   953: aastore
    //   954: astore 23
    //   956: iconst_1
    //   957: anewarray 94	java/lang/String
    //   960: astore 25
    //   962: aload 25
    //   964: iconst_0
    //   965: lload_3
    //   966: invokestatic 97	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   969: aastore
    //   970: aload_0
    //   971: ldc_w 625
    //   974: aload 23
    //   976: ldc_w 1562
    //   979: aload 25
    //   981: aconst_null
    //   982: aconst_null
    //   983: ldc_w 1564
    //   986: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   989: astore 22
    //   991: iconst_1
    //   992: istore 26
    //   994: iconst_0
    //   995: istore 27
    //   997: aload 22
    //   999: invokeinterface 634 1 0
    //   1004: ifeq +138 -> 1142
    //   1007: aload 22
    //   1009: iconst_0
    //   1010: invokeinterface 454 2 0
    //   1015: astore 35
    //   1017: iload 27
    //   1019: iconst_5
    //   1020: if_icmpge +27 -> 1047
    //   1023: iload 27
    //   1025: ifle +11 -> 1036
    //   1028: aload 21
    //   1030: bipush 124
    //   1032: invokevirtual 1567	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   1035: pop
    //   1036: aload 21
    //   1038: aload 35
    //   1040: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1043: pop
    //   1044: iinc 27 1
    //   1047: aload 35
    //   1049: aload_2
    //   1050: invokevirtual 348	com/google/android/apps/plus/content/EsAccount:getRealTimeChatParticipantId	()Ljava/lang/String;
    //   1053: invokevirtual 583	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1056: ifne -59 -> 997
    //   1059: iload 26
    //   1061: ifne +12 -> 1073
    //   1064: aload 20
    //   1066: ldc_w 1569
    //   1069: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1072: pop
    //   1073: aload 22
    //   1075: iconst_1
    //   1076: invokeinterface 454 2 0
    //   1081: astore 36
    //   1083: aload 36
    //   1085: ifnull -88 -> 997
    //   1088: aload 36
    //   1090: ldc_w 1177
    //   1093: invokevirtual 583	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1096: ifne -99 -> 997
    //   1099: aload 20
    //   1101: aload 22
    //   1103: iconst_1
    //   1104: invokeinterface 454 2 0
    //   1109: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1112: pop
    //   1113: iconst_0
    //   1114: istore 26
    //   1116: goto -119 -> 997
    //   1119: iconst_0
    //   1120: istore 19
    //   1122: goto -231 -> 891
    //   1125: astore 16
    //   1127: aload 12
    //   1129: ifnull +10 -> 1139
    //   1132: aload 12
    //   1134: invokeinterface 182 1 0
    //   1139: aload 16
    //   1141: athrow
    //   1142: aload 22
    //   1144: ifnull +10 -> 1154
    //   1147: aload 22
    //   1149: invokeinterface 182 1 0
    //   1154: aload 20
    //   1156: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1159: astore 28
    //   1161: aload 21
    //   1163: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1166: astore 29
    //   1168: ldc 35
    //   1170: iconst_3
    //   1171: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   1174: ifeq +27 -> 1201
    //   1177: ldc 35
    //   1179: new 43	java/lang/StringBuilder
    //   1182: dup
    //   1183: ldc_w 1571
    //   1186: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1189: aload 28
    //   1191: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1194: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1197: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   1200: pop
    //   1201: ldc 35
    //   1203: iconst_3
    //   1204: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   1207: ifeq +27 -> 1234
    //   1210: ldc 35
    //   1212: new 43	java/lang/StringBuilder
    //   1215: dup
    //   1216: ldc_w 1573
    //   1219: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1222: aload 29
    //   1224: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1227: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1230: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   1233: pop
    //   1234: new 79	android/content/ContentValues
    //   1237: dup
    //   1238: invokespecial 80	android/content/ContentValues:<init>	()V
    //   1241: astore 30
    //   1243: aload 30
    //   1245: ldc_w 550
    //   1248: aload 28
    //   1250: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   1253: aload 30
    //   1255: ldc_w 1575
    //   1258: aload 29
    //   1260: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   1263: aload 5
    //   1265: invokevirtual 1578	com/google/wireless/realtimechat/proto/Client$MembershipChange:hasTimestamp	()Z
    //   1268: ifeq +35 -> 1303
    //   1271: aload 5
    //   1273: invokevirtual 925	com/google/wireless/realtimechat/proto/Client$MembershipChange:getTimestamp	()J
    //   1276: lload 13
    //   1278: lcmp
    //   1279: ifle +24 -> 1303
    //   1282: iload 19
    //   1284: ifne +19 -> 1303
    //   1287: aload 30
    //   1289: ldc_w 674
    //   1292: aload 5
    //   1294: invokevirtual 925	com/google/wireless/realtimechat/proto/Client$MembershipChange:getTimestamp	()J
    //   1297: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   1300: invokevirtual 383	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   1303: iconst_1
    //   1304: anewarray 94	java/lang/String
    //   1307: astore 31
    //   1309: aload 31
    //   1311: iconst_0
    //   1312: lload_3
    //   1313: invokestatic 97	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   1316: aastore
    //   1317: aload_0
    //   1318: ldc 99
    //   1320: aload 30
    //   1322: ldc 101
    //   1324: aload 31
    //   1326: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   1329: pop
    //   1330: return
    //   1331: astore 24
    //   1333: aload 22
    //   1335: ifnull +10 -> 1345
    //   1338: aload 22
    //   1340: invokeinterface 182 1 0
    //   1345: aload 24
    //   1347: athrow
    //   1348: astore 54
    //   1350: goto -778 -> 572
    //   1353: iconst_0
    //   1354: istore 57
    //   1356: goto -1135 -> 221
    //
    // Exception table:
    //   from	to	target	type
    //   153	193	567	finally
    //   794	901	1125	finally
    //   938	1113	1331	finally
    //   197	217	1348	finally
  }

  // ERROR //
  private static void processMessage(SQLiteDatabase paramSQLiteDatabase, Context paramContext, final EsAccount paramEsAccount, Client.ChatMessage paramChatMessage, String paramString, long paramLong, boolean paramBoolean, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    // Byte code:
    //   0: aload_3
    //   1: invokevirtual 1581	com/google/wireless/realtimechat/proto/Client$ChatMessage:getContentCount	()I
    //   4: ifle +358 -> 362
    //   7: aload_3
    //   8: iconst_0
    //   9: invokevirtual 1585	com/google/wireless/realtimechat/proto/Client$ChatMessage:getContent	(I)Lcom/google/wireless/realtimechat/proto/Data$Content;
    //   12: invokevirtual 1590	com/google/wireless/realtimechat/proto/Data$Content:getText	()Ljava/lang/String;
    //   15: astore 9
    //   17: aload_3
    //   18: invokestatic 905	com/google/android/apps/plus/content/EsConversationsData:determineMessageState	(Lcom/google/wireless/realtimechat/proto/Client$ChatMessage;)I
    //   21: istore 10
    //   23: ldc 35
    //   25: iconst_3
    //   26: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   29: ifeq +51 -> 80
    //   32: ldc 35
    //   34: new 43	java/lang/StringBuilder
    //   37: dup
    //   38: ldc_w 1592
    //   41: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   44: aload 4
    //   46: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: ldc_w 1594
    //   52: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   55: iload 10
    //   57: invokevirtual 564	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   60: ldc_w 575
    //   63: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: aload_3
    //   67: invokevirtual 887	com/google/wireless/realtimechat/proto/Client$ChatMessage:getTimestamp	()J
    //   70: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   73: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   76: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   79: pop
    //   80: aload_3
    //   81: invokevirtual 887	com/google/wireless/realtimechat/proto/Client$ChatMessage:getTimestamp	()J
    //   84: lstore 11
    //   86: aload_3
    //   87: invokevirtual 1595	com/google/wireless/realtimechat/proto/Client$ChatMessage:hasMessageClientId	()Z
    //   90: ifeq +143 -> 233
    //   93: aload_3
    //   94: invokevirtual 899	com/google/wireless/realtimechat/proto/Client$ChatMessage:getMessageClientId	()Ljava/lang/String;
    //   97: astore 49
    //   99: ldc 35
    //   101: iconst_3
    //   102: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   105: ifeq +38 -> 143
    //   108: ldc 35
    //   110: new 43	java/lang/StringBuilder
    //   113: dup
    //   114: ldc_w 1597
    //   117: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   120: aload 49
    //   122: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   125: ldc_w 1599
    //   128: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   131: lload 11
    //   133: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   136: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   139: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   142: pop
    //   143: aload 49
    //   145: ifnull +88 -> 233
    //   148: new 79	android/content/ContentValues
    //   151: dup
    //   152: invokespecial 80	android/content/ContentValues:<init>	()V
    //   155: astore 50
    //   157: aload 50
    //   159: ldc_w 391
    //   162: lload 11
    //   164: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   167: invokevirtual 383	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   170: aload_0
    //   171: aload 4
    //   173: invokestatic 781	com/google/android/apps/plus/content/EsConversationsData:queryConversationRowId	(Landroid/database/sqlite/SQLiteDatabase;Ljava/lang/String;)Ljava/lang/Long;
    //   176: astore 51
    //   178: aload 51
    //   180: ifnonnull +190 -> 370
    //   183: ldc 35
    //   185: iconst_5
    //   186: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   189: ifeq +44 -> 233
    //   192: ldc 35
    //   194: new 43	java/lang/StringBuilder
    //   197: dup
    //   198: ldc_w 1601
    //   201: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   204: lload 11
    //   206: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   209: ldc_w 1603
    //   212: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   215: aload 4
    //   217: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   220: ldc_w 891
    //   223: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   226: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   229: invokestatic 280	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   232: pop
    //   233: aconst_null
    //   234: astore 13
    //   236: iconst_1
    //   237: anewarray 94	java/lang/String
    //   240: dup
    //   241: iconst_0
    //   242: ldc_w 1091
    //   245: aastore
    //   246: astore 14
    //   248: iconst_1
    //   249: anewarray 94	java/lang/String
    //   252: astore 16
    //   254: aload 16
    //   256: iconst_0
    //   257: aload_3
    //   258: invokevirtual 908	com/google/wireless/realtimechat/proto/Client$ChatMessage:getSenderId	()Ljava/lang/String;
    //   261: aastore
    //   262: aload_0
    //   263: ldc 220
    //   265: aload 14
    //   267: ldc_w 350
    //   270: aload 16
    //   272: aconst_null
    //   273: aconst_null
    //   274: aconst_null
    //   275: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   278: astore 13
    //   280: aload 13
    //   282: invokeinterface 166 1 0
    //   287: ifne +124 -> 411
    //   290: ldc 35
    //   292: iconst_3
    //   293: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   296: ifeq +48 -> 344
    //   299: ldc 35
    //   301: new 43	java/lang/StringBuilder
    //   304: dup
    //   305: ldc_w 1605
    //   308: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   311: aload_3
    //   312: invokevirtual 887	com/google/wireless/realtimechat/proto/Client$ChatMessage:getTimestamp	()J
    //   315: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   318: ldc_w 1607
    //   321: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   324: aload_3
    //   325: invokevirtual 908	com/google/wireless/realtimechat/proto/Client$ChatMessage:getSenderId	()Ljava/lang/String;
    //   328: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   331: ldc_w 891
    //   334: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   337: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   340: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   343: pop
    //   344: aload 8
    //   346: aload 4
    //   348: invokestatic 894	com/google/android/apps/plus/realtimechat/BunchCommands:getConversationListForConversation	(Ljava/lang/String;)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   351: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   354: aload 13
    //   356: invokeinterface 182 1 0
    //   361: return
    //   362: ldc_w 1177
    //   365: astore 9
    //   367: goto -350 -> 17
    //   370: iconst_2
    //   371: anewarray 94	java/lang/String
    //   374: astore 52
    //   376: aload 52
    //   378: iconst_0
    //   379: aload 49
    //   381: invokestatic 408	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   384: aastore
    //   385: aload 52
    //   387: iconst_1
    //   388: aload 51
    //   390: invokestatic 408	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   393: aastore
    //   394: aload_0
    //   395: ldc 150
    //   397: aload 50
    //   399: ldc_w 1089
    //   402: aload 52
    //   404: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   407: pop
    //   408: goto -175 -> 233
    //   411: aload 13
    //   413: invokeinterface 182 1 0
    //   418: new 79	android/content/ContentValues
    //   421: dup
    //   422: invokespecial 80	android/content/ContentValues:<init>	()V
    //   425: astore 17
    //   427: aload_3
    //   428: iconst_0
    //   429: invokevirtual 1585	com/google/wireless/realtimechat/proto/Client$ChatMessage:getContent	(I)Lcom/google/wireless/realtimechat/proto/Data$Content;
    //   432: invokevirtual 1610	com/google/wireless/realtimechat/proto/Data$Content:hasPhotoMetadata	()Z
    //   435: istore 18
    //   437: aconst_null
    //   438: astore 19
    //   440: iload 18
    //   442: ifeq +26 -> 468
    //   445: aload_3
    //   446: iconst_0
    //   447: invokevirtual 1585	com/google/wireless/realtimechat/proto/Client$ChatMessage:getContent	(I)Lcom/google/wireless/realtimechat/proto/Data$Content;
    //   450: invokevirtual 1614	com/google/wireless/realtimechat/proto/Data$Content:getPhotoMetadata	()Lcom/google/wireless/realtimechat/proto/Data$PhotoMetadata;
    //   453: invokevirtual 1619	com/google/wireless/realtimechat/proto/Data$PhotoMetadata:getUrl	()Ljava/lang/String;
    //   456: astore 19
    //   458: aload 17
    //   460: ldc_w 451
    //   463: aload 19
    //   465: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   468: aload 17
    //   470: ldc 152
    //   472: lload 5
    //   474: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   477: invokevirtual 383	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   480: aload 17
    //   482: ldc_w 385
    //   485: aload_3
    //   486: invokevirtual 908	com/google/wireless/realtimechat/proto/Client$ChatMessage:getSenderId	()Ljava/lang/String;
    //   489: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   492: aload 17
    //   494: ldc_w 387
    //   497: aload 9
    //   499: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   502: aload 17
    //   504: ldc 154
    //   506: iload 10
    //   508: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   511: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   514: aload 17
    //   516: ldc_w 389
    //   519: iconst_1
    //   520: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   523: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   526: aload 17
    //   528: ldc_w 391
    //   531: lload 11
    //   533: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   536: invokevirtual 383	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   539: aload_3
    //   540: invokevirtual 1620	com/google/wireless/realtimechat/proto/Client$ChatMessage:hasTimestamp	()Z
    //   543: istore 20
    //   545: aconst_null
    //   546: astore 21
    //   548: iload 20
    //   550: ifeq +15 -> 565
    //   553: aload_0
    //   554: lload 5
    //   556: aload_3
    //   557: invokevirtual 887	com/google/wireless/realtimechat/proto/Client$ChatMessage:getTimestamp	()J
    //   560: invokestatic 579	com/google/android/apps/plus/content/EsConversationsData:queryMessageRowId	(Landroid/database/sqlite/SQLiteDatabase;JJ)Ljava/lang/Long;
    //   563: astore 21
    //   565: ldc 35
    //   567: iconst_3
    //   568: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   571: ifeq +38 -> 609
    //   574: ldc 35
    //   576: new 43	java/lang/StringBuilder
    //   579: dup
    //   580: ldc_w 1622
    //   583: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   586: lload 5
    //   588: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   591: ldc_w 1624
    //   594: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   597: aload 21
    //   599: invokevirtual 277	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   602: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   605: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   608: pop
    //   609: iconst_1
    //   610: istore 22
    //   612: aload 21
    //   614: ifnull +515 -> 1129
    //   617: ldc 35
    //   619: iconst_3
    //   620: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   623: ifeq +12 -> 635
    //   626: ldc 35
    //   628: ldc_w 1626
    //   631: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   634: pop
    //   635: iconst_1
    //   636: anewarray 94	java/lang/String
    //   639: astore 44
    //   641: aload 44
    //   643: iconst_0
    //   644: aload 21
    //   646: invokestatic 408	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   649: aastore
    //   650: aload_0
    //   651: ldc 150
    //   653: aload 17
    //   655: ldc 101
    //   657: aload 44
    //   659: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   662: pop
    //   663: iconst_0
    //   664: istore 22
    //   666: lconst_0
    //   667: lstore 25
    //   669: lconst_0
    //   670: lstore 27
    //   672: aconst_null
    //   673: astore 29
    //   675: iconst_4
    //   676: anewarray 94	java/lang/String
    //   679: dup
    //   680: iconst_0
    //   681: ldc_w 505
    //   684: aastore
    //   685: dup
    //   686: iconst_1
    //   687: ldc_w 397
    //   690: aastore
    //   691: dup
    //   692: iconst_2
    //   693: ldc_w 676
    //   696: aastore
    //   697: dup
    //   698: iconst_3
    //   699: ldc_w 471
    //   702: aastore
    //   703: astore 30
    //   705: iconst_1
    //   706: anewarray 94	java/lang/String
    //   709: astore 32
    //   711: aload 32
    //   713: iconst_0
    //   714: lload 5
    //   716: invokestatic 97	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   719: aastore
    //   720: aload_0
    //   721: ldc 99
    //   723: aload 30
    //   725: ldc 101
    //   727: aload 32
    //   729: aconst_null
    //   730: aconst_null
    //   731: aconst_null
    //   732: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   735: astore 29
    //   737: aload 29
    //   739: invokeinterface 166 1 0
    //   744: istore 33
    //   746: iconst_0
    //   747: istore 34
    //   749: iconst_0
    //   750: istore 35
    //   752: iload 33
    //   754: ifeq +77 -> 831
    //   757: aload 29
    //   759: iconst_0
    //   760: invokeinterface 1126 2 0
    //   765: ifne +564 -> 1329
    //   768: aload 29
    //   770: iconst_0
    //   771: invokeinterface 179 2 0
    //   776: ifeq +553 -> 1329
    //   779: iconst_1
    //   780: istore 34
    //   782: aload 29
    //   784: iconst_1
    //   785: invokeinterface 170 2 0
    //   790: lstore 25
    //   792: aload 29
    //   794: iconst_2
    //   795: invokeinterface 170 2 0
    //   800: lstore 27
    //   802: aload 29
    //   804: iconst_3
    //   805: invokeinterface 1126 2 0
    //   810: ifne +525 -> 1335
    //   813: aload 29
    //   815: iconst_3
    //   816: invokeinterface 179 2 0
    //   821: istore 42
    //   823: iload 42
    //   825: ifeq +510 -> 1335
    //   828: iconst_1
    //   829: istore 35
    //   831: aload 29
    //   833: ifnull +10 -> 843
    //   836: aload 29
    //   838: invokeinterface 182 1 0
    //   843: ldc 35
    //   845: iconst_3
    //   846: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   849: ifeq +38 -> 887
    //   852: ldc 35
    //   854: new 43	java/lang/StringBuilder
    //   857: dup
    //   858: ldc_w 587
    //   861: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   864: lload 11
    //   866: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   869: ldc_w 589
    //   872: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   875: lload 25
    //   877: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   880: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   883: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   886: pop
    //   887: lload 11
    //   889: lload 25
    //   891: lcmp
    //   892: iflt -531 -> 361
    //   895: ldc 35
    //   897: iconst_3
    //   898: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   901: ifeq +12 -> 913
    //   904: ldc 35
    //   906: ldc_w 591
    //   909: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   912: pop
    //   913: aload 17
    //   915: invokevirtual 234	android/content/ContentValues:clear	()V
    //   918: iload 22
    //   920: ifeq +15 -> 935
    //   923: aload 17
    //   925: ldc_w 395
    //   928: iconst_1
    //   929: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   932: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   935: aload 17
    //   937: ldc_w 401
    //   940: aload 9
    //   942: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   945: aload 17
    //   947: ldc_w 403
    //   950: aload 19
    //   952: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   955: aload 17
    //   957: ldc_w 399
    //   960: aload_3
    //   961: invokevirtual 908	com/google/wireless/realtimechat/proto/Client$ChatMessage:getSenderId	()Ljava/lang/String;
    //   964: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   967: aload 17
    //   969: ldc_w 397
    //   972: lload 11
    //   974: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   977: invokevirtual 383	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   980: aload 17
    //   982: ldc_w 405
    //   985: iconst_1
    //   986: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   989: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   992: iload 34
    //   994: ifne +364 -> 1358
    //   997: ldc 35
    //   999: iconst_3
    //   1000: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   1003: ifeq +12 -> 1015
    //   1006: ldc 35
    //   1008: ldc_w 1628
    //   1011: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   1014: pop
    //   1015: aload 17
    //   1017: ldc_w 674
    //   1020: lload 11
    //   1022: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   1025: invokevirtual 383	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   1028: iconst_1
    //   1029: anewarray 94	java/lang/String
    //   1032: astore 36
    //   1034: aload 36
    //   1036: iconst_0
    //   1037: lload 5
    //   1039: invokestatic 97	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   1042: aastore
    //   1043: aload_0
    //   1044: ldc 99
    //   1046: aload 17
    //   1048: ldc 101
    //   1050: aload 36
    //   1052: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   1055: pop
    //   1056: aload_3
    //   1057: invokevirtual 908	com/google/wireless/realtimechat/proto/Client$ChatMessage:getSenderId	()Ljava/lang/String;
    //   1060: aload_2
    //   1061: invokevirtual 348	com/google/android/apps/plus/content/EsAccount:getRealTimeChatParticipantId	()Ljava/lang/String;
    //   1064: invokevirtual 583	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1067: ifne -706 -> 361
    //   1070: aload 8
    //   1072: invokevirtual 613	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:getCurrentConversationRowId	()Ljava/lang/Long;
    //   1075: ifnull +17 -> 1092
    //   1078: lload 5
    //   1080: aload 8
    //   1082: invokevirtual 613	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:getCurrentConversationRowId	()Ljava/lang/Long;
    //   1085: invokevirtual 200	java/lang/Long:longValue	()J
    //   1088: lcmp
    //   1089: ifeq -728 -> 361
    //   1092: iload 35
    //   1094: ifne -733 -> 361
    //   1097: iload 10
    //   1099: iconst_4
    //   1100: if_icmpeq +9 -> 1109
    //   1103: iload 10
    //   1105: iconst_3
    //   1106: if_icmpne -745 -> 361
    //   1109: aload 8
    //   1111: invokevirtual 707	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:setShouldTriggerNotifications	()V
    //   1114: goto -753 -> 361
    //   1117: astore 15
    //   1119: aload 13
    //   1121: invokeinterface 182 1 0
    //   1126: aload 15
    //   1128: athrow
    //   1129: ldc 35
    //   1131: iconst_3
    //   1132: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   1135: ifeq +12 -> 1147
    //   1138: ldc 35
    //   1140: ldc_w 1630
    //   1143: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   1146: pop
    //   1147: aload 8
    //   1149: invokevirtual 613	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:getCurrentConversationRowId	()Ljava/lang/Long;
    //   1152: ifnull +17 -> 1169
    //   1155: lload 5
    //   1157: aload 8
    //   1159: invokevirtual 613	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:getCurrentConversationRowId	()Ljava/lang/Long;
    //   1162: invokevirtual 200	java/lang/Long:longValue	()J
    //   1165: lcmp
    //   1166: ifeq +23 -> 1189
    //   1169: iload 10
    //   1171: iconst_5
    //   1172: if_icmpeq +17 -> 1189
    //   1175: aload_2
    //   1176: invokevirtual 348	com/google/android/apps/plus/content/EsAccount:getRealTimeChatParticipantId	()Ljava/lang/String;
    //   1179: aload_3
    //   1180: invokevirtual 908	com/google/wireless/realtimechat/proto/Client$ChatMessage:getSenderId	()Ljava/lang/String;
    //   1183: invokevirtual 583	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1186: ifeq +128 -> 1314
    //   1189: aload 17
    //   1191: ldc_w 393
    //   1194: iconst_1
    //   1195: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1198: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   1201: aload_2
    //   1202: invokevirtual 348	com/google/android/apps/plus/content/EsAccount:getRealTimeChatParticipantId	()Ljava/lang/String;
    //   1205: aload_3
    //   1206: invokevirtual 908	com/google/wireless/realtimechat/proto/Client$ChatMessage:getSenderId	()Ljava/lang/String;
    //   1209: invokevirtual 583	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1212: ifne +70 -> 1282
    //   1215: iload 7
    //   1217: ifeq +65 -> 1282
    //   1220: iload 10
    //   1222: iconst_3
    //   1223: if_icmpne +59 -> 1282
    //   1226: aload 8
    //   1228: aload 4
    //   1230: aload_3
    //   1231: invokevirtual 887	com/google/wireless/realtimechat/proto/Client$ChatMessage:getTimestamp	()J
    //   1234: getstatic 605	com/google/wireless/realtimechat/proto/Client$Receipt$Type:DELIVERED	Lcom/google/wireless/realtimechat/proto/Client$Receipt$Type;
    //   1237: invokestatic 609	com/google/android/apps/plus/realtimechat/BunchCommands:sendReceipt	(Ljava/lang/String;JLcom/google/wireless/realtimechat/proto/Client$Receipt$Type;)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   1240: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   1243: aload 8
    //   1245: invokevirtual 613	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:getCurrentConversationRowId	()Ljava/lang/Long;
    //   1248: ifnull +34 -> 1282
    //   1251: aload 8
    //   1253: invokevirtual 613	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:getCurrentConversationRowId	()Ljava/lang/Long;
    //   1256: invokevirtual 200	java/lang/Long:longValue	()J
    //   1259: lload 5
    //   1261: lcmp
    //   1262: ifne +20 -> 1282
    //   1265: aload 8
    //   1267: aload 4
    //   1269: aload_3
    //   1270: invokevirtual 887	com/google/wireless/realtimechat/proto/Client$ChatMessage:getTimestamp	()J
    //   1273: getstatic 616	com/google/wireless/realtimechat/proto/Client$Receipt$Type:READ	Lcom/google/wireless/realtimechat/proto/Client$Receipt$Type;
    //   1276: invokestatic 609	com/google/android/apps/plus/realtimechat/BunchCommands:sendReceipt	(Ljava/lang/String;JLcom/google/wireless/realtimechat/proto/Client$Receipt$Type;)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   1279: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   1282: getstatic 25	com/google/android/apps/plus/content/EsConversationsData:sHandler	Landroid/os/Handler;
    //   1285: new 1632	com/google/android/apps/plus/content/EsConversationsData$2
    //   1288: dup
    //   1289: aload_1
    //   1290: aload_2
    //   1291: invokespecial 1633	com/google/android/apps/plus/content/EsConversationsData$2:<init>	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)V
    //   1294: invokevirtual 599	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   1297: pop
    //   1298: aload_0
    //   1299: ldc 150
    //   1301: aconst_null
    //   1302: aload 17
    //   1304: invokevirtual 359	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   1307: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   1310: pop
    //   1311: goto -645 -> 666
    //   1314: aload 17
    //   1316: ldc_w 393
    //   1319: iconst_0
    //   1320: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   1323: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   1326: goto -125 -> 1201
    //   1329: iconst_0
    //   1330: istore 34
    //   1332: goto -550 -> 782
    //   1335: iconst_0
    //   1336: istore 35
    //   1338: goto -507 -> 831
    //   1341: astore 31
    //   1343: aload 29
    //   1345: ifnull +10 -> 1355
    //   1348: aload 29
    //   1350: invokeinterface 182 1 0
    //   1355: aload 31
    //   1357: athrow
    //   1358: lload 27
    //   1360: lconst_0
    //   1361: lcmp
    //   1362: ifne -334 -> 1028
    //   1365: ldc 35
    //   1367: iconst_3
    //   1368: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   1371: ifeq +27 -> 1398
    //   1374: ldc 35
    //   1376: new 43	java/lang/StringBuilder
    //   1379: dup
    //   1380: ldc_w 1635
    //   1383: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   1386: lload 11
    //   1388: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1391: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1394: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   1397: pop
    //   1398: aload 17
    //   1400: ldc_w 674
    //   1403: lload 11
    //   1405: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   1408: invokevirtual 383	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   1411: aload 17
    //   1413: ldc_w 676
    //   1416: lload 11
    //   1418: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   1421: invokevirtual 383	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   1424: goto -396 -> 1028
    //
    // Exception table:
    //   from	to	target	type
    //   236	354	1117	finally
    //   675	823	1341	finally
  }

  private static void processParticipantError(SQLiteDatabase paramSQLiteDatabase, Context paramContext, EsAccount paramEsAccount, Client.ParticipantError paramParticipantError, long paramLong1, long paramLong2, boolean paramBoolean, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "processParticipantError status: " + paramParticipantError.getStatus().name());
    if ((!paramParticipantError.hasFullName()) || (!paramParticipantError.hasStatus()))
    {
      if (EsLog.isLoggable("EsConversationsData", 5))
        Log.w("EsConversationsData", "Participant error with no name or status");
      return;
    }
    String str = paramParticipantError.getFullName();
    Data.ResponseStatus localResponseStatus = paramParticipantError.getStatus();
    int i;
    switch (4.$SwitchMap$com$google$wireless$realtimechat$proto$Data$ResponseStatus[localResponseStatus.ordinal()])
    {
    default:
      i = R.string.realtimechat_conversation_error_message_general;
    case 5:
    case 6:
    case 7:
    case 8:
    }
    while (true)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = ("<b>" + str + "</b>");
      insertSystemMessage(paramSQLiteDatabase, paramContext, paramEsAccount, paramLong1, paramContext.getString(i, arrayOfObject), 5, false, paramEsAccount.getRealTimeChatParticipantId(), 4, paramLong2, true, paramRealTimeChatOperationState);
      break;
      i = R.string.realtimechat_conversation_error_message_invalid_email;
      continue;
      i = R.string.realtimechat_conversation_error_message_invalid_phone_number;
      continue;
      i = R.string.realtimechat_conversation_error_message_invalid_country;
      continue;
      i = R.string.realtimechat_conversation_error_message_sms_invites;
    }
  }

  private static void processPreviewMessage$43824df1(SQLiteDatabase paramSQLiteDatabase, Client.ChatMessage paramChatMessage, String paramString, long paramLong)
  {
    if (paramChatMessage.getContentCount() > 0);
    for (String str1 = paramChatMessage.getContent(0).getText(); ; str1 = "")
    {
      int i = determineMessageState(paramChatMessage);
      if (EsLog.isLoggable("EsConversationsData", 3))
        Log.d("EsConversationsData", "processPreviewMessage conversationId: " + paramString + " state: " + i + " timestamp: " + paramChatMessage.getTimestamp());
      long l = paramChatMessage.getTimestamp();
      boolean bool = paramChatMessage.getContent(0).hasPhotoMetadata();
      String str2 = null;
      if (bool)
        str2 = paramChatMessage.getContent(0).getPhotoMetadata().getUrl();
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("latest_message_text", str1);
      localContentValues.put("latest_message_image_url", str2);
      localContentValues.put("latest_message_author_id", paramChatMessage.getSenderId());
      localContentValues.put("latest_message_timestamp", Long.valueOf(l));
      localContentValues.put("latest_message_type", Integer.valueOf(1));
      String[] arrayOfString = new String[1];
      arrayOfString[0] = String.valueOf(paramLong);
      paramSQLiteDatabase.update("conversations", localContentValues, "_id=?", arrayOfString);
      return;
    }
  }

  private static void processReceipt$4fede216(SQLiteDatabase paramSQLiteDatabase, long paramLong, Client.Receipt paramReceipt)
  {
    boolean bool1 = paramReceipt.hasMessageClientId();
    String str = null;
    if (bool1)
      str = paramReceipt.getMessageClientId();
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "processReceipt Conversation row id: " + paramLong + ", clientMessageId: " + str + ", Type: " + paramReceipt.getType() + ", Timestamp: " + paramReceipt.getMessageTimestamp());
    ContentValues localContentValues = new ContentValues();
    long l = paramReceipt.getMessageTimestamp();
    Cursor localCursor = null;
    int i = 3;
    while (true)
    {
      try
      {
        String[] arrayOfString1 = { "status", "_id" };
        String[] arrayOfString2 = new String[2];
        arrayOfString2[0] = String.valueOf(paramLong);
        arrayOfString2[1] = String.valueOf(l);
        localCursor = paramSQLiteDatabase.query("messages", arrayOfString1, "conversation_id=? AND timestamp=?", arrayOfString2, null, null, null);
        Long localLong1;
        if (localCursor.moveToFirst())
        {
          i = localCursor.getInt(0);
          Long localLong2 = Long.valueOf(localCursor.getLong(1));
          localLong1 = localLong2;
          if (localCursor != null)
            localCursor.close();
        }
        switch (4.$SwitchMap$com$google$wireless$realtimechat$proto$Client$Receipt$Type[paramReceipt.getType().ordinal()])
        {
        default:
          if ((localLong1 != null) && (localContentValues.size() != 0))
            paramSQLiteDatabase.update("messages", localContentValues, "_id=" + localLong1, null);
          return;
          localLong1 = null;
          if (str == null)
            continue;
          localCursor.close();
          String[] arrayOfString3 = { "status" };
          String[] arrayOfString4 = new String[2];
          arrayOfString4[0] = String.valueOf(paramLong);
          arrayOfString4[1] = str;
          localCursor = paramSQLiteDatabase.query("messages", arrayOfString3, "conversation_id=? AND message_id=?", arrayOfString4, null, null, null);
          boolean bool2 = localCursor.moveToFirst();
          localLong1 = null;
          if (!bool2)
            continue;
          i = localCursor.getInt(0);
          localContentValues.put("timestamp", Long.valueOf(l));
          localLong1 = null;
          continue;
        case 1:
        case 2:
        case 3:
        }
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
      if ((i != 4) && (i != 5))
      {
        localContentValues.put("status", Integer.valueOf(3));
        continue;
        if (i != 5)
        {
          localContentValues.put("status", Integer.valueOf(4));
          continue;
          localContentValues.put("status", Integer.valueOf(5));
        }
      }
    }
  }

  public static void processSuggestionsResponse$541cf8e7(Context paramContext, EsAccount paramEsAccount, Client.SuggestionsResponse paramSuggestionsResponse, Client.BunchClientRequest paramBunchClientRequest)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "processSuggestionsResponse");
    if ((paramBunchClientRequest.hasSuggestionsRequest()) && (paramBunchClientRequest.getSuggestionsRequest().getSuggestionsType() != Client.SuggestionsRequest.SuggestionsType.ONLY_NEW))
    {
      if (EsLog.isLoggable("EsConversationsData", 3))
        Log.d("EsConversationsData", "ignoring");
      return;
    }
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    while (true)
    {
      int i;
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
            localSQLiteDatabase.insertWithOnConflict("messenger_suggestions", null, localContentValues, 5);
            j = k;
            continue;
          }
        }
        else
        {
          localSQLiteDatabase.delete("messenger_suggestions", "participant_id NOT IN (" + localStringBuilder.toString() + ")", null);
          localSQLiteDatabase.setTransactionSuccessful();
          localSQLiteDatabase.endTransaction();
          notifySuggestionsChanged(paramContext, paramEsAccount);
        }
      }
      finally
      {
        localSQLiteDatabase.endTransaction();
      }
    }
  }

  private static void processTileEvent(SQLiteDatabase paramSQLiteDatabase, Context paramContext, EsAccount paramEsAccount, long paramLong, Client.TileEvent paramTileEvent, int paramInt, boolean paramBoolean, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    String str = paramTileEvent.getEventType();
    long l = paramTileEvent.getTimestamp();
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "processTileEvent conversation id: " + paramTileEvent.getConversationId() + ", Event Type: " + str + ", Tile Type: " + paramTileEvent.getTileType() + ", Tile Version: " + paramTileEvent.getTileVersion() + ", Timestamp " + l);
    if (str.equals("JOIN_HANGOUT"))
      processHangoutTile(paramSQLiteDatabase, paramContext, paramEsAccount, paramTileEvent, paramLong, paramInt, paramBoolean, paramRealTimeChatOperationState);
    while (true)
    {
      updateLatestEventTimestamp(paramSQLiteDatabase, paramLong, paramTileEvent.getTimestamp());
      return;
      if (EsLog.isLoggable("EsConversationsData", 3))
        Log.w("EsConversationsData", "processTileEvent for unexpected event type: " + str);
    }
  }

  public static void processUserCreationResponse(Context paramContext, EsAccount paramEsAccount, Client.UserCreationResponse paramUserCreationResponse, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "processUserCreationResponse participantId: " + paramUserCreationResponse.getParticipantId());
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      long l = queryLoadConversationsSince(localSQLiteDatabase);
      if (EsLog.isLoggable("EsConversationsData", 3))
        Log.d("EsConversationsData", "requesting conversations changed since " + l);
      paramRealTimeChatOperationState.addRequest(BunchCommands.getConversationList(l));
      paramRealTimeChatOperationState.addRequest(BunchCommands.getUserInfo(paramUserCreationResponse.getParticipantId()));
      paramRealTimeChatOperationState.addRequest(BunchCommands.getOOBSuggestionsRequest());
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("key", "requested_conversations_since");
      localContentValues.put("value", String.valueOf(l));
      localSQLiteDatabase.insertWithOnConflict("realtimechat_metadata", null, localContentValues, 5);
      localSQLiteDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  private static String queryConversationId(SQLiteDatabase paramSQLiteDatabase, long paramLong)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "queryConversationId " + paramLong);
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramLong);
    return DatabaseUtils.stringForQuery(paramSQLiteDatabase, "SELECT conversation_id  FROM conversations WHERE _id=?", arrayOfString);
  }

  private static Long queryConversationRowId(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    Cursor localCursor = null;
    try
    {
      localCursor = paramSQLiteDatabase.query("conversations", new String[] { "_id" }, "conversation_id=?", new String[] { paramString }, null, null, null);
      localCursor.moveToFirst();
      if (!localCursor.isAfterLast())
      {
        Long localLong2 = Long.valueOf(localCursor.getLong(0));
        localLong1 = localLong2;
        return localLong1;
      }
      if (localCursor != null)
        localCursor.close();
      Long localLong1 = null;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
  }

  public static String queryDatastoreVersion(Context paramContext, EsAccount paramEsAccount)
  {
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      String str = queryDatastoreVersion(localSQLiteDatabase);
      localSQLiteDatabase.setTransactionSuccessful();
      return str;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  private static String queryDatastoreVersion(SQLiteDatabase paramSQLiteDatabase)
  {
    Cursor localCursor = null;
    try
    {
      localCursor = paramSQLiteDatabase.query("realtimechat_metadata", new String[] { "value" }, "key = ?", new String[] { "datastore_version" }, null, null, null, null);
      if (localCursor.moveToFirst())
      {
        String str2 = localCursor.getString(0);
        str1 = str2;
        return str1;
      }
      if (localCursor != null)
        localCursor.close();
      String str1 = null;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
  }

  // ERROR //
  private static long queryLoadConversationsSince(SQLiteDatabase paramSQLiteDatabase)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: ldc 245
    //   5: iconst_1
    //   6: anewarray 94	java/lang/String
    //   9: dup
    //   10: iconst_0
    //   11: ldc 243
    //   13: aastore
    //   14: ldc_w 1781
    //   17: iconst_1
    //   18: anewarray 94	java/lang/String
    //   21: dup
    //   22: iconst_0
    //   23: ldc_w 1765
    //   26: aastore
    //   27: aconst_null
    //   28: aconst_null
    //   29: aconst_null
    //   30: aconst_null
    //   31: invokevirtual 1153	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   34: astore_1
    //   35: aload_1
    //   36: invokeinterface 166 1 0
    //   41: istore_3
    //   42: iload_3
    //   43: ifeq +87 -> 130
    //   46: aload_1
    //   47: iconst_0
    //   48: invokeinterface 454 2 0
    //   53: invokestatic 1786	java/lang/Long:valueOf	(Ljava/lang/String;)Ljava/lang/Long;
    //   56: invokevirtual 200	java/lang/Long:longValue	()J
    //   59: lstore 12
    //   61: lload 12
    //   63: lstore 6
    //   65: aload_1
    //   66: ifnull +9 -> 75
    //   69: aload_1
    //   70: invokeinterface 182 1 0
    //   75: lload 6
    //   77: lreturn
    //   78: astore 10
    //   80: ldc 35
    //   82: bipush 6
    //   84: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   87: ifeq +27 -> 114
    //   90: ldc 35
    //   92: new 43	java/lang/StringBuilder
    //   95: dup
    //   96: ldc_w 1788
    //   99: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   102: aload 10
    //   104: invokevirtual 277	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   107: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   110: invokestatic 449	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   113: pop
    //   114: aload_1
    //   115: ifnull +9 -> 124
    //   118: aload_1
    //   119: invokeinterface 182 1 0
    //   124: lconst_0
    //   125: lstore 6
    //   127: goto -52 -> 75
    //   130: aload_1
    //   131: ifnull +9 -> 140
    //   134: aload_1
    //   135: invokeinterface 182 1 0
    //   140: aconst_null
    //   141: astore 4
    //   143: aload_0
    //   144: ldc 99
    //   146: iconst_1
    //   147: anewarray 94	java/lang/String
    //   150: dup
    //   151: iconst_0
    //   152: ldc_w 1790
    //   155: aastore
    //   156: aconst_null
    //   157: aconst_null
    //   158: aconst_null
    //   159: aconst_null
    //   160: aconst_null
    //   161: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   164: astore 4
    //   166: aload 4
    //   168: invokeinterface 166 1 0
    //   173: ifeq +45 -> 218
    //   176: aload 4
    //   178: iconst_0
    //   179: invokeinterface 170 2 0
    //   184: lstore 8
    //   186: lload 8
    //   188: lstore 6
    //   190: aload 4
    //   192: ifnull -117 -> 75
    //   195: aload 4
    //   197: invokeinterface 182 1 0
    //   202: goto -127 -> 75
    //   205: astore_2
    //   206: aload_1
    //   207: ifnull +9 -> 216
    //   210: aload_1
    //   211: invokeinterface 182 1 0
    //   216: aload_2
    //   217: athrow
    //   218: aload 4
    //   220: ifnull +10 -> 230
    //   223: aload 4
    //   225: invokeinterface 182 1 0
    //   230: lconst_0
    //   231: lstore 6
    //   233: goto -158 -> 75
    //   236: astore 5
    //   238: aload 4
    //   240: ifnull +10 -> 250
    //   243: aload 4
    //   245: invokeinterface 182 1 0
    //   250: aload 5
    //   252: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   46	61	78	java/lang/NumberFormatException
    //   2	42	205	finally
    //   46	61	205	finally
    //   80	114	205	finally
    //   143	186	236	finally
  }

  private static Long queryMessageRowId(SQLiteDatabase paramSQLiteDatabase, long paramLong1, long paramLong2)
  {
    Cursor localCursor = null;
    try
    {
      String[] arrayOfString1 = { "_id" };
      String[] arrayOfString2 = new String[2];
      arrayOfString2[0] = String.valueOf(paramLong1);
      arrayOfString2[1] = String.valueOf(paramLong2);
      localCursor = paramSQLiteDatabase.query("messages", arrayOfString1, "conversation_id=? AND timestamp=?", arrayOfString2, null, null, null);
      localCursor.moveToFirst();
      if (!localCursor.isAfterLast())
      {
        Long localLong2 = Long.valueOf(localCursor.getLong(0));
        localLong1 = localLong2;
        return localLong1;
      }
      if (localCursor != null)
        localCursor.close();
      Long localLong1 = null;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
  }

  private static int queryMessageStatus(SQLiteDatabase paramSQLiteDatabase, long paramLong)
  {
    Cursor localCursor = null;
    try
    {
      String[] arrayOfString1 = { "status" };
      String[] arrayOfString2 = new String[1];
      arrayOfString2[0] = String.valueOf(paramLong);
      localCursor = paramSQLiteDatabase.query("messages", arrayOfString1, "_id=?", arrayOfString2, null, null, null);
      localCursor.moveToFirst();
      if (!localCursor.isAfterLast())
      {
        int j = localCursor.getInt(0);
        i = j;
        return i;
      }
      localCursor.close();
      int i = -1;
    }
    finally
    {
      localCursor.close();
    }
  }

  private static long queryOldestMessageTimestamp(SQLiteDatabase paramSQLiteDatabase, long paramLong)
  {
    Cursor localCursor = null;
    try
    {
      String[] arrayOfString1 = { "MIN(timestamp)" };
      String[] arrayOfString2 = new String[1];
      arrayOfString2[0] = String.valueOf(paramLong);
      localCursor = paramSQLiteDatabase.query("messages", arrayOfString1, "conversation_id=?", arrayOfString2, null, null, null);
      if (localCursor.moveToFirst())
      {
        long l2 = localCursor.getLong(0);
        l1 = l2;
        return l1;
      }
      if (localCursor != null)
        localCursor.close();
      long l1 = 0L;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
  }

  private static Long queryOneToOneConversation$51a85815(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    Cursor localCursor = null;
    try
    {
      localCursor = paramSQLiteDatabase.rawQuery("select conversations" + "._id" + ",conversations" + "._id" + " FROM conversations" + ",conversation_participants" + " WHERE conversation_participants" + ".conversation_id" + "=conversations" + "._id" + " AND is_group" + "=0 AND participant_id" + "=?", new String[] { paramString });
      if (localCursor.moveToFirst())
      {
        Long localLong2 = Long.valueOf(localCursor.getLong(0));
        localLong1 = localLong2;
        return localLong1;
      }
      if (localCursor != null)
        localCursor.close();
      Long localLong1 = null;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
  }

  private static String queryParticipantFirstName(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    Object localObject2;
    if (paramString == null)
      localObject2 = null;
    while (true)
    {
      return localObject2;
      Cursor localCursor = null;
      try
      {
        localCursor = paramSQLiteDatabase.query("participants", new String[] { "first_name" }, "participant_id=?", new String[] { paramString }, null, null, null);
        if (localCursor.moveToFirst())
        {
          String str = localCursor.getString(0);
          localObject2 = str;
          if (localCursor == null)
            continue;
          localCursor.close();
          continue;
        }
        if (localCursor != null)
          localCursor.close();
        localObject2 = null;
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
    }
  }

  private static String queryParticipantFullName(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    Cursor localCursor = null;
    try
    {
      localCursor = paramSQLiteDatabase.query("participants", new String[] { "full_name" }, "participant_id=?", new String[] { paramString }, null, null, null);
      if (localCursor.moveToFirst())
      {
        String str2 = localCursor.getString(0);
        str1 = str2;
        return str1;
      }
      if (localCursor != null)
        localCursor.close();
      String str1 = null;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
  }

  public static void removeAllConversationsFromInviterLocally$37a126b9(Context paramContext, EsAccount paramEsAccount, String paramString)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "removeAllConversationsFromInviterLocally");
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      localSQLiteDatabase.delete("conversations", "inviter_id=?", new String[] { paramString });
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      notifyConversationsChanged(paramContext, paramEsAccount);
      return;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  private static int removeExcessMessages(SQLiteDatabase paramSQLiteDatabase, long paramLong)
  {
    long l1 = 0L;
    Cursor localCursor = null;
    try
    {
      String[] arrayOfString1 = { "timestamp" };
      String[] arrayOfString2 = new String[1];
      arrayOfString2[0] = String.valueOf(paramLong);
      localCursor = paramSQLiteDatabase.query("messages", arrayOfString1, "conversation_id= ?", arrayOfString2, null, null, "timestamp DESC", "20,1");
      if (localCursor.moveToFirst())
      {
        long l2 = localCursor.getLong(0);
        l1 = l2;
      }
      localCursor.close();
      String[] arrayOfString3 = new String[2];
      arrayOfString3[0] = String.valueOf(paramLong);
      arrayOfString3[1] = String.valueOf(l1);
      return paramSQLiteDatabase.delete("messages", "conversation_id=? AND timestamp<?", arrayOfString3);
    }
    finally
    {
      localCursor.close();
    }
  }

  // ERROR //
  public static void removeMessageLocally(Context paramContext, EsAccount paramEsAccount, long paramLong)
  {
    // Byte code:
    //   0: ldc 35
    //   2: iconst_3
    //   3: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   6: ifeq +26 -> 32
    //   9: ldc 35
    //   11: new 43	java/lang/StringBuilder
    //   14: dup
    //   15: ldc_w 1831
    //   18: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   21: lload_2
    //   22: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   25: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   28: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   31: pop
    //   32: aload_0
    //   33: aload_1
    //   34: invokestatic 68	com/google/android/apps/plus/content/EsDatabaseHelper:getDatabaseHelper	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)Lcom/google/android/apps/plus/content/EsDatabaseHelper;
    //   37: invokevirtual 72	com/google/android/apps/plus/content/EsDatabaseHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   40: astore 4
    //   42: aload 4
    //   44: invokevirtual 77	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   47: aconst_null
    //   48: astore 5
    //   50: aload 4
    //   52: ldc 150
    //   54: iconst_1
    //   55: anewarray 94	java/lang/String
    //   58: dup
    //   59: iconst_0
    //   60: ldc 152
    //   62: aastore
    //   63: new 43	java/lang/StringBuilder
    //   66: dup
    //   67: ldc 156
    //   69: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   72: lload_2
    //   73: invokestatic 97	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   76: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   79: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   82: aconst_null
    //   83: aconst_null
    //   84: aconst_null
    //   85: aconst_null
    //   86: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   89: astore 5
    //   91: aload 5
    //   93: invokeinterface 166 1 0
    //   98: istore 8
    //   100: aconst_null
    //   101: astore 9
    //   103: iload 8
    //   105: ifeq +20 -> 125
    //   108: aload 5
    //   110: iconst_0
    //   111: invokeinterface 170 2 0
    //   116: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   119: astore 15
    //   121: aload 15
    //   123: astore 9
    //   125: aload 5
    //   127: ifnull +10 -> 137
    //   130: aload 5
    //   132: invokeinterface 182 1 0
    //   137: aload 4
    //   139: ldc 150
    //   141: new 43	java/lang/StringBuilder
    //   144: dup
    //   145: ldc 156
    //   147: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   150: lload_2
    //   151: invokestatic 97	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   154: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   157: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   160: aconst_null
    //   161: invokevirtual 226	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   164: pop
    //   165: aload 4
    //   167: ldc 150
    //   169: iconst_5
    //   170: anewarray 94	java/lang/String
    //   173: dup
    //   174: iconst_0
    //   175: ldc_w 387
    //   178: aastore
    //   179: dup
    //   180: iconst_1
    //   181: ldc_w 451
    //   184: aastore
    //   185: dup
    //   186: iconst_2
    //   187: ldc_w 391
    //   190: aastore
    //   191: dup
    //   192: iconst_3
    //   193: ldc_w 389
    //   196: aastore
    //   197: dup
    //   198: iconst_4
    //   199: ldc_w 385
    //   202: aastore
    //   203: new 43	java/lang/StringBuilder
    //   206: dup
    //   207: ldc_w 627
    //   210: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   213: aload 9
    //   215: invokevirtual 277	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   218: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   221: aconst_null
    //   222: aconst_null
    //   223: aconst_null
    //   224: ldc_w 1826
    //   227: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   230: astore 5
    //   232: aload 5
    //   234: invokeinterface 634 1 0
    //   239: ifeq +38 -> 277
    //   242: aload 5
    //   244: iconst_3
    //   245: invokeinterface 179 2 0
    //   250: istore 14
    //   252: iload 14
    //   254: iconst_2
    //   255: if_icmpeq +22 -> 277
    //   258: iload 14
    //   260: iconst_1
    //   261: if_icmpeq +16 -> 277
    //   264: iload 14
    //   266: bipush 6
    //   268: if_icmpeq +9 -> 277
    //   271: iload 14
    //   273: iconst_3
    //   274: if_icmpne -42 -> 232
    //   277: new 79	android/content/ContentValues
    //   280: dup
    //   281: invokespecial 80	android/content/ContentValues:<init>	()V
    //   284: astore 12
    //   286: aload 5
    //   288: invokeinterface 1778 1 0
    //   293: ifne +187 -> 480
    //   296: aload 12
    //   298: ldc_w 401
    //   301: aload 5
    //   303: iconst_0
    //   304: invokeinterface 454 2 0
    //   309: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   312: aload 12
    //   314: ldc_w 403
    //   317: aload 5
    //   319: iconst_1
    //   320: invokeinterface 454 2 0
    //   325: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   328: aload 12
    //   330: ldc_w 397
    //   333: aload 5
    //   335: iconst_2
    //   336: invokeinterface 170 2 0
    //   341: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   344: invokevirtual 383	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   347: aload 12
    //   349: ldc_w 405
    //   352: aload 5
    //   354: iconst_3
    //   355: invokeinterface 179 2 0
    //   360: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   363: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   366: aload 12
    //   368: ldc_w 399
    //   371: aload 5
    //   373: iconst_4
    //   374: invokeinterface 454 2 0
    //   379: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   382: aload 4
    //   384: ldc 99
    //   386: aload 12
    //   388: new 43	java/lang/StringBuilder
    //   391: dup
    //   392: ldc 156
    //   394: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   397: aload 9
    //   399: invokevirtual 277	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   402: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   405: aconst_null
    //   406: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   409: pop
    //   410: aload 5
    //   412: ifnull +10 -> 422
    //   415: aload 5
    //   417: invokeinterface 182 1 0
    //   422: aload 4
    //   424: invokevirtual 137	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   427: aload 4
    //   429: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   432: aload 9
    //   434: ifnull +13 -> 447
    //   437: aload_0
    //   438: aload_1
    //   439: aload 9
    //   441: invokevirtual 200	java/lang/Long:longValue	()J
    //   444: invokestatic 204	com/google/android/apps/plus/content/EsConversationsData:notifyMessagesChanged	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;J)V
    //   447: aload_0
    //   448: aload_1
    //   449: invokestatic 144	com/google/android/apps/plus/content/EsConversationsData:notifyConversationsChanged	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)V
    //   452: return
    //   453: astore 6
    //   455: aload 5
    //   457: ifnull +10 -> 467
    //   460: aload 5
    //   462: invokeinterface 182 1 0
    //   467: aload 6
    //   469: athrow
    //   470: astore 7
    //   472: aload 4
    //   474: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   477: aload 7
    //   479: athrow
    //   480: aload 12
    //   482: ldc_w 401
    //   485: aconst_null
    //   486: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   489: aload 12
    //   491: ldc_w 403
    //   494: aconst_null
    //   495: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   498: aload 12
    //   500: ldc_w 405
    //   503: iconst_0
    //   504: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   507: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   510: aload 12
    //   512: ldc_w 399
    //   515: aconst_null
    //   516: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   519: goto -137 -> 382
    //   522: astore 11
    //   524: aload 5
    //   526: ifnull +10 -> 536
    //   529: aload 5
    //   531: invokeinterface 182 1 0
    //   536: aload 11
    //   538: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   50	121	453	finally
    //   130	165	470	finally
    //   415	427	470	finally
    //   460	470	470	finally
    //   529	539	470	finally
    //   165	410	522	finally
    //   480	519	522	finally
  }

  // ERROR //
  public static void requestOlderEvents(Context paramContext, EsAccount paramEsAccount, long paramLong, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokestatic 68	com/google/android/apps/plus/content/EsDatabaseHelper:getDatabaseHelper	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)Lcom/google/android/apps/plus/content/EsDatabaseHelper;
    //   5: invokevirtual 72	com/google/android/apps/plus/content/EsDatabaseHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   8: astore 5
    //   10: aload 5
    //   12: invokevirtual 77	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   15: aconst_null
    //   16: astore 6
    //   18: aload 5
    //   20: ldc 99
    //   22: iconst_2
    //   23: anewarray 94	java/lang/String
    //   26: dup
    //   27: iconst_0
    //   28: ldc 152
    //   30: aastore
    //   31: dup
    //   32: iconst_1
    //   33: ldc_w 676
    //   36: aastore
    //   37: new 43	java/lang/StringBuilder
    //   40: dup
    //   41: ldc 156
    //   43: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   46: lload_2
    //   47: invokestatic 97	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   50: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   53: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   56: aconst_null
    //   57: aconst_null
    //   58: aconst_null
    //   59: aconst_null
    //   60: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   63: astore 6
    //   65: aload 6
    //   67: invokeinterface 166 1 0
    //   72: ifeq +87 -> 159
    //   75: ldc 35
    //   77: iconst_3
    //   78: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   81: ifeq +50 -> 131
    //   84: ldc 35
    //   86: new 43	java/lang/StringBuilder
    //   89: dup
    //   90: ldc_w 1834
    //   93: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   96: aload 6
    //   98: iconst_1
    //   99: invokeinterface 170 2 0
    //   104: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   107: ldc_w 1836
    //   110: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   113: aload 6
    //   115: iconst_0
    //   116: invokeinterface 454 2 0
    //   121: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   124: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   127: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   130: pop
    //   131: aload 4
    //   133: aload 6
    //   135: iconst_0
    //   136: invokeinterface 454 2 0
    //   141: lconst_0
    //   142: aload 6
    //   144: iconst_1
    //   145: invokeinterface 170 2 0
    //   150: getstatic 125	com/google/android/apps/plus/realtimechat/BunchCommands:MAX_EVENTS_PER_REQUEST	I
    //   153: invokestatic 129	com/google/android/apps/plus/realtimechat/BunchCommands:getEventStream	(Ljava/lang/String;JJI)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   156: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   159: new 79	android/content/ContentValues
    //   162: dup
    //   163: invokespecial 80	android/content/ContentValues:<init>	()V
    //   166: astore 9
    //   168: aload 9
    //   170: ldc_w 1393
    //   173: iconst_1
    //   174: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   177: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   180: aload 5
    //   182: ldc 99
    //   184: aload 9
    //   186: new 43	java/lang/StringBuilder
    //   189: dup
    //   190: ldc 156
    //   192: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   195: lload_2
    //   196: invokestatic 97	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   199: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   202: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   205: aconst_null
    //   206: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   209: pop
    //   210: aload 6
    //   212: ifnull +10 -> 222
    //   215: aload 6
    //   217: invokeinterface 182 1 0
    //   222: aload 5
    //   224: invokevirtual 137	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   227: aload 5
    //   229: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   232: return
    //   233: astore 7
    //   235: aload 6
    //   237: ifnull +10 -> 247
    //   240: aload 6
    //   242: invokeinterface 182 1 0
    //   247: aload 7
    //   249: athrow
    //   250: astore 8
    //   252: aload 5
    //   254: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   257: aload 8
    //   259: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   18	210	233	finally
    //   215	227	250	finally
    //   240	250	250	finally
  }

  // ERROR //
  public static android.os.Bundle resendMessageLocally$65290203(Context paramContext, EsAccount paramEsAccount, long paramLong, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    // Byte code:
    //   0: ldc 35
    //   2: iconst_3
    //   3: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   6: ifeq +26 -> 32
    //   9: ldc 35
    //   11: new 43	java/lang/StringBuilder
    //   14: dup
    //   15: ldc_w 1840
    //   18: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   21: lload_2
    //   22: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   25: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   28: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   31: pop
    //   32: aload_0
    //   33: aload_1
    //   34: invokestatic 68	com/google/android/apps/plus/content/EsDatabaseHelper:getDatabaseHelper	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)Lcom/google/android/apps/plus/content/EsDatabaseHelper;
    //   37: invokevirtual 72	com/google/android/apps/plus/content/EsDatabaseHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   40: astore 5
    //   42: aload 5
    //   44: invokevirtual 77	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   47: aconst_null
    //   48: astore 6
    //   50: aload 5
    //   52: ldc 150
    //   54: iconst_1
    //   55: anewarray 94	java/lang/String
    //   58: dup
    //   59: iconst_0
    //   60: ldc_w 1091
    //   63: aastore
    //   64: new 43	java/lang/StringBuilder
    //   67: dup
    //   68: ldc 156
    //   70: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   73: lload_2
    //   74: invokestatic 97	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   77: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   80: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   83: aconst_null
    //   84: aconst_null
    //   85: aconst_null
    //   86: aconst_null
    //   87: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   90: astore 6
    //   92: aload 6
    //   94: invokeinterface 166 1 0
    //   99: istore 9
    //   101: aconst_null
    //   102: astore 10
    //   104: iload 9
    //   106: ifeq +20 -> 126
    //   109: aload 6
    //   111: iconst_0
    //   112: invokeinterface 170 2 0
    //   117: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   120: astore 12
    //   122: aload 12
    //   124: astore 10
    //   126: aload 6
    //   128: ifnull +10 -> 138
    //   131: aload 6
    //   133: invokeinterface 182 1 0
    //   138: aload 5
    //   140: aload_1
    //   141: lload_2
    //   142: iconst_1
    //   143: iconst_1
    //   144: aload 4
    //   146: invokestatic 196	com/google/android/apps/plus/content/EsConversationsData:sendMessageInDatabase$728fb81e	(Landroid/database/sqlite/SQLiteDatabase;Lcom/google/android/apps/plus/content/EsAccount;JZZLcom/google/android/apps/plus/realtimechat/RealTimeChatOperationState;)Landroid/os/Bundle;
    //   149: astore 11
    //   151: aload 5
    //   153: invokevirtual 137	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   156: aload 5
    //   158: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   161: aload 10
    //   163: ifnull +13 -> 176
    //   166: aload_0
    //   167: aload_1
    //   168: aload 10
    //   170: invokevirtual 200	java/lang/Long:longValue	()J
    //   173: invokestatic 204	com/google/android/apps/plus/content/EsConversationsData:notifyMessagesChanged	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;J)V
    //   176: aload 11
    //   178: areturn
    //   179: astore 7
    //   181: aload 6
    //   183: ifnull +10 -> 193
    //   186: aload 6
    //   188: invokeinterface 182 1 0
    //   193: aload 7
    //   195: athrow
    //   196: astore 8
    //   198: aload 5
    //   200: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   203: aload 8
    //   205: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   50	122	179	finally
    //   131	156	196	finally
    //   186	196	196	finally
  }

  // ERROR //
  private static android.os.Bundle sendMessageInDatabase$728fb81e(SQLiteDatabase paramSQLiteDatabase, EsAccount paramEsAccount, long paramLong, boolean paramBoolean1, boolean paramBoolean2, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    // Byte code:
    //   0: new 413	android/os/Bundle
    //   3: dup
    //   4: invokespecial 414	android/os/Bundle:<init>	()V
    //   7: astore 7
    //   9: aconst_null
    //   10: astore 8
    //   12: aload_0
    //   13: ldc 150
    //   15: iconst_4
    //   16: anewarray 94	java/lang/String
    //   19: dup
    //   20: iconst_0
    //   21: ldc 152
    //   23: aastore
    //   24: dup
    //   25: iconst_1
    //   26: ldc_w 380
    //   29: aastore
    //   30: dup
    //   31: iconst_2
    //   32: ldc_w 387
    //   35: aastore
    //   36: dup
    //   37: iconst_3
    //   38: ldc_w 451
    //   41: aastore
    //   42: new 43	java/lang/StringBuilder
    //   45: dup
    //   46: ldc 156
    //   48: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   51: lload_2
    //   52: invokestatic 97	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   55: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   58: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   61: aconst_null
    //   62: aconst_null
    //   63: aconst_null
    //   64: aconst_null
    //   65: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   68: astore 8
    //   70: aload 8
    //   72: invokeinterface 166 1 0
    //   77: istore 10
    //   79: aconst_null
    //   80: astore 11
    //   82: aconst_null
    //   83: astore 12
    //   85: aconst_null
    //   86: astore 13
    //   88: aconst_null
    //   89: astore 14
    //   91: iload 10
    //   93: ifeq +50 -> 143
    //   96: aload 8
    //   98: iconst_0
    //   99: invokeinterface 170 2 0
    //   104: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   107: astore 11
    //   109: aload 8
    //   111: iconst_1
    //   112: invokeinterface 454 2 0
    //   117: astore 13
    //   119: aload 8
    //   121: iconst_2
    //   122: invokeinterface 454 2 0
    //   127: astore 14
    //   129: aload 8
    //   131: iconst_3
    //   132: invokeinterface 454 2 0
    //   137: astore 46
    //   139: aload 46
    //   141: astore 12
    //   143: aload 8
    //   145: ifnull +10 -> 155
    //   148: aload 8
    //   150: invokeinterface 182 1 0
    //   155: aload 11
    //   157: ifnonnull +35 -> 192
    //   160: ldc 35
    //   162: iconst_5
    //   163: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   166: ifeq +26 -> 192
    //   169: ldc 35
    //   171: new 43	java/lang/StringBuilder
    //   174: dup
    //   175: ldc_w 1842
    //   178: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   181: lload_2
    //   182: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   185: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   188: invokestatic 280	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   191: pop
    //   192: ldc2_w 321
    //   195: invokestatic 327	java/lang/System:currentTimeMillis	()J
    //   198: lmul
    //   199: lstore 15
    //   201: aconst_null
    //   202: astore 17
    //   204: aload_0
    //   205: ldc 99
    //   207: iconst_4
    //   208: anewarray 94	java/lang/String
    //   211: dup
    //   212: iconst_0
    //   213: ldc 152
    //   215: aastore
    //   216: dup
    //   217: iconst_1
    //   218: ldc_w 489
    //   221: aastore
    //   222: dup
    //   223: iconst_2
    //   224: ldc_w 550
    //   227: aastore
    //   228: dup
    //   229: iconst_3
    //   230: ldc_w 397
    //   233: aastore
    //   234: new 43	java/lang/StringBuilder
    //   237: dup
    //   238: ldc 156
    //   240: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   243: aload 11
    //   245: invokestatic 408	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   248: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   251: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   254: aconst_null
    //   255: aconst_null
    //   256: aconst_null
    //   257: aconst_null
    //   258: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   261: astore 17
    //   263: aload 17
    //   265: invokeinterface 166 1 0
    //   270: istore 19
    //   272: aconst_null
    //   273: astore 20
    //   275: aconst_null
    //   276: astore 21
    //   278: iload 19
    //   280: ifeq +55 -> 335
    //   283: aload 17
    //   285: iconst_0
    //   286: invokeinterface 454 2 0
    //   291: astore 20
    //   293: aload 17
    //   295: iconst_1
    //   296: invokeinterface 454 2 0
    //   301: astore 21
    //   303: aload 21
    //   305: invokestatic 460	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   308: ifeq +13 -> 321
    //   311: aload 17
    //   313: iconst_2
    //   314: invokeinterface 454 2 0
    //   319: astore 21
    //   321: aload 17
    //   323: iconst_3
    //   324: invokeinterface 170 2 0
    //   329: lstore 43
    //   331: lload 43
    //   333: lstore 15
    //   335: aload 17
    //   337: ifnull +10 -> 347
    //   340: aload 17
    //   342: invokeinterface 182 1 0
    //   347: aload 7
    //   349: ldc 152
    //   351: aload 20
    //   353: invokevirtual 553	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   356: aload 7
    //   358: ldc_w 555
    //   361: aload 21
    //   363: invokevirtual 553	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   366: aload 7
    //   368: ldc_w 422
    //   371: lload_2
    //   372: invokevirtual 420	android/os/Bundle:putLong	(Ljava/lang/String;J)V
    //   375: aload 7
    //   377: ldc_w 1844
    //   380: aload 12
    //   382: invokevirtual 553	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   385: aload 12
    //   387: ifnull +14 -> 401
    //   390: aload 12
    //   392: ldc_w 462
    //   395: invokevirtual 466	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   398: ifne +306 -> 704
    //   401: aload 20
    //   403: ifnull +494 -> 897
    //   406: aload 20
    //   408: ldc_w 329
    //   411: invokevirtual 466	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   414: ifeq +483 -> 897
    //   417: invokestatic 1848	com/google/wireless/realtimechat/proto/Client$ClientConversation:newBuilder	()Lcom/google/wireless/realtimechat/proto/Client$ClientConversation$Builder;
    //   420: astore 29
    //   422: aload 29
    //   424: aload 20
    //   426: invokevirtual 1854	com/google/wireless/realtimechat/proto/Client$ClientConversation$Builder:setId	(Ljava/lang/String;)Lcom/google/wireless/realtimechat/proto/Client$ClientConversation$Builder;
    //   429: pop
    //   430: aconst_null
    //   431: astore 31
    //   433: aload_0
    //   434: ldc_w 625
    //   437: iconst_4
    //   438: anewarray 94	java/lang/String
    //   441: dup
    //   442: iconst_0
    //   443: ldc_w 343
    //   446: aastore
    //   447: dup
    //   448: iconst_1
    //   449: ldc_w 1539
    //   452: aastore
    //   453: dup
    //   454: iconst_2
    //   455: ldc_w 352
    //   458: aastore
    //   459: dup
    //   460: iconst_3
    //   461: ldc_w 389
    //   464: aastore
    //   465: new 43	java/lang/StringBuilder
    //   468: dup
    //   469: ldc_w 627
    //   472: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   475: aload 11
    //   477: invokestatic 408	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   480: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   483: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   486: aconst_null
    //   487: aconst_null
    //   488: aconst_null
    //   489: aconst_null
    //   490: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   493: astore 31
    //   495: aload 31
    //   497: invokeinterface 634 1 0
    //   502: ifeq +150 -> 652
    //   505: invokestatic 1857	com/google/wireless/realtimechat/proto/Data$Participant:newBuilder	()Lcom/google/wireless/realtimechat/proto/Data$Participant$Builder;
    //   508: astore 35
    //   510: aload 35
    //   512: aload 31
    //   514: iconst_0
    //   515: invokeinterface 454 2 0
    //   520: invokevirtual 1863	com/google/wireless/realtimechat/proto/Data$Participant$Builder:setParticipantId	(Ljava/lang/String;)Lcom/google/wireless/realtimechat/proto/Data$Participant$Builder;
    //   523: pop
    //   524: aload 31
    //   526: iconst_1
    //   527: invokeinterface 454 2 0
    //   532: astore 37
    //   534: aload 37
    //   536: ifnull +11 -> 547
    //   539: aload 35
    //   541: aload 37
    //   543: invokevirtual 1866	com/google/wireless/realtimechat/proto/Data$Participant$Builder:setFirstName	(Ljava/lang/String;)Lcom/google/wireless/realtimechat/proto/Data$Participant$Builder;
    //   546: pop
    //   547: aload 31
    //   549: iconst_2
    //   550: invokeinterface 454 2 0
    //   555: astore 38
    //   557: aload 38
    //   559: ifnull +11 -> 570
    //   562: aload 35
    //   564: aload 38
    //   566: invokevirtual 1869	com/google/wireless/realtimechat/proto/Data$Participant$Builder:setFullName	(Ljava/lang/String;)Lcom/google/wireless/realtimechat/proto/Data$Participant$Builder;
    //   569: pop
    //   570: aload 35
    //   572: aload 31
    //   574: iconst_3
    //   575: invokeinterface 179 2 0
    //   580: invokestatic 1871	com/google/android/apps/plus/content/EsConversationsData:convertParticipantType	(I)Lcom/google/wireless/realtimechat/proto/Data$Participant$Type;
    //   583: invokevirtual 1875	com/google/wireless/realtimechat/proto/Data$Participant$Builder:setType	(Lcom/google/wireless/realtimechat/proto/Data$Participant$Type;)Lcom/google/wireless/realtimechat/proto/Data$Participant$Builder;
    //   586: pop
    //   587: aload 29
    //   589: aload 35
    //   591: invokevirtual 1878	com/google/wireless/realtimechat/proto/Data$Participant$Builder:build	()Lcom/google/wireless/realtimechat/proto/Data$Participant;
    //   594: invokevirtual 1882	com/google/wireless/realtimechat/proto/Client$ClientConversation$Builder:addParticipant	(Lcom/google/wireless/realtimechat/proto/Data$Participant;)Lcom/google/wireless/realtimechat/proto/Client$ClientConversation$Builder;
    //   597: pop
    //   598: goto -103 -> 495
    //   601: astore 32
    //   603: aload 31
    //   605: ifnull +10 -> 615
    //   608: aload 31
    //   610: invokeinterface 182 1 0
    //   615: aload 32
    //   617: athrow
    //   618: astore 9
    //   620: aload 8
    //   622: ifnull +10 -> 632
    //   625: aload 8
    //   627: invokeinterface 182 1 0
    //   632: aload 9
    //   634: athrow
    //   635: astore 18
    //   637: aload 17
    //   639: ifnull +10 -> 649
    //   642: aload 17
    //   644: invokeinterface 182 1 0
    //   649: aload 18
    //   651: athrow
    //   652: aload 31
    //   654: invokeinterface 1158 1 0
    //   659: iconst_1
    //   660: if_icmple +225 -> 885
    //   663: aload 29
    //   665: getstatic 474	com/google/wireless/realtimechat/proto/Data$ConversationType:GROUP	Lcom/google/wireless/realtimechat/proto/Data$ConversationType;
    //   668: invokevirtual 1885	com/google/wireless/realtimechat/proto/Client$ClientConversation$Builder:setType	(Lcom/google/wireless/realtimechat/proto/Data$ConversationType;)Lcom/google/wireless/realtimechat/proto/Client$ClientConversation$Builder;
    //   671: pop
    //   672: aload 31
    //   674: ifnull +10 -> 684
    //   677: aload 31
    //   679: invokeinterface 182 1 0
    //   684: aload 6
    //   686: aload 29
    //   688: invokevirtual 1887	com/google/wireless/realtimechat/proto/Client$ClientConversation$Builder:build	()Lcom/google/wireless/realtimechat/proto/Client$ClientConversation;
    //   691: aload 13
    //   693: aload 14
    //   695: aconst_null
    //   696: iload 4
    //   698: invokestatic 363	com/google/android/apps/plus/realtimechat/BunchCommands:createConversation	(Lcom/google/wireless/realtimechat/proto/Client$ClientConversation;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   701: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   704: lload 15
    //   706: lconst_1
    //   707: ladd
    //   708: lstore 22
    //   710: new 79	android/content/ContentValues
    //   713: dup
    //   714: invokespecial 80	android/content/ContentValues:<init>	()V
    //   717: astore 24
    //   719: iload 5
    //   721: ifeq +88 -> 809
    //   724: aload 24
    //   726: ldc_w 401
    //   729: aload 14
    //   731: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   734: aload 24
    //   736: ldc_w 403
    //   739: aload 12
    //   741: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   744: aload 24
    //   746: ldc_w 399
    //   749: aload_1
    //   750: invokevirtual 348	com/google/android/apps/plus/content/EsAccount:getRealTimeChatParticipantId	()Ljava/lang/String;
    //   753: invokevirtual 241	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   756: aload 24
    //   758: ldc_w 397
    //   761: lload 22
    //   763: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   766: invokevirtual 383	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   769: aload 24
    //   771: ldc_w 405
    //   774: iconst_1
    //   775: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   778: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   781: iconst_1
    //   782: anewarray 94	java/lang/String
    //   785: astore 27
    //   787: aload 27
    //   789: iconst_0
    //   790: aload 11
    //   792: invokestatic 408	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   795: aastore
    //   796: aload_0
    //   797: ldc 99
    //   799: aload 24
    //   801: ldc 101
    //   803: aload 27
    //   805: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   808: pop
    //   809: aload 24
    //   811: invokevirtual 234	android/content/ContentValues:clear	()V
    //   814: iload 4
    //   816: ifeq +102 -> 918
    //   819: bipush 7
    //   821: istore 25
    //   823: aload 24
    //   825: ldc 154
    //   827: iload 25
    //   829: invokestatic 88	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   832: invokevirtual 92	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   835: iload 5
    //   837: ifeq +16 -> 853
    //   840: aload 24
    //   842: ldc_w 391
    //   845: lload 22
    //   847: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   850: invokevirtual 383	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   853: aload_0
    //   854: ldc 150
    //   856: aload 24
    //   858: new 43	java/lang/StringBuilder
    //   861: dup
    //   862: ldc 156
    //   864: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   867: lload_2
    //   868: invokestatic 97	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   871: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   874: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   877: aconst_null
    //   878: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   881: pop
    //   882: aload 7
    //   884: areturn
    //   885: aload 29
    //   887: getstatic 373	com/google/wireless/realtimechat/proto/Data$ConversationType:ONE_TO_ONE	Lcom/google/wireless/realtimechat/proto/Data$ConversationType;
    //   890: invokevirtual 1885	com/google/wireless/realtimechat/proto/Client$ClientConversation$Builder:setType	(Lcom/google/wireless/realtimechat/proto/Data$ConversationType;)Lcom/google/wireless/realtimechat/proto/Client$ClientConversation$Builder;
    //   893: pop
    //   894: goto -222 -> 672
    //   897: aload 6
    //   899: aload 20
    //   901: aload 13
    //   903: aload 14
    //   905: aload 12
    //   907: iload 4
    //   909: invokestatic 430	com/google/android/apps/plus/realtimechat/BunchCommands:sendMessage	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;
    //   912: invokevirtual 121	com/google/android/apps/plus/realtimechat/RealTimeChatOperationState:addRequest	(Lcom/google/wireless/realtimechat/proto/Client$BunchClientRequest;)V
    //   915: goto -211 -> 704
    //   918: iconst_1
    //   919: istore 25
    //   921: goto -98 -> 823
    //
    // Exception table:
    //   from	to	target	type
    //   433	598	601	finally
    //   652	672	601	finally
    //   885	894	601	finally
    //   12	139	618	finally
    //   204	331	635	finally
  }

  public static long sendMessageLocally(Context paramContext, EsAccount paramEsAccount, long paramLong, String paramString1, String paramString2, boolean paramBoolean, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "sendMessageLocally conversationRowId: " + paramLong);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    while (true)
    {
      Long localLong;
      try
      {
        str1 = queryConversationId(localSQLiteDatabase, paramLong);
        i = 1;
        if (str1.startsWith("c:"))
        {
          i = 0;
          str2 = "c:" + StringUtils.randomString(32);
          l1 = 0L;
          localCursor = null;
        }
      }
      finally
      {
        try
        {
          String str1;
          String str2;
          long l1;
          localCursor = localSQLiteDatabase.query("messages", new String[] { "MAX(timestamp)" }, "conversation_id=" + String.valueOf(paramLong), null, null, null, null);
          if (localCursor.moveToFirst())
          {
            long l4 = localCursor.getLong(0);
            l1 = l4;
          }
          if (localCursor != null)
            localCursor.close();
          long l2 = 1000L * System.currentTimeMillis();
          if (l2 <= l1)
            l2 = l1 + 1L;
          ContentValues localContentValues = new ContentValues();
          localContentValues.put("latest_message_text", paramString1);
          localContentValues.put("latest_message_image_url", paramString2);
          localContentValues.put("latest_message_author_id", paramEsAccount.getRealTimeChatParticipantId());
          localContentValues.put("latest_message_timestamp", Long.valueOf(l2));
          localContentValues.put("latest_message_type", Integer.valueOf(1));
          String[] arrayOfString = new String[1];
          arrayOfString[0] = String.valueOf(paramLong);
          localSQLiteDatabase.update("conversations", localContentValues, "_id=?", arrayOfString);
          localContentValues.clear();
          localContentValues.put("message_id", str2);
          localContentValues.put("conversation_id", Long.valueOf(paramLong));
          localContentValues.put("author_id", paramEsAccount.getRealTimeChatParticipantId());
          localContentValues.put("text", paramString1);
          localContentValues.put("status", Integer.valueOf(i));
          localContentValues.put("type", Integer.valueOf(1));
          localContentValues.put("timestamp", Long.valueOf(l2));
          localContentValues.put("notification_seen", Integer.valueOf(1));
          localContentValues.put("image_url", paramString2);
          localLong = Long.valueOf(localSQLiteDatabase.insert("messages", null, localContentValues));
          if (paramBoolean)
            paramRealTimeChatOperationState.addRequest(BunchCommands.sendMessage(str1, str2, paramString1, paramString2, false));
          localSQLiteDatabase.setTransactionSuccessful();
          localSQLiteDatabase.endTransaction();
          notifyMessagesChanged(paramContext, paramEsAccount, paramLong);
          notifyConversationsChanged(paramContext, paramEsAccount);
          return l3;
          if (paramBoolean)
            continue;
          int i = 2;
        }
        finally
        {
          Cursor localCursor;
          if (localCursor != null)
            localCursor.close();
        }
      }
      long l3 = localLong.longValue();
    }
  }

  public static void sendPresenceRequestLocally(Context paramContext, EsAccount paramEsAccount, long paramLong, boolean paramBoolean1, boolean paramBoolean2, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "sendPresenceRequestLocally  conversationRowId: " + paramLong + " isPresent: " + paramBoolean1);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      paramRealTimeChatOperationState.addRequest(BunchCommands.presenceRequest(queryConversationId(localSQLiteDatabase, paramLong), paramBoolean1, paramBoolean2));
      localSQLiteDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  public static void sendTypingRequestLocally(Context paramContext, EsAccount paramEsAccount, long paramLong, Client.Typing.Type paramType, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "sendTypingRequestLocally  conversationRowId: " + paramLong + " typingType: " + paramType.name());
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      paramRealTimeChatOperationState.addRequest(BunchCommands.typingRequest(queryConversationId(localSQLiteDatabase, paramLong), paramType));
      localSQLiteDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  // ERROR //
  public static void setMessageStatusLocally(Context paramContext, EsAccount paramEsAccount, long paramLong, int paramInt)
  {
    // Byte code:
    //   0: ldc 35
    //   2: iconst_3
    //   3: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   6: ifeq +26 -> 32
    //   9: ldc 35
    //   11: new 43	java/lang/StringBuilder
    //   14: dup
    //   15: ldc_w 1916
    //   18: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   21: lload_2
    //   22: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   25: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   28: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   31: pop
    //   32: aload_0
    //   33: aload_1
    //   34: invokestatic 68	com/google/android/apps/plus/content/EsDatabaseHelper:getDatabaseHelper	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)Lcom/google/android/apps/plus/content/EsDatabaseHelper;
    //   37: invokevirtual 72	com/google/android/apps/plus/content/EsDatabaseHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   40: astore 5
    //   42: aload 5
    //   44: invokevirtual 77	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   47: aconst_null
    //   48: astore 6
    //   50: aload 5
    //   52: ldc 150
    //   54: iconst_1
    //   55: anewarray 94	java/lang/String
    //   58: dup
    //   59: iconst_0
    //   60: ldc 152
    //   62: aastore
    //   63: new 43	java/lang/StringBuilder
    //   66: dup
    //   67: ldc 156
    //   69: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   72: lload_2
    //   73: invokestatic 97	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   76: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   79: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   82: aconst_null
    //   83: aconst_null
    //   84: aconst_null
    //   85: aconst_null
    //   86: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   89: astore 6
    //   91: aload 6
    //   93: invokeinterface 166 1 0
    //   98: istore 9
    //   100: aconst_null
    //   101: astore 10
    //   103: iload 9
    //   105: ifeq +20 -> 125
    //   108: aload 6
    //   110: iconst_0
    //   111: invokeinterface 170 2 0
    //   116: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   119: astore 11
    //   121: aload 11
    //   123: astore 10
    //   125: aload 6
    //   127: ifnull +10 -> 137
    //   130: aload 6
    //   132: invokeinterface 182 1 0
    //   137: aload 5
    //   139: lload_2
    //   140: bipush 8
    //   142: invokestatic 212	com/google/android/apps/plus/content/EsConversationsData:updateMessageStatus$4372adf	(Landroid/database/sqlite/SQLiteDatabase;JI)V
    //   145: aload 5
    //   147: invokevirtual 137	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   150: aload 5
    //   152: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   155: aload 10
    //   157: ifnull +13 -> 170
    //   160: aload_0
    //   161: aload_1
    //   162: aload 10
    //   164: invokevirtual 200	java/lang/Long:longValue	()J
    //   167: invokestatic 204	com/google/android/apps/plus/content/EsConversationsData:notifyMessagesChanged	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;J)V
    //   170: return
    //   171: astore 7
    //   173: aload 6
    //   175: ifnull +10 -> 185
    //   178: aload 6
    //   180: invokeinterface 182 1 0
    //   185: aload 7
    //   187: athrow
    //   188: astore 8
    //   190: aload 5
    //   192: invokevirtual 140	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   195: aload 8
    //   197: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   50	121	171	finally
    //   130	150	188	finally
    //   178	188	188	finally
  }

  private static final void syncParticipants(SQLiteDatabase paramSQLiteDatabase, EsAccount paramEsAccount, long paramLong, boolean paramBoolean, Client.ClientConversation paramClientConversation)
  {
    ContentValues localContentValues = new ContentValues();
    List localList1 = paramClientConversation.getParticipantList();
    List localList2 = paramClientConversation.getInactiveParticipantList();
    StringBuilder localStringBuilder1 = new StringBuilder();
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    localStringBuilder1.append("conversation_id=?");
    localArrayList.add(String.valueOf(paramLong));
    localStringBuilder1.append(" AND participant_id NOT IN (");
    Iterator localIterator1 = localList1.iterator();
    while (localIterator1.hasNext())
    {
      Data.Participant localParticipant3 = (Data.Participant)localIterator1.next();
      if (localParticipant3.hasParticipantId())
      {
        i++;
        localStringBuilder1.append("?,");
        localArrayList.add(localParticipant3.getParticipantId());
      }
    }
    localStringBuilder1.setLength(-1 + localStringBuilder1.length());
    localStringBuilder1.append(")");
    localContentValues.put("active", Integer.valueOf(0));
    if (i > 0)
      paramSQLiteDatabase.update("conversation_participants", localContentValues, localStringBuilder1.toString(), (String[])localArrayList.toArray(new String[0]));
    StringBuilder localStringBuilder2 = new StringBuilder();
    StringBuilder localStringBuilder3 = new StringBuilder();
    int j = 0;
    int k = 1;
    Iterator localIterator2 = localList1.iterator();
    if (localIterator2.hasNext())
    {
      Data.Participant localParticipant2 = (Data.Participant)localIterator2.next();
      if (j < 5)
      {
        if (j > 0)
          localStringBuilder2.append('|');
        localStringBuilder2.append(localParticipant2.getParticipantId());
        if (!localParticipant2.getParticipantId().equals(paramEsAccount.getRealTimeChatParticipantId()))
        {
          if (k == 0)
            localStringBuilder3.append(", ");
          k = 0;
          if (!paramBoolean)
            break label581;
          localStringBuilder3.append(localParticipant2.getFirstName());
        }
      }
      while (true)
      {
        j++;
        localContentValues.clear();
        localContentValues.put("participant_id", localParticipant2.getParticipantId());
        localContentValues.put("full_name", localParticipant2.getFullName());
        if ((localParticipant2.getFirstName() != null) && (!localParticipant2.getFirstName().equals("")))
          localContentValues.put("first_name", localParticipant2.getFirstName());
        localContentValues.put("type", Integer.valueOf(convertParticipantType(localParticipant2)));
        String[] arrayOfString3 = new String[1];
        arrayOfString3[0] = localParticipant2.getParticipantId();
        int n = paramSQLiteDatabase.update("participants", localContentValues, "participant_id=?", arrayOfString3);
        if (EsLog.isLoggable("EsConversationsData", 3))
          Log.d("EsConversationsData", "Update Participant " + localParticipant2.getParticipantId() + " " + n);
        if (n == 0)
          paramSQLiteDatabase.insert("participants", null, localContentValues);
        localContentValues.clear();
        localContentValues.put("conversation_id", Long.valueOf(paramLong));
        localContentValues.put("participant_id", localParticipant2.getParticipantId());
        localContentValues.put("active", Integer.valueOf(1));
        localContentValues.put("sequence", Integer.valueOf(j));
        paramSQLiteDatabase.insertWithOnConflict("conversation_participants", null, localContentValues, 5);
        break;
        label581: localStringBuilder3.append(localParticipant2.getFullName());
        k = 0;
      }
    }
    if (localList2 != null)
    {
      Iterator localIterator3 = localList2.iterator();
      while (localIterator3.hasNext())
      {
        Data.Participant localParticipant1 = (Data.Participant)localIterator3.next();
        localContentValues.clear();
        localContentValues.put("participant_id", localParticipant1.getParticipantId());
        localContentValues.put("full_name", localParticipant1.getFullName());
        if ((localParticipant1.getFirstName() != null) && (!localParticipant1.getFirstName().equals("")))
          localContentValues.put("first_name", localParticipant1.getFirstName());
        localContentValues.put("type", Integer.valueOf(convertParticipantType(localParticipant1)));
        String[] arrayOfString2 = new String[1];
        arrayOfString2[0] = localParticipant1.getParticipantId();
        int m = paramSQLiteDatabase.update("participants", localContentValues, "participant_id=?", arrayOfString2);
        if (EsLog.isLoggable("EsConversationsData", 3))
          Log.d("EsConversationsData", "Update Participant " + localParticipant1.getParticipantId() + " " + m);
        if (m == 0)
          paramSQLiteDatabase.insert("participants", null, localContentValues);
        localContentValues.clear();
        localContentValues.put("conversation_id", Long.valueOf(paramLong));
        localContentValues.put("participant_id", localParticipant1.getParticipantId());
        localContentValues.put("active", Integer.valueOf(0));
        paramSQLiteDatabase.insertWithOnConflict("conversation_participants", null, localContentValues, 5);
      }
    }
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "generatedName " + localStringBuilder3.toString());
    localContentValues.clear();
    localContentValues.put("generated_name", localStringBuilder3.toString());
    localContentValues.put("packed_participants", localStringBuilder2.toString());
    String[] arrayOfString1 = new String[1];
    arrayOfString1[0] = String.valueOf(paramLong);
    paramSQLiteDatabase.update("conversations", localContentValues, "_id=?", arrayOfString1);
  }

  private static final void updateConversationId(SQLiteDatabase paramSQLiteDatabase, String paramString1, String paramString2)
  {
    ContentValues localContentValues = new ContentValues();
    Cursor localCursor1 = paramSQLiteDatabase.query("conversations", new String[] { "_id" }, "conversation_id=?", new String[] { paramString2 }, null, null, null);
    try
    {
      long l1;
      Cursor localCursor2;
      if (localCursor1.moveToFirst())
      {
        if (EsLog.isLoggable("EsConversationsData", 3))
          Log.d("EsConversationsData", "Already have a conversation Id " + paramString2);
        l1 = localCursor1.getLong(0);
        localCursor2 = paramSQLiteDatabase.query("conversations", new String[] { "_id" }, "conversation_id=?", new String[] { paramString1 }, null, null, null);
      }
      try
      {
        if (localCursor2.moveToFirst())
        {
          long l2 = localCursor2.getLong(0);
          localContentValues.put("conversation_id", Long.valueOf(l2));
          String[] arrayOfString1 = new String[1];
          arrayOfString1[0] = String.valueOf(l1);
          paramSQLiteDatabase.update("messages", localContentValues, "conversation_id=?", arrayOfString1);
          localContentValues.clear();
          localContentValues.put("conversation_id", Long.valueOf(l2));
          String[] arrayOfString2 = new String[1];
          arrayOfString2[0] = String.valueOf(l1);
          paramSQLiteDatabase.update("conversation_participants", localContentValues, "conversation_id=?", arrayOfString2);
          String[] arrayOfString3 = new String[1];
          arrayOfString3[0] = String.valueOf(l1);
          paramSQLiteDatabase.delete("conversations", "_id=?", arrayOfString3);
        }
        localCursor2.close();
        localCursor1.close();
        localContentValues.clear();
        localContentValues.put("conversation_id", paramString2);
        paramSQLiteDatabase.update("conversations", localContentValues, "conversation_id=?", new String[] { paramString1 });
        return;
      }
      finally
      {
      }
    }
    finally
    {
      localCursor1.close();
    }
  }

  public static void updateConversationMutedLocally(Context paramContext, EsAccount paramEsAccount, long paramLong, boolean paramBoolean, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "updateConversationMutedLocally conversationRowId: " + paramLong + " muted: " + paramBoolean);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      ContentValues localContentValues = new ContentValues();
      if (paramBoolean)
      {
        i = 1;
        localContentValues.put("is_muted", Integer.valueOf(i));
        localSQLiteDatabase.update("conversations", localContentValues, "_id=" + String.valueOf(paramLong), null);
        paramRealTimeChatOperationState.addRequest(BunchCommands.setConversationMuted(queryConversationId(localSQLiteDatabase, paramLong), paramBoolean));
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        return;
      }
      int i = 0;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  public static void updateConversationNameLocally(Context paramContext, EsAccount paramEsAccount, long paramLong, String paramString, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "updateConversationNameLocally conversationRowId: " + paramLong);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      paramRealTimeChatOperationState.addRequest(BunchCommands.setConversationName(queryConversationId(localSQLiteDatabase, paramLong), paramString));
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      notifyConversationsChanged(paramContext, paramEsAccount);
      return;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  // ERROR //
  private static void updateLatestEventTimestamp(SQLiteDatabase paramSQLiteDatabase, long paramLong1, long paramLong2)
  {
    // Byte code:
    //   0: ldc 35
    //   2: iconst_3
    //   3: invokestatic 41	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   6: ifeq +36 -> 42
    //   9: ldc 35
    //   11: new 43	java/lang/StringBuilder
    //   14: dup
    //   15: ldc_w 1963
    //   18: invokespecial 48	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   21: lload_1
    //   22: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   25: ldc_w 1072
    //   28: invokevirtual 134	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   31: lload_3
    //   32: invokevirtual 52	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   35: invokevirtual 56	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   38: invokestatic 62	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   41: pop
    //   42: lconst_0
    //   43: lstore 5
    //   45: lconst_0
    //   46: lstore 7
    //   48: aconst_null
    //   49: astore 9
    //   51: aload_0
    //   52: ldc 245
    //   54: iconst_1
    //   55: anewarray 94	java/lang/String
    //   58: dup
    //   59: iconst_0
    //   60: ldc 243
    //   62: aastore
    //   63: ldc_w 1781
    //   66: iconst_1
    //   67: anewarray 94	java/lang/String
    //   70: dup
    //   71: iconst_0
    //   72: ldc 238
    //   74: aastore
    //   75: aconst_null
    //   76: aconst_null
    //   77: aconst_null
    //   78: aconst_null
    //   79: invokevirtual 1153	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   82: astore 9
    //   84: aload 9
    //   86: invokeinterface 166 1 0
    //   91: istore 11
    //   93: iconst_0
    //   94: istore 12
    //   96: iload 11
    //   98: ifeq +25 -> 123
    //   101: aload 9
    //   103: iconst_0
    //   104: invokeinterface 179 2 0
    //   109: istore 24
    //   111: iconst_0
    //   112: istore 12
    //   114: iload 24
    //   116: iconst_1
    //   117: if_icmpne +6 -> 123
    //   120: iconst_1
    //   121: istore 12
    //   123: aload 9
    //   125: ifnull +10 -> 135
    //   128: aload 9
    //   130: invokeinterface 182 1 0
    //   135: aconst_null
    //   136: astore 13
    //   138: iconst_3
    //   139: anewarray 94	java/lang/String
    //   142: dup
    //   143: iconst_0
    //   144: ldc_w 505
    //   147: aastore
    //   148: dup
    //   149: iconst_1
    //   150: ldc_w 674
    //   153: aastore
    //   154: dup
    //   155: iconst_2
    //   156: ldc_w 397
    //   159: aastore
    //   160: astore 14
    //   162: iconst_1
    //   163: anewarray 94	java/lang/String
    //   166: astore 16
    //   168: aload 16
    //   170: iconst_0
    //   171: lload_1
    //   172: invokestatic 97	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   175: aastore
    //   176: aload_0
    //   177: ldc 99
    //   179: aload 14
    //   181: ldc 101
    //   183: aload 16
    //   185: aconst_null
    //   186: aconst_null
    //   187: aconst_null
    //   188: invokevirtual 160	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   191: astore 13
    //   193: aload 13
    //   195: invokeinterface 166 1 0
    //   200: istore 17
    //   202: iconst_0
    //   203: istore 18
    //   205: iload 17
    //   207: ifeq +52 -> 259
    //   210: aload 13
    //   212: iconst_0
    //   213: invokeinterface 1126 2 0
    //   218: ifne +170 -> 388
    //   221: aload 13
    //   223: iconst_0
    //   224: invokeinterface 179 2 0
    //   229: ifeq +159 -> 388
    //   232: iconst_1
    //   233: istore 18
    //   235: aload 13
    //   237: iconst_1
    //   238: invokeinterface 170 2 0
    //   243: lstore 5
    //   245: aload 13
    //   247: iconst_2
    //   248: invokeinterface 170 2 0
    //   253: lstore 22
    //   255: lload 22
    //   257: lstore 7
    //   259: aload 13
    //   261: ifnull +10 -> 271
    //   264: aload 13
    //   266: invokeinterface 182 1 0
    //   271: lload_3
    //   272: lload 5
    //   274: lcmp
    //   275: ifle +95 -> 370
    //   278: new 79	android/content/ContentValues
    //   281: dup
    //   282: invokespecial 80	android/content/ContentValues:<init>	()V
    //   285: astore 19
    //   287: iload 18
    //   289: ifne +20 -> 309
    //   292: iload 12
    //   294: ifne +15 -> 309
    //   297: aload 19
    //   299: ldc_w 674
    //   302: lload_3
    //   303: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   306: invokevirtual 383	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   309: lload 5
    //   311: lconst_0
    //   312: lcmp
    //   313: ifle +22 -> 335
    //   316: lload 7
    //   318: lload_3
    //   319: lcmp
    //   320: ifle +15 -> 335
    //   323: aload 19
    //   325: ldc_w 397
    //   328: lload_3
    //   329: invokestatic 175	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   332: invokevirtual 383	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   335: aload 19
    //   337: invokevirtual 1443	android/content/ContentValues:size	()I
    //   340: ifle +30 -> 370
    //   343: iconst_1
    //   344: anewarray 94	java/lang/String
    //   347: astore 20
    //   349: aload 20
    //   351: iconst_0
    //   352: lload_1
    //   353: invokestatic 97	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   356: aastore
    //   357: aload_0
    //   358: ldc 99
    //   360: aload 19
    //   362: ldc 101
    //   364: aload 20
    //   366: invokevirtual 105	android/database/sqlite/SQLiteDatabase:update	(Ljava/lang/String;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   369: pop
    //   370: return
    //   371: astore 10
    //   373: aload 9
    //   375: ifnull +10 -> 385
    //   378: aload 9
    //   380: invokeinterface 182 1 0
    //   385: aload 10
    //   387: athrow
    //   388: iconst_0
    //   389: istore 18
    //   391: goto -156 -> 235
    //   394: astore 15
    //   396: aload 13
    //   398: ifnull +10 -> 408
    //   401: aload 13
    //   403: invokeinterface 182 1 0
    //   408: aload 15
    //   410: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   51	111	371	finally
    //   138	255	394	finally
  }

  private static void updateMessageStatus$4372adf(SQLiteDatabase paramSQLiteDatabase, long paramLong, int paramInt)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("status", Integer.valueOf(paramInt));
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramLong);
    paramSQLiteDatabase.update("messages", localContentValues, "_id=?", arrayOfString);
  }

  public static void updateMessageUriAndSendLocally$4f1d5505(Context paramContext, EsAccount paramEsAccount, long paramLong1, long paramLong2, String paramString, RealTimeChatOperationState paramRealTimeChatOperationState)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "updateMessageUriAndSendLocally messageRowId: " + paramLong2);
    SQLiteDatabase localSQLiteDatabase = EsDatabaseHelper.getDatabaseHelper(paramContext, paramEsAccount).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    try
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("image_url", paramString);
      localSQLiteDatabase.update("messages", localContentValues, "_id=" + String.valueOf(paramLong2), null);
      sendMessageInDatabase$728fb81e(localSQLiteDatabase, paramEsAccount, paramLong2, false, false, paramRealTimeChatOperationState);
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      notifyMessagesChanged(paramContext, paramEsAccount, paramLong1);
      return;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
  }

  private static void updatePreviewSystemMessage$5091a27(SQLiteDatabase paramSQLiteDatabase, long paramLong1, String paramString1, String paramString2, int paramInt, long paramLong2)
  {
    if (EsLog.isLoggable("EsConversationsData", 3))
      Log.d("EsConversationsData", "updatePreviewSystemMessage  messageType: " + 3 + " senderId: " + paramString2 + " timestamp: " + paramLong2);
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("is_visible", Integer.valueOf(1));
    localContentValues.put("latest_message_timestamp", Long.valueOf(paramLong2));
    localContentValues.put("latest_message_author_id", paramString2);
    localContentValues.put("latest_message_text", paramString1);
    localContentValues.put("latest_message_image_url", null);
    localContentValues.put("latest_message_type", Integer.valueOf(3));
    String[] arrayOfString = new String[1];
    arrayOfString[0] = String.valueOf(paramLong1);
    paramSQLiteDatabase.update("conversations", localContentValues, "_id=?", arrayOfString);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.EsConversationsData
 * JD-Core Version:    0.6.2
 */