package com.google.android.apps.plus.iu;

import android.content.ComponentName;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.android.gallery3d.common.Entry;
import com.android.gallery3d.common.Entry.Column;
import com.android.gallery3d.common.Entry.Table;
import com.android.gallery3d.common.EntrySchema;
import com.android.gallery3d.common.Fingerprint;
import java.util.ArrayList;

@Entry.Table("upload_tasks")
class UploadTaskEntry extends Entry
{
  private static final String[] REQUIRED_COLUMNS = { "account", "content_uri", "media_record_id" };
  public static final EntrySchema SCHEMA = new EntrySchema(UploadTaskEntry.class);

  @Entry.Column("account")
  private String mAccount;

  @Entry.Column("album_id")
  private String mAlbumId;

  @Entry.Column("album_title")
  private String mAlbumTitle;

  @Entry.Column("auth_token_type")
  private String mAuthTokenType;

  @Entry.Column("bytes_total")
  private long mBytesTotal;

  @Entry.Column("bytes_uploaded")
  private long mBytesUploaded;
  private ComponentName mComponentName;

  @Entry.Column("content_uri")
  private String mContentUri;

  @Entry.Column("display_name")
  private String mDisplayName;
  private Throwable mError;

  @Entry.Column("event_id")
  private String mEventId;

  @Entry.Column("fingerprint")
  private byte[] mFingerprint;

  @Entry.Column("media_record_id")
  private long mMediaRecordId;

  @Entry.Column("mime_type")
  private String mMimeType;

  @Entry.Column("priority")
  private int mPriority;

  @Entry.Column("component_name")
  private String mRawComponentName;

  @Entry.Column("request_template")
  private String mRequestTemplate;

  @Entry.Column("state")
  private int mState = 3;

  @Entry.Column("upload_url")
  private String mUploadUrl;

  @Entry.Column("uploaded_time")
  private long mUploadedTime;

  @Entry.Column("url")
  private String mUrl;

  static UploadTaskEntry createNew(ContentValues paramContentValues)
  {
    ArrayList localArrayList = new ArrayList();
    for (String str : REQUIRED_COLUMNS)
      if (paramContentValues.get(str) == null)
        localArrayList.add(str);
    if (!localArrayList.isEmpty())
      throw new RuntimeException("missing fields in upload request: " + localArrayList);
    return (UploadTaskEntry)SCHEMA.valuesToObject(paramContentValues, new UploadTaskEntry());
  }

  public static UploadTaskEntry fromDb(SQLiteDatabase paramSQLiteDatabase, long paramLong)
  {
    UploadTaskEntry localUploadTaskEntry = new UploadTaskEntry();
    if (SCHEMA.queryWithId(paramSQLiteDatabase, paramLong, localUploadTaskEntry));
    while (true)
    {
      return localUploadTaskEntry;
      localUploadTaskEntry = null;
    }
  }

  public final String getAccount()
  {
    return this.mAccount;
  }

  public final String getAlbumId()
  {
    return this.mAlbumId;
  }

  public final long getBytesTotal()
  {
    return this.mBytesTotal;
  }

  public final long getBytesUploaded()
  {
    return this.mBytesUploaded;
  }

  final ComponentName getComponentName()
  {
    if ((this.mComponentName == null) && (this.mRawComponentName != null))
      this.mComponentName = ComponentName.unflattenFromString(this.mRawComponentName);
    return this.mComponentName;
  }

  public final Uri getContentUri()
  {
    return Uri.parse(this.mContentUri);
  }

  public final String getEventId()
  {
    return this.mEventId;
  }

  public final Fingerprint getFingerprint()
  {
    if (this.mFingerprint == null);
    for (Fingerprint localFingerprint = null; ; localFingerprint = new Fingerprint(this.mFingerprint))
      return localFingerprint;
  }

  public final long getMediaRecordId()
  {
    return this.mMediaRecordId;
  }

  final String getMimeType()
  {
    return this.mMimeType;
  }

  public final int getPercentageUploaded()
  {
    int i;
    if ((this.mBytesTotal == 0L) || (this.mBytesUploaded == 0L))
      i = 0;
    while (true)
    {
      return i;
      i = (int)Math.round(100.0D * ((float)this.mBytesUploaded / (float)this.mBytesTotal));
      if (i > 100)
        i = 100;
    }
  }

  final String getRequestTemplate()
  {
    return this.mRequestTemplate;
  }

  public final int getState()
  {
    return this.mState;
  }

  final String getUploadUrl()
  {
    return this.mUploadUrl;
  }

  public final Uri getUrl()
  {
    if (this.mUrl == null);
    for (Uri localUri = null; ; localUri = Uri.parse(this.mUrl))
      return localUri;
  }

  public final boolean hasFingerprint()
  {
    if (this.mFingerprint != null);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean hasPriority()
  {
    int i = 1;
    if ((this.mPriority == 2) || (this.mPriority == i));
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  public final boolean isCancellable()
  {
    int i = 1;
    if ((this.mState == i) || (this.mState == 2) || (this.mState == 3));
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  public final boolean isReadyForUpload()
  {
    int i = 1;
    if ((this.mState == 3) || (this.mState == i));
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  public final boolean isStartedYet()
  {
    if (this.mBytesUploaded > 0L);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean isUploading()
  {
    int i = 1;
    if (this.mState == i);
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  public final void setAlbumId(String paramString)
  {
    this.mAlbumId = paramString;
  }

  final void setAuthTokenType(String paramString)
  {
    this.mAuthTokenType = paramString;
  }

  public final void setBytesTotal(long paramLong)
  {
    this.mBytesTotal = paramLong;
  }

  public final void setBytesUploaded(long paramLong)
  {
    this.mBytesUploaded = paramLong;
  }

  public final void setFingerprint(Fingerprint paramFingerprint)
  {
    this.mFingerprint = paramFingerprint.getBytes();
  }

  final void setMimeType(String paramString)
  {
    this.mMimeType = paramString;
  }

  final void setPriority(int paramInt)
  {
    this.mPriority = 1;
  }

  final void setRequestTemplate(String paramString)
  {
    this.mRequestTemplate = paramString;
  }

  public final void setState(int paramInt)
  {
    this.mState = paramInt;
  }

  public final void setState(int paramInt, Throwable paramThrowable)
  {
    this.mState = paramInt;
    this.mError = paramThrowable;
  }

  final void setUploadUrl(String paramString)
  {
    this.mUploadUrl = paramString;
  }

  final void setUploadedTime()
  {
    this.mUploadedTime = System.currentTimeMillis();
  }

  final void setUrl(String paramString)
  {
    this.mUrl = paramString;
  }

  public final boolean shouldRetry()
  {
    if (this.mState == 2);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public String toString()
  {
    return SCHEMA.toDebugString(this, new String[] { "content_uri", "media_record_id", "album_id", "event_id", "mime_type", "state", "bytes_total" }) + " [" + getPercentageUploaded() + "%]";
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.UploadTaskEntry
 * JD-Core Version:    0.6.2
 */