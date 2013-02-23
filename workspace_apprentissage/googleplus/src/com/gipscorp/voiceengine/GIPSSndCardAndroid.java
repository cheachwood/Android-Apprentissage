package com.gipscorp.voiceengine;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Process;
import android.util.Log;
import java.nio.ByteBuffer;
import java.util.concurrent.locks.ReentrantLock;

class GIPSSndCardAndroid
{
  private AudioManager _audioManager;
  private AudioRecord _audioRecord = null;
  private AudioTrack _audioTrack = null;
  private int _bufferedPlaySamples = 0;
  private int _bufferedRecSamples = 0;
  private Context _context;
  private boolean _doPlayInit = true;
  private boolean _doRecInit = true;
  private boolean _isPlaying = false;
  private boolean _isRecording = false;
  private ByteBuffer _playBuffer;
  private final ReentrantLock _playLock = new ReentrantLock();
  private int _playPosition = 0;
  private ByteBuffer _recBuffer;
  private final ReentrantLock _recLock = new ReentrantLock();
  private byte[] _tempBufPlay;
  private byte[] _tempBufRec;
  final String logTag = "GIPS Snd Card";

  GIPSSndCardAndroid()
  {
    try
    {
      this._playBuffer = ByteBuffer.allocateDirect(960);
      this._recBuffer = ByteBuffer.allocateDirect(960);
      this._tempBufPlay = new byte[960];
      this._tempBufRec = new byte[960];
      return;
    }
    catch (Exception localException)
    {
      while (true)
        GIPSLog(localException.getMessage());
    }
  }

  private void GIPSLog(String paramString)
  {
    Log.d("GIPS Snd Card", paramString);
  }

  private void GIPSLogErr(String paramString)
  {
    Log.e("GIPS Snd Card", paramString);
  }

  private int GetPlayoutVolume()
  {
    if ((this._audioManager == null) && (this._context != null))
      this._audioManager = ((AudioManager)this._context.getSystemService("audio"));
    int i = -1;
    if (this._audioManager != null)
      i = this._audioManager.getStreamVolume(0);
    return i;
  }

  private int InitPlayback(int paramInt)
  {
    int i = AudioTrack.getMinBufferSize(paramInt, 2, 2);
    int j = i;
    if (i < 6000)
      j = i * 2;
    this._bufferedPlaySamples = 0;
    if (this._audioTrack != null)
    {
      this._audioTrack.release();
      this._audioTrack = null;
    }
    try
    {
      this._audioTrack = new AudioTrack(0, paramInt, 2, 2, j, 1);
      if (this._audioTrack.getState() != 1)
      {
        k = -1;
        return k;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        GIPSLog(localException.getMessage());
        int k = -1;
        continue;
        if ((this._audioManager == null) && (this._context != null))
          this._audioManager = ((AudioManager)this._context.getSystemService("audio"));
        if (this._audioManager == null)
          k = 0;
        else
          k = this._audioManager.getStreamMaxVolume(0);
      }
    }
  }

  private int InitRecording(int paramInt1, int paramInt2)
  {
    int i = 2 * AudioRecord.getMinBufferSize(paramInt2, 2, 2);
    this._bufferedRecSamples = (paramInt2 * 5 / 200);
    if (this._audioRecord != null)
    {
      this._audioRecord.release();
      this._audioRecord = null;
    }
    try
    {
      this._audioRecord = new AudioRecord(paramInt1, paramInt2, 2, 2, i);
      if (this._audioRecord.getState() != 1)
      {
        j = -1;
        return j;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        GIPSLog(localException.getMessage());
        int j = -1;
        continue;
        j = this._bufferedRecSamples;
      }
    }
  }

  private int PlayAudio(int paramInt)
  {
    this._playLock.lock();
    while (true)
    {
      int k;
      try
      {
        AudioTrack localAudioTrack = this._audioTrack;
        if (localAudioTrack == null)
        {
          this._playLock.unlock();
          m = -2;
          return m;
        }
        boolean bool1 = this._doPlayInit;
        if (bool1);
        try
        {
          Process.setThreadPriority(-19);
          this._doPlayInit = false;
          this._playBuffer.get(this._tempBufPlay);
          int i = this._audioTrack.write(this._tempBufPlay, 0, paramInt);
          this._playBuffer.rewind();
          this._bufferedPlaySamples += (i >> 1);
          int j = this._audioTrack.getPlaybackHeadPosition();
          if (j < this._playPosition)
            this._playPosition = 0;
          this._bufferedPlaySamples -= j - this._playPosition;
          this._playPosition = j;
          boolean bool2 = this._isRecording;
          k = 0;
          if (!bool2)
            k = this._bufferedPlaySamples;
          if (i != paramInt)
          {
            this._playLock.unlock();
            m = -1;
          }
        }
        catch (Exception localException)
        {
          GIPSLog("Set play thread priority failed: " + localException.getMessage());
          continue;
        }
      }
      finally
      {
        this._playLock.unlock();
      }
      this._playLock.unlock();
      int m = k;
    }
  }

