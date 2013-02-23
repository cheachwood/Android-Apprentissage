package com.google.android.picasasync;

import android.content.ContentValues;
import com.android.gallery3d.common.EntrySchema;
import com.android.gallery3d.common.Utils;
import com.google.android.apps.plus.json.JsonReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class AlbumCollectorJson extends PicasaJsonReaderParser
{
  private static final Map<String, PicasaJsonReaderParser.ObjectField> sAlbumEntryFieldMap = new HashMap();
  private static final Map<String, PicasaJsonReaderParser.ObjectField> sLinkFieldMap;

  static
  {
    EntrySchema localEntrySchema = AlbumEntry.SCHEMA;
    Map localMap = sAlbumEntryFieldMap;
    localMap.put("gphoto$id", newObjectField(localEntrySchema.getColumn("_id")));
    localMap.put("gphoto$albumType", newObjectField(localEntrySchema.getColumn("album_type")));
    localMap.put("gphoto$user", newObjectField(localEntrySchema.getColumn("user")));
    localMap.put("gphoto$bytesUsed", newObjectField(localEntrySchema.getColumn("bytes_used")));
    localMap.put("title", newObjectField(localEntrySchema.getColumn("title")));
    localMap.put("summary", newObjectField(localEntrySchema.getColumn("summary")));
    localMap.put("gphoto$numphotos", newObjectField(localEntrySchema.getColumn("num_photos")));
    localMap.put("published", new PicasaJsonReaderParser.ObjectField("date_published", 10));
    localMap.put("updated", new PicasaJsonReaderParser.ObjectField("date_updated", 10));
    localMap.put("app$edited", new PicasaJsonReaderParser.ObjectField("date_edited", 10));
    localMap.put("link", new PicasaJsonReaderParser.ObjectField(13));
    HashMap localHashMap1 = new HashMap();
    localMap.put("media$group", new PicasaJsonReaderParser.NestedObjectField(localHashMap1));
    localHashMap1.put("media$thumbnail", new PicasaJsonReaderParser.ObjectField(14));
    HashMap localHashMap2 = new HashMap();
    sLinkFieldMap = localHashMap2;
    localHashMap2.put("rel", new PicasaJsonReaderParser.ObjectField("rel", 0));
    localHashMap2.put("type", new PicasaJsonReaderParser.ObjectField("type", 0));
    localHashMap2.put("href", new PicasaJsonReaderParser.ObjectField("href", 0));
  }

  public AlbumCollectorJson(PicasaApi.EntryHandler paramEntryHandler)
  {
    super(paramEntryHandler);
  }

  protected final void addHtmlPageUrl(JsonReader paramJsonReader, ContentValues paramContentValues)
    throws IOException
  {
    paramJsonReader.beginArray();
    ContentValues localContentValues = new ContentValues();
    while (paramJsonReader.hasNext())
    {
      localContentValues.clear();
      parseObject(paramJsonReader, sLinkFieldMap, localContentValues);
      String str1 = localContentValues.getAsString("rel");
      String str2 = localContentValues.getAsString("type");
      if ((Utils.equals(str1, "alternate")) && (Utils.equals(str2, "text/html")))
      {
        paramContentValues.put("html_page_url", localContentValues.getAsString("href"));
        while (paramJsonReader.hasNext())
          paramJsonReader.skipValue();
      }
    }
    paramJsonReader.endArray();
  }

  protected final void addThumbnailUrl(JsonReader paramJsonReader, ContentValues paramContentValues, String paramString)
    throws IOException
  {
    paramJsonReader.beginArray();
    while (paramJsonReader.hasNext())
    {
      String str = parseObject(paramJsonReader, "url");
      if (str != null)
      {
        paramContentValues.put(paramString, str);
        while (paramJsonReader.hasNext())
          paramJsonReader.skipValue();
      }
    }
    paramJsonReader.endArray();
  }

  protected Map<String, PicasaJsonReaderParser.ObjectField> getEntryFieldMap()
  {
    return sAlbumEntryFieldMap;
  }

  protected void handleComplexValue(JsonReader paramJsonReader, int paramInt, ContentValues paramContentValues)
    throws IOException
  {
    switch (paramInt)
    {
    default:
      paramJsonReader.skipValue();
    case 13:
    case 14:
    }
    while (true)
    {
      return;
      addHtmlPageUrl(paramJsonReader, paramContentValues);
      continue;
      addThumbnailUrl(paramJsonReader, paramContentValues, "thumbnail_url");
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasasync.AlbumCollectorJson
 * JD-Core Version:    0.6.2
 */