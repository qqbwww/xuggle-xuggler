/*******************************************************************************
 * Copyright (c) 2008, 2010 Xuggle Inc.  All rights reserved.
 *  
 * This file is part of Xuggle-Xuggler-Main.
 *
 * Xuggle-Xuggler-Main is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Xuggle-Xuggler-Main is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Xuggle-Xuggler-Main.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

package com.xuggle.mediatool;

import static java.lang.System.exit;
import static java.lang.System.out;

import java.io.File;

import com.xuggle.ferry.JNIMemoryManager;
import com.xuggle.ferry.RefCounted;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.ICodec;

/**
 * <strong>Start Here --</strong>一个用于MediaTools的工厂，API的全局设定
 * <p>
 * 这里有一个{@link ToolFactory}程序打开和输入媒体文件的例子，在你的机器上播放媒体时转码所有媒体
 * 到一种新的格式，
 * </p>
 * 
 * <pre>
 * IMediaReader reader = ToolFactory.makeReader(&quot;input.mpg&quot;);
 * reader.addListener(ToolFactory.makeViewer(true));
 * reader.addListener(ToolFactory.makeWriter(&quot;output.flv&quot;, reader));
 * while (reader.readPacket() == null)
 *   ;
 * </pre>
 * 
 * @author trebor
 * @author aclarke
 * 
 */
public class ToolFactory
{

  /**
   * A private constructor to force only static members in this class.
   */
  private ToolFactory()
  {

  }

  /* IMediaReader Factories */

  /**
   * 创建一个{@link IMediaReader}从给定URL指定的媒体容器读取和分发解码的媒体
   * 
   * @param url 媒体内容的存放位置，也可以是一个文件名
   * @return An {@link IMediaReader}
   */

  public static IMediaReader makeReader(String url)
  {
    return new MediaReader(url);
  }

  /**
   * 创建一个{@link IMediaReader}从给定URL指定的媒体容器读取和分发解码的媒体
   * 
   * <p>
   * 
   * Any Xuggler resourced opened by the {@link IMediaReader} will be closed by
   * the {@link IMediaReader}, however resources opened outside the
   * {@link IMediaReader} will not be closed. In short {@link IMediaReader}
   * closes what it opens.
   * 
   * </p>
   * 
   * @param container an already created media container to read data from.
   */

  public static IMediaReader makeReader(IContainer container)
  {
    return new MediaReader(container);
  }

  /* MediaWriter constructors */
  /**
   * Use a specified {@link IMediaReader} as a source for media data and meta
   * data about the container and it's streams. The {@link IMediaReader} must be
   * configured such that streams will not be dynamically added to the
   * container, which is the default for {@link IMediaReader}.
   * 
   * @param url the url or filename of the media destination
   * @param reader the media source
   * 
   * @throws IllegalArgumentException if the specified {@link IMediaReader} is
   *         configure to allow dynamic adding of streams.
   */

  public static IMediaWriter makeWriter(String url, IMediaReader reader)
  {
    return new MediaWriter(url, reader);
  }

  /**
   * Use a specified {@link IContainer} as a source for and meta data about the
   * container and it's streams. The {@link IContainer} must be configured such
   * that streams will not be dynamically added to the container.
   * 
   * @param url the url or filename of the media destination
   * @param inputContainer the source media container which will be queries to
   *        determine what streams and media should be added when writing data.
   * 
   * @throws IllegalArgumentException if the specifed {@link IContainer} is not
   *         a of type READ or is configure to allow dynamic adding of streams.
   */

  public static IMediaWriter makeWriter(String url, IContainer inputContainer)
  {
    return new MediaWriter(url, inputContainer);
  }

  /**
   * Create a MediaWriter which will require subsequent calls to
   * {@link IMediaWriter#addVideoStream(int, int, ICodec, int, int)} and/or
   * {@link IMediaWriter#addAudioStream(int, int, ICodec, int, int)} to
   * configure the writer. Streams may be added or further configured as needed
   * until the first attempt to write data.
   * 
   * @param url the url or filename of the media destination
   */
  public static IMediaWriter makeWriter(String url)
  {
    return new MediaWriter(url);
  }

