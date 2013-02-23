package com.google.android.apps.plus.json;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public final class JsonReader
  implements Closeable
{
  private final char[] buffer = new char[1024];
  private final Reader in;
  private boolean lenient = false;
  private int limit = 0;
  private String name;
  private int pos = 0;
  private boolean skipping;
  private final List<JsonScope> stack = new ArrayList();
  private JsonToken token;
  private String value;
  private int valueLength;
  private int valuePos;

  public JsonReader(Reader paramReader)
  {
    push(JsonScope.EMPTY_DOCUMENT);
    this.skipping = false;
    if (paramReader == null)
      throw new NullPointerException("in == null");
    this.in = paramReader;
  }

  private JsonToken advance()
    throws IOException
  {
    peek();
    JsonToken localJsonToken = this.token;
    this.token = null;
    this.value = null;
    this.name = null;
    return localJsonToken;
  }

  private static JsonToken decodeNumber(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    int i = paramInt1;
    int j = paramArrayOfChar[paramInt1];
    if (j == 45)
    {
      i++;
      j = paramArrayOfChar[i];
    }
    int k;
    int m;
    if (j == 48)
    {
      k = i + 1;
      m = paramArrayOfChar[k];
    }
    JsonToken localJsonToken;
    while (m == 46)
    {
      k++;
      m = paramArrayOfChar[k];
      while (true)
        if ((m >= 48) && (m <= 57))
        {
          k++;
          m = paramArrayOfChar[k];
          continue;
          if ((j >= 49) && (j <= 57))
          {
            k = i + 1;
            for (m = paramArrayOfChar[k]; (m >= 48) && (m <= 57); m = paramArrayOfChar[k])
              k++;
            break;
          }
          localJsonToken = JsonToken.STRING;
        }
    }
    while (true)
    {
      return localJsonToken;
      if ((m == 101) || (m == 69))
      {
        int n = k + 1;
        int i1 = paramArrayOfChar[n];
        if ((i1 == 43) || (i1 == 45))
        {
          n++;
          i1 = paramArrayOfChar[n];
        }
        int i2;
        if ((i1 >= 48) && (i1 <= 57))
        {
          k = n + 1;
          i2 = paramArrayOfChar[k];
        }
        while (true)
          if ((i2 >= 48) && (i2 <= 57))
          {
            k++;
            i2 = paramArrayOfChar[k];
            continue;
            localJsonToken = JsonToken.STRING;
            break;
          }
      }
      if (k == paramInt1 + paramInt2)
        localJsonToken = JsonToken.NUMBER;
      else
        localJsonToken = JsonToken.STRING;
    }
  }

  private void expect(JsonToken paramJsonToken)
    throws IOException
  {
    peek();
    if (this.token != paramJsonToken)
      throw new IllegalStateException("Expected " + paramJsonToken + " but was " + peek());
    advance();
  }

  private boolean fillBuffer(int paramInt)
    throws IOException
  {
    if (this.limit != this.pos)
    {
      this.limit -= this.pos;
      System.arraycopy(this.buffer, this.pos, this.buffer, 0, this.limit);
    }
    while (true)
    {
      this.pos = 0;
      do
      {
        int i = this.in.read(this.buffer, this.limit, this.buffer.length - this.limit);
        bool = false;
        if (i == -1)
          break;
        this.limit = (i + this.limit);
      }
      while (this.limit < paramInt);
      boolean bool = true;
      return bool;
      this.limit = 0;
    }
  }

  private CharSequence getSnippet()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = Math.min(this.pos, 20);
    localStringBuilder.append(this.buffer, this.pos - i, i);
    int j = Math.min(this.limit - this.pos, 20);
    localStringBuilder.append(this.buffer, this.pos, j);
    return localStringBuilder;
  }

  private JsonToken nextInArray(boolean paramBoolean)
    throws IOException
  {
    if (paramBoolean)
      replaceTop(JsonScope.NONEMPTY_ARRAY);
    JsonToken localJsonToken;
    switch (nextNonWhitespace())
    {
    default:
      this.pos = (-1 + this.pos);
      localJsonToken = nextValue();
    case 93:
      while (true)
      {
        return localJsonToken;
        switch (nextNonWhitespace())
        {
        case 44:
        default:
          throw syntaxError("Unterminated array");
        case 93:
          pop();
          localJsonToken = JsonToken.END_ARRAY;
          this.token = localJsonToken;
          break;
        case 59:
          throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
          if (!paramBoolean)
            break label156;
          pop();
          localJsonToken = JsonToken.END_ARRAY;
          this.token = localJsonToken;
        }
      }
    case 44:
    case 59:
    }
    label156: throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
  }

  private JsonToken nextInObject(boolean paramBoolean)
    throws IOException
  {
    int i;
    JsonToken localJsonToken;
    if (paramBoolean)
    {
      switch (nextNonWhitespace())
      {
      default:
        this.pos = (-1 + this.pos);
        i = nextNonWhitespace();
        switch (i)
        {
        default:
          throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        case 39:
        case 34:
        }
        break;
      case 125:
      }
      pop();
      localJsonToken = JsonToken.END_OBJECT;
      this.token = localJsonToken;
    }
    while (true)
    {
      return localJsonToken;
      switch (nextNonWhitespace())
      {
      case 44:
      case 59:
      default:
        throw syntaxError("Unterminated object");
      case 125:
      }
      pop();
      localJsonToken = JsonToken.END_OBJECT;
      this.token = localJsonToken;
      continue;
      throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
      this.name = nextString((char)i);
      replaceTop(JsonScope.DANGLING_NAME);
      localJsonToken = JsonToken.NAME;
      this.token = localJsonToken;
    }
  }

  private String nextLiteral(boolean paramBoolean)
    throws IOException
  {
    StringBuilder localStringBuilder = null;
    this.valuePos = -1;
    this.valueLength = 0;
    int i = 0;
    do
    {
      while (i + this.pos < this.limit)
        switch (this.buffer[(i + this.pos)])
        {
        default:
          i++;
          break;
        case '#':
        case '/':
        case ';':
        case '=':
        case '\\':
          throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        case '\t':
        case '\n':
        case '\f':
        case '\r':
        case ' ':
        case ',':
        case ':':
        case '[':
        case ']':
        case '{':
        case '}':
        }
      if (i >= this.buffer.length)
        break;
    }
    while (fillBuffer(i + 1));
    this.buffer[this.limit] = '\000';
    label218: String str;
    if ((paramBoolean) && (localStringBuilder == null))
    {
      this.valuePos = this.pos;
      str = null;
    }
    while (true)
    {
      this.valueLength = (i + this.valueLength);
      this.pos = (i + this.pos);
      return str;
      if (localStringBuilder == null)
        localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.buffer, this.pos, i);
      this.valueLength = (i + this.valueLength);
      this.pos = (i + this.pos);
      boolean bool = fillBuffer(1);
      i = 0;
      if (bool)
        break;
      i = 0;
      break label218;
      if (this.skipping)
      {
        str = "skipped!";
      }
      else if (localStringBuilder == null)
      {
        str = new String(this.buffer, this.pos, i);
      }
      else
      {
        localStringBuilder.append(this.buffer, this.pos, i);
        str = localStringBuilder.toString();
      }
    }
  }

  private int nextNonWhitespace()
    throws IOException
  {
    if ((this.pos < this.limit) || (fillBuffer(1)))
    {
      char[] arrayOfChar = this.buffer;
      int i = this.pos;
      this.pos = (i + 1);
      int j = arrayOfChar[i];
      switch (j)
      {
      case 9:
      case 10:
      case 13:
      case 32:
      default:
      case 47:
        do
          return j;
        while ((this.pos == this.limit) && (!fillBuffer(1)));
        throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
      case 35:
      }
      throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
    }
    throw new EOFException("End of input");
  }

  private String nextString(char paramChar)
    throws IOException
  {
    StringBuilder localStringBuilder = null;
    do
    {
      int i = this.pos;
      while (this.pos < this.limit)
      {
        char[] arrayOfChar1 = this.buffer;
        int j = this.pos;
        this.pos = (j + 1);
        char c1 = arrayOfChar1[j];
        if (c1 == paramChar)
        {
          String str2;
          if (this.skipping)
            str2 = "skipped!";
          while (true)
          {
            return str2;
            if (localStringBuilder == null)
            {
              str2 = new String(this.buffer, i, -1 + (this.pos - i));
            }
            else
            {
              localStringBuilder.append(this.buffer, i, -1 + (this.pos - i));
              str2 = localStringBuilder.toString();
            }
          }
        }
        if (c1 == '\\')
        {
          if (localStringBuilder == null)
            localStringBuilder = new StringBuilder();
          localStringBuilder.append(this.buffer, i, -1 + (this.pos - i));
          if ((this.pos == this.limit) && (!fillBuffer(1)))
            throw syntaxError("Unterminated escape sequence");
          char[] arrayOfChar2 = this.buffer;
          int k = this.pos;
          this.pos = (k + 1);
          char c2 = arrayOfChar2[k];
          switch (c2)
          {
          default:
          case 'u':
          case 't':
          case 'b':
          case 'n':
          case 'r':
          case 'f':
          }
          while (true)
          {
            localStringBuilder.append(c2);
            i = this.pos;
            break;
            if ((4 + this.pos > this.limit) && (!fillBuffer(4)))
              throw syntaxError("Unterminated escape sequence");
            String str1 = new String(this.buffer, this.pos, 4);
            this.pos = (4 + this.pos);
            c2 = (char)Integer.parseInt(str1, 16);
            continue;
            c2 = '\t';
            continue;
            c2 = '\b';
            continue;
            c2 = '\n';
            continue;
            c2 = '\r';
            continue;
            c2 = '\f';
          }
        }
      }
      if (localStringBuilder == null)
        localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.buffer, i, this.pos - i);
    }
    while (fillBuffer(1));
    throw syntaxError("Unterminated string");
  }

  private JsonToken nextValue()
    throws IOException
  {
    int i = nextNonWhitespace();
    JsonToken localJsonToken1;
    switch (i)
    {
    default:
      this.pos = (-1 + this.pos);
      this.value = nextLiteral(true);
      if (this.valueLength == 0)
        throw syntaxError("Expected literal value");
      break;
    case 123:
      push(JsonScope.EMPTY_OBJECT);
      localJsonToken1 = JsonToken.BEGIN_OBJECT;
      this.token = localJsonToken1;
    case 91:
    case 39:
    case 34:
    }
    while (true)
    {
      return localJsonToken1;
      push(JsonScope.EMPTY_ARRAY);
      localJsonToken1 = JsonToken.BEGIN_ARRAY;
      this.token = localJsonToken1;
      continue;
      throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
      this.value = nextString((char)i);
      localJsonToken1 = JsonToken.STRING;
      this.token = localJsonToken1;
      continue;
      JsonToken localJsonToken2;
      if (this.valuePos == -1)
        localJsonToken2 = JsonToken.STRING;
      while (true)
      {
        this.token = localJsonToken2;
        if (this.token != JsonToken.STRING)
          break;
        throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        if ((this.valueLength == 4) && (('n' == this.buffer[this.valuePos]) || ('N' == this.buffer[this.valuePos])) && (('u' == this.buffer[(1 + this.valuePos)]) || ('U' == this.buffer[(1 + this.valuePos)])) && (('l' == this.buffer[(2 + this.valuePos)]) || ('L' == this.buffer[(2 + this.valuePos)])) && (('l' == this.buffer[(3 + this.valuePos)]) || ('L' == this.buffer[(3 + this.valuePos)])))
        {
          this.value = "null";
          localJsonToken2 = JsonToken.NULL;
        }
        else if ((this.valueLength == 4) && (('t' == this.buffer[this.valuePos]) || ('T' == this.buffer[this.valuePos])) && (('r' == this.buffer[(1 + this.valuePos)]) || ('R' == this.buffer[(1 + this.valuePos)])) && (('u' == this.buffer[(2 + this.valuePos)]) || ('U' == this.buffer[(2 + this.valuePos)])) && (('e' == this.buffer[(3 + this.valuePos)]) || ('E' == this.buffer[(3 + this.valuePos)])))
        {
          this.value = "true";
          localJsonToken2 = JsonToken.BOOLEAN;
        }
        else if ((this.valueLength == 5) && (('f' == this.buffer[this.valuePos]) || ('F' == this.buffer[this.valuePos])) && (('a' == this.buffer[(1 + this.valuePos)]) || ('A' == this.buffer[(1 + this.valuePos)])) && (('l' == this.buffer[(2 + this.valuePos)]) || ('L' == this.buffer[(2 + this.valuePos)])) && (('s' == this.buffer[(3 + this.valuePos)]) || ('S' == this.buffer[(3 + this.valuePos)])) && (('e' == this.buffer[(4 + this.valuePos)]) || ('E' == this.buffer[(4 + this.valuePos)])))
        {
          this.value = "false";
          localJsonToken2 = JsonToken.BOOLEAN;
        }
        else
        {
          this.value = new String(this.buffer, this.valuePos, this.valueLength);
          localJsonToken2 = decodeNumber(this.buffer, this.valuePos, this.valueLength);
        }
      }
      localJsonToken1 = this.token;
    }
  }

  private JsonScope pop()
  {
    return (JsonScope)this.stack.remove(-1 + this.stack.size());
  }

  private void push(JsonScope paramJsonScope)
  {
    this.stack.add(paramJsonScope);
  }

  private void replaceTop(JsonScope paramJsonScope)
  {
    this.stack.set(-1 + this.stack.size(), paramJsonScope);
  }

  private IOException syntaxError(String paramString)
    throws IOException
  {
    throw new MalformedJsonException(paramString + " near " + getSnippet());
  }

  public final void beginArray()
    throws IOException
  {
    expect(JsonToken.BEGIN_ARRAY);
  }

  public final void beginObject()
    throws IOException
  {
    expect(JsonToken.BEGIN_OBJECT);
  }

  public final void close()
    throws IOException
  {
    this.value = null;
    this.token = null;
    this.stack.clear();
    this.stack.add(JsonScope.CLOSED);
    this.in.close();
  }

  public final void endArray()
    throws IOException
  {
    expect(JsonToken.END_ARRAY);
  }

  public final void endObject()
    throws IOException
  {
    expect(JsonToken.END_OBJECT);
  }

  public final boolean hasNext()
    throws IOException
  {
    peek();
    if ((this.token != JsonToken.END_OBJECT) && (this.token != JsonToken.END_ARRAY));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public final boolean nextBoolean()
    throws IOException
  {
    peek();
    if (this.token != JsonToken.BOOLEAN)
      throw new IllegalStateException("Expected a boolean but was " + this.token);
    if (this.value == "true");
    for (boolean bool = true; ; bool = false)
    {
      advance();
      return bool;
    }
  }

  public final double nextDouble()
    throws IOException
  {
    peek();
    if ((this.token != JsonToken.STRING) && (this.token != JsonToken.NUMBER))
      throw new IllegalStateException("Expected a double but was " + this.token);
    double d = Double.parseDouble(this.value);
    advance();
    return d;
  }

  public final int nextInt()
    throws IOException
  {
    peek();
    if ((this.token != JsonToken.STRING) && (this.token != JsonToken.NUMBER))
      throw new IllegalStateException("Expected an int but was " + this.token);
    try
    {
      int j = Integer.parseInt(this.value);
      i = j;
      advance();
      return i;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      int i;
      double d;
      do
      {
        d = Double.parseDouble(this.value);
        i = (int)d;
      }
      while (i == d);
    }
    throw new NumberFormatException(this.value);
  }

  public final long nextLong()
    throws IOException
  {
    peek();
    if ((this.token != JsonToken.STRING) && (this.token != JsonToken.NUMBER))
      throw new IllegalStateException("Expected a long but was " + this.token);
    try
    {
      long l2 = Long.parseLong(this.value);
      l1 = l2;
      advance();
      return l1;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      long l1;
      double d;
      do
      {
        d = Double.parseDouble(this.value);
        l1 = ()d;
      }
      while (l1 == d);
    }
    throw new NumberFormatException(this.value);
  }

  public final String nextName()
    throws IOException
  {
    peek();
    if (this.token != JsonToken.NAME)
      throw new IllegalStateException("Expected a name but was " + peek());
    String str = this.name;
    advance();
    return str;
  }

  public final String nextString()
    throws IOException
  {
    peek();
    if ((this.token != JsonToken.STRING) && (this.token != JsonToken.NUMBER) && (this.token != JsonToken.NULL))
      throw new IllegalStateException("Expected a string but was " + peek());
    String str = this.value;
    advance();
    return str;
  }

  public final JsonToken peek()
    throws IOException
  {
    JsonToken localJsonToken;
    if (this.token != null)
      localJsonToken = this.token;
    while (true)
    {
      return localJsonToken;
      switch (1.$SwitchMap$com$google$android$apps$plus$json$JsonScope[((JsonScope)this.stack.get(-1 + this.stack.size())).ordinal()])
      {
      default:
        throw new AssertionError();
      case 1:
        replaceTop(JsonScope.NONEMPTY_DOCUMENT);
        localJsonToken = nextValue();
        if ((this.token != JsonToken.BEGIN_ARRAY) && (this.token != JsonToken.BEGIN_OBJECT))
          throw new IOException("Expected JSON document to start with '[' or '{' but was " + this.token);
        break;
      case 2:
        localJsonToken = nextInArray(true);
        break;
      case 3:
        localJsonToken = nextInArray(false);
        break;
      case 4:
        localJsonToken = nextInObject(true);
        break;
      case 5:
        switch (nextNonWhitespace())
        {
        case 59:
        case 60:
        default:
          throw syntaxError("Expected ':'");
        case 61:
          throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        case 58:
        }
        replaceTop(JsonScope.NONEMPTY_OBJECT);
        localJsonToken = nextValue();
        break;
      case 6:
        localJsonToken = nextInObject(false);
        break;
      case 7:
        try
        {
          nextValue();
          throw syntaxError("Expected EOF");
        }
        catch (EOFException localEOFException)
        {
          localJsonToken = JsonToken.END_DOCUMENT;
          this.token = localJsonToken;
        }
      case 8:
      }
    }
    throw new IllegalStateException("JsonReader is closed");
  }

  public final void skipValue()
    throws IOException
  {
    this.skipping = true;
    int i = 0;
    try
    {
      JsonToken localJsonToken1 = advance();
      if (localJsonToken1 != JsonToken.BEGIN_ARRAY)
      {
        JsonToken localJsonToken2 = JsonToken.BEGIN_OBJECT;
        if (localJsonToken1 != localJsonToken2);
      }
      else
      {
        i++;
      }
      while (i == 0)
      {
        return;
        if (localJsonToken1 != JsonToken.END_ARRAY)
        {
          JsonToken localJsonToken3 = JsonToken.END_OBJECT;
          if (localJsonToken1 != localJsonToken3);
        }
        else
        {
          i--;
        }
      }
    }
    finally
    {
      this.skipping = false;
    }
  }

  public final String toString()
  {
    return getClass().getSimpleName() + " near " + getSnippet();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.json.JsonReader
 * JD-Core Version:    0.6.2
 */