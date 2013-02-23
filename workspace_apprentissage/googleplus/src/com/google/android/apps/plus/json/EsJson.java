package com.google.android.apps.plus.json;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class EsJson<T>
{
  protected static final Object JSON_KEY = new Object();
  protected static final Object JSON_STRING = new Object();
  private static final Charset UTF_8 = Charset.forName("UTF-8");
  private static HashMap<Class<?>, SimpleJson<?>> sSimpleJsonMap = new HashMap();
  private Object[] mConfiguration;
  private FieldConverter[] mFieldConverters;
  private Class<T> mTargetClass;

  protected EsJson()
  {
  }

  protected EsJson(Class<T> paramClass, Object[] paramArrayOfObject)
  {
    this.mTargetClass = paramClass;
    this.mConfiguration = paramArrayOfObject;
  }

  public static <E> EsJson<E> buildJson(Class<E> paramClass, Object[] paramArrayOfObject)
  {
    return new EsJson(paramClass, paramArrayOfObject)
    {
    };
  }

  public static <E> EsJson<E> getSimpleJson(Class<E> paramClass)
  {
    SimpleJson localSimpleJson = (SimpleJson)sSimpleJsonMap.get(paramClass);
    if (localSimpleJson == null)
    {
      localSimpleJson = new SimpleJson(paramClass);
      sSimpleJsonMap.put(paramClass, localSimpleJson);
    }
    return localSimpleJson;
  }

  private static void initConverter(FieldConverter paramFieldConverter, Field paramField, boolean paramBoolean)
  {
    paramFieldConverter.field = paramField;
    Class localClass1 = paramField.getType();
    if (localClass1 == String.class)
      paramFieldConverter.type = 0;
    while (true)
    {
      if (paramBoolean)
        paramFieldConverter.type = (0x20 | paramFieldConverter.type);
      return;
      if ((localClass1 == Integer.class) || (localClass1 == Integer.TYPE))
      {
        paramFieldConverter.type = 1;
      }
      else if ((localClass1 == Long.class) || (localClass1 == Long.TYPE))
      {
        paramFieldConverter.type = 2;
      }
      else if ((localClass1 == Float.class) || (localClass1 == Float.TYPE))
      {
        paramFieldConverter.type = 3;
      }
      else if ((localClass1 == Double.class) || (localClass1 == Double.TYPE))
      {
        paramFieldConverter.type = 4;
      }
      else if ((localClass1 == Boolean.class) || (localClass1 == Boolean.TYPE))
      {
        paramFieldConverter.type = 5;
      }
      else if (localClass1 == BigInteger.class)
      {
        paramFieldConverter.type = 6;
      }
      else if (List.class.isAssignableFrom(localClass1))
      {
        Class localClass2 = (Class)((java.lang.reflect.ParameterizedType)paramField.getGenericType()).getActualTypeArguments()[0];
        paramFieldConverter.type = 7;
        if (localClass2 == String.class)
          paramFieldConverter.itemType = 0;
        while (true)
        {
          if (!paramBoolean)
            break label321;
          paramFieldConverter.itemType = (0x20 | paramFieldConverter.itemType);
          paramBoolean = false;
          break;
          if (localClass2 == Integer.class)
            paramFieldConverter.itemType = 1;
          else if (localClass2 == Long.class)
            paramFieldConverter.itemType = 2;
          else if (localClass2 == Float.class)
            paramFieldConverter.itemType = 3;
          else if (localClass2 == Double.class)
            paramFieldConverter.itemType = 4;
          else if (localClass2 == Boolean.class)
            paramFieldConverter.itemType = 5;
          else if (localClass2 == BigInteger.class)
            paramFieldConverter.itemType = 6;
          else
            paramFieldConverter.itemType = 8;
        }
      }
      else
      {
        label321: paramFieldConverter.type = 8;
      }
    }
  }

  private void initializeFieldConverters()
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    FieldConverter localFieldConverter;
    int j;
    Object localObject1;
    int i3;
    if (i < this.mConfiguration.length)
    {
      localFieldConverter = new FieldConverter((byte)0);
      Object[] arrayOfObject1 = this.mConfiguration;
      j = i + 1;
      localObject1 = arrayOfObject1[i];
      if (localObject1 != JSON_KEY)
        break label452;
      Object[] arrayOfObject6 = this.mConfiguration;
      int i2 = j + 1;
      localFieldConverter.key = ((String)arrayOfObject6[j]);
      Object[] arrayOfObject7 = this.mConfiguration;
      i3 = i2 + 1;
      localObject1 = arrayOfObject7[i2];
    }
    label174: label452: for (i = i3; ; i = j)
    {
      while (true)
      {
        Object localObject2 = JSON_STRING;
        boolean bool = false;
        if (localObject1 == localObject2)
        {
          bool = true;
          Object[] arrayOfObject5 = this.mConfiguration;
          int i1 = i + 1;
          localObject1 = arrayOfObject5[i];
          i = i1;
        }
        String str;
        if ((localObject1 instanceof EsJson))
        {
          localFieldConverter.json = ((EsJson)localObject1);
          Object[] arrayOfObject4 = this.mConfiguration;
          int n = i + 1;
          localObject1 = arrayOfObject4[i];
          i = n;
          str = (String)localObject1;
          if (localFieldConverter.key == null)
            localFieldConverter.key = str;
          localFieldConverter.firstChar = localFieldConverter.key.charAt(0);
        }
        try
        {
          while (true)
          {
            Field localField = this.mTargetClass.getField(str);
            initConverter(localFieldConverter, localField, bool);
            localArrayList.add(localFieldConverter);
            break;
            if ((Integer.class == localObject1) || (Long.class == localObject1) || (Float.class == localObject1) || (Double.class == localObject1) || (Boolean.class == localObject1) || (BigInteger.class == localObject1))
            {
              Object[] arrayOfObject2 = this.mConfiguration;
              int k = i + 1;
              localObject1 = arrayOfObject2[i];
              i = k;
              break label174;
            }
            if (!(localObject1 instanceof Class))
              break label174;
            Class localClass = (Class)localObject1;
            try
            {
              localFieldConverter.json = ((EsJson)localClass.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]));
              Object[] arrayOfObject3 = this.mConfiguration;
              int m = i + 1;
              localObject1 = arrayOfObject3[i];
              i = m;
            }
            catch (Exception localException)
            {
              throw new IllegalStateException("Invalid EsJson class: " + localClass, localException);
            }
          }
        }
        catch (NoSuchFieldException localNoSuchFieldException)
        {
          throw new IllegalStateException("No such field: " + this.mTargetClass + "." + str);
        }
      }
      this.mFieldConverters = new FieldConverter[localArrayList.size()];
      localArrayList.toArray(this.mFieldConverters);
      return;
    }
  }

  private T read(JsonReader paramJsonReader, UnknownKeyHandler paramUnknownKeyHandler)
    throws IOException
  {
    if (this.mFieldConverters == null)
    {
      if (this.mTargetClass == null)
        throw new UnsupportedOperationException("A JSON class must either configure the automatic parser or override read(JsonReader)");
      initializeFieldConverters();
    }
    Object localObject1 = newInstance();
    paramJsonReader.beginObject();
    while (paramJsonReader.hasNext())
    {
      String str = paramJsonReader.nextName();
      int i = str.charAt(0);
      int j = 0;
      label61: int k = this.mFieldConverters.length;
      Object localObject2 = null;
      Object localObject3;
      if (j < k)
      {
        FieldConverter localFieldConverter = this.mFieldConverters[j];
        if ((localFieldConverter.firstChar == i) && (localFieldConverter.key.equals(str)))
          localObject2 = localFieldConverter;
      }
      else
      {
        if (localObject2 == null)
          break label950;
        switch (localObject2.type)
        {
        case 9:
        case 10:
        case 11:
        case 12:
        case 13:
        case 14:
        case 15:
        case 16:
        case 17:
        case 18:
        case 19:
        case 20:
        case 21:
        case 22:
        case 23:
        case 24:
        case 25:
        case 26:
        case 27:
        case 28:
        case 29:
        case 30:
        case 31:
        default:
          localObject3 = null;
          paramJsonReader.skipValue();
        case 0:
        case 32:
        case 1:
        case 33:
        case 2:
        case 34:
        case 3:
        case 35:
        case 4:
        case 36:
        case 5:
        case 37:
        case 6:
        case 38:
        case 8:
        case 7:
        }
      }
      while (true)
      {
        try
        {
          localObject2.field.set(localObject1, localObject3);
        }
        catch (Exception localException)
        {
          throw new IOException("Cannot assign field value: " + localObject2.field + " " + localObject3 + " " + localException.getMessage());
        }
        j++;
        break label61;
        localObject3 = paramJsonReader.nextString();
        continue;
        localObject3 = Integer.valueOf(paramJsonReader.nextInt());
        continue;
        localObject3 = Integer.decode(paramJsonReader.nextString());
        continue;
        localObject3 = Long.valueOf(paramJsonReader.nextLong());
        continue;
        localObject3 = Long.decode(paramJsonReader.nextString());
        continue;
        localObject3 = Float.valueOf((float)paramJsonReader.nextDouble());
        continue;
        localObject3 = Float.valueOf(paramJsonReader.nextString());
        continue;
        localObject3 = Double.valueOf(paramJsonReader.nextDouble());
        continue;
        localObject3 = Double.valueOf(paramJsonReader.nextString());
        continue;
        if (paramJsonReader.nextBoolean());
        for (localObject3 = Boolean.TRUE; ; localObject3 = Boolean.FALSE)
          break;
        localObject3 = Boolean.valueOf(paramJsonReader.nextString());
        continue;
        localObject3 = new BigInteger(paramJsonReader.nextString());
        continue;
        localObject3 = localObject2.json.read(paramJsonReader, paramUnknownKeyHandler);
        continue;
        ArrayList localArrayList = new ArrayList();
        paramJsonReader.beginArray();
        while (paramJsonReader.hasNext())
          switch (localObject2.itemType)
          {
          default:
            break;
          case 0:
          case 32:
            localArrayList.add(paramJsonReader.nextString());
            break;
          case 1:
            localArrayList.add(Integer.valueOf(paramJsonReader.nextInt()));
            break;
          case 33:
            localArrayList.add(Integer.decode(paramJsonReader.nextString()));
            break;
          case 2:
            localArrayList.add(Long.valueOf(paramJsonReader.nextLong()));
            break;
          case 34:
            localArrayList.add(Long.decode(paramJsonReader.nextString()));
            break;
          case 3:
            localArrayList.add(Float.valueOf((float)paramJsonReader.nextDouble()));
            break;
          case 35:
            localArrayList.add(Float.valueOf(paramJsonReader.nextString()));
            break;
          case 4:
            localArrayList.add(Double.valueOf(paramJsonReader.nextDouble()));
            break;
          case 36:
            localArrayList.add(Double.valueOf(paramJsonReader.nextString()));
            break;
          case 5:
            if (paramJsonReader.nextBoolean());
            for (Boolean localBoolean = Boolean.TRUE; ; localBoolean = Boolean.FALSE)
            {
              localArrayList.add(localBoolean);
              break;
            }
          case 37:
            localArrayList.add(Boolean.valueOf(paramJsonReader.nextString()));
            break;
          case 6:
          case 38:
            localArrayList.add(new BigInteger(paramJsonReader.nextString()));
            break;
          case 8:
            localArrayList.add(localObject2.json.read(paramJsonReader, null));
          }
        paramJsonReader.endArray();
        localObject3 = localArrayList;
      }
      label950: boolean bool = false;
      if (paramUnknownKeyHandler != null)
        bool = paramUnknownKeyHandler.handleUnknownKey$7f90bde0();
      if (!bool)
        paramJsonReader.skipValue();
    }
    paramJsonReader.endObject();
    return localObject1;
  }

  private void write(JsonWriter paramJsonWriter, T paramT)
    throws IOException
  {
    writeObject(paramJsonWriter, paramT);
  }

  private void writeObject(JsonWriter paramJsonWriter, Object paramObject)
    throws IOException
  {
    if (this.mFieldConverters == null)
    {
      if (this.mTargetClass == null)
        throw new UnsupportedOperationException("A JSON class must either configure the automatic parser or override read(Jsonwriter)");
      initializeFieldConverters();
    }
    paramJsonWriter.beginObject();
    Object[] arrayOfObject = getValues(paramObject);
    int i = 0;
    if (i < arrayOfObject.length)
    {
      Object localObject1;
      FieldConverter localFieldConverter;
      if (arrayOfObject[i] != null)
      {
        localObject1 = arrayOfObject[i];
        localFieldConverter = this.mFieldConverters[i];
        paramJsonWriter.name(localFieldConverter.key);
        switch (localFieldConverter.type)
        {
        case 9:
        case 10:
        case 11:
        case 12:
        case 13:
        case 14:
        case 15:
        case 16:
        case 17:
        case 18:
        case 19:
        case 20:
        case 21:
        case 22:
        case 23:
        case 24:
        case 25:
        case 26:
        case 27:
        case 28:
        case 29:
        case 30:
        case 31:
        default:
        case 0:
        case 32:
        case 1:
        case 2:
        case 6:
        case 3:
        case 4:
        case 5:
        case 33:
        case 34:
        case 35:
        case 36:
        case 37:
        case 38:
        case 8:
        case 7:
        }
      }
      while (true)
      {
        i++;
        break;
        paramJsonWriter.value((String)localObject1);
        continue;
        paramJsonWriter.value((Number)localObject1);
        continue;
        paramJsonWriter.value((Float)localObject1);
        continue;
        paramJsonWriter.value((Double)localObject1);
        continue;
        paramJsonWriter.value(((Boolean)localObject1).booleanValue());
        continue;
        paramJsonWriter.value(localObject1.toString());
        continue;
        localFieldConverter.json.writeObject(paramJsonWriter, localObject1);
        continue;
        List localList = (List)localObject1;
        paramJsonWriter.beginArray();
        int j = localList.size();
        int k = 0;
        if (k < j)
        {
          Object localObject2 = localList.get(k);
          switch (localFieldConverter.itemType)
          {
          default:
          case 0:
          case 32:
          case 1:
          case 2:
          case 6:
          case 3:
          case 4:
          case 5:
          case 33:
          case 34:
          case 35:
          case 36:
          case 37:
          case 38:
          case 8:
          }
          while (true)
          {
            k++;
            break;
            paramJsonWriter.value((String)localObject2);
            continue;
            paramJsonWriter.value((Number)localObject2);
            continue;
            paramJsonWriter.value((Float)localObject2);
            continue;
            paramJsonWriter.value((Double)localObject2);
            continue;
            paramJsonWriter.value(((Boolean)localObject2).booleanValue());
            continue;
            paramJsonWriter.value(localObject2.toString());
            continue;
            localFieldConverter.json.writeObject(paramJsonWriter, localObject2);
          }
        }
        paramJsonWriter.endArray();
      }
    }
    paramJsonWriter.endObject();
  }

  protected final void buildDefaultConfiguration()
  {
    Field[] arrayOfField = this.mTargetClass.getFields();
    this.mFieldConverters = new FieldConverter[arrayOfField.length];
    for (int i = 0; i < arrayOfField.length; i++)
    {
      Field localField = arrayOfField[i];
      FieldConverter localFieldConverter = new FieldConverter((byte)0);
      localFieldConverter.key = localField.getName();
      localFieldConverter.firstChar = localFieldConverter.key.charAt(0);
      initConverter(localFieldConverter, localField, false);
      if ((localFieldConverter.type == 8) || (localFieldConverter.itemType == 8))
        throw new RuntimeException("Cannot use default JSON for object containing fields of non-basic types: " + this.mTargetClass + "." + localFieldConverter.field.getName());
      this.mFieldConverters[i] = localFieldConverter;
    }
  }

  public final T fromByteArray(byte[] paramArrayOfByte)
  {
    try
    {
      Object localObject = fromInputStream(new ByteArrayInputStream(paramArrayOfByte));
      return localObject;
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("Cannot parse JSON using " + getClass().getSimpleName(), localIOException);
    }
  }

  public final T fromInputStream(InputStream paramInputStream)
    throws IOException
  {
    JsonReader localJsonReader = new JsonReader(new InputStreamReader(paramInputStream, UTF_8));
    Object localObject = read(localJsonReader, null);
    localJsonReader.close();
    return localObject;
  }

  public final T fromString(String paramString)
  {
    try
    {
      JsonReader localJsonReader = new JsonReader(new StringReader(paramString));
      Object localObject = read(localJsonReader, null);
      localJsonReader.close();
      return localObject;
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("Cannot parse JSON using " + getClass().getSimpleName(), localIOException);
    }
  }

  protected Object[] getValues(T paramT)
  {
    Object[] arrayOfObject = new Object[this.mFieldConverters.length];
    int i = 0;
    while (i < this.mFieldConverters.length)
      try
      {
        arrayOfObject[i] = this.mFieldConverters[i].field.get(paramT);
        i++;
      }
      catch (Exception localException)
      {
        throw new RuntimeException("Cannot obtain field value: " + this.mFieldConverters[i].field, localException);
      }
    return arrayOfObject;
  }

  public T newInstance()
  {
    try
    {
      Object localObject = this.mTargetClass.newInstance();
      return localObject;
    }
    catch (Exception localException)
    {
      throw new RuntimeException("Cannot create new instance", localException);
    }
  }

  public final boolean setField(T paramT, String paramString, Object paramObject)
  {
    if (this.mFieldConverters == null)
    {
      if (this.mTargetClass == null)
        throw new UnsupportedOperationException("A JSON class must configure the automatic parser to use setField");
      initializeFieldConverters();
    }
    int i = paramString.charAt(0);
    FieldConverter[] arrayOfFieldConverter = this.mFieldConverters;
    int j = arrayOfFieldConverter.length;
    for (int k = 0; ; k++)
    {
      boolean bool = false;
      FieldConverter localFieldConverter;
      if (k < j)
      {
        localFieldConverter = arrayOfFieldConverter[k];
        if ((i != localFieldConverter.firstChar) || (!paramString.equals(localFieldConverter.key)));
      }
      else
      {
        try
        {
          localFieldConverter.field.set(paramT, paramObject);
          bool = true;
          return bool;
        }
        catch (Exception localException)
        {
          throw new RuntimeException("Cannot assign field value: " + localFieldConverter.field + " " + paramObject + " " + localException.getMessage());
        }
      }
    }
  }

  public final byte[] toByteArray(T paramT)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    try
    {
      writeToStream(localByteArrayOutputStream, paramT);
      return localByteArrayOutputStream.toByteArray();
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("Cannot generate JSON using " + getClass().getSimpleName(), localIOException);
    }
  }

  public String toPrettyString(T paramT)
  {
    StringWriter localStringWriter = new StringWriter();
    JsonWriter localJsonWriter = new JsonWriter(localStringWriter);
    localJsonWriter.setIndent(" ");
    try
    {
      write(localJsonWriter, paramT);
      localJsonWriter.flush();
      return localStringWriter.toString();
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("Cannot generate JSON using " + getClass().getSimpleName(), localIOException);
    }
  }

  public final String toString(T paramT)
  {
    StringWriter localStringWriter = new StringWriter();
    JsonWriter localJsonWriter = new JsonWriter(localStringWriter);
    try
    {
      write(localJsonWriter, paramT);
      localJsonWriter.flush();
      return localStringWriter.toString();
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("Cannot generate JSON using " + getClass().getSimpleName(), localIOException);
    }
  }

  public final void writeToStream(OutputStream paramOutputStream, T paramT)
    throws IOException
  {
    JsonWriter localJsonWriter = new JsonWriter(new BufferedWriter(new OutputStreamWriter(paramOutputStream, UTF_8), 8192));
    write(localJsonWriter, paramT);
    localJsonWriter.flush();
  }

  private static final class FieldConverter
  {
    Field field;
    char firstChar;
    int itemType;
    EsJson<?> json;
    String key;
    int type;
  }

  private static final class SimpleJson<E> extends EsJson<E>
  {
    public SimpleJson(Class<E> paramClass)
    {
      super(new Object[0]);
      buildDefaultConfiguration();
    }
  }

  public static abstract interface UnknownKeyHandler
  {
    public abstract boolean handleUnknownKey$7f90bde0()
      throws IOException;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.json.EsJson
 * JD-Core Version:    0.6.2
 */