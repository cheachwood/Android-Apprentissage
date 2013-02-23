package com.google.android.picasasync;

import android.content.ContentValues;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import com.android.gallery3d.common.EntrySchema.ColumnInfo;
import com.android.gallery3d.common.Utils;
import com.google.android.apps.plus.json.JsonReader;
import com.google.android.apps.plus.json.JsonToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

abstract class PicasaJsonReaderParser
{
  int entryCount;
  private final PicasaApi.EntryHandler mHandler;
  int totalCount;

  protected PicasaJsonReaderParser(PicasaApi.EntryHandler paramEntryHandler)
  {
    this.mHandler = ((PicasaApi.EntryHandler)Utils.checkNotNull(paramEntryHandler));
  }

  protected static ObjectField newObjectField(EntrySchema.ColumnInfo paramColumnInfo)
  {
    int i;
    switch (paramColumnInfo.type)
    {
    case 2:
    default:
      Log.e("gp.PicasaAPI", "unexpected column " + paramColumnInfo.name + " of type " + paramColumnInfo.type);
      i = 11;
    case 0:
    case 1:
    case 3:
    case 4:
    case 5:
    case 6:
    }
    while (true)
    {
      return new ObjectField(paramColumnInfo.name, i);
      i = 0;
      continue;
      i = 1;
      continue;
      i = 3;
      continue;
      i = 4;
      continue;
      i = 5;
      continue;
      i = 6;
    }
  }

  private void parseFeed(JsonReader paramJsonReader)
    throws IOException
  {
    String str1 = null;
    this.entryCount = 0;
    this.totalCount = -1;
    paramJsonReader.beginObject();
    while (paramJsonReader.hasNext())
    {
      String str2 = paramJsonReader.nextName();
      if (str2.equals("gd$etag"))
      {
        str1 = paramJsonReader.nextString();
      }
      else if (str2.equals("openSearch$totalResults"))
      {
        this.totalCount = Integer.parseInt(parseObject(paramJsonReader, "$t"));
      }
      else if (str2.equals("entry"))
      {
        paramJsonReader.beginArray();
        while (paramJsonReader.hasNext())
        {
          ContentValues localContentValues = new ContentValues();
          parseObject(paramJsonReader, getEntryFieldMap(), localContentValues);
          this.mHandler.handleEntry(localContentValues);
          this.entryCount = (1 + this.entryCount);
        }
        paramJsonReader.endArray();
      }
      else
      {
        paramJsonReader.skipValue();
      }
    }
    paramJsonReader.endObject();
    Log.v("gp.PicasaAPI", "   etag: --> " + str1 + ",entryCount=" + this.entryCount);
  }

  private static void parsePrimitiveValue(JsonReader paramJsonReader, ObjectField paramObjectField, ContentValues paramContentValues)
    throws IOException
  {
    String str1 = paramObjectField.columnName;
    int i = paramObjectField.type;
    switch (i)
    {
    case 2:
    case 7:
    case 8:
    case 9:
    default:
      try
      {
        throw new RuntimeException("unexpected type: " + i + " for " + str1);
      }
      catch (Exception localException2)
      {
        Log.e("gp.PicasaAPI", "error parsing value", localException2);
        paramJsonReader.skipValue();
      }
    case 0:
    case 1:
    case 3:
    case 4:
    case 5:
    case 6:
    case 10:
    }
    while (true)
    {
      return;
      paramContentValues.put(str1, paramJsonReader.nextString());
      continue;
      BooleanObjectField localBooleanObjectField = (BooleanObjectField)paramObjectField;
      if (Boolean.parseBoolean(paramJsonReader.nextString()));
      for (int j = localBooleanObjectField.onValue; ; j = localBooleanObjectField.offValue)
      {
        paramContentValues.put(str1, Integer.valueOf(j));
        break;
      }
      paramContentValues.put(str1, Integer.valueOf(paramJsonReader.nextInt()));
      continue;
      paramContentValues.put(str1, Long.valueOf(paramJsonReader.nextLong()));
      continue;
      paramContentValues.put(str1, Float.valueOf((float)paramJsonReader.nextDouble()));
      continue;
      paramContentValues.put(str1, Double.valueOf(paramJsonReader.nextDouble()));
      continue;
      String str2 = paramJsonReader.nextString();
      boolean bool = TextUtils.isEmpty(str2);
      if (!bool)
        try
        {
          Time localTime = new Time();
          localTime.parse3339(str2);
          paramContentValues.put(str1, Long.valueOf(localTime.toMillis(true)));
        }
        catch (Exception localException1)
        {
          Log.w("gp.PicasaAPI", "parseAtomTimestamp", localException1);
        }
    }
  }

