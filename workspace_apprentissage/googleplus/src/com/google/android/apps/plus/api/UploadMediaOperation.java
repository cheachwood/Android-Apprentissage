package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.ApiaryHttpRequestConfiguration;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.android.apps.plus.util.Property;
import com.google.api.services.plusi.model.UploadMediaRequest;
import com.google.api.services.plusi.model.UploadMediaRequestJson;
import com.google.api.services.plusi.model.UploadMediaResponse;
import com.google.api.services.plusi.model.UploadMediaResponseJson;

public final class UploadMediaOperation extends PlusiOperation<UploadMediaRequest, UploadMediaResponse>
{
  private static final Bundle QUERY_PARAMS = localBundle;
  private final String mAlbumId;
  private final String mAlbumLabel;
  private final String mAlbumTitle;
  private final String mOwnerId;
  private final byte[] mPayloadData;
  private UploadMediaResponse mResponse;
  private Integer mTopOffset;

  static
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("uploadType", "multipart");
  }

  public UploadMediaOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString1, String paramString2, String paramString3, String paramString4, byte[] paramArrayOfByte)
  {
    super(paramContext, paramEsAccount, "uploadmedia", "/upload" + Property.PLUS_FRONTEND_PATH.get(), QUERY_PARAMS, UploadMediaRequestJson.getInstance(), UploadMediaResponseJson.getInstance(), paramIntent, paramOperationListener, new ApiaryHttpRequestConfiguration(paramContext, paramEsAccount, "oauth2:https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/plus.stream.read https://www.googleapis.com/auth/plus.stream.write https://www.googleapis.com/auth/plus.circles.write https://www.googleapis.com/auth/plus.circles.read https://www.googleapis.com/auth/plus.photos.readwrite https://www.googleapis.com/auth/plus.native", null, "multipart/related; boundary=onetwothreefourfivesixseven"));
    this.mOwnerId = paramString1;
    this.mAlbumId = paramString2;
    this.mAlbumTitle = paramString3;
    this.mAlbumLabel = paramString4;
    this.mTopOffset = null;
    this.mPayloadData = paramArrayOfByte;
  }

  public UploadMediaOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString1, String paramString2, byte[] paramArrayOfByte)
  {
    this(paramContext, paramEsAccount, paramIntent, paramOperationListener, paramString1, paramString2, null, null, paramArrayOfByte);
  }

  public UploadMediaOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString1, String paramString2, byte[] paramArrayOfByte, Integer paramInteger)
  {
    this(paramContext, paramEsAccount, paramIntent, paramOperationListener, paramString1, paramString2, null, null, paramArrayOfByte);
    this.mTopOffset = paramInteger;
  }

  public final UploadMediaResponse getUploadMediaResponse()
  {
    return this.mResponse;
  }

  public static final class UploadMediaException extends ProtocolException
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.UploadMediaOperation
 * JD-Core Version:    0.6.2
 */