  /** Media Viewer Factories */
  /**
   * Construct a default media viewer.
   */

  public static IMediaViewer makeViewer()
  {
    return new MediaViewer();
  }

  /**
   * Construct a media viewer which plays in the specified mode.
   * 
   * @param mode the play mode of this viewer
   */

  public static IMediaViewer makeViewer(IMediaViewer.Mode mode)
  {
    return new MediaViewer(mode);
  }

  /**
   * 构造一个媒体查看器，可选地显示媒体统计信息
   *
   * @param showStats 是否显示媒体统计信息
   */

  public static IMediaViewer makeViewer(boolean showStats)
  {
    return new MediaViewer(showStats);
  }

  /**
   * Construct a media viewer which plays in the specified mode and optionally
   * shows media statistics.
   * 
   * @param mode the play mode of this viewer
   * @param showStats display media statistics
   */

  public static IMediaViewer makeViewer(IMediaViewer.Mode mode,
      boolean showStats)
  {
    return new MediaViewer(mode, showStats);
  }

  /**
   * Construct a media viewer, optionally show media statistics and specify the
   * default frame close behavior.
   * 
   * @param showStats display media statistics
   * @param defaultCloseOperation what should Swing do if the window is closed.
   *        See the {@link javax.swing.WindowConstants} documentation for valid
   *        values.
   */

  public static IMediaViewer makeViewer(boolean showStats,
      int defaultCloseOperation)
  {
    return new MediaViewer(showStats, defaultCloseOperation);
  }

  /**
   * Construct a media viewer which plays in the specified mode, optionally
   * shows media statistics and specifies the default frame close behavior.
   * 
   * @param mode the play mode of this viewer
   * @param showStats display media statistics
   * @param defaultCloseOperation what should Swing do if the window is closed.
   *        See the {@link javax.swing.WindowConstants} documentation for valid
   *        values.
   */

  public static IMediaViewer makeViewer(IMediaViewer.Mode mode,
      boolean showStats, int defaultCloseOperation)
  {
    return new MediaViewer(mode, showStats, defaultCloseOperation);
  }

  /** {@link IMediaDebugListener} Factories */

  /**
   * Construct a debug listener which logs all event types.
   */

  public static IMediaDebugListener makeDebugListener()
  {
    return new MediaDebugListener();
  }

  /**
   * Construct a debug listener with custom set of event types to log.
   * 
   * @param events the event types which will be logged
   */

  public static IMediaDebugListener makeDebugListener(
      IMediaDebugListener.Event... events)
  {
    return new MediaDebugListener(events);
  }

  /**
   * Construct a debug listener with custom set of event types to log.
   * 
   * @param mode log mode, see {@link IMediaDebugListener.Mode}
   * @param events the event types which will be logged
   */

  public static IMediaDebugListener makeDebugListener(
      IMediaDebugListener.Mode mode, IMediaDebugListener.Event... events)
  {
    return new MediaDebugListener(mode, events);
  }
  
  /**
   * Construct a debug listener with custom name and set of event types to
   * log.
   * 
   * @param name symbolic name for this listener
   * @param mode log mode, see {@link IMediaDebugListener.Mode}
   * @param events the event types which will be logged
   */

  public static IMediaDebugListener makeDebugListener(String name,
    IMediaDebugListener.Mode mode, IMediaDebugListener.Event... events)
  {
    return new MediaDebugListener(name, mode, events);
  }

  /**
   * A sample program for the {@link ToolFactory}.  If given
   * one argument on the command line, it will interpret that
   * as a media file to read and play.  If given more than one,
   * it will attempt to transcode the first file into formats
   * guessed for all the other arguments.
   * <p>
   * For example, this decodes and plays &quot;input.flv&quot;, while
   * transcoding &quot;input.flv&quot; into &quot;output1.mov&quot;
   * and &quot;output2.mp4&quot;
   * </p>
   * <pre>
   * com.xuggle.mediatool.ToolFactory input.flv output1.mov output2.mp4
   * </pre>
   * 
   * @param args input filename and option output filenames
   */