  private int RecordAudio(int paramInt)
  {
    this._recLock.lock();
    while (true)
    {
      try
      {
        AudioRecord localAudioRecord = this._audioRecord;
        if (localAudioRecord == null)
        {
          this._recLock.unlock();
          i = -2;
          return i;
        }
        boolean bool = this._doRecInit;
        if (bool);
        try
        {
          Process.setThreadPriority(-19);
          this._doRecInit = false;
          this._recBuffer.rewind();
          int j = this._audioRecord.read(this._tempBufRec, 0, paramInt);
          this._recBuffer.put(this._tempBufRec);
          if (j != paramInt)
          {
            this._recLock.unlock();
            i = -1;
          }
        }
        catch (Exception localException2)
        {
          GIPSLog("Set rec thread priority failed: " + localException2.getMessage());
          continue;
        }
      }
      catch (Exception localException1)
      {
        GIPSLogErr("RecordAudio try failed: " + localException1.getMessage());
        this._recLock.unlock();
        int i = this._bufferedPlaySamples;
        continue;
      }
      finally
      {
        this._recLock.unlock();
      }
      this._recLock.unlock();
    }
  }

  private void SetAudioMode(boolean paramBoolean)
  {
    int i = Integer.parseInt(Build.VERSION.SDK);
    if ((this._audioManager == null) && (this._context != null))
      this._audioManager = ((AudioManager)this._context.getSystemService("audio"));
    if (this._audioManager == null)
    {
      Log.e("GIPS Snd Card", "Could not set audio mode - no audio manager");
      return;
    }
    if (((Build.BRAND.equals("Samsung")) || (Build.BRAND.equals("samsung"))) && (8 == i))
      if (!paramBoolean)
        break label117;
    label117: for (int j = 4; ; j = 0)
    {
      this._audioManager.setMode(j);
      if (this._audioManager.getMode() == j)
        break;
      Log.e("GIPS Snd Card", "Could not set audio mode for Samsung device");
      break;
      break;
    }
  }

  private int SetPlayoutSpeaker(boolean paramBoolean)
  {
    if ((this._audioManager == null) && (this._context != null))
      this._audioManager = ((AudioManager)this._context.getSystemService("audio"));
    int j;
    if (this._audioManager == null)
    {
      Log.e("GIPS Snd Card", "Could not change audio routing - no audio manager");
      j = -1;
      return j;
    }
    int i = Integer.parseInt(Build.VERSION.SDK);
    if ((3 == i) || (4 == i))
      if (paramBoolean)
        this._audioManager.setMode(0);
    while (true)
    {
      j = 0;
      break;
      this._audioManager.setMode(2);
      continue;
      if (((Build.BRAND.equals("Samsung")) || (Build.BRAND.equals("samsung"))) && ((5 == i) || (6 == i) || (7 == i)))
      {
        if (paramBoolean)
          this._audioManager.setMode(2);
      }
      else
      {
        this._audioManager.setSpeakerphoneOn(paramBoolean);
        continue;
      }
      this._audioManager.setSpeakerphoneOn(paramBoolean);
      this._audioManager.setMode(0);
    }
  }

  private int SetPlayoutVolume(int paramInt)
  {
    if ((this._audioManager == null) && (this._context != null))
      this._audioManager = ((AudioManager)this._context.getSystemService("audio"));
    int i = -1;
    if (this._audioManager != null)
    {
      this._audioManager.setStreamVolume(0, paramInt, 0);
      i = 0;
    }
    return i;
  }

  private int StartPlayback()
  {
    if (!this._isRecording)
      SetAudioMode(true);
    try
    {
      this._audioTrack.play();
      this._isPlaying = true;
      i = 0;
      return i;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      while (true)
      {
        localIllegalStateException.printStackTrace();
        int i = -1;
      }
    }
  }

  private int StartRecording()
  {
    if (!this._isPlaying)
      SetAudioMode(true);
    try
    {
      this._audioRecord.startRecording();
      this._isRecording = true;
      i = 0;
      return i;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      while (true)
      {
        localIllegalStateException.printStackTrace();
        int i = -1;
      }
    }
  }

  private int StopPlayback()
  {
    int i = 0;
    this._playLock.lock();
    try
    {
      int j = this._audioTrack.getPlayState();
      if (j == 3);
      try
      {
        this._audioTrack.stop();
        this._audioTrack.flush();
        this._audioTrack.release();
        this._audioTrack = null;
        this._doPlayInit = true;
        this._playLock.unlock();
        if (!this._isRecording)
          SetAudioMode(false);
        this._isPlaying = false;
        return i;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        while (true)
        {
          localIllegalStateException.printStackTrace();
          this._doPlayInit = true;
          this._playLock.unlock();
          i = -1;
        }
      }
    }
    finally
    {
      this._doPlayInit = true;
      this._playLock.unlock();
    }
  }

  private int StopRecording()
  {
    int i = 0;
    this._recLock.lock();
    try
    {
      int j = this._audioRecord.getRecordingState();
      if (j == 3);
      try
      {
        this._audioRecord.stop();
        this._audioRecord.release();
        this._audioRecord = null;
        this._doRecInit = true;
        this._recLock.unlock();
        if (!this._isPlaying)
          SetAudioMode(false);
        this._isRecording = false;
        return i;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        while (true)
        {
          localIllegalStateException.printStackTrace();
          this._doRecInit = true;
          this._recLock.unlock();
          i = -1;
        }
      }
    }
    finally
    {
      this._doRecInit = true;
      this._recLock.unlock();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.gipscorp.voiceengine.GIPSSndCardAndroid
 * JD-Core Version:    0.6.2
 */