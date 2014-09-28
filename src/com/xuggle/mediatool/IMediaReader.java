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

import java.awt.image.BufferedImage;

import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IContainerFormat;
import com.xuggle.xuggler.IError;
import com.xuggle.xuggler.IVideoPicture;

/**
 * 一个{@link IMediaCoder}从一个{@link IContainer}读取和解码媒体。
 * 
 * 
 * <p>
 * {@link IMediaReader}打开一个media container，从中读取packets。
 * 解码数据，然后分发数据信息到已注册的{@link IMediaListener}对象。
 * 主要关注的方法是{@link #readPacket()}.
 * 
 * </p>
 * <p>
 * 
 * 这里是一个很简单的程序的例子，当{@link IMediaReader}打开一个container
 * 后打印一行
 * 
 *  </p>
 *  <pre>
 *  IMediaDebugListener myListener = new MediaListenerAdapter(){
 *    public void onOpen(IMediaGenerator pipe) {
 *      System.out.println("opened: " + ((IMediaReader)pipe).getUrl());
 *    }
 *  };
 *  IMediaReader reader = ToolFactory.makeReader("myinputfile.flv");
 *  reader.addListener(myListener);
 *  while(reader.readPacket() == null)
 *    ;
 *  </pre>
 *  <p>
 *  
 *  这里有一个稍微有用一点的例子，我们读取文件并实时显示。
 *  
 *  </p>
 *  <pre>
 *  IMediaReader reader = ToolFactory.makeReader("myinputfile.flv");
 *  reader.addListener(ToolFactory.makeViewer());
 *  while(reader.readPacket() == null)
 *    ;
 *  </pre>
 *  <p>
 *  For examples of this class in action, see the
 *  com.xuggle.mediatool.demos package.
 *  </p>
 * @author trebor
 * @author aclarke
 *
 */

public interface IMediaReader extends IMediaCoder
{

  /**
   * 设置底层container支持动态流的开关。默认值为false。查看
   * {@link IContainer#open(String, IContainer.Type, IContainerFormat, boolean, boolean)}
   * 
   * <p>
   * 
   * 如果设置为false,{@link IMediaReader}假设在{link #open()}调用之后，不会在添加新
   * 流到后面，并且会搜索整个媒体文件查找元数据。如果为true，{@ IMediaReader}不会预读，
   * 取而代之，它会在{@link #readPacket()}返回新流的第一个packet时搜寻流的元数据。注意
   * {@link IMediaWriter}只能从一个这个值被置为false的{@link IMediaReader}初始化。
   * 
   * </p>
   * <p>
   * 
   * 要生效，MediaReader不应该由一个已经打开的{@link IContainer}创建，这个方法
   * 必须在第一次调用{@link #readPacket()}之前调用。
   * 
   * </p>
   * 
   * @param streamsCanBeAddedDynamically true if new streams may appear at any
   *        time during a {@link #readPacket} call
   * 
   * @throws RuntimeException if the media container is already open
   */

  public abstract void setAddDynamicStreams(boolean streamsCanBeAddedDynamically);

  /** 
   * Report if the underlying media container supports adding dynamic
   * streams.  See {@link IContainer#open(String, IContainer.Type,
   * IContainerFormat, boolean, boolean)}.
   * 
   * @return true if new streams can may appear at any time during a
   *         {@link #readPacket} call
   * @see #setAddDynamicStreams(boolean)
   */

  public abstract boolean canAddDynamicStreams();

  /** 
   * 设置，是否底层媒体容器在容器打开时试图创建所有的元数据，潜在的这回阻塞知道它
   * 准备好足够的数据以找到容器中的所有流。如果置为false，它只会阻塞在读取容器格式
   * 的最小头文件部分。查看 See {@link IContainer#open(String, IContainer.Type, IContainerFormat, boolean, boolean)}.
   * 默认值为true。
   *
   * <p>
   * 
   * To have an effect, the MediaReader must not have been created with
   * an already open {@link IContainer}, and this method must be called
   * before the first call to {@link #readPacket}.
   *
   * </p>
   * 
   * @param queryStreamMetaData true if meta data is to be queried
   * 
   * @throws RuntimeException if the media container is already open
   */

