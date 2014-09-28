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

package com.xuggle.mediatool.event;

import java.util.concurrent.TimeUnit;

import com.xuggle.mediatool.IMediaListener;
import com.xuggle.xuggler.IMediaData;

/**
 * 一个包含原始，解码后的媒体数据的 {@link IEvent}.
 * 
 * @author aclarke
 *
 */
public interface IRawMediaEvent extends IStreamEvent
{

  /**
   * 这个对象的{@link IMediaData}。
   * 如果{@link #getJavaData()}返回非null，则这个方法可能返回null。
   * <p>
   * 返回的 {@link IMediaData}只有在{@link IMediaListener}方法
   * 调用期间有效。事项不要在调用返回后访问它。
   *  如果你需要持有这个数据的一个副本，可以复制数据到你自己
   * 的对象，或者使用{@link IMediaData#copyReference()}来创建一个存活在
   * 你调用生命周期外的引用。
   * </p>
   * 
   * @return the media data, or null if unavailable
   */
  public abstract IMediaData getMediaData();

  /**
   * 这个事件注册的Java对象，如果为null，你必须使用{@link #getMediaData()}.
   * 不是所有的{@link IRawMediaEvent}都支持附加Java对象、
   * @return the object, or null if not available
   */
  public abstract Object getJavaData();

  /**
   * 获取这个媒体的时间戳(微秒) {@link TimeUnit#MICROSECONDS}.
   * @return the timeStamp, or null if none.
   */
  public abstract Long getTimeStamp();

  /**
   * 获取这个媒体的时间戳(按指定的时间单位).
   * @param unit the time unit
   * @return the time stamp, or null if none
   * @throws IllegalArgumentException if unit is null
   */
  public abstract Long getTimeStamp(TimeUnit unit);

  /**
   * 时间单位 {@link #getTimeStamp()}.
   * @return the timeUnit
   */
  public abstract TimeUnit getTimeUnit();

}
