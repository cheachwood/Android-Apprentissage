package com.google.android.picasasync;

import android.content.ContentValues;
import android.text.TextUtils;
import com.android.gallery3d.common.EntrySchema;
import com.android.gallery3d.common.Fingerprint;
import com.google.android.apps.plus.json.JsonReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

final class PhotoCollectorJson extends AlbumCollectorJson
{
  private static final Map<String, PicasaJsonReaderParser.ObjectField> sMediaContentFieldMap;
  private static final Map<String, PicasaJsonReaderParser.ObjectField> sPhotoEntryFieldMap = new HashMap();
  private static final Map<String, PicasaJsonReaderParser.ObjectField> sShapeFieldMap;

  static
  {
    EntrySchema localEntrySchema = PhotoEntry.SCHEMA;
    Map localMap = sPhotoEntryFieldMap;
    localMap.put("gphoto$id", newObjectField(localEntrySchema.getColumn("_id")));
    localMap.put("gphoto$albumid", newObjectField(localEntrySchema.getColumn("album_id")));
    localMap.put("gphoto$timestamp", newObjectField(localEntrySchema.getColumn("date_taken")));
    localMap.put("gphoto$width", newObjectField(localEntrySchema.getColumn("width")));
    localMap.put("gphoto$height", newObjectField(localEntrySchema.getColumn("height")));
    localMap.put("gphoto$size", newObjectField(localEntrySchema.getColumn("size")));
    localMap.put("title", newObjectField(localEntrySchema.getColumn("title")));
    localMap.put("summary", newObjectField(localEntrySchema.getColumn("summary")));
    localMap.put("gphoto$commentCount", newObjectField(localEntrySchema.getColumn("comment_count")));
    localMap.put("gphoto$rotation", newObjectField(localEntrySchema.getColumn("rotation")));
    localMap.put("published", new PicasaJsonReaderParser.ObjectField("date_published", 10));
    localMap.put("updated", new PicasaJsonReaderParser.ObjectField("date_updated", 10));
    localMap.put("app$edited", new PicasaJsonReaderParser.ObjectField("date_edited", 10));
    localMap.put("link", new PicasaJsonReaderParser.ObjectField(13));
    localMap.put("gphoto$streamId", new PicasaJsonReaderParser.ObjectField(15));
    HashMap localHashMap1 = new HashMap();
    localMap.put("media$group", new PicasaJsonReaderParser.NestedObjectField(localHashMap1));
    localHashMap1.put("media$content", new PicasaJsonReaderParser.ObjectField(17));
    localHashMap1.put("media$thumbnail", new PicasaJsonReaderParser.ObjectField(18));
    localHashMap1.put("media$keywords", newObjectField(localEntrySchema.getColumn("keywords")));
    HashMap localHashMap2 = new HashMap();
    localMap.put("gphoto$shapes", new PicasaJsonReaderParser.NestedObjectField(localHashMap2));
    localHashMap2.put("gphoto$shape", new PicasaJsonReaderParser.ObjectField(16));
    HashMap localHashMap3 = new HashMap();
    localMap.put("georss$where", new PicasaJsonReaderParser.NestedObjectField(localHashMap3));
    localHashMap3.put("gml$Point", new PicasaJsonReaderParser.ObjectField(14));
    HashMap localHashMap4 = new HashMap();
    localMap.put("exif$tags", new PicasaJsonReaderParser.NestedObjectField(localHashMap4));
    localHashMap4.put("exif$make", newObjectField(localEntrySchema.getColumn("exif_make")));
    localHashMap4.put("exif$model", newObjectField(localEntrySchema.getColumn("exif_model")));
    localHashMap4.put("exif$exposure", newObjectField(localEntrySchema.getColumn("exif_exposure")));
    localHashMap4.put("exif$flash", new PicasaJsonReaderParser.BooleanObjectField("exif_flash", 1, 2));
    localHashMap4.put("exif$focallength", newObjectField(localEntrySchema.getColumn("exif_focal_length")));
    localHashMap4.put("exif$iso", newObjectField(localEntrySchema.getColumn("exif_iso")));
    localHashMap4.put("exif$fstop", newObjectField(localEntrySchema.getColumn("exif_fstop")));
    HashMap localHashMap5 = new HashMap();
    sMediaContentFieldMap = localHashMap5;
    localHashMap5.put("url", new PicasaJsonReaderParser.ObjectField("url", 0));
    sMediaContentFieldMap.put("type", new PicasaJsonReaderParser.ObjectField("type", 0));
    HashMap localHashMap6 = new HashMap();
    sShapeFieldMap = localHashMap6;
    localHashMap6.put("personid", new PicasaJsonReaderParser.ObjectField("personid", 0));
    localHashMap6.put("name", new PicasaJsonReaderParser.ObjectField("name", 0));
    localHashMap6.put("upperLeft", new PicasaJsonReaderParser.ObjectField("upperLeft", 0));
    localHashMap6.put("lowerRight", new PicasaJsonReaderParser.ObjectField("lowerRight", 0));
  }

