package com.google.android.apps.plus.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.apps.plus.api.MediaRef;
import com.google.api.services.plusi.model.EmbedClientItem;
import com.google.api.services.plusi.model.Emotishare;
import com.google.api.services.plusi.model.Thing;
import com.google.api.services.plusi.model.Thumbnail;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DbEmbedEmotishare extends DbSerializer
  implements Parcelable
{
  public static final Parcelable.Creator<DbEmbedEmotishare> CREATOR = new Parcelable.Creator()
  {
  };
  private String mDescription;
  private MediaRef mImageRef;
  private String mImageUrl;
  private String mName;
  private String mType;
  private String mUrl;

  private DbEmbedEmotishare()
  {
  }

  private DbEmbedEmotishare(Parcel paramParcel)
  {
    this.mUrl = paramParcel.readString();
    this.mImageUrl = paramParcel.readString();
    this.mName = paramParcel.readString();
    this.mDescription = paramParcel.readString();
    this.mType = paramParcel.readString();
    this.mImageRef = DbEmotishareMetadata.createMediaRef(this.mImageUrl);
  }

  public DbEmbedEmotishare(Emotishare paramEmotishare)
  {
    this.mUrl = paramEmotishare.url;
    this.mType = paramEmotishare.emotion;
    this.mName = paramEmotishare.name;
    this.mDescription = paramEmotishare.description;
    if (paramEmotishare.proxiedImage != null)
      this.mImageUrl = paramEmotishare.proxiedImage.imageUrl;
  }

  public DbEmbedEmotishare(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.mUrl = paramString3;
    this.mType = paramString1;
    this.mName = paramString2;
    this.mDescription = paramString4;
    this.mImageUrl = paramString3;
    this.mImageRef = DbEmotishareMetadata.createMediaRef(paramString3);
  }

  public static DbEmbedEmotishare deserialize(ByteBuffer paramByteBuffer)
  {
    DbEmbedEmotishare localDbEmbedEmotishare;
    if (paramByteBuffer == null)
      localDbEmbedEmotishare = null;
    while (true)
    {
      return localDbEmbedEmotishare;
      localDbEmbedEmotishare = new DbEmbedEmotishare();
      localDbEmbedEmotishare.mUrl = getShortString(paramByteBuffer);
      localDbEmbedEmotishare.mImageUrl = getShortString(paramByteBuffer);
      localDbEmbedEmotishare.mName = getShortString(paramByteBuffer);
      localDbEmbedEmotishare.mDescription = getShortString(paramByteBuffer);
      localDbEmbedEmotishare.mType = getShortString(paramByteBuffer);
      localDbEmbedEmotishare.mImageRef = DbEmotishareMetadata.createMediaRef(localDbEmbedEmotishare.mImageUrl);
    }
  }

  public static DbEmbedEmotishare deserialize(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null);
    for (DbEmbedEmotishare localDbEmbedEmotishare = null; ; localDbEmbedEmotishare = deserialize(ByteBuffer.wrap(paramArrayOfByte)))
      return localDbEmbedEmotishare;
  }

  public static byte[] serialize(DbEmbedEmotishare paramDbEmbedEmotishare)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(64);
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    putShortString(localDataOutputStream, paramDbEmbedEmotishare.mUrl);
    putShortString(localDataOutputStream, paramDbEmbedEmotishare.getImageUrl());
    putShortString(localDataOutputStream, paramDbEmbedEmotishare.mName);
    putShortString(localDataOutputStream, paramDbEmbedEmotishare.mDescription);
    putShortString(localDataOutputStream, paramDbEmbedEmotishare.mType);
    byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
    localDataOutputStream.close();
    return arrayOfByte;
  }

  public final EmbedClientItem createEmbed()
  {
    EmbedClientItem localEmbedClientItem = new EmbedClientItem();
    localEmbedClientItem.type = new ArrayList();
    localEmbedClientItem.type.add("EMOTISHARE");
    Emotishare localEmotishare = new Emotishare();
    localEmotishare.url = this.mUrl;
    localEmotishare.emotion = this.mType;
    localEmotishare.name = this.mName;
    if (this.mImageUrl != null)
    {
      localEmotishare.proxiedImage = new Thumbnail();
      localEmotishare.proxiedImage.imageUrl = this.mImageUrl;
    }
    localEmbedClientItem.emotishare = localEmotishare;
    localEmbedClientItem.type.add("THING");
    Thing localThing = new Thing();
    localThing.url = this.mUrl;
    localThing.name = this.mName;
    localThing.imageUrl = this.mImageUrl;
    localEmbedClientItem.thing = localThing;
    return localEmbedClientItem;
  }

  public int describeContents()
  {
    return 0;
  }

  public final MediaRef getImageRef()
  {
    return this.mImageRef;
  }

  public final String getImageUrl()
  {
    if (this.mImageUrl != null);
    for (String str = this.mImageUrl; ; str = this.mUrl)
      return str;
  }

  public final String getName()
  {
    return this.mName;
  }

  public final String getType()
  {
    return this.mType;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.mUrl);
    paramParcel.writeString(this.mImageUrl);
    paramParcel.writeString(this.mName);
    paramParcel.writeString(this.mDescription);
    paramParcel.writeString(this.mType);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.DbEmbedEmotishare
 * JD-Core Version:    0.6.2
 */