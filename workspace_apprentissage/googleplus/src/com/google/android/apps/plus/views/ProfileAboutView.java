package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.Html.TagHandler;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.method.LinkMovementMethod;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.style;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.util.SpannableUtils;
import com.google.api.services.plusi.model.GoogleReviewProto;
import com.google.api.services.plusi.model.ZagatAspectRatingProto;
import com.google.api.services.plusi.model.ZagatAspectRatingsProto;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import org.xml.sax.XMLReader;

public class ProfileAboutView extends EsScrollView
  implements View.OnClickListener, ColumnGridView.PressedHighlightable, Recyclable
{
  private static Drawable mEducationBackground;
  private static Drawable mLocationsBackground;
  private static final DisplayPolicies sDefaultPolicy = new DisplayPolicies();
  private static Drawable sEmploymentBackground;
  private static int sIsExpandedMarginBottom;
  private static int sPlusOneStandardTextColor;
  private static float sPlusOneTextSize;
  private static int sPlusOnedByMeTextColor;
  private DetailsLayout mDetails;
  boolean mEditEnabled = false;
  private HeaderLayout mHeader;
  private final LayoutInflater mInflater = (LayoutInflater)getContext().getSystemService("layout_inflater");
  boolean mIsExpanded = false;
  private OnClickListener mOnClickListener;
  private DisplayPolicies mPolicy = sDefaultPolicy;
  private ProfileLayout mProfileLayout;

  public ProfileAboutView(Context paramContext)
  {
    super(paramContext);
    Resources localResources = getContext().getResources();
    if (sPlusOnedByMeTextColor == 0)
    {
      sPlusOnedByMeTextColor = localResources.getColor(R.color.card_plus_oned_text);
      sPlusOneStandardTextColor = localResources.getColor(R.color.card_not_plus_oned_text);
      sPlusOneTextSize = localResources.getDimension(R.dimen.card_plus_oned_text_size);
      sIsExpandedMarginBottom = localResources.getDimensionPixelOffset(R.dimen.profile_card_bottom_padding);
      sEmploymentBackground = localResources.getDrawable(R.drawable.profile_selectable_item_background);
      mEducationBackground = localResources.getDrawable(R.drawable.profile_selectable_item_background);
      mLocationsBackground = localResources.getDrawable(R.drawable.profile_selectable_item_background);
    }
  }

  public ProfileAboutView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = getContext().getResources();
    if (sPlusOnedByMeTextColor == 0)
    {
      sPlusOnedByMeTextColor = localResources.getColor(R.color.card_plus_oned_text);
      sPlusOneStandardTextColor = localResources.getColor(R.color.card_not_plus_oned_text);
      sPlusOneTextSize = localResources.getDimension(R.dimen.card_plus_oned_text_size);
      sIsExpandedMarginBottom = localResources.getDimensionPixelOffset(R.dimen.profile_card_bottom_padding);
      sEmploymentBackground = localResources.getDrawable(R.drawable.profile_selectable_item_background);
      mEducationBackground = localResources.getDrawable(R.drawable.profile_selectable_item_background);
      mLocationsBackground = localResources.getDrawable(R.drawable.profile_selectable_item_background);
    }
  }

  public ProfileAboutView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    Resources localResources = getContext().getResources();
    if (sPlusOnedByMeTextColor == 0)
    {
      sPlusOnedByMeTextColor = localResources.getColor(R.color.card_plus_oned_text);
      sPlusOneStandardTextColor = localResources.getColor(R.color.card_not_plus_oned_text);
      sPlusOneTextSize = localResources.getDimension(R.dimen.card_plus_oned_text_size);
      sIsExpandedMarginBottom = localResources.getDimensionPixelOffset(R.dimen.profile_card_bottom_padding);
      sEmploymentBackground = localResources.getDrawable(R.drawable.profile_selectable_item_background);
      mEducationBackground = localResources.getDrawable(R.drawable.profile_selectable_item_background);
      mLocationsBackground = localResources.getDrawable(R.drawable.profile_selectable_item_background);
    }
  }

  private void addReviewToParent(GoogleReviewProto paramGoogleReviewProto, View paramView, int paramInt)
  {
    LinearLayout localLinearLayout = (LinearLayout)paramView.findViewById(R.id.content);
    View localView = this.mInflater.inflate(R.layout.profile_item_local_user_review, localLinearLayout, false);
    int i = localLinearLayout.getChildCount();
    LocalReviewListItemView localLocalReviewListItemView = (LocalReviewListItemView)localView.findViewById(R.id.local_review_item);
    int j;
    if (i == 0)
    {
      j = 1;
      if (j != 0)
        break label132;
    }
    label132: for (boolean bool = true; ; bool = false)
    {
      localLocalReviewListItemView.setTopBorderVisible(bool);
      localLocalReviewListItemView.setReview(paramGoogleReviewProto);
      localLocalReviewListItemView.setAuthorAvatarOnClickListener(this);
      Integer[] arrayOfInteger = new Integer[2];
      arrayOfInteger[0] = Integer.valueOf(paramInt);
      arrayOfInteger[1] = Integer.valueOf(i);
      localLocalReviewListItemView.setTag(arrayOfInteger);
      localLocalReviewListItemView.setOnClickListener(this);
      localLinearLayout.addView(localView);
      return;
      j = 0;
      break;
    }
  }

  private void bindDataView(View paramView, int paramInt, String paramString1, String paramString2)
  {
    ImageView localImageView = (ImageView)paramView.findViewById(16908294);
    if (paramInt != 0)
      localImageView.setImageResource(paramInt);
    while (true)
    {
      ((TextView)paramView.findViewById(16908308)).setText(paramString1);
      ((TextView)paramView.findViewById(16908309)).setText(paramString2.toUpperCase());
      setupContentDescription(paramView, paramString1, paramString2);
      return;
      localImageView.setImageDrawable(null);
    }
  }

  private void bindExpandArea()
  {
    int k;
    int i;
    if (this.mPolicy.showExpandButtonText)
    {
      if (this.mIsExpanded)
      {
        k = R.string.profile_show_less;
        this.mHeader.expandArea.setText(k);
      }
    }
    else
    {
      if (!this.mIsExpanded)
        break label92;
      i = R.drawable.icn_events_arrow_up;
    }
    for (int j = 0; ; j = sIsExpandedMarginBottom)
    {
      this.mHeader.expandArea.setCompoundDrawablesWithIntrinsicBounds(0, 0, i, 0);
      this.mHeader.expandArea.setVisibility(0);
      this.mHeader.container.setPadding(0, 0, 0, j);
      return;
      k = R.string.profile_show_more;
      break;
      label92: i = R.drawable.icn_events_arrow_down;
    }
  }

  private void bindIntroductionView(View paramView, String paramString)
  {
    TextView localTextView = (TextView)paramView;
    SpannableStringBuilder localSpannableStringBuilder = ClickableStaticLayout.buildStateSpans(paramString, new IntroductionTagHandler());
    int i = localSpannableStringBuilder.length();
    for (int j = 0; (j != i) && (Character.isWhitespace(localSpannableStringBuilder.charAt(j))); j++);
    if (j != 0)
    {
      localSpannableStringBuilder.delete(0, j);
      i = localSpannableStringBuilder.length();
    }
    for (int k = i - 1; (k >= 0) && (Character.isWhitespace(localSpannableStringBuilder.charAt(k))); k--);
    if (k != i - 1)
      localSpannableStringBuilder.delete(k + 1, i);
    localTextView.setText(localSpannableStringBuilder);
    localTextView.setContentDescription(localSpannableStringBuilder);
    if (!(localTextView.getMovementMethod() instanceof LinkMovementMethod))
      localTextView.setMovementMethod(LinkMovementMethod.getInstance());
  }

  private void bindLinkView(View paramView, String paramString1, String paramString2, String paramString3)
  {
    ((EsImageView)paramView.findViewById(16908294)).setUrl(paramString2);
    ((TextView)paramView.findViewById(16908308)).setText(paramString1);
    TextView localTextView = (TextView)paramView.findViewById(16908309);
    if (paramString3 != null)
    {
      localTextView.setVisibility(0);
      localTextView.setText(paramString3.toUpperCase());
      setupContentDescription(paramView, paramString1, paramString3);
    }
    while (true)
    {
      return;
      localTextView.setVisibility(8);
      paramView.setContentDescription(paramString1);
    }
  }

  private void bindSectionHeader(SectionHeaderView paramSectionHeaderView, int paramInt, boolean paramBoolean)
  {
    paramSectionHeaderView.setText(paramInt);
    paramSectionHeaderView.setContentDescription(getString(paramInt));
    paramSectionHeaderView.enableEditIcon(paramBoolean);
  }

  private void enableAvatarChangePhotoIcon(boolean paramBoolean)
  {
    ImageView localImageView = this.mHeader.avatarChoosePhotoIcon;
    if (paramBoolean);
    for (int i = 0; ; i = 8)
    {
      localImageView.setVisibility(i);
      return;
    }
  }

  private void enableCoverPhotoChangePhotoIcon(boolean paramBoolean)
  {
    ImageView localImageView = this.mHeader.coverPhotoChoosePhotoIcon;
    if (paramBoolean);
    for (int i = 0; ; i = 8)
    {
      localImageView.setVisibility(i);
      return;
    }
  }

  private static void enableDivider(View paramView, boolean paramBoolean)
  {
    View localView = paramView.findViewById(R.id.divider);
    if (localView != null)
      if (!paramBoolean)
        break label24;
    label24: for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      return;
    }
  }

  private View getLabeledStringView(ViewGroup paramViewGroup, View paramView, int paramInt1, int paramInt2, String paramString)
  {
    String str = getString(paramInt2);
    return getLabeledStringView(paramViewGroup, paramView, paramInt1, str.toUpperCase(), paramString, str);
  }

  private View getLabeledStringView(ViewGroup paramViewGroup, View paramView, int paramInt, String paramString1, String paramString2, String paramString3)
  {
    if (paramView == null)
      paramView = this.mInflater.inflate(paramInt, paramViewGroup, false);
    ((TextView)paramView.findViewById(16908308)).setText(paramString2);
    TextView localTextView = (TextView)paramView.findViewById(16908309);
    if (TextUtils.isEmpty(paramString1))
      localTextView.setVisibility(8);
    while (true)
    {
      setupContentDescription(paramView, paramString2, paramString3);
      return paramView;
      localTextView.setText(paramString1);
      localTextView.setVisibility(0);
    }
  }

  private String getString(int paramInt)
  {
    return getContext().getString(paramInt);
  }

  private void initProfileLayout()
  {
    this.mProfileLayout = new ProfileLayout(this);
    this.mHeader = this.mProfileLayout.header;
    this.mDetails = this.mProfileLayout.details;
  }

  private static void setupContentDescription(View paramView, CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if ((paramCharSequence2 != null) && (paramCharSequence2.length() > 0))
    {
      localStringBuilder.append(paramCharSequence2);
      localStringBuilder.append(" ");
    }
    if ((paramCharSequence1 != null) && (paramCharSequence1.length() > 0))
      localStringBuilder.append(paramCharSequence1);
    paramView.setContentDescription(localStringBuilder.toString());
  }

  private void updateGenericListSectionDividers(ViewGroup paramViewGroup)
  {
    int i = paramViewGroup.getChildCount();
    if (i == 0);
    while (true)
    {
      return;
      int k;
      View localView;
      for (int j = 0; ; j = k)
      {
        k = j + 1;
        localView = paramViewGroup.getChildAt(j);
        if (k == i)
          break;
        enableDivider(localView, true);
      }
      enableDivider(localView, false);
    }
  }

  public final void addAddress(String paramString1, String paramString2)
  {
    View localView = this.mInflater.inflate(R.layout.profile_item_multi_line_with_icon, this.mDetails.addresses, false);
    int i;
    if (this.mDetails.addresses.getChildCount() == 0)
    {
      i = 1;
      if (i == 0)
        break label102;
    }
    label102: for (int j = R.drawable.profile_address; ; j = 0)
    {
      bindDataView(localView, j, paramString1, paramString2);
      this.mDetails.addresses.addView(localView);
      localView.setId(R.id.address_content);
      localView.setTag(paramString1);
      localView.setOnTouchListener(new ItemOnTouchListener((byte)0));
      localView.setOnClickListener(this);
      return;
      i = 0;
      break;
    }
  }

  public final void addCircleReview(GoogleReviewProto paramGoogleReviewProto)
  {
    addReviewToParent(paramGoogleReviewProto, this.mDetails.container.findViewById(R.id.circle_activity), 2);
  }

  public final void addEducationLocation(String paramString1, String paramString2)
  {
    View localView = getLabeledStringView(this.mDetails.educationSection, null, R.layout.profile_item_multi_line, paramString2, paramString1, paramString1 + ", " + paramString2);
    ((ViewGroup)this.mDetails.educationSection.findViewById(R.id.content)).addView(localView);
    this.mDetails.educationSectionLastLocation = localView;
  }

  public final void addEmail(String paramString1, String paramString2)
  {
    View localView = this.mInflater.inflate(R.layout.profile_item_two_line_with_icon, this.mDetails.emails, false);
    int i;
    if (this.mDetails.emails.getChildCount() == 0)
    {
      i = 1;
      if (i == 0)
        break label102;
    }
    label102: for (int j = R.drawable.profile_email; ; j = 0)
    {
      bindDataView(localView, j, paramString1, paramString2);
      this.mDetails.emails.addView(localView);
      localView.setId(R.id.email_content);
      localView.setTag(paramString1);
      localView.setOnTouchListener(new ItemOnTouchListener((byte)0));
      localView.setOnClickListener(this);
      return;
      i = 0;
      break;
    }
  }

  public final void addEmploymentLocation(String paramString1, String paramString2)
  {
    View localView = getLabeledStringView(this.mDetails.workSection, null, R.layout.profile_item_multi_line, paramString2, paramString1, paramString1 + ", " + paramString2);
    ((ViewGroup)this.mDetails.workSection.findViewById(R.id.content)).addView(localView);
    this.mDetails.workSectionLastLocation = localView;
  }

  public final void addLink(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    View localView = this.mInflater.inflate(R.layout.profile_item_link, this.mDetails.locations, false);
    if (this.mDetails.links.getChildCount() == 0);
    while (true)
    {
      bindLinkView(localView, paramString2, paramString3, paramString4);
      this.mDetails.links.addView(localView);
      localView.setId(R.id.link_content);
      localView.setTag(paramString1);
      localView.setOnTouchListener(new ItemOnTouchListener((byte)0));
      localView.setOnClickListener(this);
      return;
      paramString4 = null;
    }
  }

  public final void addLocalReview(GoogleReviewProto paramGoogleReviewProto)
  {
    addReviewToParent(paramGoogleReviewProto, this.mDetails.container.findViewById(R.id.local_reviews), 0);
  }

  public final void addLocation(String paramString, boolean paramBoolean)
  {
    View localView1 = this.mInflater.inflate(R.layout.profile_item_location, this.mDetails.locations, false);
    this.mDetails.locations.addView(localView1);
    LocationItem localLocationItem = new LocationItem(paramString, paramBoolean);
    View localView2 = localView1.findViewById(16908294);
    if (localLocationItem.current);
    for (int i = 0; ; i = 4)
    {
      localView2.setVisibility(i);
      ((TextView)localView1.findViewById(16908308)).setText(localLocationItem.address);
      localView1.setContentDescription(localLocationItem.address);
      localView1.setTag(localLocationItem);
      localView1.setOnTouchListener(new ItemOnTouchListener((byte)0));
      localView1.setOnClickListener(this);
      return;
    }
  }

  public final void addPhoneNumber(String paramString1, String paramString2, boolean paramBoolean)
  {
    View localView1 = this.mInflater.inflate(R.layout.profile_item_phone, this.mDetails.phoneNumbers, false);
    int i;
    int j;
    label52: View localView2;
    View localView3;
    if (this.mDetails.phoneNumbers.getChildCount() == 0)
    {
      i = 1;
      String str = PhoneNumberUtils.formatNumber(paramString1);
      if (i == 0)
        break label164;
      j = R.drawable.profile_phone;
      bindDataView(localView1, j, str, paramString2);
      this.mDetails.phoneNumbers.addView(localView1);
      localView1.setId(R.id.phone_content);
      localView1.setTag(paramString1);
      localView1.setOnTouchListener(new ItemOnTouchListener((byte)0));
      localView1.setOnClickListener(this);
      localView2 = localView1.findViewById(R.id.send_text_button);
      localView3 = localView1.findViewById(R.id.vertical_divider);
      if (!paramBoolean)
        break label170;
      localView2.setVisibility(0);
      localView3.setVisibility(0);
      localView2.setTag(paramString1);
      localView2.setOnClickListener(this);
    }
    while (true)
    {
      return;
      i = 0;
      break;
      label164: j = 0;
      break label52;
      label170: localView2.setVisibility(8);
      localView3.setVisibility(8);
    }
  }

  public final void addYourReview(GoogleReviewProto paramGoogleReviewProto)
  {
    addReviewToParent(paramGoogleReviewProto, this.mDetails.container.findViewById(R.id.user_activity), 1);
  }

  public final void clearAddresses()
  {
    this.mDetails.addresses.removeAllViews();
  }

  public final void clearAllReviews()
  {
    ((LinearLayout)this.mDetails.container.findViewById(R.id.user_activity).findViewById(R.id.content)).removeAllViews();
    ((LinearLayout)this.mDetails.container.findViewById(R.id.circle_activity).findViewById(R.id.content)).removeAllViews();
    ((LinearLayout)this.mDetails.container.findViewById(R.id.local_reviews).findViewById(R.id.content)).removeAllViews();
  }

  public final void clearEducationLocations()
  {
    ((ViewGroup)this.mDetails.educationSection.findViewById(R.id.content)).removeAllViews();
    this.mDetails.educationSectionLastLocation = null;
    this.mDetails.educationSection.findViewById(R.id.no_items).setVisibility(8);
  }

  public final void clearEmails()
  {
    this.mDetails.emails.removeAllViews();
  }

  public final void clearEmploymentLocations()
  {
    ((ViewGroup)this.mDetails.workSection.findViewById(R.id.content)).removeAllViews();
    this.mDetails.workSectionLastLocation = null;
    this.mDetails.workSection.findViewById(R.id.no_items).setVisibility(8);
  }

  public final void clearLinks()
  {
    this.mDetails.links.removeAllViews();
  }

  public final void clearLocations()
  {
    this.mDetails.locations.removeAllViews();
    this.mDetails.locationsSection.findViewById(R.id.no_items).setVisibility(8);
  }

  public final void clearPhoneNumbers()
  {
    this.mDetails.phoneNumbers.removeAllViews();
  }

  public final void enableContactSection(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mDetails.contactSection.setVisibility(0);
      bindSectionHeader((SectionHeaderView)this.mDetails.contactSection.findViewById(R.id.header), R.string.profile_section_contact, false);
    }
    while (true)
    {
      return;
      this.mDetails.contactSection.setVisibility(8);
    }
  }

  public final void enableEducationSection(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mDetails.educationSection.setVisibility(0);
      SectionHeaderView localSectionHeaderView = (SectionHeaderView)this.mDetails.educationSection.findViewById(R.id.header);
      bindSectionHeader(localSectionHeaderView, R.string.profile_section_education, this.mEditEnabled);
      if (this.mDetails.educationSectionLastLocation != null)
        enableDivider(this.mDetails.educationSectionLastLocation, false);
      if (this.mEditEnabled)
      {
        localSectionHeaderView.findViewById(R.id.edit).setVisibility(0);
        localSectionHeaderView.setOnClickListener(this);
        localSectionHeaderView.setTag(Integer.valueOf(1101));
        localSectionHeaderView.setBackgroundDrawable(mEducationBackground);
      }
    }
    while (true)
    {
      return;
      this.mDetails.educationSection.setVisibility(8);
    }
  }

  public final void enableHompageSection(boolean paramBoolean)
  {
    View localView = this.mDetails.container.findViewById(R.id.homepage);
    if (paramBoolean);
    for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      bindSectionHeader((SectionHeaderView)localView.findViewById(R.id.homepage_header), R.string.profile_section_links, false);
      return;
    }
  }

  public final void enableLinksSection(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mDetails.linksSection.setVisibility(0);
      bindSectionHeader((SectionHeaderView)this.mDetails.linksSection.findViewById(R.id.header), R.string.profile_section_links, false);
    }
    while (true)
    {
      return;
      this.mDetails.linksSection.setVisibility(8);
    }
  }

  public final void enableLocalDetailsSection(boolean paramBoolean)
  {
    View localView = this.mDetails.container.findViewById(R.id.local_details);
    if (paramBoolean);
    for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      if (paramBoolean)
        bindSectionHeader((SectionHeaderView)localView.findViewById(R.id.local_details_header), R.string.profile_local_section_details, false);
      return;
    }
  }

  public final void enableLocalEditorialReviewsSection(boolean paramBoolean)
  {
    View localView = this.mDetails.container.findViewById(R.id.zagat);
    if (paramBoolean);
    for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      return;
    }
  }

  public final void enableLocalReviewsSection(boolean paramBoolean)
  {
    View localView = this.mDetails.container.findViewById(R.id.local_reviews);
    if (paramBoolean)
    {
      localView.setVisibility(0);
      bindSectionHeader((SectionHeaderView)localView.findViewById(R.id.header), R.string.profile_local_section_reviews, false);
    }
    while (true)
    {
      return;
      localView.setVisibility(8);
    }
  }

  public final void enableLocalYourActivitySection(boolean paramBoolean)
  {
    View localView = this.mDetails.container.findViewById(R.id.user_activity);
    if (paramBoolean)
    {
      localView.setVisibility(0);
      bindSectionHeader((SectionHeaderView)localView.findViewById(R.id.header), R.string.profile_local_section_your_activity, false);
    }
    while (true)
    {
      return;
      localView.setVisibility(8);
    }
  }

  public final void enableLocalYourCirclesActivitySection(boolean paramBoolean)
  {
    View localView = this.mDetails.container.findViewById(R.id.circle_activity);
    if (paramBoolean)
    {
      localView.setVisibility(0);
      bindSectionHeader((SectionHeaderView)localView.findViewById(R.id.header), R.string.profile_local_section_activity_from_your_circles, false);
    }
    while (true)
    {
      return;
      localView.setVisibility(8);
    }
  }

  public final void enableLocationsSection(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mDetails.locationsSection.setVisibility(0);
      SectionHeaderView localSectionHeaderView = (SectionHeaderView)this.mDetails.locationsSection.findViewById(R.id.header);
      bindSectionHeader(localSectionHeaderView, R.string.profile_section_places, false);
      if (this.mEditEnabled)
      {
        localSectionHeaderView.findViewById(R.id.edit).setVisibility(0);
        localSectionHeaderView.setOnClickListener(this);
        localSectionHeaderView.setTag(Integer.valueOf(1102));
        localSectionHeaderView.setBackgroundDrawable(mLocationsBackground);
      }
    }
    while (true)
    {
      return;
      this.mDetails.locationsSection.setVisibility(8);
    }
  }

  public final void enablePersonalSection(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mDetails.personalSection.setVisibility(0);
      bindSectionHeader((SectionHeaderView)this.mDetails.personalSection.findViewById(R.id.header), R.string.profile_section_personal, false);
    }
    while (true)
    {
      return;
      this.mDetails.personalSection.setVisibility(8);
    }
  }

  public final void enableWorkSection(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mDetails.workSection.setVisibility(0);
      SectionHeaderView localSectionHeaderView = (SectionHeaderView)this.mDetails.workSection.findViewById(R.id.header);
      bindSectionHeader(localSectionHeaderView, R.string.profile_section_employment, this.mEditEnabled);
      if (this.mDetails.workSectionLastLocation != null)
        enableDivider(this.mDetails.workSectionLastLocation, false);
      if (this.mEditEnabled)
      {
        localSectionHeaderView.findViewById(R.id.edit).setVisibility(0);
        localSectionHeaderView.setOnClickListener(this);
        localSectionHeaderView.setTag(Integer.valueOf(1100));
        localSectionHeaderView.setBackgroundDrawable(sEmploymentBackground);
      }
    }
    while (true)
    {
      return;
      this.mDetails.workSection.setVisibility(8);
    }
  }

  public final void init(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.mProfileLayout == null)
      initProfileLayout();
    this.mHeader.coverPhoto.setVisibility(0);
    this.mHeader.scrapbookAlbum.setVisibility(8);
    this.mHeader.expandArea.setOnClickListener(this);
    this.mHeader.circlesButton.setOnClickListener(this);
    this.mHeader.addToCirclesButton.setOnClickListener(this);
    this.mHeader.plusOneButton.setOnClickListener(this);
    this.mIsExpanded = paramBoolean1;
    this.mEditEnabled = paramBoolean2;
  }

  public void onClick(View paramView)
  {
    int i = paramView.getId();
    if (i == R.id.expand)
    {
      if (this.mIsExpanded)
      {
        this.mDetails.container.setVisibility(8);
        this.mIsExpanded = false;
        bindExpandArea();
        paramView.setContentDescription(getString(R.string.expand_more_info_content_description));
        requestLayout();
      }
    }
    else
      if (this.mOnClickListener != null)
        break label97;
    while (true)
    {
      return;
      this.mDetails.container.setVisibility(0);
      this.mIsExpanded = true;
      bindExpandArea();
      paramView.setContentDescription(getString(R.string.collapse_more_info_content_description));
      break;
      label97: if (i == R.id.avatar_image)
      {
        this.mOnClickListener.onAvatarClicked();
      }
      else if (i == R.id.cover_photo_image)
      {
        this.mOnClickListener.onCoverPhotoClicked(0);
      }
      else if (i == R.id.photo_1)
      {
        this.mOnClickListener.onCoverPhotoClicked(1);
      }
      else if (i == R.id.photo_2)
      {
        this.mOnClickListener.onCoverPhotoClicked(2);
      }
      else if (i == R.id.photo_3)
      {
        this.mOnClickListener.onCoverPhotoClicked(3);
      }
      else if (i == R.id.photo_4)
      {
        this.mOnClickListener.onCoverPhotoClicked(4);
      }
      else if (i == R.id.photo_5)
      {
        this.mOnClickListener.onCoverPhotoClicked(5);
      }
      else if (i == R.id.location)
      {
        LocationItem localLocationItem = (LocationItem)paramView.getTag();
        this.mOnClickListener.onLocationClicked(localLocationItem.address);
      }
      else if (i == R.id.email_content)
      {
        this.mOnClickListener.onEmailClicked((String)paramView.getTag());
      }
      else if (i == R.id.phone_content)
      {
        this.mOnClickListener.onPhoneNumberClicked((String)paramView.getTag());
      }
      else if (i == R.id.send_text_button)
      {
        this.mOnClickListener.onSendTextClicked((String)paramView.getTag());
      }
      else if (i == R.id.address_content)
      {
        this.mOnClickListener.onAddressClicked((String)paramView.getTag());
      }
      else if (i == R.id.link_content)
      {
        this.mOnClickListener.onLinkClicked((String)paramView.getTag());
      }
      else if ((i == R.id.circles_button) || (i == R.id.add_to_circles_button))
      {
        this.mOnClickListener.onCirclesButtonClicked();
      }
      else if (i == R.id.map_button)
      {
        this.mOnClickListener.onLocalMapClicked((String)paramView.getTag());
      }
      else if (i == R.id.directions_button)
      {
        this.mOnClickListener.onLocalDirectionsClicked((String)paramView.getTag());
      }
      else if (i == R.id.call_button)
      {
        this.mOnClickListener.onLocalCallClicked((String)paramView.getTag());
      }
      else if (i == R.id.zagat_explanation)
      {
        this.mOnClickListener.onZagatExplanationClicked();
      }
      else if (i == R.id.local_review_item)
      {
        int j = ((Integer[])paramView.getTag())[0].intValue();
        int k = ((Integer[])paramView.getTag())[1].intValue();
        this.mOnClickListener.onLocalReviewClicked(j, k);
      }
      else if (i == R.id.author_avatar)
      {
        AvatarView localAvatarView = (AvatarView)paramView;
        this.mOnClickListener.onReviewAuthorAvatarClicked(localAvatarView.getGaiaId());
      }
      else if (i == R.id.plus_one)
      {
        this.mOnClickListener.onPlusOneClicked();
      }
      else if (i == R.id.expand)
      {
        this.mOnClickListener.onExpandClicked(this.mIsExpanded);
      }
      else if (i == R.id.header)
      {
        Integer localInteger = (Integer)paramView.getTag();
        if (localInteger != null)
          switch (localInteger.intValue())
          {
          default:
            break;
          case 1100:
            this.mOnClickListener.onEditEmploymentClicked();
            break;
          case 1101:
            this.mOnClickListener.onEditEducationClicked();
            break;
          case 1102:
            this.mOnClickListener.onEditPlacesLivedClicked();
          }
      }
    }
  }

  protected void onFinishInflate()
  {
    super.onFinishInflate();
    initProfileLayout();
    setVerticalFadingEdgeEnabled(true);
    setFadingEdgeLength(50);
  }

  public void onRecycle()
  {
    this.mOnClickListener = null;
    if (this.mHeader != null)
    {
      this.mHeader.coverPhoto.onRecycle();
      this.mHeader.avatarImage.onRecycle();
      for (int i = 0; i < 5; i++)
        this.mHeader.scrapbookPhoto[i].onRecycle();
      this.mHeader.expandArea.setOnClickListener(null);
      this.mHeader.coverPhoto.setOnClickListener(null);
      this.mHeader.avatarImage.setOnClickListener(null);
      this.mHeader.circlesButton.setOnClickListener(null);
      this.mHeader.addToCirclesButton.setOnClickListener(null);
      this.mHeader.plusOneButton.setOnClickListener(null);
    }
    this.mHeader = null;
    this.mDetails = null;
    this.mProfileLayout = null;
  }

  public void setAddedByCount(Integer paramInteger)
  {
    if (paramInteger != null)
    {
      NumberFormat localNumberFormat = NumberFormat.getIntegerInstance();
      Resources localResources = getContext().getResources();
      int i = R.plurals.profile_added_by;
      int j = paramInteger.intValue();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localNumberFormat.format(paramInteger);
      String str = localResources.getQuantityString(i, j, arrayOfObject);
      this.mHeader.addedByCount.setText(str);
      this.mHeader.addedByCount.setVisibility(0);
    }
    while (true)
    {
      return;
      this.mHeader.addedByCount.setVisibility(8);
    }
  }

  public void setAvatarToDefault(boolean paramBoolean)
  {
    this.mHeader.avatarImage.setResourceMissing(true);
    this.mHeader.avatarImage.setOnClickListener(this);
    enableAvatarChangePhotoIcon(paramBoolean);
  }

  public void setAvatarUrl(String paramString, boolean paramBoolean)
  {
    this.mHeader.avatarImage.setMediaRef(new MediaRef(paramString, MediaRef.MediaType.IMAGE));
    this.mHeader.avatarImage.setOnClickListener(this);
    enableAvatarChangePhotoIcon(paramBoolean);
  }

  public void setBirthday(String paramString)
  {
    if (paramString != null)
    {
      if (this.mDetails.birthday == null)
        this.mDetails.birthday = this.mDetails.personalSection.findViewById(1001);
      View localView = getLabeledStringView(this.mDetails.personalSection, this.mDetails.birthday, R.layout.profile_item_two_line, R.string.profile_item_birthday, paramString);
      if (this.mDetails.birthday == null)
      {
        this.mDetails.birthday = localView;
        this.mDetails.birthday.setId(1001);
        this.mDetails.personalSection.addView(localView);
      }
      this.mDetails.birthday.setVisibility(0);
    }
    while (true)
    {
      return;
      if (this.mDetails.birthday != null)
        this.mDetails.birthday.setVisibility(8);
    }
  }

  public void setCircles(ArrayList<String> paramArrayList)
  {
    this.mHeader.circlesButton.setVisibility(0);
    this.mHeader.circlesButton.setCircles(paramArrayList);
    this.mHeader.addToCirclesButton.setVisibility(8);
    this.mHeader.blockedText.setVisibility(8);
    this.mHeader.progressBar.setVisibility(8);
  }

  public void setCoverPhotoToDefault(boolean paramBoolean)
  {
    this.mHeader.coverPhoto.setScaleMode(0);
    this.mHeader.coverPhoto.setResourceMissing(true);
    enableCoverPhotoChangePhotoIcon(paramBoolean);
    if (paramBoolean)
      this.mHeader.coverPhoto.setOnClickListener(this);
  }

  public void setCoverPhotoUrl(String paramString, int paramInt, boolean paramBoolean)
  {
    if (paramString == null)
      setCoverPhotoToDefault(paramBoolean);
    while (true)
    {
      return;
      this.mHeader.coverPhoto.setMediaRef(new MediaRef(paramString, MediaRef.MediaType.IMAGE));
      this.mHeader.coverPhoto.setTopOffset(paramInt);
      this.mHeader.coverPhoto.setOnClickListener(this);
      enableCoverPhotoChangePhotoIcon(paramBoolean);
    }
  }

  public void setDisplayPolicies(DisplayPolicies paramDisplayPolicies)
  {
    int i = 0;
    this.mPolicy = paramDisplayPolicies;
    Object localObject;
    if (this.mPolicy.hideButtons)
    {
      this.mHeader.buttons.setVisibility(8);
      if (!this.mPolicy.showDetailsAlways)
        break label85;
      this.mHeader.expandArea.setVisibility(8);
      localObject = this.mDetails.container;
    }
    while (true)
    {
      ((View)localObject).setVisibility(i);
      requestLayout();
      return;
      this.mHeader.buttons.setVisibility(0);
      break;
      label85: bindExpandArea();
      View localView = this.mDetails.container;
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

  public void setEducation(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      this.mHeader.education.container.setVisibility(0);
      this.mHeader.education.text.setText(paramString);
      if (this.mPolicy.showInfoIcons)
        this.mHeader.education.icon.setVisibility(0);
    }
    while (true)
    {
      return;
      this.mHeader.education.icon.setVisibility(8);
      continue;
      this.mHeader.education.container.setVisibility(8);
    }
  }

  public void setEmployer(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      this.mHeader.employer.container.setVisibility(0);
      this.mHeader.employer.text.setText(paramString);
      if (this.mPolicy.showInfoIcons)
        this.mHeader.employer.icon.setVisibility(0);
    }
    while (true)
    {
      return;
      this.mHeader.employer.icon.setVisibility(8);
      continue;
      this.mHeader.employer.container.setVisibility(8);
    }
  }

  public void setGender(String paramString)
  {
    if (paramString != null)
    {
      if (this.mDetails.gender == null)
        this.mDetails.gender = this.mDetails.personalSection.findViewById(1000);
      View localView = getLabeledStringView(this.mDetails.personalSection, this.mDetails.gender, R.layout.profile_item_two_line, R.string.profile_item_gender, paramString);
      if (this.mDetails.gender == null)
      {
        this.mDetails.gender = localView;
        this.mDetails.gender.setId(1000);
        this.mDetails.personalSection.addView(localView);
      }
      this.mDetails.gender.setVisibility(0);
    }
    while (true)
    {
      return;
      if (this.mDetails.gender != null)
        this.mDetails.gender.setVisibility(8);
    }
  }

  public void setHomepage(String paramString1, String paramString2, String paramString3)
  {
    View localView = this.mDetails.container.findViewById(R.id.homepage).findViewById(R.id.link_content);
    bindLinkView(localView, paramString2, paramString3, null);
    enableDivider(localView, false);
    localView.setTag(paramString1);
    localView.setOnTouchListener(new ItemOnTouchListener((byte)0));
    localView.setOnClickListener(this);
  }

  public void setIntroduction(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      this.mDetails.introduction.setVisibility(0);
      bindSectionHeader((SectionHeaderView)this.mDetails.introduction.findViewById(R.id.header), R.string.profile_section_introduction, false);
      bindIntroductionView(this.mDetails.introduction.findViewById(R.id.content), paramString);
    }
    while (true)
    {
      return;
      this.mDetails.introduction.setVisibility(8);
    }
  }

  public void setIsExpanded(boolean paramBoolean)
  {
    this.mIsExpanded = paramBoolean;
    bindExpandArea();
  }

  public void setLocalActions(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    View localView1 = this.mDetails.container.findViewById(R.id.local_actions);
    localView1.setVisibility(0);
    LocalActionsItem localLocalActionsItem = new LocalActionsItem(paramString2, paramString1, paramString3, paramString4);
    String str1 = localLocalActionsItem.title;
    String str2 = localLocalActionsItem.mapsCid;
    StringBuilder localStringBuilder1 = new StringBuilder("http://maps.google.com/maps?cid=").append(Uri.encode(str2));
    if (str1 != null)
      localStringBuilder1.append("&q=").append(Uri.encode(str1));
    String str3 = localStringBuilder1.toString();
    String str4 = localLocalActionsItem.title;
    String str5 = localLocalActionsItem.address;
    StringBuilder localStringBuilder2 = new StringBuilder("http://maps.google.com/maps?daddr=");
    if (str4 != null)
      localStringBuilder2.append(Uri.encode(str4)).append(", ");
    localStringBuilder2.append(Uri.encode(str5));
    String str6 = localStringBuilder2.toString();
    View localView2 = localView1.findViewById(R.id.map_button);
    View localView3 = localView1.findViewById(R.id.directions_button);
    View localView4 = localView1.findViewById(R.id.vertical_divider_call);
    View localView5 = localView1.findViewById(R.id.call_button);
    localView2.setTag(str3);
    localView2.setOnClickListener(this);
    localView3.setTag(str6);
    localView3.setOnClickListener(this);
    if (!TextUtils.isEmpty(localLocalActionsItem.phone))
    {
      localView4.setVisibility(0);
      localView5.setVisibility(0);
      localView5.setTag(localLocalActionsItem.phone);
      localView5.setOnClickListener(this);
    }
    while (true)
    {
      return;
      localView4.setVisibility(8);
      localView5.setVisibility(8);
    }
  }

  public void setLocalDetails(List<String> paramList, String paramString1, String paramString2, String paramString3)
  {
    View localView1 = this.mDetails.container.findViewById(R.id.local_details).findViewById(R.id.local_details_content);
    LocalDetailsItem localLocalDetailsItem = new LocalDetailsItem(paramList, paramString1, paramString2, paramString3);
    View localView2 = localView1.findViewById(R.id.known_for_terms_row);
    View localView3 = localView1.findViewById(R.id.phone_row);
    View localView4 = localView1.findViewById(R.id.open_hours_row);
    StringBuilder localStringBuilder = new StringBuilder();
    if (!localLocalDetailsItem.knownForTerms.isEmpty())
    {
      Iterator localIterator = localLocalDetailsItem.knownForTerms.iterator();
      for (int i = 1; localIterator.hasNext(); i = i)
      {
        String str2 = (String)localIterator.next();
        if (!TextUtils.isEmpty(str2))
        {
          if (i == 0)
            localStringBuilder.append(" · ");
          i = 0;
          localStringBuilder.append(str2);
        }
      }
    }
    label230: String str1;
    if (localStringBuilder.length() > 0)
    {
      localView2.setVisibility(0);
      ((TextView)localView2.findViewById(R.id.known_for_terms_value)).setText(localStringBuilder);
      if (TextUtils.isEmpty(localLocalDetailsItem.phone))
        break label289;
      localView3.setVisibility(0);
      ((TextView)localView3.findViewById(R.id.phone_value)).setText(localLocalDetailsItem.phone);
      if (TextUtils.isEmpty(localLocalDetailsItem.openingHoursFull))
        break label299;
      str1 = localLocalDetailsItem.openingHoursFull;
      label248: if (TextUtils.isEmpty(str1))
        break label309;
      localView4.setVisibility(0);
      ((TextView)localView4.findViewById(R.id.open_hours_value)).setText(str1);
    }
    while (true)
    {
      return;
      localView2.setVisibility(8);
      break;
      label289: localView3.setVisibility(8);
      break label230;
      label299: str1 = localLocalDetailsItem.openingHoursSummary;
      break label248;
      label309: localView4.setVisibility(8);
    }
  }

  public void setLocalEditorialReviews(ZagatAspectRatingsProto paramZagatAspectRatingsProto, String paramString1, String paramString2, String paramString3, int paramInt)
  {
    View localView1 = this.mDetails.container.findViewById(R.id.zagat);
    LocalEditorialReviewItem localLocalEditorialReviewItem = new LocalEditorialReviewItem(paramZagatAspectRatingsProto, paramString1, paramString2, paramString3, paramInt);
    boolean bool = "ZAGAT_OFFICIAL".equals(localLocalEditorialReviewItem.scores.source);
    int i = localLocalEditorialReviewItem.scores.aspectRating.size();
    View localView2 = localView1.findViewById(R.id.zagat_logo);
    View localView3 = localView1.findViewById(R.id.user_rated_logo);
    View[] arrayOfView = new View[4];
    arrayOfView[0] = localView1.findViewById(R.id.rating_item_1);
    arrayOfView[1] = localView1.findViewById(R.id.rating_item_2);
    arrayOfView[2] = localView1.findViewById(R.id.rating_item_3);
    arrayOfView[3] = localView1.findViewById(R.id.rating_item_4);
    TextView localTextView1 = (TextView)localView1.findViewById(R.id.zagat_editorial_text);
    TextView localTextView2 = (TextView)localView1.findViewById(R.id.review_count_and_price);
    int j;
    if (bool)
    {
      localView2.setVisibility(0);
      localView3.setVisibility(8);
      j = 0;
      label183: if (j >= 4)
        break label400;
      if (j >= i)
        break label303;
      arrayOfView[j].setVisibility(0);
      ((TextView)arrayOfView[j].findViewById(R.id.rating_label)).setText(((ZagatAspectRatingProto)localLocalEditorialReviewItem.scores.aspectRating.get(j)).labelDisplay);
      ((TextView)arrayOfView[j].findViewById(R.id.rating_value)).setText(((ZagatAspectRatingProto)localLocalEditorialReviewItem.scores.aspectRating.get(j)).valueDisplay);
    }
    while (true)
    {
      j++;
      break label183;
      localView2.setVisibility(8);
      localView3.setVisibility(0);
      break;
      label303: if ((j == i) && (bool) && (localLocalEditorialReviewItem.priceLabel != null) && (localLocalEditorialReviewItem.priceValue != null))
      {
        arrayOfView[j].setVisibility(0);
        ((TextView)arrayOfView[j].findViewById(R.id.rating_label)).setText(localLocalEditorialReviewItem.priceLabel);
        ((TextView)arrayOfView[j].findViewById(R.id.rating_value)).setText(localLocalEditorialReviewItem.priceValue);
      }
      else
      {
        arrayOfView[j].setVisibility(8);
      }
    }
    label400: int k;
    if ((localLocalEditorialReviewItem.priceValue != null) && (localLocalEditorialReviewItem.priceLabel == null))
    {
      k = 1;
      if ((!bool) || (k == 0))
        break label540;
      localTextView2.setVisibility(0);
      localTextView2.setText(localLocalEditorialReviewItem.priceValue);
      label445: if (TextUtils.isEmpty(localLocalEditorialReviewItem.editorialText))
        break label693;
      localTextView1.setVisibility(0);
      SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
      SpannableUtils.appendWithSpan(localSpannableStringBuilder, getString(R.string.profile_local_from_zagat), new TextAppearanceSpan(getContext(), R.style.ProfileLocalEditorialRating_FromZagat));
      localSpannableStringBuilder.append(" ").append(localLocalEditorialReviewItem.editorialText);
      localTextView1.setText(localSpannableStringBuilder);
    }
    while (true)
    {
      localView1.findViewById(R.id.zagat_explanation).setOnClickListener(this);
      return;
      k = 0;
      break;
      label540: if ((!bool) && ((localLocalEditorialReviewItem.reviewCount > 0) || (k != 0)))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        if (localLocalEditorialReviewItem.reviewCount > 0)
        {
          Resources localResources = getContext().getResources();
          int m = R.plurals.profile_local_review_count;
          int n = localLocalEditorialReviewItem.reviewCount;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Integer.valueOf(localLocalEditorialReviewItem.reviewCount);
          localStringBuilder.append(localResources.getQuantityString(m, n, arrayOfObject));
        }
        if (k != 0)
        {
          if (localStringBuilder.length() > 0)
            localStringBuilder.append(" · ");
          localStringBuilder.append(localLocalEditorialReviewItem.priceValue);
        }
        localTextView2.setVisibility(0);
        localTextView2.setText(localStringBuilder.toString());
        break label445;
      }
      localTextView2.setVisibility(8);
      break label445;
      label693: localTextView1.setVisibility(8);
    }
  }

  public void setLocation(String paramString, boolean paramBoolean)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      this.mHeader.location.container.setVisibility(0);
      this.mHeader.location.text.setText(paramString);
      if (paramBoolean)
      {
        this.mHeader.location.text.setSingleLine(true);
        if (!this.mPolicy.showInfoIcons)
          break label125;
        this.mHeader.location.icon.setVisibility(0);
      }
    }
    while (true)
    {
      return;
      this.mHeader.location.text.setSingleLine(false);
      this.mHeader.location.text.setMaxLines(2);
      this.mHeader.location.text.setEllipsize(TextUtils.TruncateAt.MARQUEE);
      break;
      label125: this.mHeader.location.icon.setVisibility(8);
      continue;
      this.mHeader.location.container.setVisibility(8);
    }
  }

  public void setLocationUrl(String paramString)
  {
    ImageResourceView localImageResourceView = this.mDetails.map;
    if (paramString != null)
    {
      localImageResourceView.setMediaRef(new MediaRef(paramString, MediaRef.MediaType.IMAGE));
      localImageResourceView.setVisibility(0);
    }
    while (true)
    {
      return;
      localImageResourceView.setVisibility(8);
    }
  }

  public void setName(String paramString1, String paramString2, String paramString3)
  {
    int i = 8;
    int j;
    label37: int k;
    label54: int m;
    label79: TextView localTextView2;
    if ((TextUtils.isEmpty(paramString2)) && (TextUtils.isEmpty(paramString3)))
    {
      j = 1;
      if (j == 0)
        break label114;
      this.mHeader.fullName.setText(paramString1);
      ConstrainedTextView localConstrainedTextView = this.mHeader.fullName;
      if (j == 0)
        break label180;
      k = 0;
      localConstrainedTextView.setVisibility(k);
      TextView localTextView1 = this.mHeader.givenName;
      if (j == 0)
        break label187;
      m = i;
      localTextView1.setVisibility(m);
      localTextView2 = this.mHeader.familyName;
      if (j == 0)
        break label193;
    }
    while (true)
    {
      localTextView2.setVisibility(i);
      return;
      j = 0;
      break;
      label114: if ((paramString1 != null) && (paramString3 != null) && (paramString1.startsWith(paramString3)))
      {
        this.mHeader.givenName.setText(paramString3);
        this.mHeader.familyName.setText(paramString2);
        break label37;
      }
      this.mHeader.givenName.setText(paramString2);
      this.mHeader.familyName.setText(paramString3);
      break label37;
      label180: k = i;
      break label54;
      label187: m = 0;
      break label79;
      label193: i = 0;
    }
  }

  public void setNoEducationLocations()
  {
    clearEducationLocations();
    this.mDetails.educationSection.findViewById(R.id.no_items).setVisibility(0);
  }

  public void setNoEmploymentLocations()
  {
    clearEmploymentLocations();
    this.mDetails.workSection.findViewById(R.id.no_items).setVisibility(0);
  }

  public void setNoLocations()
  {
    clearLocations();
    this.mDetails.locationsSection.findViewById(R.id.no_items).setVisibility(0);
  }

  public void setOnClickListener(OnClickListener paramOnClickListener)
  {
    this.mOnClickListener = paramOnClickListener;
  }

  public void setPlusOneData(String paramString, boolean paramBoolean)
  {
    Button localButton = this.mHeader.plusOneButton;
    if (paramString != null)
    {
      localButton.setText(paramString);
      if (paramBoolean)
      {
        localButton.setTextColor(sPlusOnedByMeTextColor);
        localButton.setBackgroundResource(R.drawable.plusone_by_me_button);
        localButton.setVisibility(0);
      }
    }
    while (true)
    {
      return;
      localButton.setTextColor(sPlusOneStandardTextColor);
      localButton.setBackgroundResource(R.drawable.plusone_button);
      break;
      localButton.setVisibility(8);
    }
  }

  public void setScrapbookAlbumUrls(Long paramLong, String[] paramArrayOfString, boolean paramBoolean)
  {
    this.mHeader.coverPhoto.setVisibility(8);
    this.mHeader.scrapbookAlbum.setVisibility(0);
    this.mHeader.scrapbookAlbum.setTag(paramLong);
    int i = Math.min(paramArrayOfString.length, 5);
    for (int j = 0; j < i; j++)
    {
      this.mHeader.scrapbookPhoto[j].setMediaRef(new MediaRef(paramArrayOfString[j], MediaRef.MediaType.IMAGE));
      this.mHeader.scrapbookPhoto[j].setOnClickListener(this);
    }
    enableCoverPhotoChangePhotoIcon(paramBoolean);
  }

  public void setTagLine(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      this.mDetails.tagLine.setVisibility(0);
      bindSectionHeader((SectionHeaderView)this.mDetails.tagLine.findViewById(R.id.header), R.string.profile_section_tagline, false);
      bindIntroductionView(this.mDetails.tagLine.findViewById(R.id.content), paramString);
    }
    while (true)
    {
      return;
      this.mDetails.tagLine.setVisibility(8);
    }
  }

  public final boolean shouldHighlightOnPress()
  {
    return false;
  }

  public final void showAddToCircles(boolean paramBoolean)
  {
    this.mHeader.circlesButton.setVisibility(8);
    this.mHeader.addToCirclesButton.setVisibility(0);
    if (paramBoolean)
      this.mHeader.addToCirclesButton.setText(getString(R.string.follow));
    while (true)
    {
      this.mHeader.blockedText.setVisibility(8);
      this.mHeader.progressBar.setVisibility(8);
      return;
      this.mHeader.addToCirclesButton.setText(getString(R.string.add_to_circles));
    }
  }

  public final void showBlocked()
  {
    this.mHeader.circlesButton.setVisibility(8);
    this.mHeader.addToCirclesButton.setVisibility(8);
    this.mHeader.blockedText.setVisibility(0);
    this.mHeader.progressBar.setVisibility(8);
  }

  public final void showError(boolean paramBoolean, String paramString)
  {
    if (paramBoolean)
    {
      this.mHeader.container.setVisibility(8);
      this.mDetails.container.setVisibility(8);
      this.mProfileLayout.error.setVisibility(0);
      this.mProfileLayout.error.setText(paramString);
    }
    while (true)
    {
      return;
      this.mHeader.container.setVisibility(0);
      this.mProfileLayout.error.setVisibility(8);
    }
  }

  public final void showNone()
  {
    this.mHeader.circlesButton.setVisibility(8);
    this.mHeader.addToCirclesButton.setVisibility(8);
    this.mHeader.blockedText.setVisibility(8);
    this.mHeader.progressBar.setVisibility(8);
  }

  public final void showProgress()
  {
    this.mHeader.circlesButton.setVisibility(8);
    this.mHeader.addToCirclesButton.setVisibility(8);
    this.mHeader.blockedText.setVisibility(8);
    this.mHeader.progressBar.setVisibility(0);
  }

  public final void updateContactSectionDividers()
  {
    int i = this.mDetails.addresses.getChildCount();
    View localView = null;
    if (i > 0)
      localView = this.mDetails.addresses.getChildAt(i - 1);
    if (localView == null)
    {
      int k = this.mDetails.phoneNumbers.getChildCount();
      if (k > 0)
        localView = this.mDetails.phoneNumbers.getChildAt(k - 1);
    }
    if (localView == null)
    {
      int j = this.mDetails.emails.getChildCount();
      if (j > 0)
        localView = this.mDetails.emails.getChildAt(j - 1);
    }
    if (localView != null)
      enableDivider(localView, false);
  }

  public final void updateLinksSectionDividers()
  {
    updateGenericListSectionDividers(this.mDetails.links);
  }

  public final void updateLocationsSectionDividers()
  {
    updateGenericListSectionDividers(this.mDetails.locations);
  }

  public final void updatePersonalSectionDividers()
  {
    View localView1 = this.mDetails.gender;
    View localView2 = null;
    if (localView1 != null)
    {
      int i = this.mDetails.gender.getVisibility();
      localView2 = null;
      if (i != 8)
      {
        enableDivider(this.mDetails.gender, true);
        localView2 = this.mDetails.gender;
      }
    }
    if ((this.mDetails.birthday != null) && (this.mDetails.birthday.getVisibility() != 8))
    {
      enableDivider(this.mDetails.birthday, true);
      localView2 = this.mDetails.birthday;
    }
    if (localView2 != null)
      enableDivider(localView2, false);
  }

  private static final class DetailsLayout
  {
    public ViewGroup addresses;
    public View birthday;
    public ViewGroup contactSection;
    public View container;
    public ViewGroup educationSection;
    public View educationSectionLastLocation;
    public ViewGroup emails;
    public View gender;
    public View introduction;
    public ViewGroup links;
    public ViewGroup linksSection;
    public ViewGroup locations;
    public ViewGroup locationsSection;
    public ImageResourceView map;
    public ViewGroup personalSection;
    public ViewGroup phoneNumbers;
    public View tagLine;
    public ViewGroup workSection;
    public View workSectionLastLocation;
  }

  public static final class DisplayPolicies
  {
    public boolean hideButtons = false;
    public boolean showDetailsAlways = false;
    public boolean showExpandButtonText = false;
    public boolean showInfoIcons = true;
  }

  private static final class HeaderLayout
  {
    public CirclesButton addToCirclesButton;
    public TextView addedByCount;
    public ImageView avatarChoosePhotoIcon;
    public ImageResourceView avatarImage;
    public TextView blockedText;
    public View buttons;
    public CirclesButton circlesButton;
    public View container;
    public CoverPhotoImageView coverPhoto;
    public ImageView coverPhotoChoosePhotoIcon;
    public ProfileAboutView.InfoRow education;
    public ProfileAboutView.InfoRow employer;
    public TextView expandArea;
    public TextView familyName;
    public ConstrainedTextView fullName;
    public TextView givenName;
    public ProfileAboutView.InfoRow location;
    public Button plusOneButton;
    public ProgressBar progressBar;
    public View scrapbookAlbum;
    public ImageResourceView[] scrapbookPhoto = new ImageResourceView[5];
  }

  private static final class InfoRow
  {
    View container;
    public ImageView icon;
    public TextView text;

    public InfoRow(View paramView, int paramInt)
    {
      this.container = paramView.findViewById(paramInt);
      this.icon = ((ImageView)this.container.findViewById(R.id.icon));
      this.text = ((TextView)this.container.findViewById(R.id.text));
    }
  }

  public final class IntroductionTagHandler
    implements Html.TagHandler
  {
    private Stack<Integer> mListStack;

    public IntroductionTagHandler()
    {
    }

    private void handleListTag(boolean paramBoolean1, Editable paramEditable, boolean paramBoolean2)
    {
      if (this.mListStack == null)
        this.mListStack = new Stack();
      int i;
      if (paramBoolean1)
      {
        if ((paramEditable.length() == 0) || (paramEditable.charAt(-1 + paramEditable.length()) != '\n'))
          paramEditable.append("\n");
        Stack localStack = this.mListStack;
        if (paramBoolean2)
        {
          i = 0;
          localStack.push(Integer.valueOf(i));
        }
      }
      while (true)
      {
        return;
        i = -1;
        break;
        if (!this.mListStack.isEmpty())
          this.mListStack.pop();
      }
    }

    public final void handleTag(boolean paramBoolean, String paramString, Editable paramEditable, XMLReader paramXMLReader)
    {
      boolean bool1 = true;
      if ("ul".equals(paramString))
        handleListTag(paramBoolean, paramEditable, false);
      while (true)
      {
        return;
        if ("ol".equals(paramString))
        {
          handleListTag(paramBoolean, paramEditable, bool1);
        }
        else if ("li".equals(paramString))
        {
          if (paramBoolean)
          {
            if (this.mListStack == null);
            while (true)
            {
              for (boolean bool2 = false; bool2 < bool1; bool2++)
                paramEditable.append("  ");
              int i = this.mListStack.size();
            }
            if ((this.mListStack == null) || (this.mListStack.isEmpty()) || (((Integer)this.mListStack.peek()).intValue() == -1));
            int j;
            for (String str = "• "; ; str = j + ". ")
            {
              paramEditable.append(str);
              break;
              j = 1 + ((Integer)this.mListStack.pop()).intValue();
              this.mListStack.push(Integer.valueOf(j));
            }
          }
          paramEditable.append("\n");
        }
      }
    }
  }

  private static class Item
  {
  }

  private final class ItemOnTouchListener
    implements View.OnTouchListener
  {
    volatile MotionEvent mLastEvent;

    private ItemOnTouchListener()
    {
    }

    public final boolean onTouch(final View paramView, final MotionEvent paramMotionEvent)
    {
      switch (paramMotionEvent.getAction())
      {
      case 2:
      default:
      case 0:
      case 1:
      case 3:
      }
      while (true)
      {
        return false;
        this.mLastEvent = paramMotionEvent;
        ProfileAboutView.this.postDelayed(new Runnable()
        {
          public final void run()
          {
            if (ProfileAboutView.ItemOnTouchListener.this.mLastEvent == paramMotionEvent)
              paramView.setBackgroundColor(-3355444);
            ProfileAboutView.ItemOnTouchListener.this.mLastEvent = null;
          }
        }
        , 100L);
        continue;
        this.mLastEvent = null;
        paramView.setBackgroundColor(0);
      }
    }
  }

  private static final class LocalActionsItem extends ProfileAboutView.Item
  {
    final String address;
    final String mapsCid;
    final String phone;
    final String title;

    public LocalActionsItem(String paramString1, String paramString2, String paramString3, String paramString4)
    {
      super();
      this.phone = paramString1;
      this.title = paramString2;
      this.mapsCid = paramString3;
      this.address = paramString4;
    }
  }

  private static final class LocalDetailsItem extends ProfileAboutView.Item
  {
    final List<String> knownForTerms;
    final String openingHoursFull;
    final String openingHoursSummary;
    final String phone;

    public LocalDetailsItem(List<String> paramList, String paramString1, String paramString2, String paramString3)
    {
      super();
      this.phone = paramString1;
      this.knownForTerms = paramList;
      this.openingHoursSummary = paramString2;
      this.openingHoursFull = paramString3;
    }
  }

  private static final class LocalEditorialReviewItem extends ProfileAboutView.Item
  {
    final String editorialText;
    final String priceLabel;
    final String priceValue;
    final int reviewCount;
    final ZagatAspectRatingsProto scores;

    public LocalEditorialReviewItem(ZagatAspectRatingsProto paramZagatAspectRatingsProto, String paramString1, String paramString2, String paramString3, int paramInt)
    {
      super();
      this.scores = paramZagatAspectRatingsProto;
      this.editorialText = paramString1;
      this.priceLabel = paramString2;
      this.priceValue = paramString3;
      this.reviewCount = paramInt;
    }
  }

  private static final class LocationItem extends ProfileAboutView.Item
  {
    final String address;
    final boolean current;

    public LocationItem(String paramString, boolean paramBoolean)
    {
      super();
      this.address = paramString;
      this.current = paramBoolean;
    }
  }

  public static abstract interface OnClickListener
  {
    public abstract void onAddressClicked(String paramString);

    public abstract void onAvatarClicked();

    public abstract void onCirclesButtonClicked();

    public abstract void onCoverPhotoClicked(int paramInt);

    public abstract void onEditEducationClicked();

    public abstract void onEditEmploymentClicked();

    public abstract void onEditPlacesLivedClicked();

    public abstract void onEmailClicked(String paramString);

    public abstract void onExpandClicked(boolean paramBoolean);

    public abstract void onLinkClicked(String paramString);

    public abstract void onLocalCallClicked(String paramString);

    public abstract void onLocalDirectionsClicked(String paramString);

    public abstract void onLocalMapClicked(String paramString);

    public abstract void onLocalReviewClicked(int paramInt1, int paramInt2);

    public abstract void onLocationClicked(String paramString);

    public abstract void onPhoneNumberClicked(String paramString);

    public abstract void onPlusOneClicked();

    public abstract void onReviewAuthorAvatarClicked(String paramString);

    public abstract void onSendTextClicked(String paramString);

    public abstract void onZagatExplanationClicked();
  }

  private static final class ProfileLayout
  {
    private static final int[] SCRAPBOOK_PHOTO_IDS = arrayOfInt;
    public ProfileAboutView.DetailsLayout details = new ProfileAboutView.DetailsLayout((byte)0);
    public TextView error;
    public ProfileAboutView.HeaderLayout header = new ProfileAboutView.HeaderLayout((byte)0);

    static
    {
      int[] arrayOfInt = new int[5];
      arrayOfInt[0] = R.id.photo_1;
      arrayOfInt[1] = R.id.photo_2;
      arrayOfInt[2] = R.id.photo_3;
      arrayOfInt[3] = R.id.photo_4;
      arrayOfInt[4] = R.id.photo_5;
    }

    public ProfileLayout(View paramView)
    {
      this.error = ((TextView)paramView.findViewById(R.id.server_error));
      View localView1 = paramView.findViewById(R.id.header);
      this.header.container = localView1;
      this.header.coverPhoto = ((CoverPhotoImageView)localView1.findViewById(R.id.cover_photo_image));
      this.header.coverPhotoChoosePhotoIcon = ((ImageView)localView1.findViewById(R.id.choose_cover_photo_icon));
      this.header.coverPhoto.setResourceLoadingDrawable(R.drawable.profile_scrapbook_loading);
      this.header.coverPhoto.setResourceMissingDrawable(R.drawable.default_cover_photo);
      this.header.scrapbookAlbum = localView1.findViewById(R.id.scrapbook_album);
      for (int i = 0; i < 5; i++)
      {
        this.header.scrapbookPhoto[i] = ((ImageResourceView)this.header.scrapbookAlbum.findViewById(SCRAPBOOK_PHOTO_IDS[i]));
        this.header.scrapbookPhoto[i].setSizeCategory(2);
        this.header.scrapbookPhoto[i].setResourceLoadingDrawable(R.drawable.profile_scrapbook_loading);
        this.header.scrapbookPhoto[i].setResourceMissingDrawable(R.drawable.profile_scrapbook_loading);
      }
      this.header.avatarImage = ((ImageResourceView)localView1.findViewById(R.id.avatar_image));
      this.header.avatarImage.setResourceLoadingDrawable(R.drawable.profile_avatar_loading);
      this.header.avatarImage.setResourceMissingDrawable(new BitmapDrawable(paramView.getResources(), EsAvatarData.getMediumDefaultAvatar(paramView.getContext())));
      this.header.avatarChoosePhotoIcon = ((ImageView)localView1.findViewById(R.id.choose_photo_icon));
      this.header.addedByCount = ((TextView)localView1.findViewById(R.id.added_by_count));
      this.header.fullName = ((ConstrainedTextView)localView1.findViewById(R.id.full_name));
      this.header.givenName = ((TextView)localView1.findViewById(R.id.given_name));
      this.header.familyName = ((TextView)localView1.findViewById(R.id.family_name));
      this.header.employer = new ProfileAboutView.InfoRow(localView1, R.id.employer);
      this.header.education = new ProfileAboutView.InfoRow(localView1, R.id.education);
      this.header.location = new ProfileAboutView.InfoRow(localView1, R.id.location);
      this.header.buttons = localView1.findViewById(R.id.buttons);
      this.header.circlesButton = ((CirclesButton)localView1.findViewById(R.id.circles_button));
      this.header.addToCirclesButton = ((CirclesButton)localView1.findViewById(R.id.add_to_circles_button));
      this.header.addToCirclesButton.setShowIcon(false);
      this.header.blockedText = ((TextView)localView1.findViewById(R.id.blocked));
      this.header.progressBar = ((ProgressBar)localView1.findViewById(R.id.progress_bar));
      this.header.plusOneButton = ((Button)localView1.findViewById(R.id.plus_one));
      this.header.plusOneButton.setTextSize(0, ProfileAboutView.sPlusOneTextSize);
      this.header.plusOneButton.setTypeface(Typeface.DEFAULT_BOLD);
      this.header.expandArea = ((TextView)localView1.findViewById(R.id.expand));
      View localView2 = paramView.findViewById(R.id.details);
      this.details.container = localView2;
      this.details.tagLine = localView2.findViewById(R.id.tagline);
      this.details.introduction = localView2.findViewById(R.id.intro);
      this.details.contactSection = ((ViewGroup)localView2.findViewById(R.id.contact));
      this.details.emails = ((ViewGroup)this.details.contactSection.findViewById(R.id.email_content));
      this.details.phoneNumbers = ((ViewGroup)this.details.contactSection.findViewById(R.id.phone_content));
      this.details.addresses = ((ViewGroup)this.details.contactSection.findViewById(R.id.address_content));
      this.details.personalSection = ((ViewGroup)localView2.findViewById(R.id.personal));
      this.details.workSection = ((ViewGroup)localView2.findViewById(R.id.work_section));
      this.details.educationSection = ((ViewGroup)localView2.findViewById(R.id.education));
      this.details.locationsSection = ((ViewGroup)localView2.findViewById(R.id.places));
      this.details.map = ((ImageResourceView)this.details.locationsSection.findViewById(R.id.map));
      this.details.locations = ((ViewGroup)this.details.locationsSection.findViewById(R.id.content));
      this.details.linksSection = ((ViewGroup)localView2.findViewById(R.id.links));
      this.details.links = ((ViewGroup)this.details.linksSection.findViewById(R.id.link_content));
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.ProfileAboutView
 * JD-Core Version:    0.6.2
 */