  public PhotoCollectorJson(PicasaApi.EntryHandler paramEntryHandler)
  {
    super(paramEntryHandler);
  }

  private int getFaces(JsonReader paramJsonReader, StringBuilder paramStringBuilder1, StringBuilder paramStringBuilder2, StringBuilder paramStringBuilder3)
    throws IOException
  {
    int i = 0;
    paramJsonReader.beginArray();
    ContentValues localContentValues = new ContentValues();
    while (paramJsonReader.hasNext())
    {
      localContentValues.clear();
      parseObject(paramJsonReader, sShapeFieldMap, localContentValues);
      String str1 = localContentValues.getAsString("name");
      String str2 = localContentValues.getAsString("personid");
      String str3 = localContentValues.getAsString("upperLeft");
      String str4 = localContentValues.getAsString("lowerRight");
      if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2)) && (!TextUtils.isEmpty(str3)) && (!TextUtils.isEmpty(str4)))
      {
        i++;
        if (i > 1)
        {
          paramStringBuilder1.append(',');
          paramStringBuilder2.append(',');
          paramStringBuilder3.append(',');
        }
        paramStringBuilder1.append(str1);
        paramStringBuilder2.append(str2);
        paramStringBuilder3.append(str3).append(' ').append(str4);
      }
    }
    paramJsonReader.endArray();
    return i;
  }

  private void parseStreamIds(JsonReader paramJsonReader, ContentValues paramContentValues)
    throws IOException
  {
    ArrayList localArrayList = new ArrayList();
    paramJsonReader.beginArray();
    while (paramJsonReader.hasNext())
    {
      String str = parseObject(paramJsonReader, "$t");
      if (str != null)
        if (str.equals("camera_sync_created"))
          paramContentValues.put("camera_sync", Integer.valueOf(1));
        else
          localArrayList.add(str);
    }
    paramJsonReader.endArray();
    Fingerprint localFingerprint = Fingerprint.extractFingerprint(localArrayList);
    if (localFingerprint != null)
    {
      paramContentValues.put("fingerprint", localFingerprint.getBytes());
      paramContentValues.put("fingerprint_hash", Integer.valueOf(localFingerprint.hashCode()));
    }
  }

  protected final Map<String, PicasaJsonReaderParser.ObjectField> getEntryFieldMap()
  {
    return sPhotoEntryFieldMap;
  }

  protected final void handleComplexValue(JsonReader paramJsonReader, int paramInt, ContentValues paramContentValues)
    throws IOException
  {
    switch (paramInt)
    {
    default:
      paramJsonReader.skipValue();
    case 13:
    case 17:
    case 18:
    case 14:
    case 15:
    case 16:
    }
    while (true)
    {
      return;
      addHtmlPageUrl(paramJsonReader, paramContentValues);
      continue;
      paramJsonReader.beginArray();
      ContentValues localContentValues = new ContentValues();
      while (paramJsonReader.hasNext())
      {
        localContentValues.clear();
        parseObject(paramJsonReader, sMediaContentFieldMap, localContentValues);
        String str2 = localContentValues.getAsString("type");
        if ((!paramContentValues.containsKey("content_url")) || (str2.startsWith("video/")))
        {
          paramContentValues.put("content_url", localContentValues.getAsString("url"));
          paramContentValues.put("content_type", str2);
        }
      }
      paramJsonReader.endArray();
      continue;
      addThumbnailUrl(paramJsonReader, paramContentValues, "screennail_url");
      continue;
      String str1 = parseObject(paramJsonReader, "gml$pos");
      if (str1 != null)
      {
        int i = str1.indexOf(' ');
        if (i != -1)
        {
          paramContentValues.put("latitude", str1.substring(0, i));
          paramContentValues.put("longitude", str1.substring(i + 1));
          continue;
          parseStreamIds(paramJsonReader, paramContentValues);
          continue;
          StringBuilder localStringBuilder1 = new StringBuilder();
          StringBuilder localStringBuilder2 = new StringBuilder();
          StringBuilder localStringBuilder3 = new StringBuilder();
          if (getFaces(paramJsonReader, localStringBuilder1, localStringBuilder2, localStringBuilder3) > 0)
          {
            paramContentValues.put("face_names", localStringBuilder1.toString());
            paramContentValues.put("face_ids", localStringBuilder2.toString());
            paramContentValues.put("face_rectangles", localStringBuilder3.toString());
          }
        }
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasasync.PhotoCollectorJson
 * JD-Core Version:    0.6.2
 */