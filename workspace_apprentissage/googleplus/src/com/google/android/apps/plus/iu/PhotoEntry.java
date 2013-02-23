package com.google.android.apps.plus.iu;

import com.android.gallery3d.common.Entry;
import com.android.gallery3d.common.Entry.Column;
import com.android.gallery3d.common.Entry.Table;
import com.android.gallery3d.common.EntrySchema;
import com.android.gallery3d.common.Utils;

@Entry.Table("photos")
public final class PhotoEntry extends Entry
{
  public static final EntrySchema SCHEMA = new EntrySchema(PhotoEntry.class);

  @Entry.Column(indexed=true, value="album_id")
  public String albumId;

  @Entry.Column("content_type")
  public String contentType;

  @Entry.Column("date_edited")
  public long dateEdited;

  @Entry.Column("date_published")
  public long datePublished;

  @Entry.Column("date_taken")
  public long dateTaken;

  @Entry.Column("date_updated")
  public long dateUpdated;

  @Entry.Column("fingerprint")
  public byte[] fingerprint;

  @Entry.Column("fingerprint_hash")
  int fingerprintHash;

  @Entry.Column("height")
  public int height;

  @Entry.Column("size")
  public int size;

  @Entry.Column("title")
  public String title;

  @Entry.Column("user_id")
  public long userId;

  @Entry.Column("width")
  public int width;

  public final boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof PhotoEntry;
    boolean bool2 = false;
    if (!bool1);
    while (true)
    {
      return bool2;
      PhotoEntry localPhotoEntry = (PhotoEntry)paramObject;
      boolean bool3 = Utils.equals(this.albumId, localPhotoEntry.albumId);
      bool2 = false;
      if (bool3)
      {
        boolean bool4 = this.userId < localPhotoEntry.userId;
        bool2 = false;
        if (!bool4)
        {
          boolean bool5 = Utils.equals(this.title, localPhotoEntry.title);
          bool2 = false;
          if (bool5)
          {
            boolean bool6 = this.datePublished < localPhotoEntry.datePublished;
            bool2 = false;
            if (!bool6)
            {
              boolean bool7 = this.dateUpdated < localPhotoEntry.dateUpdated;
              bool2 = false;
              if (!bool7)
              {
                boolean bool8 = this.dateEdited < localPhotoEntry.dateEdited;
                bool2 = false;
                if (!bool8)
                {
                  boolean bool9 = this.dateTaken < localPhotoEntry.dateTaken;
                  bool2 = false;
                  if (!bool9)
                  {
                    int i = this.width;
                    int j = localPhotoEntry.width;
                    bool2 = false;
                    if (i == j)
                    {
                      int k = this.height;
                      int m = localPhotoEntry.height;
                      bool2 = false;
                      if (k == m)
                      {
                        int n = this.size;
                        int i1 = localPhotoEntry.size;
                        bool2 = false;
                        if (n == i1)
                          bool2 = true;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  public final int hashCode()
  {
    return super.hashCode();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.PhotoEntry
 * JD-Core Version:    0.6.2
 */