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

import com.xuggle.mediatool.event.AddStreamEvent;
import com.xuggle.mediatool.event.CloseCoderEvent;
import com.xuggle.mediatool.event.CloseEvent;
import com.xuggle.mediatool.event.FlushEvent;
import com.xuggle.mediatool.event.IAddStreamEvent;
import com.xuggle.mediatool.event.IAudioSamplesEvent;
import com.xuggle.mediatool.event.ICloseCoderEvent;
import com.xuggle.mediatool.event.ICloseEvent;
import com.xuggle.mediatool.event.IFlushEvent;
import com.xuggle.mediatool.event.IOpenCoderEvent;
import com.xuggle.mediatool.event.IOpenEvent;
import com.xuggle.mediatool.event.IReadPacketEvent;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.mediatool.event.IWriteHeaderEvent;
import com.xuggle.mediatool.event.IWritePacketEvent;
import com.xuggle.mediatool.event.IWriteTrailerEvent;
import com.xuggle.mediatool.event.OpenCoderEvent;
import com.xuggle.mediatool.event.OpenEvent;
import com.xuggle.mediatool.event.ReadPacketEvent;
import com.xuggle.mediatool.event.WriteHeaderEvent;
import com.xuggle.mediatool.event.WritePacketEvent;
import com.xuggle.mediatool.event.WriteTrailerEvent;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.IVideoPicture;

/**
 * 供{@link IMediaGenerator}调用，对一个已定义的事件响应.
 * 
 * <p>
 *
 * 你可以使用实现了这个接口的对象去监听{@link IMediaGenerator}对象产生的事件。
 * 要实现功能，创建你自己的{@link IMediaListener}对象，并绑定这个对象到{@link IMediaGenerator}
 * ，通过调用{@link IMediaGenerator#addListener(IMediaListener)}.方法。
 *
 * </p>
 * <p>
 * 这些方法在处理时会阻塞发起调用的{@link IMediaGenerator}，所以要尽快返回。如果你有一个
 * 长时间的运行动作要执行，另起线程。
 *
 * </p>
 */

public interface IMediaListener
{

  /**
   * 在一个 {@link IMediaReader}解码一个视频图片或 {@link IMediaWriter}编码一个视频
   * 图片时调用
   *
   * @param event An event containing either an {@link IVideoPicture}, a
   *        {@link BufferedImage}, or both. The {@link IVideoPicture} is only
   *        valid for the duration of this callback. If you need to access it
   *        after you return, you must either copy the data into your own
   *        buffers, or call {@link IVideoPicture#copyReference()} to get your
   *        own reference to the data.
   */

  public void onVideoPicture(IVideoPictureEvent event);

  /**
   * Called after audio samples have been decoded or encoded by an
   * {@link IMediaGenerator}
   * 
   * @param event An event containing the {@link IAudioSamples} for this event.
   *        The {@link IAudioSamples} in this event is only valid for the
   *        duration of this callback. If you need to access it after you
   *        return, you must either copy the data into your own buffers, or call
   *        {@link IAudioSamples#copyReference()} to get your own reference to
   *        the data.
   */

  public void onAudioSamples(IAudioSamplesEvent event);

  /**
   * Called after an {@link IMediaGenerator} is opened.
   * 
   * @param event A {@link OpenEvent}
   */

  public void onOpen(IOpenEvent event);

  /**
   * Called after an {@link IMediaGenerator} is closed.
   * 
   * @param event A {@link CloseEvent}
   */

  public void onClose(ICloseEvent event);

  /**
   * Called after an stream is added to an {@link IMediaGenerator}. This occurs
   * when a new stream is added (if writing) or encountered by the
   * {@link IMediaCoder} (if reading). If the stream is not already been opened,
   * then {@link #onOpenCoder(IOpenCoderEvent)} will be called at some later
   * point.
   * 
   * @param event A {@link AddStreamEvent} event
   */

  public void onAddStream(IAddStreamEvent event);

  /**
   * Called after a decoder or encoder is opened by a {@link IMediaCoder}
   * 
   * @param event A {@link OpenCoderEvent}
   */

  public void onOpenCoder(IOpenCoderEvent event);

  /**
   * Called after an decoder or encoder is closed by the {@link IMediaCoder}
   * .
   * 
   * @param event A {@link CloseCoderEvent}
   */

  public void onCloseCoder(ICloseCoderEvent event);

  /**
   * Called after a {@link IPacket} has been read by a
   * {@link IMediaReader}.
   * 
   * @param event A {@link ReadPacketEvent}. This {@link IPacket} in this event
   *        is only valid for the duration of this call. If you need to use the
   *        data after this call has returned, you must either copy the data in
   *        this call, or use {@link IPacket#copyReference()} to create a new
   *        object with a reference you can own.
   */

  public void onReadPacket(IReadPacketEvent event);

  /**
   * Called after a {@link IPacket} has been written by a
   * {@link IMediaWriter}.
   * 
   * @param event A {@link WritePacketEvent}. The {@link IPacket} in this event
   *        is only valid for the duration of this call. If you need to use the
   *        data after this call has returned, you must either copy the data in
   *        this call, or use {@link IPacket#copyReference()} to create a new
   *        object with a reference you can own.
   */

  public void onWritePacket(IWritePacketEvent event);

  /**
   * Called after a {@link IMediaWriter} writes the header.
   * 
   * @param event A {@link WriteHeaderEvent}
   */

  public void onWriteHeader(IWriteHeaderEvent event);

  /**
   * Called after a {@link IMediaWriter} has flushed its buffers.
   * 
   * @param event A {@link FlushEvent}
   */

  public void onFlush(IFlushEvent event);

  /**
   * Called after a {@link IMediaWriter} writes the trailer.
   * 
   * @param event A {@link WriteTrailerEvent}
   */

  public void onWriteTrailer(IWriteTrailerEvent event);
}
