package com.google.android.apps.plus.iu;

import android.content.ContentValues;
import com.android.gallery3d.common.EntrySchema;
import com.android.gallery3d.common.Fingerprint;
import com.google.android.apps.plus.json.JsonReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

final class PhotoCollectorJson extends PicasaJsonReaderParser
{
  private static final Map<String, PicasaJsonReaderParser.ObjectField> sMediaContentFieldMap;
  private static final Map<String, PicasaJsonReaderParser.ObjectField> sPhotoEntryFieldMap = new HashMap();

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
    localMap.put("published", new PicasaJsonReaderParser.ObjectField("date_published", 10));
    localMap.put("updated", new PicasaJsonReaderParser.ObjectField("date_updated", 10));
    localMap.put("app$edited", new PicasaJsonReaderParser.ObjectField("date_edited", 10));
    localMap.put("gphoto$streamId", new PicasaJsonReaderParser.ObjectField(13));
    HashMap localHashMap1 = new HashMap();
    localMap.put("media$group", new PicasaJsonReaderParser.NestedObjectField(localHashMap1));
    localHashMap1.put("media$content", new PicasaJsonReaderParser.ObjectField(14));
    HashMap localHashMap2 = new HashMap();
    sMediaContentFieldMap = localHashMap2;
    localHashMap2.put("type", new PicasaJsonReaderParser.ObjectField("type", 0));
  }

  public PhotoCollectorJson(PicasaApi.EntryHandler paramEntryHandler)
  {
    super(paramEntryHandler);
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
    case 14:
    case 13:
    }
    while (true)
    {
      return;
      paramJsonReader.beginArray();
      ContentValues localContentValues = new ContentValues();
      while (paramJsonReader.hasNext())
      {
        localContentValues.clear();
        parseObject(paramJsonReader, sMediaContentFieldMap, localContentValues);
        String str = localContentValues.getAsString("type");
        if (str != null)
          paramContentValues.put("content_type", str);
      }
      paramJsonReader.endArray();
      continue;
      parseStreamIds(paramJsonReader, paramContentValues);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.PhotoCollectorJson
 * JD-Core Version:    0.6.2
 */