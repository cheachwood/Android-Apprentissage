package com.google.android.apps.plus.realtimechat;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsConversationsData;
import com.google.android.apps.plus.content.EsNetworkData;
import com.google.android.apps.plus.network.HttpTransactionMetrics;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.xmpp.GoogleTalkClient;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.wireless.realtimechat.proto.Client.BatchCommand;
import com.google.wireless.realtimechat.proto.Client.BatchCommand.Builder;
import com.google.wireless.realtimechat.proto.Client.BunchClientRequest;
import com.google.wireless.realtimechat.proto.Client.BunchServerResponse;
import com.google.wireless.realtimechat.proto.Client.BunchServerStateUpdate;
import com.google.wireless.realtimechat.proto.Client.ChatMessageRequest;
import com.google.wireless.realtimechat.proto.Client.ChatMessageResponse;
import com.google.wireless.realtimechat.proto.Client.ConversationListRequest;
import com.google.wireless.realtimechat.proto.Client.ConversationListResponse;
import com.google.wireless.realtimechat.proto.Client.ConversationPreferenceResponse;
import com.google.wireless.realtimechat.proto.Client.EventStreamResponse;
import com.google.wireless.realtimechat.proto.Client.InviteResponse;
import com.google.wireless.realtimechat.proto.Client.LeaveConversationResponse;
import com.google.wireless.realtimechat.proto.Client.NewConversationRequest;
import com.google.wireless.realtimechat.proto.Client.NewConversationResponse;
import com.google.wireless.realtimechat.proto.Client.SetAclsRequest.Acl;
import com.google.wireless.realtimechat.proto.Client.SuggestionsResponse;
import com.google.wireless.realtimechat.proto.Client.UserCreationResponse;
import com.google.wireless.realtimechat.proto.Client.UserInfoResponse;
import com.google.wireless.realtimechat.proto.Data.ResponseStatus;
import com.google.wireless.webapps.Version.ClientVersion;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.apache.http.impl.HttpConnectionMetricsImpl;
import org.apache.http.impl.io.HttpTransportMetricsImpl;

