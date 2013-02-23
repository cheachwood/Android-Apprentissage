package com.google.android.apps.plus.hangout;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.apps.plus.R.anim;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.util.Property;

public class ToastsView extends FrameLayout
{
  private final Animation animIn;
  private final Animation animOut;
  private final EventHandler eventHandler = new EventHandler((byte)0);
  private Runnable hideToastRunnable;
  private ImageView imageView;
  private final Handler mHandler = new Handler(Looper.getMainLooper());
  private TextView mTextView;

  public ToastsView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    View localView = LayoutInflater.from(paramContext).inflate(R.layout.hangout_toasts_view, this, true);
    this.imageView = ((ImageView)localView.findViewById(R.id.toast_icon));
    this.mTextView = ((TextView)localView.findViewById(R.id.toast_text));
    this.hideToastRunnable = new Runnable()
    {
      public final void run()
      {
        ToastsView.this.startAnimation();
      }
    };
    this.animIn = AnimationUtils.loadAnimation(paramContext, R.anim.fade_in);
    this.animIn.setAnimationListener(new Animation.AnimationListener()
    {
      public final void onAnimationEnd(Animation paramAnonymousAnimation)
      {
      }

      public final void onAnimationRepeat(Animation paramAnonymousAnimation)
      {
      }

      public final void onAnimationStart(Animation paramAnonymousAnimation)
      {
        ToastsView.this.setVisibility(0);
      }
    });
    this.animOut = AnimationUtils.loadAnimation(paramContext, R.anim.fade_out);
    this.animOut.setAnimationListener(new Animation.AnimationListener()
    {
      public final void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        ToastsView.this.setVisibility(8);
      }

      public final void onAnimationRepeat(Animation paramAnonymousAnimation)
      {
      }

      public final void onAnimationStart(Animation paramAnonymousAnimation)
      {
      }
    });
  }

  public final void addToast(int paramInt)
  {
    addToast(new StringToastInfo(getContext().getResources().getString(paramInt)));
  }

  public final void addToast(ToastInfo paramToastInfo)
  {
    this.imageView.setVisibility(0);
    paramToastInfo.populateView(this.imageView, this.mTextView);
    if (!TextUtils.isEmpty(this.mTextView.getText()))
    {
      this.mHandler.removeCallbacks(this.hideToastRunnable);
      this.mHandler.postDelayed(this.hideToastRunnable, 5000L);
      startAnimation(this.animIn);
    }
  }

  public final void onPause()
  {
    GCommApp.getInstance(getContext()).unregisterForEvents(getContext(), this.eventHandler, false);
  }

  public final void onResume()
  {
    GCommApp.getInstance(getContext()).registerForEvents(getContext(), this.eventHandler, false);
  }

  private final class EventHandler extends GCommEventHandler
  {
    private EventHandler()
    {
    }

    public final void onMediaBlock(MeetingMember paramMeetingMember1, MeetingMember paramMeetingMember2, boolean paramBoolean)
    {
      super.onMediaBlock(paramMeetingMember1, paramMeetingMember2, paramBoolean);
      ToastsView.this.addToast(new ToastsView.MediaBlockToast(ToastsView.this, paramMeetingMember1, paramMeetingMember2, paramBoolean));
    }

    public final void onMeetingMemberExited(MeetingMember paramMeetingMember)
    {
      super.onMeetingMemberExited(paramMeetingMember);
      ToastsView.this.addToast(new ToastsView.MeetingMemberToast(ToastsView.this, paramMeetingMember));
    }

    public final void onMeetingMemberPresenceConnectionStatusChanged(MeetingMember paramMeetingMember)
    {
      if (GCommApp.getInstance(ToastsView.this.getContext()).shouldShowToastForMember(paramMeetingMember))
        ToastsView.this.addToast(new ToastsView.MeetingMemberToast(ToastsView.this, paramMeetingMember));
    }

    public final void onRemoteMute(MeetingMember paramMeetingMember1, MeetingMember paramMeetingMember2)
    {
      super.onRemoteMute(paramMeetingMember1, paramMeetingMember2);
      ToastsView.this.addToast(new ToastsView.RemoteMuteToast(ToastsView.this, paramMeetingMember1, paramMeetingMember2));
    }

    public final void onVCardResponse(MeetingMember paramMeetingMember)
    {
      super.onVCardResponse(paramMeetingMember);
      if (GCommApp.getInstance(ToastsView.this.getContext()).shouldShowToastForMember(paramMeetingMember))
        ToastsView.this.addToast(new ToastsView.MeetingMemberToast(ToastsView.this, paramMeetingMember));
    }
  }

  private final class MediaBlockToast extends ToastsView.ToastInfo
  {
    private final MeetingMember mBlockee;
    private final MeetingMember mBlocker;
    private final boolean mIsRecording;

    MediaBlockToast(MeetingMember paramMeetingMember1, MeetingMember paramBoolean, boolean arg4)
    {
      super((byte)0);
      this.mBlockee = paramMeetingMember1;
      this.mBlocker = paramBoolean;
      boolean bool;
      if (Property.FORCE_HANGOUT_RECORD_ABUSE.getBoolean())
        bool = true;
      this.mIsRecording = bool;
    }

    final void populateView(ImageView paramImageView, TextView paramTextView)
    {
      Resources localResources = ToastsView.this.getResources();
      String str1;
      if (this.mIsRecording)
      {
        str1 = localResources.getString(R.string.hangout_recording_abuse);
        paramImageView.setVisibility(8);
      }
      while (true)
      {
        paramTextView.setText(str1);
        return;
        if ((this.mBlockee != null) && (this.mBlocker != null))
        {
          Avatars.renderAvatar(ToastsView.this.getContext(), this.mBlockee, paramImageView);
          String str2 = this.mBlockee.getName(ToastsView.this.getContext());
          String str3 = this.mBlocker.getName(ToastsView.this.getContext());
          if (this.mBlocker.isSelf())
            str1 = localResources.getString(R.string.hangout_media_block_by_self, new Object[] { str2 });
          else if (this.mBlockee.isSelf())
            str1 = localResources.getString(R.string.hangout_media_block_to_self, new Object[] { str3 });
          else
            str1 = localResources.getString(R.string.hangout_media_block, new Object[] { str3, str2 });
        }
        else
        {
          str1 = null;
        }
      }
    }
  }

  private final class MeetingMemberToast extends ToastsView.ToastInfo
  {
    private final MeetingMember meetingMember;
    private final int messageId;

    MeetingMemberToast(MeetingMember arg2)
    {
      super((byte)0);
      Object localObject;
      this.meetingMember = localObject;
      switch (ToastsView.4.$SwitchMap$com$google$android$apps$plus$hangout$MeetingMember$Status[localObject.getCurrentStatus().ordinal()])
      {
      default:
        this.messageId = -1;
      case 1:
      case 2:
      case 3:
      }
      while (true)
      {
        return;
        if (localObject.getPreviousStatus() == MeetingMember.Status.CONNECTING)
        {
          this.messageId = R.string.hangout_member_unable_to_join;
        }
        else
        {
          this.messageId = R.string.hangout_member_exiting_meeting;
          continue;
          this.messageId = -1;
          continue;
          this.messageId = R.string.hangout_member_entering_meeting;
        }
      }
    }

    final void populateView(ImageView paramImageView, TextView paramTextView)
    {
      if (this.messageId == -1);
      while (true)
      {
        return;
        Avatars.renderAvatar(ToastsView.this.getContext(), this.meetingMember, paramImageView);
        String str = this.meetingMember.getName(ToastsView.this.getContext());
        paramTextView.setText(String.format(ToastsView.this.getResources().getString(this.messageId), new Object[] { str }));
      }
    }
  }

  private final class RemoteMuteToast extends ToastsView.ToastInfo
  {
    private final MeetingMember mutee;
    private final MeetingMember muter;

    RemoteMuteToast(MeetingMember paramMeetingMember1, MeetingMember arg3)
    {
      super((byte)0);
      this.mutee = paramMeetingMember1;
      Object localObject;
      this.muter = localObject;
    }

    final void populateView(ImageView paramImageView, TextView paramTextView)
    {
      Avatars.renderAvatar(ToastsView.this.getContext(), this.mutee, paramImageView);
      String str1 = this.mutee.getName(ToastsView.this.getContext());
      String str2 = this.muter.getName(ToastsView.this.getContext());
      String str3;
      if (this.muter.isSelf())
        str3 = ToastsView.this.getResources().getString(R.string.hangout_remote_mute_by_self, new Object[] { str1 });
      while (true)
      {
        paramTextView.setText(str3);
        return;
        if (this.mutee.isSelf())
          str3 = ToastsView.this.getResources().getString(R.string.hangout_remote_mute_to_self, new Object[] { str2 });
        else
          str3 = ToastsView.this.getResources().getString(R.string.hangout_remote_mute, new Object[] { str2, str1 });
      }
    }
  }

  private final class StringToastInfo extends ToastsView.ToastInfo
  {
    private final String string;

    StringToastInfo(String arg2)
    {
      super((byte)0);
      Object localObject;
      this.string = localObject;
    }

    final void populateView(ImageView paramImageView, TextView paramTextView)
    {
      paramImageView.setVisibility(8);
      paramTextView.setText(this.string);
    }
  }

  private abstract class ToastInfo
  {
    private ToastInfo()
    {
    }

    abstract void populateView(ImageView paramImageView, TextView paramTextView);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.ToastsView
 * JD-Core Version:    0.6.2
 */