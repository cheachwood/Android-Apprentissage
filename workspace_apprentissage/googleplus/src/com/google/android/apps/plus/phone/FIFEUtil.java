package com.google.android.apps.plus.phone;

import android.graphics.Point;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FIFEUtil
{
  private static final Pattern FIFE_HOSTED_IMAGE_URL_RE = Pattern.compile("^((http(s)?):)?\\/\\/((((lh[3-6]\\.((ggpht)|(googleusercontent)|(google)))|(bp[0-3]\\.blogger))\\.com)|(www\\.google\\.com\\/visualsearch\\/lh))\\/");
  private static final Joiner JOIN_ON_SLASH;
  private static final Splitter SPLIT_ON_EQUALS = Splitter.on("=").omitEmptyStrings();
  private static final Splitter SPLIT_ON_SLASH = Splitter.on("/").omitEmptyStrings();

  static
  {
    JOIN_ON_SLASH = Joiner.on("/");
  }

  public static String getImageUriOptions(Uri paramUri)
  {
    ArrayList localArrayList1 = newArrayList(SPLIT_ON_SLASH.split(paramUri.getPath()));
    int i = localArrayList1.size();
    if ((localArrayList1.size() > 1) && (((String)localArrayList1.get(0)).equals("image")))
      i--;
    int k;
    int m;
    label157: String str1;
    if ((i >= 4) && (i <= 6))
    {
      String str2 = paramUri.getPath();
      ArrayList localArrayList3 = newArrayList(SPLIT_ON_SLASH.split(str2));
      if ((localArrayList3.size() > 0) && (((String)localArrayList3.get(0)).equals("image")))
        localArrayList3.remove(0);
      int j = localArrayList3.size();
      if ((!str2.endsWith("/")) && (j == 5))
      {
        k = 1;
        if (j != 4)
          break label187;
        m = 1;
        if ((k != 0) || (m != 0))
          break label193;
        str1 = (String)localArrayList3.get(4);
      }
    }
    while (true)
    {
      return str1;
      k = 0;
      break;
      label187: m = 0;
      break label157;
      label193: str1 = "";
      continue;
      if (i == 1)
      {
        ArrayList localArrayList2 = newArrayList(SPLIT_ON_EQUALS.split(paramUri.getPath()));
        if (localArrayList2.size() > 1);
        for (str1 = (String)localArrayList2.get(1); ; str1 = "")
          break;
      }
      str1 = "";
    }
  }

  public static Point getImageUrlSize(String paramString)
  {
    Point localPoint = new Point();
    if ((paramString == null) || (!isFifeHostedUrl(paramString)));
    String str1;
    do
    {
      return localPoint;
      str1 = getImageUriOptions(Uri.parse(paramString));
    }
    while (TextUtils.isEmpty(str1));
    String[] arrayOfString = str1.split("-");
    int i = 0;
    while (i < arrayOfString.length)
    {
      String str2 = arrayOfString[i];
      try
      {
        if (str2.startsWith("w"))
        {
          localPoint.x = Integer.parseInt(str2.substring(1));
        }
        else if (str2.startsWith("h"))
        {
          localPoint.y = Integer.parseInt(str2.substring(1));
        }
        else if (str2.startsWith("s"))
        {
          int j = Integer.parseInt(str2.substring(1));
          localPoint.y = j;
          localPoint.x = j;
        }
        label144: i++;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        break label144;
      }
    }
  }

  public static boolean isFifeHostedUrl(String paramString)
  {
    if (paramString == null);
    for (boolean bool = false; ; bool = FIFE_HOSTED_IMAGE_URL_RE.matcher(paramString).find())
      return bool;
  }

  private static String makeUriString(Uri paramUri)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    String str1 = paramUri.getScheme();
    if (str1 != null)
      localStringBuilder.append(str1).append(':');
    String str2 = paramUri.getEncodedAuthority();
    if (str2 != null)
      localStringBuilder.append("//").append(str2);
    String str3 = Uri.encode(paramUri.getPath(), "/=");
    if (str3 != null)
      localStringBuilder.append(str3);
    String str4 = paramUri.getEncodedQuery();
    if (!TextUtils.isEmpty(str4))
      localStringBuilder.append('?').append(str4);
    String str5 = paramUri.getEncodedFragment();
    if (!TextUtils.isEmpty(str5))
      localStringBuilder.append('#').append(str5);
    return localStringBuilder.toString();
  }

  private static <E> ArrayList<E> newArrayList(Iterable<? extends E> paramIterable)
  {
    Iterator localIterator = paramIterable.iterator();
    ArrayList localArrayList = new ArrayList();
    while (localIterator.hasNext())
      localArrayList.add(localIterator.next());
    return localArrayList;
  }

  public static Uri setImageUriOptions(String paramString, Uri paramUri)
  {
    int i = 1;
    ArrayList localArrayList1 = newArrayList(SPLIT_ON_SLASH.split(paramUri.getPath()));
    int j = localArrayList1.size();
    if ((localArrayList1.size() > i) && (((String)localArrayList1.get(0)).equals("image")))
      j--;
    String str2;
    ArrayList localArrayList3;
    if ((j >= 4) && (j <= 6))
    {
      str2 = paramUri.getPath();
      localArrayList3 = newArrayList(SPLIT_ON_SLASH.split(str2));
      if ((localArrayList3.size() <= 0) || (!((String)localArrayList3.get(0)).equals("image")))
        break label381;
      localArrayList3.remove(0);
    }
    label166: label200: label381: for (int k = i; ; k = 0)
    {
      int m = localArrayList3.size();
      boolean bool = str2.endsWith("/");
      int n;
      Uri localUri;
      if ((!bool) && (m == 5))
      {
        n = i;
        if (m != 4)
          break label284;
        if (n != 0)
          localArrayList3.add(localArrayList3.get(4));
        if (i == 0)
          break label289;
        localArrayList3.add(paramString);
        if (k != 0)
          localArrayList3.add(0, "image");
        if (bool)
          localArrayList3.add("");
        localUri = paramUri.buildUpon().path("/" + JOIN_ON_SLASH.appendTo(new StringBuilder(), localArrayList3).toString()).build();
      }
      while (true)
      {
        return localUri;
        n = 0;
        break;
        i = 0;
        break label166;
        localArrayList3.set(4, paramString);
        break label200;
        if (j == i)
        {
          ArrayList localArrayList2 = newArrayList(SPLIT_ON_EQUALS.split(paramUri.getPath()));
          String str1 = (String)localArrayList2.get(0) + "=" + paramString;
          localUri = paramUri.buildUpon().path(str1).build();
        }
        else
        {
          localUri = paramUri;
        }
      }
    }
  }

  public static Uri setImageUrlOptions(String paramString1, String paramString2)
  {
    return setImageUriOptions(paramString1, Uri.parse(paramString2));
  }

  public static String setImageUrlSize(int paramInt1, int paramInt2, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((paramString == null) || (!isFifeHostedUrl(paramString)));
    StringBuffer localStringBuffer;
    for (String str = paramString; ; str = makeUriString(setImageUrlOptions(localStringBuffer.toString(), paramString)))
    {
      return str;
      localStringBuffer = new StringBuffer();
      localStringBuffer.append("w").append(paramInt1);
      localStringBuffer.append("-h").append(paramInt2);
      localStringBuffer.append("-d-no");
    }
  }

  public static String setImageUrlSize(int paramInt, String paramString, boolean paramBoolean)
  {
    if ((paramString == null) || (!isFifeHostedUrl(paramString)));
    while (true)
    {
      return paramString;
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append("s").append(paramInt);
      localStringBuffer.append("-d-no");
      if (paramBoolean)
        localStringBuffer.append("-c");
      paramString = makeUriString(setImageUrlOptions(localStringBuffer.toString(), paramString));
    }
  }

  private static final class Joiner
  {
    private final String separator;

    private Joiner(String paramString)
    {
      this.separator = paramString;
    }

    public static Joiner on(String paramString)
    {
      return new Joiner(paramString);
    }

    private static CharSequence toString(Object paramObject)
    {
      if ((paramObject instanceof CharSequence));
      for (Object localObject = (CharSequence)paramObject; ; localObject = paramObject.toString())
        return localObject;
    }

    public final StringBuilder appendTo(StringBuilder paramStringBuilder, Iterable<?> paramIterable)
    {
      Iterator localIterator = paramIterable.iterator();
      if (localIterator.hasNext())
      {
        paramStringBuilder.append(toString(localIterator.next()));
        while (localIterator.hasNext())
        {
          paramStringBuilder.append(this.separator);
          paramStringBuilder.append(toString(localIterator.next()));
        }
      }
      return paramStringBuilder;
    }
  }

  static final class Splitter
  {
    private final boolean omitEmptyStrings;
    private final Strategy strategy;

    private Splitter(Strategy paramStrategy)
    {
      this(paramStrategy, false);
    }

    private Splitter(Strategy paramStrategy, boolean paramBoolean)
    {
      this.strategy = paramStrategy;
      this.omitEmptyStrings = paramBoolean;
    }

    public static Splitter on(String paramString)
    {
      if ((paramString == null) || (paramString.length() == 0))
        throw new IllegalArgumentException("separator may not be empty or null");
      return new Splitter(new Strategy()
      {
      });
    }

    public final Splitter omitEmptyStrings()
    {
      return new Splitter(this.strategy, true);
    }

    public final Iterable<String> split(final CharSequence paramCharSequence)
    {
      return new Iterable()
      {
        public final Iterator<String> iterator()
        {
          return FIFEUtil.Splitter.this.strategy.iterator(FIFEUtil.Splitter.this, paramCharSequence);
        }
      };
    }

    private static abstract class AbstractIterator<T>
      implements Iterator<T>
    {
      T next;
      State state = State.NOT_READY;

      protected abstract T computeNext();

      public final boolean hasNext()
      {
        if (this.state == State.FAILED)
          throw new IllegalStateException();
        int i = FIFEUtil.1.$SwitchMap$com$google$android$apps$plus$phone$FIFEUtil$Splitter$AbstractIterator$State[this.state.ordinal()];
        boolean bool = false;
        switch (i)
        {
        default:
          this.state = State.FAILED;
          this.next = computeNext();
          State localState1 = this.state;
          State localState2 = State.DONE;
          bool = false;
          if (localState1 != localState2)
            this.state = State.READY;
          break;
        case 1:
        case 2:
        }
        for (bool = true; ; bool = true)
          return bool;
      }

      public final T next()
      {
        if (!hasNext())
          throw new NoSuchElementException();
        this.state = State.NOT_READY;
        return this.next;
      }

      public void remove()
      {
        throw new UnsupportedOperationException();
      }

      static enum State
      {
        static
        {
          NOT_READY = new State("NOT_READY", 1);
          DONE = new State("DONE", 2);
          FAILED = new State("FAILED", 3);
          State[] arrayOfState = new State[4];
          arrayOfState[0] = READY;
          arrayOfState[1] = NOT_READY;
          arrayOfState[2] = DONE;
          arrayOfState[3] = FAILED;
        }
      }
    }

    private static abstract class SplittingIterator extends FIFEUtil.Splitter.AbstractIterator<String>
    {
      int offset = 0;
      final boolean omitEmptyStrings;
      final CharSequence toSplit;

      protected SplittingIterator(FIFEUtil.Splitter paramSplitter, CharSequence paramCharSequence)
      {
        super();
        this.omitEmptyStrings = paramSplitter.omitEmptyStrings;
        this.toSplit = paramCharSequence;
      }

      abstract int separatorEnd(int paramInt);

      abstract int separatorStart(int paramInt);
    }

    private static abstract interface Strategy
    {
      public abstract Iterator<String> iterator(FIFEUtil.Splitter paramSplitter, CharSequence paramCharSequence);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.FIFEUtil
 * JD-Core Version:    0.6.2
 */