public final class BunchClient extends GoogleTalkClient
{
  private Handler mBackgroundHandler;
  private HandlerThread mBackgroundThread;
  private Version.ClientVersion mClientVersion;
  private boolean mConnected = false;
  private Handler.Callback mHandlerCallback = new Handler.Callback()
  {
    public final boolean handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
      case 100:
      case 101:
      }
      while (true)
      {
        return true;
        BunchClient.this.checkResponseReceived((Client.BunchClientRequest)paramAnonymousMessage.obj);
        continue;
        BunchClient.access$000(BunchClient.this, (BunchClient.PendingRequest)paramAnonymousMessage.obj);
      }
    }
  };
  private BunchClientListener mListener;
  private final PendingRequestList mPendingRequestList = new PendingRequestList();
  private final Collection<Pair<Long, Client.BunchClientRequest>> mQueuedCommands = new LinkedList();

  public BunchClient(EsAccount paramEsAccount, Context paramContext, String paramString1, String paramString2, BunchClientListener paramBunchClientListener)
  {
    super(paramEsAccount, paramContext, paramString1, paramString2, "bunch");
    this.mListener = paramBunchClientListener;
  }

  private Client.BatchCommand.Builder createBatchCommandBuilderWithClientVersion()
  {
    return Client.BatchCommand.newBuilder().setClientVersionMessage(this.mClientVersion);
  }

  private static boolean expectResponse(Client.BunchClientRequest paramBunchClientRequest)
  {
    if (paramBunchClientRequest.hasReceiptRequest());
    for (boolean bool = false; ; bool = true)
      return bool;
  }

  private static String getRequestTypeName(Client.BunchClientRequest paramBunchClientRequest)
  {
    String str;
    if (paramBunchClientRequest.hasChatMessageRequest())
      str = "ChatMessageRequest";
    while (true)
    {
      return str;
      if (paramBunchClientRequest.hasConversationJoinRequest())
        str = "ConversationJoinRequest";
      else if (paramBunchClientRequest.hasConversationListRequest())
        str = "ConversationListRequest";
      else if (paramBunchClientRequest.hasConversationPreferenceRequest())
        str = "ConversationPreferenceRequest";
      else if (paramBunchClientRequest.hasConversationRenameRequest())
        str = "ConversationRenameRequest";
      else if (paramBunchClientRequest.hasConversationRequest())
        str = "ConversationRequest";
      else if (paramBunchClientRequest.hasConversationSearchRequest())
        str = "ConversationSearchRequest";
      else if (paramBunchClientRequest.hasDeviceRegistrationRequest())
        str = "DeviceRegistrationRequest";
      else if (paramBunchClientRequest.hasEventSearchRequest())
        str = "EventSearchRequest";
      else if (paramBunchClientRequest.hasEventStreamRequest())
        str = "EventStreamRequest";
      else if (paramBunchClientRequest.hasGlobalConversationPreferencesRequest())
        str = "GlobalConversationPreferencesRequest";
      else if (paramBunchClientRequest.hasHangoutInviteFinishRequest())
        str = "HangoutInviteFinishRequest";
      else if (paramBunchClientRequest.hasHangoutInviteKeepAliveRequest())
        str = "HangoutInviteKeepAliveRequest";
      else if (paramBunchClientRequest.hasHangoutInviteReplyRequest())
        str = "HangoutInviteReplyRequest";
      else if (paramBunchClientRequest.hasHangoutRingFinishRequest())
        str = "HangoutRingFinishRequest";
      else if (paramBunchClientRequest.hasInviteRequest())
        str = "InviteRequest";
      else if (paramBunchClientRequest.hasLeaveConversationRequest())
        str = "LeaveConversationRequest";
      else if (paramBunchClientRequest.hasPingRequest())
        str = "PingRequest";
      else if (paramBunchClientRequest.hasPresenceRequest())
        str = "PresenceRequest";
      else if (paramBunchClientRequest.hasReceiptRequest())
        str = "ReceiptRequest";
      else if (paramBunchClientRequest.hasReplyToInviteRequest())
        str = "ReplyToInviteRequest";
      else if (paramBunchClientRequest.hasSetAclsRequest())
        str = "SetAclsRequest";
      else if (paramBunchClientRequest.hasSuggestionsRequest())
        str = "SuggestionsRequest";
      else if (paramBunchClientRequest.hasTileEventRequest())
        str = "TileEventRequest";
      else if (paramBunchClientRequest.hasTypingRequest())
        str = "TypingRequest";
      else if (paramBunchClientRequest.hasUserCreationRequest())
        str = "UserCreationRequest";
      else if (paramBunchClientRequest.hasUserInfoRequest())
        str = "UserInfoRequest";
      else
        str = "Unknown";
    }
  }

  private static String getResponseTypeName(Client.BunchServerResponse paramBunchServerResponse)
  {
    String str;
    if (paramBunchServerResponse.hasChatMessageResponse())
      str = "ChatMessageResponse";
    while (true)
    {
      return str;
      if (paramBunchServerResponse.hasConversationJoinResponse())
        str = "ConversationJoinResponse";
      else if (paramBunchServerResponse.hasConversationListResponse())
        str = "ConversationListResponse";
      else if (paramBunchServerResponse.hasConversationPreferenceResponse())
        str = "ConversationPreferenceResponse";
      else if (paramBunchServerResponse.hasConversationRenameResponse())
        str = "ConversationRenameResponse";
      else if (paramBunchServerResponse.hasConversationResponse())
        str = "ConversationResponse";
      else if (paramBunchServerResponse.hasConversationSearchResponse())
        str = "ConversationSearchResponse";
      else if (paramBunchServerResponse.hasDeviceRegistrationResponse())
        str = "DeviceRegistrationResponse";
      else if (paramBunchServerResponse.hasError())
        str = "Error";
      else if (paramBunchServerResponse.hasEventSearchResponse())
        str = "EventSearchResponse";
      else if (paramBunchServerResponse.hasEventSteamResponse())
        str = "EventSteamResponse";
      else if (paramBunchServerResponse.hasGlobalConversationPreferencesResponse())
        str = "GlobalConversationPreferencesResponse";
      else if (paramBunchServerResponse.hasHangoutInviteFinishResponse())
        str = "HangoutInviteFinishResponse";
      else if (paramBunchServerResponse.hasHangoutInviteKeepAliveResponse())
        str = "HangoutInviteKeepAliveResponse";
      else if (paramBunchServerResponse.hasHangoutInviteReplyResponse())
        str = "HangoutInviteReplyResponse";
      else if (paramBunchServerResponse.hasHangoutInviteResponse())
        str = "HangoutInviteResponse";
      else if (paramBunchServerResponse.hasHangoutRingFinishResponse())
        str = "HangoutRingFinishResponse";
      else if (paramBunchServerResponse.hasInviteResponse())
        str = "InviteResponse";
      else if (paramBunchServerResponse.hasLeaveConversationResponse())
        str = "LeaveConversationResponse";
      else if (paramBunchServerResponse.hasPingResponse())
        str = "PingResponse";
      else if (paramBunchServerResponse.hasPresenceResponse())
        str = "PresenceResponse";
      else if (paramBunchServerResponse.hasReceiptResponse())
        str = "ReceiptResponse";
      else if (paramBunchServerResponse.hasReplyToInviteResponse())
        str = "ReplyToInviteResponse";
      else if (paramBunchServerResponse.hasSetAclsResponse())
        str = "SetAclsResponse";
      else if (paramBunchServerResponse.hasSuggestionsResponse())
        str = "SuggestionsResponse";
      else if (paramBunchServerResponse.hasTileEventResponse())
        str = "TileEventResponse";
      else if (paramBunchServerResponse.hasTypingResponse())
        str = "TypingResponse";
      else if (paramBunchServerResponse.hasUserCreationResponse())
        str = "UserCreationResponse";
      else if (paramBunchServerResponse.hasUserInfoResponse())
        str = "UserInfoResponse";
      else
        str = "Unknown";
    }
  }

  private void handleError(Client.BunchClientRequest paramBunchClientRequest, Data.ResponseStatus paramResponseStatus, int paramInt1, int paramInt2)
  {
    if ((paramResponseStatus == Data.ResponseStatus.ERROR) || (paramResponseStatus == Data.ResponseStatus.ERROR_UNEXPECTED) || (paramResponseStatus == Data.ResponseStatus.ERROR_TEMPORARY))
      if ((paramInt2 < 3) && (this.mBackgroundHandler != null))
      {
        Client.BunchClientRequest localBunchClientRequest = BunchCommands.retry(paramBunchClientRequest);
        long l = 1000L << paramInt2;
        PendingRequest localPendingRequest = new PendingRequest(paramInt1, localBunchClientRequest, SystemClock.elapsedRealtime(), paramInt2 + 1);
        Message localMessage = this.mBackgroundHandler.obtainMessage(101, localPendingRequest);
        this.mBackgroundHandler.sendMessageDelayed(localMessage, l);
        if (EsLog.isLoggable("BunchClient", 5))
          Log.w("BunchClient", "Bunch server error: " + paramBunchClientRequest.getRequestClientId() + " " + paramResponseStatus + " retrying in " + l);
      }
    while (true)
    {
      return;
      if (EsLog.isLoggable("BunchClient", 5))
      {
        Log.w("BunchClient", "Bunch server error: " + paramBunchClientRequest.getRequestClientId() + " " + paramResponseStatus + " giving up");
        continue;
        if (EsLog.isLoggable("BunchClient", 5))
          Log.w("BunchClient", "Bunch server error: " + paramBunchClientRequest.getRequestClientId() + " " + paramResponseStatus + " fatal");
      }
    }
  }

  private void processResponse(Client.BunchServerResponse paramBunchServerResponse, RealTimeChatOperationState paramRealTimeChatOperationState, List<RealTimeChatServiceResult> paramList)
  {
    String str = paramBunchServerResponse.getRequestClientId();
    while (true)
    {
      PendingRequest localPendingRequest2;
      try
      {
        long l1 = SystemClock.elapsedRealtime() - 90000L;
        Iterator localIterator = this.mPendingRequestList.trimOutdatedRequestIds(l1).iterator();
        if (!localIterator.hasNext())
          break;
        localPendingRequest2 = (PendingRequest)localIterator.next();
        if (localPendingRequest2 == null)
          continue;
        if (retryOnTimeout(localPendingRequest2.mRequest))
        {
          this.mPendingRequestList.addRequest(localPendingRequest2.mRequest.getRequestClientId(), localPendingRequest2);
          continue;
        }
      }
      finally
      {
      }
      if (EsLog.isLoggable("BunchClient", 4))
      {
        Log.i("BunchClient", "request " + getRequestTypeName(localPendingRequest2.mRequest) + " type [" + localPendingRequest2.mRequest.getRequestClientId() + "] timed out");
        TimedOutException localTimedOutException = new TimedOutException((byte)0);
        EsNetworkData.insertData(getContext(), getAccount(), localPendingRequest2.mMetrics, localTimedOutException);
      }
    }
    PendingRequest localPendingRequest1 = this.mPendingRequestList.getData(str);
    if (localPendingRequest1 != null)
      this.mPendingRequestList.removeRequest(localPendingRequest1.mRequest.getRequestClientId());
    long l2 = SystemClock.elapsedRealtime();
    int i;
    int j;
    Client.BunchClientRequest localBunchClientRequest;
    BunchClientListener localBunchClientListener;
    Data.ResponseStatus localResponseStatus1;
    Client.UserInfoResponse localUserInfoResponse;
    SharedPreferences.Editor localEditor;
    if (localPendingRequest1 != null)
    {
      if (EsLog.isLoggable("BunchClient", 4))
        Log.i("BunchClient", "Received " + getResponseTypeName(paramBunchServerResponse) + " [" + paramBunchServerResponse.getRequestClientId() + "] processing");
      RealTimeChatServiceResult localRealTimeChatServiceResult = new RealTimeChatServiceResult(localPendingRequest1.mRequestId, 1, paramBunchServerResponse);
      paramList.add(localRealTimeChatServiceResult);
      HttpTransportMetricsImpl localHttpTransportMetricsImpl1 = new HttpTransportMetricsImpl();
      HttpTransportMetricsImpl localHttpTransportMetricsImpl2 = new HttpTransportMetricsImpl();
      localHttpTransportMetricsImpl1.setBytesTransferred(paramBunchServerResponse.getSerializedSize());
      localHttpTransportMetricsImpl2.setBytesTransferred(localPendingRequest1.mRequest.getSerializedSize());
      HttpConnectionMetricsImpl localHttpConnectionMetricsImpl = new HttpConnectionMetricsImpl(localHttpTransportMetricsImpl1, localHttpTransportMetricsImpl2);
      localHttpConnectionMetricsImpl.incrementRequestCount();
      localHttpConnectionMetricsImpl.incrementResponseCount();
      localPendingRequest1.mMetrics.setConnectionMetrics(localHttpConnectionMetricsImpl);
      localPendingRequest1.mMetrics.onEndTransaction();
      localPendingRequest1.mMetrics.onStartResultProcessing();
      i = localPendingRequest1.mRequestId;
      j = localPendingRequest1.mRetryCount;
      localBunchClientRequest = localPendingRequest1.mRequest;
      localBunchClientListener = this.mListener;
      localResponseStatus1 = Data.ResponseStatus.OK;
      if (!paramBunchServerResponse.hasUserInfoResponse())
        break label859;
      localUserInfoResponse = paramBunchServerResponse.getUserInfoResponse();
      localEditor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
      if (EsLog.isLoggable("BunchClient", 3))
        Log.d("BunchClient", "updateAcl " + localUserInfoResponse.getAcl());
      if (localUserInfoResponse.getAcl().getNumber() != 1)
        break label773;
      localEditor.putString(getContext().getString(R.string.realtimechat_acl_key), getContext().getString(R.string.key_acl_setting_anyone));
      localEditor.commit();
    }
    while (true)
    {
      long l3 = SystemClock.elapsedRealtime();
      localPendingRequest1.mMetrics.onEndResultProcessing();
      Long localLong = Long.valueOf(localPendingRequest1.mTimestamp);
      if ((EsLog.isLoggable("BunchClient", 4)) && (localPendingRequest1.mRequest != null))
        Log.i("BunchClient", "command type [" + getRequestTypeName(localPendingRequest1.mRequest) + "] roundTripTime " + (l2 - localLong.longValue()) + " ms processingTime " + (l3 - l2) + " inBytes " + paramBunchServerResponse.getSerializedSize() + " outBytes " + localPendingRequest1.mRequest.getSerializedSize());
      Data.ResponseStatus localResponseStatus2 = Data.ResponseStatus.OK;
      if (localResponseStatus1 != localResponseStatus2)
        new ResponseFailedException(localResponseStatus1);
      EsNetworkData.insertData(getContext(), getAccount(), localPendingRequest1.mMetrics, null);
      while (true)
      {
        return;
        if (EsLog.isLoggable("BunchClient", 4))
          Log.i("BunchClient", "Received " + getResponseTypeName(paramBunchServerResponse) + " [" + paramBunchServerResponse.getRequestClientId() + "] ignoring");
      }
      label773: if (localUserInfoResponse.getAcl().getNumber() == 2)
      {
        localEditor.putString(getContext().getString(R.string.realtimechat_acl_key), getContext().getString(R.string.key_acl_setting_extended_circles));
        break;
      }
      if (localUserInfoResponse.getAcl().getNumber() != 3)
        break;
      localEditor.putString(getContext().getString(R.string.realtimechat_acl_key), getContext().getString(R.string.key_acl_setting_my_circles));
      break;
      label859: if ((!paramBunchServerResponse.hasSetAclsResponse()) && (!paramBunchServerResponse.hasTypingResponse()) && (!paramBunchServerResponse.hasPresenceResponse()) && (!paramBunchServerResponse.hasTileEventResponse()) && (!paramBunchServerResponse.hasReceiptResponse()))
        if (paramBunchServerResponse.hasPingResponse())
        {
          if (EsLog.isLoggable("BunchClient", 4))
            Log.i("BunchClient", "Ping response from backend");
          if (localBunchClientListener != null)
            localBunchClientListener.onPingReceived(this);
        }
        else if (paramBunchServerResponse.hasUserCreationResponse())
        {
          Client.UserCreationResponse localUserCreationResponse = paramBunchServerResponse.getUserCreationResponse();
          localResponseStatus1 = localUserCreationResponse.getStatus();
          Data.ResponseStatus localResponseStatus11 = Data.ResponseStatus.OK;
          if (localResponseStatus1 != localResponseStatus11)
            handleError(localBunchClientRequest, localResponseStatus1, i, j);
          else
            EsConversationsData.processUserCreationResponse(getContext(), getAccount(), localUserCreationResponse, paramRealTimeChatOperationState);
        }
        else if (paramBunchServerResponse.hasSuggestionsResponse())
        {
          Client.SuggestionsResponse localSuggestionsResponse = paramBunchServerResponse.getSuggestionsResponse();
          localResponseStatus1 = localSuggestionsResponse.getStatus();
          Data.ResponseStatus localResponseStatus10 = Data.ResponseStatus.OK;
          if (localResponseStatus1 != localResponseStatus10)
            handleError(localBunchClientRequest, localResponseStatus1, i, j);
          else
            EsConversationsData.processSuggestionsResponse$541cf8e7(getContext(), getAccount(), localSuggestionsResponse, localBunchClientRequest);
        }
        else if (paramBunchServerResponse.hasConversationListResponse())
        {
          Client.ConversationListResponse localConversationListResponse = paramBunchServerResponse.getConversationListResponse();
          localResponseStatus1 = localConversationListResponse.getStatus();
          Data.ResponseStatus localResponseStatus9 = Data.ResponseStatus.OK;
          if (localResponseStatus1 != localResponseStatus9)
            handleError(localBunchClientRequest, localResponseStatus1, i, j);
          else
            EsConversationsData.processConversationListResponse(getContext(), getAccount(), localConversationListResponse, paramRealTimeChatOperationState);
        }
        else if (paramBunchServerResponse.hasEventSteamResponse())
        {
          Client.EventStreamResponse localEventStreamResponse = paramBunchServerResponse.getEventSteamResponse();
          localResponseStatus1 = localEventStreamResponse.getStatus();
          Data.ResponseStatus localResponseStatus8 = Data.ResponseStatus.OK;
          if (localResponseStatus1 != localResponseStatus8)
            handleError(localBunchClientRequest, localResponseStatus1, i, j);
          else
            EsConversationsData.processEventStreamResponse(getContext(), getAccount(), localEventStreamResponse, paramRealTimeChatOperationState);
        }
        else if (paramBunchServerResponse.hasConversationResponse())
        {
          Client.NewConversationResponse localNewConversationResponse = paramBunchServerResponse.getConversationResponse();
          localResponseStatus1 = localNewConversationResponse.getStatus();
          Data.ResponseStatus localResponseStatus7 = Data.ResponseStatus.OK;
          if (localResponseStatus1 != localResponseStatus7)
            handleError(localBunchClientRequest, localResponseStatus1, i, j);
          else
            EsConversationsData.processConversationResponse(getContext(), getAccount(), localNewConversationResponse, paramRealTimeChatOperationState);
        }
        else if (paramBunchServerResponse.hasChatMessageResponse())
        {
          Client.ChatMessageResponse localChatMessageResponse = paramBunchServerResponse.getChatMessageResponse();
          localResponseStatus1 = localChatMessageResponse.getStatus();
          Data.ResponseStatus localResponseStatus6 = Data.ResponseStatus.OK;
          if (localResponseStatus1 != localResponseStatus6)
            handleError(localBunchClientRequest, localResponseStatus1, i, j);
          else
            EsConversationsData.processChatMessageResponse(getContext(), getAccount(), localChatMessageResponse, paramRealTimeChatOperationState);
        }
        else if (paramBunchServerResponse.hasInviteResponse())
        {
          Client.InviteResponse localInviteResponse = paramBunchServerResponse.getInviteResponse();
          localResponseStatus1 = localInviteResponse.getStatus();
          Data.ResponseStatus localResponseStatus5 = Data.ResponseStatus.OK;
          if (localResponseStatus1 != localResponseStatus5)
            handleError(localBunchClientRequest, localResponseStatus1, i, j);
          else
            EsConversationsData.processInviteResponse(getContext(), getAccount(), localInviteResponse, localPendingRequest1.mRequest, paramRealTimeChatOperationState);
        }
        else if (paramBunchServerResponse.hasConversationPreferenceResponse())
        {
          Client.ConversationPreferenceResponse localConversationPreferenceResponse = paramBunchServerResponse.getConversationPreferenceResponse();
          localResponseStatus1 = localConversationPreferenceResponse.getStatus();
          Data.ResponseStatus localResponseStatus4 = Data.ResponseStatus.OK;
          if (localResponseStatus1 != localResponseStatus4)
          {
            handleError(localBunchClientRequest, localResponseStatus1, i, j);
          }
          else
          {
            getContext();
            getAccount();
            EsConversationsData.processConversationPreferenceResponse$43e73c50(localConversationPreferenceResponse);
          }
        }
        else if (paramBunchServerResponse.hasLeaveConversationResponse())
        {
          localResponseStatus1 = paramBunchServerResponse.getLeaveConversationResponse().getStatus();
          Data.ResponseStatus localResponseStatus3 = Data.ResponseStatus.OK;
          if (localResponseStatus1 != localResponseStatus3)
            handleError(localBunchClientRequest, localResponseStatus1, i, j);
          else
            EsConversationsData.processLeaveConversationResponse$6cb3bb58(getContext(), getAccount(), localPendingRequest1.mRequest);
        }
        else if (EsLog.isLoggable("BunchClient", 5))
        {
          Log.w("BunchClient", "Unexpected response from bunch server");
        }
    }
  }

  private static boolean retryOnTimeout(Client.BunchClientRequest paramBunchClientRequest)
  {
    if ((paramBunchClientRequest.hasUserCreationRequest()) || (paramBunchClientRequest.hasConversationListRequest()) || (paramBunchClientRequest.hasEventStreamRequest()));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private void sendQueuedCommands()
  {
    Client.BatchCommand.Builder localBuilder;
    while (true)
    {
      Pair localPair;
      PendingRequest localPendingRequest;
      try
      {
        if (EsLog.isLoggable("BunchClient", 3))
          Log.d("BunchClient", "Sending " + this.mQueuedCommands.size() + " pending commands");
        synchronized (this.mQueuedCommands)
        {
          localBuilder = createBatchCommandBuilderWithClientVersion();
          Iterator localIterator = this.mQueuedCommands.iterator();
          if (!localIterator.hasNext())
            break;
          localPair = (Pair)localIterator.next();
          if ((((Client.BunchClientRequest)localPair.second).hasChatMessageRequest()) && (SystemClock.elapsedRealtime() - ((Long)localPair.first).longValue() > 20000L))
          {
            i = 0;
            if (i == 0)
              break label263;
            localBuilder.addRequest((Client.BunchClientRequest)localPair.second);
            localPendingRequest = this.mPendingRequestList.getData(((Client.BunchClientRequest)localPair.second).getRequestClientId());
            if (localPendingRequest != null)
              break label214;
            if (!EsLog.isLoggable("BunchClient", 5))
              continue;
            Log.w("BunchClient", "null pendingRequest in sendQueuedCommand");
          }
        }
      }
      finally
      {
      }
      int i = 1;
      continue;
      label214: localPendingRequest.mMetrics = new HttpTransactionMetrics();
      localPendingRequest.mMetrics.onBeginTransaction("RealTimeChat:" + getRequestTypeName((Client.BunchClientRequest)localPair.second));
      continue;
      label263: if (EsLog.isLoggable("BunchClient", 3))
        Log.d("BunchClient", "dropping outdated command");
      this.mPendingRequestList.removeRequest(((Client.BunchClientRequest)localPair.second).getRequestClientId());
    }
    sendMessage(localBuilder.build().toByteArray());
    this.mQueuedCommands.clear();
  }

  private boolean shouldEnqueueIfDisconnected(Client.BunchClientRequest paramBunchClientRequest)
  {
    boolean bool1 = false;
    if (paramBunchClientRequest == null);
    while (true)
    {
      return bool1;
      while (true)
      {
        try
        {
          if ((!paramBunchClientRequest.hasInviteRequest()) && (!paramBunchClientRequest.hasEventStreamRequest()) && (!paramBunchClientRequest.hasConversationRenameRequest()) && (!paramBunchClientRequest.hasLeaveConversationRequest()) && (!paramBunchClientRequest.hasReceiptRequest()) && (!paramBunchClientRequest.hasReplyToInviteRequest()) && (!paramBunchClientRequest.hasSuggestionsRequest()))
          {
            if (paramBunchClientRequest.hasSetAclsRequest())
              continue;
            if (paramBunchClientRequest.hasChatMessageRequest())
            {
              Client.ChatMessageRequest localChatMessageRequest = paramBunchClientRequest.getChatMessageRequest();
              if (!localChatMessageRequest.hasMessageClientId())
                break label341;
              str3 = localChatMessageRequest.getMessageClientId();
              Iterator localIterator2 = this.mQueuedCommands.iterator();
              if (!localIterator2.hasNext())
                break label353;
              Client.BunchClientRequest localBunchClientRequest2 = (Client.BunchClientRequest)((Pair)localIterator2.next()).second;
              if (!localBunchClientRequest2.hasChatMessageRequest())
                continue;
              if (!localBunchClientRequest2.getChatMessageRequest().hasMessageClientId())
                break label347;
              str4 = localBunchClientRequest2.getChatMessageRequest().getMessageClientId();
              if ((str4 == null) || (!str4.equals(str3)))
                continue;
              bool1 = false;
              break;
            }
            boolean bool2 = paramBunchClientRequest.hasConversationRequest();
            bool1 = false;
            if (!bool2)
              break;
            Client.NewConversationRequest localNewConversationRequest1 = paramBunchClientRequest.getConversationRequest();
            if (localNewConversationRequest1.hasConversationClientId())
            {
              str1 = localNewConversationRequest1.getConversationClientId();
              Iterator localIterator1 = this.mQueuedCommands.iterator();
              if (!localIterator1.hasNext())
                continue;
              Client.BunchClientRequest localBunchClientRequest1 = (Client.BunchClientRequest)((Pair)localIterator1.next()).second;
              if (!localBunchClientRequest1.hasChatMessageRequest())
                continue;
              Client.NewConversationRequest localNewConversationRequest2 = localBunchClientRequest1.getConversationRequest();
              if (!localNewConversationRequest2.hasConversationClientId())
                continue;
              str2 = localNewConversationRequest2.getConversationClientId();
              if (str2 == null)
                continue;
              boolean bool3 = str2.equals(str1);
              if (!bool3)
                continue;
              bool1 = false;
              break;
            }
            String str1 = null;
            continue;
            String str2 = null;
            continue;
            continue;
            bool1 = true;
          }
        }
        finally
        {
        }
        break;
        label341: String str3 = null;
        continue;
        label347: String str4 = null;
      }
      label353: bool1 = true;
    }
  }

  public final void checkResponseReceived(Client.BunchClientRequest paramBunchClientRequest)
  {
    while (true)
    {
      try
      {
        PendingRequest localPendingRequest = this.mPendingRequestList.getData(paramBunchClientRequest.getRequestClientId());
        Client.BunchClientRequest localBunchClientRequest = null;
        if (localPendingRequest != null)
        {
          this.mPendingRequestList.removeRequest(paramBunchClientRequest.getRequestClientId());
          TimedOutException localTimedOutException = new TimedOutException((byte)0);
          EsNetworkData.insertData(getContext(), getAccount(), localPendingRequest.mMetrics, localTimedOutException);
          if (EsLog.isLoggable("BunchClient", 4))
            Log.i("BunchClient", "Retrying command " + getRequestTypeName(paramBunchClientRequest) + " [" + paramBunchClientRequest.getRequestClientId() + "] expecting response");
          int i = 1 + localPendingRequest.mRetryCount;
          if (i < 3)
          {
            localBunchClientRequest = BunchCommands.retry(paramBunchClientRequest);
            int j = localPendingRequest.mRequestId;
            int k = i + 1;
            LinkedList localLinkedList = new LinkedList();
            localLinkedList.add(localBunchClientRequest);
            sendCommands(localLinkedList, j, k);
          }
        }
        else
        {
          if (localBunchClientRequest == null)
            break label352;
          boolean bool1 = this.mConnected;
          boolean bool2 = false;
          if (bool1)
            bool2 = sendMessage(createBatchCommandBuilderWithClientVersion().addRequest(localBunchClientRequest).build().toByteArray());
          if (bool2)
            break label332;
          if (shouldEnqueueIfDisconnected(paramBunchClientRequest))
          {
            if (EsLog.isLoggable("BunchClient", 3))
              Log.d("BunchClient", "queueing");
            this.mQueuedCommands.add(new Pair(Long.valueOf(SystemClock.elapsedRealtime()), paramBunchClientRequest));
          }
          return;
        }
        boolean bool3 = EsLog.isLoggable("BunchClient", 5);
        localBunchClientRequest = null;
        if (!bool3)
          continue;
        Log.w("BunchClient", "Bunch request timeout " + localPendingRequest.mRequest.getRequestClientId() + " giving up");
        localBunchClientRequest = null;
        continue;
      }
      finally
      {
      }
      label332: if (EsLog.isLoggable("BunchClient", 3))
      {
        Log.d("BunchClient", "sent");
        continue;
        label352: if (EsLog.isLoggable("BunchClient", 3))
          Log.d("BunchClient", "response received for " + paramBunchClientRequest.getRequestClientId());
      }
    }
  }

  public final boolean connected()
  {
    try
    {
      boolean bool = this.mConnected;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public final void disconnect()
  {
    try
    {
      this.mQueuedCommands.clear();
      this.mPendingRequestList.clear();
      this.mListener = null;
      if (this.mBackgroundThread != null)
      {
        this.mBackgroundThread.quit();
        this.mBackgroundThread = null;
      }
      this.mConnected = false;
      super.disconnect();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public final boolean hasPendingCommands()
  {
    boolean bool1 = true;
    while (true)
    {
      PendingRequest localPendingRequest;
      try
      {
        long l = SystemClock.elapsedRealtime() - 90000L;
        Iterator localIterator = this.mPendingRequestList.trimOutdatedRequestIds(l).iterator();
        if (!localIterator.hasNext())
          break;
        localPendingRequest = (PendingRequest)localIterator.next();
        if (localPendingRequest == null)
          continue;
        if (retryOnTimeout(localPendingRequest.mRequest))
        {
          this.mPendingRequestList.addRequest(localPendingRequest.mRequest.getRequestClientId(), localPendingRequest);
          continue;
        }
      }
      finally
      {
      }
      this.mPendingRequestList.removeRequest(localPendingRequest.mRequest.getRequestClientId());
      if (EsLog.isLoggable("BunchClient", 4))
        Log.i("BunchClient", "request " + getRequestTypeName(localPendingRequest.mRequest) + " type [" + localPendingRequest.mRequest.getRequestClientId() + "] timed out");
      TimedOutException localTimedOutException = new TimedOutException((byte)0);
      EsNetworkData.insertData(getContext(), getAccount(), localPendingRequest.mMetrics, localTimedOutException);
    }
    boolean bool2 = this.mQueuedCommands.isEmpty();
    if (!bool2);
    while (true)
    {
      return bool1;
      if ((EsLog.isLoggable("BunchClient", 2)) && (!this.mPendingRequestList.isEmpty()))
      {
        Log.v("BunchClient", "hasPendingCommands");
        this.mPendingRequestList.dump();
      }
      boolean bool3 = this.mPendingRequestList.isEmpty();
      if (bool3)
        bool1 = false;
    }
  }

  protected final void onConnected()
  {
    try
    {
      if (EsLog.isLoggable("BunchClient", 3))
        Log.d("BunchClient", "onConnected");
      this.mConnected = true;
      BunchClientListener localBunchClientListener = this.mListener;
      if (localBunchClientListener != null)
        localBunchClientListener.onConnected(this);
      this.mBackgroundThread = new HandlerThread("BunchHandlerThread");
      this.mBackgroundThread.start();
      this.mBackgroundHandler = new Handler(this.mBackgroundThread.getLooper(), this.mHandlerCallback);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  protected final void onDisconnected(int paramInt)
  {
    try
    {
      if (EsLog.isLoggable("BunchClient", 3))
        Log.d("BunchClient", "Disconnected from server");
      Iterator localIterator = this.mPendingRequestList.getRequestIds().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        PendingRequest localPendingRequest = this.mPendingRequestList.getData(str);
        if (localPendingRequest != null)
        {
          if (EsLog.isLoggable("BunchClient", 4))
            Log.i("BunchClient", "request " + getRequestTypeName(localPendingRequest.mRequest) + " type [" + str + "] failed due to disconnect");
          this.mPendingRequestList.removeRequest(str);
        }
      }
    }
    finally
    {
    }
    if (this.mBackgroundThread != null)
    {
      this.mBackgroundThread.quit();
      this.mBackgroundThread = null;
    }
    if (this.mBackgroundHandler != null)
    {
      this.mBackgroundHandler.removeMessages(101);
      this.mBackgroundHandler.removeMessages(100);
      this.mBackgroundHandler = null;
    }
    this.mConnected = false;
    BunchClientListener localBunchClientListener = this.mListener;
    if (localBunchClientListener != null)
      localBunchClientListener.onDisconnected(this, paramInt);
  }

  protected final void onMessageReceived(byte[] paramArrayOfByte)
  {
    Client.BatchCommand localBatchCommand;
    RealTimeChatOperationState localRealTimeChatOperationState;
    LinkedList localLinkedList1;
    int i;
    String str;
    while (true)
    {
      Client.BunchServerStateUpdate localBunchServerStateUpdate;
      try
      {
        if (!this.mConnected)
        {
          if (EsLog.isLoggable("BunchClient", 3))
            Log.w("BunchClient", "Message received after disconnect");
          return;
        }
        try
        {
          localBatchCommand = Client.BatchCommand.parseFrom(paramArrayOfByte);
          localRealTimeChatOperationState = new RealTimeChatOperationState(RealTimeChatService.getCurrentConversationRowId());
          localLinkedList1 = new LinkedList();
          i = 0;
          Iterator localIterator1 = localBatchCommand.getStateUpdateList().iterator();
          if (!localIterator1.hasNext())
            break;
          localBunchServerStateUpdate = (Client.BunchServerStateUpdate)localIterator1.next();
          if (EsLog.isLoggable("BunchClient", 4))
          {
            StringBuilder localStringBuilder = new StringBuilder("Received stateUpdate ");
            if (!localBunchServerStateUpdate.hasChatMessage())
              continue;
            str = "ChatMessage";
            label130: Log.i("BunchClient", str);
          }
          EsConversationsData.processBunchServerUpdate(getContext(), getAccount(), localBunchServerStateUpdate, localRealTimeChatOperationState);
          continue;
        }
        catch (InvalidProtocolBufferException localInvalidProtocolBufferException)
        {
        }
        if (!EsLog.isLoggable("BunchClient", 5))
          continue;
        Log.w("BunchClient", "Invalid BatchCommand message received");
      }
      finally
      {
      }
      str = "GroupConversationRename";
      continue;
      if (localBunchServerStateUpdate.hasInvalidateLocalCache())
      {
        str = "InvalidateLocalCache";
      }
      else if (localBunchServerStateUpdate.hasMembershipChange())
      {
        str = "MembershipChange";
      }
      else if (localBunchServerStateUpdate.hasMigration())
      {
        str = "Migration";
      }
      else if (localBunchServerStateUpdate.hasNewConversation())
      {
        str = "NewConversation";
      }
      else if (localBunchServerStateUpdate.hasPresence())
      {
        str = "Presence";
      }
      else if (localBunchServerStateUpdate.hasReceipt())
      {
        str = "Receipt";
      }
      else if (localBunchServerStateUpdate.hasTileEvent())
      {
        str = "TileEvent";
      }
      else
      {
        if (!localBunchServerStateUpdate.hasTyping())
          break label733;
        str = "Typing";
      }
    }
    Iterator localIterator2 = localBatchCommand.getResponseList().iterator();
    int m;
    while (localIterator2.hasNext())
    {
      Client.BunchServerResponse localBunchServerResponse = (Client.BunchServerResponse)localIterator2.next();
      if (!localBunchServerResponse.hasUserCreationResponse())
        break label726;
      m = 1;
      label380: processResponse(localBunchServerResponse, localRealTimeChatOperationState, localLinkedList1);
      i = m;
    }
    if (localRealTimeChatOperationState.shouldTriggerNotifications())
      RealTimeChatNotifications.update(getContext(), getAccount(), false);
    if (localRealTimeChatOperationState.getClientVersionChanged())
      updateClientVersion();
    LinkedList localLinkedList2 = new LinkedList();
    label663: label676: label720: label726: label733: label741: label752: label756: 
    while (true)
    {
      Client.BunchClientRequest localBunchClientRequest;
      Client.ConversationListRequest localConversationListRequest1;
      Client.ConversationListRequest localConversationListRequest2;
      int k;
      int j;
      try
      {
        Iterator localIterator3 = localRealTimeChatOperationState.getRequests().iterator();
        if (!localIterator3.hasNext())
          break label676;
        localBunchClientRequest = (Client.BunchClientRequest)localIterator3.next();
        if (!localBunchClientRequest.hasConversationListRequest())
          break label663;
        Iterator localIterator4 = this.mPendingRequestList.iterator();
        if (!localIterator4.hasNext())
          break label720;
        PendingRequest localPendingRequest = (PendingRequest)localIterator4.next();
        if ((localPendingRequest == null) || (localPendingRequest.mRequest == null) || (!localPendingRequest.mRequest.hasConversationListRequest()))
          continue;
        localConversationListRequest1 = localPendingRequest.mRequest.getConversationListRequest();
        localConversationListRequest2 = localBunchClientRequest.getConversationListRequest();
        if (localConversationListRequest1.getType() != localConversationListRequest2.getType())
          break label752;
        if ((localConversationListRequest1.hasTimestamp()) && (localConversationListRequest2.hasTimestamp()) && (localConversationListRequest1.getTimestamp() <= localConversationListRequest2.getTimestamp()))
        {
          k = 1;
          break label741;
          if (j != 0)
            continue;
          localLinkedList2.add(localBunchClientRequest);
          continue;
        }
      }
      finally
      {
      }
      if ((localConversationListRequest1.hasConversationId()) && (localConversationListRequest2.hasConversationId()) && (localConversationListRequest1.getConversationId() == localConversationListRequest2.getConversationId()))
      {
        k = 1;
        break label741;
        localLinkedList2.add(localBunchClientRequest);
        continue;
        if (i != 0)
          sendQueuedCommands();
        sendCommands(localLinkedList2, -1, 0);
        BunchClientListener localBunchClientListener = this.mListener;
        if (localBunchClientListener == null)
          break;
        localBunchClientListener.onResultsReceived(this, localLinkedList1);
        break;
        j = 0;
        continue;
        m = i;
        break label380;
        str = "Unknown";
        break label130;
      }
      while (true)
      {
        if (k == 0)
          break label756;
        j = 1;
        break;
        k = 0;
      }
    }
  }

  public final boolean sendCommand(Client.BunchClientRequest paramBunchClientRequest, int paramInt)
  {
    LinkedList localLinkedList = new LinkedList();
    localLinkedList.add(paramBunchClientRequest);
    return sendCommands(localLinkedList, paramInt, 0);
  }

  public final boolean sendCommands(Collection<Client.BunchClientRequest> paramCollection, int paramInt1, int paramInt2)
  {
    if (EsLog.isLoggable("BunchClient", 3))
      Log.d("BunchClient", "sendCommands " + paramInt1);
    while (true)
    {
      Client.BunchClientRequest localBunchClientRequest3;
      try
      {
        Iterator localIterator1 = paramCollection.iterator();
        if (!localIterator1.hasNext())
          break;
        localBunchClientRequest3 = (Client.BunchClientRequest)localIterator1.next();
        if (expectResponse(localBunchClientRequest3))
        {
          if (EsLog.isLoggable("BunchClient", 4))
            Log.i("BunchClient", "Sending command " + getRequestTypeName(localBunchClientRequest3) + " [" + localBunchClientRequest3.getRequestClientId() + "] expecting response");
          PendingRequest localPendingRequest = new PendingRequest(paramInt1, localBunchClientRequest3, SystemClock.elapsedRealtime(), paramInt2);
          localPendingRequest.mMetrics = new HttpTransactionMetrics();
          localPendingRequest.mMetrics.onBeginTransaction("RealTimeChat:" + getRequestTypeName(localBunchClientRequest3));
          this.mPendingRequestList.addRequest(localBunchClientRequest3.getRequestClientId(), localPendingRequest);
          continue;
        }
      }
      finally
      {
      }
      if (EsLog.isLoggable("BunchClient", 4))
        Log.i("BunchClient", "Sending command " + getRequestTypeName(localBunchClientRequest3) + " [" + localBunchClientRequest3.getRequestClientId() + "] not expecting response");
    }
    boolean bool1 = this.mConnected;
    boolean bool2 = false;
    if (bool1)
    {
      bool2 = sendMessage(createBatchCommandBuilderWithClientVersion().addAllRequest(paramCollection).build().toByteArray());
      Iterator localIterator3 = paramCollection.iterator();
      while (localIterator3.hasNext())
      {
        Client.BunchClientRequest localBunchClientRequest2 = (Client.BunchClientRequest)localIterator3.next();
        if ((retryOnTimeout(localBunchClientRequest2)) && (this.mBackgroundHandler != null))
        {
          long l = 15000L << paramInt2;
          Message localMessage = this.mBackgroundHandler.obtainMessage(100, localBunchClientRequest2);
          this.mBackgroundHandler.sendMessageDelayed(localMessage, l);
          if (EsLog.isLoggable("BunchClient", 5))
            Log.w("BunchClient", "Bunch request timeout " + localBunchClientRequest2.getRequestClientId() + " checking in " + l);
        }
      }
    }
    if (!bool2)
    {
      Iterator localIterator2 = paramCollection.iterator();
      while (localIterator2.hasNext())
      {
        Client.BunchClientRequest localBunchClientRequest1 = (Client.BunchClientRequest)localIterator2.next();
        if (shouldEnqueueIfDisconnected(localBunchClientRequest1))
        {
          if (EsLog.isLoggable("BunchClient", 3))
            Log.d("BunchClient", "queueing");
          this.mQueuedCommands.add(new Pair(Long.valueOf(SystemClock.elapsedRealtime()), localBunchClientRequest1));
        }
      }
    }
    if (EsLog.isLoggable("BunchClient", 3))
      Log.d("BunchClient", "sent");
    return bool2;
  }

  public final void sendKeepAlive()
  {
    try
    {
      if (EsLog.isLoggable("BunchClient", 3))
        Log.d("BunchClient", "Sending ping to bunch");
      sendCommand(BunchCommands.ping(1000L * System.currentTimeMillis()), -1);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  // ERROR //
  public final void updateClientVersion()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iconst_0
    //   3: istore_1
    //   4: aload_0
    //   5: invokevirtual 596	com/google/android/apps/plus/realtimechat/BunchClient:getContext	()Landroid/content/Context;
    //   8: invokevirtual 1144	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   11: aload_0
    //   12: invokevirtual 596	com/google/android/apps/plus/realtimechat/BunchClient:getContext	()Landroid/content/Context;
    //   15: invokevirtual 1147	android/content/Context:getPackageName	()Ljava/lang/String;
    //   18: iconst_0
    //   19: invokevirtual 1153	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   22: getfield 1158	android/content/pm/PackageInfo:versionCode	I
    //   25: istore_1
    //   26: aload_0
    //   27: invokevirtual 596	com/google/android/apps/plus/realtimechat/BunchClient:getContext	()Landroid/content/Context;
    //   30: aload_0
    //   31: invokevirtual 600	com/google/android/apps/plus/realtimechat/BunchClient:getAccount	()Lcom/google/android/apps/plus/content/EsAccount;
    //   34: invokestatic 1162	com/google/android/apps/plus/content/EsConversationsData:queryDatastoreVersion	(Landroid/content/Context;Lcom/google/android/apps/plus/content/EsAccount;)Ljava/lang/String;
    //   37: invokestatic 1168	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   40: istore 7
    //   42: iload 7
    //   44: istore 5
    //   46: aload_0
    //   47: invokestatic 1173	com/google/wireless/webapps/Version$ClientVersion:newBuilder	()Lcom/google/wireless/webapps/Version$ClientVersion$Builder;
    //   50: getstatic 1179	com/google/wireless/webapps/Version$ClientVersion$App:GOOGLE_PLUS	Lcom/google/wireless/webapps/Version$ClientVersion$App;
    //   53: invokevirtual 1185	com/google/wireless/webapps/Version$ClientVersion$Builder:setApp	(Lcom/google/wireless/webapps/Version$ClientVersion$App;)Lcom/google/wireless/webapps/Version$ClientVersion$Builder;
    //   56: getstatic 1191	com/google/wireless/webapps/Version$ClientVersion$BuildType:PUBLIC	Lcom/google/wireless/webapps/Version$ClientVersion$BuildType;
    //   59: invokevirtual 1195	com/google/wireless/webapps/Version$ClientVersion$Builder:setBuildType	(Lcom/google/wireless/webapps/Version$ClientVersion$BuildType;)Lcom/google/wireless/webapps/Version$ClientVersion$Builder;
    //   62: getstatic 1201	com/google/wireless/webapps/Version$ClientVersion$PlatformType:ANDROID	Lcom/google/wireless/webapps/Version$ClientVersion$PlatformType;
    //   65: invokevirtual 1205	com/google/wireless/webapps/Version$ClientVersion$Builder:setPlatformType	(Lcom/google/wireless/webapps/Version$ClientVersion$PlatformType;)Lcom/google/wireless/webapps/Version$ClientVersion$Builder;
    //   68: iload_1
    //   69: invokevirtual 1209	com/google/wireless/webapps/Version$ClientVersion$Builder:setVersion	(I)Lcom/google/wireless/webapps/Version$ClientVersion$Builder;
    //   72: iload 5
    //   74: invokevirtual 1212	com/google/wireless/webapps/Version$ClientVersion$Builder:setDataVersion	(I)Lcom/google/wireless/webapps/Version$ClientVersion$Builder;
    //   77: getstatic 1218	android/os/Build$VERSION:RELEASE	Ljava/lang/String;
    //   80: invokevirtual 1222	com/google/wireless/webapps/Version$ClientVersion$Builder:setDeviceOs	(Ljava/lang/String;)Lcom/google/wireless/webapps/Version$ClientVersion$Builder;
    //   83: getstatic 1227	android/os/Build:DEVICE	Ljava/lang/String;
    //   86: invokevirtual 1230	com/google/wireless/webapps/Version$ClientVersion$Builder:setDeviceHardware	(Ljava/lang/String;)Lcom/google/wireless/webapps/Version$ClientVersion$Builder;
    //   89: invokevirtual 1233	com/google/wireless/webapps/Version$ClientVersion$Builder:build	()Lcom/google/wireless/webapps/Version$ClientVersion;
    //   92: putfield 228	com/google/android/apps/plus/realtimechat/BunchClient:mClientVersion	Lcom/google/wireless/webapps/Version$ClientVersion;
    //   95: aload_0
    //   96: monitorexit
    //   97: return
    //   98: astore 4
    //   100: ldc 55
    //   102: iconst_5
    //   103: invokestatic 61	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   106: ifeq +12 -> 118
    //   109: ldc 55
    //   111: ldc_w 1235
    //   114: invokestatic 173	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   117: pop
    //   118: iconst_0
    //   119: istore 5
    //   121: goto -75 -> 46
    //   124: astore_3
    //   125: aload_0
    //   126: monitorexit
    //   127: aload_3
    //   128: athrow
    //   129: astore_2
    //   130: goto -104 -> 26
    //
    // Exception table:
    //   from	to	target	type
    //   26	42	98	java/lang/Exception
    //   4	26	124	finally
    //   26	42	124	finally
    //   46	95	124	finally
    //   100	118	124	finally
    //   4	26	129	android/content/pm/PackageManager$NameNotFoundException
  }

  public static abstract interface BunchClientListener
  {
    public abstract void onConnected(BunchClient paramBunchClient);

    public abstract void onDisconnected(BunchClient paramBunchClient, int paramInt);

    public abstract void onPingReceived(BunchClient paramBunchClient);

    public abstract void onResultsReceived(BunchClient paramBunchClient, List<RealTimeChatServiceResult> paramList);
  }

  private final class PendingRequest
  {
    HttpTransactionMetrics mMetrics;
    public Client.BunchClientRequest mRequest;
    public int mRequestId;
    public int mRetryCount;
    public long mTimestamp;

    PendingRequest(int paramBunchClientRequest, Client.BunchClientRequest paramLong, long arg4, int arg6)
    {
      this.mRequestId = paramBunchClientRequest;
      this.mRequest = paramLong;
      this.mTimestamp = ???;
      int i;
      this.mRetryCount = i;
    }
  }

  private static final class PendingRequestList
    implements Iterable<BunchClient.PendingRequest>
  {
    private final HashMap<String, BunchClient.PendingRequest> mRequestData = new HashMap();
    private final LinkedList<String> mRequestList = new LinkedList();

    public final void addRequest(String paramString, BunchClient.PendingRequest paramPendingRequest)
    {
      this.mRequestList.addLast(paramString);
      this.mRequestData.put(paramString, paramPendingRequest);
    }

    public final void clear()
    {
      this.mRequestList.clear();
      this.mRequestData.clear();
    }

    public final void dump()
    {
      if (EsLog.isLoggable("BunchClient", 3))
      {
        Log.d("BunchClient", "mRequestList");
        Iterator localIterator1 = this.mRequestList.iterator();
        while (localIterator1.hasNext())
        {
          String str2 = (String)localIterator1.next();
          Log.d("BunchClient", "  requestId " + str2);
        }
        Log.d("BunchClient", "mRequestData");
        Iterator localIterator2 = this.mRequestData.keySet().iterator();
        while (localIterator2.hasNext())
        {
          String str1 = (String)localIterator2.next();
          BunchClient.PendingRequest localPendingRequest = (BunchClient.PendingRequest)this.mRequestData.get(str1);
          Log.d("BunchClient", "  requestId " + str1 + " " + BunchClient.getRequestTypeName(localPendingRequest.mRequest));
        }
      }
    }

    public final BunchClient.PendingRequest getData(String paramString)
    {
      return (BunchClient.PendingRequest)this.mRequestData.get(paramString);
    }

    public final List<String> getRequestIds()
    {
      LinkedList localLinkedList = new LinkedList();
      Iterator localIterator = this.mRequestList.iterator();
      while (localIterator.hasNext())
        localLinkedList.add((String)localIterator.next());
      return localLinkedList;
    }

    public final boolean isEmpty()
    {
      return this.mRequestData.isEmpty();
    }

    public final Iterator<BunchClient.PendingRequest> iterator()
    {
      return this.mRequestData.values().iterator();
    }

    public final void removeRequest(String paramString)
    {
      this.mRequestData.remove(paramString);
    }

    public final List<BunchClient.PendingRequest> trimOutdatedRequestIds(long paramLong)
    {
      int i = 0;
      LinkedList localLinkedList = new LinkedList();
      while ((i == 0) && (!this.mRequestList.isEmpty()))
      {
        String str = (String)this.mRequestList.getFirst();
        BunchClient.PendingRequest localPendingRequest = (BunchClient.PendingRequest)this.mRequestData.get(str);
        if (localPendingRequest != null)
        {
          Long localLong = Long.valueOf(localPendingRequest.mTimestamp);
          if ((localLong == null) || (localLong.longValue() < paramLong))
          {
            localLinkedList.add(localPendingRequest);
            this.mRequestData.remove(str);
            this.mRequestList.removeFirst();
          }
          else
          {
            i = 1;
          }
        }
        else
        {
          this.mRequestList.removeFirst();
        }
      }
      return localLinkedList;
    }
  }

  private final class ResponseFailedException extends Exception
  {
    private static final long serialVersionUID = -2031648336431940975L;
    Data.ResponseStatus mStatus;

    ResponseFailedException(Data.ResponseStatus arg2)
    {
      Object localObject;
      this.mStatus = localObject;
    }

    public final String toString()
    {
      return super.toString() + " " + this.mStatus;
    }
  }

  private final class TimedOutException extends Exception
  {
    private static final long serialVersionUID = 3488037460306249294L;

    private TimedOutException()
    {
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.BunchClient
 * JD-Core Version:    0.6.2
 */