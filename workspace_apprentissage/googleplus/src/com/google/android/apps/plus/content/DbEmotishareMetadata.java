package com.google.android.apps.plus.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class DbEmotishareMetadata extends DbSerializer
  implements Parcelable
{
  public static final Parcelable.Creator<DbEmotishareMetadata> CREATOR = new Parcelable.Creator()
  {
  };
  private ArrayList<String> mCategory;
  private DbEmbedEmotishare mEmbed;
  private int mGeneration;
  private MediaRef mIconRef;
  private String mIconUrl;
  private int mId;
  private String mShareText;

  private DbEmotishareMetadata()
  {
  }

  public DbEmotishareMetadata(int paramInt1, ArrayList<String> paramArrayList, String paramString1, String paramString2, DbEmbedEmotishare paramDbEmbedEmotishare, int paramInt2)
  {
    this.mId = paramInt1;
    this.mCategory = paramArrayList;
    this.mShareText = paramString1;
    this.mIconUrl = paramString2;
    this.mGeneration = paramInt2;
    this.mEmbed = paramDbEmbedEmotishare;
    this.mIconRef = createMediaRef(this.mIconUrl);
  }

  private DbEmotishareMetadata(Parcel paramParcel)
  {
    this.mId = paramParcel.readInt();
    this.mCategory = new ArrayList();
    paramParcel.readStringList(this.mCategory);
    this.mShareText = paramParcel.readString();
    this.mIconUrl = paramParcel.readString();
    this.mGeneration = paramParcel.readInt();
    this.mEmbed = ((DbEmbedEmotishare)paramParcel.readParcelable(DbEmbedEmotishare.class.getClassLoader()));
    this.mIconRef = createMediaRef(this.mIconUrl);
  }

  public static MediaRef createMediaRef(String paramString)
  {
    return new MediaRef(null, 0L, paramString, null, MediaRef.MediaType.IMAGE);
  }

  public static DbEmotishareMetadata deserialize(byte[] paramArrayOfByte)
  {
    DbEmotishareMetadata localDbEmotishareMetadata;
    if (paramArrayOfByte == null)
      localDbEmotishareMetadata = null;
    while (true)
    {
      return localDbEmotishareMetadata;
      ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
      localDbEmotishareMetadata = new DbEmotishareMetadata();
      localDbEmotishareMetadata.mId = localByteBuffer.getInt();
      localDbEmotishareMetadata.mCategory = new ArrayList(getShortStringList(localByteBuffer));
      localDbEmotishareMetadata.mShareText = getShortString(localByteBuffer);
      localDbEmotishareMetadata.mIconUrl = getShortString(localByteBuffer);
      localDbEmotishareMetadata.mGeneration = localByteBuffer.getInt();
      localDbEmotishareMetadata.mEmbed = DbEmbedEmotishare.deserialize(localByteBuffer);
      localDbEmotishareMetadata.mIconRef = createMediaRef(localDbEmotishareMetadata.mIconUrl);
    }
  }

  public static byte[] serialize(DbEmotishareMetadata paramDbEmotishareMetadata)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(64);
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    localDataOutputStream.writeInt(paramDbEmotishareMetadata.mId);
    putShortStringList(localDataOutputStream, paramDbEmotishareMetadata.mCategory);
    putShortString(localDataOutputStream, paramDbEmotishareMetadata.mShareText);
    putShortString(localDataOutputStream, paramDbEmotishareMetadata.mIconUrl);
    localDataOutputStream.writeInt(paramDbEmotishareMetadata.mGeneration);
    localDataOutputStream.write(DbEmbedEmotishare.serialize(paramDbEmotishareMetadata.mEmbed));
    byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
    localDataOutputStream.close();
    return arrayOfByte;
  }

  public int describeContents()
  {
    return 0;
  }

  public final DbEmbedEmotishare getEmbed()
  {
    return this.mEmbed;
  }

  public final int getGeneration()
  {
    return this.mGeneration;
  }

  public final MediaRef getIconRef()
  {
    return this.mIconRef;
  }

  public final int getId()
  {
    return this.mId;
  }

  public final MediaRef getImageRef()
  {
    if (this.mEmbed == null);
    for (MediaRef localMediaRef = null; ; localMediaRef = this.mEmbed.getImageRef())
      return localMediaRef;
  }

  public final String getImageUrl()
  {
    if (this.mEmbed == null);
    for (String str = null; ; str = this.mEmbed.getImageUrl())
      return str;
  }

  public final String getName()
  {
    if (this.mEmbed == null);
    for (String str = null; ; str = this.mEmbed.getName())
      return str;
  }

  public final String getShareText()
  {
    return this.mShareText;
  }

  public final String getType()
  {
    if (this.mEmbed == null);
    for (String str = null; ; str = this.mEmbed.getType())
      return str;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("TypedImageEmbed name: ");
    if (this.mEmbed == null);
    for (String str = null; ; str = this.mEmbed.getName())
      return str + ", ID: " + this.mId + ", cat: " + this.mCategory + ", share: " + this.mShareText + ", icon: " + this.mIconUrl + ", gen: " + this.mGeneration + ", embed: " + this.mEmbed;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.mId);
    paramParcel.writeStringList(this.mCategory);
    paramParcel.writeString(this.mShareText);
    paramParcel.writeString(this.mIconUrl);
    paramParcel.writeInt(this.mGeneration);
    paramParcel.writeParcelable(this.mEmbed, 0);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.DbEmotishareMetadata
 * JD-Core Version:    0.6.2
 */