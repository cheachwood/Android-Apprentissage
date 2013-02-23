package com.google.android.apps.plus.api;

import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.URLSpan;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.CircleData;
import com.google.android.apps.plus.views.MentionSpan;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ApiUtils
{
  private static final String[] CIRCLE_ID_PREFIXES = { "f.", "g." };
  public static final int EMAIL_MENTION_PREFIX_LENGTH = 2;
  public static final int ID_MENTION_PREFIX_LENGTH = 2;

  public static String buildPostableString$6d7f0b14(Spannable paramSpannable)
  {
    URLSpan[] arrayOfURLSpan = (URLSpan[])paramSpannable.getSpans(0, paramSpannable.length(), URLSpan.class);
    ArrayList localArrayList1 = new ArrayList();
    int i = 0;
    if (i < arrayOfURLSpan.length)
    {
      int n = localArrayList1.size();
      label83: label119: for (int i1 = 0; ; i1++)
      {
        if (i1 < n + 1)
        {
          if (!MentionSpan.isMention(arrayOfURLSpan[i]))
            continue;
          if (n > i1)
            break label83;
          localArrayList1.add(arrayOfURLSpan[i]);
        }
        while (true)
        {
          i++;
          break;
          if (paramSpannable.getSpanEnd(localArrayList1.get(i1)) <= paramSpannable.getSpanEnd(arrayOfURLSpan[i]))
            break label119;
          localArrayList1.add(i1, arrayOfURLSpan[i]);
        }
      }
    }
    int j = localArrayList1.size();
    ArrayList localArrayList2 = new ArrayList(j);
    int[] arrayOfInt1 = new int[j];
    int[] arrayOfInt2 = new int[j];
    int k = 0;
    if (k < j)
    {
      String str = ((URLSpan)localArrayList1.get(k)).getURL().substring(1);
      if (str.startsWith("g:"))
        localArrayList2.add("+" + str.substring(ID_MENTION_PREFIX_LENGTH));
      while (true)
      {
        arrayOfInt1[k] = paramSpannable.getSpanStart(localArrayList1.get(k));
        arrayOfInt2[k] = paramSpannable.getSpanEnd(localArrayList1.get(k));
        k++;
        break;
        if (str.startsWith("e:"))
          localArrayList2.add("+" + str.substring(EMAIL_MENTION_PREFIX_LENGTH));
        else
          localArrayList2.add("+" + str);
      }
    }
    StringBuilder localStringBuilder = new StringBuilder(paramSpannable.toString());
    for (int m = j - 1; m >= 0; m--)
      localStringBuilder.replace(arrayOfInt1[m], arrayOfInt2[m], (String)localArrayList2.get(m));
    return localStringBuilder.toString();
  }

  public static String prependProtocol(String paramString)
  {
    if ((!TextUtils.isEmpty(paramString)) && (!paramString.startsWith("http:")) && (!paramString.startsWith("https:")))
      paramString = "https:" + paramString;
    return paramString;
  }

  public static AudienceData removeCircleIdNamespaces(AudienceData paramAudienceData)
  {
    ArrayList localArrayList = new ArrayList(paramAudienceData.getCircleCount());
    CircleData[] arrayOfCircleData = paramAudienceData.getCircles();
    int i = arrayOfCircleData.length;
    int j = 0;
    if (j < i)
    {
      CircleData localCircleData = arrayOfCircleData[j];
      String[] arrayOfString = CIRCLE_ID_PREFIXES;
      int k = arrayOfString.length;
      for (int m = 0; ; m++)
        if (m < k)
        {
          String str = arrayOfString[m];
          if (localCircleData.getId().startsWith(str))
            localCircleData = new CircleData(localCircleData.getId().substring(str.length()), localCircleData.getType(), localCircleData.getName(), localCircleData.getSize());
        }
        else
        {
          localArrayList.add(localCircleData);
          j++;
          break;
        }
    }
    return new AudienceData(Arrays.asList(paramAudienceData.getUsers()), localArrayList);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.ApiUtils
 * JD-Core Version:    0.6.2
 */