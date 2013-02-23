package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.text.Spannable;
import android.text.Spannable.Factory;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.apps.plus.R.attr;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.util.HelpUrl;

public class SquareLandingView extends EsScrollView
  implements View.OnClickListener, ColumnGridView.PressedHighlightable, Recyclable
{
  private static Drawable sSelectableItemBackground;
  private boolean mAlwaysExpanded = false;
  private int mButtonAction;
  private boolean mIsExpanded = false;
  private OnClickListener mOnClickListener;
  private SquareLayout mSquareLayout;

  public SquareLandingView(Context paramContext)
  {
    super(paramContext);
    if (sSelectableItemBackground == null)
    {
      Resources.Theme localTheme = getContext().getTheme();
      int[] arrayOfInt = new int[1];
      arrayOfInt[0] = R.attr.buttonSelectableBackground;
      TypedArray localTypedArray = localTheme.obtainStyledAttributes(arrayOfInt);
      int i = localTypedArray.getResourceId(0, 0);
      sSelectableItemBackground = getResources().getDrawable(i);
      localTypedArray.recycle();
    }
  }

  public SquareLandingView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    if (sSelectableItemBackground == null)
    {
      Resources.Theme localTheme = getContext().getTheme();
      int[] arrayOfInt = new int[1];
      arrayOfInt[0] = R.attr.buttonSelectableBackground;
      TypedArray localTypedArray = localTheme.obtainStyledAttributes(arrayOfInt);
      int i = localTypedArray.getResourceId(0, 0);
      sSelectableItemBackground = getResources().getDrawable(i);
      localTypedArray.recycle();
    }
  }

  public SquareLandingView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (sSelectableItemBackground == null)
    {
      Resources.Theme localTheme = getContext().getTheme();
      int[] arrayOfInt = new int[1];
      arrayOfInt[0] = R.attr.buttonSelectableBackground;
      TypedArray localTypedArray = localTheme.obtainStyledAttributes(arrayOfInt);
      int i = localTypedArray.getResourceId(0, 0);
      sSelectableItemBackground = getResources().getDrawable(i);
      localTypedArray.recycle();
    }
  }

  private void bindExpandArea()
  {
    ImageView localImageView = this.mSquareLayout.expandArea;
    if (this.mIsExpanded);
    for (int i = R.drawable.icn_events_arrow_up; ; i = R.drawable.icn_events_arrow_down)
    {
      localImageView.setImageResource(i);
      return;
    }
  }

  private String getString(int paramInt)
  {
    return getResources().getString(paramInt);
  }

  private void initSquareLayout()
  {
    if (this.mSquareLayout == null)
      this.mSquareLayout = new SquareLayout(this);
  }

  public final void hideBlockingExplanation()
  {
    this.mSquareLayout.blockingExplanation.setText(null);
    this.mSquareLayout.blockingExplanation.setMovementMethod(null);
    this.mSquareLayout.blockingExplanation.setVisibility(8);
  }

  public final void hideNotificationSwitch()
  {
    this.mSquareLayout.notificationSection.setVisibility(8);
  }

  public final void init(boolean paramBoolean1, boolean paramBoolean2)
  {
    int i = 0;
    initSquareLayout();
    this.mSquareLayout.joinLeaveButton.setOnClickListener(this);
    this.mIsExpanded = paramBoolean1;
    this.mAlwaysExpanded = paramBoolean2;
    Object localObject;
    if (this.mAlwaysExpanded)
    {
      this.mSquareLayout.expandArea.setVisibility(8);
      localObject = this.mSquareLayout.details;
    }
    while (true)
    {
      ((View)localObject).setVisibility(i);
      this.mSquareLayout.notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
      {
        public final void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
        {
          if (SquareLandingView.this.mOnClickListener != null)
            SquareLandingView.this.mOnClickListener.onNotificationSwitchChanged(paramAnonymousBoolean);
        }
      });
      requestLayout();
      return;
      bindExpandArea();
      this.mSquareLayout.header.setOnClickListener(this);
      this.mSquareLayout.expandArea.setVisibility(0);
      View localView = this.mSquareLayout.details;
      if (this.mIsExpanded)
      {
        localObject = localView;
        i = 0;
      }
      else
      {
        i = 8;
        localObject = localView;
      }
    }
  }

  public void onClick(View paramView)
  {
    int i = paramView.getId();
    if ((i == R.id.header) && (!this.mAlwaysExpanded))
    {
      if (this.mIsExpanded)
      {
        this.mSquareLayout.details.setVisibility(8);
        this.mIsExpanded = false;
        paramView.setContentDescription(getString(R.string.expand_more_info_content_description));
        bindExpandArea();
        requestLayout();
      }
    }
    else
      if (this.mOnClickListener != null)
        break label100;
    while (true)
    {
      return;
      this.mSquareLayout.details.setVisibility(0);
      this.mIsExpanded = true;
      paramView.setContentDescription(getString(R.string.collapse_more_info_content_description));
      break;
      label100: if (i == R.id.header)
        this.mOnClickListener.onExpandClicked(this.mIsExpanded);
      else if (i == R.id.join_leave_button)
        this.mOnClickListener.onJoinLeaveClicked(this.mButtonAction);
    }
  }

  protected void onFinishInflate()
  {
    super.onFinishInflate();
    initSquareLayout();
    setVerticalFadingEdgeEnabled(true);
    setFadingEdgeLength(50);
  }

  public void onRecycle()
  {
    this.mOnClickListener = null;
    if (this.mSquareLayout != null)
    {
      this.mSquareLayout.squarePhoto.onRecycle();
      this.mSquareLayout.header.setOnClickListener(null);
      this.mSquareLayout.joinLeaveButton.setOnClickListener(null);
      this.mSquareLayout.notificationSwitch.setOnCheckedChangeListener(null);
      setMemberVisibility(false);
      hideBlockingExplanation();
    }
    this.mSquareLayout = null;
  }

  public void setMemberVisibility(boolean paramBoolean)
  {
    String str = this.mSquareLayout.memberCount.getText().toString();
    if (paramBoolean)
    {
      Spannable localSpannable = Spannable.Factory.getInstance().newSpannable(str);
      localSpannable.setSpan(new ClickableSpan()
      {
        public final void onClick(View paramAnonymousView)
        {
          if (SquareLandingView.this.mOnClickListener != null)
            SquareLandingView.this.mOnClickListener.onMembersClicked();
        }

        public final void updateDrawState(TextPaint paramAnonymousTextPaint)
        {
          super.updateDrawState(paramAnonymousTextPaint);
          paramAnonymousTextPaint.setUnderlineText(false);
        }
      }
      , 0, str.length(), 33);
      this.mSquareLayout.memberCount.setBackgroundDrawable(sSelectableItemBackground);
      this.mSquareLayout.memberCount.setText(localSpannable);
      this.mSquareLayout.memberCount.setMovementMethod(LinkMovementMethod.getInstance());
    }
    while (true)
    {
      return;
      this.mSquareLayout.memberCount.setBackgroundDrawable(null);
      this.mSquareLayout.memberCount.setText(str);
      this.mSquareLayout.memberCount.setMovementMethod(null);
    }
  }

  public void setOnClickListener(OnClickListener paramOnClickListener)
  {
    this.mOnClickListener = paramOnClickListener;
  }

  public void setSquareAboutText(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      this.mSquareLayout.description.setText(paramString);
      this.mSquareLayout.description.setVisibility(0);
    }
    while (true)
    {
      return;
      this.mSquareLayout.description.setVisibility(8);
    }
  }

  public void setSquareMemberCount(int paramInt)
  {
    if (paramInt == 0)
      this.mSquareLayout.memberCount.setVisibility(8);
    while (true)
    {
      return;
      this.mSquareLayout.memberCount.setVisibility(0);
      Resources localResources = getResources();
      int i = R.plurals.square_members_count;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      String str = localResources.getQuantityString(i, paramInt, arrayOfObject);
      this.mSquareLayout.memberCount.setText(str);
    }
  }

  public void setSquareName(String paramString)
  {
    this.mSquareLayout.squareName.setText(paramString);
  }

  public void setSquarePhoto(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      paramString = null;
    this.mSquareLayout.squarePhoto.setUrl(paramString);
  }

  public void setSquareVisibility(int paramInt)
  {
    int i;
    int j;
    int k;
    switch (paramInt)
    {
    default:
      i = 0;
      j = 0;
      k = 0;
      if (i != 0)
      {
        this.mSquareLayout.squareVisibility.setVisibility(0);
        this.mSquareLayout.squareVisibility.setText(j);
        this.mSquareLayout.squareVisibility.setCompoundDrawablesWithIntrinsicBounds(k, 0, 0, 0);
      }
      break;
    case 0:
    case 1:
    }
    while (true)
    {
      return;
      i = 1;
      j = R.string.square_public;
      k = R.drawable.ic_public_small;
      break;
      i = 1;
      j = R.string.square_private;
      k = R.drawable.ic_private_small;
      break;
      this.mSquareLayout.squareVisibility.setVisibility(8);
    }
  }

  public final boolean shouldHighlightOnPress()
  {
    return false;
  }

  public final void showBlockingExplanation()
  {
    String str = getResources().getString(R.string.url_param_help_privacy_block);
    Uri localUri = HelpUrl.getHelpUrl(getContext(), str);
    Resources localResources = getResources();
    int i = R.string.square_blocking_explanation;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = localUri.toString();
    Spanned localSpanned = Html.fromHtml(localResources.getString(i, arrayOfObject));
    URLSpan[] arrayOfURLSpan = (URLSpan[])localSpanned.getSpans(0, localSpanned.length(), URLSpan.class);
    if (arrayOfURLSpan.length > 0)
    {
      SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(localSpanned);
      final URLSpan localURLSpan = arrayOfURLSpan[0];
      int j = localSpanned.getSpanStart(localURLSpan);
      int k = localSpanned.getSpanEnd(localURLSpan);
      localSpannableStringBuilder.setSpan(new ClickableSpan()
      {
        public final void onClick(View paramAnonymousView)
        {
          if (SquareLandingView.this.mOnClickListener != null)
            SquareLandingView.this.mOnClickListener.onBlockingHelpLinkClicked(Uri.parse(localURLSpan.getURL()));
        }

        public final void updateDrawState(TextPaint paramAnonymousTextPaint)
        {
          super.updateDrawState(paramAnonymousTextPaint);
          paramAnonymousTextPaint.setUnderlineText(false);
        }
      }
      , j, k, 33);
      this.mSquareLayout.blockingExplanation.setText(localSpannableStringBuilder);
      this.mSquareLayout.blockingExplanation.setMovementMethod(LinkMovementMethod.getInstance());
      this.mSquareLayout.blockingExplanation.setVisibility(0);
    }
  }

  public final void showNotificationSwitch(boolean paramBoolean)
  {
    this.mSquareLayout.notificationSection.setVisibility(0);
    this.mSquareLayout.notificationSwitch.setChecked(paramBoolean);
  }

  public final void updateJoinLeaveButton(int paramInt)
  {
    this.mButtonAction = paramInt;
    int i;
    int j;
    int k;
    boolean bool;
    switch (paramInt)
    {
    default:
      i = R.string.square_leave;
      j = -3355444;
      k = R.drawable.plusone_button;
      bool = false;
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 0:
    }
    while (true)
    {
      this.mSquareLayout.joinLeaveButton.setText(i);
      this.mSquareLayout.joinLeaveButton.setTextColor(j);
      this.mSquareLayout.joinLeaveButton.setBackgroundResource(k);
      this.mSquareLayout.joinLeaveButton.setEnabled(bool);
      return;
      i = R.string.square_join;
      j = -1;
      k = R.drawable.plusone_by_me_button;
      bool = true;
      continue;
      i = R.string.square_accept_invitation;
      j = -1;
      k = R.drawable.plusone_by_me_button;
      bool = true;
      continue;
      i = R.string.square_request_to_join;
      j = -1;
      k = R.drawable.plusone_by_me_button;
      bool = true;
      continue;
      i = R.string.square_leave;
      j = -12303292;
      k = R.drawable.plusone_button;
      bool = true;
      continue;
      i = R.string.square_cancel_join_request;
      j = -12303292;
      k = R.drawable.plusone_button;
      bool = true;
      continue;
      i = R.string.square_invitation_required;
      j = -3355444;
      k = R.drawable.plusone_button;
      bool = false;
    }
  }

  public static abstract interface OnClickListener
  {
    public abstract void onBlockingHelpLinkClicked(Uri paramUri);

    public abstract void onExpandClicked(boolean paramBoolean);

    public abstract void onJoinLeaveClicked(int paramInt);

    public abstract void onMembersClicked();

    public abstract void onNotificationSwitchChanged(boolean paramBoolean);
  }

  private static final class SquareLayout
  {
    public TextView blockingExplanation;
    public TextView description;
    public View details;
    public ImageView expandArea;
    public View header;
    public Button joinLeaveButton;
    public TextView memberCount;
    public View notificationSection;
    public CompoundButton notificationSwitch;
    public ConstrainedTextView squareName;
    public EsImageView squarePhoto;
    public TextView squareVisibility;

    public SquareLayout(View paramView)
    {
      this.header = paramView.findViewById(R.id.header);
      this.details = paramView.findViewById(R.id.details);
      this.squarePhoto = ((EsImageView)this.header.findViewById(R.id.square_photo));
      this.squareName = ((ConstrainedTextView)this.header.findViewById(R.id.square_name));
      this.squareVisibility = ((TextView)this.header.findViewById(R.id.square_visibility));
      this.memberCount = ((TextView)this.header.findViewById(R.id.member_count));
      this.expandArea = ((ImageView)this.header.findViewById(R.id.expand));
      this.description = ((TextView)this.details.findViewById(R.id.description));
      this.joinLeaveButton = ((Button)this.details.findViewById(R.id.join_leave_button));
      this.notificationSection = this.details.findViewById(R.id.notification_section);
      this.notificationSwitch = ((CompoundButton)this.details.findViewById(R.id.notification_switch));
      this.blockingExplanation = ((TextView)this.details.findViewById(R.id.blocking_explanation));
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.SquareLandingView
 * JD-Core Version:    0.6.2
 */