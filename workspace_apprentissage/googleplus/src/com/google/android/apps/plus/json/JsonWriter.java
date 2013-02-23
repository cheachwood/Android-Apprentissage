package com.google.android.apps.plus.json;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public final class JsonWriter
  implements Closeable
{
  private String indent;
  private final Writer out;
  private String separator;
  private final List<JsonScope> stack = new ArrayList();

  public JsonWriter(Writer paramWriter)
  {
    this.stack.add(JsonScope.EMPTY_DOCUMENT);
    this.separator = ":";
    if (paramWriter == null)
      throw new NullPointerException("out == null");
    this.out = paramWriter;
  }

  private void beforeValue(boolean paramBoolean)
    throws IOException
  {
    switch (1.$SwitchMap$com$google$android$apps$plus$json$JsonScope[peek().ordinal()])
    {
    default:
      throw new IllegalStateException("Nesting problem: " + this.stack);
    case 1:
      if (!paramBoolean)
        throw new IllegalStateException("JSON must start with an array or an object.");
      replaceTop(JsonScope.NONEMPTY_DOCUMENT);
    case 2:
    case 3:
    case 4:
      while (true)
      {
        return;
        replaceTop(JsonScope.NONEMPTY_ARRAY);
        newline();
        continue;
        this.out.append(',');
        newline();
        continue;
        this.out.append(this.separator);
        replaceTop(JsonScope.NONEMPTY_OBJECT);
      }
    case 5:
    }
    throw new IllegalStateException("JSON must have only one top-level value.");
  }

  private JsonWriter close(JsonScope paramJsonScope1, JsonScope paramJsonScope2, String paramString)
    throws IOException
  {
    JsonScope localJsonScope = peek();
    if ((localJsonScope != paramJsonScope2) && (localJsonScope != paramJsonScope1))
      throw new IllegalStateException("Nesting problem: " + this.stack);
    this.stack.remove(-1 + this.stack.size());
    if (localJsonScope == paramJsonScope2)
      newline();
    this.out.write(paramString);
    return this;
  }

  private void newline()
    throws IOException
  {
    if (this.indent == null);
    while (true)
    {
      return;
      this.out.write("\n");
      for (int i = 1; i < this.stack.size(); i++)
        this.out.write(this.indent);
    }
  }

  private JsonWriter nullValue()
    throws IOException
  {
    beforeValue(false);
    this.out.write("null");
    return this;
  }

  private JsonWriter open(JsonScope paramJsonScope, String paramString)
    throws IOException
  {
    beforeValue(true);
    this.stack.add(paramJsonScope);
    this.out.write(paramString);
    return this;
  }

  private JsonScope peek()
  {
    return (JsonScope)this.stack.get(-1 + this.stack.size());
  }

  private void replaceTop(JsonScope paramJsonScope)
  {
    this.stack.set(-1 + this.stack.size(), paramJsonScope);
  }

  private void string(String paramString)
    throws IOException
  {
    this.out.write("\"");
    int i = paramString.length();
    int j = 0;
    if (j < i)
    {
      char c = paramString.charAt(j);
      int k;
      switch (c)
      {
      default:
        if (c <= '\037')
        {
          Writer localWriter2 = this.out;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Integer.valueOf(c);
          localWriter2.write(String.format("\\u%04x", arrayOfObject));
          k = j;
        }
        break;
      case '"':
      case '\\':
      case '\t':
      case '\b':
      case '\n':
      case '\r':
      case '\f':
      }
      while (true)
      {
        j = k + 1;
        break;
        this.out.write(92);
        do
        {
          this.out.write(c);
          k = j;
          break;
          this.out.write("\\t");
          k = j;
          break;
          this.out.write("\\b");
          k = j;
          break;
          this.out.write("\\n");
          k = j;
          break;
          this.out.write("\\r");
          k = j;
          break;
          this.out.write("\\f");
          k = j;
          break;
        }
        while (!Character.isHighSurrogate(c));
        Writer localWriter1 = this.out;
        k = j + 1;
        localWriter1.write(paramString, j, 2);
      }
    }
    this.out.write("\"");
  }

  public final JsonWriter beginArray()
    throws IOException
  {
    return open(JsonScope.EMPTY_ARRAY, "[");
  }

  public final JsonWriter beginObject()
    throws IOException
  {
    return open(JsonScope.EMPTY_OBJECT, "{");
  }

  public final void close()
    throws IOException
  {
    this.out.close();
    if (peek() != JsonScope.NONEMPTY_DOCUMENT)
      throw new IOException("Incomplete document");
  }

  public final JsonWriter endArray()
    throws IOException
  {
    return close(JsonScope.EMPTY_ARRAY, JsonScope.NONEMPTY_ARRAY, "]");
  }

  public final JsonWriter endObject()
    throws IOException
  {
    return close(JsonScope.EMPTY_OBJECT, JsonScope.NONEMPTY_OBJECT, "}");
  }

  public final void flush()
    throws IOException
  {
    this.out.flush();
  }

  public final JsonWriter name(String paramString)
    throws IOException
  {
    if (paramString == null)
      throw new NullPointerException("name == null");
    JsonScope localJsonScope = peek();
    if (localJsonScope == JsonScope.NONEMPTY_OBJECT)
      this.out.write(44);
    while (localJsonScope == JsonScope.EMPTY_OBJECT)
    {
      newline();
      replaceTop(JsonScope.DANGLING_NAME);
      string(paramString);
      return this;
    }
    throw new IllegalStateException("Nesting problem: " + this.stack);
  }

  public final void setIndent(String paramString)
  {
    if (paramString.length() == 0)
      this.indent = null;
    for (this.separator = ":"; ; this.separator = ": ")
    {
      return;
      this.indent = paramString;
    }
  }

  public final JsonWriter value(Number paramNumber)
    throws IOException
  {
    if (paramNumber == null)
      this = nullValue();
    while (true)
    {
      return this;
      String str = paramNumber.toString();
      if ((str.equals("-Infinity")) || (str.equals("Infinity")) || (str.equals("NaN")))
        throw new IllegalArgumentException("Numeric values must be finite, but was " + paramNumber);
      beforeValue(false);
      this.out.append(str);
    }
  }

  public final JsonWriter value(String paramString)
    throws IOException
  {
    if (paramString == null)
      this = nullValue();
    while (true)
    {
      return this;
      beforeValue(false);
      string(paramString);
    }
  }

  public final JsonWriter value(boolean paramBoolean)
    throws IOException
  {
    beforeValue(false);
    Writer localWriter = this.out;
    if (paramBoolean);
    for (String str = "true"; ; str = "false")
    {
      localWriter.write(str);
      return this;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.json.JsonWriter
 * JD-Core Version:    0.6.2
 */