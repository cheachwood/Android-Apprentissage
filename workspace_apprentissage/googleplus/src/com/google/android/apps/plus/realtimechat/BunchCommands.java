package com.google.android.apps.plus.realtimechat;

import com.google.wireless.realtimechat.proto.Client.BunchClientRequest;
import com.google.wireless.realtimechat.proto.Client.BunchClientRequest.Builder;
import com.google.wireless.realtimechat.proto.Client.ChatMessage;
import com.google.wireless.realtimechat.proto.Client.ChatMessage.Builder;
import com.google.wireless.realtimechat.proto.Client.ChatMessageRequest;
import com.google.wireless.realtimechat.proto.Client.ChatMessageRequest.Builder;
import com.google.wireless.realtimechat.proto.Client.ClientConversation;
import com.google.wireless.realtimechat.proto.Client.ConversationListRequest;
import com.google.wireless.realtimechat.proto.Client.ConversationListRequest.Builder;
import com.google.wireless.realtimechat.proto.Client.ConversationListRequest.Type;
import com.google.wireless.realtimechat.proto.Client.ConversationPreferenceRequest;
import com.google.wireless.realtimechat.proto.Client.ConversationPreferenceRequest.Builder;
import com.google.wireless.realtimechat.proto.Client.ConversationPreferenceRequest.Type;
import com.google.wireless.realtimechat.proto.Client.ConversationRenameRequest;
import com.google.wireless.realtimechat.proto.Client.ConversationRenameRequest.Builder;
import com.google.wireless.realtimechat.proto.Client.DeviceRegistrationRequest;
import com.google.wireless.realtimechat.proto.Client.DeviceRegistrationRequest.Builder;
import com.google.wireless.realtimechat.proto.Client.DeviceRegistrationRequest.DeviceType;
import com.google.wireless.realtimechat.proto.Client.DeviceRegistrationRequest.RegistrationType;
import com.google.wireless.realtimechat.proto.Client.EventStreamRequest;
import com.google.wireless.realtimechat.proto.Client.EventStreamRequest.Builder;
import com.google.wireless.realtimechat.proto.Client.EventStreamRequest.Order;
import com.google.wireless.realtimechat.proto.Client.InviteRequest;
import com.google.wireless.realtimechat.proto.Client.InviteRequest.Builder;
import com.google.wireless.realtimechat.proto.Client.LeaveConversationRequest;
import com.google.wireless.realtimechat.proto.Client.LeaveConversationRequest.Builder;
import com.google.wireless.realtimechat.proto.Client.NewConversationRequest;
import com.google.wireless.realtimechat.proto.Client.NewConversationRequest.Builder;
import com.google.wireless.realtimechat.proto.Client.PingRequest;
import com.google.wireless.realtimechat.proto.Client.PingRequest.Builder;
import com.google.wireless.realtimechat.proto.Client.Presence.Type;
import com.google.wireless.realtimechat.proto.Client.PresenceRequest;
import com.google.wireless.realtimechat.proto.Client.PresenceRequest.Builder;
import com.google.wireless.realtimechat.proto.Client.Receipt.Type;
import com.google.wireless.realtimechat.proto.Client.ReceiptRequest;
import com.google.wireless.realtimechat.proto.Client.ReceiptRequest.Builder;
import com.google.wireless.realtimechat.proto.Client.ReceiptRequest.ReceiptInfo;
import com.google.wireless.realtimechat.proto.Client.ReceiptRequest.ReceiptInfo.Builder;
import com.google.wireless.realtimechat.proto.Client.ReplyToInviteRequest;
import com.google.wireless.realtimechat.proto.Client.ReplyToInviteRequest.Builder;
import com.google.wireless.realtimechat.proto.Client.ReplyToInviteRequest.Reply;
import com.google.wireless.realtimechat.proto.Client.SetAclsRequest;
import com.google.wireless.realtimechat.proto.Client.SetAclsRequest.Acl;
import com.google.wireless.realtimechat.proto.Client.SetAclsRequest.Builder;
import com.google.wireless.realtimechat.proto.Client.SuggestionsRequest;
import com.google.wireless.realtimechat.proto.Client.SuggestionsRequest.Builder;
import com.google.wireless.realtimechat.proto.Client.SuggestionsRequest.SuggestionsType;
import com.google.wireless.realtimechat.proto.Client.TileEventRequest;
import com.google.wireless.realtimechat.proto.Client.TileEventRequest.Builder;
import com.google.wireless.realtimechat.proto.Client.Typing.Type;
import com.google.wireless.realtimechat.proto.Client.TypingRequest;
import com.google.wireless.realtimechat.proto.Client.TypingRequest.Builder;
import com.google.wireless.realtimechat.proto.Client.UserCreationRequest;
import com.google.wireless.realtimechat.proto.Client.UserCreationRequest.Builder;
import com.google.wireless.realtimechat.proto.Client.UserInfoRequest;
import com.google.wireless.realtimechat.proto.Client.UserInfoRequest.Builder;
import com.google.wireless.realtimechat.proto.Data.Content;
import com.google.wireless.realtimechat.proto.Data.Content.Builder;
import com.google.wireless.realtimechat.proto.Data.KeyValue;
import com.google.wireless.realtimechat.proto.Data.KeyValue.Builder;
import com.google.wireless.realtimechat.proto.Data.Participant;
import com.google.wireless.realtimechat.proto.Data.Participant.Builder;
import com.google.wireless.realtimechat.proto.Data.PhotoMetadata;
import com.google.wireless.realtimechat.proto.Data.PhotoMetadata.Builder;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public final class BunchCommands
{
  public static final int MAX_EVENTS_PER_REQUEST = (int)Math.ceil(25.0D);
  private static final String SESSION_ID = String.format("%s%08x", arrayOfObject);
  private static short mRequestId = 0;

  static
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = "c:";
    arrayOfObject[1] = Integer.valueOf(new Random().nextInt());
  }

  private static Client.BunchClientRequest.Builder createBunchClientRequestBuilderWithClientId()
  {
    synchronized (SESSION_ID)
    {
      StringBuilder localStringBuilder = new StringBuilder().append(SESSION_ID);
      Object[] arrayOfObject = new Object[1];
      short s = mRequestId;
      mRequestId = (short)(s + 1);
      arrayOfObject[0] = Short.valueOf(s);
      String str2 = String.format(":%x", arrayOfObject);
      Client.BunchClientRequest.Builder localBuilder = Client.BunchClientRequest.newBuilder().setRequestClientId(str2);
      return localBuilder;
    }
  }

  public static Client.BunchClientRequest createConversation(Client.ClientConversation paramClientConversation, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    Client.ChatMessage.Builder localBuilder = Client.ChatMessage.newBuilder();
    localBuilder.setMessageClientId(paramString1);
    localBuilder.setRetry(paramBoolean);
    localBuilder.addContent(Data.Content.newBuilder().setText(paramString2));
    Client.NewConversationRequest.Builder localBuilder1 = Client.NewConversationRequest.newBuilder().setType(paramClientConversation.getType()).setConversationClientId(paramClientConversation.getId()).setRetry(paramBoolean).setChatMessage(localBuilder.build());
    Iterator localIterator = paramClientConversation.getParticipantList().iterator();
    while (localIterator.hasNext())
      localBuilder1.addParticipant((Data.Participant)localIterator.next());
    return createBunchClientRequestBuilderWithClientId().setConversationRequest(localBuilder1).build();
  }

  public static Client.BunchClientRequest createUser(String paramString, long paramLong)
  {
    Client.UserCreationRequest.Builder localBuilder = Client.UserCreationRequest.newBuilder();
    if (paramString != null)
      localBuilder.setDeviceRegistration(Client.DeviceRegistrationRequest.newBuilder().setDeviceType(Client.DeviceRegistrationRequest.DeviceType.ANDROID).setRegistrationType(Client.DeviceRegistrationRequest.RegistrationType.REGISTER).setAndroidRegistrationId(paramString).setAndroidId(paramLong).setLocale(Locale.getDefault().toString()).build());
    return createBunchClientRequestBuilderWithClientId().setUserCreationRequest(localBuilder.build()).build();
  }

  public static Client.BunchClientRequest getConversationList(long paramLong)
  {
    return createBunchClientRequestBuilderWithClientId().setConversationListRequest(Client.ConversationListRequest.newBuilder().setType(Client.ConversationListRequest.Type.SINCE).setTimestamp(paramLong)).build();
  }

  public static Client.BunchClientRequest getConversationListForConversation(String paramString)
  {
    return createBunchClientRequestBuilderWithClientId().setConversationListRequest(Client.ConversationListRequest.newBuilder().setType(Client.ConversationListRequest.Type.ID).setConversationId(paramString)).build();
  }

  public static Client.BunchClientRequest getEventStream(String paramString, long paramLong1, long paramLong2, int paramInt)
  {
    Client.EventStreamRequest.Builder localBuilder = Client.EventStreamRequest.newBuilder().setConversationId(paramString).setCount(paramInt).setOrder(Client.EventStreamRequest.Order.LATEST);
    if (paramLong1 != 0L)
      localBuilder.setStart(paramLong1);
    if (paramLong2 != 0L)
      localBuilder.setEnd(paramLong2);
    return createBunchClientRequestBuilderWithClientId().setEventStreamRequest(localBuilder).build();
  }

  public static Client.BunchClientRequest getOOBSuggestionsRequest()
  {
    return createBunchClientRequestBuilderWithClientId().setSuggestionsRequest(Client.SuggestionsRequest.newBuilder().setSuggestionsType(Client.SuggestionsRequest.SuggestionsType.ONLY_NEW).build()).build();
  }

  public static Client.BunchClientRequest getSuggestionsRequest(Collection<String> paramCollection, Client.SuggestionsRequest.SuggestionsType paramSuggestionsType)
  {
    return createBunchClientRequestBuilderWithClientId().setSuggestionsRequest(Client.SuggestionsRequest.newBuilder().setSuggestionsType(paramSuggestionsType).addAllParticipantId(paramCollection).build()).build();
  }

  public static Client.BunchClientRequest getUserInfo(String paramString)
  {
    Client.UserInfoRequest localUserInfoRequest = Client.UserInfoRequest.newBuilder().setParticipantId(paramString).build();
    return createBunchClientRequestBuilderWithClientId().setUserInfoRequest(localUserInfoRequest).build();
  }

  public static Client.BunchClientRequest inviteParticipants(String paramString, Collection<Data.Participant> paramCollection)
  {
    Client.InviteRequest.Builder localBuilder = Client.InviteRequest.newBuilder().setConversationId(paramString);
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      Data.Participant localParticipant = (Data.Participant)localIterator.next();
      localBuilder.addParticipant(Data.Participant.newBuilder().setParticipantId(localParticipant.getParticipantId()).setFirstName(localParticipant.getFirstName()).setFullName(localParticipant.getFullName()));
    }
    return createBunchClientRequestBuilderWithClientId().setInviteRequest(localBuilder).build();
  }

  public static Client.BunchClientRequest leaveConversation(String paramString)
  {
    return createBunchClientRequestBuilderWithClientId().setLeaveConversationRequest(Client.LeaveConversationRequest.newBuilder().setConversationId(paramString)).build();
  }

  public static Client.BunchClientRequest ping(long paramLong)
  {
    return createBunchClientRequestBuilderWithClientId().setPingRequest(Client.PingRequest.newBuilder().setTimestamp(paramLong).build()).build();
  }

  public static Client.BunchClientRequest presenceRequest(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    Client.BunchClientRequest.Builder localBuilder = createBunchClientRequestBuilderWithClientId();
    Client.PresenceRequest.Builder localBuilder1 = Client.PresenceRequest.newBuilder().setConversationId(paramString).setReciprocate(paramBoolean2);
    if (paramBoolean1);
    for (Client.Presence.Type localType = Client.Presence.Type.FOCUS; ; localType = Client.Presence.Type.UNFOCUS)
      return localBuilder.setPresenceRequest(localBuilder1.setType(localType)).build();
  }

  public static Client.BunchClientRequest replyToInviteRequest(String paramString1, String paramString2)
  {
    Client.ReplyToInviteRequest.Reply localReply = Client.ReplyToInviteRequest.Reply.ACCEPT;
    return createBunchClientRequestBuilderWithClientId().setReplyToInviteRequest(Client.ReplyToInviteRequest.newBuilder().setReply(localReply).setConversationId(paramString1).setReplyToId(paramString2)).build();
  }

  public static Client.BunchClientRequest retry(Client.BunchClientRequest paramBunchClientRequest)
  {
    StringBuilder localStringBuilder = new StringBuilder().append(SESSION_ID);
    Object[] arrayOfObject = new Object[1];
    short s = mRequestId;
    mRequestId = (short)(s + 1);
    arrayOfObject[0] = Short.valueOf(s);
    String str = String.format(":%x", arrayOfObject);
    Client.ChatMessageRequest localChatMessageRequest1;
    Data.Content.Builder localBuilder4;
    Client.BunchClientRequest localBunchClientRequest;
    if (paramBunchClientRequest.hasChatMessageRequest())
    {
      localChatMessageRequest1 = paramBunchClientRequest.getChatMessageRequest();
      Client.ChatMessageRequest.Builder localBuilder3 = Client.ChatMessageRequest.newBuilder();
      localBuilder3.setMessageClientId(localChatMessageRequest1.getMessageClientId());
      localBuilder3.setConversationId(localChatMessageRequest1.getConversationId());
      localBuilder3.setRetry(true);
      if (localChatMessageRequest1.getContentCount() > 0)
      {
        localBuilder4 = Data.Content.newBuilder();
        if (localChatMessageRequest1.getContent(0).hasPhotoUrl())
        {
          localBuilder4.setPhotoMetadata(Data.PhotoMetadata.newBuilder().setUrl(localChatMessageRequest1.getContent(0).getPhotoMetadata().getUrl()));
          localBuilder3.addContent(localBuilder4);
        }
      }
      else
      {
        Client.ChatMessageRequest localChatMessageRequest2 = localBuilder3.build();
        localBunchClientRequest = Client.BunchClientRequest.newBuilder().setRequestClientId(str).setChatMessageRequest(localChatMessageRequest2).build();
      }
    }
    while (true)
    {
      return localBunchClientRequest;
      localBuilder4.setText(localChatMessageRequest1.getContent(0).getText());
      break;
      if (paramBunchClientRequest.hasConversationRequest())
      {
        Client.NewConversationRequest localNewConversationRequest = paramBunchClientRequest.getConversationRequest();
        Client.ChatMessage localChatMessage1 = localNewConversationRequest.getChatMessage();
        Client.ChatMessage.Builder localBuilder = Client.ChatMessage.newBuilder();
        localBuilder.setMessageClientId(localChatMessage1.getMessageClientId());
        localBuilder.setConversationId(localChatMessage1.getConversationId());
        localBuilder.setRetry(true);
        Data.Content.Builder localBuilder2;
        if (localChatMessage1.getContentCount() > 0)
        {
          localBuilder2 = Data.Content.newBuilder();
          if (!localChatMessage1.getContent(0).hasPhotoUrl())
            break label391;
          localBuilder2.setPhotoMetadata(Data.PhotoMetadata.newBuilder().setUrl(localChatMessage1.getContent(0).getPhotoMetadata().getUrl()));
        }
        Client.NewConversationRequest.Builder localBuilder1;
        while (true)
        {
          localBuilder.addContent(localBuilder2);
          Client.ChatMessage localChatMessage2 = localBuilder.build();
          localBuilder1 = Client.NewConversationRequest.newBuilder().setType(localNewConversationRequest.getType()).setConversationClientId(localNewConversationRequest.getConversationClientId()).setRetry(true).setChatMessage(localChatMessage2);
          Iterator localIterator = localNewConversationRequest.getParticipantList().iterator();
          while (localIterator.hasNext())
            localBuilder1.addParticipant((Data.Participant)localIterator.next());
          label391: localBuilder2.setText(localChatMessage1.getContent(0).getText());
        }
        localBunchClientRequest = Client.BunchClientRequest.newBuilder().setRequestClientId(str).setConversationRequest(localBuilder1).build();
      }
      else
      {
        localBunchClientRequest = Client.BunchClientRequest.newBuilder(paramBunchClientRequest).setRequestClientId(str).build();
      }
    }
  }

  public static Client.BunchClientRequest sendMessage(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
  {
    Client.ChatMessageRequest.Builder localBuilder = Client.ChatMessageRequest.newBuilder();
    localBuilder.setMessageClientId(paramString2);
    localBuilder.setConversationId(paramString1);
    localBuilder.setRetry(paramBoolean);
    if (paramString4 != null)
      localBuilder.addContent(Data.Content.newBuilder().setPhotoMetadata(Data.PhotoMetadata.newBuilder().setUrl(paramString4)));
    while (true)
    {
      return createBunchClientRequestBuilderWithClientId().setChatMessageRequest(localBuilder).build();
      localBuilder.addContent(Data.Content.newBuilder().setText(paramString3));
    }
  }

  public static Client.BunchClientRequest sendReadReceipts(String paramString, List<Long> paramList)
  {
    LinkedList localLinkedList = new LinkedList();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      Long localLong = (Long)localIterator.next();
      localLinkedList.add(Client.ReceiptRequest.ReceiptInfo.newBuilder().setEventTimestamp(localLong.longValue()).setType(Client.Receipt.Type.READ).build());
    }
    Client.ReceiptRequest.Builder localBuilder = Client.ReceiptRequest.newBuilder().setConversationId(paramString).addAllReceiptInfo(localLinkedList);
    return createBunchClientRequestBuilderWithClientId().setReceiptRequest(localBuilder.build()).build();
  }

  public static Client.BunchClientRequest sendReceipt(String paramString, long paramLong, Client.Receipt.Type paramType)
  {
    Client.ReceiptRequest.ReceiptInfo localReceiptInfo = Client.ReceiptRequest.ReceiptInfo.newBuilder().setEventTimestamp(paramLong).setType(paramType).build();
    Client.ReceiptRequest.Builder localBuilder = Client.ReceiptRequest.newBuilder().setConversationId(paramString).addReceiptInfo(localReceiptInfo);
    return createBunchClientRequestBuilderWithClientId().setReceiptRequest(localBuilder.build()).build();
  }

  public static Client.BunchClientRequest setAcl(int paramInt)
  {
    return createBunchClientRequestBuilderWithClientId().setSetAclsRequest(Client.SetAclsRequest.newBuilder().setAcl(Client.SetAclsRequest.Acl.valueOf(paramInt))).build();
  }

  public static Client.BunchClientRequest setConversationMuted(String paramString, boolean paramBoolean)
  {
    if (paramBoolean);
    for (Client.ConversationPreferenceRequest.Type localType = Client.ConversationPreferenceRequest.Type.MUTE; ; localType = Client.ConversationPreferenceRequest.Type.UNMUTE)
      return createBunchClientRequestBuilderWithClientId().setConversationPreferenceRequest(Client.ConversationPreferenceRequest.newBuilder().setConversationId(paramString).setType(localType)).build();
  }

  public static Client.BunchClientRequest setConversationName(String paramString1, String paramString2)
  {
    return createBunchClientRequestBuilderWithClientId().setConversationRenameRequest(Client.ConversationRenameRequest.newBuilder().setConversationId(paramString1).setNewDisplayName(paramString2)).build();
  }

  public static Client.BunchClientRequest tileEventRequest(String paramString1, String paramString2, int paramInt, String paramString3, Map<String, String> paramMap)
  {
    Client.TileEventRequest.Builder localBuilder = Client.TileEventRequest.newBuilder().setConversationId(paramString1).setTileType(paramString2).setTileVersion(paramInt).setEventType(paramString3);
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localBuilder.addEventData(Data.KeyValue.newBuilder().setKey((String)localEntry.getKey()).setValue((String)localEntry.getValue()));
    }
    return createBunchClientRequestBuilderWithClientId().setTileEventRequest(localBuilder).build();
  }

  public static Client.BunchClientRequest typingRequest(String paramString, Client.Typing.Type paramType)
  {
    return createBunchClientRequestBuilderWithClientId().setTypingRequest(Client.TypingRequest.newBuilder().setConversationId(paramString).setType(paramType)).build();
  }

  public static Client.BunchClientRequest unregisterDevice$6995facd(long paramLong)
  {
    Client.DeviceRegistrationRequest localDeviceRegistrationRequest = Client.DeviceRegistrationRequest.newBuilder().setDeviceType(Client.DeviceRegistrationRequest.DeviceType.ANDROID).setRegistrationType(Client.DeviceRegistrationRequest.RegistrationType.UNREGISTER).setAndroidId(paramLong).build();
    return createBunchClientRequestBuilderWithClientId().setDeviceRegistrationRequest(localDeviceRegistrationRequest).build();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.BunchCommands
 * JD-Core Version:    0.6.2
 */