  public static void main(String[] args)
  {
    if (args.length < 1)
    {
      System.out.println("Must enter at least one Source File to read.");
      exit(0);
    }

    File source = new File(args[0]);
    if (!source.exists())
    {
      out.println("Source file does not exist: " + source);
      exit(0);
    }

    IMediaReader reader = ToolFactory.makeReader(args[0]);
    reader.addListener(ToolFactory.makeViewer(true));
    for(int i = 1; i< args.length; i++)
      reader.addListener(ToolFactory.makeWriter(args[i], reader));
    while (reader.readPacket() == null)
      do {} while(false);

  }

  /**
   * Turns on and off Turbo-Charging for the {@link ToolFactory} package.
   * <p>
   * Turbo-Charging is off by default.
   * </p>
   * <p>
   * When running Turbo-Charged {@link ToolFactory} will make a variety of tuning
   * assumptions that can speed up execution of your program, sometimes by
   * significant amounts.  {@link ToolFactory} was designed from
   * the ground up to run Turbo-Charged, but it can cause issues
   * for other com.xuggle.xuggler-based programs running
   * in the same Java process.
   * </p>
   * <p> It is safe to turn on if your program only uses
   * interfaces in the com.xuggle.mediatool API, and you are not running
   * in the same Java process as other programs using the
   * com.xuggle.xuggler API.
   * </p>
   * <p>
   * If you turn on Turbo-Charging and then access any of the
   * underlying com.xuggle.xuggler interfaces (e.g.
   * {@link IMediaCoder#getContainer()}) behind ToolFactory,
   * you must:
   * <ul>
   * <li>Call
   * {@link RefCounted#delete()} on any references returned on the stack
   * to your methods.</li>
   * <li>Call {@link RefCounted#copyReference()} on any
   * com.xuggle.xuggler parameters passed to your methods
   * that you need to keep a reference to after your method returns.</li>
   * <li>Ensure you do not call {@link RefCounted#delete()} on
   * parameters passed <strong>into</strong> your methods.  The calling
   * method is managing that reference.</li>
   * </ul>
   * <p>
   * Failure to follow these rules could lead to {@link OutOfMemoryError}
   * errors, or to premature releasing of resources.
   * </p>
   * <p>
   * Turbo-Charging works by changing the global
   * {@link com.xuggle.ferry.JNIMemoryManager.MemoryModel} that the underlying
   * com.xuggle.xuggler API in your program is using. If you are using
   * {@link ToolFactory} in a java program that contains other code using the
   * com.xuggle.xuggler API, you will force that code to use the new
   * memory model.
   * </p>
   * <p>
   * 
   * Turbo-Charging should not be used by pregnant women.
   * 
   * </p>
   * <p>
   * 
   * Badgers considering Turbo-Charging should check burrows for
   * at least three feet of vertical clearance.
   * 
   * </p>
   * <p>
   * 
   * Turbo-Charging Java Programs should be given at least 2-weeks of
   * vacation per year in order to adequately recover from the
   * Turbo-Charging experience. 
   * 
   * </p>
   * <p>
   * 
   * Turbo-Charging is illegal without a license in British Columbia.
   * Contact the Royal Canadian Mounted Police.  
   * </p>
   * <p>
   * 
   * Writing documentation about Turbo-Charging may lead to
   * excessive stupidity.  Limiting documentation to four warnings
   * or fewer is strongly recommended.
   * 
   * </p>
   * 
   * 
   * @param turbo should we turn on turbo mode
   * 
   * @see JNIMemoryManager#setMemoryModel(com.xuggle.ferry.JNIMemoryManager.MemoryModel)
   */
  public static void setTurboCharged(boolean turbo)
  {
    if (turbo)
      JNIMemoryManager
          .setMemoryModel(JNIMemoryManager.MemoryModel.NATIVE_BUFFERS);
    else
      JNIMemoryManager
          .setMemoryModel(JNIMemoryManager.MemoryModel.JAVA_STANDARD_HEAP);
  }

  /**
   * Is {@link ToolFactory} running in Turbo-Charged mode?
   * @return true if Turbo-Charged.  false if really just slogging
   *   along and finding its own way.
   */
  public static boolean isTurboCharged()
  {
    return !(JNIMemoryManager.getMemoryModel() == JNIMemoryManager.MemoryModel.JAVA_STANDARD_HEAP);
  }
}
