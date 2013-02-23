package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsEventData;
import com.google.android.apps.plus.fragments.EventActiveState;
import com.google.api.services.plusi.model.PlusEvent;

public class EventRsvpSpinnerLayout extends ExactLayout
  implements View.OnClickListener, AdapterView.OnItemSelectedListener
{
  private static Drawable sAddPhotosDrawable;
  private static String sAddPhotosText;
  private static boolean sInitialized;
  private static Drawable sInviteMoreDrawable;
  private static String sInviteMoreText;
  private static int sPadding;
  private EventActionButtonLayout mActionButton;
  private int mCurrentSelectionIndex = -1;
  private PlusEvent mEvent;
  private EventActionListener mEventActionListener;
  private boolean mEventOver;
  private EventRsvpListener mListener;
  private Spinner mRsvpSpinner;
  private boolean mShowActionButton;
  private RsvpSpinnerAdapter mSpinnerAdapter;

  public EventRsvpSpinnerLayout(Context paramContext)
  {
    super(paramContext);
    init(paramContext, null, 0);
  }

  public EventRsvpSpinnerLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet, 0);
  }

  public EventRsvpSpinnerLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet, paramInt);
  }

  private void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    if (!sInitialized)
    {
      Resources localResources = paramContext.getResources();
      sPadding = localResources.getDimensionPixelSize(R.dimen.event_card_details_padding);
      sInviteMoreDrawable = localResources.getDrawable(R.drawable.icn_events_rsvp_invite_more);
      sInviteMoreText = localResources.getString(R.string.event_button_invite_more_label);
      sAddPhotosDrawable = localResources.getDrawable(R.drawable.icn_events_rsvp_add_photo);
      sAddPhotosText = localResources.getString(R.string.event_button_add_photos_label);
      sInitialized = true;
    }
    this.mRsvpSpinner = new Spinner(paramContext);
    this.mRsvpSpinner.setLayoutParams(new ExactLayout.LayoutParams(-1, -2));
    addView(this.mRsvpSpinner);
    this.mActionButton = new EventActionButtonLayout(paramContext, paramAttributeSet, paramInt);
    this.mActionButton.setOnClickListener(this);
    addView(this.mActionButton);
    setPadding(sPadding, sPadding, sPadding, sPadding);
  }

  public final void bind(PlusEvent paramPlusEvent, EventActiveState paramEventActiveState, EventRsvpListener paramEventRsvpListener, EventActionListener paramEventActionListener)
  {
    this.mListener = paramEventRsvpListener;
    this.mEventActionListener = paramEventActionListener;
    this.mEvent = paramPlusEvent;
    long l = System.currentTimeMillis();
    this.mEventOver = EsEventData.isEventOver(this.mEvent, l);
    this.mSpinnerAdapter = new RsvpSpinnerAdapter(getContext(), this.mEventOver);
    this.mRsvpSpinner.setAdapter(this.mSpinnerAdapter);
    String str;
    if (paramEventActiveState.temporalRsvpValue != null)
    {
      str = paramEventActiveState.temporalRsvpValue;
      this.mCurrentSelectionIndex = RsvpSpinnerAdapter.access$000(this.mSpinnerAdapter, str);
      this.mRsvpSpinner.setSelection(this.mCurrentSelectionIndex);
      this.mSpinnerAdapter.notifyDataSetChanged();
      this.mRsvpSpinner.setOnItemSelectedListener(this);
      this.mRsvpSpinner.setEnabled(paramEventActiveState.isRsvpEnabled);
      if ((this.mCurrentSelectionIndex != RsvpSpinnerAdapter.access$000(this.mSpinnerAdapter, "ATTENDING")) || (((!this.mEventOver) || (!EsEventData.canViewerAddPhotos(this.mEvent))) && ((this.mEventOver) || (!paramEventActiveState.canInviteOthers))))
        break label194;
    }
    label194: for (boolean bool = true; ; bool = false)
    {
      this.mShowActionButton = bool;
      return;
      str = EsEventData.getRsvpType(paramPlusEvent);
      break;
    }
  }

  protected void measureChildren(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j;
    if (!this.mShowActionButton)
    {
      j = i;
      measure(this.mRsvpSpinner, j, 1073741824, 0, 0);
      setCorner(this.mRsvpSpinner, 0, 0);
      if (!this.mShowActionButton)
        break label148;
      measure(this.mActionButton, j, 1073741824, this.mRsvpSpinner.getMeasuredHeight(), 1073741824);
      setCorner(this.mActionButton, j + 0 + sPadding, 0);
      this.mActionButton.setVisibility(0);
      if ((this.mEvent == null) || (!this.mEventOver))
        break label132;
      this.mActionButton.bind(sAddPhotosText, sAddPhotosDrawable);
    }
    while (true)
    {
      return;
      j = Math.max(0, (i - sPadding) / 2);
      break;
      label132: this.mActionButton.bind(sInviteMoreText, sInviteMoreDrawable);
      continue;
      label148: this.mActionButton.setVisibility(8);
    }
  }

  public void onClick(View paramView)
  {
    if ((this.mEventActionListener != null) && (paramView == this.mActionButton))
    {
      if (!this.mEventOver)
        break label32;
      this.mEventActionListener.onAddPhotosClicked();
    }
    while (true)
    {
      return;
      label32: this.mEventActionListener.onInviteMoreClicked();
    }
  }

  public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    int i;
    if (this.mCurrentSelectionIndex != paramInt)
    {
      if (this.mCurrentSelectionIndex != -1)
        break label87;
      i = 1;
      if (i == 0);
      switch (paramInt)
      {
      default:
        label52: this.mCurrentSelectionIndex = paramInt;
        if (this.mCurrentSelectionIndex != RsvpSpinnerAdapter.access$000(this.mSpinnerAdapter, "ATTENDING"))
          break;
      case 0:
      case 1:
      case 2:
      }
    }
    for (boolean bool = true; ; bool = false)
    {
      this.mShowActionButton = bool;
      requestLayout();
      return;
      label87: i = 0;
      break;
      this.mListener.onRsvpChanged("ATTENDING");
      break label52;
      EventRsvpListener localEventRsvpListener = this.mListener;
      if (this.mEventOver);
      for (String str = "NOT_ATTENDING"; ; str = "MAYBE")
      {
        localEventRsvpListener.onRsvpChanged(str);
        break;
      }
      this.mListener.onRsvpChanged("NOT_ATTENDING");
      break label52;
    }
  }

  public void onNothingSelected(AdapterView<?> paramAdapterView)
  {
  }

  private final class RsvpSpinnerAdapter extends BaseAdapter
  {
    private Context mContext;
    private boolean mPast;

    public RsvpSpinnerAdapter(Context paramBoolean, boolean arg3)
    {
      boolean bool;
      this.mPast = bool;
      this.mContext = paramBoolean;
    }

    public final int getCount()
    {
      if (this.mPast);
      for (int i = 2; ; i = 3)
        return i;
    }

    public final Object getItem(int paramInt)
    {
      return Integer.valueOf(paramInt);
    }

    public final long getItemId(int paramInt)
    {
      return paramInt;
    }

    public final View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      Object localObject = null;
      View localView = null;
      TextView localTextView;
      Resources localResources;
      int i;
      switch (paramInt)
      {
      default:
        if (localView != null)
        {
          localTextView = (TextView)((TextView)localView.findViewById(R.id.text)).findViewById(R.id.text);
          if (localTextView != null)
          {
            localResources = EventRsvpSpinnerLayout.this.getContext().getResources();
            if (!"MAYBE".equals(localObject))
              break label185;
            i = R.string.event_rsvp_maybe;
          }
        }
        break;
      case 0:
      case 1:
      case 2:
      }
      while (true)
      {
        localTextView.setText(localResources.getString(i));
        return localView;
        localView = LayoutInflater.from(this.mContext).inflate(R.layout.event_rsvp_attending, paramViewGroup, false);
        localObject = "ATTENDING";
        break;
        if (!this.mPast)
        {
          localView = LayoutInflater.from(this.mContext).inflate(R.layout.event_rsvp_maybe, paramViewGroup, false);
          localObject = "MAYBE";
          break;
        }
        localView = LayoutInflater.from(this.mContext).inflate(R.layout.event_rsvp_not_attending, paramViewGroup, false);
        localObject = "NOT_ATTENDING";
        break;
        label185: if ("NOT_ATTENDING".equals(localObject))
        {
          i = R.string.event_rsvp_not_attending;
        }
        else
        {
          boolean bool = "ATTENDING".equals(localObject);
          i = 0;
          if (bool)
            i = R.string.event_rsvp_attending;
        }
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.EventRsvpSpinnerLayout
 * JD-Core Version:    0.6.2
 */