package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.AuthData;
import com.google.android.apps.plus.network.HttpOperation;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.service.AndroidNotification;
import com.google.android.apps.plus.util.EsLog;
import com.google.protobuf.MessageLite;
import com.google.wireless.tacotruck.proto.Network.BatchRequest;
import com.google.wireless.tacotruck.proto.Network.BatchRequest.Builder;
import com.google.wireless.tacotruck.proto.Network.BatchResponse;
import com.google.wireless.tacotruck.proto.Network.Request;
import com.google.wireless.tacotruck.proto.Network.Request.Builder;
import com.google.wireless.tacotruck.proto.Network.Request.Type;
import com.google.wireless.tacotruck.proto.Network.Response;
import com.google.wireless.tacotruck.proto.Network.Response.ErrorCode;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ByteArrayEntity;

abstract class EsOperation extends HttpOperation
{
  protected final List<EsRequest> mRequests = new ArrayList();

  public EsOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, OutputStream paramOutputStream, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramString1, paramString2, paramEsAccount, null, null, null);
  }

  protected final HttpEntity createPostData()
  {
    Network.BatchRequest.Builder localBuilder = Network.BatchRequest.newBuilder();
    for (int i = 0; i < this.mRequests.size(); i++)
    {
      EsRequest localEsRequest = (EsRequest)this.mRequests.get(i);
      Network.Request.Builder localBuilder1 = Network.Request.newBuilder();
      localBuilder1.setRequestId(Integer.toString(i));
      localBuilder1.setRequestType(localEsRequest.getType());
      localBuilder1.setRequestBody(localEsRequest.getMessage().toByteString());
      localBuilder.addRequest(localBuilder1.build());
    }
    String str = AuthData.getActionToken(this.mAccount.getName(), "ME_AT");
    if (EsLog.isLoggable("HttpTransaction", 3))
      Log.d("HttpTransaction", "Using action token: " + str);
    localBuilder.setActionToken(str);
    ByteArrayEntity localByteArrayEntity = new ByteArrayEntity(localBuilder.build().toByteArray());
    localByteArrayEntity.setContentType("application/octet-stream");
    return localByteArrayEntity;
  }

  public String getName()
  {
    if (this.mRequests.isEmpty());
    for (String str = "NO OP"; ; str = ((EsRequest)this.mRequests.get(0)).getType().name())
      return str;
  }

  protected final boolean isAuthenticationError(Exception paramException)
  {
    if ((paramException instanceof ServerException))
      switch (1.$SwitchMap$com$google$wireless$tacotruck$proto$Network$Response$ErrorCode[((ServerException)paramException).getTacoTruckErrorCode().ordinal()])
      {
      default:
      case 4:
      }
    for (boolean bool = super.isAuthenticationError(paramException); ; bool = true)
      return bool;
  }

  protected final boolean isImmediatelyRetryableError(Exception paramException)
  {
    if ((paramException instanceof ServerException))
      switch (1.$SwitchMap$com$google$wireless$tacotruck$proto$Network$Response$ErrorCode[((ServerException)paramException).getTacoTruckErrorCode().ordinal()])
      {
      default:
      case 3:
      case 4:
      }
    for (boolean bool = super.isImmediatelyRetryableError(paramException); ; bool = true)
      return bool;
  }

  public void onHttpCookie(Cookie paramCookie)
  {
    super.onHttpCookie(paramCookie);
    if ("ME_AT".equals(paramCookie.getName()))
    {
      String str = paramCookie.getValue();
      AuthData.setActionToken(this.mAccount.getName(), "ME_AT", str);
    }
  }

  protected final void onHttpHandleContentFromStream$6508b088(InputStream paramInputStream)
    throws IOException
  {
    Network.BatchResponse localBatchResponse = Network.BatchResponse.parseFrom(paramInputStream);
    onStartResultProcessing();
    int i = localBatchResponse.getResponseCount();
    for (int j = 0; j < i; j++)
    {
      Network.Response localResponse2 = localBatchResponse.getResponse(j);
      if (!localResponse2.hasRequestType())
        throw new ProtocolException("Received a response without request type");
      if (localResponse2.hasErrorCode())
      {
        Network.Response.ErrorCode localErrorCode = localResponse2.getErrorCode();
        Network.Request.Type localType = localResponse2.getRequestType();
        String str2 = localType.name();
        String str3 = str2 + ": " + localErrorCode.getNumber() + "/" + localErrorCode.name();
        Log.e("HttpTransaction", "Error for request type: " + localType + ", error number: " + localErrorCode.getNumber() + ", error code: " + localErrorCode);
        switch (1.$SwitchMap$com$google$wireless$tacotruck$proto$Network$Response$ErrorCode[localErrorCode.ordinal()])
        {
        default:
          throw new ServerException(localErrorCode, str3);
        case 1:
          AndroidNotification.showUpgradeRequiredNotification(this.mContext);
          throw new ServerException(localErrorCode, str3);
        case 2:
        }
        throw new ServerException(localErrorCode, str3);
      }
    }
    for (int k = 0; k < i; k++)
    {
      Network.Response localResponse1 = localBatchResponse.getResponse(k);
      if ((localResponse1.hasIsMasterResponse()) && (localResponse1.getIsMasterResponse()))
      {
        String str1 = localResponse1.getRequestId();
        ((EsRequest)this.mRequests.get(Integer.parseInt(str1))).getMessage();
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.EsOperation
 * JD-Core Version:    0.6.2
 */