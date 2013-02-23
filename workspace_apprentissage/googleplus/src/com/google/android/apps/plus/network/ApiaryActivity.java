package com.google.android.apps.plus.network;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.apps.plus.api.CallToActionData;
import com.google.api.services.plusi.model.AppInvite;
import com.google.api.services.plusi.model.DeepLink;
import com.google.api.services.plusi.model.DeepLinkData;
import com.google.api.services.plusi.model.EmbedClientItem;
import com.google.api.services.plusi.model.LinkPreviewResponse;
import com.google.api.services.plusi.model.LinkPreviewResponseJson;
import com.google.api.services.plusi.model.MediaLayout;
import com.google.api.services.plusi.model.Thing;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ApiaryActivity
  implements Parcelable
{
  public static final Parcelable.Creator<ApiaryActivity> CREATOR = new Parcelable.Creator()
  {
  };
  private CallToActionData mCallToActionButton;
  private Bundle mContentDeepLinkMetadata;
  private LinkPreviewResponse mLinkPreview;

  private void update()
    throws IOException
  {
    if (this.mLinkPreview == null)
    {
      if (this.mContentDeepLinkMetadata == null)
        throw new IOException("No metadata.");
    }
    else
      update((MediaLayout)this.mLinkPreview.mediaLayout.get(0));
  }

  public int describeContents()
  {
    return 0;
  }

  public String getContent()
  {
    return this.mContentDeepLinkMetadata.getString("description");
  }

  public String getDisplayName()
  {
    return this.mContentDeepLinkMetadata.getString("title");
  }

  public final EmbedClientItem getEmbed(String paramString)
  {
    Object localObject;
    if ((this.mLinkPreview != null) && (this.mLinkPreview.embedItem != null))
      localObject = (EmbedClientItem)this.mLinkPreview.embedItem.get(0);
    while (true)
    {
      if ((paramString != null) && (localObject != null))
      {
        ((EmbedClientItem)localObject).deepLinkData = new DeepLinkData();
        ((EmbedClientItem)localObject).deepLinkData.deepLinkId = paramString;
      }
      return localObject;
      if (this.mContentDeepLinkMetadata != null)
      {
        EmbedClientItem localEmbedClientItem = new EmbedClientItem();
        localEmbedClientItem.thing = new Thing();
        localEmbedClientItem.thing.name = this.mContentDeepLinkMetadata.getString("title");
        localEmbedClientItem.thing.description = this.mContentDeepLinkMetadata.getString("description");
        localEmbedClientItem.thing.imageUrl = this.mContentDeepLinkMetadata.getString("thumbnailUrl");
        localEmbedClientItem.type = new ArrayList();
        localEmbedClientItem.type.add("THING");
        if (this.mCallToActionButton != null)
        {
          localObject = new EmbedClientItem();
          ((EmbedClientItem)localObject).appInvite = new AppInvite();
          ((EmbedClientItem)localObject).appInvite.about = localEmbedClientItem;
          ((EmbedClientItem)localObject).appInvite.callToAction = new DeepLink();
          ((EmbedClientItem)localObject).appInvite.callToAction.deepLinkLabel = this.mCallToActionButton.mLabel;
          ((EmbedClientItem)localObject).appInvite.callToAction.label = this.mCallToActionButton.mLabel;
          ((EmbedClientItem)localObject).appInvite.callToAction.deepLink = new DeepLinkData();
          ((EmbedClientItem)localObject).appInvite.callToAction.deepLink.deepLinkId = this.mCallToActionButton.mDeepLinkId;
          ((EmbedClientItem)localObject).appInvite.callToAction.deepLink.url = this.mCallToActionButton.mUrl;
          ((EmbedClientItem)localObject).type = new ArrayList();
          ((EmbedClientItem)localObject).type.add("APP_INVITE");
        }
        while (true)
        {
          break;
          localObject = localEmbedClientItem;
        }
      }
      localObject = null;
    }
  }

  public String getFavIconUrl()
  {
    return null;
  }

  public String getImage()
  {
    return this.mContentDeepLinkMetadata.getString("thumbnailUrl");
  }

  public final String getMediaJson()
  {
    List localList;
    String str;
    if (this.mLinkPreview == null)
    {
      localList = null;
      str = null;
      if (localList != null)
      {
        boolean bool = localList.isEmpty();
        str = null;
        if (!bool)
          break label41;
      }
    }
    while (true)
    {
      return str;
      localList = this.mLinkPreview.blackboxPreviewData;
      break;
      label41: StringBuilder localStringBuilder = new StringBuilder("[");
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        localStringBuilder.append((String)localIterator.next());
        localStringBuilder.append(",");
      }
      localStringBuilder.deleteCharAt(-1 + localStringBuilder.length());
      localStringBuilder.append("]");
      str = localStringBuilder.toString();
    }
  }

  public Type getType()
  {
    return Type.NONE;
  }

  public final void setCallToActionMetadata(CallToActionData paramCallToActionData)
  {
    this.mCallToActionButton = paramCallToActionData;
  }

  public final void setContentDeepLinkMetadata(Bundle paramBundle)
    throws IOException
  {
    this.mContentDeepLinkMetadata = paramBundle;
    update();
  }

  public final void setLinkPreview(LinkPreviewResponse paramLinkPreviewResponse)
    throws IOException
  {
    this.mLinkPreview = paramLinkPreviewResponse;
    update();
  }

  protected void update(MediaLayout paramMediaLayout)
    throws IOException
  {
    if (this.mLinkPreview == null)
      throw new IOException("No metadata.");
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    if (this.mLinkPreview != null)
      paramParcel.writeString(LinkPreviewResponseJson.getInstance().toString(this.mLinkPreview));
    while (true)
    {
      paramParcel.writeBundle(this.mContentDeepLinkMetadata);
      paramParcel.writeParcelable(this.mCallToActionButton, 0);
      return;
      paramParcel.writeString(null);
    }
  }

  public static enum Type
  {
    static
    {
      ARTICLE = new Type("ARTICLE", 1);
      PHOTOALBUM = new Type("PHOTOALBUM", 2);
      VIDEO = new Type("VIDEO", 3);
      AUDIO = new Type("AUDIO", 4);
      Type[] arrayOfType = new Type[5];
      arrayOfType[0] = NONE;
      arrayOfType[1] = ARTICLE;
      arrayOfType[2] = PHOTOALBUM;
      arrayOfType[3] = VIDEO;
      arrayOfType[4] = AUDIO;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.ApiaryActivity
 * JD-Core Version:    0.6.2
 */