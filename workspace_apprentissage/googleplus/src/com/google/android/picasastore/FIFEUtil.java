package com.google.android.picasastore;

import android.net.Uri;
import android.net.Uri.Builder;
import java.util.ArrayList;
import java.util.Collection;
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

  public static String getImageUrlOptions(String paramString)
  {
    Uri localUri = Uri.parse(paramString);
    ArrayList localArrayList1 = newArrayList(SPLIT_ON_SLASH.split(localUri.getPath()));
    int i = localArrayList1.size();
    if ((localArrayList1.size() > 1) && (((String)localArrayList1.get(0)).equals("image")));
    for (int j = i - 1; ; j = i)
    {
      int m;
      int n;
      label166: String str1;
      if ((j >= 4) && (j <= 6))
      {
        String str2 = localUri.getPath();
        ArrayList localArrayList3 = newArrayList(SPLIT_ON_SLASH.split(str2));
        if ((localArrayList3.size() > 0) && (((String)localArrayList3.get(0)).equals("image")))
          localArrayList3.remove(0);
        int k = localArrayList3.size();
        if ((!str2.endsWith("/")) && (k == 5))
        {
          m = 1;
          if (k != 4)
            break label198;
          n = 1;
          if ((m != 0) || (n != 0))
            break label204;
          str1 = (String)localArrayList3.get(4);
        }
      }
      while (true)
      {
        return str1;
        m = 0;
        break;
        label198: n = 0;
        break label166;
        label204: str1 = "";
        continue;
        if (j == 1)
        {
          ArrayList localArrayList2 = newArrayList(SPLIT_ON_EQUALS.split(localUri.getPath()));
          if (localArrayList2.size() > 1)
            str1 = (String)localArrayList2.get(1);
          else
            str1 = "";
        }
        else
        {
          str1 = "";
        }
      }
    }
  }

  public static boolean isFifeHostedUrl(String paramString)
  {
    if (paramString == null);
    for (boolean bool = false; ; bool = FIFE_HOSTED_IMAGE_URL_RE.matcher(paramString).find())
      return bool;
  }

  private static <E> ArrayList<E> newArrayList(Iterable<? extends E> paramIterable)
  {
    ArrayList localArrayList;
    if ((paramIterable instanceof Collection))
      localArrayList = new ArrayList((Collection)paramIterable);
    while (true)
    {
      return localArrayList;
      Iterator localIterator = paramIterable.iterator();
      localArrayList = new ArrayList();
      while (localIterator.hasNext())
        localArrayList.add(localIterator.next());
    }
  }

  public static Uri setImageUrlOptions(String paramString1, String paramString2)
  {
    int i = 1;
    Uri localUri1 = Uri.parse(paramString2);
    ArrayList localArrayList1 = newArrayList(SPLIT_ON_SLASH.split(localUri1.getPath()));
    int j = localArrayList1.size();
    if ((localArrayList1.size() > i) && (((String)localArrayList1.get(0)).equals("image")));
    for (int k = j - 1; ; k = j)
    {
      String str2;
      ArrayList localArrayList3;
      if ((k >= 4) && (k <= 6))
      {
        str2 = localUri1.getPath();
        localArrayList3 = newArrayList(SPLIT_ON_SLASH.split(str2));
        if ((localArrayList3.size() <= 0) || (!((String)localArrayList3.get(0)).equals("image")))
          break label393;
        localArrayList3.remove(0);
      }
      label393: for (int m = i; ; m = 0)
      {
        int n = localArrayList3.size();
        boolean bool = str2.endsWith("/");
        int i1;
        label178: label212: Uri localUri2;
        if ((!bool) && (n == 5))
        {
          i1 = i;
          if (n != 4)
            break label296;
          if (i1 != 0)
            localArrayList3.add(localArrayList3.get(4));
          if (i == 0)
            break label301;
          localArrayList3.add(paramString1);
          if (m != 0)
            localArrayList3.add(0, "image");
          if (bool)
            localArrayList3.add("");
          localUri2 = localUri1.buildUpon().path("/" + JOIN_ON_SLASH.appendTo(new StringBuilder(), localArrayList3).toString()).build();
        }
        while (true)
        {
          return localUri2;
          i1 = 0;
          break;
          label296: i = 0;
          break label178;
          label301: localArrayList3.set(4, paramString1);
          break label212;
          if (k == i)
          {
            ArrayList localArrayList2 = newArrayList(SPLIT_ON_EQUALS.split(localUri1.getPath()));
            String str1 = (String)localArrayList2.get(0) + "=" + paramString1;
            localUri2 = localUri1.buildUpon().path(str1).build();
          }
          else
          {
            localUri2 = localUri1;
          }
        }
      }
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
        int i = FIFEUtil.1.$SwitchMap$com$google$android$picasastore$FIFEUtil$Splitter$AbstractIterator$State[this.state.ordinal()];
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
 * Qualified Name:     com.google.android.picasastore.FIFEUtil
 * JD-Core Version:    0.6.2
 */