  public abstract void setQueryMetaData(boolean queryStreamMetaData);

  /** 
   * Report if the underlying media container will attempt to establish
   * all meta data when the container is opened, which will potentially
   * block until it has ready enough data to find all streams in a
   * container.  If false, it will only block to read a minimal header
   * for this container format.  See {@link IContainer#open(String,
   * IContainer.Type, IContainerFormat, boolean, boolean)}.
   * 
   * @return true meta data will be queried
   * @see #setQueryMetaData(boolean)
   */

  public abstract boolean willQueryMetaData();

  /**
   * Should {@link IMediaReader} automatically call {@link #close()}, only if
   * ERROR_EOF is returned from {@link #readPacket}. Otherwise {@link #close()}
   * is automatically called when any error value is returned. The default value
   * for this is false.
   * 
   * @param closeOnEofOnly true if meta data is to be queried
   * 
   * @throws RuntimeException if the media container is already open
   */

  public abstract void setCloseOnEofOnly(boolean closeOnEofOnly);

  /** 
   * Report if close will called only if ERROR_EOF is returned from
   * {@link #readPacket}.  Otherwise close is called when any error
   * value is returned.  The default value for this is false.
   * 
   * @return true if will close on ERROR_EOF only
   * @see #setCloseOnEofOnly(boolean)
   */

  public abstract boolean willCloseOnEofOnly();

  /**
   * Decodes the next packet and calls all registered {@link IMediaListener}
   * objects.
   * 
   * <p>
   * 
   * If a complete {@link IVideoPicture} or {@link IAudioSamples} set are
   * decoded, it will be dispatched to the listeners added to the media reader.
   * 
   * </p>
   * 
   * <p>
   * 
   * This method will automatically call {@link #open()} if it has not
   * already been called, and will automatically call {@link #close()} when
   * it reads an error or end of file from the file.  The default
   * close behavior can be changed with {@link #setCloseOnEofOnly(boolean)}.
   * 
   * </p>
   * 
   * @return null if there are more packets to read, otherwise return an IError
   *         instance. If {@link IError#getType()} ==
   *         {@link com.xuggle.xuggler.IError.Type#ERROR_EOF} then end of file
   *         has been reached.
   */

  public abstract IError readPacket();

  /**
   * Asks the {@link IMediaReader} to generate {@link BufferedImage} images when
   * calling
   * {@link IMediaListener#onVideoPicture(IVideoPictureEvent)}
   * .
   * 
   * <p>
   * NOTE: Only {@link BufferedImage#TYPE_3BYTE_BGR} is supported today.
   * </p>
   * 
   * <p>
   * If set to a non-negative value, {@link IMediaReader} will resample any
   * video data it has decoded into the right colorspace for the
   * {@link BufferedImage}, and generate a new {@link BufferedImage} to pass in
   * on each
   * {@link IMediaListener#onVideoPicture(IVideoPictureEvent)
   * }
   * call.
   * </p>
   * 
   * @param bufferedImageType The buffered image type (e.g.
   *        {@link BufferedImage#TYPE_3BYTE_BGR}) you want {@link IMediaReader}
   *        to generate. Set to -1 to disable this feature.
   * 
   * @see BufferedImage
   */

  public abstract void setBufferedImageTypeToGenerate(int bufferedImageType);

  /**
   * Get the {@link BufferedImage} type this {@link IMediaReader} will
   * generate.
   * @return the type, or -1 if disabled.
   * 
   * @see #getBufferedImageTypeToGenerate()
   */

  public abstract int getBufferedImageTypeToGenerate();
  
  /**
   * {@inheritDoc}
   */

  public abstract IContainer getContainer();

  /**
   * {@inheritDoc}
   */

  public abstract String getUrl();

  /**
   * {@inheritDoc}
   */

  public abstract void open();

  /**
   * {@inheritDoc}
   */

  public abstract void close();

}
