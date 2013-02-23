package com.android.gallery3d.common;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;

public final class EntrySchema
{
  private static final String[] SQLITE_TYPES = { "TEXT", "INTEGER", "INTEGER", "INTEGER", "INTEGER", "REAL", "REAL", "NONE" };
  private final ColumnInfo[] mColumnInfo;
  private final boolean mHasFullTextIndex;
  private final String[] mProjection;
  private final String mTableName;

  public EntrySchema(Class<? extends Entry> paramClass)
  {
    ColumnInfo[] arrayOfColumnInfo = parseColumnInfo(paramClass);
    Entry.Table localTable = (Entry.Table)paramClass.getAnnotation(Entry.Table.class);
    if (localTable == null);
    String[] arrayOfString;
    boolean bool;
    for (String str = null; ; str = localTable.value())
    {
      this.mTableName = str;
      this.mColumnInfo = arrayOfColumnInfo;
      arrayOfString = new String[0];
      bool = false;
      if (arrayOfColumnInfo == null)
        break;
      arrayOfString = new String[arrayOfColumnInfo.length];
      for (int i = 0; i != arrayOfColumnInfo.length; i++)
      {
        ColumnInfo localColumnInfo = arrayOfColumnInfo[i];
        arrayOfString[i] = localColumnInfo.name;
        if (localColumnInfo.fullText)
          bool = true;
      }
    }
    this.mProjection = arrayOfString;
    this.mHasFullTextIndex = bool;
  }

  private void objectToValues(Entry paramEntry, ContentValues paramContentValues)
  {
    while (true)
    {
      int j;
      String str;
      Field localField;
      try
      {
        ColumnInfo[] arrayOfColumnInfo = this.mColumnInfo;
        int i = arrayOfColumnInfo.length;
        j = 0;
        if (j >= i)
          break label246;
        ColumnInfo localColumnInfo = arrayOfColumnInfo[j];
        str = localColumnInfo.name;
        localField = localColumnInfo.field;
        switch (localColumnInfo.type)
        {
        case 0:
          paramContentValues.put(str, (String)localField.get(paramEntry));
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        }
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new RuntimeException(localIllegalAccessException);
      }
      paramContentValues.put(str, Boolean.valueOf(localField.getBoolean(paramEntry)));
      break label247;
      paramContentValues.put(str, Short.valueOf(localField.getShort(paramEntry)));
      break label247;
      paramContentValues.put(str, Integer.valueOf(localField.getInt(paramEntry)));
      break label247;
      paramContentValues.put(str, Long.valueOf(localField.getLong(paramEntry)));
      break label247;
      paramContentValues.put(str, Float.valueOf(localField.getFloat(paramEntry)));
      break label247;
      paramContentValues.put(str, Double.valueOf(localField.getDouble(paramEntry)));
      break label247;
      paramContentValues.put(str, (byte[])localField.get(paramEntry));
      break label247;
      label246: return;
      label247: j++;
    }
  }

