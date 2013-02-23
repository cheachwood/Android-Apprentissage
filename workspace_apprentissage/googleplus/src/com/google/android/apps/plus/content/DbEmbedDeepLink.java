package com.google.android.apps.plus.content;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.apps.plus.R.string;
import com.google.api.services.plusi.model.DeepLinkData;
import com.google.api.services.plusi.model.PackagingServiceClient;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class DbEmbedDeepLink extends DbSerializer
{
  protected ArrayList<String> mClientPackageNames;
  protected String mDeepLinkId;
  protected String mLabel;
  protected String mUrl;

  protected DbEmbedDeepLink()
  {
  }

  public DbEmbedDeepLink(DeepLinkData paramDeepLinkData, String paramString)
  {
    this.mClientPackageNames = new ArrayList();
    if (paramDeepLinkData.client != null)
    {
      Iterator localIterator = paramDeepLinkData.client.iterator();
      while (localIterator.hasNext())
      {
        PackagingServiceClient localPackagingServiceClient = (PackagingServiceClient)localIterator.next();
        if ((!TextUtils.isEmpty(localPackagingServiceClient.androidPackageName)) && (TextUtils.equals("ANDROID", localPackagingServiceClient.type)))
          this.mClientPackageNames.add(localPackagingServiceClient.androidPackageName);
      }
    }
    this.mDeepLinkId = paramDeepLinkData.deepLinkId;
    this.mUrl = paramDeepLinkData.url;
    this.mLabel = paramString;
  }

  public static DbEmbedDeepLink deserialize(byte[] paramArrayOfByte)
  {
    DbEmbedDeepLink localDbEmbedDeepLink;
    if (paramArrayOfByte == null)
      localDbEmbedDeepLink = null;
    while (true)
    {
      return localDbEmbedDeepLink;
      ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
      localDbEmbedDeepLink = new DbEmbedDeepLink();
      localDbEmbedDeepLink.mClientPackageNames = ((ArrayList)getShortStringList(localByteBuffer));
      localDbEmbedDeepLink.mDeepLinkId = getShortString(localByteBuffer);
      localDbEmbedDeepLink.mLabel = getShortString(localByteBuffer);
      localDbEmbedDeepLink.mUrl = getShortString(localByteBuffer);
    }
  }

  public static byte[] serialize(DeepLinkData paramDeepLinkData, String paramString)
    throws IOException
  {
    DbEmbedDeepLink localDbEmbedDeepLink = new DbEmbedDeepLink(paramDeepLinkData, paramString);
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(128);
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    putShortStringList(localDataOutputStream, localDbEmbedDeepLink.mClientPackageNames);
    putShortString(localDataOutputStream, localDbEmbedDeepLink.mDeepLinkId);
    putShortString(localDataOutputStream, localDbEmbedDeepLink.mLabel);
    putShortString(localDataOutputStream, localDbEmbedDeepLink.mUrl);
    byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
    localDataOutputStream.close();
    return arrayOfByte;
  }

  public final ArrayList<String> getClientPackageNames()
  {
    return this.mClientPackageNames;
  }

  public final String getDeepLinkId()
  {
    return this.mDeepLinkId;
  }

  public final String getLabelOrDefault(Context paramContext)
  {
    if (TextUtils.isEmpty(this.mLabel));
    for (String str = paramContext.getString(R.string.app_invite_default_action); ; str = this.mLabel)
      return str;
  }

  public final String getUrl()
  {
    return this.mUrl;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.DbEmbedDeepLink
 * JD-Core Version:    0.6.2
 */