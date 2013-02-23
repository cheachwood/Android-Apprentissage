package com.google.android.picasasync;

import com.android.gallery3d.common.Entry;
import com.android.gallery3d.common.Entry.Column;
import com.android.gallery3d.common.Entry.Table;
import com.android.gallery3d.common.EntrySchema;
import com.android.gallery3d.common.Utils;

@Entry.Table("albums")
public class AlbumEntry extends Entry
{
  public static final EntrySchema SCHEMA = new EntrySchema(AlbumEntry.class);

  @Entry.Column("album_type")
  public String albumType;

  @Entry.Column("bytes_used")
  public long bytesUsed;

  @Entry.Column(defaultValue="1", value="cache_flag")
  public int cacheFlag;

  @Entry.Column(defaultValue="0", value="cache_status")
  public int cacheStatus;

  @Entry.Column("date_edited")
  public long dateEdited;

  @Entry.Column("date_published")
  public long datePublished;

  @Entry.Column("date_updated")
  public long dateUpdated;

  @Entry.Column("html_page_url")
  public String htmlPageUrl;

  @Entry.Column("location_string")
  public String locationString;

  @Entry.Column("num_photos")
  public int numPhotos;

  @Entry.Column("photos_dirty")
  public boolean photosDirty;

  @Entry.Column("photos_etag")
  public String photosEtag = null;

  @Entry.Column("summary")
  public String summary;

  @Entry.Column("thumbnail_url")
  public String thumbnailUrl;

  @Entry.Column("title")
  public String title;

  @Entry.Column("user")
  public String user;

  @Entry.Column(indexed=true, value="user_id")
  public long userId;

  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof AlbumEntry;
    boolean bool2 = false;
    if (!bool1);
    while (true)
    {
      return bool2;
      AlbumEntry localAlbumEntry = (AlbumEntry)paramObject;
      boolean bool3 = this.userId < localAlbumEntry.userId;
      bool2 = false;
      if (!bool3)
      {
        int i = this.cacheFlag;
        int j = localAlbumEntry.cacheFlag;
        bool2 = false;
        if (i == j)
        {
          int k = this.cacheStatus;
          int m = localAlbumEntry.cacheStatus;
          bool2 = false;
          if (k == m)
          {
            boolean bool4 = this.photosDirty;
            boolean bool5 = localAlbumEntry.photosDirty;
            bool2 = false;
            if (bool4 == bool5)
            {
              boolean bool6 = Utils.equals(this.albumType, localAlbumEntry.albumType);
              bool2 = false;
              if (bool6)
              {
                boolean bool7 = Utils.equals(this.user, localAlbumEntry.user);
                bool2 = false;
                if (bool7)
                {
                  boolean bool8 = Utils.equals(this.title, localAlbumEntry.title);
                  bool2 = false;
                  if (bool8)
                  {
                    boolean bool9 = Utils.equals(this.summary, localAlbumEntry.summary);
                    bool2 = false;
                    if (bool9)
                    {
                      boolean bool10 = this.datePublished < localAlbumEntry.datePublished;
                      bool2 = false;
                      if (!bool10)
                      {
                        boolean bool11 = this.dateUpdated < localAlbumEntry.dateUpdated;
                        bool2 = false;
                        if (!bool11)
                        {
                          boolean bool12 = this.dateEdited < localAlbumEntry.dateEdited;
                          bool2 = false;
                          if (!bool12)
                          {
                            int n = this.numPhotos;
                            int i1 = localAlbumEntry.numPhotos;
                            bool2 = false;
                            if (n == i1)
                            {
                              boolean bool13 = this.bytesUsed < localAlbumEntry.bytesUsed;
                              bool2 = false;
                              if (!bool13)
                              {
                                boolean bool14 = Utils.equals(this.locationString, localAlbumEntry.locationString);
                                bool2 = false;
                                if (bool14)
                                {
                                  boolean bool15 = Utils.equals(this.thumbnailUrl, localAlbumEntry.thumbnailUrl);
                                  bool2 = false;
                                  if (bool15)
                                  {
                                    boolean bool16 = Utils.equals(this.htmlPageUrl, localAlbumEntry.htmlPageUrl);
                                    bool2 = false;
                                    if (bool16)
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
            }
          }
        }
      }
    }
  }

  public int hashCode()
  {
    return super.hashCode();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasasync.AlbumEntry
 * JD-Core Version:    0.6.2
 */