  private static void parseColumnInfo(Class<? extends Object> paramClass, ArrayList<ColumnInfo> paramArrayList)
  {
    Field[] arrayOfField = paramClass.getDeclaredFields();
    int i = 0;
    if (i != arrayOfField.length)
    {
      Field localField = arrayOfField[i];
      Entry.Column localColumn = (Entry.Column)localField.getAnnotation(Entry.Column.class);
      Class localClass;
      int j;
      if (localColumn != null)
      {
        localClass = localField.getType();
        if (localClass != String.class)
          break label117;
        j = 0;
      }
      while (true)
      {
        int k = paramArrayList.size();
        paramArrayList.add(new ColumnInfo(localColumn.value(), j, localColumn.indexed(), localColumn.fullText(), localColumn.allowNull(), localColumn.defaultValue(), localField, k));
        i++;
        break;
        label117: if (localClass == Boolean.TYPE)
        {
          j = 1;
        }
        else if (localClass == Short.TYPE)
        {
          j = 2;
        }
        else if (localClass == Integer.TYPE)
        {
          j = 3;
        }
        else if (localClass == Long.TYPE)
        {
          j = 4;
        }
        else if (localClass == Float.TYPE)
        {
          j = 5;
        }
        else if (localClass == Double.TYPE)
        {
          j = 6;
        }
        else
        {
          if (localClass != [B.class)
            break label216;
          j = 7;
        }
      }
      label216: throw new IllegalArgumentException("Unsupported field type for column: " + localClass.getName());
    }
  }

  private ColumnInfo[] parseColumnInfo(Class<? extends Object> paramClass)
  {
    ArrayList localArrayList = new ArrayList();
    while (paramClass != null)
    {
      parseColumnInfo(paramClass, localArrayList);
      paramClass = paramClass.getSuperclass();
    }
    ColumnInfo[] arrayOfColumnInfo = new ColumnInfo[localArrayList.size()];
    localArrayList.toArray(arrayOfColumnInfo);
    return arrayOfColumnInfo;
  }

  private static void setIfNotNull(Field paramField, Object paramObject1, Object paramObject2)
    throws IllegalAccessException
  {
    if (paramObject2 != null)
      paramField.set(paramObject1, paramObject2);
  }

  public final void createTables(SQLiteDatabase paramSQLiteDatabase)
  {
    String str1 = this.mTableName;
    if (str1 != null);
    StringBuilder localStringBuilder1;
    for (boolean bool = true; ; bool = false)
    {
      Utils.assertTrue(bool);
      localStringBuilder1 = new StringBuilder("CREATE TABLE ");
      localStringBuilder1.append(str1);
      localStringBuilder1.append(" (_id INTEGER PRIMARY KEY AUTOINCREMENT");
      for (ColumnInfo localColumnInfo5 : this.mColumnInfo)
        if (!"_id".equals(localColumnInfo5.name))
        {
          localStringBuilder1.append(',');
          localStringBuilder1.append(localColumnInfo5.name);
          localStringBuilder1.append(' ');
          localStringBuilder1.append(SQLITE_TYPES[localColumnInfo5.type]);
          if (!localColumnInfo5.allowNull)
            localStringBuilder1.append(" NOT NULL");
          if (!TextUtils.isEmpty(localColumnInfo5.defaultValue))
          {
            localStringBuilder1.append(" DEFAULT ");
            localStringBuilder1.append(localColumnInfo5.defaultValue);
          }
        }
    }
    localStringBuilder1.append(");");
    paramSQLiteDatabase.execSQL(localStringBuilder1.toString());
    localStringBuilder1.setLength(0);
    for (ColumnInfo localColumnInfo4 : this.mColumnInfo)
      if (localColumnInfo4.indexed)
      {
        localStringBuilder1.append("CREATE INDEX ");
        localStringBuilder1.append(str1);
        localStringBuilder1.append("_index_");
        localStringBuilder1.append(localColumnInfo4.name);
        localStringBuilder1.append(" ON ");
        localStringBuilder1.append(str1);
        localStringBuilder1.append(" (");
        localStringBuilder1.append(localColumnInfo4.name);
        localStringBuilder1.append(");");
        paramSQLiteDatabase.execSQL(localStringBuilder1.toString());
        localStringBuilder1.setLength(0);
      }
    if (this.mHasFullTextIndex)
    {
      String str2 = str1 + "_fulltext";
      localStringBuilder1.append("CREATE VIRTUAL TABLE ");
      localStringBuilder1.append(str2);
      localStringBuilder1.append(" USING FTS3 (_id INTEGER PRIMARY KEY");
      for (ColumnInfo localColumnInfo3 : this.mColumnInfo)
        if (localColumnInfo3.fullText)
        {
          String str4 = localColumnInfo3.name;
          localStringBuilder1.append(',');
          localStringBuilder1.append(str4);
          localStringBuilder1.append(" TEXT");
        }
      localStringBuilder1.append(");");
      paramSQLiteDatabase.execSQL(localStringBuilder1.toString());
      localStringBuilder1.setLength(0);
      StringBuilder localStringBuilder2 = new StringBuilder("INSERT OR REPLACE INTO ");
      localStringBuilder2.append(str2);
      localStringBuilder2.append(" (_id");
      for (ColumnInfo localColumnInfo2 : this.mColumnInfo)
        if (localColumnInfo2.fullText)
        {
          localStringBuilder2.append(',');
          localStringBuilder2.append(localColumnInfo2.name);
        }
      localStringBuilder2.append(") VALUES (new._id");
      for (ColumnInfo localColumnInfo1 : this.mColumnInfo)
        if (localColumnInfo1.fullText)
        {
          localStringBuilder2.append(",new.");
          localStringBuilder2.append(localColumnInfo1.name);
        }
      localStringBuilder2.append(");");
      String str3 = localStringBuilder2.toString();
      localStringBuilder1.append("CREATE TRIGGER ");
      localStringBuilder1.append(str1);
      localStringBuilder1.append("_insert_trigger AFTER INSERT ON ");
      localStringBuilder1.append(str1);
      localStringBuilder1.append(" FOR EACH ROW BEGIN ");
      localStringBuilder1.append(str3);
      localStringBuilder1.append("END;");
      paramSQLiteDatabase.execSQL(localStringBuilder1.toString());
      localStringBuilder1.setLength(0);
      localStringBuilder1.append("CREATE TRIGGER ");
      localStringBuilder1.append(str1);
      localStringBuilder1.append("_update_trigger AFTER UPDATE ON ");
      localStringBuilder1.append(str1);
      localStringBuilder1.append(" FOR EACH ROW BEGIN ");
      localStringBuilder1.append(str3);
      localStringBuilder1.append("END;");
      paramSQLiteDatabase.execSQL(localStringBuilder1.toString());
      localStringBuilder1.setLength(0);
      localStringBuilder1.append("CREATE TRIGGER ");
      localStringBuilder1.append(str1);
      localStringBuilder1.append("_delete_trigger AFTER DELETE ON ");
      localStringBuilder1.append(str1);
      localStringBuilder1.append(" FOR EACH ROW BEGIN DELETE FROM ");
      localStringBuilder1.append(str2);
      localStringBuilder1.append(" WHERE _id = old._id; END;");
      paramSQLiteDatabase.execSQL(localStringBuilder1.toString());
      localStringBuilder1.setLength(0);
    }
  }

  public final <T extends Entry> T cursorToObject(Cursor paramCursor, T paramT)
  {
    int j;
    int k;
    Field localField;
    while (true)
    {
      try
      {
        ColumnInfo[] arrayOfColumnInfo = this.mColumnInfo;
        int i = arrayOfColumnInfo.length;
        j = 0;
        if (j >= i)
          break;
        ColumnInfo localColumnInfo = arrayOfColumnInfo[j];
        k = localColumnInfo.projectionIndex;
        localField = localColumnInfo.field;
        switch (localColumnInfo.type)
        {
        case 0:
          if (paramCursor.isNull(k))
          {
            localObject2 = null;
            localField.set(paramT, localObject2);
          }
          break;
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        }
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new RuntimeException(localIllegalAccessException);
      }
      Object localObject2 = paramCursor.getString(k);
      continue;
      if (paramCursor.getShort(k) != 1)
        break label301;
    }
    label295: label301: for (boolean bool = true; ; bool = false)
    {
      localField.setBoolean(paramT, bool);
      break label295;
      localField.setShort(paramT, paramCursor.getShort(k));
      break label295;
      localField.setInt(paramT, paramCursor.getInt(k));
      break label295;
      localField.setLong(paramT, paramCursor.getLong(k));
      break label295;
      localField.setFloat(paramT, paramCursor.getFloat(k));
      break label295;
      localField.setDouble(paramT, paramCursor.getDouble(k));
      break label295;
      if (paramCursor.isNull(k));
      byte[] arrayOfByte;
      for (Object localObject1 = null; ; localObject1 = arrayOfByte)
      {
        localField.set(paramT, localObject1);
        break;
        arrayOfByte = paramCursor.getBlob(k);
      }
      return paramT;
      j++;
      break;
    }
  }

  public final boolean deleteWithId(SQLiteDatabase paramSQLiteDatabase, long paramLong)
  {
    int i = 1;
    String str = this.mTableName;
    String[] arrayOfString = new String[i];
    arrayOfString[0] = Long.toString(paramLong);
    if (paramSQLiteDatabase.delete(str, "_id=?", arrayOfString) == i);
    while (true)
    {
      return i;
      int j = 0;
    }
  }

  public final void dropTables(SQLiteDatabase paramSQLiteDatabase)
  {
    String str = this.mTableName;
    StringBuilder localStringBuilder = new StringBuilder("DROP TABLE IF EXISTS ");
    localStringBuilder.append(str);
    localStringBuilder.append(';');
    paramSQLiteDatabase.execSQL(localStringBuilder.toString());
    localStringBuilder.setLength(0);
    if (this.mHasFullTextIndex)
    {
      localStringBuilder.append("DROP TABLE IF EXISTS ");
      localStringBuilder.append(str);
      localStringBuilder.append("_fulltext");
      localStringBuilder.append(';');
      paramSQLiteDatabase.execSQL(localStringBuilder.toString());
    }
  }

  public final ColumnInfo getColumn(String paramString)
  {
    int i = getColumnIndex(paramString);
    if (i < 0);
    for (ColumnInfo localColumnInfo = null; ; localColumnInfo = this.mColumnInfo[i])
      return localColumnInfo;
  }

  public final int getColumnIndex(String paramString)
  {
    ColumnInfo[] arrayOfColumnInfo = this.mColumnInfo;
    int i = arrayOfColumnInfo.length;
    int j = 0;
    ColumnInfo localColumnInfo;
    if (j < i)
    {
      localColumnInfo = arrayOfColumnInfo[j];
      if (!localColumnInfo.name.equals(paramString));
    }
    for (int k = localColumnInfo.projectionIndex; ; k = -1)
    {
      return k;
      j++;
      break;
    }
  }

  public final String[] getProjection()
  {
    return this.mProjection;
  }

  public final String getTableName()
  {
    return this.mTableName;
  }

  public final long insertOrReplace(SQLiteDatabase paramSQLiteDatabase, Entry paramEntry)
  {
    ContentValues localContentValues = new ContentValues();
    objectToValues(paramEntry, localContentValues);
    if (paramEntry.id == 0L)
      localContentValues.remove("_id");
    long l = paramSQLiteDatabase.replace(this.mTableName, "_id", localContentValues);
    paramEntry.id = l;
    return l;
  }

  public final boolean queryWithId(SQLiteDatabase paramSQLiteDatabase, long paramLong, Entry paramEntry)
  {
    String str = this.mTableName;
    String[] arrayOfString1 = this.mProjection;
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = Long.toString(paramLong);
    Cursor localCursor = paramSQLiteDatabase.query(str, arrayOfString1, "_id=?", arrayOfString2, null, null, null);
    boolean bool1 = localCursor.moveToFirst();
    boolean bool2 = false;
    if (bool1)
    {
      cursorToObject(localCursor, paramEntry);
      bool2 = true;
    }
    localCursor.close();
    return bool2;
  }

  public final String toDebugString(Entry paramEntry, String[] paramArrayOfString)
  {
    try
    {
      StringBuilder localStringBuilder1 = new StringBuilder();
      localStringBuilder1.append("ID=").append(paramEntry.id);
      int i = paramArrayOfString.length;
      int j = 0;
      if (j < i)
      {
        String str2 = paramArrayOfString[j];
        Object localObject = getColumn(str2).field.get(paramEntry);
        StringBuilder localStringBuilder2 = localStringBuilder1.append(" ").append(str2).append("=");
        if (localObject == null);
        for (String str3 = "null"; ; str3 = localObject.toString())
        {
          localStringBuilder2.append(str3);
          j++;
          break;
        }
      }
      String str1 = localStringBuilder1.toString();
      return str1;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new RuntimeException(localIllegalAccessException);
    }
  }

  public final <T extends Entry> T valuesToObject(ContentValues paramContentValues, T paramT)
  {
    while (true)
    {
      int j;
      String str;
      Field localField;
      try
      {
        ColumnInfo[] arrayOfColumnInfo = this.mColumnInfo;
        int i = arrayOfColumnInfo.length;
        j = 0;
        if (j >= i)
          break label222;
        ColumnInfo localColumnInfo = arrayOfColumnInfo[j];
        str = localColumnInfo.name;
        localField = localColumnInfo.field;
        switch (localColumnInfo.type)
        {
        case 0:
          setIfNotNull(localField, paramT, paramContentValues.getAsString(str));
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        }
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new RuntimeException(localIllegalAccessException);
      }
      setIfNotNull(localField, paramT, paramContentValues.getAsBoolean(str));
      break label224;
      setIfNotNull(localField, paramT, paramContentValues.getAsShort(str));
      break label224;
      setIfNotNull(localField, paramT, paramContentValues.getAsInteger(str));
      break label224;
      setIfNotNull(localField, paramT, paramContentValues.getAsLong(str));
      break label224;
      setIfNotNull(localField, paramT, paramContentValues.getAsFloat(str));
      break label224;
      setIfNotNull(localField, paramT, paramContentValues.getAsDouble(str));
      break label224;
      setIfNotNull(localField, paramT, paramContentValues.getAsByteArray(str));
      break label224;
      label222: return paramT;
      label224: j++;
    }
  }

  public static final class ColumnInfo
  {
    public final boolean allowNull;
    public final String defaultValue;
    public final Field field;
    public final boolean fullText;
    public final boolean indexed;
    public final String name;
    public final int projectionIndex;
    public final int type;

    public ColumnInfo(String paramString1, int paramInt1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, String paramString2, Field paramField, int paramInt2)
    {
      this.name = paramString1.toLowerCase();
      this.type = paramInt1;
      this.indexed = paramBoolean1;
      this.fullText = paramBoolean2;
      this.allowNull = paramBoolean3;
      this.defaultValue = paramString2;
      this.field = paramField;
      this.projectionIndex = paramInt2;
      paramField.setAccessible(true);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.gallery3d.common.EntrySchema
 * JD-Core Version:    0.6.2
 */