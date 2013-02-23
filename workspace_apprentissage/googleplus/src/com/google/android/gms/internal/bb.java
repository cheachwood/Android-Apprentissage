package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.List;

public final class bb
{
  public static a d(Object paramObject)
  {
    return new a(paramObject, (byte)0);
  }

  public static final class a
  {
    private final List<String> df;
    private final Object dg;

    private a(Object paramObject)
    {
      this.dg = bj.c(paramObject);
      this.df = new ArrayList();
    }

    public final a a(String paramString, Object paramObject)
    {
      this.df.add((String)bj.c(paramString) + "=" + String.valueOf(paramObject));
      return this;
    }

    public final String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder(100).append(this.dg.getClass().getSimpleName()).append('{');
      int i = this.df.size();
      for (int j = 0; j < i; j++)
      {
        localStringBuilder.append((String)this.df.get(j));
        if (j < i - 1)
          localStringBuilder.append(", ");
      }
      return '}';
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.bb
 * JD-Core Version:    0.6.2
 */