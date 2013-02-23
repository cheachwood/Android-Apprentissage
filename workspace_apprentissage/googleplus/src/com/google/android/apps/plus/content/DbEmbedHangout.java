package com.google.android.apps.plus.content;

import android.text.TextUtils;
import com.google.api.services.plusi.model.EmbedsPerson;
import com.google.api.services.plusi.model.HangoutConsumer;
import com.google.api.services.plusi.model.HangoutStartContext;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public final class DbEmbedHangout extends DbSerializer
{
  protected ArrayList<String> mAttendeeAvatarUrls;
  protected ArrayList<String> mAttendeeGaiaIds;
  protected ArrayList<String> mAttendeeNames;
  protected String mHangoutId;
  protected String mStatus;
  protected String mYoutubeLiveId;

  protected DbEmbedHangout()
  {
  }

  private DbEmbedHangout(HangoutConsumer paramHangoutConsumer)
  {
    if (paramHangoutConsumer.startContext != null);
    for (this.mHangoutId = paramHangoutConsumer.startContext.hangoutId; ; this.mHangoutId = null)
    {
      this.mAttendeeGaiaIds = new ArrayList();
      this.mAttendeeNames = new ArrayList();
      this.mAttendeeAvatarUrls = new ArrayList();
      if (paramHangoutConsumer.attendees == null)
        break;
      int i = 0;
      int j = paramHangoutConsumer.attendees.size();
      while (i < j)
      {
        EmbedsPerson localEmbedsPerson = (EmbedsPerson)paramHangoutConsumer.attendees.get(i);
        this.mAttendeeGaiaIds.add(localEmbedsPerson.ownerObfuscatedId);
        this.mAttendeeNames.add(localEmbedsPerson.name);
        this.mAttendeeAvatarUrls.add(localEmbedsPerson.imageUrl);
        i++;
      }
    }
    this.mYoutubeLiveId = paramHangoutConsumer.youtubeLiveId;
    this.mStatus = paramHangoutConsumer.status;
  }

  public static DbEmbedHangout deserialize(byte[] paramArrayOfByte)
  {
    DbEmbedHangout localDbEmbedHangout;
    if (paramArrayOfByte == null)
      localDbEmbedHangout = null;
    while (true)
    {
      return localDbEmbedHangout;
      ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
      localDbEmbedHangout = new DbEmbedHangout();
      localDbEmbedHangout.mHangoutId = getShortString(localByteBuffer);
      localDbEmbedHangout.mAttendeeGaiaIds = ((ArrayList)getShortStringList(localByteBuffer));
      localDbEmbedHangout.mAttendeeNames = ((ArrayList)getShortStringList(localByteBuffer));
      localDbEmbedHangout.mAttendeeAvatarUrls = ((ArrayList)getShortStringList(localByteBuffer));
      localDbEmbedHangout.mYoutubeLiveId = getShortString(localByteBuffer);
      localDbEmbedHangout.mStatus = getShortString(localByteBuffer);
    }
  }

  public static byte[] serialize(HangoutConsumer paramHangoutConsumer)
    throws IOException
  {
    DbEmbedHangout localDbEmbedHangout = new DbEmbedHangout(paramHangoutConsumer);
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(256);
    DataOutputStream localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
    putShortString(localDataOutputStream, localDbEmbedHangout.mHangoutId);
    putShortStringList(localDataOutputStream, localDbEmbedHangout.mAttendeeGaiaIds);
    putShortStringList(localDataOutputStream, localDbEmbedHangout.mAttendeeNames);
    putShortStringList(localDataOutputStream, localDbEmbedHangout.mAttendeeAvatarUrls);
    putShortString(localDataOutputStream, localDbEmbedHangout.mYoutubeLiveId);
    putShortString(localDataOutputStream, localDbEmbedHangout.mStatus);
    byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
    localDataOutputStream.close();
    return arrayOfByte;
  }

  public final ArrayList<String> getAttendeeAvatarUrls()
  {
    return this.mAttendeeAvatarUrls;
  }

  public final ArrayList<String> getAttendeeGaiaIds()
  {
    return this.mAttendeeGaiaIds;
  }

  public final ArrayList<String> getAttendeeNames()
  {
    return this.mAttendeeNames;
  }

  public final String getHangoutId()
  {
    return this.mHangoutId;
  }

  public final int getNumAttendees()
  {
    return this.mAttendeeGaiaIds.size();
  }

  public final String getYoutubeLiveId()
  {
    return this.mYoutubeLiveId;
  }

  public final boolean isInProgress()
  {
    return TextUtils.equals("ACTIVE", this.mStatus);
  }

  public final boolean isJoinable()
  {
    return TextUtils.isEmpty(this.mYoutubeLiveId);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.DbEmbedHangout
 * JD-Core Version:    0.6.2
 */