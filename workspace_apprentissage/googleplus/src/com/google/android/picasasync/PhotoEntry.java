package com.google.android.picasasync;

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
  public long albumId;

  @Entry.Column(defaultValue="0", value="cache_status")
  int cacheStatus;

  @Entry.Column("camera_sync")
  int cameraSync;

  @Entry.Column("comment_count")
  public int commentCount;

  @Entry.Column("content_type")
  public String contentType;

  @Entry.Column("content_url")
  public String contentUrl;

  @Entry.Column("date_edited")
  public long dateEdited;

  @Entry.Column("date_published")
  public long datePublished;

  @Entry.Column("date_taken")
  public long dateTaken;

  @Entry.Column("date_updated")
  public long dateUpdated;

  @Entry.Column(indexed=true, value="display_index")
  public int displayIndex;

  @Entry.Column("exif_exposure")
  public float exifExposure;

  @Entry.Column("exif_flash")
  public int exifFlash;

  @Entry.Column("exif_focal_length")
  public float exifFocalLength;

  @Entry.Column("exif_fstop")
  public float exifFstop;

  @Entry.Column("exif_iso")
  public int exifIso;

  @Entry.Column("exif_make")
  public String exifMake;

  @Entry.Column("exif_model")
  public String exifModel;

  @Entry.Column("face_ids")
  public String faceIds;

  @Entry.Column("face_names")
  public String faceNames;

  @Entry.Column("face_rectangles")
  public String faceRects;

  @Entry.Column("fingerprint")
  public byte[] fingerprint;

  @Entry.Column("fingerprint_hash")
  int fingerprintHash;

  @Entry.Column("height")
  public int height;

  @Entry.Column("html_page_url")
  public String htmlPageUrl;

  @Entry.Column("keywords")
  public String keywords;

  @Entry.Column("latitude")
  public double latitude;

  @Entry.Column("longitude")
  public double longitude;

  @Entry.Column("rotation")
  public int rotation;

  @Entry.Column("screennail_url")
  public String screennailUrl;

  @Entry.Column("size")
  public int size;

  @Entry.Column("summary")
  public String summary;

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
      boolean bool3 = this.albumId < localPhotoEntry.albumId;
      bool2 = false;
      if (!bool3)
      {
        int i = this.displayIndex;
        int j = localPhotoEntry.displayIndex;
        bool2 = false;
        if (i == j)
        {
          boolean bool4 = this.userId < localPhotoEntry.userId;
          bool2 = false;
          if (!bool4)
          {
            boolean bool5 = Utils.equals(this.title, localPhotoEntry.title);
            bool2 = false;
            if (bool5)
            {
              boolean bool6 = Utils.equals(this.summary, localPhotoEntry.summary);
              bool2 = false;
              if (bool6)
              {
                boolean bool7 = this.datePublished < localPhotoEntry.datePublished;
                bool2 = false;
                if (!bool7)
                {
                  boolean bool8 = this.dateUpdated < localPhotoEntry.dateUpdated;
                  bool2 = false;
                  if (!bool8)
                  {
                    boolean bool9 = this.dateEdited < localPhotoEntry.dateEdited;
                    bool2 = false;
                    if (!bool9)
                    {
                      boolean bool10 = this.dateTaken < localPhotoEntry.dateTaken;
                      bool2 = false;
                      if (!bool10)
                      {
                        int k = this.commentCount;
                        int m = localPhotoEntry.commentCount;
                        bool2 = false;
                        if (k == m)
                        {
                          int n = this.width;
                          int i1 = localPhotoEntry.width;
                          bool2 = false;
                          if (n == i1)
                          {
                            int i2 = this.height;
                            int i3 = localPhotoEntry.height;
                            bool2 = false;
                            if (i2 == i3)
                            {
                              int i4 = this.rotation;
                              int i5 = localPhotoEntry.rotation;
                              bool2 = false;
                              if (i4 == i5)
                              {
                                int i6 = this.size;
                                int i7 = localPhotoEntry.size;
                                bool2 = false;
                                if (i6 == i7)
                                {
                                  boolean bool11 = this.latitude < localPhotoEntry.latitude;
                                  bool2 = false;
                                  if (!bool11)
                                  {
                                    boolean bool12 = this.longitude < localPhotoEntry.longitude;
                                    bool2 = false;
                                    if (!bool12)
                                    {
                                      boolean bool13 = Utils.equals(this.contentUrl, localPhotoEntry.contentUrl);
                                      bool2 = false;
                                      if (bool13)
                                      {
                                        boolean bool14 = Utils.equals(this.htmlPageUrl, localPhotoEntry.htmlPageUrl);
                                        bool2 = false;
                                        if (bool14)
                                        {
                                          boolean bool15 = Utils.equals(this.keywords, localPhotoEntry.keywords);
                                          bool2 = false;
                                          if (bool15)
                                          {
                                            boolean bool16 = Utils.equals(this.faceNames, localPhotoEntry.faceNames);
                                            bool2 = false;
                                            if (bool16)
                                            {
                                              boolean bool17 = Utils.equals(this.faceIds, localPhotoEntry.faceIds);
                                              bool2 = false;
                                              if (bool17)
                                              {
                                                boolean bool18 = Utils.equals(this.faceRects, localPhotoEntry.faceRects);
                                                bool2 = false;
                                                if (bool18)
                                                {
                                                  boolean bool19 = Utils.equals(this.exifMake, localPhotoEntry.exifMake);
                                                  bool2 = false;
                                                  if (bool19)
                                                  {
                                                    boolean bool20 = Utils.equals(this.exifModel, localPhotoEntry.exifModel);
                                                    bool2 = false;
                                                    if (bool20)
                                                    {
                                                      boolean bool21 = this.exifExposure < localPhotoEntry.exifExposure;
                                                      bool2 = false;
                                                      if (!bool21)
                                                      {
                                                        int i8 = this.exifFlash;
                                                        int i9 = localPhotoEntry.exifFlash;
                                                        bool2 = false;
                                                        if (i8 == i9)
                                                        {
                                                          boolean bool22 = this.exifFocalLength < localPhotoEntry.exifFocalLength;
                                                          bool2 = false;
                                                          if (!bool22)
                                                          {
                                                            boolean bool23 = this.exifFstop < localPhotoEntry.exifFstop;
                                                            bool2 = false;
                                                            if (!bool23)
                                                            {
                                                              int i10 = this.exifIso;
                                                              int i11 = localPhotoEntry.exifIso;
                                                              bool2 = false;
                                                              if (i10 == i11)
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

  public final int hashCode()
  {
    return super.hashCode();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasasync.PhotoEntry
 * JD-Core Version:    0.6.2
 */