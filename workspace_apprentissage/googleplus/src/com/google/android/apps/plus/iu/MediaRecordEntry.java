package com.google.android.apps.plus.iu;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.android.gallery3d.common.Entry;
import com.android.gallery3d.common.Entry.Column;
import com.android.gallery3d.common.Entry.Table;
import com.android.gallery3d.common.EntrySchema;
import com.android.gallery3d.common.Fingerprint;

@Entry.Table("media_record")
public class MediaRecordEntry extends Entry
{
  public static final EntrySchema SCHEMA = new EntrySchema(MediaRecordEntry.class);

  @Entry.Column("album_id")
  private String mAlbumId;

  @Entry.Column("bytes_total")
  private long mBytesTotal;

  @Entry.Column("bytes_uploaded")
  private long mBytesUploaded;
  private Throwable mError;

  @Entry.Column("event_id")
  private String mEventId;

  @Entry.Column("fingerprint")
  private byte[] mFingerprint;

  @Entry.Column(allowNull=false, defaultValue="0", value="from_camera")
  private boolean mFromCamera;

  @Entry.Column(allowNull=false, defaultValue="1", value="is_image")
  private boolean mIsImage;

  @Entry.Column(allowNull=false, value="media_hash")
  private long mMediaHash;

  @Entry.Column(allowNull=false, indexed=true, value="media_id")
  private long mMediaId;

  @Entry.Column(allowNull=false, value="media_time")
  private long mMediaTime;

  @Entry.Column(allowNull=false, value="media_url")
  private String mMediaUrl;

  @Entry.Column("upload_account")
  private String mUploadAccount;

  @Entry.Column("upload_error")
  private String mUploadError;

  @Entry.Column("upload_id")
  private long mUploadId;

  @Entry.Column(allowNull=false, defaultValue="0", value="upload_reason")
  private int mUploadReason;

  @Entry.Column(allowNull=false, defaultValue="200", value="upload_state")
  private int mUploadState;

  @Entry.Column("upload_time")
  private long mUploadTime;

  @Entry.Column("upload_url")
  private String mUploadUrl;

  static MediaRecordEntry createNew(ContentValues paramContentValues)
  {
    return (MediaRecordEntry)SCHEMA.valuesToObject(paramContentValues, new MediaRecordEntry());
  }

  public static MediaRecordEntry fromCursor(Cursor paramCursor)
  {
    return (MediaRecordEntry)SCHEMA.cursorToObject(paramCursor, new MediaRecordEntry());
  }

  public static MediaRecordEntry fromId(SQLiteDatabase paramSQLiteDatabase, long paramLong)
  {
    MediaRecordEntry localMediaRecordEntry = new MediaRecordEntry();
    if (SCHEMA.queryWithId(paramSQLiteDatabase, paramLong, localMediaRecordEntry));
    while (true)
    {
      return localMediaRecordEntry;
      localMediaRecordEntry = null;
    }
  }

  public static MediaRecordEntry fromMediaId(SQLiteDatabase paramSQLiteDatabase, long paramLong)
  {
    String str = SCHEMA.getTableName();
    String[] arrayOfString1 = SCHEMA.getProjection();
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = Long.toString(paramLong);
    Cursor localCursor = paramSQLiteDatabase.query(str, arrayOfString1, "media_id = ? AND upload_account IS NULL", arrayOfString2, null, null, null);
    try
    {
      if (localCursor.moveToFirst())
      {
        localMediaRecordEntry = (MediaRecordEntry)SCHEMA.cursorToObject(localCursor, new MediaRecordEntry());
        return localMediaRecordEntry;
      }
      localCursor.close();
      MediaRecordEntry localMediaRecordEntry = null;
    }
    finally
    {
      localCursor.close();
    }
  }

  public final String getAlbumId()
  {
    return this.mAlbumId;
  }

  public final long getBytesTotal()
  {
    return this.mBytesTotal;
  }

  public final String getEventId()
  {
    return this.mEventId;
  }

  public final Fingerprint getFingerprint()
  {
    if (this.mFingerprint != null);
    for (Fingerprint localFingerprint = new Fingerprint(this.mFingerprint); ; localFingerprint = null)
      return localFingerprint;
  }

  public final byte[] getFingerprintBytes()
  {
    return this.mFingerprint;
  }

  public final long getMediaTime()
  {
    return this.mMediaTime;
  }

  public final String getMediaUrl()
  {
    return this.mMediaUrl;
  }

  public final String getUploadAccount()
  {
    return this.mUploadAccount;
  }

  public final long getUploadId()
  {
    return this.mUploadId;
  }

  public final int getUploadReason()
  {
    return this.mUploadReason;
  }

  public final boolean isImage()
  {
    return this.mIsImage;
  }

  public final MediaRecordEntry setBytesUploaded(long paramLong)
  {
    this.mBytesUploaded = paramLong;
    return this;
  }

  public final MediaRecordEntry setState(int paramInt)
  {
    this.mUploadState = paramInt;
    return this;
  }

  public final MediaRecordEntry setState(int paramInt1, int paramInt2)
  {
    this.mUploadState = (paramInt1 + paramInt2);
    return this;
  }

  public final MediaRecordEntry setState(int paramInt1, int paramInt2, Throwable paramThrowable)
  {
    this.mUploadState = (paramInt1 + paramInt2);
    this.mError = paramThrowable;
    return this;
  }

  public final MediaRecordEntry setUploadAccount(String paramString)
  {
    this.mUploadAccount = paramString;
    return this;
  }

  public final MediaRecordEntry setUploadId(long paramLong)
  {
    this.mUploadId = paramLong;
    return this;
  }

  public final MediaRecordEntry setUploadReason(int paramInt)
  {
    this.mUploadReason = paramInt;
    return this;
  }

  public final MediaRecordEntry setUploadTime(long paramLong)
  {
    this.mUploadTime = paramLong;
    return this;
  }

  public final MediaRecordEntry setUploadUrl(String paramString)
  {
    this.mUploadUrl = paramString;
    return this;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append(SCHEMA.toDebugString(this, new String[] { "media_url", "media_id", "album_id", "event_id", "upload_reason", "upload_state", "upload_account", "upload_url", "bytes_total" })).append(" [");
    boolean bool1 = this.mBytesTotal < 0L;
    int i = 0;
    if (bool1)
    {
      boolean bool2 = this.mBytesUploaded < 0L;
      i = 0;
      if (bool2)
        break label118;
    }
    while (true)
    {
      return i + "%]";
      label118: i = Math.min((int)Math.round(100.0D * ((float)this.mBytesUploaded / (float)this.mBytesTotal)), 100);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.MediaRecordEntry
 * JD-Core Version:    0.6.2
 */