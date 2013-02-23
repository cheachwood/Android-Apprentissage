package com.google.android.apps.plus.network;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.apps.plus.api.ApiaryErrorResponse;
import com.google.android.apps.plus.api.OzServerException;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.json.EsJson;
import com.google.android.apps.plus.json.GenericJson;
import com.google.android.apps.plus.json.MalformedJsonException;
import com.google.android.apps.plus.service.AndroidNotification;
import com.google.android.apps.plus.util.EsLog;
import com.google.api.services.plusi.model.ApiaryFields;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

public abstract class ApiaryOperation<Request extends GenericJson, Response extends GenericJson> extends HttpOperation
{
  protected final EsJson<Request> mRequestJson;
  protected final EsJson<Response> mResponseJson;

  protected ApiaryOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, EsJson<Request> paramEsJson, EsJson<Response> paramEsJson1, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, HttpRequestConfiguration paramHttpRequestConfiguration, String paramString2)
  {
    super(paramContext, paramString2, paramString1, paramHttpRequestConfiguration, paramEsAccount, null, paramIntent, paramOperationListener);
    this.mRequestJson = paramEsJson;
    this.mResponseJson = paramEsJson1;
  }

  public ApiaryOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String paramString3, EsJson<Request> paramEsJson, EsJson<Response> paramEsJson1, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    this(paramContext, paramEsAccount, paramString1, paramString2, paramString3, paramEsJson, paramEsJson1, paramIntent, paramOperationListener, new ApiaryHttpRequestConfiguration(paramContext, paramEsAccount, paramString3, paramString2));
  }

  protected ApiaryOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, String paramString3, EsJson<Request> paramEsJson, EsJson<Response> paramEsJson1, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, HttpRequestConfiguration paramHttpRequestConfiguration)
  {
    this(paramContext, paramEsAccount, paramString1, paramEsJson, paramEsJson1, paramIntent, paramOperationListener, paramHttpRequestConfiguration, "POST");
  }

  protected JsonHttpEntity<Request> createHttpEntity(Request paramRequest)
  {
    return new JsonHttpEntity(this.mRequestJson, paramRequest);
  }

  protected HttpEntity createPostData()
  {
    if (this.mRequestJson == null);
    GenericJson localGenericJson;
    for (JsonHttpEntity localJsonHttpEntity = createHttpEntity(null); ; localJsonHttpEntity = createHttpEntity(localGenericJson))
    {
      return localJsonHttpEntity;
      localGenericJson = (GenericJson)this.mRequestJson.newInstance();
      ApiaryFields localApiaryFields = new ApiaryFields();
      localApiaryFields.appVersion = Integer.valueOf(ClientVersion.from(this.mContext));
      if (this.mAccount.isPlusPage())
        localApiaryFields.effectiveUser = this.mAccount.getGaiaId();
      this.mRequestJson.setField(localGenericJson, "commonFields", localApiaryFields);
      populateRequest(localGenericJson);
      if ((EsLog.isLoggable("HttpTransaction", 3)) || (EsLog.isLoggable(getLogTag(), 3)))
        EsLog.doWriteToLog(3, "HttpTransaction", "Apiary request: " + localGenericJson.getClass().getSimpleName() + "\n" + this.mRequestJson.toPrettyString(localGenericJson));
    }
  }

  protected String getLogTag()
  {
    return "HttpTransaction";
  }

  protected abstract void handleResponse(Response paramResponse)
    throws IOException;

  protected final boolean isAuthenticationError(Exception paramException)
  {
    if ((paramException instanceof OzServerException))
      switch (((OzServerException)paramException).getErrorCode())
      {
      default:
      case 1:
      }
    for (boolean bool = super.isAuthenticationError(paramException); ; bool = true)
      return bool;
  }

  protected final boolean isImmediatelyRetryableError(Exception paramException)
  {
    if ((paramException instanceof OzServerException))
      switch (((OzServerException)paramException).getErrorCode())
      {
      default:
      case 1:
      case 6:
      }
    for (boolean bool = super.isImmediatelyRetryableError(paramException); ; bool = true)
      return bool;
  }

  protected final void onHttpHandleContentFromStream$6508b088(InputStream paramInputStream)
    throws IOException
  {
    onStartResultProcessing();
    GenericJson localGenericJson;
    if (this.mResponseJson != null)
    {
      localGenericJson = (GenericJson)this.mResponseJson.fromInputStream(paramInputStream);
      if ((EsLog.isLoggable("HttpTransaction", 2)) || (EsLog.isLoggable(getLogTag(), 2)))
      {
        if (this.mResponseJson == null)
          break label114;
        EsLog.doWriteToLog(2, "HttpTransaction", "Apiary response: " + ((GenericJson)this.mResponseJson.newInstance()).getClass().getSimpleName() + "\n" + this.mResponseJson.toPrettyString(localGenericJson));
      }
    }
    while (true)
    {
      handleResponse(localGenericJson);
      return;
      localGenericJson = null;
      break;
      label114: Log.v("HttpTransaction", "Apiary response ignored");
    }
  }

  public void onHttpReadErrorFromStream(InputStream paramInputStream, String paramString, int paramInt1, Header[] paramArrayOfHeader, int paramInt2)
    throws IOException
  {
    if ((EsLog.isLoggable("HttpTransaction", 4)) || (EsLog.isLoggable(getLogTag(), 4)))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Apiary error response: ").append(getName()).append('\n');
      paramInputStream = captureResponse(paramInputStream, paramInt1, localStringBuilder);
      Log.i("HttpTransaction", localStringBuilder.toString());
    }
    if (paramInt2 == 401);
    while (true)
    {
      return;
      try
      {
        ApiaryErrorResponse localApiaryErrorResponse = (ApiaryErrorResponse)ApiaryErrorResponse.JSON.fromInputStream(paramInputStream);
        if (!TextUtils.isEmpty(localApiaryErrorResponse.getErrorType()))
        {
          if ((EsLog.isLoggable("HttpTransaction", 6)) || (EsLog.isLoggable(getLogTag(), 6)))
            Log.e("HttpTransaction", "Apiary error reason: " + localApiaryErrorResponse.getErrorType());
          OzServerException localOzServerException = new OzServerException(localApiaryErrorResponse);
          switch (localOzServerException.getErrorCode())
          {
          default:
          case 10:
          }
          while (true)
          {
            throw localOzServerException;
            AndroidNotification.showUpgradeRequiredNotification(this.mContext);
          }
        }
      }
      catch (MalformedJsonException localMalformedJsonException)
      {
      }
    }
  }

  protected abstract void populateRequest(Request paramRequest);
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.ApiaryOperation
 * JD-Core Version:    0.6.2
 */