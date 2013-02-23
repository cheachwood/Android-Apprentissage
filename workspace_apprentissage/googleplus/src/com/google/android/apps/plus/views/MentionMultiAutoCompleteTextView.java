package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.MultiAutoCompleteTextView;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.style;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.fragments.PeopleSearchListAdapter;
import com.google.android.apps.plus.fragments.PeopleSearchResults;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.ScreenMetrics;
import com.google.android.apps.plus.util.MentionTokenizer;
import com.google.android.apps.plus.util.SoftInput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public final class MentionMultiAutoCompleteTextView extends MultiAutoCompleteTextView
{
  private AudienceView mAudienceView;
  private PeopleSearchListAdapter mMentionCursorAdapter;
  private MentionTokenizer mMentionTokenizer = new MentionTokenizer();

  public MentionMultiAutoCompleteTextView(Context paramContext)
  {
    super(themedApplicationContext(paramContext, null));
  }

  public MentionMultiAutoCompleteTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(themedApplicationContext(paramContext, paramAttributeSet), paramAttributeSet);
  }

  public MentionMultiAutoCompleteTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(themedApplicationContext(paramContext, paramAttributeSet), paramAttributeSet, paramInt);
  }

  private void adjustInputMethod(boolean paramBoolean)
  {
    int i = getInputType();
    ScreenMetrics localScreenMetrics = ScreenMetrics.getInstance(getContext());
    if ((getResources().getConfiguration().orientation == 1) || (localScreenMetrics.screenDisplayType == 1) || (!paramBoolean));
    for (int j = i & 0xFFFEFFFF; ; j = i | 0x10000)
    {
      if (i != j)
      {
        setRawInputType(j);
        InputMethodManager localInputMethodManager = SoftInput.getInputMethodManager(getContext());
        if (localInputMethodManager != null)
          localInputMethodManager.restartInput(this);
      }
      return;
    }
  }

  private List<PersonData> getPersonList()
  {
    Editable localEditable = getText();
    int i = localEditable.length();
    MentionSpan[] arrayOfMentionSpan = (MentionSpan[])localEditable.getSpans(0, localEditable.length(), MentionSpan.class);
    ArrayList localArrayList = new ArrayList();
    HashSet localHashSet = new HashSet();
    int j = 0;
    int k = arrayOfMentionSpan.length;
    while (j < k)
    {
      String str1 = arrayOfMentionSpan[j].getAggregateId();
      if (!localHashSet.contains(str1))
      {
        localHashSet.add(str1);
        String str2 = localEditable.subSequence(localEditable.getSpanStart(arrayOfMentionSpan[j]), Math.min(i, 1 + localEditable.getSpanEnd(arrayOfMentionSpan[j]))).toString();
        if (str2.startsWith("+"))
          str2 = str2.substring(1);
        localArrayList.add(EsPeopleData.buildPersonFromPersonIdAndName(str1, str2));
      }
      j++;
    }
    return localArrayList;
  }

  private static Context themedApplicationContext(Context paramContext, AttributeSet paramAttributeSet)
  {
    int i;
    if (paramAttributeSet != null)
      if ("dark".equalsIgnoreCase(paramAttributeSet.getAttributeValue(null, "theme_style")))
        i = R.style.CircleBrowserTheme_DarkActionBar;
    while (true)
    {
      return new ContextThemeWrapper(paramContext.getApplicationContext(), i);
      i = R.style.CircleBrowserTheme;
      continue;
      i = R.style.CircleBrowserTheme;
    }
  }

  protected final CharSequence convertSelectionToString(Object paramObject)
  {
    Cursor localCursor = (Cursor)paramObject;
    SpannableString localSpannableString = new SpannableString("+" + super.convertSelectionToString(paramObject));
    int i = localCursor.getColumnIndex("person_id");
    if (i != -1)
      localSpannableString.setSpan(new MentionSpan(localCursor.getString(i)), 0, localSpannableString.length(), 33);
    return localSpannableString;
  }

  public final void destroy()
  {
    if (this.mMentionCursorAdapter != null)
    {
      this.mMentionCursorAdapter.close();
      this.mMentionCursorAdapter = null;
    }
    setAdapter(null);
    ((ViewGroup)getParent()).removeView(this);
  }

  public final int getCursorYPosition()
  {
    Layout localLayout = getLayout();
    if (localLayout == null);
    for (int i = 0; ; i = localLayout.getLineBaseline(localLayout.getLineForOffset(getSelectionEnd())))
      return i;
  }

  public final int getCursorYTop()
  {
    Layout localLayout = getLayout();
    if (localLayout == null);
    for (int i = 0; ; i = localLayout.getLineTop(localLayout.getLineForOffset(getSelectionEnd())))
      return i;
  }

  public final void init(Fragment paramFragment, EsAccount paramEsAccount, String paramString, AudienceView paramAudienceView)
  {
    this.mMentionCursorAdapter = new PeopleSearchListAdapter(getContext(), paramFragment.getFragmentManager(), paramFragment.getLoaderManager(), paramEsAccount, 1);
    this.mMentionCursorAdapter.setPublicProfileSearchEnabled(true);
    this.mMentionCursorAdapter.setIncludePlusPages(true);
    this.mMentionCursorAdapter.setMention(paramString);
    this.mAudienceView = paramAudienceView;
    setAdapter(this.mMentionCursorAdapter);
    setTokenizer(new MentionTokenizer());
    setThreshold(3);
    addTextChangedListener(new TextWatcher()
    {
      public final void afterTextChanged(Editable paramAnonymousEditable)
      {
      }

      public final void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        if ((paramAnonymousCharSequence instanceof Spannable))
        {
          Spannable localSpannable = (Spannable)paramAnonymousCharSequence;
          int j = -1 + (paramAnonymousInt1 + paramAnonymousInt2);
          int k = 0;
          List localList1 = MentionMultiAutoCompleteTextView.this.getPersonList();
          for (URLSpan localURLSpan : (URLSpan[])localSpannable.getSpans(paramAnonymousInt1, j, URLSpan.class))
            if (MentionSpan.isMention(localURLSpan))
            {
              localSpannable.removeSpan(localURLSpan);
              k = 1;
            }
          if (k != 0)
          {
            List localList2 = MentionMultiAutoCompleteTextView.this.getPersonList();
            MentionMultiAutoCompleteTextView.this.updateMentionAcls(localList1, localList2);
          }
        }
        int i = (int)MentionMultiAutoCompleteTextView.this.getContext().getResources().getDimension(R.dimen.plus_mention_suggestion_popup_offset);
        MentionMultiAutoCompleteTextView.this.setDropDownVerticalOffset(i + MentionMultiAutoCompleteTextView.this.getCursorYPosition() - MentionMultiAutoCompleteTextView.this.getHeight());
      }

      public final void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        int i = MentionMultiAutoCompleteTextView.this.getSelectionEnd();
        MentionMultiAutoCompleteTextView localMentionMultiAutoCompleteTextView = MentionMultiAutoCompleteTextView.this;
        if (1 + MentionMultiAutoCompleteTextView.this.mMentionTokenizer.findTokenStart(paramAnonymousCharSequence, i) <= i);
        for (boolean bool = true; ; bool = false)
        {
          localMentionMultiAutoCompleteTextView.adjustInputMethod(bool);
          return;
        }
      }
    });
  }

  protected final void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if (this.mMentionCursorAdapter != null)
      this.mMentionCursorAdapter.onStart();
  }

  protected final void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    if (this.mMentionCursorAdapter != null)
      this.mMentionCursorAdapter.onStop();
  }

  public final void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    if (this.mMentionCursorAdapter != null)
      this.mMentionCursorAdapter.onCreate(localSavedState.adapterState);
    Editable localEditable = getEditableText();
    for (URLSpan localURLSpan : (URLSpan[])localEditable.getSpans(0, localEditable.length(), URLSpan.class))
      if (MentionSpan.isMention(localURLSpan))
      {
        MentionSpan localMentionSpan = new MentionSpan(localURLSpan);
        int k = localEditable.getSpanStart(localURLSpan);
        int m = localEditable.getSpanEnd(localURLSpan);
        int n = localEditable.getSpanFlags(localURLSpan);
        localEditable.removeSpan(localURLSpan);
        localEditable.setSpan(localMentionSpan, k, m, n);
      }
  }

  public final Parcelable onSaveInstanceState()
  {
    Parcelable localParcelable = super.onSaveInstanceState();
    PeopleSearchListAdapter localPeopleSearchListAdapter = this.mMentionCursorAdapter;
    Bundle localBundle = null;
    if (localPeopleSearchListAdapter != null)
    {
      localBundle = new Bundle();
      this.mMentionCursorAdapter.onSaveInstanceState(localBundle);
    }
    return new SavedState(localParcelable, localBundle);
  }

  protected final void replaceText(CharSequence paramCharSequence)
  {
    List localList = getPersonList();
    super.replaceText(paramCharSequence);
    updateMentionAcls(localList, getPersonList());
    adjustInputMethod(false);
  }

  public final void setHtml(String paramString)
  {
    Spanned localSpanned = Html.fromHtml(paramString);
    Object[] arrayOfObject = localSpanned.getSpans(0, localSpanned.length(), Object.class);
    if (arrayOfObject == null)
      setText(localSpanned.toString());
    while (true)
    {
      return;
      SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
      localSpannableStringBuilder.append(localSpanned);
      int i = -1 + arrayOfObject.length;
      if (i >= 0)
      {
        Object localObject = arrayOfObject[i];
        int j = localSpannableStringBuilder.getSpanStart(localObject);
        int k = localSpannableStringBuilder.getSpanEnd(localObject);
        int m;
        if ((localObject instanceof StyleSpan))
        {
          m = ((StyleSpan)localObject).getStyle();
          if (m == 1)
          {
            localSpannableStringBuilder.insert(k, "*");
            localSpannableStringBuilder.insert(j, "*");
          }
        }
        while (true)
        {
          label131: localSpannableStringBuilder.removeSpan(localObject);
          String str1;
          do
          {
            i--;
            break;
            if (m == 2)
            {
              localSpannableStringBuilder.insert(k, "_");
              localSpannableStringBuilder.insert(j, "_");
              break label131;
            }
            if (m != 3)
              break label131;
            localSpannableStringBuilder.insert(k, "*_");
            localSpannableStringBuilder.insert(j, "_*");
            break label131;
            if (!(localObject instanceof URLSpan))
              break label131;
            str1 = ((URLSpan)localObject).getURL();
            if ((str1 == null) || (!Intents.isProfileUrl(str1)))
              break label292;
          }
          while ((j == 0) || (localSpannableStringBuilder.charAt(j - 1) != '+'));
          String str2 = Intents.getPersonIdFromProfileUrl(str1);
          if (str2 != null)
          {
            localSpannableStringBuilder.setSpan(new MentionSpan(str2), j - 1, k, 0);
            continue;
            label292: if ((str1 == null) || (!Intents.isHashtagUrl(str1)))
              localSpannableStringBuilder.replace(j, k, str1);
          }
        }
      }
      setText(localSpannableStringBuilder);
    }
  }

  protected final void updateMentionAcls(List<PersonData> paramList1, List<PersonData> paramList2)
  {
    if (this.mAudienceView == null);
    while (true)
    {
      return;
      Iterator localIterator1 = paramList2.iterator();
      while (localIterator1.hasNext())
      {
        PersonData localPersonData2 = (PersonData)localIterator1.next();
        this.mAudienceView.addPerson(localPersonData2);
      }
      Iterator localIterator2 = paramList1.iterator();
      while (localIterator2.hasNext())
      {
        PersonData localPersonData1 = (PersonData)localIterator2.next();
        if (!EsPeopleData.isPersonInList(localPersonData1, paramList2))
          this.mAudienceView.removePerson(localPersonData1);
      }
    }
  }

  public static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
    };
    final Bundle adapterState;

    private SavedState(Parcel paramParcel)
    {
      super();
      this.adapterState = ((Bundle)paramParcel.readParcelable(PeopleSearchResults.class.getClassLoader()));
    }

    SavedState(Parcelable paramParcelable, Bundle paramBundle)
    {
      super();
      this.adapterState = paramBundle;
    }

    public String toString()
    {
      return "MentionMultiAutoComplete.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.adapterState + "}";
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeParcelable(this.adapterState, 0);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.MentionMultiAutoCompleteTextView
 * JD-Core Version:    0.6.2
 */