  protected abstract Map<String, ObjectField> getEntryFieldMap();

  protected abstract void handleComplexValue(JsonReader paramJsonReader, int paramInt, ContentValues paramContentValues)
    throws IOException;

  public final void parse(InputStream paramInputStream)
    throws IOException
  {
    JsonReader localJsonReader = new JsonReader(new InputStreamReader(paramInputStream, "UTF-8"));
    localJsonReader.beginObject();
    if (localJsonReader.hasNext())
      if (localJsonReader.nextName().equals("feed"))
        parseFeed(localJsonReader);
    while (true)
    {
      return;
      localJsonReader.skipValue();
      break;
      localJsonReader.endObject();
    }
  }

  protected final String parseObject(JsonReader paramJsonReader, String paramString)
    throws IOException
  {
    paramJsonReader.beginObject();
    String str;
    while (true)
    {
      boolean bool = paramJsonReader.hasNext();
      str = null;
      if (!bool)
        break;
      if (paramString.equals(paramJsonReader.nextName()))
      {
        if (paramJsonReader.peek() == JsonToken.BEGIN_OBJECT)
          str = parseObject(paramJsonReader, "$t");
        while (paramJsonReader.hasNext())
        {
          paramJsonReader.nextName();
          paramJsonReader.skipValue();
          continue;
          str = paramJsonReader.nextString();
        }
      }
      paramJsonReader.skipValue();
    }
    paramJsonReader.endObject();
    return str;
  }

  protected final void parseObject(JsonReader paramJsonReader, Map<String, ObjectField> paramMap, ContentValues paramContentValues)
    throws IOException
  {
    paramJsonReader.beginObject();
    while (paramJsonReader.hasNext())
    {
      ObjectField localObjectField = (ObjectField)paramMap.get(paramJsonReader.nextName());
      if (localObjectField != null)
      {
        if (localObjectField.type >= 12)
        {
          switch (localObjectField.type)
          {
          default:
            handleComplexValue(paramJsonReader, localObjectField.type, paramContentValues);
            break;
          case 12:
            parseObject(paramJsonReader, ((NestedObjectField)localObjectField).map, paramContentValues);
            break;
          }
        }
        else
        {
          if (paramJsonReader.peek() == JsonToken.BEGIN_OBJECT)
          {
            paramJsonReader.beginObject();
            if (paramJsonReader.hasNext())
            {
              Utils.assertTrue(paramJsonReader.nextName().equals("$t"));
              parsePrimitiveValue(paramJsonReader, localObjectField, paramContentValues);
              if (paramJsonReader.hasNext())
                break label157;
            }
            label157: for (boolean bool = true; ; bool = false)
            {
              Utils.assertTrue(bool);
              paramJsonReader.endObject();
              break;
            }
          }
          parsePrimitiveValue(paramJsonReader, localObjectField, paramContentValues);
        }
      }
      else
        paramJsonReader.skipValue();
    }
    paramJsonReader.endObject();
  }

  protected static final class BooleanObjectField extends PicasaJsonReaderParser.ObjectField
  {
    final int offValue = 2;
    final int onValue = 1;

    BooleanObjectField(String paramString, int paramInt1, int paramInt2)
    {
      super(1);
    }
  }

  protected static final class NestedObjectField extends PicasaJsonReaderParser.ObjectField
  {
    final Map<String, PicasaJsonReaderParser.ObjectField> map;

    NestedObjectField(Map<String, PicasaJsonReaderParser.ObjectField> paramMap)
    {
      super();
      this.map = paramMap;
    }
  }

  protected static class ObjectField
  {
    final String columnName;
    final int type;

    ObjectField(int paramInt)
    {
      this.columnName = null;
      this.type = paramInt;
      if (paramInt > 10);
      for (boolean bool = true; ; bool = false)
      {
        Utils.assertTrue(bool);
        return;
      }
    }

    ObjectField(String paramString, int paramInt)
    {
      this.columnName = paramString;
      this.type = paramInt;
      if (paramInt <= 10);
      for (boolean bool = true; ; bool = false)
      {
        Utils.assertTrue(bool);
        return;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasasync.PicasaJsonReaderParser
 * JD-Core Version:    0.6.2
 */