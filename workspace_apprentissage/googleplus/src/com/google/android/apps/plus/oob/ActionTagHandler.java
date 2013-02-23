package com.google.android.apps.plus.oob;

import android.text.Editable;
import android.text.Html.TagHandler;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xml.sax.XMLReader;

public final class ActionTagHandler
  implements Html.TagHandler
{
  static final Pattern ACTION_RE = Pattern.compile("<action\\s+id=['\"](\\d+)['\"]>");
  private final List<String> mActionIds;
  private final Callback mCallback;
  private int mIndex;
  private final int mLinkColor;

  public ActionTagHandler(List<String> paramList, int paramInt, Callback paramCallback)
  {
    this.mActionIds = paramList;
    this.mLinkColor = paramInt;
    this.mCallback = paramCallback;
    this.mIndex = 0;
  }

  public static List<String> findActionIds(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    Matcher localMatcher = ACTION_RE.matcher(paramString);
    while (localMatcher.find())
      localArrayList.add(localMatcher.group(1));
    return localArrayList;
  }

  public final void handleTag(boolean paramBoolean, String paramString, Editable paramEditable, XMLReader paramXMLReader)
  {
    int i;
    if (paramString.equalsIgnoreCase("action"))
    {
      i = paramEditable.length();
      if (paramBoolean)
      {
        List localList = this.mActionIds;
        int m = this.mIndex;
        this.mIndex = (m + 1);
        paramEditable.setSpan(new ActionId((String)localList.get(m)), i, i, 17);
      }
    }
    while (true)
    {
      return;
      Object[] arrayOfObject = paramEditable.getSpans(0, paramEditable.length(), ActionId.class);
      int k;
      if (arrayOfObject.length > 0)
      {
        k = arrayOfObject.length;
        label101: if (k > 0)
          if (paramEditable.getSpanFlags(arrayOfObject[(k - 1)]) != 17);
      }
      for (Object localObject = arrayOfObject[(k - 1)]; ; localObject = null)
      {
        int j = paramEditable.getSpanStart(localObject);
        paramEditable.removeSpan(localObject);
        if (j == i)
          break;
        paramEditable.setSpan(new ActionClickableSpan(((ActionId)localObject).mActionId), j, i, 33);
        break;
        k--;
        break label101;
      }
      if ((!paramString.equalsIgnoreCase("html")) && (!paramString.equalsIgnoreCase("body")))
        Log.w("ActionTagHandler", "Unexpected tag '" + paramString + "' found.");
    }
  }

  private final class ActionClickableSpan extends ClickableSpan
  {
    private final String mActionId;

    ActionClickableSpan(String arg2)
    {
      Object localObject;
      this.mActionId = localObject;
    }

    public final void onClick(View paramView)
    {
      ActionTagHandler.this.mCallback.onActionId(this.mActionId);
    }

    public final void updateDrawState(TextPaint paramTextPaint)
    {
      paramTextPaint.setColor(ActionTagHandler.this.mLinkColor);
    }
  }

  private static class ActionId
  {
    public String mActionId;

    public ActionId(String paramString)
    {
      this.mActionId = paramString;
    }
  }

  public static abstract interface Callback
  {
    public abstract void onActionId(String paramString);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.oob.ActionTagHandler
 * JD-Core Version:    0.6.2
 */