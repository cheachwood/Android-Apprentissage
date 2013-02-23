package com.google.android.apps.plus.iu;

public abstract interface Uploader
{
  public static final class LocalIoException extends Exception
  {
    private static final long serialVersionUID = -1384577611439153329L;

    public LocalIoException(Throwable paramThrowable)
    {
      super();
    }
  }

  public static final class MediaFileChangedException extends Exception
  {
    private static final long serialVersionUID = -8858438283331535589L;

    public MediaFileChangedException(String paramString)
    {
      super();
    }
  }

  public static final class MediaFileUnavailableException extends Exception
  {
    private static final long serialVersionUID = 6439686480505899471L;

    public MediaFileUnavailableException(Throwable paramThrowable)
    {
      super();
    }
  }

  public static final class PicasaQuotaException extends Exception
  {
    private static final long serialVersionUID = -22525693778211948L;

    public PicasaQuotaException(String paramString)
    {
      super();
    }
  }

  public static final class RestartException extends Exception
  {
    private static final long serialVersionUID = -2575549139581664777L;

    public RestartException(String paramString)
    {
      super();
    }
  }

  public static final class UnauthorizedException extends Exception
  {
    private static final long serialVersionUID = 7476449921115679307L;

    public UnauthorizedException(String paramString)
    {
      super();
    }

    public UnauthorizedException(Throwable paramThrowable)
    {
      super();
    }
  }

  public static final class UploadException extends Exception
  {
    private static final long serialVersionUID = 4567932751848488557L;

    public UploadException(String paramString)
    {
      super();
    }

    public UploadException(String paramString, Throwable paramThrowable)
    {
      super(paramThrowable);
    }
  }

  public static abstract interface UploadProgressListener
  {
    public abstract void onFileChanged(UploadTaskEntry paramUploadTaskEntry);

    public abstract void onProgress(UploadTaskEntry paramUploadTaskEntry);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.Uploader
 * JD-Core Version:    0.6.2
 */