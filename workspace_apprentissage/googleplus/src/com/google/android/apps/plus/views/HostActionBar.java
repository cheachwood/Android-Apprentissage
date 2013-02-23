package com.google.android.apps.plus.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnDismissListener;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.phone.ScreenMetrics;
import com.google.android.apps.plus.util.AccessibilityUtils;

public class HostActionBar extends RelativeLayout
  implements View.OnClickListener, View.OnLongClickListener, AdapterView.OnItemSelectedListener
{
  private static Handler sHandler;
  private ImageView mActionButton1;
  private boolean mActionButton1Visible;
  private ImageView mActionButton2;
  private boolean mActionButton2Visible;
  private int mActionId1;
  private int mActionId2;
  private boolean mActive;
  private ImageView mAppIcon;
  private boolean mContextActionMode;
  private String mCurrentButtonActionText;
  private SpinnerAdapter mDefaultPrimarySpinnerAdapter;
  private View mDoneButton;
  private OnDoneButtonClickListener mDoneButtonListener;
  private Runnable mInvalidateActionBarRunnable = new Runnable()
  {
    public final void run()
    {
      if (HostActionBar.this.mListener != null)
        HostActionBar.this.mListener.onActionBarInvalidated();
    }
  };
  private HostActionBarListener mListener;
  private int mNotificationCount;
  private View mNotificationCountOverflow;
  private TextView mNotificationCountText;
  private View mOverflowMenuButton;
  private Object mOverflowPopupMenu;
  private boolean mOverflowPopupMenuVisible;
  private Spinner mPrimarySpinner;
  private View mPrimarySpinnerContainer;
  private boolean mPrimarySpinnerVisible;
  private View mProgressIndicator;
  private boolean mProgressIndicatorVisible;
  private ImageView mRefreshButton;
  private boolean mRefreshButtonVisible;
  private boolean mRefreshButtonVisibleIfRoom;
  private boolean mRefreshHighlighted;
  private SearchViewAdapter mSearchViewAdapter;
  private View mSearchViewContainer;
  private boolean mSearchViewVisible;
  private View mShareMenuButton;
  private boolean mShareMenuVisible;
  private Object mSharePopupMenu;
  private boolean mSharePopupMenuVisible;
  private TextView mTitle;
  private boolean mTitleVisible;
  private View mUpButton;
  private OnUpButtonClickListener mUpButtonListener;

  public HostActionBar(Context paramContext)
  {
    super(paramContext);
  }

  public HostActionBar(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public HostActionBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  private void configurePopupMenuListeners(Object paramObject)
  {
    Object localObject;
    if (Build.VERSION.SDK_INT >= 14)
    {
      localObject = new PopupMenuListenerV14((byte)0);
      ((PopupMenu)paramObject).setOnDismissListener((PopupMenuListenerV14)localObject);
    }
    while (true)
    {
      ((PopupMenu)paramObject).setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener)localObject);
      return;
      localObject = new PopupMenuListener((byte)0);
    }
  }

  private Object getOverflowPopupMenu()
  {
    Object localObject;
    if (this.mOverflowPopupMenu != null)
      localObject = this.mOverflowPopupMenu;
    while (true)
    {
      return localObject;
      localObject = new PopupMenu(getContext(), this.mOverflowMenuButton);
      configurePopupMenuListeners(localObject);
      ((Activity)getContext()).onCreateOptionsMenu(((PopupMenu)localObject).getMenu());
      this.mOverflowPopupMenu = localObject;
    }
  }

  private boolean isOverflowMenuSupported()
  {
    boolean bool = true;
    if (Build.VERSION.SDK_INT >= 14)
      if (ViewConfiguration.get(getContext()).hasPermanentMenuKey());
    while (true)
    {
      return bool;
      bool = false;
      continue;
      if (Build.VERSION.SDK_INT < 11)
        bool = false;
    }
  }

  private boolean prepareOverflowMenu()
  {
    boolean bool;
    if (this.mOverflowPopupMenu == null)
    {
      bool = false;
      return bool;
    }
    Menu localMenu = ((PopupMenu)this.mOverflowPopupMenu).getMenu();
    ((Activity)getContext()).onPrepareOptionsMenu(localMenu);
    int i = localMenu.size();
    for (int j = 0; ; j++)
    {
      bool = false;
      if (j >= i)
        break;
      if (localMenu.getItem(j).isVisible())
      {
        bool = true;
        break;
      }
    }
  }

  private boolean prepareSharePopupMenu()
  {
    boolean bool;
    if (this.mSharePopupMenu == null)
      bool = false;
    while (true)
    {
      return bool;
      Menu localMenu = ((PopupMenu)this.mSharePopupMenu).getMenu();
      ((Activity)getContext()).onPrepareOptionsMenu(localMenu);
      bool = false;
      int i = localMenu.size();
      for (int j = 0; j < i; j++)
      {
        MenuItem localMenuItem = localMenu.getItem(j);
        localMenuItem.getItemId();
        if (null.length < 0)
          throw new NullPointerException();
        if (0 == 0)
        {
          bool = true;
          localMenuItem.setVisible(false);
        }
      }
    }
  }

  private void showOverflowMenu()
  {
    this.mSharePopupMenuVisible = false;
    PopupMenu localPopupMenu = (PopupMenu)getOverflowPopupMenu();
    prepareOverflowMenu();
    this.mOverflowPopupMenuVisible = true;
    localPopupMenu.show();
  }

  private void showSharePopupMenu()
  {
    this.mOverflowPopupMenuVisible = false;
    Object localObject;
    if (this.mSharePopupMenu != null)
      localObject = this.mSharePopupMenu;
    while (true)
    {
      PopupMenu localPopupMenu = (PopupMenu)localObject;
      if (prepareSharePopupMenu())
      {
        this.mSharePopupMenuVisible = true;
        localPopupMenu.show();
      }
      return;
      localObject = new PopupMenu(getContext(), this.mShareMenuButton);
      configurePopupMenuListeners(localObject);
      ((Activity)getContext()).onCreateOptionsMenu(((PopupMenu)localObject).getMenu());
      this.mSharePopupMenu = localObject;
    }
  }

  private boolean showTooltip(View paramView, CharSequence paramCharSequence)
  {
    int[] arrayOfInt = new int[2];
    Rect localRect = new Rect();
    paramView.getLocationOnScreen(arrayOfInt);
    paramView.getWindowVisibleDisplayFrame(localRect);
    Context localContext = getContext();
    int i = paramView.getWidth();
    int j = paramView.getHeight();
    int k = localContext.getResources().getDisplayMetrics().widthPixels;
    Toast localToast = Toast.makeText(localContext, paramCharSequence, 0);
    localToast.setGravity(53, k - arrayOfInt[0] - i / 2, j);
    localToast.show();
    return true;
  }

  public final void commit()
  {
    View localView1 = this.mUpButton;
    int i;
    int j;
    label35: int k;
    label57: int m;
    label95: int n;
    label136: int i1;
    label177: int i2;
    label208: int i3;
    label236: int i4;
    label259: int i5;
    if (this.mContextActionMode)
    {
      i = 8;
      localView1.setVisibility(i);
      View localView2 = this.mDoneButton;
      if (!this.mContextActionMode)
        break label328;
      j = 0;
      localView2.setVisibility(j);
      TextView localTextView = this.mTitle;
      if (!this.mTitleVisible)
        break label335;
      k = 0;
      localTextView.setVisibility(k);
      View localView3 = this.mPrimarySpinnerContainer;
      if ((!this.mPrimarySpinnerVisible) || (this.mPrimarySpinner.getAdapter().getCount() <= 0))
        break label342;
      m = 0;
      localView3.setVisibility(m);
      if (!this.mPrimarySpinnerVisible)
        this.mPrimarySpinner.setAdapter(this.mDefaultPrimarySpinnerAdapter);
      View localView4 = this.mSearchViewContainer;
      if (!this.mSearchViewVisible)
        break label349;
      n = 0;
      localView4.setVisibility(n);
      this.mSearchViewAdapter.setVisible(this.mSearchViewVisible);
      ImageView localImageView1 = this.mRefreshButton;
      if ((!isRefreshButtonVisible()) || (this.mProgressIndicatorVisible))
        break label356;
      i1 = 0;
      localImageView1.setVisibility(i1);
      ImageView localImageView2 = this.mRefreshButton;
      Resources localResources = getResources();
      if (!this.mRefreshHighlighted)
        break label363;
      i2 = R.drawable.ic_refresh_blue;
      localImageView2.setImageDrawable(localResources.getDrawable(i2));
      View localView5 = this.mProgressIndicator;
      if (!this.mProgressIndicatorVisible)
        break label371;
      i3 = 0;
      localView5.setVisibility(i3);
      ImageView localImageView3 = this.mActionButton1;
      if (!this.mActionButton1Visible)
        break label378;
      i4 = 0;
      localImageView3.setVisibility(i4);
      ImageView localImageView4 = this.mActionButton2;
      if (!this.mActionButton2Visible)
        break label385;
      i5 = 0;
      label282: localImageView4.setVisibility(i5);
      this.mShareMenuButton.setVisibility(8);
      if (isOverflowMenuSupported())
      {
        if (!this.mSharePopupMenuVisible)
          break label392;
        prepareSharePopupMenu();
      }
    }
    while (true)
    {
      this.mActive = true;
      return;
      i = 0;
      break;
      label328: j = 8;
      break label35;
      label335: k = 8;
      break label57;
      label342: m = 8;
      break label95;
      label349: n = 8;
      break label136;
      label356: i1 = 8;
      break label177;
      label363: i2 = R.drawable.ic_refresh;
      break label208;
      label371: i3 = 8;
      break label236;
      label378: i4 = 8;
      break label259;
      label385: i5 = 8;
      break label282;
      label392: if (!this.mOverflowPopupMenuVisible)
        break label407;
      prepareOverflowMenu();
    }
    label407: View localView6 = this.mOverflowMenuButton;
    getOverflowPopupMenu();
    boolean bool = prepareOverflowMenu();
    int i6 = 0;
    if (bool);
    while (true)
    {
      localView6.setVisibility(i6);
      break;
      i6 = 8;
    }
  }

  public final void dismissPopupMenus()
  {
    if (this.mOverflowPopupMenu != null)
      ((PopupMenu)this.mOverflowPopupMenu).dismiss();
    if (this.mSharePopupMenu != null)
      ((PopupMenu)this.mSharePopupMenu).dismiss();
  }

  public final void finishContextActionMode()
  {
    if (this.mContextActionMode)
    {
      this.mContextActionMode = false;
      if (this.mActive)
      {
        this.mUpButton.setVisibility(0);
        this.mDoneButton.setVisibility(8);
      }
    }
  }

  public final SearchViewAdapter getSearchViewAdapter()
  {
    return this.mSearchViewAdapter;
  }

  public final void hideProgressIndicator()
  {
    this.mProgressIndicatorVisible = false;
    int i;
    int j;
    label55: ImageView localImageView3;
    int k;
    if (this.mActive)
    {
      this.mProgressIndicator.setVisibility(8);
      ImageView localImageView1 = this.mRefreshButton;
      if (!isRefreshButtonVisible())
        break label89;
      i = 0;
      localImageView1.setVisibility(i);
      ImageView localImageView2 = this.mActionButton1;
      if (!this.mActionButton1Visible)
        break label95;
      j = 0;
      localImageView2.setVisibility(j);
      localImageView3 = this.mActionButton2;
      boolean bool = this.mActionButton2Visible;
      k = 0;
      if (!bool)
        break label102;
    }
    while (true)
    {
      localImageView3.setVisibility(k);
      return;
      label89: i = 8;
      break;
      label95: j = 8;
      break label55;
      label102: k = 8;
    }
  }

  public final void invalidateActionBar()
  {
    if (sHandler == null)
      sHandler = new Handler(Looper.getMainLooper());
    sHandler.removeCallbacks(this.mInvalidateActionBarRunnable);
    sHandler.post(this.mInvalidateActionBarRunnable);
  }

  public final boolean isRefreshButtonVisible()
  {
    boolean bool1 = true;
    if (this.mRefreshButtonVisible);
    while (true)
    {
      return bool1;
      if (this.mRefreshButtonVisibleIfRoom)
      {
        if (this.mActionButton1Visible)
        {
          ScreenMetrics localScreenMetrics = ScreenMetrics.getInstance(getContext());
          if (getResources().getConfiguration().orientation == 2);
          for (boolean bool2 = bool1; ; bool2 = false)
          {
            if ((localScreenMetrics.screenDisplayType != 0) || (bool2))
              break label68;
            bool1 = false;
            break;
          }
        }
      }
      else
        label68: bool1 = false;
    }
  }

  public void onClick(View paramView)
  {
    if (paramView == this.mUpButton)
      if (this.mUpButtonListener != null)
        this.mUpButtonListener.onUpButtonClick();
    while (true)
    {
      return;
      if (paramView == this.mDoneButton)
      {
        if (this.mDoneButtonListener != null)
          this.mDoneButtonListener.onDoneButtonClick();
      }
      else if (paramView == this.mOverflowMenuButton)
        showOverflowMenu();
      else if (paramView == this.mShareMenuButton)
        showSharePopupMenu();
      else if (paramView == this.mRefreshButton)
      {
        if (this.mListener != null)
          this.mListener.onRefreshButtonClicked();
      }
      else if (paramView == this.mActionButton1)
      {
        if (this.mListener != null)
          this.mListener.onActionButtonClicked(this.mActionId1);
      }
      else if ((paramView == this.mActionButton2) && (this.mListener != null))
        this.mListener.onActionButtonClicked(this.mActionId2);
    }
  }

  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mUpButton = findViewById(R.id.up);
    this.mUpButton.setOnClickListener(this);
    this.mDoneButton = findViewById(R.id.done);
    this.mDoneButton.setOnClickListener(this);
    this.mAppIcon = ((ImageView)findViewById(R.id.icon));
    this.mTitle = ((TextView)findViewById(R.id.title));
    this.mPrimarySpinnerContainer = findViewById(R.id.primary_spinner_container);
    this.mPrimarySpinner = ((Spinner)findViewById(R.id.primary_spinner));
    this.mPrimarySpinner.setOnItemSelectedListener(this);
    this.mDefaultPrimarySpinnerAdapter = new ArrayAdapter(getContext(), R.layout.simple_spinner_item);
    this.mPrimarySpinner.setAdapter(this.mDefaultPrimarySpinnerAdapter);
    this.mSearchViewContainer = findViewById(R.id.search_view_container);
    this.mSearchViewAdapter = SearchViewAdapter.createInstance(findViewById(R.id.search_src_text));
    this.mSearchViewAdapter.requestFocus(false);
    this.mShareMenuButton = findViewById(R.id.share_menu_anchor);
    if (isOverflowMenuSupported())
    {
      this.mShareMenuButton.setOnClickListener(this);
      this.mShareMenuButton.setOnLongClickListener(this);
    }
    this.mRefreshButton = ((ImageView)findViewById(R.id.refresh_button));
    this.mRefreshButton.setOnClickListener(this);
    this.mRefreshButton.setOnLongClickListener(this);
    this.mActionButton1 = ((ImageView)findViewById(R.id.action_button_1));
    this.mActionButton1.setOnClickListener(this);
    this.mActionButton1.setOnLongClickListener(this);
    this.mActionButton2 = ((ImageView)findViewById(R.id.action_button_2));
    this.mActionButton2.setOnClickListener(this);
    this.mActionButton2.setOnLongClickListener(this);
    this.mProgressIndicator = findViewById(R.id.progress_indicator);
    this.mNotificationCountText = ((TextView)findViewById(R.id.notification_count));
    this.mNotificationCountText.setText("99");
    this.mNotificationCountText.setVisibility(8);
    this.mNotificationCountOverflow = findViewById(R.id.notification_count_overflow);
    this.mOverflowMenuButton = findViewById(R.id.menu);
    if (isOverflowMenuSupported())
      this.mOverflowMenuButton.setOnClickListener(this);
    while (true)
    {
      this.mCurrentButtonActionText = "";
      return;
      this.mOverflowMenuButton.setVisibility(8);
    }
  }

  public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.mListener != null)
      this.mListener.onPrimarySpinnerSelectionChange(paramInt);
  }

  public boolean onLongClick(View paramView)
  {
    boolean bool = true;
    if (paramView == this.mShareMenuButton)
      showTooltip(paramView, getContext().getString(R.string.share_menu_anchor_content_description));
    while (true)
    {
      return bool;
      if (paramView == this.mRefreshButton)
        showTooltip(paramView, getContext().getString(R.string.menu_refresh));
      else if (paramView == this.mActionButton1)
        showTooltip(paramView, this.mActionButton1.getContentDescription());
      else if (paramView == this.mActionButton2)
        showTooltip(paramView, this.mActionButton2.getContentDescription());
      else
        bool = false;
    }
  }

  public void onNothingSelected(AdapterView<?> paramAdapterView)
  {
  }

  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    if (localSavedState.overflowPopupMenuVisible)
      post(new Runnable()
      {
        public final void run()
        {
          HostActionBar.this.showOverflowMenu();
        }
      });
    if (localSavedState.sharePopupMenuVisible)
      post(new Runnable()
      {
        public final void run()
        {
          HostActionBar.this.showSharePopupMenu();
        }
      });
  }

  public Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    if (Build.VERSION.SDK_INT >= 14)
    {
      localSavedState.overflowPopupMenuVisible = this.mOverflowPopupMenuVisible;
      localSavedState.sharePopupMenuVisible = this.mSharePopupMenuVisible;
    }
    return localSavedState;
  }

  public final void reset()
  {
    this.mActive = false;
    this.mContextActionMode = false;
    this.mTitleVisible = false;
    this.mPrimarySpinnerVisible = false;
    this.mSearchViewVisible = false;
    this.mRefreshButtonVisible = false;
    this.mRefreshButtonVisibleIfRoom = false;
    this.mActionButton1Visible = false;
    this.mActionButton2Visible = false;
    this.mProgressIndicatorVisible = false;
    this.mShareMenuVisible = false;
    this.mRefreshHighlighted = false;
  }

  public void setHostActionBarListener(HostActionBarListener paramHostActionBarListener)
  {
    this.mListener = paramHostActionBarListener;
  }

  public void setNotificationCount(int paramInt)
  {
    this.mNotificationCount = paramInt;
    if (this.mNotificationCount == 0)
    {
      this.mAppIcon.setVisibility(0);
      this.mNotificationCountText.setVisibility(8);
      this.mNotificationCountOverflow.setVisibility(8);
    }
    while (true)
    {
      setUpButtonContentDescription(null);
      return;
      this.mAppIcon.setVisibility(4);
      if (this.mNotificationCount <= 99)
      {
        this.mNotificationCountText.setText(Integer.toString(this.mNotificationCount));
        this.mNotificationCountText.setVisibility(0);
        this.mNotificationCountOverflow.setVisibility(8);
      }
      else
      {
        this.mNotificationCountText.setVisibility(8);
        this.mNotificationCountOverflow.setVisibility(0);
      }
    }
  }

  public void setOnDoneButtonClickListener(OnDoneButtonClickListener paramOnDoneButtonClickListener)
  {
    this.mDoneButtonListener = paramOnDoneButtonClickListener;
  }

  public void setOnUpButtonClickListener(OnUpButtonClickListener paramOnUpButtonClickListener)
  {
    this.mUpButtonListener = paramOnUpButtonClickListener;
  }

  public void setPrimarySpinnerSelection(int paramInt)
  {
    if ((paramInt < this.mPrimarySpinner.getCount()) && (paramInt >= 0))
      this.mPrimarySpinner.setSelection(paramInt);
  }

  public void setUpButtonContentDescription(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (!TextUtils.isEmpty(paramString))
    {
      this.mCurrentButtonActionText = paramString;
      AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, paramString);
    }
    while (true)
    {
      if (this.mNotificationCount > 0)
      {
        Resources localResources = getResources();
        int i = R.plurals.accessibility_notification_count_description;
        int j = this.mNotificationCount;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(this.mNotificationCount);
        AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, localResources.getQuantityString(i, j, arrayOfObject));
      }
      this.mUpButton.setContentDescription(localStringBuilder.toString());
      return;
      AccessibilityUtils.appendAndSeparateIfNotEmpty(localStringBuilder, this.mCurrentButtonActionText);
    }
  }

  public final void showActionButton(int paramInt1, int paramInt2, int paramInt3)
  {
    int j;
    int k;
    if (!this.mActionButton1Visible)
    {
      this.mActionButton1Visible = true;
      this.mActionId1 = paramInt1;
      this.mActionButton1.setImageResource(paramInt2);
      this.mActionButton1.setContentDescription(getContext().getString(paramInt3));
      if (this.mActive)
      {
        ImageView localImageView2 = this.mActionButton1;
        if (!this.mActionButton1Visible)
          break label105;
        j = 0;
        localImageView2.setVisibility(j);
        ImageView localImageView3 = this.mRefreshButton;
        if (!isRefreshButtonVisible())
          break label112;
        boolean bool2 = this.mProgressIndicatorVisible;
        k = 0;
        if (bool2)
          break label112;
        label97: localImageView3.setVisibility(k);
      }
    }
    label105: label112: 
    do
    {
      return;
      j = 8;
      break;
      k = 8;
      break label97;
      if (this.mActionButton2Visible)
        break label203;
      this.mActionButton2Visible = true;
      this.mActionId2 = paramInt1;
      this.mActionButton2.setImageResource(paramInt2);
      this.mActionButton2.setContentDescription(getContext().getString(paramInt3));
    }
    while (!this.mActive);
    ImageView localImageView1 = this.mActionButton2;
    boolean bool1 = this.mActionButton2Visible;
    int i = 0;
    if (bool1);
    while (true)
    {
      localImageView1.setVisibility(i);
      break;
      i = 8;
    }
    label203: throw new IllegalArgumentException("Only two action buttons are supported");
  }

  public final void showPrimarySpinner(SpinnerAdapter paramSpinnerAdapter, int paramInt)
  {
    this.mPrimarySpinnerVisible = true;
    this.mPrimarySpinner.setAdapter(paramSpinnerAdapter);
    int i = paramSpinnerAdapter.getCount();
    if (i > 0)
      this.mPrimarySpinner.setSelection(paramInt);
    View localView;
    if (this.mActive)
    {
      localView = this.mPrimarySpinnerContainer;
      if (i <= 0)
        break label60;
    }
    label60: for (int j = 0; ; j = 8)
    {
      localView.setVisibility(j);
      return;
    }
  }

  public final void showProgressIndicator()
  {
    this.mProgressIndicatorVisible = true;
    if (this.mActive)
    {
      this.mRefreshButton.setVisibility(8);
      if ((this.mRefreshButtonVisibleIfRoom) && (!isRefreshButtonVisible()))
      {
        this.mActionButton1.setVisibility(8);
        this.mActionButton2.setVisibility(8);
      }
      this.mProgressIndicator.setVisibility(0);
    }
  }

  public final void showRefreshButton()
  {
    this.mRefreshButtonVisible = true;
    if ((this.mActive) && (!this.mProgressIndicatorVisible))
      this.mRefreshButton.setVisibility(0);
  }

  public final void showRefreshButtonIfRoom()
  {
    this.mRefreshButtonVisibleIfRoom = true;
    if ((this.mActive) && (!this.mProgressIndicatorVisible) && (isRefreshButtonVisible()))
      this.mRefreshButton.setVisibility(0);
  }

  public final void showSearchView()
  {
    this.mSearchViewVisible = true;
    if (this.mActive)
      this.mSearchViewContainer.setVisibility(0);
  }

  public final void showTitle(int paramInt)
  {
    boolean bool1;
    TextView localTextView;
    int i;
    if (paramInt != 0)
    {
      bool1 = true;
      this.mTitleVisible = bool1;
      this.mTitle.setText(paramInt);
      if (this.mActive)
      {
        localTextView = this.mTitle;
        boolean bool2 = this.mTitleVisible;
        i = 0;
        if (!bool2)
          break label57;
      }
    }
    while (true)
    {
      localTextView.setVisibility(i);
      return;
      bool1 = false;
      break;
      label57: i = 8;
    }
  }

  public final void showTitle(String paramString)
  {
    boolean bool1;
    TextView localTextView;
    int i;
    if (!TextUtils.isEmpty(paramString))
    {
      bool1 = true;
      this.mTitleVisible = bool1;
      this.mTitle.setText(paramString);
      if (this.mActive)
      {
        localTextView = this.mTitle;
        boolean bool2 = this.mTitleVisible;
        i = 0;
        if (!bool2)
          break label60;
      }
    }
    while (true)
    {
      localTextView.setVisibility(i);
      return;
      bool1 = false;
      break;
      label60: i = 8;
    }
  }

  public final void startContextActionMode()
  {
    if (!this.mContextActionMode)
    {
      this.mContextActionMode = true;
      if (this.mActive)
      {
        this.mUpButton.setVisibility(8);
        this.mDoneButton.setVisibility(0);
      }
    }
  }

  public final void updateRefreshButtonIcon(boolean paramBoolean)
  {
    this.mRefreshHighlighted = paramBoolean;
    ImageView localImageView = this.mRefreshButton;
    Resources localResources = getResources();
    if (paramBoolean);
    for (int i = R.drawable.ic_refresh_blue; ; i = R.drawable.ic_refresh)
    {
      localImageView.setImageDrawable(localResources.getDrawable(i));
      return;
    }
  }

  public static abstract interface HostActionBarListener
  {
    public abstract void onActionBarInvalidated();

    public abstract void onActionButtonClicked(int paramInt);

    public abstract void onPrimarySpinnerSelectionChange(int paramInt);

    public abstract void onRefreshButtonClicked();
  }

  public static abstract interface OnDoneButtonClickListener
  {
    public abstract void onDoneButtonClick();
  }

  public static abstract interface OnUpButtonClickListener
  {
    public abstract void onUpButtonClick();
  }

  private class PopupMenuListener
    implements PopupMenu.OnMenuItemClickListener
  {
    private PopupMenuListener()
    {
    }

    public boolean onMenuItemClick(MenuItem paramMenuItem)
    {
      return ((Activity)HostActionBar.this.getContext()).onOptionsItemSelected(paramMenuItem);
    }
  }

  private final class PopupMenuListenerV14 extends HostActionBar.PopupMenuListener
    implements PopupMenu.OnDismissListener
  {
    private PopupMenuListenerV14()
    {
      super((byte)0);
    }

    public final void onDismiss(PopupMenu paramPopupMenu)
    {
      if (paramPopupMenu == HostActionBar.this.mOverflowPopupMenu)
        HostActionBar.access$402(HostActionBar.this, false);
      while (true)
      {
        return;
        HostActionBar.access$502(HostActionBar.this, false);
      }
    }
  }

  static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
    };
    boolean overflowPopupMenuVisible;
    boolean sharePopupMenuVisible;

    private SavedState(Parcel paramParcel)
    {
      super();
      boolean bool2;
      if (paramParcel.readInt() != 0)
      {
        bool2 = bool1;
        this.overflowPopupMenuVisible = bool2;
        if (paramParcel.readInt() == 0)
          break label39;
      }
      while (true)
      {
        this.sharePopupMenuVisible = bool1;
        return;
        bool2 = false;
        break;
        label39: bool1 = false;
      }
    }

    SavedState(Parcelable paramParcelable)
    {
      super();
    }

    public String toString()
    {
      String str = Integer.toHexString(System.identityHashCode(this));
      return "HostActionBar.SavedState{" + str + " overflowPopupMenuVisible=" + this.overflowPopupMenuVisible + " sharePopupMenuVisible=" + this.sharePopupMenuVisible + "}";
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      int i = 1;
      super.writeToParcel(paramParcel, paramInt);
      int j;
      if (this.overflowPopupMenuVisible)
      {
        j = i;
        paramParcel.writeInt(j);
        if (!this.sharePopupMenuVisible)
          break label43;
      }
      while (true)
      {
        paramParcel.writeInt(i);
        return;
        j = 0;
        break;
        label43: i = 0;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.HostActionBar
 * JD-Core Version:    0.6.2
 */